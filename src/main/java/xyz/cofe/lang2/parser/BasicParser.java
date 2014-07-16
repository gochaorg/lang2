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
package xyz.cofe.lang2.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.ext.AbstractExtender;
import xyz.cofe.lang2.vm.ext.BasicExtender;
import xyz.cofe.lang2.vm.ext.JREArrayExtender;
import xyz.cofe.lang2.vm.ext.NumberExtender;
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.ext.StringExtender;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.ext.JavaTypeExtender;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.AbstractTreeNode;
import xyz.cofe.lang2.vm.op.ArrayIndex;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.For;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.lang2.vm.op.Operator;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;
import xyz.cofe.collection.iterators.TreeWalk;


/**
 * Базовый парсер
 * @author gocha
 */
public class BasicParser extends lang2Parser 
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(BasicParser.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(BasicParser.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(BasicParser.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(BasicParser.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(BasicParser.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(BasicParser.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
	 * Конструктор
	 * @param input входные данные
	 */
    public BasicParser(TokenStream input,ParserOptions opts) 
    {
        this(input, new RecognizerSharedState(),opts);
    }

    /**
     * Конструктор
     * @param source входные данные
     */
    public BasicParser(String source,ParserOptions opts){
    	this( new CommonTokenStream(new lang2Lexer(new ANTLRStringStream(source))), new RecognizerSharedState(),opts);
    }
    
    /**
     * Конструктор 
     * @param input входные данные
     * @param state востановление после ошибок
     */
    public BasicParser(TokenStream input, RecognizerSharedState state,ParserOptions opts) 
    {
        super(input, state);
        parserOptions = opts;
    }
    
    /**
     * Опции парсера
     */
    protected ParserOptions parserOptions = null;

    /**
     * Указывает опции парсера
     * @return Опции парсера
     */
    public ParserOptions getParserOptions() {
        return parserOptions;
    }

    private OptL2Factory optl2factory = null;

    @Override
    public Factory factory() {
        if( optl2factory!=null )return optl2factory;
        if( optl2factory==null ){
//            Factory f = super.factory();
////            if( parserOptions!=null ){
////                if( parserOptions.isPythonLikeMethod() ){
////                    f = new PythFactory(memory);
////                }
////            }
//            optl2factory = new OptL2Factory(f, getParserOptions());
//            logFine("factory() created OptL2Factory");

            optl2factory = new OptL2Factory(memory(), getParserOptions());
            logFine("factory() created OptL2Factory");
        }
        return optl2factory;
    }
    
    /**
     * Перехваченные сообщения о ошибках
     */
    protected List<RecognitionException> catchedException
            = new ArrayList<RecognitionException>();
    
    /**
     * Перехватывать сообщения о ошибках
     */
    protected boolean catchExceptions = false;
    
    /**
     * Сообщать о пустом исходнике
     */
    protected boolean reportEmptySourceError = false;
    
    /**
     * Парсинг исходного кода
     * @return выражение 
     * @throws RecognitionException Ошибка в исходнике
     */
    public Value parse() throws RecognitionException{
        try{
            // Начинаем перехват сообщений о ошибках
            catchExceptions = true;
            catchedException.clear();
            
            Value v = expressions();
            if( v!=null )setExtender(v);

            if( checkEmptyInstuctions(v) ){
                if( reportEmptySourceError ){
                    for( RecognitionException e : catchedException ){
                        super.reportError(e);
                    }
                }
                catchedException.clear();
            }else{
                for( RecognitionException e : catchedException ){
                    super.reportError(e);
                }
                catchedException.clear();
            }
            
            return v;
        }
        finally{
            catchExceptions = false;
            catchedException.clear();
        }
    }
    
    /**
     * Проверяет пустая ли эта конструкция
     * @param v Конструкция / код
     * @return true - да, ничего не выполняет.
     */
    public static boolean isEmpty(Value v){
        ExpressionList el = null;
        
        if( v instanceof ExpressionList ){
            el = (ExpressionList)v;
        }else if( v instanceof DebugWrapperValue ){
            Value unwrap = ((DebugWrapperValue)v).unwrap();
            if( unwrap instanceof ExpressionList ){
                el = (ExpressionList)unwrap;
            }
        }

        if( el!=null ){
            if( el.getValues().length==0 ){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Анализ перехвата сообщений о ошибке - 
     * случай:
     * когда был подан исходник не содержащий инструкций для выполнения
     * Должно быть: 
     * 1. 1 одно сообщение - NoViableAltException
     * 2. v = ExpressionList не содержащий инструкций
     * @param v Анализируемый код
     * @return true - этот случай, false - другой какой то случай
     */
    private boolean checkEmptyInstuctions(Value v){
        if( catchedException.size()==1 
                && v!=null 
                && catchedException.get(0) instanceof NoViableAltException
                ){
            if( isEmpty(v) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Устанавливает расширение во всем дереве операций
     * @param root Дерево
     */
    protected void setExtender(Value root){
        if (root== null) {
            throw new IllegalArgumentException("root==null");
        }
        for( TreeWalk<Value> tw : AbstractTreeNode.tree(root) ){
            Value v = tw.currentNode();
            if( v instanceof ArrayIndex ){
                ((ArrayIndex)v).setExtender(arrayExtender);
            }
            if( v instanceof For ){
                ((For)v).setExtender(forExtender);
            }
            if( v instanceof Operator ){
                ((Operator)v).setExtender(operatorExtender);
            }
        }
    }
    
    /**
     * Перехват сообщений о ошибках
     * @param e Сообщение о ошибке
     */
    @Override
    public void reportError(RecognitionException e){
        if( catchExceptions ){
            catchedException.add(e);
        }else{
            super.reportError(e);
        }
    }
    
    /**
     * Расширение объекта String
     */
    protected StringExtender stringExtender = null;
    public StringExtender getStringExtender(){
        if( stringExtender==null )stringExtender = new StringExtender();
        return stringExtender;
    }

    /**
     * Расширение объекта Number
     */
    protected NumberExtender numberExtender = null;
    public NumberExtender getNumberExtender(){
        if( numberExtender==null )numberExtender = new NumberExtender();
        return numberExtender;
    }

    private JavaTypeExtender javaTypeExtender = null;
    /**
     * Расширение Java типов
     */
    public JavaTypeExtender getJavaTypeExtender(){
        if( javaTypeExtender==null )javaTypeExtender = new JavaTypeExtender();
        return javaTypeExtender;
    }

    private JREArrayExtender jreArrayExtender = null;
    /**
     * Расширение Java массивов
     */
    public JREArrayExtender getJREArrayExtender(){
        if( jreArrayExtender==null )jreArrayExtender = new JREArrayExtender();
        return jreArrayExtender;
    }

    private AbstractExtender[] extenders = null;
    
    /**
     * Расширения
     */
    protected AbstractExtender[] getExtenders(){
        if( extenders!=null )return extenders;
        extenders = new AbstractExtender[]{
            getStringExtender(), 
            getNumberExtender(), 
            getJREArrayExtender(),
            getJavaTypeExtender()
        };
        StringBuilder l = new StringBuilder();
        l.append("getExtenders() count="+extenders.length);
        for(int i=0;i<extenders.length;i++){
            l.append("\n");
            l.append(i);
            l.append(". extender class=");
            l.append(extenders[i].getClass());
            l.append(" toString=");
            l.append(extenders[i].toString());
        }
        logFine(l.toString());
        return extenders;
    }
    
    /**
     * Расширение ArrayIndex
     */
    protected ArrayIndex.Extender arrayExtender = new ArrayIndex.Extender() {
        @Override
        public Pair<Object, Boolean> extendArrayIndex(Value invoker, Object base, Object index) {
            Pair<Object,Boolean> res = AbstractExtender.faild;
            for( AbstractExtender ext : getExtenders() ){
                res = ext.extendArrayIndex(invoker, base, index);
                if( res.B() )return res;
            }
            return res;
        }
    };

    /**
     * Расширение For
     */
    protected For.Extender forExtender = new For.Extender() {
        @Override
        public Pair<Iterator, Boolean> extendFor(Value invoker, Object extendedObject) {
            Pair<Iterator, Boolean> res = AbstractExtender.faildForExtend;
            for( AbstractExtender ext : getExtenders() ){
                res = ext.extendFor(invoker, extendedObject);
                if( res.B() )return res;
            }
            return res;
        }
    };
    
    /**
     * Расширение оператора
     */
    protected Operator.Extender operatorExtender = new OperatorExtender();

    public class OperatorExtender
            extends BasicExtender
            implements Operator.TypeExtender
    {
        @Override
        public Pair<Integer, Boolean> extendCompare(Value invoker, Object arg1, Object arg2) {
            Pair<Integer, Boolean> res = AbstractExtender.faildCompare;
            for( AbstractExtender ext : getExtenders() ){
                res = ext.extendCompare(invoker, arg1, arg2);
                if( res.B() )return res;
            }
            return res;
        }

        @Override
        public Pair<Object, Boolean> extendOperator(Value invoker, OperatorName opName, Object... args) {
            Pair<Object, Boolean> res = AbstractExtender.faild;
            for( AbstractExtender ext : getExtenders() ){
                res = ext.extendOperator(invoker, opName, args);
                if( res.B() )return res;
            }
            return res;
        }

        private final BasicPair<Type,Boolean> noExtend = new BasicPair<Type, Boolean>(
                PrimitiveType.Undefined, false);

        @Override
        public Pair<Type, Boolean> extendOperatorType(Value invoker, OperatorName opName, Type... argTypes) {
            for( AbstractExtender ext : getExtenders() ){
                if( ext instanceof Operator.TypeExtender ){
                    Pair<Type,Boolean> r = ((Operator.TypeExtender)ext).extendOperatorType(invoker, opName, argTypes);
                    if( r.B() )return r;
                }
            }
            return noExtend;
        }
    }
}
