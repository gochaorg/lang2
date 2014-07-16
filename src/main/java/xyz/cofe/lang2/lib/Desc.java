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


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.types.SimpleTypes;
import xyz.cofe.config.SimpleConfig;

/**
 * @author gocha
 */
public class Desc implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Desc.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Desc.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Desc.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Desc.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Desc.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Desc.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="constructors">
    protected SimpleConfig conf = null;
    protected SimpleConfig templates = null;
    
    public Desc(SimpleConfig config) {
        this.conf = config == null ? new SimpleConfig() : config;
        this.templates = conf.subset("templates.");
    }
    
    public Desc() {
        this.conf = new SimpleConfig();//owner.getConf().subset("descJre.");
        this.templates = conf.subset("templates.");
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="out, print()">
    private Writer _out = null;

    public Writer getOut() {
        if (_out == null)
            _out = new OutputStreamWriter(System.out);
        return _out;
    }

    public void setOut(Writer out) {
        _out = out;
    }
    
    private void print(String text) {
        try {
            Writer w = getOut();
            w.write(text);
            boolean flush = conf.get("flush", true);
            if( flush )w.flush();
        } catch (IOException ex) {
            Logger.getLogger(Desc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="print">
    private Print _print = new Print();
    private Print print() {
        if (_print == null)
            _print = new Print(conf.subset("print."));
        return _print;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="jre">
    private DescJRE _jre = null;
    private DescJRE jre() {
        if (_jre != null)
            return _jre;
        _jre = new DescJRE(conf.subset("jre."));
        return _jre;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="map">
    private DescMap _map = null;
    private DescMap map() {
        if (_map != null)
            return _map;
        _map = new DescMap(conf.subset("map."));
        return _map;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="list">
    private DescList _list = null;

    private DescList list() {
        if (_list != null)
            return _list;
        _list = new DescList(conf.subset("list."));
        return _list;
    }
    // </editor-fold>
    
    @Override
    public Object call(Object... arguments) {
        if( arguments==null )return null;
        if( arguments.length<1 )return null;
        write(arguments);
        return null;
    }
    
    private void write(Object[] arguments){
        for( int i=0; i<arguments.length; i++ ){
            if( i>0 ){
                writeSplitter();
            }
            Object arg = arguments[i];
            if( arg==null ){
                writeNull();
            }else{
                write(arg);
            }
        }
//        boolean appendEndl = conf.get("appendEndl", true);
    }
    
    private void writeSplitter(){
        print(templates.get("splitter", ", "));
    }
    
    private void writeNull(){
        print(templates.get("null", "null"));
    }
    
    private void write(Object object){
        if( object==null ){
            writeNull();
            return;
        }
        
        Class c = object.getClass();
        if( SimpleTypes.isSimple(c) || object instanceof String ){
            print( print().sprint(object) );
            return;
        }
        
        if( c.isArray() ){
            StringBuilder sb = new StringBuilder();
            sb.append(list().descArray(object));
            print(sb.toString());
            return;
        }
        
        if( object instanceof Map ){
            print( map().descMap((Map)object) );
            return;
        }

        if( object instanceof Collection ){
            print( list().descList( (Collection)object) );
            return;
        }
        
        print( jre().descJREObject(object) );
    }
}
