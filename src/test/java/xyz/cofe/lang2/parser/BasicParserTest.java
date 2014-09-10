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
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.Value;
//import lang2.vm.op.VMObject;
import org.junit.Test;

/**
 * Тестирование парсера
 * @author gocha
 */
public class BasicParserTest extends BaseTest
{
    @Test
    public void unaryMinus(){
        log.println("Унарный минус");
        log.println("========================");
        
        assertParseExpressions("-1+1", "0");
    }
    
    /**
     * Проверка multipleExpression, additionExpression
     */
    @Test
    public void multipleAddition(){
        log.println("Умножение, сложение, ...");
        log.println("========================");
        
		assertParseExpressions("(2+2-3)*4/2","2");
		assertParseExpressions("\"abc\" + \"def\"", "abcdef");
        assertParseExpressions("13 % 10","3");
    }
    
    /**
     * Проверка compareExpression
     */
    @Test
    public void compare(){
        log.println("Операции сравнения");
        log.println("========================");
        
		assertParseExpressions("1==1","true");
		assertParseExpressions("2<>1","true");
		assertParseExpressions("2>1","true");
		assertParseExpressions("2<1","false");
		assertParseExpressions("2>=2","true");
		assertParseExpressions("2>=1","true");
		assertParseExpressions("1<=2","true");
		assertParseExpressions("1<=1","true");
		assertParseExpressions("\"abc\" < \"def\"", "true");
		assertParseExpressions("\"abc\" == \"def\"", "false");
    }

    /**
     * andExpression, xorExpression, orExpression
     */
    @Test
    public void andXorOr(){
        log.println("Логические операции");
        log.println("========================");

        assertParseExpressions("true and true","true");
		assertParseExpressions("true and false","false");
		assertParseExpressions("true and not false","true");

		assertParseExpressions("true or true","true");
		assertParseExpressions("true or false","true");

		assertParseExpressions("true xor true","false");
		assertParseExpressions("true xor false","true");
		assertParseExpressions("false xor true","true");
		assertParseExpressions("false xor false","false");
    }

    /**
     * assign
     */
    @Test
    public void assign(){
        log.println("Присваивание");
        log.println("========================");
        
		memory.put("a", (double)1.0);
		memory.put("b", (double)2.0);
		memory.put("c", (double)0.0);
		memory.put("d", (double)0.0);
		assertParseExpressions("d = c = a * 3 + b","5.0");
        Object c = memory.get("c");
        Object d = memory.get("d");
		assertTrue(c!=null);
        assertTrue(d!=null);
        assertTrue(c instanceof Number);
        assertTrue(d instanceof Number);
        assertTrue(((Number)c).equals(5.0d));
        assertTrue(((Number)d).equals(5.0d));
    }

    /**
     * arrays
     */
    @Test
    public void arrays(){
        log.println("Проверка массивов");
        log.println("========================");

        Map mapVar = new HashMap();
		memory.put("map", mapVar);
		mapVar.put("a", "str");
		mapVar.put("b", "ing");
		assertParseExpressions("map.a + map[\"b\"]","string");
		assertParseExpressions("map.c = map.a + map[\"b\"]","string");
        
        assertTrue(mapVar.containsKey("c"));
        Object c = mapVar.get("c");
        
        assertTrue(c!=null);
        assertTrue(c.equals("string"));

        String code = "";
        code = "var lst = [ \"str\", \"ing\" ];" +
                "lst[0] + lst[1]";
        assertParseExpressions(code, "string");

        code = "var lst = [ 1, 2 ];" +
               "lst.length";
        assertParseExpressions(code, "2");
    }
    
    @Test
    public void varDefine(){
        log.println("Определение переменной");
        log.println("========================");
        
		memory.remove("newVar");
		assertParseExpressions("var newVar = 1 + 2","3");
        
        assertTrue(memory.containsKey("newVar"));
        Object newVar = memory.get("newVar");
        
        assertTrue(newVar.equals(3));
    }
    
    @Test
    public void block(){
        log.println("Блок операций");
        log.println("========================");
        
		assertParseExpressions("{1+2; 2+2}", "4");
		assertParseExpressions("{var a=1; {var a=2} a}", "1" );
    }
    
    @Test
    public void _if(){
        log.println("Условный оператор");
        log.println("========================");
        
		assertParseExpressions("1<2 ? \"yes\"", "yes");
		assertParseExpressions("1>2 ? \"yes\" : \"no\"", "no");
    }
    
    @Test
    public void _while(){
        log.println("Цикл");
        log.println("========================");
        
        assertParseExpressions("var i=0; while( i<5 ) i=i+1; i", "5");
    }
    
    public static class PrintFunction implements Callable
    {
        private PrintWriter writer = null;
        
        public PrintFunction(){
            writer = new PrintWriter(System.out);
        }
        
        public PrintFunction(PrintWriter writer){
            if (writer== null) {                
                throw new IllegalArgumentException("writer==null");
            }
            this.writer = writer;
        }
        
        @Override
        public Object call(Object... arguments) {
            if( arguments!=null ){
                for( Object a : arguments ){
                    writer.print( a==null ? "null" : a);
                }
            }else{
                writer.println("null");
            }
            return null;
        }
    }
    
    @Test
    public void callFunction(){
        log.println("Вызов функции");
        log.println("=============");
        
		Callable funSumma = new Callable() {
			@Override
			public Object call(Object... arguments) {
				if( arguments!=null ){
                    double d = 0;
					for( Object a : arguments ){
						if( a==null )continue;
                        if( a instanceof Number ){
                            d += ((Number)a).doubleValue();
                        }
					}
                    return d;
				}else{
					return 0d;
				}
			}
		};

		memory.put("print", new PrintFunction());
        memory.put("summa", funSumma);
		assertParseExpressions( "print( 1+2, \"abc\" )",null );
        assertParseExpressions( "summa( 1, 2, 3 )","6.0" );

        External external = new External();
        memory.put("external", external);
        assertParseExpressions( "external.add( 1, 2 )","3.0" );
        assertParseExpressions( "external.add( \"con\", \"cat\" )","concat" );
        assertParseExpressions( "external.stringFiled","string" );
    }

    
    @Test
    public void callList(){
        log.println("Вызов списка как функции");
        log.println("========================");
        
        assertParseExpressions(
                "var list = [ \"a\", \"b\", \"c\"];\n"
            +   "list( 0 )", 
            "a");

        assertParseExpressions(
                "var list = [ \"a\", \"b\", \"c\"];\n"
            +   "list( 1 )", 
            "b");

        assertParseExpressions(
                "var list = [ [ \"a\", \"a.1\" ], [ \"b\", \"b.1\" ], [ \"c\", \"c.1\" ] ];\n"
            +   "list( 0,1 )", 
            "a.1");

        assertParseExpressions(
                "var list = [ [ \"a\", \"a.1\" ], [ \"b\", \"b.1\" ], [ \"c\", \"c.1\" ] ];\n"
            +   "list( 1,1 )", 
            "b.1");
    }
    
    public static class External
    {
        public double add( Number a, Number b ){
            if( a==null && b==null )return 0;
            if( a!=null && b==null )return a.doubleValue();
            if( a==null && b!=null )return b.doubleValue();
            return a.doubleValue() + b.doubleValue();
        }

        public String add( String a, String b ){
            if( a==null && b==null )return "";
            if( a!=null && b==null )return a;
            if( a==null && b!=null )return b;
            return a + b;
        }

        public Object stringFiled = "string";

        public boolean And( boolean a, boolean b ){ return a & b; }
        public double summa( double a, float b, long c, int d, short e, byte f )
        {
            return a + b + c + d + e + f;
        }
        public String concat( char a, char b ){
            return new String(new char[]{a,b});
        }
    }

    /**
     * Конвертирование в java примитвные типы (boolean, int, ...)
     */
    @Test
    public void convertPrimitiveTypes(){
        log.println("Конвертирование в java примитвные типы (boolean, int, ...)");
        log.println("==========================================================");
        
        External external = new External();
        memory.put("external", external);
        
        assertParseExpressions( "external.And( true, true )","true" );
        assertParseExpressions( "external.summa( 1, 2, 3, 4, 5, 6 )","21.0" );
        assertParseExpressions( "external.concat( \"abc\", \"def\" )","ad" );
    }
    
    /**
     * Тестирование создания функций
     */
    @Test
    public void functionDefine(){
        log.println("Тестирование создания функций");
        log.println("=============================");

        String code = "";
        code = "var f1 = function( a, b ) a+b; f1( 1,2 )";
        assertParseExpressions(code, "3");

        code = "var f2 = function( a ) a > 2 ? recursion( a-1 ) * a : a; f2( 4 )";
        assertParseExpressions(code, "24");
    }
    
    @Test
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
    
    @Test
    public void flow(){
        log.println("Цикл, рекурсия, ...");
        log.println("========================");
        
        String code = "";
        code = "var a=1;" +
                "while( true ){" +
                    "a = a + 1;" +
                    "a > 5 ? break" +
                    "}" +
                "a";
        assertParseExpressions(code, "6");

        code = "var a=1;" +
                "var b=[];" +
                "while( true ){" +
                    "a = a + 1;" +
                    "a > 20 ? break;" +
                    "a > 5 ? continue;" +
                    "b[b.length]=a" +
                    "}" +
                "b";
        assertParseExpressions(code, "[2, 3, 4, 5]");

        code = "var f1 = function( a ){" +
                "a > 5 ? return a + a;" +
                "a < 0 ? return a - a;" +
                "a * a" +
                "}" +
                "f1( 6 ) + f1( -2 ) + f1( 1 )";
        assertParseExpressions(code, "13");
    }
    
    @Test
    public void tryCatch(){
        log.println("Тестирование try / catch");
        log.println("========================");
        
        Callable genException = new Callable() {
            @Override
            public Object call(Object... arguments) {
                if( arguments!=null && arguments.length>0 ){
                    Object arg = arguments[0];
                    throw new Error(arg==null ? "null" : arg.toString());
                }
                throw new Error("Пользовательское исключение");
            }
        };
        memory.put("error", genException);
        memory.put("print", new PrintFunction());
        
        String code = "";
        code = "try{ error( \"message\" ); } catch( e ) { print( e.getMessage() ); e.getMessage(); }";
        assertParseExpressions(code, "message");
        
        code = "try{ "
                + "throw \"abc\"; "
                + "} catch( e ) { print( e ); \"ok:\"+e; }"
            ;
        assertParseExpressions(code, "ok:abc");
        
        code = "var fact = function( x ){"
//                + "x < 0 "
//                + "x > 1 ? recursion( x-1 ) * x : 1;"
                + "if ( x < 0 ) throw \"factorial (\"+x+\") error\";"
                + "if ( x > 1 ) recursion( x-1 ) * x else 1;"
                + "};"
//                + "fact( 4 );"
                + "try {"
                + "fact( -2 );"
                + "} catch( e ) { print( e ); \"ok\"; }"
                ;
        assertParseExpressions(code, "ok");
    }
    
    public static class TestForProperties{
        public String field = "123";
        
        protected String prop = "abc";

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }
    }
    
    @Test
    public void readJREProperties(){
        log.println("readJREProperties");
        log.println("=================");
        
        String code = null;
        
        TestForProperties p = new TestForProperties();
        memory.put("obj", p);
        
        code = "obj.field";
        assertParseExpressions(code, "123");

        code = "obj.prop";
        assertParseExpressions(code, "abc");

        code = "obj.prop = \"12345\"; obj.prop";
        assertParseExpressions(code, "12345");
    }
    
    @Test
    public void forTest(){
        log.println("FOR Тестирование итераций");
        log.println("=========================");
        
        String code = null;
        code =  "var src = [ 1 , 2 , 3 ]; "
                + "var sum = 0; "
                + "for( v in src ) { "
                + "sum = sum + v;"
                + " }"
                + "sum";
        assertParseExpressions(code, "6");
        
        code = "var src = { \n"
                + "idx : 0, \n"
                + "hasNext : function( self ) { self.idx < 5 }, \n"
                + "next : function( self ){ self.idx = self.idx + 1; self.idx } \n"
                + "}; \n"
                + "var sum = 0; \n"
                + "for( v in src ) { \n"
                + "sum = sum + v; \n"
                + "} \n"
                + "sum";
        
        assertParseExpressions(code, "15");
    }

    @Test
    public void javaNumberConversion(){
        log.println("Конвертирование java чисел");
        log.println("==========================");
        
        String code = null;
        code =  "var a = 1; "
            + "a.intValue()";
        assertParseExpressions(code, "1");

        code =  "var a = 2; "
            + "a.doubleValue()";
        assertParseExpressions(code, "2.0");
        
//        code = "var src = { \n"
//                + "idx : 0, \n"
//                + "hasNext : function() { this.idx < 5 }, \n"
//                + "next : function(){ this.idx = this.idx + 1; this.idx } \n"
//                + "}; \n"
//                + "var sum = 0; \n"
//                + "for( v in src ) { \n"
//                + "sum = sum + v; \n"
//                + "} \n"
//                + "sum";
//        
//        assertParseExpressions(code, "15");
    }
    
    @Test
    public void ifNotDefinedVariable(){
        log.println("Неопределенная переменная");
        log.println("==========================");
        
        String varName = "undefVar";
        
        if( memory.containsKey(varName) ){
            memory.remove(varName);
        }
        
        String code = 
                "var v1 = 1;\n"
            +   "if( "+varName+" ){ v1=2 };\n"
            +   "v1";
        
        assertParseExpressions(code, "1");

        code = 
                "var v1 = 1;\n"
            +   "if( "+varName+" ){ v1=2 } else { v1=3 }\n"
            +   "v1";
        
        assertParseExpressions(code, "3");
    }

    @Test
    public void ifNullCondition(){
        log.println("Null ссылка в условии if");
        log.println("==========================");
        
        String code = 
                "var v1 = null;\n"
            +   "var v2 = 1;\n"
            +   "if( v1 ){\n"
            +   "v2 = 2;\n"
            +   "}else{\n"
            +   "v2 = 3;\n"
            +   "}\n"
            +   "v2";
        
        assertParseExpressions(code, "3");
    }

    @Test
    public void ifZeroCondition(){
        log.println("Ноль в условии if");
        log.println("==========================");
        
        String code = 
                "var v1 = 0;\n"
            +   "var v2 = 1;\n"
            +   "if( v1 ){\n"
            +   "v2 = 2;\n"
            +   "}else{\n"
            +   "v2 = 3;\n"
            +   "}\n"
            +   "v2";
        
        assertParseExpressions(code, "3");
    }

    @Test
    public void ifNotBoolCondition(){
        log.println("Не bool в условии if");
        log.println("==========================");
        
        String code = 
                "var v1 = \"abc\";\n"
            +   "var v2 = 1;\n"
            +   "if( v1 ){\n"
            +   "v2 = 2;\n"
            +   "}else{\n"
            +   "v2 = 3;\n"
            +   "}\n"
            +   "v2";
        
        assertParseExpressions(code, "2");
    }
}
