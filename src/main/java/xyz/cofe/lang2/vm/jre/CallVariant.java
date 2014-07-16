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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Варинат вызова метода/конструктора
 * @author gocha
 */
public abstract class CallVariant
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallVariant.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallVariant.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallVariant.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallVariant.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallVariant.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallVariant.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Возвращает сумарный вес всех преобразований для указанных аргументов
     * @return Сумарный вес аргументов
     */
    public int getCastWeight(){
        if( args==null )return Integer.MAX_VALUE;
        return args.getCastWeight();
    }
    
    /**
     * Отфильтровывает варинты которые нельзя вызвать, и сортирует - первые идут предпочтительные
     * @param variants Варианты вызова
     * @return Отфильтрованные вызовы
     */
    protected static <T extends CallVariant> List<CallVariant> filterAndSort(List<T> variants){
        ArrayList<CallVariant> res = new ArrayList<CallVariant>();
        if( variants==null )return res;
        for( CallVariant cv : variants ){
            if( cv.getArgs().isCallable() ){
                res.add( cv );
            }
        }
        if( res.size() > 1 ){
            Collections.sort(res, CallMethodVariant.comparator);
        }
        return res;
    }
    
    /**
     * Сравнивает веса
     * @see lang2.vm.jre.CallVariant.Comparator
     */
    public static final CallVariantComparator comparator = new CallVariantComparator();
    
    /**
     * Передоваемые аргументы в метод
     */
    protected CallableArguments args = null;
    
    /**
     * Исходные аргументы
     */
    protected Object[] sourceArgs = null;

    /**
     * Конструктор
     * @param args Аргументы с преобразованием
     * @param sourceAgrs Исходные аргументы
     */
    public CallVariant(CallableArguments args, Object[] sourceAgrs) {
        if( sourceAgrs==null )throw new IllegalArgumentException("sourceAgrs==null");
        if( args==null )throw new IllegalArgumentException("args==null");
        this.sourceArgs = sourceAgrs;
        this.args = args;
    }
    
    /**
     * Возвращает исходные аргументы
     * @return исходные аргументы
     */
    public Object[] getSourceArgs(){
        return sourceArgs;
    }
    
    /**
     * Передоваемые аргументы в метод
     * @return аргументы
     */
    public CallableArguments getArgs() {
        if( args==null ){
            args = new CallableArguments();
        }
        return args;
    }

    /**
     * Вызов метода объекта
     * @param owner Объект, чей метод вызывается
     * @return Результат вызова
     */
    public abstract Object invoke();
    
}
