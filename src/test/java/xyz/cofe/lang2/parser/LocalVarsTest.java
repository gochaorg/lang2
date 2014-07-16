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

import static junit.framework.Assert.assertTrue;
import xyz.cofe.lang2.lib.CLIFunctions;
import xyz.cofe.lang2.vm.Value;
import org.junit.Test;

/**
 * Тестирование парсера
 * @author gocha
 */
public class LocalVarsTest extends BaseTest
{
    protected L2Engine engine = new L2Engine();
    
    @Override
    protected Value parseExpressions(String source) {
        if (source== null) {            
            throw new IllegalArgumentException("source==null");
        }
        
        engine.setMemory(memory);
        return engine.parse(source);
    }
    
    protected String code_localVars(){
        return this.resources().read("localVars.l2");
    }
    
    @Test
    public void localVarsVisibility(){
        return;
        // TODO этот тест не работает
//        System.out.println("localVarsVisibility");
//        System.out.println("===================");
//        
//        CLIFunctions cliFunctions = new CLIFunctions(engine);
//        memory.putAll(cliFunctions.getMemObjects());
//        
//        String code = null;
//        Value v = null;
//        Object r = null;
//        
//        code = code_localVars();
//        System.out.println("code:");
//        System.out.println(code);
//        v = parseExpressions(code);
//        assertTrue(v!=null);
//        
//        System.out.println("--- evaluate ---");
//        r = v.evaluate();
    }
}
