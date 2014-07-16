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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.ParserOptions;
import xyz.cofe.collection.Convertor;
import xyz.cofe.common.Text;
import xyz.cofe.config.SimpleConfig;

/**
 * Перечень расширений Java
 * @author gocha
 */
public class JavaTypeExtensions extends SimpleConfig {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор, по умолчанию читает файл ресурсов lang2/res/javaTypeExtensions.xml
     */
    public JavaTypeExtensions(){
        super("javaTypeExtensions.xml", false);
    }
    
//    public JavaTypeExtensions(

//    /**
//     * Конструктор
//     * @param prefix Префикс в именах ключей
//     * @param conf Исходная карта
//     */
//    public JavaTypeExtensions(String prefix,SimpleConfig conf){
//        super(prefix, conf);
//    }

    /**
     * Выделяет последовательность из карты (ключ/значение).
     * Элемент последовательности состоит из троек: 
     * <ol>
     * <li>Имя класса расширяемого, </li>
     * <li>Имя добавляемого поля, </li>
     * <li>Класс Convertor который расширяет объект указанного класса</li>
     * </ol>
     * @param conf Карта ключ/значение
     * @param className_Field_Splitter Строка разделитель между раширяемым классом и полем в имени ключа
     * @return Список троек
     */
    public static List<String[]> getExtensionsList(SimpleConfig conf, String className_Field_Splitter){
        if( conf==null )throw new IllegalArgumentException( "conf==null" );
        if( className_Field_Splitter==null )throw new IllegalArgumentException( "className_Field_Splitter==null" );
        List<String[]> res = new ArrayList<String[]>();
        for( String key : conf.keySet() ){
            if( key==null )return null;
            String val = conf.get(key);
            if( key.contains(className_Field_Splitter) ){
                String[] clssField = Text.split(key, className_Field_Splitter);
                if( clssField!=null && clssField.length==2 ){
                    String clssName = clssField[0].trim();
                    String field = clssField[1].trim();
                    String conv = val!=null ? val.trim() : null;
                    if( clssName.length()>0 && field.length()>0 && conv!=null && conv.length()>0 ){
                        String[] ext = new String[]{clssName,field,conv};
                        res.add( ext );
                    }
                }
            }
        }
        return res;
    }
    
    /**
     * Выделяет последовательность из карты (ключ/значение).
     * Элемент последовательности состоит из троек: 
     * <ol>
     * <li>Имя класса расширяемого, </li>
     * <li>Имя добавляемого поля, </li>
     * <li>Класс Convertor который расширяет объект указанного класса</li>
     * </ol>
     * @param conf Карта ключ/значение
     * @return Список троек
     */
    public static List<String[]> getExtensionsList(SimpleConfig conf){
        if( conf==null )throw new IllegalArgumentException( "conf==null" );
        return getExtensionsList(conf, conf.get("fieldSplitter","#",
                "Строка разделитель между именем класса и полем\n"
            +   "Обычно это решетка, пример:\n"
            +   "ключ: java.lang.String#size\n"
            +   "значение: lang2.vm.ext.StringSizeField"
        ));
    }
    
    public List<ParserOptions.JavaTypeExtField> getJavaTypeExtFields(){
        return getJavaTypeExtFields(this, this.getClass().getClassLoader());
    }

    public static List<ParserOptions.JavaTypeExtField> getJavaTypeExtFields(SimpleConfig conf,ClassLoader cl){
        if( conf==null )throw new IllegalArgumentException( "conf==null" );
        if( cl==null ){
            cl = JavaTypeExtensions.class.getClassLoader();
        }
        
        logFine("getJavaTypeExtFields");

        List<ParserOptions.JavaTypeExtField> res = new ArrayList<ParserOptions.JavaTypeExtField>();

        Map<String,Convertor> convertorMap = new HashMap<String, Convertor>();
        Map<String,Class> javaTypeMap = new HashMap<String, Class>();

        Set<String> failedConvertors = new HashSet<String>();
        Set<String> failedJavaTypes = new HashSet<String>();

        List<String[]> extList = getExtensionsList(conf);
        for( String[] _ent : extList ){
            String convClassName = _ent[2];
            Convertor convertor = null;

            String javaClassName = _ent[0];
            Class javaClass = null;

            String fieldName = _ent[1];

            if( failedJavaTypes.contains(javaClassName) )continue;
            if( failedConvertors.contains(convClassName) )continue;

            if( !convertorMap.containsKey(convClassName) ){
                try {
                    logFiner("create convertor {0}", convClassName);
                    Class cls = Class.forName(convClassName,true,cl);
                    Object conv = cls.newInstance();
                    if( conv instanceof Convertor ){
                        convertorMap.put(convClassName, (Convertor)conv);
                        convertor = (Convertor)conv;
                    }else{
                        throw new ClassCastException("cast to Convertor");
                    }
                } catch (ClassCastException ex){
                    Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
                    failedConvertors.add( convClassName );
                    continue;
                } catch (InstantiationException ex) {
                    Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
                    failedConvertors.add( convClassName );
                    continue;
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
                    failedConvertors.add( convClassName );
                    continue;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
                    failedConvertors.add( convClassName );
                    continue;
                }
            }else{
                convertor = convertorMap.get(convClassName);
            }

            if( !javaTypeMap.containsKey(javaClassName) ){
                try {
                    logFiner("get java type {0}", javaClassName);
                    Class cls = Class.forName(javaClassName,true,cl);
                    javaTypeMap.put(javaClassName, cls);
                    javaClass = cls;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JavaTypeExtensions.class.getName()).log(Level.SEVERE, null, ex);
                    failedJavaTypes.add( javaClassName );
                }
            }else{
                javaClass = javaTypeMap.get(javaClassName);
            }

            ParserOptions.JavaTypeExtField jtef = new ParserOptions.JavaTypeExtField(javaClass, fieldName, convertor);
            res.add(jtef);
            logFiner( "created JavaTypeExtField( type={0} field={1} convertor={2}",javaClassName, fieldName, convClassName );
        }

        return res;
    }
}
