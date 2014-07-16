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
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.LValue;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.TypeSupport;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.collection.Pair;

/**
 * Операторы языка - Плюс, Минус, Делить и т.д. <br/>
 * Операторы определены в перечислении OperatorName <br/>
 * Оператор может иметь несколько операндов getOperands
 * @see OperatiorName
 * @see #getOperands()
 * @author Камнев Георгий Павлович
 */
public class Operator
        extends AbstractTreeNode<Value>
        implements Value, TypeSupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Operator.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Operator.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Operator.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Operator.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Operator.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
	protected OperatorName operatorName = null;
	protected Value[] operands = null;
    
    private void assignParent(){
        if( operands!=null )
            for( Value op : operands )
                if( op != null )
                    op.setParent(this);
    }
	
	/**
	 * Конструктор
	 */
	public Operator()
	{
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Operator(Operator src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		operatorName = src.operatorName;
        extender = src.extender;
		if( src.operands!=null )operands = Arrays.copyOf(src.operands, src.operands.length);
        assignParent();
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Operator(Operator src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		operatorName = src.operatorName;
        extender = src.extender;
		if( src.operands!=null )operands = Arrays.copyOf(src.operands, src.operands.length);
        if( operands!=null && deep ){
            for( int i=0;i<operands.length;i++ ){
                if( operands[i]!=null )
                    operands[i] = operands[i].deepClone();
            }
        }
        assignParent();
	}
	
	/**
	 * Конструктор
	 * @param opName Тип оператора - Имя
	 * @param values Параметры оператора (Операнды)
	 */
	public Operator(OperatorName opName,Value ... values)
	{
		this.operatorName = opName;
		this.operands = values;
        assignParent();
	}
	
	/**
	 * Возвращает имя оператора
	 * @return the operatorName Имя оператор
	 */
	public OperatorName getOperatorName()
	{
		return operatorName;
	}

	/**
	 * Устанавливает имя оператора
	 * @param operatorName the operatorName to set
	 */
	public void setOperatorName(OperatorName operatorName)
	{
		this.operatorName = operatorName;
	}

	/**
	 * Возвращает операнды
	 * @return the operands Операнды
	 */
	public Value[] getOperands()
	{
		return operands;
	}

	/**
	 * Устанавливает операнды
	 * @param operands Операнды
	 */
	public void setOperands(Value[] operands)
	{
		this.operands = operands;
        assignParent();
	}

    /* (non-Javadoc) @see lang2.vm.TypeSupport */
    @Override
    public Type getType() {
        Object ext = getExtender();
        Value[] vals = getOperands();
        if( vals==null ){
            return PrimitiveType.Undefined;
        }

        Type[] opTypes = new Type[vals.length];
        for( int i=0; i<vals.length;i++ ){
            opTypes[i] = PrimitiveType.Undefined;
            if( vals[i] instanceof TypeSupport ){
                opTypes[i] = ((TypeSupport)vals[i]).getType();
            }
        }

        if( ext!=null && ext instanceof TypeExtender ){
            Pair<Type,Boolean> extType = ((TypeExtender)ext).extendOperatorType(this, getOperatorName(), opTypes);
            if( extType!=null && extType.B() )
                return extType.A();
        }

        return PrimitiveType.Undefined;
    }
    
    protected void logEvaluate(){
        StringBuilder mess = new StringBuilder();
        
        Object[] param = 
                operands!=null ?
                    new Object[operands.length + 1]:
                    new Object[1];
        
        param[0] = operatorName;
        mess.append("operator={0}");
        
        if( operands!=null ){
            mess.append(" operands(").append(operands.length).append("): ");
            
            for( int i=0; i<operands.length; i++ ){
                param[i+1] = operands[0];
                
                if( i>0 ){
                    mess.append(", ");
                }
                mess.append("{");
                mess.append(i+1);
                mess.append("}");
            }
        }
        
        logFine(mess.toString(), param);
    }

	/* (non-Javadoc)
	 * @see lang2.vm.Value#eval()
	 */
	@Override
	public Object evaluate()
	{
        if( Thread.currentThread().isInterrupted() )return null;
        logEvaluate();
        switch( operands.length )
		{
			case 1:
			{
				Value op1 = operands[0];
				switch ( operatorName )
				{
					case NOT: return NOT(op1, this);
					case UNARY_MINUS: return UNARY_MINUS(op1, this);
				}
			}
			break;
			case 2:
			{
				Value op1 = operands[0];
				Value op2 = operands[1];
				switch ( operatorName )
				{
					case MULTIPLE: return MULTIPLE(op1, op2, this);
					case DIVIDE: return DIVIDE(op1, op2, this);
                    case MOD: return MOD(op1, op2, this);
					case ADD: return ADD(op1, op2, this);
					case SUBSTRACT: return SUBSTRACT(op1, op2, this);
					case COMAPRE_EQUALS: return COMAPRE_EQUALS(op1, op2, this);
					case COMAPRE_LESS: return COMAPRE_LESS(op1, op2, this);
					case COMAPRE_LESS_OR_EQUALS: return COMAPRE_LESS_OR_EQUALS(op1, op2, this);
					case COMAPRE_MORE: return COMAPRE_MORE(op1, op2, this);
					case COMAPRE_MORE_OR_EQUALS: return COMAPRE_MORE_OR_EQUALS(op1, op2, this);
					case COMAPRE_NOT_EQUALS: return COMAPRE_NOT_EQUALS(op1, op2, this);
					case AND: return AND(op1, op2, this);
					case OR: return OR(op1, op2, this);
					case XOR: return XOR(op1, op2, this);
					case ASSIGN: return ASSIGN(op1, op2, this);
				}
			}
			break;
		}
		return null;
	}
    
    /**
     * Расширение оператора
     */
    public interface Extender
    {
        /**
         * Расширяет операторы
         * NOT,
         * MULTIPLE, DIVIDE,
         * UNARY_MINUS,
         * ADD, SUBSTRACT,
         * AND, XOR, OR
         * @param invoker Operator вызывающий это расширение
         * @param opName Имя оператора
         * @param args Аргументы
         * @return Результат
         */
        public Pair<Object,Boolean> extendOperator(Value invoker,OperatorName opName,Object ... args);

        /**
         * Расширяет операторы COMAPRE_*
         * @param invoker Объект Operator вызывающий это расширение
         * @param arg1 Аргумент 1
         * @param arg2 Аргумент 2
         * @return Результат
         */
        public Pair<Integer,Boolean> extendCompare(Value invoker,Object arg1, Object arg2);
    }

    /**
     * Расширение оператора - типы
     */
    public interface TypeExtender
    {
        public Pair<Type,Boolean> extendOperatorType(Value invoker,
                OperatorName opName,
                Type ... argTypes);
    }
    
    /**
     * Расширение
     */
    protected Extender extender = null;
    
    /**
     * Возвращает расширение
     * @return расширение
     */
    public Extender getExtender(){ return extender; }
    
    /**
     * Устанавливает расширение
     * @param ext расширение
     */
    public void setExtender(Extender ext){ this.extender = ext; }
	
	private static Object ASSIGN( Value v1, Value v2, Operator opThis ){
		if( v1!=null && v1 instanceof LValue && v2!=null ){
            LValue lv = (LValue)v1;
            return lv.evaluate(v2.evaluate());
		}
		return null;
	}
	
	/**
	 * Приводит значение(Число/Строка...) к числу
	 * @param v Значение (Число/Строка...)
	 * @return Число
	 */
	private static Double toDouble(Object v,Operator operatorThis)
	{
        if( Thread.currentThread().isInterrupted() )return null;
		if( v instanceof Number )
		{			
			return ((Number)v).doubleValue();
		}
		else
		{
			if( v!=null )
			{
				String txt = v.toString();
				try
				{
					return Double.parseDouble(txt);
				}
				catch(java.lang.NumberFormatException ex)
				{
                    Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,"can't parse as number from:"+txt,ex );
                    throw new CastError(
                            operatorThis,
                            "can't parse as number from:"+txt,
                            ex);
				}
			}
			else
			{
                Logger.getLogger(Operator.class.getName()).log(Level.SEVERE,"value is null" );
                throw new NullRefError(
                        operatorThis,
                        "null ref");
			}
		}
	}

	/**
	 * Приводит значение(Число/Строка...) к числу
	 * @param v Значение (Число/Строка...)
	 * @return Число
	 */
	private static boolean toBool(Object v,Operator opThis)
	{
        if( Thread.currentThread().isInterrupted() )return false;
		if( v instanceof Boolean )
		{			
			return (Boolean)v;
		}
		else
		{
			if( v!=null )
			{
				String txt = v.toString();
				if( txt.equals("true") )return true;
				if( txt.equals("false") )return false;
                Logger.getLogger(Operator.class.getName()).severe(
                        "can't parse as boolean from:"+txt+" (valid values: 'true' or 'false')"
                        );
                
                throw new CastError(
                        opThis,
                        "can't parse as boolean from:"+txt);
			}
			else
			{
                Logger.getLogger(Operator.class.getName()).severe(
                        "value is null"
                        );
                
                throw new NullRefError(
                        opThis,
                        "null ref");
			}
		}
	}
    
	private static Object NOT( Value op1, Operator opThis )
	{
        Object v = op1.evaluate();
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.NOT, v);
            if( r.B() )return r.A();
        }
		return !toBool( v, opThis );
	}

	private static Object MULTIPLE( Value op1, Value op2, Operator opThis )
	{
        Object v1 = op1.evaluate();
        Object v2 = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.MULTIPLE, v1, v2);
            if( r.B() )return r.A();
        }

        return toDouble(op1.evaluate(), opThis) * 
               toDouble(op2.evaluate(), opThis);
	}

	private static Object DIVIDE( Value op1, Value op2, Operator opThis )
	{
        Object v1 = op1.evaluate();
        Object v2 = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.DIVIDE, v1, v2);
            if( r.B() )return r.A();
        }

        return toDouble(v1, opThis) /
               toDouble(v2, opThis);
	}
    
    private static Object MOD( Value op1, Value op2, Operator opThis )
	{
        Object v1 = op1.evaluate();
        Object v2 = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.MOD, v1, v2);
            if( r.B() )return r.A();
        }
        
        if( (v1 instanceof Number) && (v2 instanceof Number) ){
            return ((Number)v1).longValue() % ((Number)v2).longValue();
        }

        return toDouble(v1, opThis).longValue() %
               toDouble(v2, opThis).longValue();
	}

	private static Object UNARY_MINUS( Value op1, Operator opThis )
	{
        Object v = op1.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.UNARY_MINUS, v);
            if( r.B() )return r.A();
        }

        Object o_op1 = v;
		return - toDouble(o_op1, opThis);
	}

	private static Object ADD( Value op1, Value op2, Operator opThis )
	{
		Object a = op1.evaluate();
		Object b = op2.evaluate();

        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.ADD, a, b);
            if( r.B() )return r.A();
        }
        
		if( (a instanceof String) )
		{
			String sa = (String)a;
			String sb = b!=null ? b.toString() : "null";
			return sa + sb;
		}

		return toDouble(a,opThis) + toDouble(b,opThis);
	}

	private static Object SUBSTRACT( Value op1, Value op2, Operator opThis )
	{
        Object a = op1.evaluate();
        Object b = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.SUBSTRACT, a , b);
            if( r.B() )return r.A();
        }
        
		return toDouble(a,opThis) - 
               toDouble(b,opThis);
	}

	private static boolean COMAPRE_EQUALS( Value op1, Value op2, Operator opThis )
	{
		Object a = op1.evaluate();
		Object b = op2.evaluate();

        if( opThis.extender!=null ){
            Pair<Integer,Boolean> r = opThis.extender.extendCompare(opThis, a, b);
            if( r.B() ){
                return r.A()==0;
            }
        }
        
        if( a==null && b==null )return true;
        if( a==null && b!=null )return false;
        if( a!=null && b==null )return false;

        if( a instanceof Comparable ){
            return ((Comparable)a).compareTo(b)==0;
        }

		return a.equals(b);
	}

	private static boolean COMAPRE_NOT_EQUALS( Value op1, Value op2, Operator opThis )
	{
        boolean res = COMAPRE_EQUALS(op1, op2, opThis);
        return !res;
	}

	private static boolean COMAPRE_MORE( Value op1, Value op2, Operator opThis )
	{
		Object a = op1.evaluate();
		Object b = op2.evaluate();

        if( opThis.extender!=null ){
            Pair<Integer,Boolean> r = opThis.extender.extendCompare(opThis, a, b);
            if( r.B() ){
                return r.A()>0;
            }
        }

        if( a==null && b==null )return false;
        if( a==null && b!=null )return false;
        if( a!=null && b==null )return true;

        // Частный случай славнения чисел,
        // т.к. реализация Double по умолчанию при сравнении с Integer выдает исключение
        // JDK 1.6.20 Sun (Oracle)
        if( a instanceof Number && !a.getClass().equals(b.getClass()) ){
            if( b instanceof Number ){
                if( a instanceof Byte )return ((Comparable)a).compareTo(((Number)b).byteValue()) > 0;
                if( a instanceof Short )return ((Comparable)a).compareTo(((Number)b).shortValue()) > 0;
                if( a instanceof Integer )return ((Comparable)a).compareTo(((Number)b).intValue()) > 0;
                if( a instanceof Long )return ((Comparable)a).compareTo(((Number)b).longValue()) > 0;
                if( a instanceof Float )return ((Comparable)a).compareTo(((Number)b).floatValue()) > 0;
                if( a instanceof Double )return ((Comparable)a).compareTo(((Number)b).doubleValue()) > 0;
            }else{
                if( a instanceof Byte )return ((Comparable)a).compareTo( toDouble(b,opThis).byteValue() ) > 0;
                if( a instanceof Short )return ((Comparable)a).compareTo(toDouble(b,opThis).shortValue()) > 0;
                if( a instanceof Integer )return ((Comparable)a).compareTo(toDouble(b,opThis).intValue()) > 0;
                if( a instanceof Long )return ((Comparable)a).compareTo(toDouble(b,opThis).longValue()) > 0;
                if( a instanceof Float )return ((Comparable)a).compareTo(toDouble(b,opThis).floatValue()) > 0;
                if( a instanceof Double )return ((Comparable)a).compareTo(toDouble(b,opThis).doubleValue()) > 0;
            }
        }

        if( a instanceof Comparable ){
            return ((Comparable)a).compareTo(b)>0;
        }

		return false;
	}

	private static boolean COMAPRE_LESS( Value op1, Value op2, Operator opThis )
	{
		Object a = op1.evaluate();
		Object b = op2.evaluate();

        if( opThis.extender!=null ){
            Pair<Integer,Boolean> r = opThis.extender.extendCompare(opThis, a, b);
            if( r.B() ){
                return r.A()<0;
            }
        }
        
        if( a==null && b==null )return false;
        if( a==null && b!=null )return true;
        if( a!=null && b==null )return false;

        // Частный случай славнения чисел,
        // т.к. реализация Double по умолчанию при сравнении с Integer выдает исключение
        // JDK 1.6.20 Sun (Oracle)
        if( a instanceof Number && !a.getClass().equals(b.getClass()) ){
            if( b instanceof Number ){
                if( a instanceof Byte )return ((Comparable)a).compareTo(((Number)b).byteValue()) < 0;
                if( a instanceof Short )return ((Comparable)a).compareTo(((Number)b).shortValue()) < 0;
                if( a instanceof Integer )return ((Comparable)a).compareTo(((Number)b).intValue()) < 0;
                if( a instanceof Long )return ((Comparable)a).compareTo(((Number)b).longValue()) < 0;
                if( a instanceof Float )return ((Comparable)a).compareTo(((Number)b).floatValue()) < 0;
                if( a instanceof Double )return ((Comparable)a).compareTo(((Number)b).doubleValue()) < 0;
            }else{
                if( a instanceof Byte )return ((Comparable)a).compareTo( toDouble(b,opThis).byteValue() ) < 0;
                if( a instanceof Short )return ((Comparable)a).compareTo(toDouble(b,opThis).shortValue()) < 0;
                if( a instanceof Integer )return ((Comparable)a).compareTo(toDouble(b,opThis).intValue()) < 0;
                if( a instanceof Long )return ((Comparable)a).compareTo(toDouble(b,opThis).longValue()) < 0;
                if( a instanceof Float )return ((Comparable)a).compareTo(toDouble(b,opThis).floatValue()) < 0;
                if( a instanceof Double )return ((Comparable)a).compareTo(toDouble(b,opThis).doubleValue()) < 0;
            }
        }

        if( a instanceof Comparable ){
            return ((Comparable)a).compareTo(b)<0;
        }

		return false;
	}

	private static boolean COMAPRE_MORE_OR_EQUALS( Value op1, Value op2, Operator opThis )
	{
        return !COMAPRE_LESS(op1, op2, opThis);
	}

	private static boolean COMAPRE_LESS_OR_EQUALS( Value op1, Value op2, Operator opThis )
	{
        return !COMAPRE_MORE(op1, op2, opThis);
	}

	private static Object AND( Value op1, Value op2, Operator opThis )
	{
        Object a = op1.evaluate();
        Object b = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.AND, a, b);
            if( r.B() )return r.A();
        }
        
		return toBool(a, opThis) && toBool(b, opThis);
	}

	private static Object XOR( Value op1, Value op2, Operator opThis )
	{
        Object a = op1.evaluate();
        Object b = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.XOR, a, b);
            if( r.B() )return r.A();
        }
        
		return toBool(a, opThis) ^ toBool(b, opThis);
	}

	private static Object OR( Value op1, Value op2, Operator opThis )
	{
        Object a = op1.evaluate();
        Object b = op2.evaluate();
        
        if( opThis.extender!=null ){
            Pair<Object,Boolean> r = opThis.extender.extendOperator(opThis, OperatorName.OR, a, b);
            if( r.B() )return r.A();
        }
        
		return toBool(a,opThis) || toBool(b,opThis);
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new Operator(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return operands;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( operands==null )throw new IllegalStateException("operands==null");
        if( index<0 || index>=operands.length )throw new IndexOutOfBoundsException();
        if (tn== null) {            
            throw new IllegalArgumentException("tn==null");
        }
        operands[index] = tn;
        tn.setParent(this);
    }
}
