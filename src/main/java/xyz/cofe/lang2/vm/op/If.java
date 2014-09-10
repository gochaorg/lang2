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
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.Value;

/**
 * Условная конструкция IF.<br/>
 * Пример: <br/>
 * <code>
 * <b>if (</b> <i>Условие</i> <b>)</b> <i>Если верное условие</i> <br/>
 * <b>if (</b> 1 > 2 <b>)</b> "yes"
 * </code><br/>
 * В качестве условия могут выступать булево значения
 * @author gocha
 */
public class If extends AbstractTreeNode<Value> implements Value
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(If.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(If.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(If.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(If.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(If.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(If.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Value condition = null;
	protected Value trueExpression = null;

    /**
     * Конструктор по умолчанию
     */
	public If(){
		this(null,null);
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public If(If src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		condition = src.condition;
		trueExpression = src.trueExpression;

        if( this.condition!=null )this.condition.setParent(this);
        if( this.trueExpression!=null )this.trueExpression.setParent(this);
    }

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public If(If src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		condition = deep && src.condition!=null ? src.condition.deepClone() : src.condition;
		trueExpression = deep && src.trueExpression!=null ? src.trueExpression.deepClone() : src.trueExpression;
        
        if( this.condition!=null )this.condition.setParent(this);
        if( this.trueExpression!=null )this.trueExpression.setParent(this);
    }

    /**
     * Конструктор
     * @param condition Условие
     * @param trueExpr Выражение если условие верно
     */
	public If(Value condition,Value trueExpr){
		this.condition = condition;
		this.trueExpression = trueExpr;
        
        if( this.condition!=null )this.condition.setParent(this);
        if( this.trueExpression!=null )this.trueExpression.setParent(this);
	}

    /**
     * Указывает условие
     * @return Условие
     */
	public Value getCondition() {
		return condition;
	}

    /**
     * Указывает условие
     * @param condition Условие
     */
	public void setCondition(Value condition) {
		this.condition = condition;
        if( this.condition!=null )this.condition.setParent(this);
	}

    /**
     * Указывает выражение если условие верное
     * @return Выражение TRUE
     */
	public Value getTrueExpression() {
		return trueExpression;
	}

    /**
     * Указывает выражение если условие верное
     * @param trueExpression Выражение TRUE
     */
	public void setTrueExpression(Value trueExpression) {
		this.trueExpression = trueExpression;
        if( this.trueExpression!=null )this.trueExpression.setParent(this);
	}

    /**
     * Вычисление условия
     * @param condition условие
     * @return true - условие истинно / false - ложно
     */
    protected boolean evalConditionValue( Value condition ){
		Object c = condition.evaluate();
		if( c==null ){
            Logger.getLogger(IfElse.class.getName()).severe("condition.evaluate()==null");
            throw new NullRefError(this, "condition is null");
		}
//        if( c==null )c=(Boolean)false;
		if( !(c instanceof Boolean) ){
            Logger.getLogger(IfElse.class.getName()).severe("!(condition.evaluate() instanceof Boolean)");
            throw new CastError(this, "can't cast to boolean from condition ("+c.getClass().getName()+")");
		}
        
        return (Boolean)c;
    }
    
    /**
     * Вычисляет значение условия, и если оно верное/не верное, то возвращает соответ. значение выражение
     * @return Значение
     */
	@Override
	public Object evaluate() {
		Object r = null;
		if( condition==null ){
            Logger.getLogger(If.class.getName()).severe("condition==null");
			throw new CompileException(this,"condition==null");
		}
        if( trueExpression==null ){
            Logger.getLogger(If.class.getName()).severe("trueExpression==null");
            throw new CompileException(this,"trueExpression==null");
        }
		if( evalConditionValue(condition) ){
			r = trueExpression.evaluate();
		}
		return r;
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new If(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{condition,trueExpression};
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index<0 || index>=2 )throw new IndexOutOfBoundsException();
        if( index==0 && tn!=null ){
            condition = tn;
            condition.setParent(this);
            return;
        }
        if( index==1 && tn!=null ){
            trueExpression = tn;
            trueExpression.setParent(this);
            return;
        }
        throw new IllegalArgumentException("index="+index+" tn="+tn);
    }
}
