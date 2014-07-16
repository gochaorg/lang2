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
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Value;

/**
 * Создание массива/списка.<br/>
 * Конструкция: <code><b>'['</b> ( <i>exp</i> ( <b>','</b> <i>exp</i> ) * ) ? <b>']'</b></code>.<br/>
 * В процессе исполнения создает пустой (или заполненынй начальными данными) список.
 * @author gocha
 */
public class CreateArray extends AbstractTreeNode<Value> implements Value {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CreateArray.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CreateArray.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CreateArray.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CreateArray.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CreateArray.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CreateArray.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    public CreateArray(){
        children = new Value[]{};
    }
    
    protected Value[] children = null;

    public CreateArray(Iterable data){
        initData(data);
    }
    
    protected void initData(Iterable data){
        List<Value> values = new ArrayList<Value>();
        if( data!=null ){
            for( Object item : data ){
                if( item!=null && item instanceof Value ){
                    values.add( (Value)item );
                    ((Value)item).setParent(this);
                }else{
                    values.add(null);
                }
            }
        }
        if( values!=null ){
            children = values.toArray(new Value[]{});
        }
    }

    public CreateArray(CreateArray src){
        children = new Value[]{};
    }
    
    @Override
    public Value[] getChildren() {
        return children;
    }

    @Override
    public Object evaluate() {
        ArrayList array = new ArrayList();
        if( children!=null ){
            for( Value v : children ){
                if( v!=null ){
                    array.add( v.evaluate() );
                }else{
                    array.add( null );
                }
            }
        }
        return array;
    }

    @Override
    public Value deepClone() {
        return new CreateArray(this);
    }

    @Override
    public void setChild(int index, Value tn) {
        if( index<0 )throw new IndexOutOfBoundsException();
        if( children==null ){
            children = new Value[]{};
        }
        if( index>=children.length ){
            children = Arrays.copyOf(children, index+1);
        }
        children[index] = tn;
        if( tn!=null )tn.setParent(this);
    }
}
