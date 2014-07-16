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
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.console.ConsoleReader;
import xyz.cofe.config.SimpleConfig;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class JLineConsole 
implements Console, GetInteractiveConfig
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JLineConsole.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private ConsoleReader reader = null;
    private PrintWriter writer = null;
    private SimpleConfig config = null;
    
    public JLineConsole() throws IOException{
        reader = new ConsoleReader();
        writer = new PrintWriter(reader.getOutput());
    }

    public JLineConsole(SimpleConfig config) throws IOException{
        reader = new ConsoleReader();
        writer = new PrintWriter(reader.getOutput());
        this.config = config;
    }

    @Override
    public String readLine() {
        try {
            if( prompt!=null ){
                return reader.readLine(prompt);
            }else{
                return reader.readLine();
            }
        } catch (IOException ex) {
            logException(ex);
        }
        return null;
    }
    
    private String prompt = null;

    @Override
    public void setPrompt(String prompt) {
        this.prompt = prompt;
//        reader.setPrompt(prompt);
    }

    @Override
    public void print(String text) {
        if( text==null )return;
        try {
            reader.print(text);
//            reader.flush();
        } catch (IOException ex) {
            logException(ex);
        }
    }

    @Override
    public void println() {
        try {
            reader.println();
//            reader.flush();
        } catch (IOException ex) {
            logException(ex);
        }
    }
    
    private InteractiveConfig conf = null;

    @Override
    public InteractiveConfig getInteractiveConfig() {
        if( conf!=null )return conf;
        conf = new JLineInteractiveConfig(this,getConfig());
        return conf;
    }
    
    public SimpleConfig getConfig(){
        if( config==null )config = new SimpleConfig();
        return config;
    }
    
    private static final String escape = "\u001B[";
    
    public static enum Color {
        Black,
        Red,
        Green,
        Yellow,
        Blue,
        Magenta,
        Cyan,
        White,
        LightBlack,
        LightRed,
        LightGreen,
        LightYellow,
        LightBlue,
        LightMagenta,
        LightCyan,
        LightWhite,
        Default
    }
    
    public void setDefaultForeground(){
        writer.print(escape+"39m");
        writer.flush();
    }
    
    public void setDefaultBackground(){
        writer.print(escape+"49m");
        writer.flush();
    }
    
    public void resetAttr(){
        writer.print(escape+"0m");
        writer.flush();
    }
    
    public void setBackground( Color col ){
        if( col==null )throw new IllegalArgumentException( "col==null" );
        switch( col ){
            case Black:     writer.print(escape+"40m"); break;
            case Red:       writer.print(escape+"41m"); break;
            case Green:     writer.print(escape+"42m"); break;
            case Yellow:    writer.print(escape+"43m"); break;
            case Blue:      writer.print(escape+"44m"); break;
            case Magenta:   writer.print(escape+"45m"); break;
            case Cyan:      writer.print(escape+"46m"); break;
            case White:     writer.print(escape+"47m"); break;
            case LightBlack:     writer.print(escape+"100m"); break;
            case LightRed:       writer.print(escape+"101m"); break;
            case LightGreen:     writer.print(escape+"102m"); break;
            case LightYellow:    writer.print(escape+"103m"); break;
            case LightBlue:      writer.print(escape+"104m"); break;
            case LightMagenta:   writer.print(escape+"105m"); break;
            case LightCyan:      writer.print(escape+"106m"); break;
            case LightWhite:     writer.print(escape+"107m"); break;
            case Default:
                setDefaultBackground();
                break;
        }
        writer.flush();
    }
    
    public void setForeground( Color col ){
        if( col==null )throw new IllegalArgumentException( "col==null" );
        switch( col ){
            case Black:     writer.print(escape+"30m"); break;
            case Red:       writer.print(escape+"31m"); break;
            case Green:     writer.print(escape+"32m"); break;
            case Yellow:    writer.print(escape+"33m"); break;
            case Blue:      writer.print(escape+"34m"); break;
            case Magenta:   writer.print(escape+"35m"); break;
            case Cyan:      writer.print(escape+"36m"); break;
            case White:     writer.print(escape+"37m"); break;
            case LightBlack:     writer.print(escape+"90m"); break;
            case LightRed:       writer.print(escape+"91m"); break;
            case LightGreen:     writer.print(escape+"92m"); break;
            case LightYellow:    writer.print(escape+"93m"); break;
            case LightBlue:      writer.print(escape+"94m"); break;
            case LightMagenta:   writer.print(escape+"95m"); break;
            case LightCyan:      writer.print(escape+"96m"); break;
            case LightWhite:     writer.print(escape+"97m"); break;
            case Default:
                setDefaultForeground();
                break;
        }
        writer.flush();
    }
}
