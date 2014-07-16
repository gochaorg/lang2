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
package xyz.cofe.lang2.vm.jre;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Примитивы JRE
 * @author gocha
 */
public class Primitive
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(Primitive.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(Primitive.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(Primitive.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(Primitive.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(Primitive.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(Primitive.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Primitive(Primitive source) {
        if (source == null) {
            throw new IllegalArgumentException("source==null");
        }
        this.allowNullType = source.allowNullType;
        this.defNotNullValue = source.defNotNullValue;
        this.notNullType = source.notNullType;
    }

    public Primitive(Class notNullClass, Class allowNullClass, Object defNotNullValue) {
        this.notNullType = notNullClass;
        this.allowNullType = allowNullClass;
        this.defNotNullValue = defNotNullValue;
    }

    private Class notNullType;
    private Class allowNullType;
    private Object defNotNullValue;

    public Class getNotNullType() {
        return notNullType;
    }

    public Class getAllowNullType() {
        return allowNullType;
    }

    public Object getDefNotNullValue() {
        return defNotNullValue;
    }

    public static TargetPrimitive isPrimitive(Class argumentType) {
        if (argumentType == null) {
            throw new IllegalArgumentException("argumentType==null");
        }
        for (Primitive p : primitives) {
            if (p.allowNullType.equals(argumentType))
                return new TargetPrimitive(p, true);
            if (p.notNullType.equals(argumentType))
                return new TargetPrimitive(p, false);
        }
        return null;
    }

    private static final Primitive[] primitives = {
        new Primitive(boolean.class, Boolean.class, (boolean) false),
        new Primitive(char.class, Character.class, (char) 0),
        new Primitive(byte.class, Byte.class, (byte) 0),
        new Primitive(short.class, Short.class, (short) 0),
        new Primitive(int.class, Integer.class, (int) 0),
        new Primitive(long.class, Long.class, (long) 0),
        new Primitive(float.class, Float.class, (float) 0),
        new Primitive(double.class, Double.class, (double) 0),};
}