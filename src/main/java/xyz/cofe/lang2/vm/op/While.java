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

import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.Value;

/**
 * Цикл while
 * @author gocha
 */
public class While extends AbstractTreeNode<Value> implements Value
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(While.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(While.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(While.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(While.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(While.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(While.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор
     */
	public While(){
	}
	
    /**
     * Конструктор копирования
     * @param src 
     */
	public While(While src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		body = src.body;
		condition = src.condition;
        if( body!=null )body.setParent(this);
        if( condition!=null )condition.setParent(this);
	}

    /**
     * Конструктор
     * @param condition Условие повторения цикла
     * @param body Тело цикла
     */
	public While(Value condition,Value body){
		this.condition = condition;
		this.body = body;
        if( body!=null )body.setParent(this);
        if( condition!=null )condition.setParent(this);
	}
	
	protected Value body = null;
	protected Value condition = null;

    /**
     * Указывает тело цикла
     * @return Тело
     */
	public Value getBody() {
		return body;
	}

    /**
     * Указывает тело цикла
     * @param body Тело
     */
	public void setBody(Value body) {
		this.body = body;
        if( body!=null )body.setParent(this);
	}

    /**
     * Условие цикла
     * @return условие
     */
	public Value getCondition() {
		return condition;
	}

    /**
     * Условие цикла
     * @param condition условие
     */
	public void setCondition(Value condition) {
		this.condition = condition;
        if( condition!=null )condition.setParent(this);
	}

    /* (non-Javadoc) @see Value */
	@Override
	public Object evaluate() {
		Object r = null;
		if( body==null ){
            Logger.getLogger(While.class.getName()).severe("body is null (vm error)");
            throw new CompileException(this, "body is null (vm error)");
		}
		if( condition==null ){
            Logger.getLogger(While.class.getName()).severe("condition is null (vm error)");
            throw new CompileException(this, "condition==null");
		}
		while(true){
			Object c = condition.evaluate();
            if( c==null )c=(Boolean)false;
			if( !(c instanceof Boolean) ){
                Logger.getLogger(While.class.getName()).info("condition is not boolean:"+c.getClass().getName());
                throw new CastError(this,"can't convert to boolean from "+c);
			}
			boolean _c = ((Boolean)c);
			if( !_c )break;
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
        While w = new While(this);
        if( body!=null )w.setBody(body.deepClone());
        if( condition!=null )w.setCondition(condition.deepClone());
        return w;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{condition,body};
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index<0 || index>=2 )throw new IndexOutOfBoundsException();
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        if( index==0 )condition = tn;
        if( index==1 )body = tn;
        tn.setParent(this);
    }
}
