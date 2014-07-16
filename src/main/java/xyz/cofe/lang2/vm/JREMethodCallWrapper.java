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
package xyz.cofe.lang2.vm;

import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.err.RuntimeError;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.jre.CallMethodVariant;
import xyz.cofe.lang2.vm.jre.CallVariant;
import xyz.cofe.text.Text;

/**
 * Вызов метода объекта - Java Runtime Enviroment.<br/>
 * Возможно указать несколько методов, 
 * будет вызвать ближайщий ковариантный метод согласно переданным аргументам.
 * @author gocha
 */
public class JREMethodCallWrapper implements Callable
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    private Object owner = null;
    private Method[] methods = null;

    /**
     * Конструктор
     */
    public JREMethodCallWrapper(){
    }

    /**
     * Конструктор
     * @param owner Объект чей метод вызывается
     * @param methods Метод который необходимо вызвать
     */
    public JREMethodCallWrapper(Object owner,Method method){
        this.owner = owner;
        this.methods = new Method[]{method};
    }

    /**
     * Конструктор
     * @param owner Объект чей метод вызывается
     * @param methods Метод который необходимо вызвать, возможно несколько
     */
    public JREMethodCallWrapper(Object owner,Method[] methods){
        this.owner = owner;
        this.methods = methods;
    }

    /**
     * Методы которые необходимо вызвать
     * @return Методы
     */
    public Method[] getMethods()
    {
        return methods;
    }

    /**
     * Методы которые необходимо вызвать
     * @param methods Методы
     */
    public void setMethods(Method[] methods)
    {
        this.methods = methods;
    }

    /**
     * Объект чей методы вызывается
     * @return Объект
     */
    public Object getOwner()
    {
        return owner;
    }

    /**
     * Объект чей методы вызывается
     * @param owner Объект
     */
    public void setOwner(Object owner)
    {
        this.owner = owner;
    }

    /*
     * (non-Javadoc) @see Callable
     */
    @Override
    public Object call(Object... arguments)
    {
        if( methods==null ){
            Logger.getLogger(JREMethodCallWrapper.class.getName()).severe("methods not set");
            throw new CompileException(this, "methods==null");
        }
        if( arguments==null || arguments.length==0 )return callWithoutArguments();
        return callWithArguments(arguments);
    }
    
    // TODO: Добавить в конфигурацию
//    private static final Level logLevel = Level.FINE;
    
    private Object callWithArguments(Object... arguments){
        // DEBUG
        String argsTypes = "";
        int idx = -1;
        for( Object arg: arguments ){
            idx++;
            if( idx>0 )argsTypes += ", ";
            if( arg==null ){
                argsTypes += "null"; 
            }else{
                argsTypes += arg.getClass().getName();
            }
        }

//        Logger log = Logger.getLogger(JREMethodCallWrapper.class.getName());
        logFine("callWithArguments( {0} )",argsTypes);
        // DEBUG
        
        List<CallVariant> callVariants = CallMethodVariant.makeCallableVariants(getOwner(), methods, arguments);
        
        if( callVariants.size() > 0 ){
            logFine("call variants:");
            int i = 0;
            int tot = callVariants.size();
            for( CallVariant cv : callVariants ){
                i++;
                logFine("variant({0}/{1}):{2}",new Object[]{i,tot,cv.toString()});
            }

            CallVariant cv = callVariants.get(0);
            logFine("calling: {0}", cv.toString());
            Object res = cv.invoke();
            logFine("calling result: {0}", res);
            return res;
        }
        
        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(
                Level.FINE,
                "Нет подходящего метода для указанных аргументов: ({0})",
                argsTypes
                );

        throw new RuntimeError(this, 
                Text.template(
                "Нет подходящего метода для указанных аргументов: ({0})", argsTypes));
    }
    
    private Object callWithoutArguments(){
        StringBuilder logMethods = new StringBuilder();
        logMethods.append("methods("+methods.length+"):\n");
        int idx = -1;
        for( Method m : methods ){
            idx++;
            logMethods.append(idx+1);
            logMethods.append(":");
            logMethods.append(m.toString());
            logMethods.append("\n");
        }
        logFine("callWithoutArguments() methods={0}", logMethods.toString());
        
        StringBuilder log = new StringBuilder();
        for( Method m : methods ){
            Class[] paramTypes = m.getParameterTypes();
            int modif = m.getModifiers();
            if( paramTypes.length==0 ){
                if( owner==null && ((modif & Modifier.STATIC) == Modifier.STATIC) ){
                    try {
                        logFine("callWithoutArguments() method={0}", m.toString());
                        Object r = m.invoke(null, new Object[]{});
                        logFine("callWithoutArguments() ok method={0} result={1}", m.toString(), r);
                        return r;
                    }
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                    catch (IllegalArgumentException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                    catch (InvocationTargetException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                }
                if( owner!=null && !((modif & Modifier.STATIC) == Modifier.STATIC) ){
                    try {
                        logFine("callWithoutArguments() method={0} owner={1}", m.toString(), owner);
                        Object r = m.invoke(owner, new Object[]{});
                        logFine("callWithoutArguments() ok method={0} owner={1} result={2}", m.toString(), owner, r);
                        return r;
                    }
                    catch (IllegalAccessException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                    catch (IllegalArgumentException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                    catch (InvocationTargetException ex) {
                        Logger.getLogger(JREMethodCallWrapper.class.getName()).log(Level.SEVERE, null, ex);
                        
                        log.append("LOG:");
                        log.append("JREMethodCallWrapper.callWithoutArguments() - InvocationTargetException");
                        if( ex.getMessage()!=null )
                            log.append(", message: "+ex.getMessage());
                        log.append("\n");
                    }
                }
            }
        }
        Logger.getLogger(JREMethodCallWrapper.class.getName()).severe("method not found for this arguments");
        
        throw new RuntimeError(this, 
                Text.template( "Не найден метод без аргументов\n{0}",
                log.toString() ));
    }
}