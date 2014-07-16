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
package xyz.cofe.lang2.vm.jre;

import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.vm.Value;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.SimpleCompiler;
import xyz.cofe.collection.Convertor;
import xyz.cofe.common.Reciver;
import xyz.cofe.jdk.ByteCode;
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
public class ProxyTest {
    
    public ProxyTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
//    @Test
    public void test01(){
        Class cls = ActionListener.class;
        
        InterfaceProxyGen pg = new InterfaceProxyGen();
        String clssName = cls.getName().replace(".", "_");

        pg.setInterfaceClass(cls);
        pg.setPackageName("lang2.vm.jre.gen");
        pg.setClassName(clssName);
        
        System.out.println(pg.generate());
    }

//    @Test
    public void test02(){
        Class cls = Convertor.class;
        
        InterfaceProxyGen pg = new InterfaceProxyGen();
        String clssName = cls.getName().replace(".", "_");

        pg.setInterfaceClass(cls);
        pg.setPackageName("lang2.vm.jre.gen");
        pg.setClassName(clssName);
        
        System.out.println(pg.generate());
    }
    
    @Test
    public void test03(){
        System.out.println("test03");
        System.out.println("======");
        
        // Создаение l2 объекта
        L2Engine l2engine = new L2Engine();
        Value l2ParseTree = l2engine.parse(
            "var obj = {"
                + "convert : function( self, arg ){"
                + "arg + arg"
                + "}"
            + "};"
            + "obj");
        assertTrue(l2ParseTree!=null);
        
        Object ol2val = l2ParseTree.evaluate();
        assertTrue(ol2val!=null);
        assertTrue(ol2val instanceof java.util.Map);
        
        java.util.Map l2Object = (java.util.Map)ol2val;

        // Генерирование исходника proxy
        Class cls = Convertor.class;
        
        InterfaceProxyGen pg = new InterfaceProxyGen();
        String clssName = cls.getName().replace(".", "_");
        
        pg.setInterfaceClass(cls);
        pg.setPackageName("lang2.vm.jre.gen");
        pg.setClassName(clssName);
        
        String source = pg.generate();
        
        try {
            // Компиляция исходника
            SimpleCompiler compiler = new SimpleCompiler();
            
            ClassLoader cl = this.getClass().getClassLoader();
            compiler.setParentClassLoader(cl);
            
            compiler.cook(source);
            
            ClassLoader ccl = compiler.getClassLoader();
            
            // Получение class сгенерированого исходника
            Class c1 = ccl.loadClass(pg.getFullClassName());
            assertTrue(c1!=null);
            
            boolean isConv = cls.isAssignableFrom(c1);
            assertTrue( isConv );
            
            // Создание proxy объекта
            Constructor constr = c1.getConstructor( java.util.Map.class );
            Object proxy = constr.newInstance(l2Object);
            assertTrue( proxy instanceof Convertor );
            
            // Тестирование proxy
            String testVal = "Abc";
            Object resValue = ((Convertor)proxy).convert(testVal);
            
            assertTrue( resValue instanceof String );
            assertTrue( resValue.equals("AbcAbc") );
            
        } catch (CompileException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ProxyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
