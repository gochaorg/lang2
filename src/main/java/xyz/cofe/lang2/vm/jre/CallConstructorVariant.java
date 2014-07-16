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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.err.RuntimeError;
import xyz.cofe.text.Text;

/**
 * Вариант вызова конструктора
 * @author gocha
 */
public class CallConstructorVariant extends CallVariant
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Constructor constructor = null;
    
    public CallConstructorVariant(Constructor constructor,CallableArguments args,Object[] sourceArgs){
        super(args, sourceArgs);
        if( constructor==null ){
            throw new IllegalArgumentException("constructor==null");
        }
        this.constructor = constructor;
    }

    public Constructor getConstructor() {
        return constructor;
    }
    
    private String constrSpec(){
        String constrName = constructor==null ? "?" : constructor.getDeclaringClass().getName();
        
        String methodSpec =
                "new "+constrName+"";

        if( constructor!=null ){
            methodSpec += "(";
            Class[] paramTypes = constructor.getParameterTypes();
            int idx = -1;
            for( Class c : paramTypes ){
                idx++;
                if( idx>0 )methodSpec += ", ";
                methodSpec += c.getName();
            }
            methodSpec += ")";
        }
        return methodSpec;
    }

    @Override
    public String toString() {
        return "CallVariant{" + "constructor=" + constrSpec() + ", args=" + args + '}';
    }
    
    @Override
    public Object invoke() {
        try {
            if( constructor==null )throw new IllegalStateException("constructor==null");
            if( args==null )throw new IllegalStateException("args==null");
            Object result = args.create(constructor);
            return result;
        } catch (InstantiationException ex) {
            Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(CallConstructorVariant.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static List<CallVariant> makeCallableVariants(Class clazz, Object[] arguments){
        if( clazz==null )throw new IllegalArgumentException("clazz==null");
        if( arguments==null )throw new IllegalArgumentException("arguments==null");
        List<CallVariant> callVariants = new ArrayList<CallVariant>();
        for( Constructor ctr : clazz.getConstructors() ){
            if( ctr==null )continue;
            Class[] paramTypes = ctr.getParameterTypes();
            CallableArguments cargs = CallableArguments.makeArguments(paramTypes, arguments);
            CallConstructorVariant cVar = new CallConstructorVariant(ctr, cargs, arguments);
            callVariants.add(cVar);
        }
        callVariants = filterAndSort( callVariants );
        return callVariants;
    }
    
    /**
     * Создание объекта с указанными аргументами
     * @param clazz Класс
     * @param args Аргументы для конструктора
     * @return Объект
     * @throws RuntimeError Если нет подходящего конструктора
     */
    public static Object call(Class clazz,Object ... args)
            throws RuntimeError
    {
        if( clazz==null )throw new IllegalArgumentException("clazz==null");
        if( args==null )throw new IllegalArgumentException("args==null");
        List<CallVariant> variants = makeCallableVariants(clazz, args);
        
        // DEBUG
        String argsTypes = "";
        int idx = -1;
        for( Object arg: args ){
            idx++;
            if( idx>0 )argsTypes += ", ";
            if( arg==null )argsTypes += "null"; else argsTypes += arg.getClass().getName();
        }
        
        if( variants.size()>0 ){
            CallVariant cv = variants.get(0);
            return cv.invoke();
        }
        
        Logger.getLogger(CallConstructorVariant.class.getName()).log(
                Level.FINE,
                "Нет подходящего метода для указанных аргументов: ({0})",
                argsTypes
                );

        throw new RuntimeError(CallConstructorVariant.class, 
                Text.template(
                "Нет подходящего метода для указанных аргументов: ({0})", argsTypes));
    }
    
    /**
     * Создание объекта используя конструктор без аргументов
     * @param clazz Java Класс
     * @return Объект
     */
    public static Object callWithoutArguments(Class clazz) throws 
            NoSuchMethodException, 
            InstantiationException, 
            IllegalArgumentException, 
            IllegalAccessException, 
            InvocationTargetException
    {
        if( clazz==null )throw new IllegalArgumentException("clazz==null");
        Constructor ctr = clazz.getConstructor();
        return ctr.newInstance();
    }
}
