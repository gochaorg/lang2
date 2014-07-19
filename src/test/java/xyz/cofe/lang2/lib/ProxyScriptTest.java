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

import xyz.cofe.lang2.lib.JavaTypeFunction;
import xyz.cofe.lang2.lib.ImplementFunction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import xyz.cofe.collection.Convertor;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.text.Text;

/**
 *
 * @author Kamnev Georgiy (nt.gocha@gmail.com)
 */
public class ProxyScriptTest {
    
    public ProxyScriptTest() {
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
    public void test01()
    {
        System.out.println("proxyscript test01");
        System.out.println("==================");
        
        JavaTypeFunction javaTypeFunc = new JavaTypeFunction();
        ImplementFunction implFunc = new ImplementFunction();
        
        L2Engine engine = new L2Engine();
        
        System.out.println("Добавление функций javaType, implement");
        engine.getMemory().put("javaType", javaTypeFunc);
        engine.getMemory().put("implement", implFunc);
        
        String convertorClassName = Convertor.class.getName();
        String script = 
            "var add_implConv = {\n"+
            "convert : function( self, value ){\n"+ 
            "value + value\n"+ 
            "}\n"+
            "};\n"+
            "implement( javaType(\""+convertorClassName+"\"), add_implConv )";
        System.out.println("Скрипт:");
        System.out.println(Text.indent(script, "SCRIPT>"));
        
        Object scriptRes = engine.eval(script);
        System.out.println("Результат:");
        System.out.println(scriptRes);
        
        assertTrue(scriptRes!=null);
        assertTrue(scriptRes instanceof Convertor);
        
        System.out.println("Является instanceof Convertor");
        System.out.println("Проверка Convertor.convert( \"Abc\" ) должно быть \"AbcAbc\"");
        
        Object convRes = ((Convertor)scriptRes).convert("Abc");
        assertTrue(convRes!=null);
        assertTrue(convRes instanceof String);
        assertTrue(((String)convRes).equals("AbcAbc"));
        
        System.out.println("Результат "+convRes);
    }
}
