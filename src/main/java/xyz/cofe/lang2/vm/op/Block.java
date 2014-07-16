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

import xyz.cofe.lang2.vm.VariableScopeListener;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.RelinkVarScope;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.VariableScopeSender;
import xyz.cofe.lang2.vm.err.CompileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Блок инструкций<br/>
 * Синтаксис блока инструкций:
 * <code>
 * { инструкция1 ; инструкция2 ; ... инструкцияN }
 * </code><br/>
 * Возвращает результат последней инструкции
 * @author gocha
 */
public class Block extends AbstractTreeNode<Value> 
implements Value, VariableScopeSender, MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Block.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Block.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Block.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Block.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Map<String, Object> memory = null;
	protected Value[] values = null;

    /**
     * Конструктор по умолчанию
     */
    public Block(){
    }
	
	/**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public Block(Block src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		if( src.values!=null )values = Arrays.copyOf(src.values, src.values.length);
        memory = src.memory;
        if( values!=null )for( Value v : values )if( v!=null )v.setParent(this);
	}
	
	/**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deepCopy Глубокое копирование
     */
	public Block(Block src,boolean deepCopy){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
		if( src.values!=null )values = Arrays.copyOf(src.values, src.values.length);
        if( values!=null && deepCopy ){
            for( int i=0; i<values.length; i++ ){
                values[i] = values[i].deepClone();
            }
        }
        memory = src.memory;
        if( values!=null )for( Value v : values )if( v!=null )v.setParent(this);
	}

    /**
	 * Конструктор
	 * @param values инструкции для выполнения
	 */
	public Block(Value ... values){
		this(null,values);
	}
	
	/**
	 * Конструктор
     * @param memory "память"
	 * @param values инструкции для выполнения
	 */
	public Block(Map<String, Object> memory, Value ... values){
		this.values = values;
        if( values!=null )for( Value v : values )if( v!=null )v.setParent(this);
        this.memory = memory;
        linkVarDef();
	}

	/**
	 * Конструктор
	 * @param values инструкции для выполнения
	 */
	public Block(Iterable<Value> values){
        this(null,values);
	}
	
	/**
	 * Конструктор
     * @param memory "память"
	 * @param values инструкции для выполнения
	 */
	public Block(Map<String, Object> memory, Iterable<Value> values){
		ArrayList<Value> vl = new ArrayList<Value>();
		if( values!=null ){
			for( Value v : values ){
				if( v!=null )vl.add(v);
			}
		}
		this.values = vl.toArray(new Value[]{});
        if( values!=null )for( Value v : values )if( v!=null )v.setParent(this);
        this.memory = memory;
        linkVarDef();
	}

    /**
     * Связывает обявление локальных переменных с этой областью видимости
     */
    protected void linkVarDef(){
        RelinkVarScope rvs = new RelinkVarScope();
        rvs.go(this);
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
		//TODO Использовать link и unlink (UnlinkVarScope)
		this.values = values;
	}
	
	/**
	 * Подписчики входа/выхода из области видимости
	 */
	private Collection<VariableScopeListener> listeners = null;
	
	/**
	 * Возвращает подписчиков входа/выхода из области видимости
	 * @return Подписчики области видимости
	 */
    @Override
	public Collection<VariableScopeListener> getScopeListeners(){
		if( listeners==null )listeners = new LinkedHashSet<VariableScopeListener>();
		return listeners;
	}

    //TODO Mem Работа с памятью
    /**
     * Уведомляет подписчиков о начале выполения инструкций в блоке
     */
	@SuppressWarnings("unchecked")
	private void fireEnter(){
		if( memory==null ){
            Logger.getLogger(Block.class.getName()).severe("memory not set");
            throw new CompileException(this, "memory==null");
		}
		
		Map scope = Variable.createVarScope(memory);
		for( VariableScopeListener l : getScopeListeners() ){
			if( l!=null )l.scopeEnter(scope);
		}
	}

    //TODO Mem Работа с памятью
    /**
     * Уведомляет подписчиков о завершении выполения инструкций в блоке
     */
	@SuppressWarnings("unchecked")
	private void fireExit(Object r){
		if( memory==null ){
            Logger.getLogger(Block.class.getName()).severe("memory not set");
            throw new CompileException(this, "memory==null");
		}
		
		Map scope = Variable.releaseVarScope(memory);
		for( VariableScopeListener l : getScopeListeners() ){
			if( l!=null )l.scopeExit(scope);
		}
	}
	
	/**
	 * Выполняет блок инструкций
	 */
	@Override
	public Object evaluate() 
	{
		Object r = null;
		if( values==null ){
			fireEnter();
			fireExit(r);
			return r;
		}
        try{
            fireEnter();
            for( Value v : values ){
                r = v.evaluate();
                if( r!=null && r instanceof ExecuteFlow ){
    //                ExecuteFlow.Target t = ((ExecuteFlow)r).getExecuteFlowTarget();
    //                if( t.compareTo(ExecuteFlow.Target.Break) >= 0 )
                    break;
                }
            }
        }finally{
            fireExit(r);
        }
		return r;
	}

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        Block b = new Block(this,true);
        return b;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return values;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( values==null )throw new IllegalStateException("values==null");
        if( index<0 || index>=values.length )throw new IndexOutOfBoundsException();
        values[index] = tn;
    }
}
