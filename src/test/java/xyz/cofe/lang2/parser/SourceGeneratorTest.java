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

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import xyz.cofe.text.IndentStackWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.antlr.runtime.RecognitionException;
import xyz.cofe.lang2.vm.Value;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Тестирование генератора исходного кода
 * @author gocha
 */
public class SourceGeneratorTest
{	
	public SourceGeneratorTest() {
        OutputStreamWriter ow = new OutputStreamWriter(System.out);
        log = new IndentStackWriter(ow);
	}
	
	// Память интерпретатора
	private Map<String,Object> memory = new HashMap<String, Object>();
    
    // Лог действией
    private IndentStackWriter log = null;
    
    /**
     * Парсинг исходного кода - expression
     * @param source Исходный код
     * @return Древо выражения
     * @throws Throwable Ошибка в случаи отсуствия результата
     */
    private Value parseExpressions(String source)
    {
		Value v = null;
		BasicParser parser = new BasicParser(source,new ParserOptions());
		
		// Устанавливаем память
		parser.memory(memory);
		
        try{
            // Парсинг
            v = parser.parse();
            if( v==null ){
                fail("not parsed: "+source);
                throw new Error("not parsed");
            }
            return v;
        }
        catch(RecognitionException e){
			fail("RecognitionException:"+e.getMessage());
            throw new Error(e.getMessage(),e);
        }
    }
	
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}
	
//	private boolean match(String src,String need){
//        Value v = parseExpressions(src);
//		
//		StringWriter sw = new StringWriter();
//		SourceGenerator gen = new SourceGenerator(sw);
//		gen.go(v);
//
//		need = need.replace(" ","\\s+");
//		
//		return false;
//	}
	
	@Test
	public void test1(){
		String code = "";
        code = "var f1 = function( a, b ) a+b; f1( 1,2 )";
		Value v = null;
        v = parseExpressions(code);
		
		SourceGenerator gen = new SourceGenerator(log);
		gen.go(v);
		log.println();
		log.println("-------------------");

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

		v = parseExpressions(code);
		gen.go(v);
	}
}
