/* 
 * The MIT License
 *
 * Copyright 2014 Kamnev Georgiy (nt.gocha@gmail.com).
 *
 * Данная лицензия разрешает, безвозмездно, лицам, получившим копию данного программного 
 * обеспечения и сопутствующей документации (в дальнейшем именуемыми "Программное Обеспечение"), 
 * использовать Программное Обеспечение без ограничений, включая неограниченное право на 
 * использование, копирование, изменение, объединение, публикацию, распространение, сублицензирование 
 * и/или продажу копий Программного Обеспечения, также как и лицам, которым предоставляется 
 * данное Программное Обеспечение, при соблюдении следующих условий:
 *
 * Вышеупомянутый копирайт и данные условия должны быть включены во все копии 
 * или значимые части данного Программного Обеспечения.
 *
 * ДАННОЕ ПРОГРАММНОЕ ОБЕСПЕЧЕНИЕ ПРЕДОСТАВЛЯЕТСЯ «КАК ЕСТЬ», БЕЗ ЛЮБОГО ВИДА ГАРАНТИЙ, 
 * ЯВНО ВЫРАЖЕННЫХ ИЛИ ПОДРАЗУМЕВАЕМЫХ, ВКЛЮЧАЯ, НО НЕ ОГРАНИЧИВАЯСЬ ГАРАНТИЯМИ ТОВАРНОЙ ПРИГОДНОСТИ, 
 * СООТВЕТСТВИЯ ПО ЕГО КОНКРЕТНОМУ НАЗНАЧЕНИЮ И НЕНАРУШЕНИЯ ПРАВ. НИ В КАКОМ СЛУЧАЕ АВТОРЫ 
 * ИЛИ ПРАВООБЛАДАТЕЛИ НЕ НЕСУТ ОТВЕТСТВЕННОСТИ ПО ИСКАМ О ВОЗМЕЩЕНИИ УЩЕРБА, УБЫТКОВ 
 * ИЛИ ДРУГИХ ТРЕБОВАНИЙ ПО ДЕЙСТВУЮЩИМ КОНТРАКТАМ, ДЕЛИКТАМ ИЛИ ИНОМУ, ВОЗНИКШИМ ИЗ, ИМЕЮЩИМ 
 * ПРИЧИНОЙ ИЛИ СВЯЗАННЫМ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ ИЛИ ИСПОЛЬЗОВАНИЕМ ПРОГРАММНОГО ОБЕСПЕЧЕНИЯ 
 * ИЛИ ИНЫМИ ДЕЙСТВИЯМИ С ПРОГРАММНЫМ ОБЕСПЕЧЕНИЕМ.
 */
package xyz.cofe.lang2.vm.op;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.CommentReciver;
import xyz.cofe.lang2.parser.SourceLocation;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.NewVariables;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.VariableScopeListener;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Predicate;

// TODO ScopeSender ?
// Необходимые пременные которые закрываются этой областью известны на заре парнсинга.
// А то что есть "область видимости" - Это три сущности: VariableScopeListener / Sender + RelinkVarScope

/**
 * Функция VM
 * @author gocha
 */
public class Function extends AbstractTreeNode<Value> implements
        Callable,
        Value,
        NewVariables,
        MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Function.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Function.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Function.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Function.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Function.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected String[] parameterNames = null;
    protected Value body = null;
    protected Map<String,Object> memory = null;

    /**
     * Конструктор
     */
    public Function(){
    }

    /**
     * Конструктор копирования
     * @param src
     */
    public Function(Function src){
        if (src== null) {
            throw new IllegalArgumentException("src==null");
        }
		if( src.parameterNames!=null )
			parameterNames = Arrays.copyOf(src.parameterNames, src.parameterNames.length);

        body = src.body;
        memory = src.memory;
        comment = src.comment;

        if( body!=null )body.setParent(this);
    }

    /**
     * Конструктор копирования
     * @param src
     */
    public Function(Function src,boolean deep){
        if (src== null) {
            throw new IllegalArgumentException("src==null");
        }
		if( src.parameterNames!=null )
			parameterNames = Arrays.copyOf(src.parameterNames, src.parameterNames.length);

        body = deep && src.body!=null ? src.body.deepClone() : src.body;
        memory = src.memory;
        comment = src.comment;

        if( body!=null )body.setParent(this);
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    /**
     * Комментарий к функции
     */
    protected String comment = null;

    /**
     * Комментарий к функции
     * @return комментарий
     */
    public String getComment() {
        return comment;
    }

    /**
     * Комментарий к функции
     * @param comment комментарий
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Область видимости">
    /**
     * Подписчики входа/выхода из области видимости
     */
    protected Collection<VariableScopeListener> listeners = null;

    /**
     * Возвращает подписчиков входа/выхода из области видимости
     * @return Подписчики области видимости
     */
//    @Override
    public Collection<VariableScopeListener> getScopeListeners() {
        if (listeners == null)
            listeners = new LinkedHashSet<VariableScopeListener>();
        return listeners;
    }

    //TODO Mem Работа с памятью
    /**
     * Уведомляет подписчиков о начале выполения инструкций в блоке
     */
    @SuppressWarnings("unchecked")
    private void fireEnter(Map scope) {
        if (memory == null) {
            Logger.getLogger(Block.class.getName()).severe("memory not set");
            throw new CompileException(this, "memory==null");
        }

        for (VariableScopeListener l : getScopeListeners()) {
            if (l != null)
                l.scopeEnter(scope);
        }
    }

    //TODO Mem Работа с памятью
    /**
     * Уведомляет подписчиков о завершении выполения инструкций в блоке
     */
    @SuppressWarnings("unchecked")
    private void fireExit(Map scope) {
        if (memory == null) {
            Logger.getLogger(Block.class.getName()).severe("memory not set");
            throw new CompileException(this, "memory==null");
        }

        for (VariableScopeListener l : getScopeListeners()) {
            if (l != null)
                l.scopeExit(scope);
        }
    }// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="memory">
    /**
     * Указывает память
     * @return Память
     */
    public Map<String, Object> getMemory()
    {
        return memory;
    }

    /**
     * Указывает память
     * @param memory Память
     */
    public void setMemory(Map<String, Object> memory)
    {
        this.memory = memory;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="parameterNames">
    /**
     * Указывает имена аргументов функции
     * @return Имена аргументов функции
     */
    public String[] getParameterNames()
    {
        return parameterNames;
    }

    /**
     * Указывает имена аргументов функции
     * @param parameterNames Имена аргументов функции
     */
    public void setParameterNames(String[] parameterNames)
    {
        this.parameterNames = parameterNames;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="addParameterName()">
    /**
     * Добавляет имя аргумента функции
     * @param parameterName Имя аргумента функции
     * @return Факт добавления
     */
    public boolean addParameterName(String parameterName){
        if( parameterName==null )return false;
        if( parameterNames==null ){
            parameterNames = new String[]{parameterName};
            return true;
        }
        for( String p : parameterNames ){
            if( parameterName.equals(p) )return false;
        }
        parameterNames = Arrays.copyOf(parameterNames, parameterNames.length+1);
        parameterNames[parameterNames.length-1] = parameterName;
        return true;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="body">
    /**
     * Указывает тело функции
     * @return Тело функции
     */
    public Value getBody()
    {
        return body;
    }

    /**
     * Указывает тело функции
     * @param body Тело функции
     */
    public void setBody(Value body)
    {
        this.body = body;
        if( body!=null )body.setParent(this);
    }
    //</editor-fold>

    // TODO Doc it
    public static final String argumentsVar = "arguments";
    public static final String recursionVar = "recursion";

    private static final String[] reservedVars = new String[]{
        argumentsVar,recursionVar
    };

    //<editor-fold defaultstate="collapsed" desc="getNewVaraibleNames()">
    /*
     * (non-Javadoc)
     * @see lang2.vm.NewVariables
     */
    @Override
    public String[] getNewVaraibleNames() {
        ArrayList<String> defs = new ArrayList<String>();
        String[] args = getParameterNames();
        if( args!=null )
            defs.addAll(Arrays.asList(args));
        defs.add( argumentsVar );
        defs.add( recursionVar );
        return defs.toArray(new String[]{});
    }
    //</editor-fold>

    //TODO Mem Работа с памятью
    //<editor-fold defaultstate="collapsed" desc="call()">
    /* (non-Javadoc) @see Callable */
    @Override
    public Object call(Object... arguments)
    {
        if( memory==null ){
            Logger.getLogger(Function.class.getName()).severe("memory not set");
            throw new CompileException(this,"memory==null");
        }

        if( parameterNames==null ){
            Logger.getLogger(Function.class.getName()).severe("parameterNames not set");
            throw new CompileException(this,"parameterNames==null");
        }

        for( int i=0;i<parameterNames.length;i++ )
        {
            if( parameterNames[i]==null )
            {
                Logger.getLogger(Function.class.getName()).severe("parameterNames["+i+"] not set");
                throw new CompileException(this,"parameterNames["+i+"]==null");
            }
        }

        if( body==null ){
            Logger.getLogger(Function.class.getName()).severe("function not have body");
            throw new CompileException(this,"body==null");
        }

        // Сохраняем переменные совпадающие с аргументами функции
        Map scope = Variable.createVarScope(memory);

        //TODO Дописать ? на fireEnter / fireExit, возможно оно не нужно
        storeVariables(scope);

        // Создаем переменные
        createParametersVars(arguments);
        createSpecialVars(arguments);

        Object result = null;
        try{
            // Вызываем тело
            result = body.evaluate();
            // Проверяем на наличие значений упрвляющих потоком вычислений
            if( result!=null && result instanceof ExecuteFlow ){
                ExecuteFlow.Target flowTarget = ((ExecuteFlow)result).getExecuteFlowTarget();

                // Если соответ. результату вызова функции
                if( flowTarget==ExecuteFlow.Target.Return ){
                    // То вычисляем результат
                    if( ((ExecuteFlow)result).isHasFlowResult() ){
                        result = ((ExecuteFlow)result).getFlowResult();
                    }
                }else if( flowTarget.compareTo(ExecuteFlow.Target.Return)<0 ){
                    // Если этот результат ниже уровя функции (на уровне цикла)
                    // То вычисляем результат, иначе оставляем как есть
                    if( ((ExecuteFlow)result).isHasFlowResult() ){
                        result = ((ExecuteFlow)result).getFlowResult();
                    }else{
                        result = null;
                    }
                }
            }
        }finally{
            // Восстанавливает переменные
            restoreVariables(scope);
            Variable.releaseVarScope(memory);
        }

        return result;
    }
    //</editor-fold>

    //TODO Mem Работа с памятью
    //<editor-fold defaultstate="collapsed" desc="createSpecialVars()">
    /**
     * Создает специальные переменные как: "arguments" (передаваемые аргументы), recursion ...
     * @param arguments Аргументы
     */
    protected void createSpecialVars(Object... arguments){
//        memory.put(argumentsVar, arguments);
        VariableDeffine.defineVariable(memory, argumentsVar, arguments);
//        memory.put(recursionVar, this);
        VariableDeffine.defineVariable(memory, recursionVar, this);
    }
    //</editor-fold>

    //TODO Mem Работа с памятью
    //<editor-fold defaultstate="collapsed" desc="createParametersVars()">
    /**
     * Создает переменные - аргументы передаваемые в функцию
     * @param arguments Аргументы
     */
    protected void createParametersVars(Object... arguments){
        int paramIdx = -1;
        for( String p : parameterNames ){
            paramIdx++;
            if( paramIdx<arguments.length && paramIdx>=0 ){
//                memory.put(p, arguments[paramIdx]);
                VariableDeffine.defineVariable(memory, p, arguments[paramIdx]);
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="store restore variables">
    /**
     * Сохранение переменных совпадающих с аргументами и резервными переменными
     * @param scope Область (Карта) куда сохранять
     */
    protected void storeVariables(Map scope){
        storeVariables(scope, parameterNames);
        storeVariables(scope, reservedVars);
    }

    /**
     * Восстановление ранее сохраненных переменных
     * @param scope Область (Карта) куда ранее были сохранены переменные
     */
    protected void restoreVariables(Map scope){
        restoreVariables(scope, reservedVars);
        restoreVariables(scope, parameterNames);
    }

    //TODO Mem Работа с памятью
    /**
     * Сохраняет указанные переменные
     * @param scope Область (Карта) куда сохранять
     * @param variableNames Имена переменных
     */
    protected void storeVariables(Map scope,String ... variableNames){
        for( String p : variableNames ){
            if( memory.containsKey(p) ){
                scope.put(p, memory.get(p));
            }
        }
    }

    //TODO Mem Работа с памятью
    /**
     * Восстановление указанные ранее сохраненные переменные
     * @param scope Область (Карта) куда ранее были сохранены переменные
     * @param variableNames Имена переменных
     */
    protected void restoreVariables(Map scope,String ... variableNames){
        for( String p : variableNames ){
            if( scope.containsKey(p) )memory.put(p, scope.get(p));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="evaluate()">
    /* (non-Javadoc) @see Callable */
    @Override
    public Object evaluate()
    {
        return this;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deepClone">
    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new Function(this, true);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="children">
    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{ body };
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if (tn== null) {
            throw new IllegalArgumentException("tn==null");
        }
        if( index<0 || index>0 )throw new IndexOutOfBoundsException();
        body = tn;
        body.setParent(this);
    }
    //</editor-fold>

    /**
     * Предикат Узел(Value) является функцией
     */
    public static final Predicate<Value> isFunctionPredicate = new Predicate<Value>() {
        @Override
        public boolean validate(Value value) {
            if( value instanceof Function )return true;
            return false;
        }
    };

    /**
     * Конвертор в функцию
     */
    public static final Convertor<Value,Function> toFunctionConvertor = new Convertor<Value, Function>() {
        @Override
        public Function convert(Value from) {
            if( from instanceof Function )return (Function)from;
            return null;
        }
    };

    /**
     * Возвращает функции
     * @param root Дерево
     * @return Функции
     */
    public static Iterable<Function> getFunctions(Value root){
        if (root== null) {
            throw new IllegalArgumentException("root==null");
        }
        Iterable<Value> vals = AbstractTreeNode.walk(root);
        vals = AbstractTreeNode.filter(vals, isFunctionPredicate);
        return AbstractTreeNode.convert(vals, toFunctionConvertor);
    }

    /**
     * Расстановка комментариев
     * @param v Код
     * @param comments Комментарии
     */
    public static void setComments(Value v,SortedSet<CommentReciver.Comment> comments){
        if (v== null) {
            throw new IllegalArgumentException("v==null");
        }
        if (comments== null) {
            throw new IllegalArgumentException("comments==null");
        }
        for( Function f : getFunctions(v) ){
            SourceLocation.Range srcRange = SourceLocation.Range.rangeOf(f);
            if( srcRange==null )continue;

            setComment(f, srcRange, comments);
        }
    }

    protected static void setComment(
            Function f,
            SourceLocation.Range fSrcRange,
            SortedSet<CommentReciver.Comment> comments)
    {
        SortedSet<CommentReciver.Comment> cmts = comments.headSet(fSrcRange.getBegin().createComment(""));
        int s = cmts.size();
        if( s>0 ){
            CommentReciver.Comment cmt = cmts.last();
            if( cmt!=null && cmt.getText()!=null ){
                f.setComment(cmt.getText());
            }
        }
    }
}
