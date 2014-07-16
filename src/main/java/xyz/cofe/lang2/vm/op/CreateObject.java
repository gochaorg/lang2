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


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.Factory;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.common.Wrapper;

/**
 *
 * @author gocha
 */
public class CreateObject extends AbstractTreeNode<Value> implements Value {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CreateObject.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CreateObject.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CreateObject.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CreateObject.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CreateObject.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CreateObject.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    protected Factory factory = null;
    
    public CreateObject(Factory factory){        
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
    }
    
    public CreateObject(Factory factory,Iterable fields){
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
        if( fields!=null ){
            init(fields);
        }
    }
    
    public CreateObject(Factory factory,CreateObject src){
        if( factory==null )throw new IllegalArgumentException( "factory==null" );
        this.factory = factory;
        if( src!=null ){
            Iterable srcFields = src.getFields();
            if( srcFields!=null ){
                init(srcFields);
            }
        }
    }
    
    protected List<Value> fields = null;

    public List<Value> getFields() {
        if( fields!=null )return fields;
        fields = new ArrayList<Value>();
        return fields;
    }
    
    protected void init(Iterable fields){
        List<Value> lf = getFields();
        lf.clear();
        if( fields!=null ){
            for( Object o : fields ){
                if( o instanceof Value ){
                    lf.add( (Value)o );
                    ((Value)o).setParent(this);
                }
            }
        }
    }

    @Override
    public Value[] getChildren() {
        return getFields().toArray(new Value[]{});
    }

    @Override
    public Object evaluate() {
        LinkedHashMap object = new LinkedHashMap();
        Iterable createFields = getFields();
        if( createFields!=null ){
            for( Object o : createFields ){
                if( !(o instanceof CreateField) ){
                    if( (o instanceof Wrapper) ){
                        o = ((Wrapper)o).unwrap();
                    }else if( o instanceof Value ){
                        o = ((Value)o).evaluate();
                    }
                }
                
                if( o instanceof CreateField ){
                    CreateField cf = (CreateField)o;
                    String fieldName = cf.getFieldName();
                    Value fieldValue = cf.getFieldValue();
                    if( fieldName!=null ){
                        Object fv = fieldValue!=null ? fieldValue.evaluate() : null;
                        if( fv instanceof Function ){
                            if( !(fv instanceof Method) ){
                                Method m = factory.createMethod(object, (Function)fv);
                                fv = m;
                            }
                        }
                        object.put(fieldName, fv);
                    }
                }
            }
        }
        return object;
    }

    @Override
    public Value deepClone() {
        return new CreateObject(factory,this);
    }

    @Override
    public void setChild(int index, Value tn) {
        if( index<0 )throw new IndexOutOfBoundsException("index="+index);
        List<Value> lf = getFields();
        int addCount = index>=lf.size() ? (index-lf.size()+1) : 0;
        if( addCount>0 ){
            for( int i=0;i<addCount;i++ ){
                lf.add(null);
            }
        }
        lf.set(index,tn);
        if( tn!=null )tn.setParent(this);
    }
}
