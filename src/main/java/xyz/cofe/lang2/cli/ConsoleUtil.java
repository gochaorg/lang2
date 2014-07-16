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


import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class ConsoleUtil {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ConsoleUtil.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private Console console = null;
    
    public ConsoleUtil(Console cons){
        if( cons==null )throw new IllegalArgumentException( "cons==null" );
        this.console = cons;
    }
    
    private Reader createReader(){
        return new Reader() {
            private char[] buff = new char[256];
            private int buffDataSize = 0;
            private int buffDataBegin = 0;
            
            private void fillBuff( String text ){
                buff = text.toCharArray();
                buffDataSize = buff.length;
                buffDataBegin = 0;
            }
            
            private int readBuff(char[] cbuf, int off, int len){
                if( buffDataSize>0 ){
                    int r = buffDataSize;
                    if( r>len ){
                        r = len;
                    }
                    for( int i=0; i<r; i++ ){
                        cbuf[i+off] = buff[i+buffDataBegin];
                    }
                    buffDataSize-=r;
                    buffDataBegin+=r;
                    return r;
                }
                return 0;
            }
            
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                int r = readBuff(cbuf,off,len);
                if( r>0 )return r;
                
                String line = console.readLine();
                if( line==null )return -1;
                if( line.length()<1 )return 0;
                
                fillBuff(line);
                r = readBuff(cbuf,off,len);
                
                return r;
            }

            @Override
            public void close() throws IOException {
            }
        };
    }
    
    private Reader r = null;
    public Reader getReader(){
        if( r!=null )return r;
        r = createReader();
        return r;
    }

    private Writer createWriter(){
        return new Writer() {
            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                String text = new String(cbuf,off,len);
                console.print(text);
            }

            @Override
            public void flush() throws IOException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void close() throws IOException {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
    
    private Writer w = null;
    public Writer getWriter(){
        if( w!=null )return w;
        w = createWriter();
        return w;
    }
}
