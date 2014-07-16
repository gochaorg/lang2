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

import java.lang.reflect.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.Convertor;
import xyz.cofe.config.SimpleConfig;

/**
 * @author gocha
 */
public class DescList {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DescList.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DescList.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DescList.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DescList.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DescList.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DescList.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="constructors">
    protected SimpleConfig conf = null;
    protected SimpleConfig templates = null;
    
    public DescList(SimpleConfig config) {
        this.conf = config == null ? new SimpleConfig() : config;
        this.templates = conf.subset("templates.");
    }
    
    public DescList() {
        this.conf = new SimpleConfig();//owner.getConf().subset("descJre.");
        this.templates = conf.subset("templates.");
    }// </editor-fold>
    
    private Convertor itemConvertor = null;

    public Convertor getItemConvertor() {
        if( itemConvertor!=null )return itemConvertor;
        itemConvertor = new ToStringConv(templates.get("null", "null"));
        return itemConvertor;
    }

    public void setItemConvertor(Convertor itemConvertor) {
        this.itemConvertor = itemConvertor;
    }
    
    public String descList(Iterable src){
        if (src== null) {            
            throw new IllegalArgumentException("src==null");
        }
        StringBuilder out = new StringBuilder();
        sprintIterable(out, src, getItemConvertor());
        return out.toString();
    }
    
    public String descArray(Object array){
        if (array== null) {            
            throw new IllegalArgumentException("array==null");
        }
        StringBuilder out = new StringBuilder();
        sprintJREArray(out, array, getItemConvertor());
        return out.toString();
    }

    /**
     * Вывод JRE массива
     * @param out Куда выводить
     * @param array Сам массив
     */
    public static void sprintJREArray(StringBuilder out,Object array,Convertor<Object,String> item){
        if (out== null) {            
            throw new IllegalArgumentException("out==null");
        }
        if (array== null) {            
            throw new IllegalArgumentException("array==null");
        }
        if (item== null) {            
            throw new IllegalArgumentException("item==null");
        }
        int arrLen = Array.getLength(array);
        out.append("[");
        if( arrLen>0 ){
            out.append(" ");
            for( int i=0; i<arrLen; i++ ){
                if( i>0 ){
                    out.append(", ");
                }
                Object arrItem = Array.get(array, i);
                out.append(item.convert(arrItem));
            }
            out.append(" ");
        }
        out.append("]");
    }

    /**
     * Вывод JRE массива
     * @param out Куда выводить
     * @param array Сам массив
     */
    public static void sprintIterable(StringBuilder out,Iterable array,Convertor<Object,String> itemConvertor){
        if (out== null) {            
            throw new IllegalArgumentException("out==null");
        }
        if (array== null) {            
            throw new IllegalArgumentException("array==null");
        }
        if (itemConvertor== null) {            
            throw new IllegalArgumentException("itemConvertor==null");
        }
        out.append("[");
        int idx = -1;
        for( Object item : array ){
            idx++;
            if( idx==0 )out.append(" ");
            if( idx>0 )out.append(", ");
            out.append(itemConvertor.convert(item));
            if( idx==0 )out.append(" ");
        }
        out.append("]");
    }
}
