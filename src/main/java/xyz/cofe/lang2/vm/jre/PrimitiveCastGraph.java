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
 * Граф преобразования примитивов
 * @author gocha
 */
public class PrimitiveCastGraph
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(PrimitiveCastGraph.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Распаковка / Упаковка примитивных типов">
    // <editor-fold defaultstate="collapsed" desc="byte -> Byte, Byte -> byte">
    public final static Caster byte2Byte = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return (Byte) from;
        }

        @Override
        public String toString() {
            return "byte2Byte";
        }
    };
    public final static Caster Byte2byte = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return (byte) ((Byte) from);
        }        
        
        @Override
        public String toString() {
            return "Byte2byte";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="short -> Short, Short -> short">
    public final static Caster short2Short = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return (Short) from;
        }        
        
        @Override
        public String toString() {
            return "short2Short";
        }
    };
    public final static Caster Short2short = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return (short) ((Short) from);
        }        
        
        @Override
        public String toString() {
            return "Short2short";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="int -> Integer, Integer -> int">
    public final static Caster int2Integer = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Integer) from;
        }        
        
        @Override
        public String toString() {
            return "int2Integer";
        }
    };
    public final static Caster Integer2int = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (int) ((Integer) from);
        }        
        
        @Override
        public String toString() {
            return "Integer2int";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="long -> Long, Long -> long">
    public final static Caster long2Long = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Long) from;
        }        
        
        @Override
        public String toString() {
            return "long2Long";
        }
    };
    public final static Caster Long2long = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (long) ((Long) from);
        }        
        
        @Override
        public String toString() {
            return "Long2long";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="float -> Float, Float -> float">
    public final static Caster float2Float = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Long) from;
        }        
        
        @Override
        public String toString() {
            return "float2Float";
        }
    };
    public final static Caster Float2float = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (long) ((Long) from);
        }        
        
        @Override
        public String toString() {
            return "Float2float";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="double -> Double, Double -> double">
    public final static Caster double2Double = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Double) from;
        }        
        
        @Override
        public String toString() {
            return "double2Double";
        }
    };
    public final static Caster Double2double = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (double) ((Double) from);
        }        
        
        @Override
        public String toString() {
            return "Double2double";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="boolean -> Boolean, Boolean -> boolean">
    public final static Caster boolean2Boolean = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Boolean) from;
        }        
        
        @Override
        public String toString() {
            return "boolean2Boolean";
        }
    };
    public final static Caster Boolean2boolean = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (boolean) ((Boolean) from);
        }        
        
        @Override
        public String toString() {
            return "Boolean2boolean";
        }
    };// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="char -> Char, Char -> char">
    public final static Caster char2Char = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (Character) from;
        }        
        
        @Override
        public String toString() {
            return "char2Char";
        }
    };
    public final static Caster Char2char = new Caster()
    {        
        
        @Override
        public Object cast(Object from) {
            return (char) ((Character) from);
        }        
        
        @Override
        public String toString() {
            return "Char2char";
        }
    };// </editor-fold>
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="char -> int, int -> Char">
    public final static Caster char2int = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return (int) ((char) ((Character) from));
        }        

        @Override
        public String toString() {
            return "char2int";
        }
    };
    public final static Caster int2char = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return (char) ((int) ((Integer) from));
        }        

        @Override
        public String toString() {
            return "int2char";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Byte -> Number, Number -> Byte">
    public final static Caster Byte2Number = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Byte2Number";
        }
    };
    public final static Caster Number2byte = new Caster()
    {        

        @Override
        public Object cast(Object from) {
            return ((Number) from).byteValue();
        }        

        @Override
        public String toString() {
            return "Number2byte";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Short -> Number, Number -> Short">
    public final static Caster Short2Number = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Short2Number";
        }
    };
    public final static Caster Number2short = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from).shortValue();
        }        

        @Override
        public String toString() {
            return "Number2short";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Integer -> Number, Number -> Integer">
    public final static Caster Int2Number = new Caster()
    {
        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Int2Number";
        }
    };
    public final static Caster Number2int = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from).intValue();
        }        

        @Override
        public String toString() {
            return "Number2int";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Long -> Number, Number -> Long">
    public final static Caster Long2Number = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Long2Number";
        }
    };
    public final static Caster Number2long = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from).longValue();
        }        

        @Override
        public String toString() {
            return "Number2long";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Float -> Number, Number -> Float">
    public final static Caster Float2Number = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Float2Number";
        }
    };
    public final static Caster Number2float = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from).floatValue();
        }        

        @Override
        public String toString() {
            return "Number2float";
        }
    };// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Double -> Number, Number -> Double">
    public final static Caster Double2Number = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from);
        }        

        @Override
        public String toString() {
            return "Double2Number";
        }
    };
    public final static Caster Number2double = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            return ((Number) from).doubleValue();
        }        

        @Override
        public String toString() {
            return "Number2double";
        }
    };// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="String -> char Char -> String String -> Char">
    public final static Caster String2char = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            String str = (String) from;
            if( str.length()>0 )return str.charAt(0);
            return (char)0;
        }        

        @Override
        public String toString() {
            return "String2char";
        }
    };
    public final static Caster String2Char = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            String str = (String) from;
            if( str.length()>0 )return (Character)str.charAt(0);
            return (char)0;
        }        

        @Override
        public String toString() {
            return "String2Char";
        }
    };
    public final static Caster Char2String = new Caster()
    {        
        @Override
        public Object cast(Object from) {
            Character c = (Character)from;
            return c.toString();
        }        

        @Override
        public String toString() {
            return "Char2String";
        }
    };// </editor-fold>
    
    public static CastGraph createCastGraph(){
        CastGraph castGraph = new CastGraph();
        
        // Прямые преобразования byte -> Byte, int -> Integer
        castGraph.set(byte.class, Byte.class, new WeightedCaster(byte2Byte,0) );
        castGraph.set(Byte.class, byte.class, new WeightedCaster(Byte2byte,0) );
        
        castGraph.set(short.class, Short.class, new WeightedCaster(short2Short,0) );
        castGraph.set(Short.class, short.class, new WeightedCaster(Short2short,0) );
        
        castGraph.set(int.class, Integer.class, new WeightedCaster(int2Integer,0) );
        castGraph.set(Integer.class, int.class, new WeightedCaster(Integer2int,0) );
        
        castGraph.set(long.class, Long.class, new WeightedCaster(long2Long,0) );
        castGraph.set(Long.class, long.class, new WeightedCaster(Long2long,0) );
        
        castGraph.set(float.class, Float.class, new WeightedCaster(float2Float,0) );
        castGraph.set(Float.class, float.class, new WeightedCaster(Float2float,0) );
        
        castGraph.set(double.class, Double.class, new WeightedCaster(double2Double,0) );
        castGraph.set(Double.class, double.class, new WeightedCaster(Double2double,0) );
        
        castGraph.set(boolean.class, Boolean.class, new WeightedCaster(boolean2Boolean,0) );
        castGraph.set(Boolean.class, boolean.class, new WeightedCaster(Boolean2boolean,0) );
        
        castGraph.set(char.class, Character.class, new WeightedCaster(char2Char,0) );
        castGraph.set(Character.class, char.class, new WeightedCaster(Char2char,0) );
        
        // Преобразования тип char -> int, string -> char
        castGraph.set(char.class, int.class, new WeightedCaster(char2int,1) );
        castGraph.set(int.class, char.class, new WeightedCaster(int2char,1) );
        
        castGraph.set(Byte.class, Number.class, new WeightedCaster(Byte2Number,1) );
        castGraph.set(Number.class, byte.class, new WeightedCaster(Number2byte,1) );
        
        castGraph.set(Short.class, Number.class, new WeightedCaster(Short2Number,1) );
        castGraph.set(Number.class, short.class, new WeightedCaster(Number2short,1) );
        
        castGraph.set(Integer.class, Number.class, new WeightedCaster(Int2Number,1) );
        castGraph.set(Number.class, int.class, new WeightedCaster(Number2int,1) );
        
        castGraph.set(Long.class, Number.class, new WeightedCaster(Long2Number,1) );
        castGraph.set(Number.class, long.class, new WeightedCaster(Number2long,1) );
        
        castGraph.set(Float.class, Number.class, new WeightedCaster(Float2Number,1) );
        castGraph.set(Number.class, float.class, new WeightedCaster(Number2float,1) );
        
        castGraph.set(Double.class, Number.class, new WeightedCaster(Double2Number,1) );
        castGraph.set(Number.class, double.class, new WeightedCaster(Number2double,1) );

        castGraph.set(Character.class, String.class, new WeightedCaster(Char2String,1) );
        castGraph.set(String.class, Character.class, new WeightedCaster(String2Char,1) );
        castGraph.set(String.class, char.class, new WeightedCaster(String2char,1) );
        return castGraph;
    }
}
