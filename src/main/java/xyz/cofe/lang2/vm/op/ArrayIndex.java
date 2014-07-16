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
package xyz.cofe.lang2.vm.op;

import xyz.cofe.lang2.vm.err.CompileException;
import xyz.cofe.lang2.vm.err.CastError;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.Factory;
import xyz.cofe.lang2.vm.err.Error;
import xyz.cofe.lang2.vm.JREMethodCallWrapper;
import xyz.cofe.lang2.vm.LValue;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.err.RuntimeError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.collection.Pair;

/**
 * Выражение доступа к полям объекта/элементам массива. <br/>
 * Под массивом могут выступать:
 * <ul>
 * <li>Массивы JAVA</li>
 * <li>Списки java.util.List</li>
 * <li>Карты java.util.Map</li>
 * <li>Объекты Java Runtime Enviroment (JRE)</li>
 * <li>Объекты VM lang2.vm.VMObject</li>
 * </ul>
 * При привышении границ массива возврашается значение null.<br/>
 * Если доступ осуществляется к объекту JRE - то индекс в данном случае должен быть текстовый.
 * Индекс может указывать на поле (public) объекта либо на метод (public).
 * В случае с методом будет возвращает объект класса JREMethodCallWrapper
 * @author Камнев Георгий Павлович
 * @see JREMethodCallWrapper
 */
public class ArrayIndex extends AbstractTreeNode<Value> implements LValue
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected Value baseValue = null;
	protected Value indexValue = null;
    protected Factory factory = null;

    public ArrayIndex(Factory factory)
    {
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
    }
	
    /**
     * Конструктор копирования
     * @param factory Фабрика классов
     * @param src Образец для копирования
     */
	public ArrayIndex(Factory factory,ArrayIndex src){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
		baseValue = src.baseValue;
		indexValue = src.indexValue;
        extender = src.extender;
        if( baseValue!=null )baseValue.setParent(this);
        if( indexValue!=null )indexValue.setParent(this);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public ArrayIndex(Factory factory,ArrayIndex src,boolean deep){
		if (src== null) {			
			throw new IllegalArgumentException("src==null");
		}
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
		baseValue = src.baseValue;
		indexValue = src.indexValue;
        extender = src.extender;
        if( deep ){
            if( baseValue!=null )baseValue = baseValue.deepClone();
            if( indexValue!=null )indexValue = indexValue.deepClone();
        }
        if( baseValue!=null )baseValue.setParent(this);
        if( indexValue!=null )indexValue.setParent(this);
	}
    
	/**
	 * Конструктор доступа к элементу массива
	 * @param baseValue Массив
	 * @param indexValue Индекс
	 */
	public ArrayIndex(Factory factory,Value baseValue,Value indexValue){
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
		this.baseValue = baseValue;
		this.indexValue = indexValue;
        if( baseValue!=null )baseValue.setParent(this);
        if( indexValue!=null )indexValue.setParent(this);
	}
	
	/**
	 * Возвращает объект/массив к которому производиться доступ
	 * @return объект/массив
	 */
	public Value getBaseValue() {
		return baseValue;
	}

	/**
	 * Устанавливает объект/массив к которому производиться доступ
	 * @param baseValue объект/массив
	 */
	public void setBaseValue(Value baseValue) {
		this.baseValue = baseValue;
        if( baseValue!=null )baseValue.setParent(this);
	}

	/**
	 * Возвращает индекс к которому производиться доступ
	 * @return индекс
	 */
	public Value getIndexValue() {
		return indexValue;
	}

	/**
	 * Устанавливает индекс к которому производиться доступ
	 * @param indexValue индекс
	 */
	public void setIndexValue(Value indexValue) {
		this.indexValue = indexValue;
        if( indexValue!=null )indexValue.setParent(this);
	}

	// <editor-fold defaultstate="collapsed" desc="Запись">
    /*
     * (non-Javadoc)
     * @see lang2.vm.LValue#evaluate(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(Object value)
    {
        if (baseValue == null || indexValue == null)
            throw new Error(this, "baseValue or indexValue not set");

        if (baseValue != null && indexValue != null) {
            Object base = baseValue.evaluate();
            if (base == null) throw new NullRefError(this, "base is null");
            
//            if( base instanceof VMObject )return writeVMObject((VMObject)base, indexValue.evaluate(), value);
            if (base.getClass().isArray())return writeJREArray(base, value);
            if (base instanceof List<?>)return writeList(base, value);
            if (base instanceof Map)return writeMap(base, value);
            return writeJREObject(base, value);
        }
        return null;
    }

    /**
     * Записывает значение в массив JRE
     * @param array Массив JRE
     * @param value Значение
     * @return Записанное значение
     */
    protected Object writeJREArray(Object array, Object value){
        Object index = indexValue.evaluate();
        if (index == null) throw new NullRefError(this, "index is null");
        if (index instanceof Number) {
            int i = ((Number) index).intValue();
            int l = Array.getLength(array);
            return writeJREArray(array, l, i, value);
        }
        throw new Error(this,"index is not number");
    }

    /**
     * Записывает значение в массив JRE
     * @param array Массив JRE
     * @param arrayLength Размер массива
     * @param index Индекс, может быть указан за пределами граници массива.
     * Если есть превышение границ массива, то в данном случае поведие определяется виртуальной машиной.
     * @param value Значение (Может быть null)
     * @return Записанное значение
     */
    protected Object writeJREArray(Object array,int arrayLength,int index,Object value){
        if (index < 0 || index >= arrayLength) {
            throw new Error(this,"index out of range");
        } else {
            Array.set(array, index, value);
        }
        return value;
    }

    /**
     * Записывает значение в список (java.util.List)
     * @param base Список
     * @param value Значение (Может быть null)
     * @return Записанное значение
     */
    protected Object writeList(Object base, Object value){
        Object index = indexValue.evaluate();
        if (index == null) {
            throw new NullRefError(this, "index is null");
        }
        if (index instanceof Number) {
            int i = ((Number) index).intValue();
            return writeList((List)base, i, value);
        }
        throw new Error(this,"index is not number");
    }

    /**
     * Записывает значение в список (java.util.List)
     * @param list Список
     * @param index Индекс элемента (может быть вне границ списка)
     * @param value Значение (может быть null)
     * @return Записанное значение
     */
    protected Object writeList(List list, int index, Object value){
        int l = list.size();
        if( index < 0 ){
            throw new Error(this,"index out of range");
        }
        if ( index >= l) {
            if( index == l ){
                list.add(value);
//                Logger.getLogger(ArrayIndex.class.getName()).fine("index out of range - list expand");
                return value;
            }

            int addCount = index-l;
            for( int j=0; j<addCount; j++ )list.add( null );
            list.add( value );            
//            Logger.getLogger(ArrayIndex.class.getName()).fine("index out of range - list expand");
            return value;
        } else {
            list.set(index, value);
        }
        return value;
    }

    /**
     * Записывает значение в карту (java.util.Map)
     * @param base Карта
     * @param value Значение (Может быть null)
     * @return Записанное значение
     */
    protected Object writeMap(Object base, Object value){
        Object index = indexValue.evaluate();
        return writeMap((Map)base, index, value);
    }

    /**
     * Записывает значение в карту (java.util.Map)
     * @param map Карта
     * @param key Ключ (Может быть null)
     * @param value Значение (Может быть null)
     * @return Записанное значение
     */
    protected Object writeMap(Map map, Object key, Object value){
        map.put(key, value);
        return value;
    }
	
	//TODO Doc it
	protected Object writeJREObjectField(Object base, Class baseClass, String index, Object value)
			throws NoSuchFieldException
	{
		try
		{
			Field field = baseClass.getField((String) index);
			field.set(base, value);
			return value;
		}
		catch (IllegalArgumentException e) {
			//TODO Log level config
			Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, e);
			throw new RuntimeError(
					"ArrayIndex.writeJREObjectField() - IllegalArgumentException"
					+ ((e.getMessage()!=null) ? ", message:"+e.getMessage() : "")
					,e);
		}
		catch (IllegalAccessException e) {
			Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, e);
			throw new RuntimeError(
					"ArrayIndex.writeJREObjectField() - IllegalAccessException"
					+ ((e.getMessage()!=null) ? ", message:"+e.getMessage() : "")
					,e);
		}
		catch (SecurityException e) {
			Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, e);
			throw new RuntimeError(
					"ArrayIndex.writeJREObjectField() - SecurityException"
					+ ((e.getMessage()!=null) ? ", message:"+e.getMessage() : "")
					,e);
		}
//		catch (NoSuchFieldException e) {
//			Logger.getLogger(ArrayIndex.class.getName()).log(Level.FINER, null, e);
//			throw new RuntimeException(
//					"ArrayIndex.writeJREObject() - NoSuchFieldException"
//					+ ((e.getMessage()!=null) ? ", message:"+e.getMessage() : "")
//					,e);
//		}
	}
	
	//TODO Doc it!
	protected Object[] writeJREObjectMethod(Object base,Class baseClass,String index,Object value){
		Method[] methods = baseClass.getMethods();
		ArrayList<Method> l = new ArrayList<Method>();
		for (Method m : methods) {
			if (!m.getName().equals((String) index))continue;
			Class[] mParams = m.getParameterTypes();
			if (mParams.length != 1)continue;
			l.add(m);
		}
		methods = l.toArray(new Method[]{});
		for (Method m : methods) {
			try {
				m.invoke(base, value);
				return new Object[]{value,true};
			}
			catch (IllegalAccessException ex) {
				Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ex);
				throw new RuntimeError(
						"ArrayIndex.writeJREObjectMethod() - IllegalAccessException"
						+ ((ex.getMessage()!=null) ? ", message:"+ex.getMessage() : "")
						,ex);
			}
			catch (IllegalArgumentException ex) {
				Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ex);
				throw new RuntimeError(
						"ArrayIndex.writeJREObjectMethod() - IllegalArgumentException"
						+ ((ex.getMessage()!=null) ? ", message:"+ex.getMessage() : "")
						,ex);
			}
			catch (InvocationTargetException ex) {
				Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ex);
				throw new RuntimeError(
						"ArrayIndex.writeJREObjectMethod() - InvocationTargetException"
						+ ((ex.getMessage()!=null) ? ", message:"+ex.getMessage() : "")
						,ex);
			}
		}
		return new Object[]{value,false};
	}

    /**
     * Записывает значение в объект Java Runtime Enviroment.<br/>
     * Снаячала отыскивается указанное ( getIndexValue ) поле и если оно существует, то записывается.<br/>
     * Если того поля нет, то отыскивается соот. метод (или несколько) и вызывается (первый успешный вызов).<br/>
     * Если методы не были найдены или успешно вызваны возвращается null, в лог ошибка
     * @param base Объект
     * @param value Значение
     * @return Записанное значение
     */
    protected Object writeJREObject(Object base, Object value){
        // Доступ к полю/методу объекта
        Object index = indexValue.evaluate();
        if (index instanceof String) {
            Class baseClass = base.getClass();
			try{
				return writeJREObjectField(base, baseClass, (String)index, value);
			}
			catch( NoSuchFieldException exField ){
				Object[] res = writeJREObjectMethod( base, baseClass, (String)index, value );
				if( res==null || res.length!=2 || !(res[1] instanceof Boolean) ){
					String mess = "Ошибка возвращаемого значения из ArrayIndex.writeJREObjectMethod";
					CompileException ce = new CompileException(this, mess);
					Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ce);
					throw ce;
				}
				
				boolean succ = ((Boolean)res[1]);
				Object result = res[0];
				if( succ ){
					return result;
				}
                
                res = writeJREObjectProperty(base, baseClass, (String)index, value);
				if( res==null || res.length!=2 || !(res[1] instanceof Boolean) ){
					String mess = "Ошибка возвращаемого значения из ArrayIndex.writeJREObjectMethod";
					CompileException ce = new CompileException(this, mess);
					Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, ce);
					throw ce;
				}
				
				succ = ((Boolean)res[1]);
				result = res[0];
				if( succ ){
					return result;
				}

				String mess = 
						"object " + base.getClass().getName() + " not contains field or method, or property, "
						+ index;
				
				Logger.getLogger(ArrayIndex.class.getName()).severe(mess);
				throw new RuntimeError(this,mess);
			}
        } else {
			String mess = "unsupported indexValue type " + (index == null ? "null" : index.getClass().getName());
            Logger.getLogger(ArrayIndex.class.getName()).severe(mess);
			throw new RuntimeError(this, mess);
        }
    }
    
    protected Object[] writeJREObjectProperty(Object base,Class baseClass,String index,Object value){
        try {
            BeanInfo bi = Introspector.getBeanInfo(baseClass);
            PropertyDescriptor[] _pd = bi.getPropertyDescriptors();
            for( PropertyDescriptor pd : _pd ){
                if( index.equals(pd.getName()) ){
                    Method m = pd.getWriteMethod();
                    Class[] params = m.getParameterTypes();
                    if( params.length==1 ){
                        try {
                            m.invoke(base, value);
                            return new Object[]{ value, true };
                        } catch (IllegalAccessException ex) {
                            RuntimeError re = new RuntimeError(this, "IllegalAccessException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        } catch (IllegalArgumentException ex) {
                            RuntimeError re = new RuntimeError(this, "IllegalArgumentException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        } catch (InvocationTargetException ex) {
                            RuntimeError re = new RuntimeError(this, "InvocationTargetException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        }
                    }
                }
            }
            return new Object[]{null,false};
        }
        catch (IntrospectionException ex) {
            RuntimeError re = new RuntimeError(this, "IntrospectionException", ex);
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        }
    }
    // </editor-fold>

    /**
     * Интерфейс расширения свойств/методов объекта
     */
    public interface Extender {
        /**
         * Расширение свойств/методов объекта
         * @param invoker Кто вызывает
         * @param base Объект
         * @param index Запрашваемое свойство / метод 
         * @return Пара Возвращаемое значение / true - успешно; false - использовать обычные обработчики
         */
        public Pair<Object,Boolean> extendArrayIndex(Value invoker, Object base,Object index);
    }
    
    /**
     *  Инт. расширения или null
     */
    protected Extender extender = null;

    /**
     * Указывает инт. расширения свойств объекта
     * @return инт. расширения или null
     */
    public Extender getExtender() {
        return extender;
    }

    /**
     * Указывает инт. расширения свойств объекта
     * @param extender  инт. расширения или null
     */
    public void setExtender(Extender extender) {
        this.extender = extender;
    }
    
    /**
     * Результат вычисления и интепретации индекса
     */
    protected static class EvalValue{
        /**
         * Вычисленное значение
         */
        public Object result;
        /**
         * Удачно
         */
        public boolean success;
        /**
         * Значение индекса
         */
        public Object index;
    }
    
    /**
     * Результат вычисления и интепретации индекса
     * @param res Вычисленное значение
     * @param succ Удачно
     * @param index Значение индекса
     * @return Результат вычисления
     */
    protected static EvalValue evalValue( Object res, boolean succ, Object index ){
        EvalValue ev = new EvalValue();
        ev.result = res;
        ev.success = succ;
        ev.index = index;
        return ev;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Чтение">
    /*
     * (non-Javadoc)
     * @see lang2.vm.Value#evaluate()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object evaluate(){
        if (baseValue==null){
            Logger.getLogger(ArrayIndex.class.getName()).severe("baseValue not set");
            throw new CompileException(this, "baseValue==null");
        }
        if (indexValue==null) {
            Logger.getLogger(ArrayIndex.class.getName()).severe("indexValue not set");
            throw new CompileException(this, "indexValue==null");
        }
        Object base = baseValue.evaluate();
        if (base == null) {
            Logger.getLogger(ArrayIndex.class.getName()).fine("base==null");
            throw new NullRefError(this, "base==null");
        }
        EvalValue r = null;
//        if (base instanceof VMObject ){
//            r = readVMObject(base);
//            if( r.success )return r.result;
//        }
        if (base.getClass().isArray()){
            if( r==null )
                r = readJREArray(base);
            else
                r = readJREArray(base, r.index);
            if( r.success )return r.result;
        }
        if (base instanceof List) {
            if( r==null )
                r = readList(base);
            else
                r = readList(base,r.index);
            if( r.success )return r.result;
        }
        if (base instanceof Map){
            if( r==null )
                r = readMap(base);
            else
                r = readMap((Map)base, r.index);
            if( r.success )return r.result;
        }
        if( r==null )
            return readJREObject(base);
        else
            return readJREObject(base, r.index);
    }

//    /**
//     * Читает поле/метод(ы) объекта VM
//     * @param base Объект VM
//     * @return Значение/Удачно
//     */
//    protected EvalValue readVMObject(Object base){
//        // Доступ к полю/методу объекта
//        Object index = indexValue.evaluate();
//        if( index==null ){
//            Logger.getLogger(ArrayIndex.class.getName()).fine("index==null");
//            throw new NullRefError(this, "index==null");
//        }
//        
//        if( extender!=null ){
//            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
//            if( ext.B() )return evalValue(ext.A(), true, index);
//        }
//        
//        return readVMObject((VMObject)base, index);
//    }

//    /**
//     * Читает поле/метод(ы) объекта VM
//     * @param vmObj Объект VM
//     * @param index Поле/Метод, может быть null
//     * @return Значение/Удачно
//     */
//    protected EvalValue readVMObject(VMObject vmObj, Object index){
//        if( index==null )return null;
//        
//        if( !vmObj.containsKey(index) )//return new BasicPair<Object, Boolean>(null,false);
//            return evalValue(null, false, index);
//
//        Object vmFieldValue = vmObj.get(index);
//        if( vmFieldValue==null )//return new BasicPair<Object, Boolean>(null,true);
//            return evalValue(null, true, index);
//
//        if( vmFieldValue instanceof lang2.vm.Method ){
////            return new BasicPair<Object, Boolean>(vmFieldValue,true);
//            return evalValue(vmFieldValue, true, index);
//        }else if( 
//                (vmFieldValue instanceof Function) && 
//                !(vmFieldValue instanceof lang2.vm.Method) ){
//            //TODO Учесть VrFunction
//            lang2.vm.Method m = new lang2.vm.Method(vmObj,(Function)vmFieldValue);
//            
//            vmObj.put(vmFieldValue, m);
////            return new BasicPair<Object,Boolean>(m,true);
//            return evalValue(m, true, index);
//        }
//
////        return new BasicPair<Object,Boolean>(vmFieldValue,true);
//        return evalValue(vmFieldValue, true, index);
//    }

    /**
     * Читает значение элемента массива JRE
     * @param base Массив JRE
     * @return Значение
     */
    protected EvalValue readJREArray(Object base){
        Object index = indexValue.evaluate();
        return readJREArray(base, index);
    }
    
    protected EvalValue readJREArray(Object base,Object index){
        if( index==null ){
            Logger.getLogger(ArrayIndex.class.getName()).fine("index==null");
            throw new NullRefError(this, "index==null");
        }
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
            if( ext.B() )return evalValue(ext.A(), true, index);
        }
        if (index instanceof Number) {
            int i = ((Number) index).intValue();
            return readJREArray(index, i, index);
        }
//        return new BasicPair<Object, Boolean>(null, false);
        return evalValue(null, false, index);
    }

    /**
     * Читает значение элемента массива JRE
     * @param array Массив JRE
     * @param index Индекс
     * @return Значение
     */
    protected EvalValue readJREArray(Object array,int index, Object srcIndex){
        int l = Array.getLength(array);
        if (index < 0 || index >= l) {
            String mess = "index ("+index+") out of range";
            Logger.getLogger(ArrayIndex.class.getName()).fine(mess);
            throw new RuntimeError(this, mess);
        }
        return evalValue(Array.get(array, index), true, srcIndex);
    }

    /**
     * Читает значение элемента списка
     * @param base Список (java.util.List)
     * @return Значение
     */
    protected EvalValue readList(Object base){
        Object index = indexValue.evaluate();
        return readList(base, index);
    }
    
    protected EvalValue readList(Object base,Object index){
        if( index==null ){
            Logger.getLogger(ArrayIndex.class.getName()).fine("index==null");
            throw new NullRefError(this, "index==null");
        }
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
            if( ext.B() )return evalValue(ext.A(), true, index);
        }
        if (index instanceof Number) {
            int i = ((Number) index).intValue();
            return readList((List)base, i, index);
        }
//        String mess = "index "+index.getClass().getName()+" not supported, need number";
//        Logger.getLogger(ArrayIndex.class.getName()).fine(mess);
//        throw new CastError(this, mess);
        
//        return new BasicPair<Object, Boolean>(null, false);
        
        return evalValue(null, false, index);
    }

    /**
     * Читает значение элемента списка
     * @param list Список (java.util.List)
     * @param index Индекс
     * @return Значение
     */
    protected EvalValue readList(List list,int index,Object srcIndex){
        int l = ((List) list).size();
        if (index < 0 || index >= l) {
            StringBuilder message = new StringBuilder();
            message.append("index out (");
            message.append(index);
            message.append(" between (index >= 0,");
            message.append(" index < ");
            message.append(l);
            message.append(") of range");
            Logger.getLogger(ArrayIndex.class.getName()).fine(message.toString());
            throw new RuntimeError(this, message.toString());
        }
        
//        return new BasicPair<Object, Boolean>( list.get(index), true );
        return evalValue(list.get(index), true, srcIndex);
    }

    /**
     * Читает значение элемента карты
     * @param base Карта (java.util.Map)
     * @return Значение
     */
    protected EvalValue readMap(Object base){
        Object index = indexValue.evaluate();		
        return readMap((Map)base, index);
    }

    /**
     * Читает значение элемента карты
     * @param map Карта (java.util.Map)
     * @param index Индекс
     * @return Значение
     */
    protected EvalValue readMap(Map map,Object index){
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, map, index);
            if( ext.B() )return evalValue(ext.A(), true, index);
        }
        
        // Если карта не содержит указаный ключ - то false
        if( !map.containsKey(index) ){
            return evalValue(null, false, index);
        }
        
        Object val = map.get(index);
        if( val==null ){
            return evalValue(null, true, index);
        }

        if( val instanceof xyz.cofe.lang2.vm.Method ){
            return evalValue(val, true, index);
        }else if( 
                (val instanceof Function) && 
                !(val instanceof xyz.cofe.lang2.vm.Method) ){
            
            xyz.cofe.lang2.vm.Method m = factory.createMethod(map, (Function)val);
            
            map.put(index, m);
            return evalValue(m, true, index);
        }
        
        return evalValue( val, true, index);
    }
    
    //TODO Doc it
    protected Object readJREObjectField(Object base,Class baseClass,String index)
            throws NoSuchFieldException
    {
        try {
            Field field = baseClass.getField(index);
            return field.get(base);
        }
        catch (IllegalArgumentException e) {
            RuntimeError re =
            new RuntimeError(this, 
                    "ArrayIndex.readJREObject() - IllegalArgumentException"
                    +(e.getMessage()==null ? "" : ", message:"+e.getMessage())
                    );
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        }
        catch (IllegalAccessException e) {
            RuntimeError re =
            new RuntimeError(this, 
                    "ArrayIndex.readJREObject() - IllegalAccessException"
                    +(e.getMessage()==null ? "" : ", message:"+e.getMessage())
                    );
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        }
        catch (SecurityException e) {
            RuntimeError re =
            new RuntimeError(this, 
                    "ArrayIndex.readJREObject() - SecurityException"
                    +(e.getMessage()==null ? "" : ", message:"+e.getMessage())
                    );
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        }
    }
    
    //TODO Doc it
    protected Object readJREObjectMethod(Object base,Class baseClass,String index){
        Method[] methods = baseClass.getMethods();
        ArrayList<Method> l = new ArrayList<Method>();
        for (Method m : methods) {
            if (m.getName().equals((String) index)) {
                l.add(m);
            }
        }
        methods = l.toArray(new Method[]{});
        if (methods.length > 0) {
            return new JREMethodCallWrapper(base, methods);
        }
        
        String mess = 
                "object " + base.getClass().getName() + 
                " not contains method or field " + index;
//        Logger.getLogger(ArrayIndex.class.getName()).severe(mess);
        throw new RuntimeError(this, mess);
    }
    
    protected Object readJREObjectProperty(Object base,Class baseClass,String index){
        try {
            BeanInfo bi = Introspector.getBeanInfo(baseClass);
            PropertyDescriptor[] _propDesc = bi.getPropertyDescriptors();
            for( PropertyDescriptor pd : _propDesc ){
                if( index.equals(pd.getName()) ){
                    Method mRead = pd.getReadMethod();
                    Class[] params = mRead.getParameterTypes();
                    if( params.length==0 ){
                        try {
                            Object res = mRead.invoke(base);
                            return res;
                        } catch (IllegalAccessException ex) {
                            RuntimeError re = new RuntimeError(base, "IllegalAccessException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        } catch (IllegalArgumentException ex) {
                            RuntimeError re = new RuntimeError(base, "IllegalArgumentException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        } catch (InvocationTargetException ex) {
                            RuntimeError re = new RuntimeError(base, "InvocationTargetException", ex);
                            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
                            throw re;
                        }
                    }
                }
            }
            
            RuntimeError re = new RuntimeError(this, "object not contains field or method, or property "+index );
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        } catch (IntrospectionException ex) {
            RuntimeError re = new RuntimeError(this, "IntrospectionException", ex);
            Logger.getLogger(ArrayIndex.class.getName()).log(Level.SEVERE, null, re);
            throw re;
        }
    }
    
    public Object readJREObject(Object base){
        // Доступ к полю/методу объекта
        Object index = indexValue.evaluate();
        return readJREObject(base,index);
    }

    /**
     * Читает поле/метод(ы) объекта jre.<br/>
     * <ol>
     * <li>Если есть соот. поле, то возвращает его значение</li>
     * <li>Если есть соот. метод(ы) то возвращает объект обвертку для вызова метода(ов)</li>
     * <li>Если ничего не найдено возвращает null</li>
     * </ol>
     * @param base Объект JRE
     * @return Значение
     */
    protected Object readJREObject(Object base,Object index){
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
            if( ext.B() )return ext.A();
        }
             
        if( index==null ){
            Logger.getLogger(ArrayIndex.class.getName()).fine("index==null");
            throw new NullRefError(this, "index==null");
        }
        
        if (index instanceof String) {
            Class baseClass = base.getClass();
            try {
                return readJREObjectField(base,baseClass,(String)index);
            } catch (NoSuchFieldException ex) {
                try{
                    return readJREObjectMethod( base, baseClass, (String)index );
                }catch(RuntimeError re){
                    return readJREObjectProperty(base, baseClass, (String)index);
                }
            }
        }

        String mess = "index "+index.getClass().getName()+" not supported, need string for base: "+base.getClass().getName();
        Logger.getLogger(ArrayIndex.class.getName()).fine(mess);
        throw new CastError(this, mess);
    }
    // </editor-fold>

    /* (non-Javadoc) @see Value */
    @Override
    public Value deepClone() {
        ArrayIndex c = new ArrayIndex(this.factory,this);
        if( baseValue!=null )c.baseValue = baseValue.deepClone();
        if( indexValue!=null )c.indexValue = c.indexValue.deepClone();
        if( c.baseValue!=null )c.baseValue.setParent(c);
        if( c.indexValue!=null )c.indexValue.setParent(c);
        return c;
    }

    /* (non-Javadoc) @see Value */
    @Override
    public Value[] getChildren() {
        return new Value[]{ baseValue, indexValue };
    }

    /* (non-Javadoc) @see Value */
    @Override
    public void setChild(int index, Value tn) {
        if( index==0 ){ baseValue = tn; return; }
        if( index==1 ){ indexValue = tn; return; }
        throw new IndexOutOfBoundsException();
    }
}
