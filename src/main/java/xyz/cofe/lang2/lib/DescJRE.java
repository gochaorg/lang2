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


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Pair;
import xyz.cofe.text.IndentStackWriter;
import xyz.cofe.text.Template;
import xyz.cofe.text.TemplateParser;
import xyz.cofe.text.Text;
import xyz.cofe.types.SimpleTypes;
import xyz.cofe.config.SimpleConfig;

/**
 * @author gocha
 */
public class DescJRE {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DescJRE.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DescJRE.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DescJRE.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DescJRE.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DescJRE.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DescJRE.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="construcotrs">
    protected SimpleConfig conf = null;
    protected SimpleConfig templates = null;
    
    public DescJRE(SimpleConfig config) {
        this.conf = config == null ? new SimpleConfig() : config;
        this.templates = conf.subset("templates.");
    }
    
    public DescJRE() {
        this.conf = new SimpleConfig();//owner.getConf().subset("descJre.");
        this.templates = conf.subset("templates.");
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="map">
    private Text.StringMap map = null;

    protected void setStringMap(Text.StringMap map) {
        this.map = map;
    }
    
    private Text.StringMap map(String key, Object val) {
        return map().map(key, val);
    }

    private Text.StringMap map() {
        if (map == null)map = new Text.StringMap();
        return map;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="convertors">
    protected Convertor createPropertiesConvertor(
            final IndentStackWriter out,
            final Object obj,
            final Class cls) {
        return new Convertor()
        {

            @Override
            public Object convert(Object from) {
                String properties = null;
                out.push();
                descJREObject_Properties(out, obj, cls);
                properties = out.pop();
                properties = Text.indent(properties, "\n", "    ");
                return properties;
            }
        };
    }
    
    protected Convertor createMethodsConvertor(
            final IndentStackWriter out,
            final Object obj,
            final Class cls) {
        return new Convertor()
        {

            @Override
            public Object convert(Object from) {
                String methods = null;
                out.push();
                descJREObject_Methods(out, obj, cls);
                methods = out.pop();
                methods = Text.indent(methods, "\n", "    ");
                return methods;
            }
        };
    }
    
    protected Convertor createFieldsConvertor(
            final IndentStackWriter out,
            final Object obj,
            final Class cls) {
        return new Convertor()
        {

            @Override
            public Object convert(Object from) {
                out.push();
                descJREObject_Fields(out, obj, cls);
                String fields = out.pop();
                fields = Text.indent(fields, "\n", "    ");
                return fields;
            }
        };
    }
    
    protected Convertor createClassNameConvertor(final Class cls) {
        return new Convertor()
        {

            @Override
            public Object convert(Object from) {
                return cls.getName();
            }
        };
    }
    
    protected Convertor createDescJREDescriptor(final Object obj) {
        return new Convertor()
        {

            protected IndentStackWriter out = new IndentStackWriter(new OutputStreamWriter(System.out));
            
            @Override
            public Object convert(Object varName) {
                Class cls = obj.getClass();
                if (varName != null) {
                    if (varName.equals("className")) {
                        return createClassNameConvertor(cls).convert(varName);
                    }
                    if (varName.equals("properties")) {
                        return createPropertiesConvertor(out, obj, cls).
                                convert(varName);
                    }
                    if (varName.equals("methods")) {
                        return createMethodsConvertor(out, obj, cls).convert(varName);
                    }
                    if (varName.equals("fields")) {
                        return createFieldsConvertor(out, obj, cls).convert(varName);
                    }
                }
                return "";
            }
        };
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="classTemplate">
    private String classTemplateString = null;
    
    public String getClassTemplate() {
        if (classTemplateString != null)
            return classTemplateString;
        classTemplateString = templates.get("class",
                "class {className} {{\n"
                + "{properties}\n\n"
                + "{methods}\n\n"
                + "{fields}\n"
                + "}", "Шаблон Описания Java класса");
        return classTemplateString;
    }
    
    public void setClassTemplate(String classTemplate) {
        this.classTemplateString = classTemplate;
        this.classTemplate = null;
    }
    
    private Template classTemplate = null;
    protected Template classTemplate(){
        if( classTemplate!=null )return classTemplate;
        classTemplate = Template.parse( getClassTemplate(), new TemplateParser.Options(true) );
        return classTemplate;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="emptyClassTemplate">
    private String emptyClassTemplateString = null;
    
    public String getEmptyClassTemplate() {
        if (emptyClassTemplateString != null)
            return emptyClassTemplateString;
        emptyClassTemplateString = templates.get("emptyClass", "{{ }");
        return emptyClassTemplateString;
    }
    
    public void setEmptyClassTemplate(String classTemplate) {
        this.emptyClassTemplateString = classTemplate;
        this.emptyClassTemplate = null;
    }
    
    private Template emptyClassTemplate = null;
    protected Template emptyClassTemplate(){
        if( emptyClassTemplate!=null )return emptyClassTemplate;
        emptyClassTemplate = Template.parse( getEmptyClassTemplate(), new TemplateParser.Options(true) );
        return emptyClassTemplate;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="encodeStringConst()">
    private String encodeStringConst(String text) {
        int maxStringConstLen = conf.get("maxStringConstLen", 50);
        boolean cropped = false;
        if (maxStringConstLen > 0 && text.length() > maxStringConstLen) {
            text = text.substring(0, maxStringConstLen);
            cropped = true;
        }
        text = Text.encodeStringConstant(text);
        if (cropped) {
            text +=
                    templates.get("stringConstOverflow", " // Текст большой, не влез");
        }
        return text;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="рекурсия при обследовании объектов">
    private Stack<Object> recusiveStack = new Stack<Object>();
    
    protected Stack<Object> recusiveStack(){ return recusiveStack; }
    
    // <editor-fold defaultstate="collapsed" desc="recursive">
    private Boolean recursive = null;
    
    protected boolean recursive() {
        if (recursive == null) {
            recursive = conf.get("recursive", true);
        }
        return recursive;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="recusiveMaxLevel">
    private Integer recusiveMaxLevel = null;
    
    protected int recusiveMaxLevel() {
        if (recusiveMaxLevel == null)
            recusiveMaxLevel = conf.get("recusiveMaxLevel", 1);
        return recusiveMaxLevel;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="recusiveObjectTemplate">
    private String recusiveObjectTemplateString = null;

    protected String getRecursiveObjectTemplate() {
        if (recusiveObjectTemplateString == null) {
            recusiveObjectTemplateString = templates.get("recusive", "{{\n"
                    //+ "{properties}\n\n"
                    //+ "{methods}\n\n"
                    //+ "{fields}\n"
                    + "{properties}\n"
                    + "}");
        }
        return recusiveObjectTemplateString;
    }

    protected void setRecursiveObjectTemplate(String text) {
        recusiveObjectTemplateString = text;
        recusiveObjectTemplate = null;
    }
    private Template recusiveObjectTemplate = null;

    protected Template recusiveObjectTemplate() {
        if (recusiveObjectTemplate != null)
            return recusiveObjectTemplate;
        recusiveObjectTemplate = Template.parse(getRecursiveObjectTemplate(), new TemplateParser.Options(true));
        return recusiveObjectTemplate;
    }// </editor-fold>
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="desc object">
    public String descJREObject(Object obj) {
        if (obj == null)
            throw new IllegalArgumentException("obj==null");
        return descJREObject(obj, classTemplate(), emptyClassTemplate());
    }
    
    protected String descJREObject(Object obj, Template tmpl, Template tmplEmpty) {
        if (obj == null)
            throw new IllegalArgumentException("obj==null");
        if (tmpl == null)
            throw new IllegalArgumentException("tmpl==null");
        String objText = null;
        recusiveStack.push(obj);
        int recMax = recusiveMaxLevel();
        int currentRec = recusiveStack.size();
        if (recMax > 0 && currentRec > recMax) {
            objText = tmplEmpty.eval(createDescJREDescriptor(obj));
        } else {
            objText = tmpl.eval(createDescJREDescriptor(obj));
        }
        recusiveStack.pop();
        return objText;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="propertiesTemplate">
    private String propertiesTemplateString = null;
    
    private String getPropertiesTemplate() {
        if (propertiesTemplateString != null)
            return propertiesTemplateString;
        propertiesTemplateString = templates.get("properties",
                "// Properties:\n{propertyList}", "Шаблон описания Java свойства");
        return propertiesTemplateString;
    }
    
    private void setPropertiesTemplate(String propertiesTemplateString) {
        this.propertiesTemplateString = propertiesTemplateString;
        this.propertiesTemplateString = null;
    }
    
//    private Template propertiesTemplate = null;
//    protected Template propertiesTemplate(){
//        if( propertiesTemplate!=null )return propertiesTemplate;
//        propertiesTemplate = Template.parse( getPropertiesTemplate(), new TemplateParser.Options(true) );
//        return propertiesTemplate;
//    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="desc properties">
    private PropertyDescriptor[] descJREObject_Properties(IndentStackWriter out, Object obj, Class cls) {
        try {
            BeanInfo inf = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] _desc = inf.getPropertyDescriptors();
            
            ArrayList<PropertyDescriptor> propertyList = new ArrayList<PropertyDescriptor>(Arrays.asList(_desc));
            Collections.sort(propertyList, new JREPropertySorter());
            
            ArrayList<String> list = new ArrayList<String>();
            for (PropertyDescriptor pd : propertyList) {
                out.push();
                descJREObject_Property(out, obj, cls, pd);
                String line = out.pop();
                if (line != null && line.trim().length() > 0) {
                    list.add(line);
                }
            }
            map("propertyList", Text.join(list, "\n"));
            out.template(getPropertiesTemplate(), map());
            return _desc;
        } catch (IntrospectionException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Pair<Object, Boolean> readPropertyValue(Object obj, Class cls, PropertyDescriptor prop) {
        Method mGet = prop.getReadMethod();
        if (mGet == null)
            return new BasicPair<Object, Boolean>(null, false);
        
        try {
            Object val = null;
            val = mGet.invoke(obj);
            if (val == null)
                return new BasicPair<Object, Boolean>(null, true);
            
            Class valC = val.getClass();
            boolean isSimple = SimpleTypes.isSimple(valC);
            
            if (isSimple) {
                return new BasicPair<Object, Boolean>(val, true);
            }
            
            if (val instanceof String) {
                return new BasicPair<Object, Boolean>(
                        encodeStringConst((String) val), true);
            }
            
            boolean recAllow = recursive();
            int recMax = recusiveMaxLevel();
            int recCur = recusiveStack().size();
            
            if( recAllow ){
                if( recMax>0 ){
                    if( recCur<recMax ){
                        String resText = descJREObject(val, recusiveObjectTemplate(), emptyClassTemplate());
                        return new BasicPair<Object, Boolean>(resText, resText!=null);
                    }
                }else{
                    String resText = descJREObject(val, recusiveObjectTemplate(), emptyClassTemplate());
                    return new BasicPair<Object, Boolean>(resText, resText!=null);
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DescJRE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DescJRE.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DescJRE.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new BasicPair<Object, Boolean>(null, false);
    }
    
    private void descJREObject_Property(IndentStackWriter out, Object obj, Class cls, PropertyDescriptor prop) {
        String name = prop.getName();
        map("name", name);
        
        Class type = prop.getPropertyType();
        if (type != null) {
            map("type", type.getName());
            Pair<Object, Boolean> succ = readPropertyValue(obj, cls, prop);
            if (succ.B()) {
                map("value", succ.A());
                map("type", type.getName());
                map("name", name);
                out.template(
                        templates.get("propertyWithValue", "{name} : {type} = {value}"), map());
            } else {
                map("type", type.getName());
                map("name", name);
                out.template(
                        templates.get("property", "{name} : {type}"), map());
            }
        } else {
            Method mGet = prop.getReadMethod();
            String getDesc = null;
            
            Method mSet = prop.getWriteMethod();
            String setDesc = null;
            
            if (mGet != null) {
                out.push();
                descJREObject_Method(out, obj, cls, mGet);
                getDesc = out.pop();
            }
            
            if (mSet != null) {
                out.push();
                descJREObject_Method(out, obj, cls, mSet);
                getDesc = out.pop();
            }
            
            map("name", name);
            map("get", getDesc);
            map("set", setDesc);
            if (getDesc != null && setDesc != null) {
                out.template("// property: {name}\n{get}\n{set}", map());
            } else if (getDesc == null && setDesc != null) {
                out.template("// property: {name}\n{set}", map());
            } else if (getDesc != null && setDesc == null) {
                out.template("// property: {name}\n{get}", map());
            } else {
            }
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="desc fields">
    private void descJREObject_Fields(IndentStackWriter out, Object obj, Class cls) {
        Field[] fields = cls.getFields();
        ArrayList<Field> fieldList = new ArrayList<Field>(Arrays.asList(fields));
        Collections.sort(fieldList, new JREFieldSorter());
        
        ArrayList<String> list = new ArrayList<String>();
        for (Field field : fieldList) {
            out.push();
            descJREObject_Field(out, obj, cls, field);
            list.add(out.pop());
        }
        map("fieldList", Text.join(list, "\n"));
        out.template(
                templates.get("fields", "// Fields:\n{fieldList}"), map());
    }
    
    private void descJREObject_Field(IndentStackWriter out, Object obj, Class cls, Field field) {
        String name = field.getName();
        Class type = field.getType();
        boolean isSimple = SimpleTypes.isSimple(type);
        
        map("name", name);
        map("type", type.getName());
        if (isSimple) {
            try {
                Object val = field.get(obj);
                map("value", val);
                out.template(
                        templates.get("fieldWithValue", "{name} : {type} = {value}"), map());
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
                out.template(
                        templates.get("field", "{name} : {type}"), map());
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
                out.template("{name} : {type}", map());
            }
        } else {
            out.template(
                    templates.get("field", "{name} : {type}"), map());
        }
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="desc methods">
    private void descJREObject_Methods(IndentStackWriter out, Object obj, Class cls) {
        Method[] _methods = cls.getMethods();
        ArrayList<Method> methodList = new ArrayList<Method>();
        methodList.addAll(Arrays.asList(_methods));
        
        BeanInfo inf;
        try {
            inf = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] _desc = inf.getPropertyDescriptors();
            for (PropertyDescriptor prop : _desc) {
                Method mGet = prop.getReadMethod();
                Method mSet = prop.getWriteMethod();
                
                if (mGet != null)
                    methodList.remove(mGet);
                if (mSet != null)
                    methodList.remove(mSet);
            }
        } catch (IntrospectionException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collections.sort(methodList, new JREMethodSorter());
        
        ArrayList<String> list = new ArrayList<String>();
        for (Method m : methodList) {
            out.push();
            descJREObject_Method(out, obj, cls, m);
            list.add(out.pop());
        }
        map("methodList", Text.join(list, "\n"));
        out.template(
                templates.get("methods", "// Methods:\n{methodList}"), map());
    }
    
    private void descJREObject_Method(IndentStackWriter out, Object obj, Class cls, Method method) {
        Class retType = method.getReturnType();
        Class[] paramTypes = method.getParameterTypes();
        
        ArrayList<String> paramList = new ArrayList<String>();
        for (int i = 0; i < paramTypes.length; i++) {
            out.push();
            Class paramType = paramTypes[i];
            map("type", paramType.getName());
            map("arg", "arg" + i);
            out.template(
                    templates.get("param", "{arg} : {type}"),
                    map());
            paramList.add(out.pop());
        }
        
        String name = method.getName();
        
        map("name", name);
        map("type", retType.getName());
        map("params", Text.join(paramList, ", "));
        
        out.template(
                templates.get("method", "{name}({params}) : {type}"), map());
    }
    // </editor-fold>
}
