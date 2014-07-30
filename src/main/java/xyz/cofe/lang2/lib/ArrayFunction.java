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

package xyz.cofe.lang2.lib;


import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.err.ClassNotFoundError;

/**
 *
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class ArrayFunction 
implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ArrayFunction.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    private ClassLoader typeClassLoader = null;

    public synchronized ClassLoader getTypeClassLoader() {
        if( typeClassLoader!=null )return typeClassLoader;
        typeClassLoader = this.getClass().getClassLoader();
        return typeClassLoader;
    }

    public synchronized void setTypeClassLoader(ClassLoader typeClassLoader) {
        this.typeClassLoader = typeClassLoader;
    }

    
    @Override
    public Object call(Object... arguments) {
        if( arguments==null || arguments.length<2 )return null;
        if( arguments[0]==null )return null;
        if( arguments[1]==null )return null;
        
        Object arg0 = arguments[0];
        Object arg1 = arguments[1];
        
        Class arrClass = null;
        if( arg0 instanceof Class ){
            arrClass = (Class)arg0;
        }else if( arg0 instanceof String ){
            try {
                arrClass = Class.forName(arg0.toString(),true,getTypeClassLoader());
            } catch (ClassNotFoundException ex) {
                logException(ex);
                throw new ClassNotFoundError(ex);
            }
        }else{
            return null;
        }
        
        int count = -1;
        if( arg1 instanceof Number ){
            count = ((Number)arg1).intValue();
        }else{
            return null;
        }
        
        return Array.newInstance(arrClass, count);
    }
}
