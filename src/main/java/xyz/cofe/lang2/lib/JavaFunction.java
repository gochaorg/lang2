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


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.cli.DynamicClassLoader;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.jre.CallConstructorVariant;

/**
 * Функция по работе с Java объектами
 * @author nt.gocha@gmail.com
 */
public class JavaFunction extends HashMap implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JavaFunction.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private ClassLoader cl = null;
    private Set<URL> classpath = null;
    private String comment = null;

    public JavaFunction(String comment){
        if( comment==null )comment =  "no comments";
        this.comment = comment;
        
//        HashMap classpathField = new HashMap();
//        put( "classpath", classpathField );
//
//        classpathField.put("add", addClassPathURLFun);
    }
    
    public String getComment(){ return comment; }

//    private Set<URL> classpath(){
//        if( classpath==null )classpath = new HashSet<URL>();
//        return classpath;
//    }
//
//    private void addClassPathURL(URL classPathURL){
//        if( classPathURL==null )throw new IllegalArgumentException( "classPathURL==null" );
//        classpath().add(classPathURL);
//        cl = null;
//    }
//
//    private final Callable addClassPathURLFun = new Callable() {
//        @Override
//        public Object call(Object... arguments) {
//            if( arguments!=null ){
//                for( Object arg : arguments ){
//                    if( arg instanceof String ){
//                        try {
//                            URL url = new URL((String) arg);
//                            addClassPathURL(url);
//                        } catch (MalformedURLException ex) {
//                            throw new RuntimeException(ex);
//                        }
//                    }else if( arg instanceof URL ){
//                        URL url = (URL)arg;
//                        addClassPathURL(url);
//                    }else{
//                        throw new CastError("can't add as url "+arg);
//                    }
//                }
//            }
//            return null;
//        }
//    };
//
//    private ClassLoader cl(){
//        if( cl!=null )return cl;
//
//        Set<URL> cp = classpath();
//        if( cp.isEmpty() )return null;
//
//        cl = new URLClassLoader(classpath().toArray(new URL[]{}), this.getClass().getClassLoader());
//        return cl;
//    }
    
    public ClassLoader getClassLoader(){ return cl; }
    public void setClassLoader( ClassLoader cl ){
        this.cl = cl;
    }

    @Override
    public Object call(Object... arguments) {
        if( arguments==null || arguments.length<1 )return null;
        if( arguments[0]==null )return null;
        String className = arguments[0].toString();
        try {
            ClassLoader _cl = getClassLoader();
            if( _cl==null )_cl = this.getClass().getClassLoader();
            
            Class cls = _cl==null ? Class.forName(className) : Class.forName(className, true, _cl);
            if( arguments.length==1 ){
                try {
                    Object inst = cls.newInstance();
                    return inst;
                } catch (InstantiationException ex) {
                    throw new Error(ex.getMessage(), ex);
                } catch (IllegalAccessException ex) {
                    throw new Error(ex.getMessage(), ex);
                }
            }else{
                Object[] call_args = Arrays.copyOfRange(arguments, 1, arguments.length);
                return CallConstructorVariant.call(cls, call_args);
            }
        } catch (ClassNotFoundException ex) {
            throw new Error(ex.getMessage(), ex);
        }
    }
}
