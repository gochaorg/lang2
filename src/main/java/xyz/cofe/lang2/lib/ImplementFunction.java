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


import xyz.cofe.common.JavaClassName;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.lib.InterfaceProxyGen;

/**
 *
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class ImplementFunction 
implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ImplementFunction.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private InterfaceProxyGen _proxySourceGen = null;
    private InterfaceProxyGen proxySourceGen(){
        if( _proxySourceGen==null ){
            _proxySourceGen = new InterfaceProxyGen();
        }
        return _proxySourceGen;
    }
    
    /**
     * Возвращает имя Proxy класса
     * @param interfaceClass Интерфейс
     * @return имя класса
     */
    private JavaClassName createProxyClassName( Class interfaceClass ){
        String clssName = interfaceClass.getName().replace(".", "_");
        String pkgName = "xyz.cofe.lang2.lib.gen";
        return new JavaClassName(pkgName, clssName);
    }
    
    private SimpleCompiler _simpleCompiler = null;
    private SimpleCompiler simpleCompiler(){
        if( _simpleCompiler!=null )return _simpleCompiler;
        _simpleCompiler = new SimpleCompiler();
        
        // ClassLoader для компилятора, что бы он разрулил имеющиеся классы
        ClassLoader cl = getCompileTimeClassLoader();
        _simpleCompiler.setParentClassLoader(cl);

        return _simpleCompiler;
    }
    
    //<editor-fold defaultstate="collapsed" desc="compileTimeClassLoader">
    private ClassLoader compileTimeClassLoader = null;
    
    public ClassLoader getCompileTimeClassLoader() {
        if( compileTimeClassLoader!=null )return compileTimeClassLoader;
        compileTimeClassLoader = this.getClass().getClassLoader();
        return compileTimeClassLoader;
    }
    
    public void setCompileTimeClassLoader(ClassLoader compileTimeClassLoader) {
        this.compileTimeClassLoader = compileTimeClassLoader;
    }
    //</editor-fold>
    
    private Map<JavaClassName,Class> classCache = new TreeMap<JavaClassName, Class>();

    public synchronized Map<JavaClassName, Class> getClassCache() {
        if( classCache!=null )return classCache;
        classCache = new TreeMap<JavaClassName, Class>();
        return classCache;
    }

    public synchronized void setClassCache(Map<JavaClassName, Class> classCache) {
        this.classCache = classCache;
    }
    
    private synchronized Class compileProxyClass( JavaClassName proxyClassName, Class interfaceClass ){
        if( proxyClassName==null )throw new IllegalArgumentException( "proxyClassName==null" );
        if( interfaceClass==null )throw new IllegalArgumentException( "interfaceClass==null" );
        
        // Генерация исходника
        InterfaceProxyGen pg = proxySourceGen();
        pg.setInterfaceClass(interfaceClass);
        pg.setJavaClassName(proxyClassName);
        String source = pg.generate();
        
        logFine("Сгенерирован исходник proxy class {0} для интерфейса {1}, исходник:\n{2}", proxyClassName, interfaceClass, source);

        // Компиляция исходника
        SimpleCompiler compiler = simpleCompiler();

        try {
            // Передача исходника компилятору
            compiler.cook(source);
        } catch (CompileException ex) {
            logSevere(
                "Ошибка компиляции исходника:\n"+
                    "Ошибка: {0}\n"+
                    "Ошибка toString: {1}\n"+
                    "Исходник:\n"+
                    "{2}", 
                ex.getMessage(), ex.toString(), source, ex);
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        }

        ClassLoader compilerClassLoader = compiler.getClassLoader();

        try {
            // Получение class сгенерированого исходника
            Class proxyClass = compilerClassLoader.loadClass(proxyClassName.getFullClassName());
            return proxyClass;
        } catch (ClassNotFoundException ex) {
            logSevere(
                "Ошибка компиляции исходника - не найден класс:\n"+
                    "Ошибка: {0}\n"+
                    "Ошибка toString: {1}\n"+
                    "Исходник:\n"+
                    "{2}", 
                ex.getMessage(), ex.toString(), source, ex);
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.ClassNotFoundError(ex.getMessage(), ex);
        }
    }
    
    private synchronized Class getProxyClass( JavaClassName proxyClassName, Class interfaceClass ){
        if( proxyClassName==null )throw new IllegalArgumentException( "proxyClassName==null" );
        if( interfaceClass==null )throw new IllegalArgumentException( "interfaceClass==null" );
        
        logFine("Проверка наличия скопилированного proxy class {0} для интерфейса {1}", proxyClassName, interfaceClass);
        // Проверка кэша
        Class cls = getClassCache().get(proxyClassName);
        if( cls!=null ){
            logFine("Найден в кэше proxy class {0} для интерфейса {1}", proxyClassName, interfaceClass);
            return cls;
        }
        
        // Компиляция
        logFine("Компиляция proxy class {0} для интерфейса {1}", proxyClassName, interfaceClass);
        cls = compileProxyClass(proxyClassName, interfaceClass);
        
        // Кэширование
        logFine("Кэширование proxy class {0} для интерфейса {1}", proxyClassName, interfaceClass);
        if( cls!=null )getClassCache().put(proxyClassName, cls);
        
        return cls;
    }
    
    @Override
    public Object call(Object... arguments) {
        // Проверка входных параметров
        if( arguments==null || arguments.length<2 )return null;
        Object arg0 = arguments[0];
        Object arg1 = arguments[1];
        
        if( arg0 == null || arg1==null )return null;
        if( !(arg0 instanceof Class) )return null;
        if( !(arg1 instanceof java.util.Map) )return null;
        
        // Получение параметров
        Class interfaceClass = (Class)arg0;
        java.util.Map implementScriptObject = (java.util.Map)arg1;
        
        // Генерация исходника
        JavaClassName proxyClassName = createProxyClassName(interfaceClass);
        
        try {
            // Получение class сгенерированого исходника
            Class proxyClass = getProxyClass(proxyClassName, interfaceClass);
            
            // Создание proxy объекта
            logFine( "Создание proxy {0} для интерфейса {1}",proxyClassName, interfaceClass );
            Constructor constr = proxyClass.getConstructor( java.util.Map.class );
            
            Object proxy = constr.newInstance(implementScriptObject);
            return proxy;
        } catch (NoSuchMethodException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        } catch (SecurityException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        } catch (InstantiationException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        } catch (InvocationTargetException ex) {
            logException(ex);
            throw new xyz.cofe.lang2.vm.err.CompileException(ex.getMessage(), ex);
        }
    }
}
