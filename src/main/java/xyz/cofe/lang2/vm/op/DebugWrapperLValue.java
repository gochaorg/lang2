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
import xyz.cofe.lang2.vm.LValue;
import xyz.cofe.lang2.vm.Value;

/**
 * Обвертка над L значением для отладки
 * @author gocha
 */
public class DebugWrapperLValue extends DebugWrapperValue implements LValue
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DebugWrapperLValue.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    /**
     * Конструктор
     * @param wrappedLValue обворачиваемое значение
     */
    public DebugWrapperLValue(LValue wrappedLValue){
        super(wrappedLValue);
    }
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public DebugWrapperLValue(DebugWrapperLValue src){
		super(src);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public DebugWrapperLValue(DebugWrapperLValue src,boolean deep){
		super(src,deep);
	}

    /* (non-Javadoc) @see LValue */
    @Override
    public Object evaluate(Object value) {
        if( Thread.currentThread().isInterrupted() )return null;
        LValue lval = (LValue)wrappedValue;
        
        if( start!=null && stop!=null ){
            int startLine = start.getLine();
            int startChar = start.getCharPositionInLine();
            String opClass = wrappedValue!=null ? wrappedValue.getClass().toString() : "null";
            int stopLine = stop.getLine();
            int stopChar = stop.getCharPositionInLine();
            logFiner( 
                    "evaluate class={0} start.line={1} start.charInLine={2} stop.line={3} stop.charInLine={4} value={5}", 
                    opClass, startLine, startChar, stopLine, stopChar, value
                    );
        }
        
        Object res = lval.evaluate(value);
        
        if( start!=null && stop!=null ){
            int startLine = start.getLine();
            int startChar = start.getCharPositionInLine();
            String opClass = wrappedValue!=null ? wrappedValue.getClass().toString() : "null";
            int stopLine = stop.getLine();
            int stopChar = stop.getCharPositionInLine();
            logFiner( 
                    "evaluate.result={5} class={0} start.line={1} start.charInLine={2} stop.line={3} stop.charInLine={4} value={6}", 
                    opClass, startLine, startChar, stopLine, stopChar, res==null ? "null" : res, value
                    );
        }
        
        return res;
    }

    /* (non-Javadoc) @see LValue */
    @Override
    public Value deepClone() {
        return new DebugWrapperLValue(this, true);
    }
}
