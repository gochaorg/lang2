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

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class ThreadTest {
    
    public ThreadTest() {
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
    
    protected String getCodeFactorial(int num){
        String code = "var fact = function( n ){"
                    + "if( n<0 )return n;"
                    + "if( n==1 )return 1;"
                    + "if( n==2 )return 2;"
                    + "var v = 2;"
                    + "var c = 2;"
                    + "while( c < n ){"
                        + "c = c + 1;"
                        + "v = v * c;"
                    + "}"
                    + "v"
                + "}"
                + "fact( "+num+" )";
        return code;
    }

    protected String getCodeInfinity(){
        String code = "var c = 1;"
                    + "while( c > 0 ){"
                        + "c = c + 1;"
                    + "}"
                    + "c";
        return code;
    }
    
    @Test
    public void test(){
        System.out.println("Тест на остановку в другом потоке");
        System.out.println("=================================");
        
//        long cycleCo = 100000;
//        String code = "var counter=0;"
//                + "while( counter<"+cycleCo+" ){"
//                + "counter = counter + 1;"
//                + "}";
        final String code = getCodeInfinity();
        
        System.out.println("Код:");
        System.out.println(code);
        
        final L2Engine engine = new L2Engine();
        Date dateBegin = new Date();
        
        Thread thread = new Thread(){
            @Override
            public void run() {
                engine.eval(code);
            }
        };
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
        
        System.out.println("Ждем 2 сек.");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Нельзя даже подождать 2 сек.");
        }
        
        System.out.println("Посылаем interrupt");
        Date stopBegin = new Date();
        thread.interrupt();
        long maxWaitTime = 10 * 1000;
        
        boolean succ = true;
        long lastPrintTime = -1;
        long periodPrintTime = 50;
        long sleepTime = 0;
        int sleepTimeNano = 100;
        
        while( thread.isAlive() ){
            long waitTime = (new Date().getTime()) - stopBegin.getTime();
            if( waitTime>maxWaitTime ){
                System.out.println("Поток превысил ожидание в "+maxWaitTime+" миллисек.");
                thread.stop();
                succ = false;
            }
            if( lastPrintTime<0 ){
                System.out.println("Поток еще жив, ждем уже "+waitTime+" миллисек.");
                lastPrintTime = waitTime;
            }else{
                if( (waitTime - lastPrintTime) > periodPrintTime ){
                    System.out.println("Поток еще жив, ждем уже "+waitTime+" миллисек.");
                    lastPrintTime = waitTime;
                }
            }
            try {
                Thread.sleep(sleepTime,sleepTimeNano);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTest.class.getName()).log(Level.SEVERE, null, ex);
                fail("Нельзя даже подождать 2 сек.");
            }
            thread.interrupt();
        }
        if( !succ ){
            while( thread.isAlive() ){
                long waitTime = (new Date().getTime()) - stopBegin.getTime();
                System.out.println("Поток еще жив, ждем уже "+waitTime+" миллисек.");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadTest.class.getName()).log(Level.SEVERE, null, ex);
                    fail("Нельзя даже подождать 2 сек.");
                }
            }
        }
        Date stopEnd = new Date();
        Date dateEnd = new Date();
        
        long tRun = dateEnd.getTime() - dateBegin.getTime();
        long tStop = stopEnd.getTime() - stopBegin.getTime();
        System.out.println("Время исполнения = "+tRun+" милисек.");
        System.out.println("Время остановки  = "+tStop+" милисек.");
        System.out.println("Мак. допустимое время остановки  = "+maxWaitTime+" милисек.");
        if( succ ){
            System.out.println("Тест выполнен успешно");
        }else{
            System.out.println("Тест завален");
            fail();
        }
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
