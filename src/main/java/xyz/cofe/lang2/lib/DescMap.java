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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;
import xyz.cofe.text.Text;
import xyz.cofe.types.SimpleTypes;
import xyz.cofe.config.SimpleConfig;

/**
 *
 * @author gocha
 */
public class DescMap {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DescMap.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DescMap.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DescMap.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DescMap.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DescMap.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DescMap.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="constructor">
    protected SimpleConfig conf = null;
    protected SimpleConfig templates = null;
    
    public DescMap(SimpleConfig config) {
        this.conf = config == null ? new SimpleConfig() : config;
        this.templates = conf.subset("templates.");
    }
    
    public DescMap() {
        this.conf = new SimpleConfig();//owner.getConf().subset("descJre.");
        this.templates = conf.subset("templates.");
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="map">
    private Text.StringMap map = null;

    protected void setStringMap(Text.StringMap map) {
        this.map = map;
    }
    
    private Text.StringMap map(String key, Object val) {
        return map().map(key, val);
    }

    private Text.StringMap map() {
        if (map == null)
            new Text.StringMap();
        return map;
    }// </editor-fold>
    
    public String descMap(Map map){
        if (map== null) {            
            throw new IllegalArgumentException("map==null");
        }
        StringBuilder sb = new StringBuilder();
        
        Iterable keys = map.keySet();
        boolean sort = conf.get("sort",true);
        boolean inverse = conf.get("sort.inverse",false);
        if( sort ){
            ArrayList<String> keyList = new ArrayList<String>();
            for( Object ko : keys ){
                if( ko instanceof String ){
                    String key = (String)ko;
                    keyList.add( key );
                }
            }
            Collections.sort(keyList);
            if( inverse )Collections.reverse(keyList);
            keys = keyList;
        }
        
        String join = conf.get("join",",\n");
        sb.append("{\n");
        int idx = -1;
        for( Object key : keys ){
            if( key==null ){
                continue;
            }
            if( idx>0 )sb.append(join);
            Pair<String,Boolean> res = descMapEntry(map, key, map.get(key));
            if( res.B() ){
                idx++;
                String keyValue = res.A();
                String keyName = key.toString();
                String text = descMapEntryText(keyName,keyValue);
                boolean indent = conf.get("indent",true);
                if( indent ){
                    text = Text.join(
                                Text.indent(Text.splitNewLines(text), "    "),
                                "\n"
                            );
                }
                sb.append(text);
            }
        }
        sb.append("}");
        
        return sb.toString();
    }
    
    private String descMapEntryText(String key,String value){
        String text = Text.template("{0} : {1}", key, value);
        return text;
    }
    
    private static Print print = new Print();
    
    protected Pair<String,Boolean> descMapEntry(Map map, Object key, Object val ){
        if( val==null )
            return new BasicPair<String, Boolean>(templates.get("null","null"), Boolean.TRUE);
        
        if( val instanceof String ){
            String txt = print.sprint(val);
            return new BasicPair<String, Boolean>(txt, Boolean.TRUE);
        }
        Class c = null;
        if( val!=null && SimpleTypes.isSimple(c) ){
            String txt = print.sprint(val);
            return new BasicPair<String, Boolean>(txt, Boolean.TRUE);
        }
        return new BasicPair<String, Boolean>(null,false);
    }
}
