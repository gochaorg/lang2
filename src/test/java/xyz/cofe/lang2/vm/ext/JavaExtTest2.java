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
package xyz.cofe.lang2.vm.ext;

import java.io.OutputStreamWriter;
import java.net.URL;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.text.IndentStackWriter;
import org.junit.*;
import static org.junit.Assert.*;
import xyz.cofe.log.CLILoggers;

/**
 *
 * @author Камнев Георгий Павлович <nt.gocha@gmail.com>
 */
public class JavaExtTest2 {
    // Настройки лог
    private CLILoggers logger = null;
    
	// Лог действией
	protected IndentStackWriter log = null;
    
    public JavaExtTest2() {
        OutputStreamWriter ow = new OutputStreamWriter(System.out);
        log = new IndentStackWriter(ow);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    protected L2Engine engine = new L2Engine();
    
    protected Value parseExpressions(String source) {
        if (source== null) {            
            throw new IllegalArgumentException("source==null");
        }
        return engine.parse(source);
    }

	/**
	 * Проверяет выполнение кода (expressions) и его результата
	 * @param source Код
	 * @param result Ожидаемый результат
	 * @return true / false - выполнение теста
	 */
    protected boolean assertParseExpressions(String source, String result)
	{
        log.incLevel();
        
        log.println("Исходник:");
        log.incLevel();
        log.println(source);
        log.decLevel();
        
        log.println("Ожидаемый результат:");
        log.incLevel();
        log.println(result==null ? "null" : result);
        log.decLevel();
        log.flush();
        
		Value v = null;
        v = parseExpressions(source);
		
		// Вычисление
		Object res = v.evaluate();
		if( result==null && res==null )
		{
			log.println("Результат:");
            log.incLevel();
			log.println("null");
            log.decLevel();
			
			log.println("Результат совпал");
			log.println();
            log.flush();
            log.decLevel();
            assertTrue(true);
			return true;
		}
		if( res==null ){
            log.decLevel();
            fail("Значение не вычисленно (null): "+source);
            assertTrue(false);
            return false;
        }
		
		String s = res.toString();
		
		log.println("Результат:");
        log.incLevel();
		log.println(s);
        log.decLevel();
        log.flush();
		
		boolean eq = s.equals(result);
		if( !eq ){
            log.decLevel();
            fail("Результат не совпал!");
            assertTrue(false);
            return false;
		}else{
			log.println("Результат совпал");
            log.flush();
            log.decLevel();
            assertTrue(true);
            return true;
		}
	}
    
    @Before
    public void setUp() {
        logger = new CLILoggers();
        URL logConf = JavaExtTest2.class.getResource("JavaExtTest.xml");
        logger.parseXmlConfig(logConf);
    }
    
    @After
    public void tearDown() {
        if( logger!=null && logger.getNamedHandler().containsKey("console") ){
            logger.removeHandlerFromRoot(logger.getNamedHandler().get("console"));
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void testSize() {
//        System.out.println("testSize");
//        System.out.println("========");
//        
//        assertParseExpressions("var test=\"abcde\"; test.size", "5");
    }
}
