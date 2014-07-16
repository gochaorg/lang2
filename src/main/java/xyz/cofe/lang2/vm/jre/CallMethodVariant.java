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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Вариант вызова метода
 * @author gocha
 */
public class CallMethodVariant extends CallVariant {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallMethodVariant.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Конструктор варианта
     * @param owner Владелец метода
     * @param method Метод
     * @param args Передоваемые аргуметы
     */
    public CallMethodVariant(Object owner,Method method,CallableArguments args,Object[] sourceArgs){
        super(args, sourceArgs);
        if (method== null) {                
            throw new IllegalArgumentException("method==null");
        }
        if (args== null) {                
            throw new IllegalArgumentException("args==null");
        }
        this.method = method;
        this.owner = owner;
    }
    
    /**
     * Владелец метода
     */
    protected Object owner = null;

    /**
     * Вызываемый метод
     */
    protected Method method = null;
    
    /**
     * Возвращае владелеца метода
     * @return Владелец метода
     */
    public Object getOwner(){ return owner; }

    /**
     * Вызываемый метод
     * @return метод
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Вызов метода объекта
     * @param owner Объект, чей метод вызывается
     * @return Результат вызова
     */
    @Override
    public Object invoke(){
        Object result = null;
        try {
            result = args.invoke(owner, method);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CallMethodVariant.class.getName()).log(
                    Level.SEVERE, "exception: {0}, call variant: {1}", 
                    new Object[]{ex, toString()});
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CallMethodVariant.class.getName()).log(
                    Level.SEVERE, "exception: {0}, call variant: {1}", 
                    new Object[]{ex, toString()});
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CallMethodVariant.class.getName()).log(
                    Level.SEVERE, "exception: {0}, call variant: {1}", 
                    new Object[]{ex, toString()});
        }
        return result;
    }

    private String methodSpec(){
        String methodSpec =
                method.getDeclaringClass().getName()+"#"+method.getName()+"(";

        Class[] paramTypes = method.getParameterTypes();
        int idx = -1;
        for( Class c : paramTypes ){
            idx++;
            if( idx>0 )methodSpec += ", ";
            methodSpec += c.getName();
        }
        methodSpec += ")";
        return methodSpec;
    }

    @Override
    public String toString() {
        return "CallVariant{" + "method=" + methodSpec() + ", args=" + args + '}';
    }

    /**
     * Создает перечень вариантов вызова
     * @param owner Объект чей метод вызывается
     * @param methods Методы которые рассматриваются в качестве варианта
     * @param arguments Аргументы которые будут переданы
     * @return Перечень вариантов вызова, предпочтительный вариант будет в начале
     */
    public static List<CallVariant> makeCallableVariants(Object owner,Method[] methods, Object[] arguments){
        if( methods==null ){
            throw new IllegalArgumentException("methods==null");
        }
        if( arguments==null ){
            throw new IllegalArgumentException("arguments==null");
        }
        
        List<CallVariant> callVariants = new ArrayList<CallVariant>();

        for( Method m : methods ){
            if( m==null )continue;
            Class[] paramTypes = m.getParameterTypes();
            CallableArguments cargs = CallableArguments.makeArguments(paramTypes, arguments);
            CallMethodVariant cVar = new CallMethodVariant(owner, m, cargs, arguments);
            callVariants.add(cVar);
        }
        callVariants = filterAndSort( callVariants );

        return callVariants;
    }
}
