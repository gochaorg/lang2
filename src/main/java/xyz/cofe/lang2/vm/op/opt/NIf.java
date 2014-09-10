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


import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.IfElse;

/**
 * Условная конструкция IF.<br/>
 * Пример: <br/>
 * <code>
 * <b>if (</b> <i>Условие</i> <b>)</b> <i>Если верное условие</i> <br/>
 * <b>if (</b> 1 > 2 <b>)</b> "yes"
 * </code><br/>
 * В качестве условия могут выступать булево значения, 
 * а так же любые другие значения:
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class NIf extends If
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(NIf.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(NIf.class.getName()).log(Level.SEVERE, null, ex);
    }

    //</editor-fold>
    
    public NIf(){
    }
    
    public NIf(NIf src){
        super(src);
    }
    
    public NIf(NIf src,boolean deep){
        super(src,deep);
        if( deep ){
            this.nifOptions = src.nifOptions==null ? null : src.nifOptions.clone();
        }else{
            this.nifOptions = src.nifOptions;
        }
    }
    
    public NIf(Value condition,Value trueExp){
        super(condition,trueExp);
    }

    @Override
    public Value deepClone() {
        return new NIf(this, true);
    }
    
    protected NIfOpt nifOptions = null;

    public NIfOpt getNifOptions() {
        if( nifOptions==null )nifOptions = new NIfOpt();
        return nifOptions;
    }

    public void setNifOptions(NIfOpt nifOptions) {
        this.nifOptions = nifOptions;
    }
    
    @Override
    protected boolean evalConditionValue(Value condition) {
        return getNifOptions().evalConditionValue(condition);
    }
}
