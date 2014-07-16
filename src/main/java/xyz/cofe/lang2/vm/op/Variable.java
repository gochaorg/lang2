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

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.LValue;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.err.VarNotExistsError;

/**
 * Значение - переменная.
 * <p>
 * Используется в выражениях вида: <code> 1 + a * b </code>.
 * Так a и b заменяются на данный объект
 * </p>
 * @author Камнев Георгий Павлович
 */
public class Variable extends AbstractTreeNode<Value> implements
        Value,
        LValue,
        MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Variable.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Variable.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Variable.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Variable.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Variable.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Variable.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Map<String, Object> memory = null;
	protected String variable = null;

	/**
	 * Конструктор по умолчанию
	 */
	public Variable()
	{
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Variable(Variable src){
		if (src== null) {
			throw new IllegalArgumentException("src==null");
		}
		this.memory = src.memory;
		this.variable = src.variable;
	}

	/**
	 * Конструктор
	 * @param varMemo Память
	 * @param varName Имя переменной
	 */
	public Variable(Map<String, Object> varMemo, String varName)
	{
		this.memory = varMemo;
		this.variable = varName;
	}

	/**
	 * Возвращает "память"
	 * @return "память"
	 */
	public Map<String, Object> getMemory()
	{
		return memory;
	}

	/**
	 * Устанавливает "память"
	 * @param varMemo "память"
	 */
	public void setMemory(Map<String, Object> varMemo)
	{
		this.memory = varMemo;
	}

	/**
	 * Возвращает имя переменной
	 * @return имя переменной
	 */
	public String getVariable()
	{
		return variable;
	}

	/**
	 * Устанавливает имя переменной
	 * @param varName имя переменной
	 */
	public void setVariable(String varName)
	{
		this.variable = varName;
	}

    //TODO Mem Работа с памятью
	/**
	 * Возвращает значение переменной
	 */
	@Override
	public Object evaluate()
	{
        if( memory==null ){
            Logger.getLogger(Variable.class.getName()).severe("memory not set");
            throw new CompileException(this, "memory==null");
        }

        if( variable==null ){
            Logger.getLogger(Variable.class.getName()).severe("varibale name not set");
            throw new CompileException(this, "variable==null");
        }

		if( !memory.containsKey(variable) )
        {
            String mess = "varibale "+variable+" not exists";
            Logger.getLogger(Variable.class.getName()).severe(mess);
            throw new VarNotExistsError(this,mess);
        }

        return memory.get(variable);
	}

    //TODO Mem Работа с памятью
	/**
	 * Возвращает стек области видимости
	 * @param memory Память
	 * @return Стек области видимости
	 */
	@SuppressWarnings("unchecked")
	public static Stack getVarScopeStack(Map<String,Object> memory){
		if( memory==null )return null;

		Object oStack = memory.get("$stackVarScope");

		if( oStack!=null && oStack instanceof Stack )
			return (Stack)oStack;

		Stack stack = new Stack();
		memory.put("$stackVarScope", stack);

		return stack;
	}

    //TODO Mem Работа с памятью
	/**
	 * Создает область видимости переменных в стеке
	 * @param memory Память
	 * @return Область видимости
	 */
	@SuppressWarnings("unchecked")
	public static Map createVarScope(Map<String,Object> memory){
		if( memory==null )return null;
		Stack stack = getVarScopeStack(memory);
		Map scope = new HashMap();
		stack.push(scope);
		return scope;
	}

    //TODO Mem Работа с памятью
	/**
	 * Освобождает область видимости переменных из стека
	 * @param memory Память
	 * @return Область видимости
	 */
	@SuppressWarnings("unchecked")
	public static Map releaseVarScope(Map<String,Object> memory){
		if( memory==null )return null;
		Stack stack = getVarScopeStack(memory);
		Object o = null;
		if( stack.size()>0 )o = stack.pop();
		return o instanceof Map ? (Map)o : null;
	}
    
    //TODO Mem Работа с памятью
    /* (non-Javadoc) @see LValue */
	@Override
	public Object evaluate(Object value) {
        if( memory==null ){
            Logger.getLogger(Variable.class.getName()).severe("memory not set");
			throw new CompileException(this, "memory==null");
        }
        if( variable==null ){
            Logger.getLogger(Variable.class.getName()).severe("varibale name not set");
			throw new CompileException(this, "variable==null");
        }

        if( !memory.containsKey(variable) ){
            Logger.getLogger(Variable.class.getName()).fine("varibale "+variable+" not exists");
            throw new VarNotExistsError(this,"varibale "+variable+" not exists");
        }

        memory.put(variable, value);
		return value;
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new Variable(this);
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
}
