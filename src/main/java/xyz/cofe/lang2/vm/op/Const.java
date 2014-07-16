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
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.TypeSupport;
import xyz.cofe.lang2.vm.Value;

/**
 * Значение - константа: <b>123</b> или <b>"abc"</b>
 * @author Камнев Георгий Павлович
 */
public class Const extends AbstractTreeNode<Value> implements Value, TypeSupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Const.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Const.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Const.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Const.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Const.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Const.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Object constv = null;
	
	/**
	 * Конструктор
	 */ 
	public Const()
	{
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Const(Const src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		constv = src.constv;
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public Const(Const src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		constv = src.constv;
        if( constv!=null && constv instanceof Value ){
            constv = ((Value)constv).deepClone();
        }
	}
	
	/**
	 * Конструктор 
	 * @param val Значение константы
	 */
	public Const(Object val)
	{
		constv = val;
	}

	/**
	 * Конструктор 
	 * @param val Значение константы
     * @param parent Род. элеменет
	 */
	public Const(Object val,Value parent)
	{
		constv = val;
        setParent(parent);
	}

    /* (non-Javadoc) @see TYpeSupport */
    @Override
    public Type getType() {
        if( constv==null )return PrimitiveType.Null;
        if( constv instanceof Byte )return PrimitiveType.Byte;
        if( constv instanceof Short )return PrimitiveType.Short;
        if( constv instanceof Integer )return PrimitiveType.Integer;
        if( constv instanceof Long ) return PrimitiveType.Long;
        if( constv instanceof Float ) return PrimitiveType.Float;
        if( constv instanceof Double ) return PrimitiveType.Double;
        if( constv instanceof String )return PrimitiveType.String;
        if( constv instanceof Boolean )return PrimitiveType.Bool;
        if( constv instanceof TypeSupport )return ((TypeSupport)constv).getType();
        return PrimitiveType.Undefined;
    }
    
	/**
	 * Возвращает значение константы
	 * @return Значение константы
	 */
	public Object getConstValue()
	{
		return constv;
	}

	/**
	 * Устанавливает значение константы
	 * @param constv Значение константы
	 */
	public void setConstValue(Object constv)
	{
		this.constv = constv;
	}

	/* (non-Javadoc) 
	 * @see Value
	 */
	@Override
	public Object evaluate()
	{
		return constv;
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new Const(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{};
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String toString() {
        if( constv instanceof String ){
            return encodeString((String)constv);
        }
        return constv==null ? "null" : constv.toString();
    }
    
    public static String encodeString(String text){
        if( text==null )return "null";
        if( text.length()==0 )return "\"\"";

        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        for( int i=0; i<text.length(); i++ ){
            char c = text.charAt(i);
            switch( c ){
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                case '\'': sb.append("\\'"); break;
                case '\\': sb.append("\\\\"); break;
                default: sb.append(c); break;
            }
        }
        sb.append("\"");
        return sb.toString();
    }
}
