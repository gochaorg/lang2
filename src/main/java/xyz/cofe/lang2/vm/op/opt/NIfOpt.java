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


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.op.IfElse;

/**
 * Поведение if / else
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class NIfOpt {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(NIfOpt.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    public NIfOpt(){
    }
    
    public NIfOpt(NIfOpt src){
        if( src!=null ){
            this.nullAs = src.nullAs;
        }
    }
    
    @Override
    public NIfOpt clone(){
        return new NIfOpt(this);
    }
    
    public static enum CastAs {
        CastException,
        False,
        True
    }
    
    protected CastAs nullAs = CastAs.False;

    public CastAs getNullAs() {
        if( nullAs==null )nullAs = CastAs.False;
        return nullAs;
    }

    public void setNullAs(CastAs nullAs) {
        this.nullAs = nullAs;
    }
    
    protected CastAs zeroAs = CastAs.False;

    public CastAs getZeroAs() {
        if( zeroAs==null )zeroAs = CastAs.False;
        return zeroAs;
    }

    public void setZeroAs(CastAs zeroAs) {
        this.zeroAs = zeroAs;
    }
    
    protected CastAs notBooleanAs = CastAs.True;

    public CastAs getNotBooleanAs() {
        if( notBooleanAs==null )notBooleanAs = CastAs.True;
        return notBooleanAs;
    }

    public void setNotBooleanAs(CastAs notBooleanAs) {
        this.notBooleanAs = notBooleanAs;
    }
    
    public boolean evalConditionValue(Value condition) {
		Object c = condition.evaluate();
		if( c==null ){
            switch( getNullAs() ){
                case CastException:
                    Logger.getLogger(IfElse.class.getName()).severe("condition.evaluate()==null");
                    throw new NullRefError(this, "condition is null");
                case False:
                    return false;
                case True:
                    return true;
                default:
                    Logger.getLogger(IfElse.class.getName()).severe("condition.evaluate()==null");
                    throw new NullRefError(this, "condition is null");
            }
		}
        
		if( !(c instanceof Boolean) ){
            boolean isZero = false;
            if( c instanceof Byte && ( ((Byte)c)==0 ) ){
                isZero = true;
            }else if( c instanceof Short && ( ((Short)c)==0 ) ){
                isZero = true;
            }else if( c instanceof Integer && ( ((Integer)c)==0 ) ){
                isZero = true;
            }else if( c instanceof Long && ( ((Long)c)==0 ) ){
                isZero = true;
            }else if( c instanceof Float && ( ((Float)c)==0 ) ){
                isZero = true;
            }else if( c instanceof Double && ( ((Double)c)==0 ) ){
                isZero = true;
            }else if( c instanceof BigInteger && ( ((BigInteger)c).equals( BigInteger.ZERO ) ) ){
                isZero = true;
            }else if( c instanceof BigDecimal && ( ((BigDecimal)c).equals( BigDecimal.ZERO ) ) ){
                isZero = true;
            }
            
            if( isZero ){
                switch( getZeroAs() ){
                    case False:
                        return false;
                    case True:
                        return true;
                }
            }
            
            switch( getNotBooleanAs() ){
                case False:
                    return false;
                case True:
                    return true;
            }
            
            Logger.getLogger(IfElse.class.getName()).severe("!(condition.evaluate() instanceof Boolean)");
            throw new CastError(this, "can't cast to boolean from condition ("+c.getClass().getName()+")");
		}
        
        return (Boolean)c;
    }
}
