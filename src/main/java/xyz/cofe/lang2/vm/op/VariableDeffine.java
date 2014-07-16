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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.NewVariables;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.VariableScopeListener;

/**
 * Определяет переменную.<br/>
 * Конструкция вида: <code>var NAME = "значение"</code><br/>
 * evaluate() - Создает переменную с указанным значением и возвращает ее значение.
 * @author gocha
 */
public class VariableDeffine extends AbstractTreeNode<Value> implements
        Value,
        VariableScopeListener,
        NewVariables,
        MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(VariableDeffine.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Map<String, Object> memory = null;
	protected String variable = null;
	protected Value variableValue = null;

	/**
	 * Конструктор
	 */
	public VariableDeffine(){
		this(null,null,null);
	}

	/**
	 * Конструктор
	 * @param varMemo Память
	 * @param varName Имя переменной
	 */
	public VariableDeffine(Map<String, Object> varMemo, String varName){
		this(varMemo,varName,null);
	}

	/**
	 * Конструктор
	 * @param varMemo Память
	 * @param varName Имя переменной
	 * @param varValue Значение переменной
	 */
	public VariableDeffine(Map<String, Object> varMemo, String varName, Value varValue){
		this.memory = varMemo;
		this.variable = varName;
		this.variableValue = varValue;
        if( variableValue!=null )variableValue.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src
     */
	public VariableDeffine(VariableDeffine src){
		if (src== null) {
			throw new IllegalArgumentException("src==null");
		}
		this.memory = src.memory;
		this.variable = src.variable;
		this.variableValue = src.variableValue;
        if( variableValue!=null )variableValue.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src
     */
	public VariableDeffine(VariableDeffine src,boolean deep){
		if (src== null) {
			throw new IllegalArgumentException("src==null");
		}
		this.memory = src.memory;
		this.variable = src.variable;
		this.variableValue = deep && src.variableValue!=null ? src.variableValue.deepClone() : src.variableValue;
        if( variableValue!=null )variableValue.setParent(this);
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

	/**
	 * Возвращает значение переменной
	 * @return Значение переменной
	 */
	public Value getValue(){
		return variableValue;
	}

	/**
	 * Устанавливает значение переменной
	 * @param value Значение переменной
	 */
	public void setValue(Value value){
		variableValue = value;
        if( variableValue!=null )variableValue.setParent(this);
	}

    protected void setVarEvaluatedValue( Object value ){
		// Устанавливаем значение переменной
//        logFine( "Устанавливаем перменной {0} вычисленное значение {1}", variable, value );
//		memory.put(variable, value);
        defineVariable(memory, variable, value);
    }

    // TODO Mem Реализовать readOnly
    /**
     * Определяет переменную доступную и устанавливает ее значение
     * @param memo Память
     * @param variable Имя переменной
     * @param value Значение
     * @param readOnly true - Переменная доступна только для чтения / false - доступня для чтения - записи
     */
    public static void defineVariable( Map<String, Object> memo, String variable, Object value, boolean readOnly ){
        if( memo==null )throw new IllegalArgumentException( "memo==null" );
        if( variable==null )throw new IllegalArgumentException( "variable==null" );
        logFine( "Устанавливаем перменной {0} вычисленное значение {1}", variable, value );
		memo.put(variable, value);
    }

    // TODO Mem Реализовать readOnly
    /**
     * Определяет переменную доступную для чтения-записи и устанавливает ее значение
     * @param memo Память
     * @param variable Имя переменной
     * @param value Значение
     */
    public static void defineVariable( Map<String, Object> memo, String variable, Object value ){
        defineVariable(memo, variable, value, false);
    }

    //TODO Mem Работа с памятью
    /* (non-Javadoc) @see Value */
	@Override
	public Object evaluate() {
		if( memory==null ){
            Logger.getLogger(VariableDeffine.class.getName()).severe("memory==null");
            throw new CompileException(this, "memory==null");
		}
		if( variable==null ){
            Logger.getLogger(VariableDeffine.class.getName()).severe("variable==null");
            throw new CompileException(this, "variable==null");
		}
		Object val = null;
		if( variableValue!=null ){
			// Вычисляем значение переменной
			val = variableValue.evaluate();
		}
        setVarEvaluatedValue(val);
		return val;
	}

	/*
	 * (non-Javadoc)
	 * @see lang2.vm.NewVariables
	 */
	@Override
	public String[] getNewVaraibleNames() {
		if( variable==null )return new String[]{};
		return new String[]{ variable };
	}

    //TODO Mem Работа с памятью
	/*
	 * (non-Javadoc)
	 * @see lang2.vm.ScopeVariablesListener#scopeEnter
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public void scopeEnter(Map scope) {
		// Если есть уже переменная с указанным именем, то сохраняем ее
		if( memory!=null && variable!=null && memory.containsKey(variable) && scope!=null ){
            logFine( "СОхраняем предыдущее значение переменной {0}",variable );
			scope.put(variable, memory.get(variable));
		}else{
        }
	}

    //TODO Mem Работа с памятью
	/*
	 * (non-Javadoc)
	 * @see lang2.vm.ScopeVariablesListener#scopeExit
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void scopeExit(Map scope)
	{
		// Восстанавливаем переопределенную переменную
		if( scope==null )return;
		if( memory==null || variable==null )return;
		if( !scope.containsKey(variable) ){
            logFine("Среди сохраненых переменных нет {0} - соот. удалем ее из поля видимости", variable);
            memory.remove(variable);
        }else{
            logFine("Восстанавливаем предыдущее значение перменной {0}", variable);
            Object v = scope.get(variable);
            memory.put(variable, v);
        }
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new VariableDeffine(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{variableValue};
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index!=0 )throw new IndexOutOfBoundsException();
        if (tn== null) {
            throw new IllegalArgumentException("tn==null");
        }
        variableValue = tn;
        tn.setParent(this);
    }
}
