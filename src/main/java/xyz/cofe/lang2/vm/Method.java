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

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.Factory;
import xyz.cofe.lang2.vm.op.Function;
//import lang2.vm.op.VMObject;

/**
 * Метод объекта VM
 * @author gocha
 */
public class Method extends VrFunction
{
    /**
     * Конструктор
     */
    public Method(){
    }
    
    /**
     * Конструктор копирования
     * @param src Исходный объект
     */
    public Method(Method src,boolean deep){
        super(src,deep);
        if (src== null) {            
            throw new IllegalArgumentException("src==null");
        }
        owner = src.owner;
    }

    /**
     * @param owner Объект - владелец метода
     * @param function Функция метода
     */
    public Method(Map owner,Function function){
        super(function, false);
        this.owner = owner;
    }

    private Map owner = null;

    /**
     * Указывает объект - владелец метода
     * @return Объект - владелец метода
     */
    public Map getOwner()
    {
        return owner;
    }

    /**
     * Указывает объект - владелец метода
     * @param object Объект - владелец метода
     */
    public void setOwner(Map object)
    {
        this.owner = object;
    }

    private static final String thisVar = "this";

    /* (non-Javadoc) @see Function */
    @Override
    protected void restoreVariables(Map scope)
    {
        restoreVariables(scope,thisVar);
        super.restoreVariables(scope);
    }

    /* (non-Javadoc) @see Function */
    @Override
    protected void storeVariables(Map scope)
    {
        super.storeVariables(scope);
        storeVariables(scope, thisVar);
    }

    /* (non-Javadoc) @see Function */
    @Override
    protected void createSpecialVars(Object... arguments)
    {
        super.createSpecialVars(arguments);
        Map<String,Object> mem = getMemory();
        if( mem!=null ){
            mem.put(thisVar, getOwner());
        }else{
            // TODO Fire compiletime (vm) exception !
            Logger.getLogger(Method.class.getName()).severe("memory not set!");
        }
    }

    /* (non-Javadoc) @see Function */
    @Override
    public String[] getNewVaraibleNames() {
        String[] names = super.getNewVaraibleNames();
        names = Arrays.copyOf(names, names.length+1);
        names[names.length-1] = thisVar;
        return super.getNewVaraibleNames();
    }

    @Override
    public Value deepClone() {
        return new Method(this, true);
    }
    
    /**
     * Возвращает метод указанного объекта.
     * Если по указаному имени зарегистрирована функция, то заменит ее на одноименный метод
     * @param object Объект
     * @param name Имя метода
     * @return если нет указаного метода, или это не метод/функция, то вернет null
     */
    public static Method getMethod(Map object,String name,Factory factory){
        if( object==null )return null;
        if( name==null )return null;
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        
        if( !object.containsKey(name) )return null;
        Object val = object.get(name);
        
        if( val instanceof xyz.cofe.lang2.vm.Method ){
            return (Method)val;
        }else if( 
            (val instanceof Function) && 
            !(val instanceof Method) 
        ){
            Method m = factory.createMethod(object, (Function)val);
            object.put(name, m);
            return m;
        }
        
        return null;
    }
}
