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
package xyz.cofe.lang2.cli;

import java.io.File;
import java.net.URL;
import xyz.cofe.common.Text;
import xyz.cofe.files.FileUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class ReadScriptFileTest {
    
    public ReadScriptFileTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void read01() {
        URL url = ReadScriptFileTest.class.getResource("shebang.l2");
        assertTrue(url!=null);
        
        String dirtyScript = FileUtil.readAllText(url, FileUtil.UTF8());
        assertTrue(dirtyScript!=null);
        
        String script = new ScriptReader().readScriptFile(url, null);
        assertTrue(script!=null);
        
        int size1 = dirtyScript.length();
        int size2 = script.length();
        
        // Файл от оригинала не должен отлич по размеру символов
        assertTrue(size1 == size2);
        
        String[] lines1 = Text.splitNewLines(dirtyScript);
        String[] lines2 = Text.splitNewLines(script);
        int l1 = lines1.length;
        int l2 = lines2.length;

        // Файл от оригинала не должен отлич по кол-ву строк
        assertTrue(l1==l2);
        
        for( int i=0; i<l1; i++ ){
            String line1 = lines1[i];
            String line2 = lines2[i];
            
            l1 = line1.length();
            l2 = line2.length();

            // Файл от оригинала не должен отлич по длине строк
            assertTrue(l1==l2);
        }
        
        for( String line : lines2 ){
            boolean bashComments = line.startsWith("#");
            // Файл не должен содержать bash comments
            assertTrue(!bashComments);
        }
    }
}
