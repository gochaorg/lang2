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
 * Передаваемый аргумент в функцию, 
 * возможно его преобразование через объект Caster
 * @author gocha
 */
public class CallableArgument
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallableArgument.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор - преобразование и передача на прямую не возможно,
     * т.к. нет подходящего преобразователя типа
     * @param e Ошибка преобразования
     */
    public CallableArgument(ClassCastException e){
        this.callable = false;
        this.exception = e;
    }

    /**
     * Конструктор - передача аргумента на прямую без преобразования
     * @param value Аргумент
     */
    public CallableArgument(Object value){
        this(null,value,0);
    }

    /**
     * Передача аргумента с использованием преобразования
     * @param caster Преобразователь типа данных
     * @param sourceValue Исходный аргумент
     */
    public CallableArgument(Caster caster,Object sourceValue){
        this(caster,sourceValue,0);
    }

    /**
     * Передача аргумента с использованием преобразования
     * @param caster Преобразователь типа данных
     * @param sourceValue Исходный аргумент
     * @param addWeight Дополнительный вес
     */
    public CallableArgument(Caster caster,Object sourceValue,int addWeight){
        this.callable = true;
        this.value = sourceValue;
        this.caster = caster;
        this.addWeight = addWeight;
    }
    
    /**
     * Добавочный вес
     */
    protected int addWeight = 0;

    /**
     * Флаг - вызов возможен
     */
    private boolean callable = false;
    
    /**
     * Ошибка в процессе преобразования типов,
     * возможно не найден подходящий преобразователь типов.
     */
    private Throwable exception = null;
    
    /**
     * Исходное значение (арщумент)
     */
    private Object value = null;
    
    /**
     * Преобразователь или null
     */
    private Caster caster = null;
    
    /**
     * Преобразователь или null
     * @return  Преобразователь или null
     */
    public final Caster getCaster(){
        return caster;
    }

    /**
     * Исходное значение будет преобразовано
     * @return true - будет
     */
    public boolean isCasted() {
        return caster!=null;
    }

    /**
     * Возможен вызов функции/передача этого аргумента (возможно преобразавание или передача на прямую)
     * @return true - возможно
     */
    public boolean isCallable() {
        return callable;
    }

    /**
     * Преобразованное значение, если задан преобразователь или исходное.
     * @return Значение
     */
    public Object getValue() {
        if( caster!=null )
            return caster.cast(value);
        return value;
    }

    /**
     * Ошибка преобзования типов
     * @return Ошибка или null
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Дополнительный вес
     * @return дополнительный вес
     */
    public int getAddWeight() {
        return addWeight;
    }
    
    /**
     * Вес/кол-во преобразований типа.
     * @return 0 - напрямую, 1..более с использованием преобразователей
     */
    public int getCastWeight(){
        int res = 0;
        if( caster!=null && caster instanceof EdgeWeight ){
            return ((EdgeWeight)caster).getEdgeWeight();
        }
        return res;
    }

    /**
     * Возвращает сумарный вес
     * @return сумарный вес
     */
    public int getSummaryWeight(){
        int res = 0;
        res += getCastWeight();
        res += getAddWeight();
        return res;
    }
    
    @Override
    public String toString() {
        return "CallableArgument{" + 
                "castWeight="+getCastWeight()+
                " addWeight="+getAddWeight()+
                " summaryWeight="+getSummaryWeight()+
                " caster="+(caster==null ? "null" : caster.toString())+
                " callable=" + callable + 
                " exception=" + exception + 
                " value=" + value + 
                '}';
    }
}
