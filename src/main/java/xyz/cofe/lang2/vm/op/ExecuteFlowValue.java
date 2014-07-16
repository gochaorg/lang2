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
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.op.ExecuteFlow.Target;
import xyz.cofe.lang2.vm.err.StackedError;
import xyz.cofe.lang2.vm.Value;

/**
 * Специальное значение определяющее направление потока вычислений
 * @author gocha
 */
public class ExecuteFlowValue extends AbstractTreeNode<Value> implements Value, ExecuteFlow {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ExecuteFlowValue.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор по умолчанию
     */
    public ExecuteFlowValue()
    {
        this.target = null;
        this.hasValue = false;
        this.value = null;
    }
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public ExecuteFlowValue(ExecuteFlowValue src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		target = src.target;
		hasValue = src.hasValue;
		value = src.value;
        if( value != null ) value.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public ExecuteFlowValue(ExecuteFlowValue src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		target = src.target;
		hasValue = src.hasValue;
		value = deep && src.value!=null ? src.value.deepClone() : src.value;
        if( value != null ) value.setParent(this);
	}
    
    /**
     * Конструктор
     * @param target Цель/Направление потока вычислений
     */
    public ExecuteFlowValue(ExecuteFlow.Target target){
        this.target = target;
        this.hasValue = false;
        this.value = null;
        if( value != null ) value.setParent(this);
    }

    /**
     * Конструктор
     * @param target Цель/Направление потока вычислений
     * @param value Возвращаемое значение (return/break/continue)
     */
    public ExecuteFlowValue(ExecuteFlow.Target target,Value value){
        this.target = target;
        this.value = value;
        this.hasValue = true;
        if( value != null ) value.setParent(this);
    }

    /**
     * Создает инструкцию RETURN
     * @param value Возвращаемое значение (return/break/continue)
     * @return Инструкция RETURN
     */
    public static ExecuteFlowValue createReturn(Value value){
        return new ExecuteFlowValue(Target.Return, value);
    }

    /**
     * Создает инструкцию RETURN
     * @return Инструкция RETURN
     */
    public static ExecuteFlowValue createReturn(){
        return new ExecuteFlowValue(Target.Return);
    }

    /**
     * Создает инструкцию BREAK
     * @return Инструкция BREAK
     */
    public static ExecuteFlowValue createBreak(){
        return new ExecuteFlowValue(Target.Break);
    }

    /**
     * Создает инструкцию BREAK
     * @param value Возвращаемое значение (return/break/continue)
     * @return Инструкция BREAK
     */
    public static ExecuteFlowValue createBreak(Value value){
        return new ExecuteFlowValue(Target.Break,value);
    }

    /**
     * Создает инструкцию BREAK
     * @param Возвращаемое значение (return/break/continue)
     * @return Инструкция BREAK
     */
    public static ExecuteFlowValue createContinue(Value value){
        return new ExecuteFlowValue(Target.Continue,value);
    }

    /**
     * Создает инструкцию CONTINUE
     * @return Инструкция CONTINUE
     */
    public static ExecuteFlowValue createContinue(){
        return new ExecuteFlowValue(Target.Continue);
    }

    /**
     * Создает инструкцию THROW
     * @param Возвращаемое значение (throw e...)
     * @return Инструкция THROW
     */
    public static ExecuteFlowValue createThrow(Value value){
        return new ExecuteFlowValue(Target.Throw,value);
    }
    
    /**
     * Создает инструкцию THROW
     * @param Возвращаемое значение (throw e...)
     * @return Инструкция THROW
     */
    public static ExecuteFlowValue createThrow(Value valueParent, Value child){
        StackedError errParent = new StackedError(valueParent.evaluate().toString());
        errParent.setErrorValue(valueParent);
        
        StackedError errChild = new StackedError(child.evaluate().toString());
        errChild.setErrorValue(child);
        errChild.setParentError(errParent);
        
        return new ExecuteFlowValue(Target.Throw,new Const(errChild));
    }
    
    protected boolean hasValue = false;
    protected Value value = null;
    protected Object evaluatedValue = null;
    
    protected ExecuteFlow.Target target = null;

    /**
     * Указывает что данная инструкция возвращает значение
     * @param hasValue true - возвращает значение; false - не возвращает
     */
    public void setHasFlowResult(boolean hasValue)
    {
        this.hasValue = hasValue;
    }

    /**
     * Указывает цель/направление потока вычислений
     * @param target Цель/Направление потока вычислений
     */
    public void setExecuteFlowTarget(Target target)
    {
        this.target = target;
    }

    /**
     * Указывает возвращаемое значение (return/break/continue)
     * @return Возвращаемое значение
     */
    public Value getValue()
    {
        return value;
    }

    /**
     * Указывает возвращаемое значение (return/break/continue)
     * @param value Возвращаемое значение
     */
    public void setValue(Value value)
    {
        this.value = value;
        if( value!=null )value.setParent(this);
    }
    
    protected void logEvaluateState(){
        StringBuilder mess = new StringBuilder();
        ArrayList<Object> params = new ArrayList<Object>();
        
        mess.append("target={0}");
        params.add(target);
        
        mess.append(" hasValue={1}");
        params.add(hasValue);
        
        if( hasValue ){
            mess.append(" value={2}");
            params.add(value);
            
            if( evalFlowResultAtEvaluate() ){
                mess.append(" evaluatedValue={3}");
                params.add(evaluatedValue);
            }
        }
        
        logFine(mess.toString(),params.toArray());
    }
    
    /**
     * Вычислять возвращаемое значение при вызове evaluate() 
     * и сохранять в evaluatedValue
     * @return true - вычислять
     */
    protected boolean evalFlowResultAtEvaluate(){ return true; }

    /* (non-Javadoc) @see ExecuteFlowValue */
    @Override
    public Object evaluate()
    {
        if( evalFlowResultAtEvaluate() ){
            evaluatedValue = null;
            if( hasValue && value!=null ){
                evaluatedValue = value.evaluate();
            }
        }
        logEvaluateState();
        return this;
    }

    /**
     * Указывает цель/направление потока вычислений
     * @return Цель/Направление потока вычислений
     */
    @Override
    public Target getExecuteFlowTarget()
    {
        return target;
    }

    /**
     * Указывает что данная инструкция возвращает значение
     * @return true - возвращает значение; false - не возвращает
     */
    @Override
    public boolean isHasFlowResult()
    {
        return hasValue;
    }

    /* (non-Javadoc) @see ExecuteFlowValue */
    @Override
    public Object getFlowResult()
    {
        if( evalFlowResultAtEvaluate() ){
            return evaluatedValue;
        }else{
            Value v = getValue();
            if( v!=null )return v.evaluate();
            return null;
        }
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new ExecuteFlowValue(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{ value };
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        if( index!=0 )throw new IndexOutOfBoundsException();
        value = tn;
        value.setParent(this);
    }
}
