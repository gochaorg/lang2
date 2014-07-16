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


import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.config.SimpleConfig;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class JLineInteractiveConfig 
implements InteractiveConfig
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JLineInteractiveConfig.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private JLineConsole console = null;
    private SimpleConfig config = null;
    
    public JLineInteractiveConfig(JLineConsole console){
        this.console = console;
        this.config = new SimpleConfig();
    }

    public JLineInteractiveConfig(JLineConsole console,SimpleConfig config){
        this.console = console;
        this.config = (config==null) ? new SimpleConfig() : config;
    }
    
//    public JLineInteractiveConfig(JLineConsole console,Map<String,String> conf){
//        super(conf);
//        this.console = console;
//    }
//    
//    public JLineInteractiveConfig(JLineConsole console,Properties conf){
//        super(conf);
//        this.console = console;
//    }
    
    private JLineConsole.Color getColor(String key,JLineConsole.Color defCol,String comment){
        String v = config.get(key, defCol==null ? null : defCol.name(), comment);
        if( v==null )return defCol;
        if( v.equalsIgnoreCase(JLineConsole.Color.Black.name()) )return JLineConsole.Color.Black;
        if( v.equalsIgnoreCase(JLineConsole.Color.Blue.name()) )return JLineConsole.Color.Blue;
        if( v.equalsIgnoreCase(JLineConsole.Color.Cyan.name()) )return JLineConsole.Color.Cyan;
        if( v.equalsIgnoreCase(JLineConsole.Color.Green.name()) )return JLineConsole.Color.Green;
        if( v.equalsIgnoreCase(JLineConsole.Color.Magenta.name()) )return JLineConsole.Color.Magenta;
        if( v.equalsIgnoreCase(JLineConsole.Color.Red.name()) )return JLineConsole.Color.Red;
        if( v.equalsIgnoreCase(JLineConsole.Color.White.name()) )return JLineConsole.Color.White;
        if( v.equalsIgnoreCase(JLineConsole.Color.Yellow.name()) )return JLineConsole.Color.Yellow;

        if( v.equalsIgnoreCase(JLineConsole.Color.LightBlack.name()) )return JLineConsole.Color.LightBlack;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightBlue.name()) )return JLineConsole.Color.LightBlue;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightCyan.name()) )return JLineConsole.Color.LightCyan;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightGreen.name()) )return JLineConsole.Color.LightGreen;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightMagenta.name()) )return JLineConsole.Color.LightMagenta;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightRed.name()) )return JLineConsole.Color.LightRed;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightWhite.name()) )return JLineConsole.Color.LightWhite;
        if( v.equalsIgnoreCase(JLineConsole.Color.LightYellow.name()) )return JLineConsole.Color.LightYellow;
        
        if( v.equalsIgnoreCase(JLineConsole.Color.Default.name()) )return JLineConsole.Color.Default;
        return defCol;
    }

    @Override
    public void nextReadIteration() {
        console.setBackground( getColor("next.background",JLineConsole.Color.Default, 
            "Цвет фона при очередной итерации") );
        console.setForeground( getColor("next.foreground",JLineConsole.Color.Default, 
            "Цвет текста при очередной итерации:\n"
        +   " Black, Blue, Cyan, Green, Magenta, Red, White, Yellow,\n"
        +   " LightBlack, LightBlue, LightCyan, LightGreen, \n"
        +   " LightMagenta, LightRed, LightWhite, LightYellow,\n"
        +   " Default"
        ));
    }

    @Override
    public void printResultBegin() {
        console.setBackground( getColor("result.background",JLineConsole.Color.Default, 
            "Цвет фона результата") );
        console.setForeground( getColor("result.foreground",JLineConsole.Color.Green, 
            "Цвет текста результата") );
    }

    @Override
    public void evalBegin() {
        console.setBackground( getColor("eval.background",JLineConsole.Color.Default,
            "Цвет фона выводимых сообщений скриптом (script output)" ));
        console.setForeground( getColor("eval.foreground",JLineConsole.Color.Default,
            "Цвет текста выводимых сообщений скриптом (script output)" ));
    }

    @Override
    public void evalEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void readLineBegin() {
        console.setBackground( getColor("readline.background",JLineConsole.Color.Default,
            "Цвет фона подсказки" ));
        console.setForeground( getColor("readline.foreground",JLineConsole.Color.Blue,
            "Цвет текста подсказки" ));
    }

    @Override
    public void readLineEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void catchEvalTimeExBegin() {
        console.setBackground( getColor("exception.background",JLineConsole.Color.Default,
            "Цвет фона ошибки" ));
        console.setForeground( getColor("exception.foreground",JLineConsole.Color.Red,
            "Цвет текста ошибки" ));
    }

    @Override
    public void catchInputExBegin() {
        console.setBackground( getColor("exception.background",JLineConsole.Color.Default,
            "Цвет фона ошибки" ));
        console.setForeground( getColor("exception.foreground",JLineConsole.Color.Red,
            "Цвет текста ошибки" ));
    }

    @Override
    public void helpBegin() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void helpEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPromptBegin() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPromptEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parseBegin() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void parseEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printResultEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void catchInputExEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void catchEvalTimeExEnd() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
