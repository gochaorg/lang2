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
package xyz.cofe.lang2.lib;

import org.junit.*;
import xyz.cofe.config.SimpleConfig;
//import lang2.lib.

/**
 *
 * @author Камнев Георгий Павлович <nt.gocha@gmail.com>
 */
public class PrintTest {
    
    public PrintTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    public static class SubCmptStr {
        private String str = null;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }
        
        public void test(){
        }
    }
    
    public static class SubCmptInt {
        private int num = 0;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
        
        public void test2(){
        }
    }
    
    public static class Cmpt {
        private SubCmptStr str1 = new SubCmptStr();
        private SubCmptInt int1 = new SubCmptInt();
        public int test3(boolean a1){
            return 0;
        }

        public SubCmptStr getStr1() {
            return str1;
        }

        public void setStr1(SubCmptStr str1) {
            this.str1 = str1;
        }

        public SubCmptInt getInt1() {
            return int1;
        }

        public void setInt1(SubCmptInt int1) {
            this.int1 = int1;
        }
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testJRE(){
        SimpleConfig conf = new SimpleConfig();
        conf.put("recusiveMaxLevel", "2");
        
        DescJRE printJre = new DescJRE(conf);
        
        String desc = printJre.descJREObject(new Cmpt());
        System.out.println(desc);
//        out.flush();
    }
}
