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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.Factory;
import xyz.cofe.lang2.vm.NullRefError;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.collection.Pair;

/**
 * Доступ к полю объекта
 * @author gocha
 */
public class FieldIndex extends ArrayIndex
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(FieldIndex.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    /**
     * Конструктор по умолчанию
     */
    public FieldIndex(Factory factory)
    {
        super(factory);
    }
	
    /**
     * Конструктор копирования
     * @param src Образец для копирования
     */
	public FieldIndex(Factory factory,FieldIndex src){
		super(factory,src);
	}

    /**
     * Конструктор копирования
     * @param src Образец для копирования
     * @param deep Глубокое копирование
     */
	public FieldIndex(Factory factory, FieldIndex src,boolean deep){
		super(factory,src,deep);
	}
    
    /**
	 * Конструктор доступа к элементу объекта
	 * @param vmObject Объект
	 * @param vmField Поле
     */
    public FieldIndex(Factory factory, Value vmObject,Value vmField){
        super(factory,vmObject, vmField);
    }

    private static final String length = "length";

    /* (non-Javadoc) @see ArrayIndex */
    @Override
    protected EvalValue readJREArray(Object base,Object index)
    {
//        Value vIndex = getIndexValue();
//        if( vIndex==null ){
//            Logger.getLogger(ArrayIndex.class.getName()).severe("index is null (vm error)");
//            throw new CompileException(this, "index==null");
//        }
//        Object index = vIndex.evaluate();
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
            if( ext.B() )return evalValue(ext.A(), true, index);
        }
        if( index==null ){
            Logger.getLogger(ArrayIndex.class.getName()).severe("index is null");
            throw new NullRefError(this, "index is null");
        }
//        if( index instanceof String ){
//            String fieldName = (String)index;
//
//            if( fieldName.equals(length) ){
////                return new BasicPair<Object, Boolean>( Array.getLength(base), true );
//                return evalValue(Array.getLength(base), true, index);
//            }
//        }
        if( index instanceof Number ){
            int idx = ((Number)index).intValue();
            return super.readJREArray(base, idx, index);
//            return evalValue(base, true, index)
        }
//        String mess = "index (class:"+index.getClass().getName()+
//                " = "+index.toString()+
//                " not supported";
//        Logger.getLogger(FieldIndex.class.getName()).fine(mess);
//        throw new RuntimeError(this, mess);
        
//        return new BasicPair<Object, Boolean>(null, false);
        
        return evalValue(null, false, index);
    }

    /* (non-Javadoc) @see ArrayIndex */
    @Override
    protected EvalValue readList(Object base, Object index)
    {
//        Value vIndex = getIndexValue();
//        if( vIndex==null ){
//            Logger.getLogger(ArrayIndex.class.getName()).severe("index is null (vm error)");
//            throw new CompileException(this, "index==null");
//        }
//        Object index = vIndex.evaluate();
        if( extender!=null ){
            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, base, index);
            if( ext.B() )
//                return ext;
                return evalValue(ext.A(), true, index);
        }
        if( index==null ){
            Logger.getLogger(ArrayIndex.class.getName()).severe("index is null");
            throw new NullRefError(this, "index is null");
        }
        if( index instanceof String ){
            String fieldName = (String)index;

            if( fieldName.equals(length) ){
//                return new BasicPair<Object, Boolean>( ((List)base).size(), true );
                return evalValue( ((List)base).size(), true, index);
            }
            
            //TODO Делегировать ArrayIndex.readJREObject
        }
        if( index instanceof Number ){
            int idx = ((Number)index).intValue();
            return super.readList((List)base, idx);
        }
//        String mess = "index (class:"+index.getClass().getName()+
//                " = "+index.toString()+
//                " not supported";
//        Logger.getLogger(FieldIndex.class.getName()).fine(mess);
//        throw new RuntimeError(this, mess);
        
//        return new BasicPair<Object, Boolean>(null, false);
        
        return evalValue(null, false, index);
    }

    /* (non-Javadoc) @see ArrayIndex */
//    @Override
//    protected EvalValue readMap(Map map, Object index)
//    {
//        if( extender!=null ){
//            Pair<Object,Boolean> ext = extender.extendArrayIndex(this, map, index);
//            if( ext.B() )//return ext;
//                return evalValue(ext.A(), true, index);
//        }
//        if( index instanceof String ){
//            String fieldName = (String)index;
//            if( fieldName.equals(length) ){
////                return new BasicPair<Object, Boolean>( ((Map)map).size(), true );
//                return evalValue( ((Map)map).size(), true, index);
//            }
//            //TODO Делегировать ArrayIndex.readJREObject
//        }
//        return super.readMap(map, index);
//    }

    /* (non-Javadoc) @see ArrayIndex */
    @Override
    public Value deepClone() {
        return new FieldIndex(factory,this, true);
    }
}
