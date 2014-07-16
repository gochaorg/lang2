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
import org.antlr.runtime.RecognitionException;
import xyz.cofe.text.IndentStackWriter;
import java.util.Map;
import java.util.HashMap;
import xyz.cofe.lang2.test.tools.ResourceReader;
import xyz.cofe.collection.iterators.TreeWalk;

import xyz.cofe.types.PropertyController;
import xyz.cofe.types.ValueController;
import org.junit.Test;
import static junit.framework.Assert.*;
import xyz.cofe.lang2.vm.err.Error;
import xyz.cofe.lang2.vm.OpTreeWalker;
import xyz.cofe.lang2.vm.Value;

/**
 * Тестирование обхода древа OP
 * @author gocha
 */
public class OpTreeWalkerTest
{
	public OpTreeWalkerTest()
	{
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
            throw new Error(e.getMessage(),e);
        }
    }
    
    public static void print( IndentStackWriter log, Value opTree ){
		Iterable<TreeWalk<Value>> itr = OpTreeWalker.itrable(opTree);
		for( TreeWalk<Value> step : itr )
		{
			Value v = step.currentNode();
			log.level(step.currentLevel());
			if( v==null ){
				log.println("null");
			}else{
				log.print(v.getClass().getName());

				log.print(" properties: ");
				Iterable<ValueController> _vc = PropertyController.buildControllers(v);
				int idx = -1;
				for( ValueController vc : _vc ){
					idx++;
					if( idx>0 )log.print(", ");
					try{
						Object val = vc.getValue();
						log.print(""+vc.getName()+"="+val);
					}catch(Throwable e){
						log.print("can't read "+vc.getName());
					}
				}
				log.println(";");
			}
			log.flush();
		}
    }
    
	@Test
	public void test1(){
		ResourceReader resources = new ResourceReader(OpTreeWalkerTest.class);
		String code = resources.read("opTree-01.l2");
		assertTrue(code!=null);
		
		Value opTree = parseExpressions(code);
		assertTrue(opTree!=null);
		
		Iterable<TreeWalk<Value>> itr = OpTreeWalker.itrable(opTree);
		for( TreeWalk<Value> step : itr )
		{
			Value v = step.currentNode();
			log.level(step.currentLevel());
			if( v==null ){
				log.println("null");
			}else{
				log.print(v.getClass().getName());

				log.print(" properties: ");
				Iterable<ValueController> _vc = PropertyController.buildControllers(v);
				int idx = -1;
				for( ValueController vc : _vc ){
					idx++;
					if( idx>0 )log.print(", ");
					try{
						Object val = vc.getValue();
						log.print(""+vc.getName()+"="+val);
					}catch(Throwable e){
						log.print("can't read "+vc.getName());
					}
				}
				log.println(";");
			}
			log.flush();
		}
	}
}
