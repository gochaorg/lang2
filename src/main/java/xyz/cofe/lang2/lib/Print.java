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

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.collection.Convertor;
import xyz.cofe.text.Text;
import xyz.cofe.config.SimpleConfig;

/**
 * Текстовое представление объектов, 
 * вывод их на STDIO или какой либо другой текстовый поток
 * @author nt.gocha@gmail.com
 */
public class Print {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Print.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Print.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Print.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Print.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Print.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Конструктор">
    public Print(){
        this.conf = new SimpleConfig();
    }
    
    public Print(Map config){
        this.conf = new SimpleConfig();
        if( config!=null ){
            for( Object _e : config.entrySet() ){
                if( _e instanceof Map.Entry ){
                    Map.Entry e = (Map.Entry)_e;
                    Object k = e.getKey();
                    Object v = e.getValue();
                    if( k instanceof String ){
                        if( v instanceof String ){
                            conf.put((String)k, (String)v);
                        }else{
                            conf.put((String)k, null);
                        }
                    }
                }
            }
        }
    }
    
    public Print(SimpleConfig config){
        if( config!=null ){
            this.conf = config;
        }else{
            this.conf = new SimpleConfig();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="nullValueSting">
    /**
     * Текстовое представление null ссылки.<br/>
     * В конфиге соот. ключ nullValueSting
     */
    protected String nullValueSting = null;
    
    /**
     * Указывает Текстовое представление null ссылки
     * @return Текстовое представление null ссылки
     * @see Print#nullValueSting
     */
    public String getNullValueSting() {
        if( nullValueSting==null )nullValueSting = conf.get(
                "nullValueSting",
                "null",
                "Текстовое представление null ссылки");
        return nullValueSting;
    }
    
    /**
     * Указывает Текстовое представление null ссылки
     * @param nullValueSting  Текстовое представление null ссылки
     */
    public void setNullValueSting(String nullValueSting) {
        this.nullValueSting = nullValueSting;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="map()">
    public Text.StringMap map = new Text.StringMap(){
        @Override
        public Text.StringMap map(String key, Object value) {
            if( value==null ){
                map.map(key, getNullValueSting());
                return map;
            }
            return super.map(key, value);
        }
    };
    public Text.StringMap map(String key,Object val){
        if( val==null ){
            map.map(key, getNullValueSting());
            return map;
        }
        return map.map(key, val);
    }
    public Text.StringMap map(){
        return map;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="conf">
    protected SimpleConfig conf = null;
    
    public SimpleConfig getConf() {
        return conf;
    }// </editor-fold>
    
    /**
     * Формирование результата
     * @param res Объект
     * @return Текстовое представление
     */
    public String sprint(Object res){
        if( res==null )return getNullValueSting();
        if( res instanceof String )Const.encodeString((String)res);
        Class cls = res.getClass();
        if( cls.isArray() ){
            StringBuilder sb = new StringBuilder();
            DescList.sprintJREArray(sb, res, new Convertor<Object, String>(){
                @Override
                public String convert(Object from) {
                    return sprint(from);
                }
            });
            return sb.toString();
        }
        return res.toString();
    }
}
