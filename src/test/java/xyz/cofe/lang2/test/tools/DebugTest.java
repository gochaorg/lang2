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
package xyz.cofe.lang2.test.tools;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import xyz.cofe.lang2.parser.CommentReciver;
import xyz.cofe.lang2.parser.DebugParser;
import xyz.cofe.lang2.parser.ParserOptions;
import xyz.cofe.lang2.parser.SourceGenerator;
import xyz.cofe.files.FileUtil;

/**
 * Тестирует отладку
 * @author gocha
 */
public class DebugTest {
    public static void main(String[] args){
        DebugTest dt = new DebugTest();
        dt.testComments();
    }

    public Map<String,Object> memo = new HashMap<String, Object>();

    public void testComments(){
        DebugParser.ParseResult pres = null;
        pres = parseResource("fact.l2");
        if( pres.value!=null ){
            System.out.println("generated source="+SourceGenerator.generateSource(pres.value));
            System.out.println("value="+pres.value.evaluate());

            if( pres.comments.size()>0 ){
                System.out.println("comments:");
                for( CommentReciver.Comment e : pres.comments ){
                    System.out.println("line="+e.getY()+" char="+e.getX()+" text="+e.getText());
                }
            }

            String source = DebugSourceViewer.getSourceOf(pres.value);
            System.out.println("sources:");
            System.out.println(source);
        }
    }

    public DebugParser.ParseResult parseResource(String resourceName){
        URL resURL = DebugTest.class.getResource(resourceName);
        if( resURL!=null ){
            String source = FileUtil.readAllText(resURL,FileUtil.UTF8());
            if( source!=null ){
                return DebugParser.parseExpressions(memo,source,new ParserOptions());
            }
        }
        return null;
    }
}
