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
package xyz.cofe.lang2.vm.jre;


import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.Iterators;
import xyz.cofe.jdk.ByteCode;
import xyz.cofe.jdk.JDKCompiler;
import xyz.cofe.jdk.SourceCode;
import xyz.cofe.jvm.MemoryJavaSource;
import xyz.cofe.text.IndentStackWriter;
import xyz.cofe.text.Text;
import xyz.cofe.types.SimpleTypes;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class InterfaceProxyGen {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(InterfaceProxyGen.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    protected Class interfaceClass = null;
    protected String packageName = null;
    protected String className = null;

    public Class getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
//    protected ClassLoader compileClassLoader = null;
//
//    public ClassLoader getCompileClassLoader() {
//        return compileClassLoader;
//    }
//
//    public synchronized void setCompileClassLoader(ClassLoader compileClassLoader) {
//        this.compileClassLoader = compileClassLoader;
////        this.compiler = null;
//    }
    
    public synchronized String generate(){
        if( interfaceClass==null )throw new IllegalArgumentException( "interfaceClass==null" );
        if( packageName==null )throw new IllegalArgumentException( "packageName==null" );
        if( className==null )throw new IllegalArgumentException( "className==null" );
        
        StringWriter strw = new StringWriter();
        IndentStackWriter out = new IndentStackWriter(strw);
        
        generate(out);
        
        out.flush();
        return strw.toString();
    }
    
    public static class Param{
        public String name;
        public Class type;
    }
    
    private String ownerType = null;
    private String ownerVar = null;
    
    private synchronized void generate( IndentStackWriter out ){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        out.templateln("/* Автоматически сгенерировано {0}", sdf.format(date));
        out.templateln("   Генератор {0}", this.getClass().getName());
        out.templateln("   Генерация реализации интерфейса {0}", interfaceClass.getName());
        out.println("*/");
        out.println("");

        if( packageName!=null && packageName.length()>0 ){
            out.templateln("package {0};", packageName);
            out.println("");
        }
        
        out.templateln("public class {0} implements {1} {{", className, interfaceClass.getName());
        out.push();
        
        this.ownerType = "java.util.Map";
        this.ownerVar = "__owner";
        
        out.templateln("private {0} {1};", ownerType, ownerVar);
        
        out.templateln("public {0} ( {1} {2} ) {{", className, ownerType, ownerVar );
        out.templateln("  this.{0} = {1};", ownerVar, ownerVar);
        out.templateln("}");
        out.println("");
        
        generateMethods(out);
            
        String classbody = out.pop().trim();
        out.println(Text.indent(classbody, "  "));
        out.templateln("}");
    }

    private synchronized void generateMethods(IndentStackWriter out) throws SecurityException {
        Method[] methods = interfaceClass.getMethods();
        List<Param> params = new ArrayList<Param>();
        
        int methodIdx = -1;
        
        for( Method m : methods ){
            methodIdx++;
            if( methodIdx>0 ){
                out.println();
            }
            
            Class[] cparams = m.getParameterTypes();
            Class retType = m.getReturnType();

            // declare
            out.push();
            int paramIdx = -1;
            for( Class cparm : cparams ){
                paramIdx++;

                Param param = new Param();
                param.type = cparm;
                param.name = "arg"+paramIdx;
                params.add( param );
                
                if( paramIdx>0 ){
                    out.print(", ");
                }
                out.template("{0} {1}", param.type.getName(), param.name);
            }
            String paramsDef = out.pop();
            
            out.templateln("// метод {0}",m.toString());
            out.template("public {0} {1} ( {2} )", retType.getName(), m.getName(), paramsDef);
            
            // impl
            out.templateln("{{");
            
            out.push();
            if( SimpleTypes.isVoid(retType) ){
                generateImpl(out, m, params);
            }else{
                generateImpl(out, m, params, retType);
            }
            String methBody = out.pop().trim();
            if( methBody.length()>0 ){
                out.println( Text.indent(methBody,"  "));
            }
            
            out.templateln("}");
        }
    }
    
    private synchronized void generateImpl( 
        IndentStackWriter out,
        Method m,
        List<Param> params ){
        
        String methodName = m.getName();
        String callableType = "lang2.vm.Callable";
        
        String args = "";
        int argsIdx = -1;
        for( Param p : params ){
            argsIdx++;
            if( argsIdx>0 ) args += ", ";
            args += p.name;
        }

        out.templateln("Object res = null;");
        out.println("");
        out.templateln("if( this.{0} == null ) return;", ownerVar);
        out.println("");
        out.templateln("Object method = {0}.get(\"{1}\");", ownerVar, methodName);
        out.templateln("if( !(method instanceof {0}) ){{",callableType);
        out.templateln("  return;");
        out.templateln("}");
        out.println("");
        out.templateln("{0} call = ({0})method;",callableType);
        out.templateln("res = call.call({0});",args);
//        out.templateln("return;");
    }

    private synchronized void generateImpl( 
        IndentStackWriter out,
        Method m,
        List<Param> params,
        Class retType ){
        
        String methodName = m.getName();
        String callableType = "lang2.vm.Callable";
        String retTypeName = retType.getName();
        
        String args = "";
        int argsIdx = -1;
        for( Param p : params ){
            argsIdx++;
            if( argsIdx>0 ) args += ", ";
            args += p.name;
        }

        out.templateln("Object res = null;");
        out.println("");
        out.templateln("if( this.{0} == null ) return ({1})res;", ownerVar, retTypeName);
        out.println("");
        out.templateln("Object method = {0}.get(\"{1}\");", ownerVar, methodName);
        out.templateln("if( !(method instanceof {0}) ){{",callableType);
        out.templateln("  return ({0})res;",retTypeName);
        out.templateln("}");
        out.println("");
        out.templateln("{0} call = ({0})method;",callableType);
        out.templateln("res = call.call({0});",args);
        out.templateln("return ({0})res;", retTypeName);
    }
    
    public String getFullClassName(){
        String pkg = getPackageName();
        String clsName = getClassName();
        String fullClsName = (pkg!=null && pkg.length()>0) ? Text.trimEnd(pkg, ".") + "." + clsName : clsName;
        return fullClsName;
    }
    
//    public synchronized List<SourceCode> generateSources(){
//        List<SourceCode> sources = new ArrayList<SourceCode>();
//        String src = generate();
//        String cname = getFullClassName();
//        SourceCode sc = new MemoryJavaSource(cname, src);
//        sources.add(sc);
//        return sources;
//    }
//    
//    private org.gocha.jdk.Compiler compiler = null;
//    public org.gocha.jdk.Compiler compiler(){
//        if( compiler!=null )return compiler;
//        if( compileClassLoader!=null ){
//            compiler = new JaninoCompiler(compileClassLoader);
//        }else{
//            compiler = new JaninoCompiler();
//        }
////        compiler = new JDKCompiler();
//        return compiler;
//    }
    
//    public synchronized List<ByteCode> compileSources(){
//        List<ByteCode> res = new ArrayList<ByteCode>();
//        List<SourceCode> sources = generateSources();
//        Iterable<? extends ByteCode> compiled = compiler().compile(sources);
//        if( compiled!=null ){
//            List l = Iterators.asList(compiled);
//            for( Object o : l ){
//                if( o instanceof ByteCode ){
//                    res.add( (ByteCode)o );
//                }
//            }
//        }
//        return res;
//    }
}
