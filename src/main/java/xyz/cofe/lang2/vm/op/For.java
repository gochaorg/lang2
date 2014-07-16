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

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.Factory;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.NewVariables;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.err.RuntimeError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.VariableScopeListener;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;

/**
 * Цикл - итерация по коллекциям.<br/>
 * <code>for ( <i>Имя перменной</i> in <i>источник данных</i> ) <i>тело цикла</i> </code>. <br/>
 * Источником данных может являтся:
 * <ul>
 *  <li> Список </li>
 * </ul>
 * @author gocha
 */
public class For extends AbstractTreeNode<Value>
implements Value, NewVariables, VariableScopeListener, MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(For.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(For.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(For.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(For.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(For.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(For.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Память
     */
    protected Map<String, Object> memory = null;
    /**
     * Имя определяемой переменной
     */
    protected String variable = null;
    /**
     * Источник данных
     */
    protected Value src = null;

    /**
     * Тело цикла
     */
    protected Value body = null;
    
    protected Factory factory = null;

    public For(Factory factory){
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
    }

    /**
     * Конструктор
     * @param factory Фабрика классов
     * @param memory Память
     * @param variable Переменная
     * @param src Источник данных
     * @param body Тело цикла
     */
    public For(Factory factory, Map<String, Object> memory, String variable,Value src,Value body){
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
        this.memory = memory;
        this.variable = variable;
        this.src = src;
        this.body = body;
        if( this.body!=null )this.body.setParent(this);
        if( this.src!=null )this.src.setParent(this);
    }

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deepCopy Полная копия
     */
    public For(For src,boolean deepCopy){
        if (src== null) {
            throw new IllegalArgumentException("src==null");
        }
        this.memory = src.memory;
        this.variable = src.variable;
        this.body = deepCopy ? (src.body!=null ? src.body.deepClone() : null) : src.body;
        this.src = deepCopy ? (src.src!=null ? src.src.deepClone() : null ) : src.src;
        if( this.body!=null )this.body.setParent(this);
        if( this.src!=null )this.src.setParent(this);
        this.extender = src!=null ? src.extender : null;
    }

    /**
     * Указывает тело цикла
     * @return Тело цикла
     */
    public Value getBody() {
        return body;
    }

    /**
     * Указывает тело цикла
     * @param body Тело цикла
     */
    public void setBody(Value body) {
        this.body = body;
        if( this.body!=null )this.body.setParent(this);
    }

    /**
     * Указывает память
     * @return Память
     */
    public Map<String, Object> getMemory() {
        return memory;
    }

    /**
     * Указывает память
     * @param memory Память
     */
    public void setMemory(Map<String, Object> memory) {
        this.memory = memory;
    }

    /**
     * Указывает источник данных
     * @return источник данных
     */
    public Value getSrc() {
        return src;
    }

    /**
     * Указывает источник данных
     * @param src источник данных
     */
    public void setSrc(Value src) {
        this.src = src;
        if( this.src!=null )this.src.setParent(this);
    }

    /**
     * Указывает определяемую переменную
     * @return Имя переменной
     */
    public String getVariable() {
        return variable;
    }

    /**
     * Указывает определяемую переменную
     * @param variable Имя переменной
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    private Value[] children = new Value[]{ null, null };

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        children[0] = src;
        children[1] = body;
        return children;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index<0 || index>=2 )throw new IndexOutOfBoundsException();
        if( index==0 )src = tn;
        if( index==1 )body = tn;
    }

    /**
     * Интерфейс расширения объекта - поддержка итераторов
     */
    public interface Extender {
        /**
         * Расширение объекта - итератор
         * @param invoker Кто вызывает
         * @param extendedObject Объект который расширяется
         * @return Пара Итератор, true - успешно
         */
        Pair<Iterator,Boolean> extendFor(Value invoker,Object extendedObject );
    }

    protected Extender extender = null;

    /**
     * Указывает расширение
     * @return Расширение или null
     */
    public Extender getExtender() {
        return extender;
    }

    /**
     * Указывает расширение
     * @param extender Расширение или null
     */
    public void setExtender(Extender extender) {
        this.extender = extender;
    }

    //TODO Mem Работа с памятью
    /* (non-Javadoc) @see Value */
    @Override
    public Object evaluate() {
		if( body==null ){
            Error err = new CompileException(this, "body==null");
            Logger.getLogger(For.class.getName()).severe(err.getMessage());
            throw err;
		}
		if( src==null ){
            Error err = new CompileException(this, "src==null");
            Logger.getLogger(For.class.getName()).severe(err.getMessage());
            throw err;
		}
		if( memory==null ){
            Error err = new CompileException(this, "memory==null");
            Logger.getLogger(For.class.getName()).severe(err.getMessage());
            throw err;
		}
		if( variable==null ){
            Error err = new CompileException(this, "variable==null");
            Logger.getLogger(For.class.getName()).severe(err.getMessage());
            throw err;
		}

        Object srcData = src.evaluate();
        if( srcData==null ){
            throw new NullRefError(this, "source is null");
        }

        if( extender!=null ){
            Pair<Iterator,Boolean> res = extender.extendFor(this, srcData);
            if( res.B() ){
                return evaluateIterator(res.A());
            }
        }

        if( srcData instanceof Map ){
            Map m = (Map)srcData;
            Pair<Object,Boolean> res = evaluateMap(m);
            if( res.B() )return res.A();
        }

        if( srcData instanceof Iterable ){
            return evaluateIterable((Iterable)srcData);
        }

        if( srcData instanceof Iterator ){
            return evaluateIterator((Iterator)srcData);
        }

        Error err = new RuntimeError(this, "source ("+srcData+") not supported");
        Logger.getLogger(For.class.getName()).severe(err.getMessage());
        throw err;
    }

    protected static final Pair<Object,Boolean> faild = new BasicPair<Object, Boolean>(null,false);
    protected static Pair<Object,Boolean> success(Object res){
        return new BasicPair<Object, Boolean>(res, true);
    }

    protected Pair<Object,Boolean> evaluateMap(Map m){
        Pair<Object,Boolean> res = evaluateMapIterator(m);
        if( res.B() )return res;
        res = evaluateMapItrObject(m);
        return res;
    }

    protected Pair<Object,Boolean> evaluateMapIterator(Map m){
        if( m.containsKey("iterator") ){
            Object itr = m.get("iterator");
            if( itr instanceof Iterable ){
                return success( evaluateIterable((Iterable)itr) );
            }else if(itr instanceof Iterator ){
                return success( evaluateIterator((Iterator)itr) );
            }else if( itr instanceof Function ){
                Function f = (Function)itr;
                return evaluateIteratorFun(f);
            }
        }
        return faild;
    }

    protected Pair<Object,Boolean> evaluateMapItrObject(Map m){
        if( m.containsKey("next") && m.containsKey("hasNext") ){
            Function _next = Method.getMethod(m, "next",factory);
            Function _hasNext = Method.getMethod(m, "hasNext",factory);

            if( _next !=null  && _hasNext !=null ){
                Function next = (Function)_next;
                Function hasNext = (Function)_hasNext;

//                if( next.getParameterNames().length==0 && hasNext.getParameterNames().length==0 ){
//                    return success(evaluateIterator(createIterator(hasNext, next)));
//                }
                return success(evaluateIterator(createIterator(hasNext, next)));
            }
        }
        return faild;
    }

    protected Iterator createIterator(final Function hasNext,final Function next){
        return new Iterator() {
            @Override
            public boolean hasNext() {
                Object hn = hasNext.call();
                if( hn instanceof Boolean ){
                    return (Boolean)hn;
                }
                throw new RuntimeError(For.this, "iterator.hasNext() return not boolean");
            }

            @Override
            public Object next() {
                Object r = next.call();
                return r;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    protected Pair<Object,Boolean> evaluateIteratorFun(Function f){
        if( f.getParameterNames().length==0 ){
            Object r = f.call();
            if( r==null ){
                Error err = new NullRefError(this, "source ("+src+") return null iterator");
                Logger.getLogger(For.class.getName()).severe(err.getMessage());
                throw err;
            }
            if( r instanceof Iterable )return success(evaluateIterable((Iterable)r));
            if( r instanceof Iterator )return success(evaluateIterator((Iterator)r));
            if( r instanceof Map ){
                return evaluateMapItrObject((Map)r);
            }
        }
        return faild;
    }

    protected Object evaluateIterable(Iterable iterable){
        Iterator itr = iterable.iterator();
        if( itr==null ){
            Error err = new NullRefError(this, "source ("+src+") return null iterator");
            Logger.getLogger(For.class.getName()).severe(err.getMessage());
            throw err;
        }
        return evaluateIterator(itr);
    }

    //TODO Mem Работа с памятью
    protected Object evaluateIterator(Iterator itr){
        Object r = null;

        while( itr.hasNext() ){
            Object val = itr.next();
//            memory.put(variable, val);
            VariableDeffine.defineVariable(memory, variable, val);

            r = body.evaluate();
            if( r instanceof ExecuteFlow ){
                ExecuteFlow.Target flowTarget = ((ExecuteFlow)r).getExecuteFlowTarget();
                if( flowTarget==ExecuteFlow.Target.Break ){
                    if( ((ExecuteFlow)r).isHasFlowResult() ){
                        r = ((ExecuteFlow)r).getFlowResult();
                    }else{
                        r = null;
                    }
                    break;
                }else if( flowTarget==ExecuteFlow.Target.Continue ) {
                    if( ((ExecuteFlow)r).isHasFlowResult() ){
                        r = ((ExecuteFlow)r).getFlowResult();
                    }else{
                        r = null;
                    }
                }else if( flowTarget.compareTo(ExecuteFlow.Target.Continue)>0 ) {
                    break;
                }
            }
        }

        return r;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new For(this,true);
    }

    /* (non-Javadoc) @see NewVariables */
    @Override
    public String[] getNewVaraibleNames() {
        return new String[]{ variable };
    }

    //TODO Mem Работа с памятью
	/*
	 * (non-Javadoc)
	 * @see lang2.vm.ScopeVariablesListener
	 */
    @Override
    public void scopeEnter(Map scope) {
		// Если есть уже переменная с указанным именем, то сохраняем ее
		if( memory!=null && variable!=null && memory.containsKey(variable) && scope!=null ){
			scope.put(variable, memory.get(variable));
		}
    }

    //TODO Mem Работа с памятью
	/*
	 * (non-Javadoc)
	 * @see lang2.vm.ScopeVariablesListener
	 */
    @Override
    public void scopeExit(Map scope) {
		// Восстанавливаем переопределенную переменную
		if( scope==null )return;
		if( memory==null || variable==null )return;
		if( !scope.containsKey(variable) )return;
		Object v = scope.get(variable);
		memory.put(variable, v);
    }
}
