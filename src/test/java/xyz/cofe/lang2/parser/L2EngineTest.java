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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.Value;
//import lang2.vm.op.VMObject;
import org.junit.Test;
import xyz.cofe.log.CLILoggers;

/**
 * Тестирование парсера
 * @author gocha
 */
public class L2EngineTest extends BasicParserTest
{
    protected L2Engine engine = new L2Engine();
    
    @Override
    protected Value parseExpressions(String source) {
        if (source== null) {            
            throw new IllegalArgumentException("source==null");
        }
        
        engine.setMemory(memory);
        return engine.parse(source);
//        return super.parseExpressions(source);
    }
    
    @Test
    public void testIFVarNotExists(){
        log.println("testIFVarNotExists");
        log.println("===========================");
        
        memory.clear();
//        assertTrue(assertParseExpressions(null, null));
        assertParseExpressions(
                "if( notExistsVar ){"
                + "1;"
                + "}else{"
                + "2;"
                + "}"
                , 
                "2");
    }

    @Test
    public void listExtender(){
        log.println("listExtender");
        log.println("===========================");
        
        String code = "var l = [ \"a\", \"b\", \"c\" ];"
                + "l.size;";
        assertParseExpressions(code, "3");
        
        ArrayList<String> l2 = new ArrayList<String>();
        l2.add( "123" );
        l2.add( "234" );
        memory.put("l2", l2);
        assertParseExpressions("l2.size", "2");
    }

    @Test
    public void mapExtender(){
        log.println("mapExtender");
        log.println("===========================");
        
        String code = "var m = { a:1, b:2, c:3 };"
                + "m.size;";
        assertParseExpressions(code, "3");
        
        code = "var m = { a:1, b:2, c:3 };"
                + "var k = m.keys;"
                + "k.size;";
        assertParseExpressions(code, "3");

        code = "var m = { a:1, b:2, c:3 };"
                + "var k = m.keys;"
                + "k[0];";
        assertParseExpressions(code, "a");

        code = "var m = { a:1, b:2, c:3 };"
                + "var k = m.keys;"
                + "k[1];";
        assertParseExpressions(code, "b");

        code = "var m = { a:1, b:2, c:3 };"
                + "var k = m.keys;"
                + "k[2];";
        assertParseExpressions(code, "c");
    }
    
    @Test
    public void castTest1(){
        log.println("castTest1");
        log.println("===========================");
        
        String code = "var l = [ \"a\", \"b\", \"c\" ];"
                + "l.remove( 0 );"
                + "l.size;";
        CLILoggers logger = new CLILoggers();
        logger.parseXmlConfig(L2EngineTest.class.getResource("logconf1.xml"));
        assertParseExpressions(code, "2");
        if( logger.getNamedHandler().containsKey("console") ){
            logger.removeHandlerFromRoot(logger.getNamedHandler().get("console"));
        }
    }

    public static class Fun implements Callable{
        @Override
        public Object call(Object... arguments) {
            return "hello";
        }

        public String getComment(){
            return "return hello";
        }
        
//        public void setComment(String t){}
    }

    public static Callable helloFun = new Fun();
    @Test
    public void comments(){
        log.println("Коментарии к функции");
        log.println("===========================");
        
        String code = 
                "var testFun = /** testFun comment */ "
                + "function( a, b ){ "
                + "a + b "
                + "}; "
                + "testFun.comment";
        
        assertParseExpressions(code, "testFun comment");
        
        code = "var sumFun = /** sumFun comment */ "
                + "function( a, b ){ "
                + "a + b "
                + "}; "
                + "/** mulFun comment */ "
                + "var mulFun = function( a, b ){ a * b }; "
                + "sumFun.comment + mulFun.comment";

        assertParseExpressions(code, "sumFun commentmulFun comment");
        
        memory.put("hello", helloFun);
        code = "hello.comment";
        assertParseExpressions(code, "return hello");
    }
}
