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
import java.util.HashMap;
import java.util.Map;
import xyz.cofe.lang2.test.tools.ResourceReader;
import xyz.cofe.lang2.vm.Value;
import org.antlr.runtime.RecognitionException;
import xyz.cofe.text.IndentStackWriter;
import static junit.framework.Assert.*;

/**
 * Базовый класс для тестов
 * @author gocha
 */
public abstract class BaseTest
{
	public BaseTest() {
        OutputStreamWriter ow = new OutputStreamWriter(System.out);
        log = new IndentStackWriter(ow);
	}
	
	// Лог действией
	protected IndentStackWriter log = null;
	
	// Память интерпретатора
	protected Map<String, Object> memory = new HashMap<String, Object>();
	
	// Ресурсы
	private ResourceReader resources = null;
    
    // Опции парсера
    protected ParserOptions parserOpts = new ParserOptions();
	
	/**
	 * Создает собственную фабрику
	 * @return Фабрика классов или null
	 */
	protected Factory createCustomFactory(){
		return null;
	}
    
    /**
     * Выполнение исходного кода
     * @param source Исходный код
     * @return Значение
     */
    protected Object evalExpressions(String source){
        Value v = parseExpressions(source);
        assertTrue(v!=null);
        
        return v.evaluate();
    }

	/**
	 * Парсинг исходного кода - expression
	 * @param source Исходный код
	 * @return Древо выражения
	 * @throws Throwable Ошибка в случаи отсуствия результата
	 */
	protected Value parseExpressions(String source) {
		Value v = null;
		final Factory customFact = createCustomFactory();
//		if( customFact!=null )parser.setFactory(customFact);

		BasicParser parser = new BasicParser(source,new ParserOptions()){
            @Override
            public Factory factory() {
                if( customFact!=null )return customFact;
                return super.factory();
            }
        };
        
		// Устанавливаем память
		parser.memory(memory);
		try {
			// Парсинг
			v = parser.parse();
			if (v == null) {
				fail("not parsed: " + source);
				throw new Error("not parsed");
			}
			return v;
		} catch (RecognitionException e) {
			throw new Error(e.getMessage(), e);
		}
	}

	/**
	 * Возвращает объект для доступа к ресурсам
	 * @return Объкт доступа к ресурсам
	 */
	protected ResourceReader resources() {
		if (resources == null)
			resources = new ResourceReader(this.getClass());
		return resources;
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
}
