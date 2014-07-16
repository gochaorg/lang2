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
import xyz.cofe.lang2.vm.Value;

/**
 * Делегируемое значение, например: <code> ( <i>выражение</i>  ) </code>
 * @author Камнев Георгий Павлович
 */
public class Delegate extends AbstractTreeNode<Value> implements Value
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Delegate.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Delegate.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Delegate.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Delegate.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Delegate.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Delegate.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Value srcValue = null;

    /**
     * Конструктор по умолчанию
     */
	public Delegate(){
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Delegate(Delegate src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		srcValue = src.srcValue;
        if( this.srcValue!=null )this.srcValue.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public Delegate(Delegate src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		srcValue = deep && src.srcValue!=null ? src.srcValue.deepClone() : src.srcValue;
        if( this.srcValue!=null )this.srcValue.setParent(this);
	}
    
    /**
     * Конструктор
     * @param src Делегируемое значение
     */
	public Delegate(Value src)
	{
		if (src == null)
			throw new IllegalArgumentException("src == null");
		
		this.srcValue = src;
        if( this.srcValue!=null )this.srcValue.setParent(this);
	}

    /* (non-Javadoc) @see Value */
	@Override
	public Object evaluate()
	{
//        if( Thread.currentThread().isInterrupted() )return null;
		return srcValue.evaluate();
	}

	/**
     * Указывает делегируемое значение
	 * @return the Делегируемое значение
	 */
	public Value getSourceValue()
	{
		return srcValue;
	}

	/**
     * Указывает делегируемое значение
	 * @param srcValue Делегируемое значение
	 */
	public void setSourceValue(Value srcValue)
	{
		if (srcValue == null)
			throw new IllegalArgumentException("srcValue == null");
		
		this.srcValue = srcValue;
        if( this.srcValue!=null )this.srcValue.setParent(this);
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new Delegate(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{ srcValue };
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index!=0 )throw new IndexOutOfBoundsException();
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        srcValue = tn;
        srcValue.setParent(this);
    }
}
