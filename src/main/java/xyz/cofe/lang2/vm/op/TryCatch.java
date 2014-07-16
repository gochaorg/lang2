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
import xyz.cofe.lang2.vm.Value;

/**
 * Конструкция try catch.<br/>
 * Пример:<br/>
 * <font face="monospaced">
 * try {<br/>
 * &nbsp;  <i>Блок/выражение</i><br/>
 * &nbsp;  <i>Если не было исключение, то значение этого блока - является возвращаемым</i><br/>
 * }<br/>
 * catch( e ){<br/>
 * &nbsp;  <i>Тут блок который обрабатывает значение переменной <b>e</b>, 
 *   она содержит информацию о исключении</i><br/>
 * &nbsp;  <i>Если все же было исключение и оно обработано, то возвращаемым значение будет этого блока (catch)</i><br/>
 * }
 * </font>
 * @author gocha
 */
public class TryCatch extends AbstractTreeNode<Value> 
implements Value, MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(TryCatch.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(TryCatch.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(TryCatch.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(TryCatch.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    protected Value tryBlock = null;
    protected Value catchBlock = null;
	protected Map<String, Object> memory = null;
    protected String variable = null;
	
	/**
	 * Конструктор по умолчанию
	 */
	public TryCatch(){
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public TryCatch(TryCatch src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		setTryBlock( src.tryBlock );
		setCatchBlock( src.catchBlock );
		memory = src.memory;
		variable = src.variable;
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public TryCatch(TryCatch src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		setTryBlock( deep && src.tryBlock!=null ? src.tryBlock.deepClone() : src.tryBlock );
		setCatchBlock( deep && src.catchBlock!=null ? src.catchBlock.deepClone() : src.catchBlock );
		memory = src.memory;
		variable = src.variable;
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

    public Value getCatchBlock() {
        return catchBlock;
    }

    public void setCatchBlock(Value catchBlock) {
        this.catchBlock = catchBlock;
        if( catchBlock!=null )catchBlock.setParent(this);
    }

    public Value getTryBlock() {
        return tryBlock;
    }

    public void setTryBlock(Value tryBlock) {
        this.tryBlock = tryBlock;
        if( tryBlock!=null )tryBlock.setParent(this);
    }

    //TODO Mem Работа с памятью
    /* (non-Javadoc) @see Value */
    @Override
    public Object evaluate() {
        Object result = null;
		if( memory==null ){
			Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE,"memory==null");
			throw new CompileException(this,"memory==null");
		}
        if( variable==null ){
			Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE,"variable==null");
			throw new CompileException(this,"variable==null");
        }
        if( catchBlock==null ){
			Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE,"catchBlock==null");
			throw new CompileException(this,"catchBlock==null");
        }
        if( tryBlock==null ){
			Logger.getLogger(TryCatch.class.getName()).log(Level.SEVERE,"tryBlock==null");
			throw new CompileException(this,"tryBlock==null");
        }
        
		boolean hasThrowValue = false;
		Object throwValue = null;

		try {
			Object tryResult = tryBlock.evaluate();
			if( tryResult!=null && tryResult instanceof ExecuteFlow ){
				ExecuteFlow ef = (ExecuteFlow)tryResult;
				ExecuteFlow.Target efTarget = ef.getExecuteFlowTarget();
				if( ExecuteFlow.Target.Throw == efTarget ){
					hasThrowValue = true;
					if( ef.isHasFlowResult() ){
						throwValue = ef.getFlowResult();
					}
				}else{
					result = tryResult;
				}
			}else{
				result = tryResult;
			}
		}
		catch(Throwable e){
			hasThrowValue = true;
			throwValue = e;
		}

		if( hasThrowValue ){
			Map scope = null;
			if( memory.containsKey(variable) ){
				scope = Variable.createVarScope(memory);
				scope.put(variable, memory.get(variable));
			}
			memory.put(variable, throwValue);

			try{
				Object catchResult = catchBlock.evaluate();
				result = catchResult;
			}catch(Throwable e){
				Value v = ExecuteFlowValue.createThrow(new Const(e), new Const(throwValue));
				result = v;
			}

			if( scope!=null ){
				Variable.releaseVarScope(memory);
				memory.put(variable, scope.get(variable));
			}
		}
        return result;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        return new TryCatch(this, true);
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{tryBlock,catchBlock};
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index<0 || index >=2 )throw new IndexOutOfBoundsException();
        if( index==0 && tn!=null ){
            setTryBlock(tn);
            return;
        }
        if( index==1 ){
            setCatchBlock(tn);
            return;
        }
        throw new IllegalArgumentException("index="+index+" tn="+tn);
    }
}
