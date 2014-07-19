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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Predicate;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.text.Text;

/**
 *
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class CliFunctionTest {
    
    public CliFunctionTest() {
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
    public void javaFunctionTest01(){
        System.out.println("javaFunctionTest01");
        System.out.println("==================");
        
        // Создание l2engine
        L2Engine engine = new L2Engine();
        
        // Регистрация стандартных функций
        CLIFunctions cliFunction = new CLIFunctions(engine);
        engine.getMemory().putAll(cliFunction.getMemObjects());
        
        String typeFunction = cliFunction.getJavaFunction().getJavaTypeFunctionName();
        String implFunction = cliFunction.getJavaFunction().getImplementFunctionName();
        
        // Имплементация - Convertor
        String convertorClassName = Convertor.class.getName();
        String script = 
            "var add_implConv = {\n"+
            "  convert : function( self, value ){\n"+ 
            "    value + value\n"+ 
            "  }\n"+
            "};\n"+
            "java."+implFunction+"( java."+typeFunction+"(\""+convertorClassName+"\"), add_implConv )";

        System.out.println("Скрипт convertor:");
        System.out.println(Text.indent(script, "SCRIPT> "));
        Object scriptRes = engine.eval(script);
        assertTrue(scriptRes!=null);
        assertTrue(scriptRes instanceof Convertor);
        
        String convVal = "123";
        Object convRes = ((Convertor)scriptRes).convert(convVal);
        assertTrue(convRes!=null);
        assertTrue(convRes.toString().equals(convVal+convVal));
        
        System.out.println("результат конвертации: "+convRes);
        
        // Имплементация - Predicate - even
        String predicateClassName = Predicate.class.getName();
        script = 
                "var filterEven = {\n"
            +   "  validate : function( self, value ) {\n"
            +   "    value % 2 == 0\n"
            +   "  }\n"
            +   "};\n"
            +   "java."+implFunction+"( java."+typeFunction+"(\""+predicateClassName+"\"), filterEven )";
        
        System.out.println("Скрипт Predicate even:");
        System.out.println(Text.indent(script, "SCRIPT> "));
        scriptRes = engine.eval(script);
        assertTrue(scriptRes!=null);
        assertTrue(scriptRes instanceof Predicate);
        boolean predRes1 = ((Predicate)scriptRes).validate(10);
        boolean predRes2 = ((Predicate)scriptRes).validate(11);
        boolean predRes3 = ((Predicate)scriptRes).validate(0);
        
        assertTrue( predRes1 );
        System.out.println("проверка 10 = "+predRes1);
        
        assertTrue( !predRes2 );
        System.out.println("проверка 11 = "+predRes2);
        
        assertTrue( predRes3 );
        System.out.println("проверка 0 = "+predRes3);

        // Имплементация - Predicate - odd
        script = 
                "var filterOdd = {\n"
            +   "  validate : function( self, value ) {\n"
            +   "    value % 2 != 0\n"
            +   "  }\n"
            +   "};\n"
            +   "java."+implFunction+"( java."+typeFunction+"(\""+predicateClassName+"\"), filterOdd )";
        
        System.out.println("Скрипт Predicate odd:");
        System.out.println(Text.indent(script, "SCRIPT> "));
        scriptRes = engine.eval(script);
        assertTrue(scriptRes!=null);
        assertTrue(scriptRes instanceof Predicate);
        predRes1 = ((Predicate)scriptRes).validate(10);
        predRes2 = ((Predicate)scriptRes).validate(11);
        predRes3 = ((Predicate)scriptRes).validate(0);
        
        assertTrue( !predRes1 );
        System.out.println("проверка 10 = "+predRes1);
        
        assertTrue( predRes2 );
        System.out.println("проверка 11 = "+predRes2);
        
        assertTrue( !predRes3 );
        System.out.println("проверка 0 = "+predRes3);
    }
}
