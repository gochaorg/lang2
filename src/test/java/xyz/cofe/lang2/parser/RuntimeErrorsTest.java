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

import static junit.framework.Assert.*;
import xyz.cofe.lang2.vm.err.CastError;
import xyz.cofe.lang2.vm.err.VarNotExistsError;
import xyz.cofe.lang2.vm.Value;
import org.junit.Test;

/**
 * Проверка ошибок времени исполнения
 * @author gocha
 */
public class RuntimeErrorsTest extends BaseTest
{
    @Test
    public void whileCastError(){
        String code = "var v = 123;\n"
                + "try{\n"
                + "  while( v ){\n"
                + "    break;\n"
                + "  } \n"
                + "  v = \"failed\";\n"
                + "  } catch( e ){\n"
                + "  e; \n"
                + "} ";
        
        Value v = parseExpressions(code);
        Object res = v.evaluate();
        assertTrue(res!=null);
        assertTrue(res instanceof CastError);
    }
    
    @Test
    public void varNotExists(){
        String code = "try { \n"
                + "someVar + 1; \n"
                + "} catch( e ) { \n"
                + "e; \n"
                + "}";
        
        Value v = parseExpressions(code);
        Object res = v.evaluate();
        assertTrue(res!=null);
        assertTrue(res instanceof VarNotExistsError);
    }

    @Test
    public void varNotExists2(){
        String code = "try { \n"
                + "someVar = 1; \n"
                + "} catch( e ) { \n"
                + "e; \n"
                + "}";
        
        Value v = parseExpressions(code);
        Object res = v.evaluate();
        assertTrue(res!=null);
        assertTrue(res instanceof VarNotExistsError);
    }
}
