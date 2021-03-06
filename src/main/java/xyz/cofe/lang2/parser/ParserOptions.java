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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.Convertor;
import xyz.cofe.config.SimpleConfig;
import xyz.cofe.lang2.vm.op.opt.NIfOpt;

/**
 * Опции парсера/исполнения
 * @author gocha
 */
public class ParserOptions
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ParserOptions.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private SimpleConfig conf = null;
    private boolean storeProperties = false;
    private ClassLoader cl = null;
    
    public ParserOptions(){
        conf = new SimpleConfig();
    }
    
    public ParserOptions(ParserOptions src,boolean deepCopy){
        if( src!=null ){
            if( deepCopy ){
                if( src.conf!=null ){
                    conf = new SimpleConfig();
                    for( String k : src.conf.keySet() ){
                        String v = src.conf.get(k);
                        String cmt = src.conf.getComments().get(k);
                        String type = src.conf.getTypes().get(k);
                        if( v!=null ){
                            conf.put(k, v);
                            if( cmt!=null ){
                                conf.getComments().put(k, cmt);
                            }
                            if( type!=null ){
                                conf.getTypes().put(k, type);
                            }
                        }
                    }
                }else{
                    conf = new SimpleConfig();
                }
                storeProperties = src.storeProperties;
                cl = src.cl;
                catchNotDefVariableInIf = src.catchNotDefVariableInIf;
                ifOptions = src.ifOptions!=null ? src.ifOptions.clone() : ifOptions;
                ifNullAs = src.ifNullAs;
                ifZeroAs = src.ifZeroAs;
                ifNotBooleanAs = src.ifNotBooleanAs;
                pythMethod = src.pythMethod;
                
                javaTypeExtFields = new ArrayList<JavaTypeExtField>();
                if( src.javaTypeExtFields!=null ){
                    for( JavaTypeExtField ef : src.javaTypeExtFields ){
                        javaTypeExtFields.add(ef.clone());
                    }
                }
            }else{
                conf = src.conf;
                storeProperties = src.storeProperties;
                cl = src.cl;
                catchNotDefVariableInIf = src.catchNotDefVariableInIf;
                ifOptions = src.ifOptions;
                ifNullAs = src.ifNullAs;
                ifZeroAs = src.ifZeroAs;
                ifNotBooleanAs = src.ifNotBooleanAs;
                pythMethod = src.pythMethod;
                
                javaTypeExtFields = new ArrayList<JavaTypeExtField>();
                if( src.javaTypeExtFields!=null ){
                    for( JavaTypeExtField ef : src.javaTypeExtFields ){
                        javaTypeExtFields.add(ef);
                    }
                }
            }
        }else{
            conf = new SimpleConfig();
        }
    }
    
    @Override
    public ParserOptions clone(){
        return new ParserOptions(this,true);
    }

    public ParserOptions(SimpleConfig config){
        this.conf = config==null ? new SimpleConfig() : config;
        storeProperties = conf.get("storeProperties",false,
            "Сохранять свойства ParserOptions в конфигурации");
    }

    public ParserOptions(SimpleConfig config,ClassLoader cl){
        this.conf = config==null ? new SimpleConfig() : config;
        this.cl = cl;
        storeProperties = conf.get("storeProperties",false,
            "Сохранять свойства ParserOptions в конфигурации");
    }
    
    //<editor-fold defaultstate="collapsed" desc="catchNotDefVariableInIf">
    /**
     * Перехватывает не определенные перменные в условии <b>if</b> / <b>if else</b>
     * и возвращает false, если переменная не определенна.
     */
    private Boolean catchNotDefVariableInIf = null; //true;
    
    /**
     * Перехватывает не определенные перменные в условии <b>if</b> / <b>if else</b>
     * и возвращает false, если переменная не определенна.
     */
    public boolean isCatchNotDefVariableInIf() {
        if( catchNotDefVariableInIf==null ){
            catchNotDefVariableInIf = conf.get(
                "catchNotDefVariableInIf",true,
                    "Перехватывает не определенные перменные в условии if / if else\n"
                +   "и возвращает false, если переменная не определенна."
            );
        }
        return catchNotDefVariableInIf;
    }
    
    /**
     * Перехватывает не определенные перменные в условии <b>if</b> / <b>if else</b>
     * и возвращает false, если переменная не определенна.
     */
    public void setCatchNotDefVariableInIf(boolean catchNotDefVariableInIf) {
        this.catchNotDefVariableInIf = catchNotDefVariableInIf;
        if( storeProperties ){
            conf.set("catchNotDefVariableInIf",catchNotDefVariableInIf);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ifOptions">
    //<editor-fold defaultstate="collapsed" desc="ifOptions">
    /**
     * Поведение оператора if / if else
     */
    private NIfOpt ifOptions = null;
    
    public NIfOpt getIfOptions(){
        if( ifOptions!=null ){
            return ifOptions;
        }
        ifOptions = new NIfOpt();
        
        String nullAs = getIfNullAs();
        if( nullAs!=null ){
            if( nullAs.equalsIgnoreCase("exception") ){
                ifOptions.setNullAs(NIfOpt.CastAs.CastException);
            }else if( nullAs.equalsIgnoreCase("false") ){
                ifOptions.setNullAs(NIfOpt.CastAs.False);
            }else if( nullAs.equalsIgnoreCase("true") ){
                ifOptions.setNullAs(NIfOpt.CastAs.True);
            }
        }
        
        String zeroAs = getIfZeroAs();
        if( zeroAs!=null ){
            if( zeroAs.equalsIgnoreCase("exception") ){
                ifOptions.setZeroAs(NIfOpt.CastAs.CastException);
            }else if( zeroAs.equalsIgnoreCase("false") ){
                ifOptions.setZeroAs(NIfOpt.CastAs.False);
            }else if( zeroAs.equalsIgnoreCase("true") ){
                ifOptions.setZeroAs(NIfOpt.CastAs.True);
            }
        }
        
        String notBoolAs = getIfNotBooleanAs();
        if( notBoolAs!=null ){
            if( notBoolAs.equalsIgnoreCase("exception") ){
                ifOptions.setNotBooleanAs(NIfOpt.CastAs.CastException);
            }else if( notBoolAs.equalsIgnoreCase("false") ){
                ifOptions.setNotBooleanAs(NIfOpt.CastAs.False);
            }else if( notBoolAs.equalsIgnoreCase("true") ){
                ifOptions.setNotBooleanAs(NIfOpt.CastAs.True);
            }
        }
        
        return ifOptions;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ifNullAs">
    private String ifNullAs = null; //false;
    
    public String getIfNullAs() {
        if( ifNullAs!=null )return ifNullAs;
        ifNullAs = conf.get(
            "ifNullAs","false",
            "Поведение if при не bool условии, при null:\n"
                +   "exception - генерировать исключение\n"
                +   "false     - возвращать false\n"
                +   "true      - возвращать true\n"
        );
        return ifNullAs;
    }
    
    public void setIfNullAs(String nullAs) {
        this.ifNullAs = nullAs;
        this.ifOptions = null;
        if( storeProperties ){
            conf.set("ifNullAs",nullAs);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ifZeroAs">
    private String ifZeroAs = null; //false;
    
    public String getIfZeroAs() {
        if( ifZeroAs!=null )return ifZeroAs;
        ifZeroAs = conf.get(
            "ifZeroAs","false",
            "Поведение if при не bool условии, при нуле:\n"
                +   "exception - генерировать исключение\n"
                +   "false     - возвращать false\n"
                +   "true      - возвращать true\n"
        );
        return ifZeroAs;
    }
    
    public void setIfZeroAs(String zeroAs) {
        this.ifZeroAs = zeroAs;
        this.ifOptions = null;
        if( storeProperties ){
            conf.set("ifZeroAs",zeroAs);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ifNotBooleanAs">
    private String ifNotBooleanAs = null; //true;
    
    public String getIfNotBooleanAs() {
        if( ifNotBooleanAs!=null )return ifNotBooleanAs;
        ifNotBooleanAs = conf.get(
            "ifNotBooleanAs","true",
            "Поведение if при не bool условии, при других вариантах:\n"
                +   "exception - генерировать исключение\n"
                +   "false     - возвращать false\n"
                +   "true      - возвращать true\n"
        );
        return ifNotBooleanAs;
    }
    
    public void setIfNotBooleanAs(String notBooleanAs) {
        this.ifNotBooleanAs = notBooleanAs;
        this.ifOptions = null;
        if( storeProperties ){
            conf.set("ifNotBooleanAs",notBooleanAs);
        }
    }
    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="pythMethod">
    private Boolean pythMethod = null;

    /**
     * Использовать python способ вызова методов:<br/>
     * <b>Обычный способ:</b> 
     * <pre>
     * var object = { 
     *   a : 10,
     *   method : function( b ){
     *     this.a + b;
     *   }
     * }
     * object.method( 10 );
     * </pre>
     * <b> Python способ:</b>
     * <pre>
     * var object = { 
     *   a : 10,
     *   method : function( <b>self</b>, b ){
     *     self.a + b;
     *   }
     * }
     * object.method( 10 );
     * </pre>
     * @return true - способ python
     */
    public boolean isPythonLikeMethod() {
        if( pythMethod==null ){
            pythMethod = conf.get( "pythonLikeMethod", true,
                "Использовать python способ вызова методов:\n"+
                " Обычный способ:\n" +
                " var object = { \n" +
                "   a : 10,\n" +
                "   method : function( b ){\n" +
                "     this.a + b;\n" +
                "   }\n" +
                " }\n" +
                " object.method( 10 );\n" +
                " \n" +
                " \n" +
                "Python способ:\n" +
                " var object = { \n" +
                "   a : 10,\n" +
                "   method : function( self, b ){\n" +
                "     self.a + b;\n" +
                "   }\n" +
                " }\n" +
                " object.method( 10 );"
            );
        }
        return pythMethod;
    }
    
    /**
     * Использовать python способ вызова методов:<br/>
     * <b>Обычный способ:</b> 
     * <pre>
     * var object = { 
     *   a : 10,
     *   method : function( b ){
     *     this.a + b;
     *   }
     * }
     * object.method( 10 );
     * </pre>
     * <b> Python способ:</b>
     * <pre>
     * var object = { 
     *   a : 10,
     *   method : function( <b>self</b>, b ){
     *     self.a + b;
     *   }
     * }
     * object.method( 10 );
     * </pre>
     * @param pythMethod true - способ python
     */
    public void setPythonLikeMethod(boolean pythMethod) {
        this.pythMethod = pythMethod;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="JavaTypeExtField">
    /**
     * Перечень расширяемых Java типов
     */
    protected List<JavaTypeExtField> javaTypeExtFields = null;
    
    private static List<JavaTypeExtField> cacheJavaTypeExtFields = null;
    private static List<JavaTypeExtField> cacheJavaTypeExtFields(){
        if( cacheJavaTypeExtFields!=null )return cacheJavaTypeExtFields;
        List<JavaTypeExtField> ext = new xyz.cofe.lang2.res.JavaTypeExtensions().getJavaTypeExtFields();
        cacheJavaTypeExtFields = ext;
        return cacheJavaTypeExtFields;
    }
    
    /**
     * Перечень расширяемых Java типов
     * @return Перечень расширяемых java типов
     */
    public List<JavaTypeExtField> getJavaTypeExtFields() {
        if( javaTypeExtFields!=null ){
            return javaTypeExtFields;
        }
        
        javaTypeExtFields = new ArrayList<JavaTypeExtField>();
        
//        List<JavaTypeExtField> ext = cacheJavaTypeExtFields();
//        if( ext!=null )javaTypeExtFields.addAll(ext);
        
        SimpleConfig preset = conf.subset("javaTypeExtFields.");
        
        if( conf.get("javaTypeExtFields",true,
            "Добавить предопределенные расширения Java типов\n"
        +   "Расширение Java типов, это добавленные вертуальные методы/поля для существующих java типов.\n"
        +   "Расширение представляет из себя объект с поддержкой интерфеса org.gocha.collection.Convertor\n"
        +   "Пример:\n"
        +   "  package lang2.vm.ext;\n"
        +   "  public class StringSizeField implements Convertor {\n"
        +   "    @Override\n" 
        +   "    public Object convert(Object from) {\n" 
        +   "      if( !(from instanceof String) )throw new ClassCastException(\n"
        +   "            from!=null ? (from.getClass().getName() + \" to String\") :\n"
        +   "            \"null to String\"\n" 
        +   "            );\n" 
        +   "      String str = (String)from;\n" 
        +   "      return str.length();\n" 
        +   "    }\n" 
        +   "  }\n"
        +   "\n"
        +   "Это расширение вычисляет длину строки\n"
        +   "Теперь что бы добавить это расширение для типа String, как поле size, необходимо определить ключ/значеине:\n"
        +   "  javaTypeExtFields.java.lang.String#size=lang2.vm.ext.StringSizeField"
        ) ){
            
            String splitter = preset.get("fieldSplitter","@",
                    "Строка разделитель между именем класса и полем\n"
                +   "Допустим это \"собака\", пример:\n"
                +   "ключ: java.lang.String@size\n"
                +   "значение: lang2.vm.ext.StringSizeField"
                );
                        
            preset.get("java.lang.String"+splitter+"size","xyz.cofe.lang2.vm.ext.StringSizeField",
                    "Добавляет виртуальное поле size для строки, это поле вернет размер строки в символах\n"
                +   "var str = \"Abc\";"
                +   "str.size");
            
            preset.get("java.lang.String"+splitter+"length","xyz.cofe.lang2.vm.ext.StringSizeField",
                    "Добавляет виртуальное поле length для строки, это поле вернет размер строки в символах\n"
                +   "var str = \"Abc\";"
                +   "str.length");
            
            preset.get("java.lang.String"+splitter+"template","xyz.cofe.lang2.vm.ext.StringTemplateField",
                    "Добавляет виртуальный метод template для строки\n"
                +   "Этот метод будет использовать строку как шаблон для формироваия строки:\n"
                +   "Пример:\n"
                +   "  L2> var test=\"Hello {name} !\";\n"
                +   "  L2> test.template( { name : \"Ko\" } )\n"
                +   " OUT> Hello Ko !"
            );
            
            preset.get("java.util.List"+splitter+"size","xyz.cofe.lang2.vm.ext.ListSizeField",
                    "Добавляет виртуальное поле size для списка\n"
                +   "Пример:\n"
                +   "  L2> var test=[ 1, 2, 3 ];\n"
                +   "  L2> test.size\n"
                +   " OUT> 3"
            );
            preset.get("java.util.Map"+splitter+"size","xyz.cofe.lang2.vm.ext.MapSizeField",
                    "Добавляет виртуальное поле size для карт/объектов\n"
                +   "Пример:\n"
                +   "  L2> var test={ a:1, b:2 };\n"
                +   "  L2> test.size\n"
                +   " OUT> 2"
                );
            
            preset.get("java.util.Map"+splitter+"keys","xyz.cofe.lang2.vm.ext.MapKeysField",
                    "Добавляет виртуальное поле keys для карт/объектов\n"
                +   "Пример:\n"
                +   "  L2> var test={ a:1, b:2 };\n"
                +   "  L2> var k = test.keys;\n"
                +   "  L2> k.size;\n"
                +   " OUT> 2\n"
                +   "  L2> k[0];\n"
                +   " OUT> a\n"
                +   "  L2> k[1];\n"
                +   " OUT> b"
                );
        }
        
        List<JavaTypeExtField> ext = xyz.cofe.lang2.res.JavaTypeExtensions.getJavaTypeExtFields(preset,cl);
        if( ext!=null )javaTypeExtFields.addAll(ext);
        
        return javaTypeExtFields;
    }
    
    /**
     * Расширение java типа
     */
    public static class JavaTypeExtField {
        /**
         * Тип который расширяют
         */
        protected Class type = null;
        
        /**
         * Имя добавляемого поля
         */
        protected String fieldName = null;
        
        /**
         * Значение добовляемого поля, в метод convert передается расширяемый объект
         */
        protected Convertor fieldValue = null;
        
        /**
         * Конструктор
         * @param type Тип который расширяют
         * @param fieldName Имя добавляемого поля
         * @param fieldValue Значение добовляемого поля, в метод convert передается расширяемый объект
         */
        public JavaTypeExtField(Class type,String fieldName,Convertor fieldValue){
            if (type== null) {
                throw new IllegalArgumentException("type==null");
            }
            if (fieldName== null) {
                throw new IllegalArgumentException("fieldName==null");
            }
            if (fieldValue== null) {
                throw new IllegalArgumentException("fieldValue==null");
            }
            this.type = type;
            this.fieldName = fieldName;
            this.fieldValue = fieldValue;
        }
        
        /**
         * Конструктор копирования
         * @param src образец
         */
        public JavaTypeExtField(JavaTypeExtField src){
            if( src!=null ){
                this.type = src.type;
                this.fieldName = src.fieldName;
                this.fieldValue = src.fieldValue;
            }
        }
        
        /**
         * Клонирование объекта
         * @return клонированный объект
         */
        @Override
        public JavaTypeExtField clone(){
            return new JavaTypeExtField(this);
        }
        
        /**
         * Имя добавляемого поля
         * @return Имя добавляемого поля
         */
        public String getFieldName() {
            return fieldName;
        }
        
        /**
         * Значение добовляемого поля, в метод convert передается расширяемый объект
         * @return Значение
         */
        public Convertor getFieldValue() {
            return fieldValue;
        }
        
        /**
         * Тип который расширяют
         * @return java тип
         */
        public Class getType() {
            return type;
        }
    }
    //</editor-fold>
}
