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
package xyz.cofe.lang2.cli;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.Iterators;
import xyz.cofe.collection.Predicate;
import xyz.cofe.common.JavaClassName;
import xyz.cofe.common.Reciver;
import xyz.cofe.files.FileUtil;
import xyz.cofe.config.SimpleConfig;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class DynamicClassLoader extends URLClassLoader {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.SEVERE, null, ex);
    }

    private static void logEnter(String method,Object ... args){
        Logger.getLogger(DynamicClassLoader.class.getName()).
            entering(DynamicClassLoader.class.getName(), method, args);
    }

    private static void logExit(String method){
        Logger.getLogger(DynamicClassLoader.class.getName()).
            exiting(DynamicClassLoader.class.getName(), method);
    }
    
    private static void logExit(String method,Object res){
        Logger.getLogger(DynamicClassLoader.class.getName()).
            exiting(DynamicClassLoader.class.getName(), method, res);
    }
    //</editor-fold>
    
    private SimpleConfig conf = null;
    
    //<editor-fold defaultstate="collapsed" desc="Конструкторы">
    public DynamicClassLoader(){
        super(new URL[]{});
        init(new SimpleConfig());
    }
    
    public DynamicClassLoader(SimpleConfig conf){
        super(new URL[]{});
        init(conf==null ? new SimpleConfig() : conf);
    }
    
    public DynamicClassLoader(ClassLoader parent) {
        super(new URL[]{},parent);
        init(new SimpleConfig());
    }
    
    public DynamicClassLoader(URL[] urls,ClassLoader parent) {
        super(urls,parent);
        init(new SimpleConfig());
    }
    
    public DynamicClassLoader(URL[] urls,ClassLoader parent,URLStreamHandlerFactory f) {
        super(urls,parent,f);
        init(new SimpleConfig());
    }
    
    public DynamicClassLoader(ClassLoader parent,SimpleConfig conf) {
        super(new URL[]{},parent);
        init(conf==null ? new SimpleConfig() : conf);
    }
    
    public DynamicClassLoader(URL[] urls,ClassLoader parent,SimpleConfig conf) {
        super(urls,parent);
        init(conf==null ? new SimpleConfig() : conf);
    }
    
    public DynamicClassLoader(URL[] urls,ClassLoader parent,URLStreamHandlerFactory f,SimpleConfig conf) {
        super(urls,parent,f);
        init(conf==null ? new SimpleConfig() : conf);
    }
    //</editor-fold>
    
    private synchronized void init(SimpleConfig conf){
        this.conf = conf==null ? new SimpleConfig() : conf;
        
        if( conf.get("addDefaultPatterns",true,
                "Добавить шаблоны поиска jar файлов по умолчанию\n"
            +   "Пример шаблона:\n"
            +   "  {script.dir}/lib/**/*.jar\n"
            +   "\n"
            +   "Где:\n"
            +   "  {script.dir} - каталог со скриптом\n"
            +   "  lib          - подкаталог\n"
            +   "  **           - искать рекурсовно\n"
            +   "  *.jar        - маска файла"
        ) ){
            String fs = conf.get("fileSplitter","/");
            
            SimpleConfig scPatterns = conf.subset("patterns.", true, true);
            scPatterns.get("lib_jars","{script.dir}"+fs+"lib"+fs+"**"+fs+"*.jar");
        }
    }
    
    public static Iterable<File> createJarSearch( File root ){
        Iterable<File> iP = FileUtil.Iterators.walk(root);
        Predicate<File> filter = FileUtil.Predicates.nameMask("*.jar", false, true);
        Iterable<File> iFiltered = FileUtil.Iterators.filter(iP, filter);
        return iFiltered;
    }
    
    private Iterable<File> createJarSearch_forScriptFile( File scriptFile ){
        Map<String,Object> ctx = new HashMap<String, Object>();
        ctx.put("script", scriptFile);
        
        FilePattern fpttrn = new FilePattern();
        
        String splitter = conf.get("fileSplitter","/");
        
        Iterable<File> itr = null;
        SimpleConfig scPatterns = conf.subset("patterns.", false, true);
        for( String ptrn : scPatterns.values() ){
            Iterable<File> itr_t =
                fpttrn.createQuery(ptrn, splitter, ctx);
            if( itr==null ){
                itr = itr_t;
            }else{
                itr = FileUtil.Iterators.join(itr,itr_t);
            }
        }
        
        return itr;
    }
    
    //<editor-fold defaultstate="collapsed" desc="notify reciver функции">
    public synchronized void evalScriptFile( File file ){
        if( file==null )return;
        Iterable<File> search = createJarSearch_forScriptFile(file);
        Iterable<File> exists = getJars();
        if( exists==null ){
            setJars(search);
        }else{
            Iterable<File> p = FileUtil.Iterators.join(exists, search);
            setJars(p);
        }
        setNeedRescan();
    }
    
    public synchronized void evalScriptFile( URL file ){
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Область поиска jar">
    protected Iterable<File> jars = null;
    public synchronized Iterable<File> getJars() { return jars; }
    public synchronized void setJars(Iterable<File> jars) { this.jars = jars; }
    
    protected synchronized Iterable<File> jars(){
        Iterable<File> j = getJars();
        if( j==null )return Iterators.empty();
        return j;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="class cache map">
    protected Map<JavaClassName,Class> _classCache = null;
    protected Map<JavaClassName,Class> classCache(){
        if( _classCache!=null )return _classCache;
        _classCache = new TreeMap<JavaClassName, Class>();
        return _classCache;
    }
    
    public Map<JavaClassName,Class> getClassCacheMap(){
        return classCache();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="bin class cache map">
    protected Map<JavaClassName,BinData> _classBinCache = null;
    protected Map<JavaClassName,BinData> classBinCache(){
        if( _classBinCache!=null )return _classBinCache;
        _classBinCache = new TreeMap<JavaClassName, BinData>();
        return _classBinCache;
    }
    //</editor-fold>
    
    protected void putClassCacheFromBinCache( JavaClassName className, Class cls, JavaClassName binKey, BinData binData ){
        logFiner( "put class {0} in classCahce from binCache", cls );
        classCache().put(className, cls);
        
//        logFiner( "remove key {0} from binCache", binKey );
//        classBinCache().remove(binKey);
    }
    
    public static class BinData {
        protected JavaClassName name = null;
        protected byte[] data = null;
        
        public BinData(String name,byte[] data){
            if( name==null )throw new IllegalArgumentException( "name==null" );
            this.name = new JavaClassName(name);
            this.data = data;
        }

        public JavaClassName getName() {
            return name;
        }

        public byte[] getData() {
            return data;
        }
    }
    
    protected void scanClass( JarFile file, JarEntry jent, Reciver<BinData> reciver ) throws IOException{
        String name = jent.getName();
        
        if( !name.toLowerCase().endsWith(".class") ){
            return;
        }
        
        String className = name.substring(0, name.length()-6);
        className = className.replace("/", ".");
        
        byte[] data = new byte[(int)jent.getSize()];
        InputStream inStr = file.getInputStream(jent);
        inStr.read(data);
        
        reciver.recive(new BinData(name, data));
    }
    
    protected List<BinData> scanJarForClasses( File file ){
        final List<BinData> javaClasses = new ArrayList<BinData>();
        
        logEnter("scanJar( {0} )", file);
        try {
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while( entries.hasMoreElements() ){
                JarEntry jarEntry = entries.nextElement();
                logFine( "entry {0}", jarEntry.getName() );
                
                scanClass(jarFile, jarEntry, new Reciver<BinData>() {
                    @Override
                    public void recive(BinData obj) {
                        javaClasses.add(obj);
                    }
                });
            }
            jarFile.close();
        } catch (IOException ex) {
            logException(ex);
        }
        logExit("scanJar");
        
        return javaClasses;
    }
    
    protected Set<File> added = new HashSet<File>();
    
    protected void defineClasses( List<BinData> classes ){
        for( BinData v : classes ){
            JavaClassName k = v.getName();
            if( classBinCache().containsKey(k) ){
                classBinCache().put(k, v);
            }
        }
    }
    
    protected synchronized int scan(){
        int co = 0;
        List<BinData> javaClassess = new ArrayList<BinData>();
        for( File f : jars() ){
            if( f==null )continue;
            if( f.exists() && !added.contains(f) ){
                try {
                    addURL(f.toURI().toURL());
                    added.add( f );
                    List<BinData> clsss = scanJarForClasses(f);
                    javaClassess.addAll(clsss);
                    co++;
                    logFine("added classpath {0}", f);
                } catch (MalformedURLException ex) {
                    logException(ex);
                }
            }
        }
        defineClasses(javaClassess);
        return co;
    }
    
    protected boolean scanned = false;
    
    public synchronized void setNeedRescan(){
        scanned = false;
    }
    
    @Override
    public synchronized Class<?> loadClass(String fullClassName) throws ClassNotFoundException {
        if( fullClassName==null ){
            throw new ClassNotFoundException("class null not found");
        }

        if( !scanned ){
            scanned = true;
            logFine( "scan jars" );
            scan();
        }
        
        JavaClassName javaClassName = new JavaClassName(fullClassName);
        
        logFine( "lookup in classCahce for class {0}", fullClassName );
        Class ccls = classCache().get(javaClassName);
        if( ccls!=null ){
            logFiner( "loaded class {0} from classCahce",ccls );
            return ccls;
        }
        
        logFine( "lookup in binCahce for class {0}", fullClassName );
        BinData d = classBinCache().get(javaClassName);
        if( d!=null ){
            Class c = defineClass(fullClassName, d.getData(), 0, d.getData().length);
            if( c!=null ){
                putClassCacheFromBinCache( javaClassName, c, javaClassName, d );
                logFiner( "loaded class {0} from binCahce",c );
                return c;
            }
        }
        
        logFine("load class ({0}) from super class loader", fullClassName);
        Class c = super.loadClass(fullClassName);
        if( c!=null ){
            logFiner("loaded class ({0}) from super class loader", c);
            return c;
        }
//        
        throw new ClassNotFoundException("class "+fullClassName+" not found");
    }
}
