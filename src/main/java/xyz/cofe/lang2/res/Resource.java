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
package xyz.cofe.lang2.res;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.files.FileUtil;

/**
 * Конфигурация
 * @author gocha
 */
public class Resource extends LinkedHashMap<String, String> {
    /**
     * Конструктор по умолчанию
     */
    public Resource(){
    }
    
    /**
     * Прочесть файл ресурсов
     * @param name Имя файла
     * @param locale Проверить локализованную версию
     */
    public Resource(String name,boolean locale){
        Properties p = readProperties(name, locale);
        if( p!=null ){
            for( String k : p.stringPropertyNames() ){
                String v = p.getProperty(k);
                put( k, v );
            }
        }
    }
    
    /**
     * Читает свойства заданные в ресурс-файле
     * @param name Имя файла
     * @param locale true - Проверять так же локализованную ферсию файла/
     * Например: name=abc.xml, то будет проверен файл abc_ru.xml и слит воедино
     * @return Свойства или null если нельзя прочесть
     */
    public static Properties readProperties(String name,boolean locale){
        if (name== null) {
            throw new IllegalArgumentException("name==null");
        }
        if( !locale )return readProperties(name);
        
        if( !name.contains("/") ){
            name = "/xyz/cofe/lang2/res/" + name;
        }
        
        Locale loc = Locale.getDefault();
        String locName=name;
        if( name.toLowerCase().endsWith(".xml") ){
            locName = name.substring(0,name.length()-4) + "_" + loc.getLanguage()+name.substring(name.length()-4);
        }
        
        URL urlBase = Resource.class.getResource(name);
        URL urlLocale = Resource.class.getResource(locName);
        Properties pBase = urlBase!=null ? readProperties(urlBase) : null;
        Properties pLoc = urlLocale!=null ? readProperties(urlLocale) : null;
        Properties res = null;
        if( pBase!=null )res = pBase;
        if( pLoc!=null ){
            if( res!=null ){
                res.putAll(pLoc);
            }else{
                res = pLoc;
            }
        }
        
        return res;
    }
    
    /**
     * Чтение свойств из файла ресурсов
     * @param name Имя файла
     * @return Свойства или null, если такого файла нет
     */
    public static Properties readProperties(String name){
        if (name== null) {
            throw new IllegalArgumentException("name==null");
        }
        if( !name.contains("/") )name = "/xyz/cofe/lang2/res/"+name;
        URL url = Resource.class.getResource(name);
        if( url==null )return null;
        return readProperties(url);
    }
    
    /**
     * Чтение свойств
     * @param resource Ссылка на ресурс
     * @return Свойства или null если не возможно прочесть
     */
    public static Properties readProperties(URL resource){
        if (resource== null) {
            throw new IllegalArgumentException("resource==null");
        }
        Properties props = null;
        InputStream inStream = null;
        try {
            inStream = resource.openStream();
            String t = resource.toString();
            if( t.toLowerCase().endsWith(".xml") ){
                props = FileUtil.readXMLProperties(inStream);
            }else if( t.toLowerCase().endsWith(".properties") ){
                props = FileUtil.readProperties(inStream);
            }
            return props;
        } catch (IOException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.FINE, null, ex);
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Resource.class.getName()).log(Level.FINE, null, ex);
            }
        }
        
        if( props==null ){
            props = new Properties();
        }
        return props;
    }
}
