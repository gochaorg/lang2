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
package xyz.cofe.lang2.parser;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.PythMethod;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.NVariable;
import xyz.cofe.lang2.vm.op.Variable;

/**
 * @author gocha
 */
public class OptL2Factory extends L2Factory
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(OptL2Factory.class.getName()).log(Level.SEVERE, null, ex);
    }

    //</editor-fold>
//    /**
//     * Конструктор
//     * @param factory фабрика изначальная
//     * @param opt Опции парсера/исполнения
//     */
//    public OptL2Factory(Factory factory, ParserOptions opt)
//    {
//        super(factory);
//        if (opt== null) {            
//            throw new IllegalArgumentException("opt==null");
//        }
//        this.opt = opt;
//    }
    public OptL2Factory(Map<String, Object> memory, ParserOptions opt) {
        super(memory);
        if (opt== null) {            
            throw new IllegalArgumentException("opt==null");
        }
        this.opt = opt;
    }
    
    /**
     * Опции парсера/исполнения
     */
    protected ParserOptions opt = null;

    @Override
    public Value Variable(String id) {
        Value v = super.Variable(id);
        if( opt!=null && opt.isCatchNotDefVariableInIf() ){
            if( v instanceof Variable ){
                Variable var = (Variable)v;
                NVariable nv = new NVariable(var);
                return nv;
            }
        }
        return v;
    }

    @Override
    public Method createMethod(Map obj, xyz.cofe.lang2.vm.op.Function func) {
        if( opt!=null && opt.isPythonLikeMethod() ){
            return new PythMethod(obj,func);
        }
        return super.createMethod(obj, func);
    }
}
