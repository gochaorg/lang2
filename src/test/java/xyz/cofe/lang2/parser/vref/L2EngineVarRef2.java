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
package xyz.cofe.lang2.parser.vref;

import java.net.URL;
import xyz.cofe.lang2.parser.BasicParserTest;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.Value;
import org.junit.Test;
import xyz.cofe.log.CLILoggers;

/**
 * Тестирование парсера
 * @author gocha
 */
public class L2EngineVarRef2 extends BasicParserTest
{
    protected L2Engine l2Engine = null;
    
    @Override
    protected Value parseExpressions(String source) {
        if( l2Engine==null ){
            l2Engine = new L2Engine();
            l2Engine.setMemory(memory);
        }
        
        return l2Engine.parse(source);
    }
    
    @Test
    public void varRef2(){
        return;
        // TODO используется альтернативный способ поиска переменных
//        
//        String src = "var v = \"abc\";\n"
//                + "var obj = \n"
//                + "  {\n"
//                + "    f : function ( a )\n"
//                + "        {\n"
//                + "          a + v\n"
//                + "        }\n"
//                + "  };\n"
//                + "v = \"def\";\n"
//                + "obj.f( \"xcv\" )";
//        String must = "xcvabc";
//
//        CLILoggers logger = new CLILoggers();
//        URL logConf = L2EngineVarRefTest.class.getResource("L2EngineVarRefTest2.logconf.xml");
//        logger.parseXmlConfig(logConf);
//        
//        assertParseExpressions(src, must);
//        
//        if( logger.getNamedHandler().containsKey("console") ){
//            logger.removeHandlerFromRoot(logger.getNamedHandler().get("console"));
//        }
    }
}
