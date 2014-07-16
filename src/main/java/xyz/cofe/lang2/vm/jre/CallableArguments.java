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

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.graph.Path;
import xyz.cofe.text.IndentStackWriter;

/**
 * Аргументы для вызова функции/метода/... с использованием преобразователей
 * @author gocha
 */
public class CallableArguments
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CallableArguments.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    @Override
    public String toString() {
        StringWriter buffer = new StringWriter();
        IndentStackWriter writer = new IndentStackWriter(buffer);
        
        writer.println("CallableArguments { ");
        writer.incLevel();

        writer.print("callable=");
        writer.println(isCallable());
        
        writer.print("castWeight=");
        writer.println(getCastWeight());
        
        //.......
        writer.println("args=");
        writer.incLevel();
//        int i = -1;
        for( CallableArgument a : getCallableArguments() ){
//            i++;
//            if( i>0 )writer.println(", ");
            writer.println(a.toString());
        }
        
        //.......
        writer.decLevel();
        
        writer.decLevel();
        writer.println("}");
        writer.flush();
        return buffer.toString();
    }
    
    private CallableArgument[] args = new CallableArgument[]{};

    /**
     * Конструктор
     */
    public CallableArguments(){
        args = new CallableArgument[]{};
    }
    
    /**
     * Возвращает сумарный вес всех преобразований для указанных аргументов
     * @return Сумарный вес аргументов
     */
    public int getCastWeight(){
        int co = 0;
        for( CallableArgument a : getCallableArguments() ){
            co += a.getCastWeight();
        }
        return co;
    }

    /**
     * Возвращает сумарный вес всех преобразований для указанных аргументов
     * @return Сумарный вес аргументов
     */
    public int getSummaryWeight(){
        int co = 0;
        for( CallableArgument a : getCallableArguments() ){
            co += a.getSummaryWeight();
        }
        return co;
    }

    /**
     * Указывает что вызов с указанными аргументами (на прямую или даже с 
     * преобразователями) возможен - не должно произойти ClassCastException.
     * @return true - возможен вызов.
     */
    public boolean isCallable(){
        if( args==null )return true;
        for( CallableArgument ca : args ){
            if( !ca.isCallable() )return false;
        }
        return true;
    }

    /**
     * Возвращает массив (возможно преобразованых) значений для вызова
     * @return значения для вызова
     */
    public Object[] getArguments(){
        CallableArgument[] cargs = getCallableArguments();
        Object[] _args = new Object[cargs.length];
        for( int i=0; i<cargs.length; i++ )_args[i] = cargs[i].getValue();
        return _args;
    }

    /**
     * Вызывает метод объекта
     * @param owner Объект чей метод вызывается
     * @param method Метод
     * @return Результат вызова
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public Object invoke(Object owner, Method method) 
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Object[] arguments = getArguments();
        Object r = method.invoke(owner, arguments);
        return r;
    }
    
    /**
     * Создает объект
     * @param constructor Конструктор объекта
     * @return Объект
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    public Object create(Constructor constructor) 
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        Object[] arguments = getArguments();
        Object r = constructor.newInstance(arguments);
        return r;
    }

    /**
     * Добавляет аргумент к списку аргументов
     * @param arg Аргумент
     */
    public void add(CallableArgument arg){
        if( args == null ){
            args = new CallableArgument[]{ arg };
        }else{
            args = Arrays.<CallableArgument>copyOf(args, args.length+1);
            args[args.length-1] = arg;
        }
    }

    /**
     * Возвращает массив аргументов
     * @return Аргументы
     */
    public CallableArgument[] getCallableArguments(){ 
        if( args==null ){
            return new CallableArgument[]{};
        }
        return args; 
    }
    
    /**
     * Граф преобразования
     */
    private static CastGraph castGraph = PrimitiveCastGraph.createCastGraph();

    /**
     * Подыскивает минимальный путь преобразования типа в графе типов.
     * Если таковой путь есть, то он записывается в граф.
     * Если такой путь не найден, то генерирует исключение ClassCastException
     * @param targetType Тип к которому необходимо преобразовать.
     * @param sourceType Тип от которого происходит преобразование.
     * @return Преобразователь типов.
     */
    private static Caster cast( Class targetType, Class sourceType ){
        Caster caster = castGraph.get(sourceType, targetType);
        if( caster!=null ){
            return caster;
        }else{
            Path<Class,Caster> p = castGraph.findPath(sourceType, targetType);
            if( p!=null ){
                caster = new SequenceCaster(p);
                castGraph.set(sourceType, targetType, caster);
                return caster;
            }
        }
        
        throw new ClassCastException("Cast from "+sourceType.getName()+" to "+targetType.getName() );
    }
    
    /**
     * Создает список аргументов для вызова метода/функции/...
     * @param argumentsType Тип аргументов вызваемого метода/функции
     * @param transferValues Сами передоваемые значения
     * @return Аргументы вызова
     */
    public static CallableArguments makeArguments(Class[] argumentsType, Object[] transferValues){
        CallableArguments res = new CallableArguments();
        
        // DEBUG
        StringBuilder sb = new StringBuilder();
        int i=-1;
        for(Class argType:argumentsType){
            i++;
            if( i>0 )sb.append(", ");
            sb.append(argType.toString());
        }
        String argTypesStr = sb.toString();

        sb.setLength(0);
        i = -1;
        for(Object transfVal:transferValues){
            i++;
            if( i>0 )sb.append(", ");
            sb.append(transfVal==null ? "null" : transfVal.toString());
        }
        String transferValuesStr = sb.toString();
        logFine("makeArguments( {0}, {1} )", argTypesStr,transferValuesStr);
        // DEBUG

        for( int iArgType = 0; iArgType<argumentsType.length; iArgType++ ){
            Class argType = argumentsType[iArgType];

            Object transferValue = iArgType >= transferValues.length ? null : transferValues[iArgType];
            Class transferType = transferValue==null ? null : transferValue.getClass();
            
            logFiner("index={0} argType={1} transferType={2} transferValue={3}", iArgType, argType, transferType, transferValue);

            // Проверяем:
            // 1) что тип аргумента совпадает с типом значения или значение является подтипом аргумента
            // 2) значение не является null
            if( transferValue!=null &&
                ( transferType.equals(argType) ||
                  argType.isAssignableFrom(transferType)
                )
            ){
                int addWeight = 0;
                if( !argType.equals(transferType) ){
                    logFiner("{0} != {1}", argType, transferType);
                    int classHiarhDiff = 0;
                    int classHiarhDiffMax = 100;
                    boolean findMatches = false;
                    
                    if( !argType.isInterface() && !transferType.isInterface() ){
                        Class c = transferType;
                        while( true ){
                            if( c.equals(argType) ){
                                findMatches = true;
                                break;
                            }
                            Class sc = c.getSuperclass();
                            if( sc==null )break;
                            c = sc;
                            classHiarhDiff++;
                            if( classHiarhDiff>classHiarhDiffMax )break;
                        }
                        if( findMatches ){
                            logFiner("classHiarhDiff={0}", classHiarhDiff);
                            addWeight += classHiarhDiff;
                        }else{
                            logFiner("classHiarhDiff not found");
                            addWeight += 1;
                        }
                    }else if( argType.isInterface() && !transferType.isInterface() ){
                        addWeight += 1;
                    }else if( !argType.isInterface() && transferType.isInterface() ){
                        addWeight += 1;
                    }else if( argType.isInterface() && transferType.isInterface() ){
                        addWeight += 1;
                    }else{
                        addWeight += 1;
                    }
                }
                
                CallableArgument ca = new CallableArgument(null,(Object)transferValue,addWeight);
                res.add(ca);
                logFiner("v1 add as {0}", ca);
                continue;
            }

            // Проверям что тип аргумента функции является примитивом
            TargetPrimitive argPrimitive = Primitive.isPrimitive(argType);
            if( argPrimitive!=null ){
                // Передоваемое значение является null ?
                if( transferValue==null ){
                    if( argPrimitive.isAllowNull() ){
                        // Тип аргумента допускает передачу null значения
                        CallableArgument ca = new CallableArgument((Object)null);
                        res.add(ca);
                        logFiner("v2 add as {0}", ca);
                    }else{
                        // Тип аргумента НЕ допускает передачу null значения
                        // Используется значение по умолчанию для данного типа аргумента
                        CallableArgument ca = new CallableArgument((Object)argPrimitive.getDefNotNullValue());
                        res.add(ca);
                        logFiner("v3 add as {0}", ca);
                    }
                }else{
                    // Передаваемое значение тоже является примитовом ?
                    TargetPrimitive transferPrimitive = Primitive.isPrimitive(transferType);
                    if( transferPrimitive != null ){
                        // тогда пытамся его преобразовать значение (примитив) в тип аргумента (примитив)
                        try {
                            Caster tv = cast(argType, transferType);
                            // Преобразование успешно
                            CallableArgument ca = new CallableArgument(tv, transferValue);
                            res.add(ca);
                            logFiner("v4 add as {0}", ca);
                        }
                        catch (ClassCastException e) {
                            // Преобразование не возможно
                            CallableArgument ca = new CallableArgument(e);
                            res.add(ca);
                            logFiner("v5 add as {0}", ca);
                        }
                    }else{
                        // Передаваемой значение не является примитивом
                        // Необходимо преобразование передаваемого значения (не примитива)
                        // к типу аргумента (примитиву)
                        try {
                            Caster tv = cast(argType, transferType);
                            // Преобразование успешно
                            CallableArgument ca = new CallableArgument(tv, transferValue);
                            res.add(ca);
                            logFiner("v6 add as {0}", ca);
                        }
                        catch (ClassCastException e) {
                            // Преобразование не возможно
                            CallableArgument ca = new CallableArgument(e);
                            res.add(ca);
                            logFiner("v7 add as {0}", ca);
                        }
                    }
                }
            }else{
                if( transferValue!=null ){
                    // Тип аргумента функции НЕ является примитивом
                    if( argType.isAssignableFrom(transferType) ){
                        // Передаваемое значение, является подтипом аргумента
                        CallableArgument ca = new CallableArgument((Object)transferValue);
                        res.add(ca);
                        logFiner("v8 add as {0}", ca);
                    }else{
                        // Необходимо преобразование передаваемого значения
                        // к типу аргумента (Не примитиву)
                        try {
                            Caster tv = cast(argType, transferType);
                            // Преобразование успешно
                            CallableArgument ca = new CallableArgument(tv, transferValue);
                            res.add(ca);
                            logFiner("v9 add as {0}", ca);
                        }
                        catch (ClassCastException e) {
                            // Преобразование не возможно
                            CallableArgument ca = new CallableArgument(e);
                            res.add(ca);
                            logFiner("v10 add as {0}", ca);
                        }
                    }
                }else{
                    // Передаваемое значение (null) в аргумент не примитивного типа
                    CallableArgument ca = new CallableArgument((Object)transferValue);
                    res.add(ca);
                    logFiner("v11 add as {0}", ca);
                }
            }
        }

        return res;
    }
}