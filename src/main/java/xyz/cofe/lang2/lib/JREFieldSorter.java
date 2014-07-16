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


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author gocha
 */
public class JREFieldSorter implements Comparator<java.lang.reflect.Field>
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JREFieldSorter.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    public static enum Field {
        Name,
        Type
    }
    
    public static class OrderField {
        protected Field field = null;
        protected boolean inverse = false;
        
        public OrderField(Field field,boolean inverse){
            if (field== null) {                
                throw new IllegalArgumentException("field==null");
            }
            this.field = field;
            this.inverse = inverse;
        }

        public Field getField() {
            return field;
        }

        public boolean isInverse() {
            return inverse;
        }
    }
    
    public JREFieldSorter(OrderField ... orderFields){
        if (orderFields== null) {            
            throw new IllegalArgumentException("orderFields==null");
        }
        if( orderFields.length>0 ){
            clearOrder();
            for( int i=0; i<orderFields.length; i++ ){
                OrderField of = orderFields[i];
                if (of == null) {                
                    throw new IllegalArgumentException("orderFields["+i+"]==null");
                }
                addOrder(of.getField(), of.isInverse());
            }
        }
    }
    
    protected Field[] order = new Field[]{ Field.Name, Field.Type };
    protected Map<Field,Boolean> orderInverse = null;
    protected Map<Field,Boolean> orderInverse(){
        if( orderInverse==null ){
            orderInverse = new EnumMap<Field, Boolean>(Field.class);
        }
        return orderInverse;
    }

    public Field[] getOrder() {
        return order;
    }

    public void setOrder(Field[] order) {
        if (order== null) {            
            throw new IllegalArgumentException("order==null");
        }
        for( int i=0; i<order.length; i++ ){
            if (order[i]== null) {                
                throw new IllegalArgumentException("order["+i+"]==null");
            }
        }
        this.order = order;
    }

    public Map<Field, Boolean> getOrderInverse() {
        return orderInverse();
    }
    
    public void resetOrder(){
        orderInverse = null;
        order = new Field[]{ Field.Name, Field.Type };
    }
    
    public void clearOrder(){
        orderInverse = null;
        order = new Field[]{};
    }
    
    public void addOrder(Field field,boolean inverse){
        if (field== null) {            
            throw new IllegalArgumentException("field==null");
        }
        Arrays.copyOf(order, order.length-1);
        order[order.length-1] = field;
        orderInverse().put(field, inverse);
    }

    @Override
    public int compare(java.lang.reflect.Field fieldA, java.lang.reflect.Field fieldB) {
        int cmpName = fieldA.getName().compareTo(fieldB.getName());
        
//        Class[] paramA = methodA.get();
//        Class[] paramB = methodB.getParameterTypes();
//        
//        Integer paramACount = paramA.length;
//        Integer paramBCount = paramB.length;
//        int cmpCount = paramACount.compareTo(paramBCount);
//        
//        int minParamCount = paramACount < paramBCount ? paramACount : paramBCount;
        int cmpType = fieldA.getType().getName().compareTo(fieldB.getType().getName());
//        for( int i=0; i<minParamCount; i++ ){
//            Class cA = paramA[i];
//            Class cB = paramB[i];
//            int c = cA.getName().compareTo(cB.getName());
//            if( c!=0 ){
//                cmpParamType = c;
//                break;
//            }
//        }
        
        for( int i=0; i<order.length; i++ ){
            Field f = order[i];
            switch(f){
                case Name:
                    if( cmpName!=0 ){
                        if( orderInverse().containsKey(f) && orderInverse().get(f) )
                            return -cmpName;
                        return cmpName;
                    }
                    break;
                case Type:
                    if( cmpType!=0 ){
                        if( orderInverse().containsKey(f) && orderInverse().get(f) )
                            return -cmpType;
                        return cmpType;
                    }
                    break;
//                case ParamType:
//                    if( cmpParamType!=0 ){
//                        if( orderInverse().containsKey(f) && orderInverse().get(f) )
//                            return -cmpParamType;
//                        return cmpParamType;
//                    }
//                    break;
            }
        }
        
//        if( cmpName!=0 )return cmpName;
//        if( cmpCount!=0 )return cmpCount;
//        if( cmpParamType!=0 )return cmpParamType;
        
        return 0;
    }
}
