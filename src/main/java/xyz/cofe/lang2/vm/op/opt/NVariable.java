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
package xyz.cofe.lang2.vm.op.opt;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.err.VarNotExistsError;
import xyz.cofe.lang2.vm.ValuePath;
import xyz.cofe.collection.Predicate;
import xyz.cofe.lang2.vm.op.Variable;

/**
 * Значение - переменная.
 * <p>
 * Используется в выражениях вида: <code> 1 + a * b </code>.
 * Так a и b заменяются на данный объект
 * </p>
 * @author Камнев Георгий Павлович
 */
public class NVariable extends Variable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(NVariable.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(NVariable.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(NVariable.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(NVariable.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(NVariable.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(NVariable.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
	/**
	 * Конструктор по умолчанию
	 */
	public NVariable()
	{
	}
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public NVariable(Variable src){
        super(src);
//		if (src== null) {			
//			throw new IllegalArgumentException("src==null");
//		}
//		this.memory = src.getMemory();
//		this.variable = src.getVariable();
	}
	
	/**
	 * Конструктор
	 * @param varMemo Память
	 * @param varName Имя переменной
	 */
	public NVariable(Map<String, Object> varMemo, String varName)
	{
		this.memory = varMemo;
		this.variable = varName;
	}
	
    protected static ValuePath.Predicates.Compare cmpEq = ValuePath.Predicates.Compare.Equals;
    protected static Predicate<ValuePath> firstArg = ValuePath.Predicates.Index(cmpEq, 0);
    protected static Predicate<ValuePath> var = ValuePath.Predicates.Variable();
    protected static Predicate<ValuePath> _if = ValuePath.Predicates.If();
    protected static Predicate<ValuePath> _ifElse = ValuePath.Predicates.IfElse();
    protected static Predicate<ValuePath> if_or_ifElse = ValuePath.Predicates.Or(_if,_ifElse);
    protected static Predicate<ValuePath> ifParent = ValuePath.Predicates.Parent(if_or_ifElse);
    protected static Predicate<ValuePath> tmpl1 = ValuePath.Predicates.And(ifParent,var,firstArg);

    //TODO Mem Работа с памятью
	/**
	 * Возвращает значение переменной
	 */
	@Override
	public Object evaluate()
	{
        if( memory==null ){
            logSevere("memory not set");
            throw new CompileException(this, "memory==null");
        }
        
        if( variable==null ){
            logSevere("varibale name not set");
            throw new CompileException(this, "variable==null");
        }

		if( !memory.containsKey(variable) )
        {
            ValuePath vp = ValuePath.parsePathWODebug(this);
            String path = vp.toString();
            if( tmpl1.validate(vp) ){
//                System.out.println("matched!");
                return false;
            }
            
            String mess = "varibale "+variable+" not exists."
                    + "\npath="+path;
            
            Logger.getLogger(NVariable.class.getName()).severe(mess);
            throw new VarNotExistsError(this,mess);
        }
        
        Object varValue = memory.get(variable);
        logFiner("Переменная прочитана {0} = {1}", variable, varValue);

        return varValue;
	}
}
