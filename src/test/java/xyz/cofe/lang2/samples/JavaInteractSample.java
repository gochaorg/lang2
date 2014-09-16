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

package xyz.cofe.lang2.samples;

import java.lang.reflect.Array;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.lib.CLIFunctions;

/**
 *
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class JavaInteractSample {
    
    public JavaInteractSample() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void sampleInteract(){
        System.out.println("sampleInteract");
        System.out.println("==============");
        
        L2Engine scriptEngine = new L2Engine();
        
        String code = "1 + 2";
        Object result = scriptEngine.eval(code);
        
        System.out.println("source code:");
        System.out.println(code);
        System.out.println("result:");
        System.out.println(result);
    }
    
    @Test
    public void passingVariables(){
        System.out.println("passingVariables");
        System.out.println("================");
        
        L2Engine scriptEngine = new L2Engine();
        
        scriptEngine.getMemory().put("name", "John");
        scriptEngine.getMemory().put("summ", 1200);

        String code = "\"Hello \" + name + \" you win \" + summ + \"$\"";
        Object result = scriptEngine.eval(code);

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("result:");
        System.out.println(result);
    }

    @Test
    public void passingFunctions(){
        System.out.println("passingFunctions");
        System.out.println("================");
        
        L2Engine scriptEngine = new L2Engine();
        
        Callable sinFn = new Callable() {
            @Override
            public Object call(Object... arguments) {
                if( arguments!=null 
                    && arguments.length>0 
                    && (arguments[0] instanceof Number) ){
                    double n = ((Number)arguments[0]).doubleValue();
                    return Math.sin(n);
                }
                return null;
            }
        };
        
        scriptEngine.getMemory().put("num1", 0.5);
        scriptEngine.getMemory().put("sin", sinFn);

        String code = "sin( num1 )";
        Object result = scriptEngine.eval(code);

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("result:");
        System.out.println(result);
    }
    
    public static class PassObject {
        public int numField = 100;
        
        protected String strProperty = "some text";

        public String getStrProperty() {
            return strProperty;
        }

        public void setStrProperty(String strProperty) {
            this.strProperty = strProperty;
        }
        
        public String concat( String arg1, String arg2 ){
            StringBuilder strBldr = new StringBuilder();
            strBldr.append(arg1);
            strBldr.append(arg2);
            return strBldr.toString();
        }
    }

    @Test
    public void passingObjects(){
        System.out.println("passingObjects");
        System.out.println("==============");
        
        L2Engine scriptEngine = new L2Engine();

        PassObject passObj = new PassObject();
        scriptEngine.getMemory().put("inobj", passObj);

        String code = "inobj.concat( inobj.numField.toString(), inobj.strProperty )";
        Object result = scriptEngine.eval(code);

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("result:");
        System.out.println(result);
    }
    
    @Test
    public void cliFunctions() {
        System.out.println("cliFunctions");
        System.out.println("============");
        
        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String code = "println( \"test println function\" )";
        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        scriptEngine.eval(code);
    }

    @Test
    public void createJavaObj() {
        System.out.println("createJavaObj");
        System.out.println("=============");
        
        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String code = 
                "var file = java( \"java.io.File\", \".\" );\n"
            +   "println( file.getAbsolutePath() );\n"
            +   "file";
        
        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        Object result = scriptEngine.eval(code);
        
        System.out.println("result:");
        System.out.println(result);
        System.out.println("result instanceof java.io.File = "+(result instanceof java.io.File));
    }
    
    @Test
    public void getJavaType(){
        System.out.println("getJavaType");
        System.out.println("===========");

        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String code = 
                "var clzfile = java.type( \"java.io.File\", \".\" );\n"
            +   "println( clzfile );\n"
            +   "clzfile";

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        Object result = scriptEngine.eval(code);
        
        System.out.println("result:");
        System.out.println(result);
        
        if( result instanceof Class ){
            Class cls = (Class)result;
            System.out.println("class name: "+cls.getName());
        }
    }
    
    @Test
    public void getJavaArray(){
        System.out.println("getJavaArray");
        System.out.println("============");

        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String code = 
                "var arr = java.array( \"java.lang.String\", 3 );\n"
            +   "arr[0] = \"first\";\n"
            +   "arr[1] = \"second\";\n"
            +   "arr[2] = \"thrid\";\n"
            +   "println( arr );\n"
            +   "arr";

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        Object result = scriptEngine.eval(code);
        
        System.out.println("result:");
        System.out.println(result);
        
        if( result!=null && result.getClass().isArray() ){
            int arrLen = Array.getLength(result);
            System.out.println("array length: "+arrLen);
        }
    }
    
    @Test
    public void implementInterface(){
        System.out.println("implementInterface");
        System.out.println("==================");

        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String code = 
                "var obj = {"
            +   "  a : \"message\", \n"
            +   "  run : function( self ){\n"
            +   "    println( self.a )\n"
            +   "  }\n"
            +   "};\n"
            +   "java.implement( java.type( \"java.lang.Runnable\" ), obj )";

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        Object result = scriptEngine.eval(code);
        
        System.out.println("result:");
        System.out.println(result);
        
        if( result!=null && result instanceof Runnable ){
            Runnable run = (Runnable)result;
            
            System.out.println("run:");
            run.run();
        }
    }
    
    @Test
    public void implementInterface2(){
        System.out.println("implementInterface2");
        System.out.println("===================");

        L2Engine scriptEngine = new L2Engine();
        
        CLIFunctions cliFunctions = new CLIFunctions(scriptEngine);
        scriptEngine.getMemory().putAll(cliFunctions.getMemObjects());
        
        String interfaceName = SumItf.class.getName();
        
        String code = 
                "var obj = {"
            +   "  summa : function( self, a, b ){\n"
            +   "    a + b\n"
            +   "  }\n"
            +   "};\n"
            +   "java.implement( java.type( \""+interfaceName+"\" ), obj )";

        System.out.println("source code:");
        System.out.println(code);
        System.out.println("output:");
        
        Object result = scriptEngine.eval(code);
        
        System.out.println("result:");
        System.out.println(result);
        
        if( result!=null && result instanceof SumItf ){
            SumItf sum = (SumItf)result;
            
            System.out.println("summa( 3, 4 ):");
            System.out.println(sum.summa(3, 4));
        }
    }
}
