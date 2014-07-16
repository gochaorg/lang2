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

import xyz.cofe.lang2.vm.TypeSupport;
import xyz.cofe.lang2.vm.SourceRange;
import xyz.cofe.lang2.vm.ValuePath;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.common.Wrapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.Token;
import xyz.cofe.collection.Convertor;
import xyz.cofe.text.Template;
import xyz.cofe.text.TemplateParser;

/**
 * Обвертка над значением для отладки
 * @author gocha
 */
public class DebugWrapperValue
        extends AbstractTreeNode<Value>
        implements Wrapper<Value>, Value, SourceRange, TypeSupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DebugWrapperValue.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор
     * @param wrappedValue обвертунное значение
     */
    public DebugWrapperValue(Value wrappedValue) {
        if (wrappedValue== null) {
            throw new IllegalArgumentException("wrappedValue==null");
        }
        this.wrappedValue = wrappedValue;
        if( this.wrappedValue!=null )this.wrappedValue.setParent(this);
    }
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public DebugWrapperValue(DebugWrapperValue src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		this.wrappedValue = src.wrappedValue;
		this.start = src.start;
		this.stop = src.stop;
        if( this.wrappedValue!=null )this.wrappedValue.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public DebugWrapperValue(DebugWrapperValue src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		this.wrappedValue = deep && src.wrappedValue!=null ? src.wrappedValue.deepClone() : src.wrappedValue;
		this.start = src.start;
		this.stop = src.stop;
        if( this.wrappedValue!=null )this.wrappedValue.setParent(this);
	}

    /* (non-Javadoc) @see lang2.vm.TypeSupport */
    @Override
    public Type getType() {
        if( wrappedValue==null )return PrimitiveType.Undefined;
        if( !(wrappedValue instanceof TypeSupport) )return PrimitiveType.Undefined;
        return ((TypeSupport)wrappedValue).getType();
    }

    /**
     * обвертунное значение
     */
    protected Value wrappedValue = null;

    /* (non-Javadoc) @see Wrapper */
    @Override
    public Value unwrap() {
        return wrappedValue;
    }
    
//    protected Template enterLogTemplate = null;
////
//    public Template getEnterLogTemplate() {
//        return enterLogTemplate;
//    }
//
//    public void setEnterLogTemplate(Template enterLogTemplate) {
//        this.enterLogTemplate = enterLogTemplate;
//    }
//    
//    protected Convertor<String,String> vars = null;
//
//    public Convertor<String, String> getVars() {
//        if( vars!=null )return vars;
//        vars = new Convertor<String, String>() {
//            @Override
//            public String convert(String from) {
//                if( from=="?" )
//            }
//        };
//        return vars;
//    }
    
    public static enum LogVar{
        Class,
        StartLine,
        StopLine,
        StartCharPosInLine,
        StopCharPosInLine,
        ValuePath
        
        ;
        public Object getValue(DebugWrapperValue debugValue,Object def){
            switch(this){
                case Class:
                    if( debugValue.wrappedValue==null )return def;
                    return debugValue.wrappedValue.getClass();
                case StartLine:
                    if( debugValue.start!=null )
                        return debugValue.start.getLine();
                case StopLine:
                    if( debugValue.stop!=null )
                        return debugValue.stop.getLine();
                case StartCharPosInLine:
                    if( debugValue.start!=null )
                        return debugValue.start.getCharPositionInLine();
                case StopCharPosInLine:
                    if( debugValue.stop!=null )
                        return debugValue.stop.getCharPositionInLine();
                case ValuePath:
                    return new ValuePath(debugValue);
            }
            return def;
        }
    }
    
    protected void logEnter(){
        if( start!=null && stop!=null ){
            int startLine = start.getLine();
            int startChar = start.getCharPositionInLine();
            String opClass = wrappedValue!=null ? wrappedValue.getClass().toString() : "null";
            int stopLine = stop.getLine();
            int stopChar = stop.getCharPositionInLine();
            logFiner( 
                    "evaluate class={0} start.line={1} start.charInLine={2} stop.line={3} stop.charInLine={4}", 
                    opClass, startLine, startChar, stopLine, stopChar
                    );
        }
    }
    
    protected void logExit(Object result){
        if( start!=null && stop!=null ){
            int startLine = start.getLine();
            int startChar = start.getCharPositionInLine();
            String opClass = wrappedValue!=null ? wrappedValue.getClass().toString() : "null";
            int stopLine = stop.getLine();
            int stopChar = stop.getCharPositionInLine();
            logFiner( 
                    "evaluate.result={5} class={0} start.line={1} start.charInLine={2} stop.line={3} stop.charInLine={4}", 
                    opClass, startLine, startChar, stopLine, stopChar, result==null ? "null" : result
                    );
        }
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Object evaluate() {
        if( Thread.currentThread().isInterrupted() ){
            logFine("stop execution");
            return null;
        }
        logEnter();
        Object o = wrappedValue.evaluate();
        logExit(o);
        return o;
    }

    /**
     * Указывает начало правила
     */
    protected Token start = null;

    /* (non-Javadoc) @see SourceLocation */
    @Override
    public Token getStart() {
        return start;
    }

    /* (non-Javadoc) @see SourceLocation */
    @Override
    public void setStart(Token start) {
        this.start = start;
    }

    /**
     * Указывает конец правила
     */
    protected Token stop = null;

    /* (non-Javadoc) @see SourceLocation */
    @Override
    public Token getStop() {
        return stop;
    }

    /* (non-Javadoc) @see SourceLocation */
    @Override
    public void setStop(Token stop) {
        this.stop = stop;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new DebugWrapperValue(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{ wrappedValue };
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index!=0 )throw new IndexOutOfBoundsException();
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        wrappedValue = tn;
        wrappedValue.setParent(this);
    }
    
    @Override
    public String toString(){
        if( wrappedValue!=null )return wrappedValue.toString();
        return super.toString();
    }
}
