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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.TypeSupport;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.collection.Iterators;

/**
 * Список выражений разделенных <b>;</b><br/>
 * Синтаксис: <code> <i>Инструкция</i> { ';' <i>Инструкция</i> } </code>
 * Возвращает результат последней инструкции
 @author gocha
 */
public class ExpressionList
        extends AbstractTreeNode<Value>
        implements Value, TypeSupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ExpressionList.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private void assignParent(){
        if( values!=null )
            for( Value v : values )
                if( v != null )
                    v.setParent(this);
    }
    
    /**
     * Конструктор
     */
    public ExpressionList()
    {
        values = new Value[]{};
    }
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public ExpressionList(ExpressionList src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		if( src.values!=null )values = Arrays.copyOf(src.values, src.values.length);
        assignParent();
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public ExpressionList(ExpressionList src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		if( src.values!=null )values = Arrays.copyOf(src.values, src.values.length);
        if( deep && values!=null ){
            for( int i=0;i<values.length;i++ ){
                if( values[i]!=null )values[i] = values[i].deepClone();
            }
        }
        assignParent();
	}

	/**
	 * Конструктор
	 * @param values инструкции для выполнения
	 */
	public ExpressionList(Iterable<Value> values){
        if( values==null ){
            this.values = new Value[]{};
        }else{
            this.values = xyz.cofe.types.TypesUtil.Iterators.<Value>toArray(Iterators.<Value>notNullFilter(values), new Value[]{});
//            this.values = Iterators.<Value>toArray(Iterators.<Value>notNullFilter(values), new Value[]{});
        }
        assignParent();
	}

	/**
	 * Конструктор
	 * @param values инструкции для выполнения
	 */
	public ExpressionList(Value ... values){
        if( values==null ){
            this.values = new Value[]{};
        }else{
            this.values = xyz.cofe.types.TypesUtil.Iterators.<Value>toArray(
                    Iterators.<Value>notNullFilter(
                        Iterators.<Value>array(values)
                        ),
                    new Value[]{});
        }
        assignParent();
	}

    protected Value[] values = null;

	/**
	 * Возвращает инструкции
	 * @return Инструкции для выполнения
	 */
	public Value[] getValues(){
		return values;
	}

	/**
	 * Устанавливает инструкции
	 * @param values Инструкции для выполнения
	 */
	public void setValues(Value[] values){
		this.values = values;
        assignParent();
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Object evaluate() {
        Object r = null;
        if( values!=null ){
            for( Value v : values ){
                r = v.evaluate();
                if( r!=null && r instanceof ExecuteFlow ){
                    
    //                ExecuteFlow.Target t = ((ExecuteFlow)r).getExecuteFlowTarget();
    //                if( t.compareTo(ExecuteFlow.Target.Break) >= 0 )
                    break;
                }
            }
        }
        return r;
    }

    /* (non-Javadoc) @see lang2.vm.TypeSupport */
    @Override
    public Type getType() {
        if( values==null )return PrimitiveType.Undefined;
        Type t = PrimitiveType.Undefined;
        for( Value v : values ){
            if( v==null )continue;
            if( !(v instanceof TypeSupport) ){
                t = PrimitiveType.Undefined;
            }else{
                t = ((TypeSupport)v).getType();
            }

            if( v instanceof ExecuteFlowValue
                && v instanceof TypeSupport ){
                t = ((TypeSupport)v).getType();
                break;
            }
        }
        return t;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new ExpressionList(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return values;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index<0 || index>=values.length )throw new IndexOutOfBoundsException();
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        values[index] = tn;
        tn.setParent(this);
    }
}
