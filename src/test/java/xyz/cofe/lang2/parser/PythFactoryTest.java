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
import junit.framework.Assert;
import xyz.cofe.lang2.vm.Value;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class PythFactoryTest extends BasicParserTest {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(PythFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
    }

    //</editor-fold>
    
    @Override
    protected Factory createCustomFactory() {
//        L2Factory l2Fact = new PythFactory(memory);
//        OptL2Factory optL2Fact = new OptL2Factory(l2Fact, parserOpts);
        OptL2Factory optL2Fact = new OptL2Factory(memory, parserOpts);
        return optL2Fact;
    }
    
    @Test
    public void test01() {
        System.out.println("test01");
        System.out.println("======");
        
        String exp = "var obj = {\n"
            + " a : 1,\n"
                + " fn : function( self, a ){\n"
                + " self.a + a"
                + " }\n"
            + "};\n"
            + "obj.fn( 2 )";
        
        Value v = parseExpressions(exp);
        assertTrue(v!=null);
        
        System.out.println("tree");
        System.out.println("----");
        OpTreeWalkerTest.print(log, v);
        
        System.out.println("");
        System.out.println("eval");
        System.out.println("----");
        Object r = 
            v.evaluate();
        System.out.println(r);
        
        assertTrue(r!=null);
        assertTrue(r instanceof Number);
        assertTrue(((Number)r).intValue() == 3);
    }

    @Test
    @Override
    public void objectCreate(){
        log.println("Тестирование создания объектов");
        log.println("==============================");

        String code = "";
        code = "{ a : 1, b : \"aa\", c : true, d : function ( a, b ) { a + b } }";

        Value _objValue = parseExpressions(code);
        Object objValue = _objValue.evaluate();
        if( objValue==null )fail("value is null");
        if( !(objValue instanceof Map) )fail("value is not object");

        code = "var obj={ a : 1+2, b : \"aa\", c : true, d : function ( a, b ) { a + b } };" +
            "obj.a";
        assertParseExpressions(code, "3");

        code = "var obj={ a : 1+2, b : \"aa\", c : true, d : function ( a, b ) { a + b } };" +
                "obj.b";
        assertParseExpressions(code, "aa");

        code = "var obj={ a : 1+2, b : \"aa\", c : true, d : function ( a, b ) { a + b } };" +
               "obj.c";
        assertParseExpressions(code, "true");

        code = "var obj={ a : 1+2, b : \"aa\", c : true, d : function ( self, a, b ) { a + b } };" +
               "obj.d( 2, 3 )";
        assertParseExpressions(code, "5");

        code = "var obj={ a : \"str\", b : function ( self, c ) { self.a + c } };" +
                "obj.b( \"ing\" )";
        assertParseExpressions(code, "string");
    }
}
