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
package xyz.cofe.lang2.vm.ext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.ParserOptions;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Pair;
import xyz.cofe.collection.set.ClassSet;

/**
 * Расширяет тип/под тип добавляе новые поля
 * @author gocha
 */
public class JavaTypeExtender extends AbstractExtender
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(JavaTypeExtender.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected ClassSet extndedClasses = new ClassSet();
    protected Map<Class,Map<String,Convertor>> extenders = new HashMap<Class, Map<String, Convertor>>();
    protected static Pair<Object,Boolean> failed = new BasicPair<Object, Boolean>(null,false);
    
    public void addExtendFields(ParserOptions opts){
        if (opts== null) {            
            throw new IllegalArgumentException("opts==null");
        }
        for( ParserOptions.JavaTypeExtField e : opts.getJavaTypeExtFields() )
            addExtendField(e);
    }
    
    public boolean addExtendField(ParserOptions.JavaTypeExtField ext){
        if (ext== null) {            
            throw new IllegalArgumentException("ext==null");
        }
        return addExtendField(ext.getType(), ext.getFieldName(), ext.getFieldValue());
    }
    
    public boolean addExtendField(Class clazz,String fieldName,Convertor extendedField){
        if (clazz== null) {            
            throw new IllegalArgumentException("clazz==null");
        }
        if (fieldName== null) {            
            throw new IllegalArgumentException("fieldName==null");
        }
        if (extendedField== null) {            
            throw new IllegalArgumentException("extendedField==null");
        }
        
        logFine("addExtendField( javaClass={0} field={1} converot={2} )", clazz, fieldName, extendedField.toString());
        
        if( !extndedClasses.contains(clazz) )extndedClasses.add(clazz);
        Map<String,Convertor> m = null;
        if( extenders.containsKey(clazz) ){
            m = extenders.get(clazz);
        }else{
            m = new HashMap<String, Convertor>();
            extenders.put(clazz, m);
        }
        m.put(fieldName, extendedField);
        return true;
    }
    
    @Override
    public Pair<Object, Boolean> extendArrayIndex(Value invoker, Object base, Object index) {
        if( base==null || index==null )return failed;
        if( !(index instanceof String) )return failed;
        Class bclz = base.getClass();
        Collection<Class> eclz = //extndedClasses.getAssignableFrom(bclz, true, false);
                extndedClasses.getParentClassesFrom(bclz);
        if( eclz.size()<1 )return failed;
        if( eclz.size()==1 ){
            Class[] _eclz = eclz.toArray(new Class[]{});
            Pair<Object, Boolean> res = extend(invoker, base, _eclz[0], (String)index);
            if( res.B() ){
                logFiner(
                        "extendArrayIndex( success arrayBaseClass={0} registerClass={1} field={2} ) = {3}", 
                        bclz.getName(), _eclz[0].getName(), index, res.A()
                        );
            }else{
                logFiner(
                        "extendArrayIndex( failed arrayBaseClass={0} registerClass={1} field={2} )", 
                        bclz.getName(), _eclz[0].getName(), index
                        );
            }
            return res;
        }else{
            ClassSet cset = new ClassSet(true,eclz);
            int idx = -1;
            int co = cset.size();
            for( Class c : cset ){
                idx++;
                Pair<Object, Boolean> r = extend(invoker, base, c, (String)index);
                if( r.B() ){
                    logFiner(
                            "extendArrayIndex( success arrayBaseClass={0} registerClass={1} field={2} ) = {3}", 
                            bclz.getName(), c.getName(), index, r.A()
                            );
                    return r;
                }else{
                    logFiner(
                            (idx < (co-1)) ?
                            "extendArrayIndex( failed arrayBaseClass={0} registerClass={1} field={2} ) try next"
                            :
                            "extendArrayIndex( failed arrayBaseClass={0} registerClass={1} field={2} )"
                            , 
                            bclz.getName(), c.getName(), index
                            );
                }
            }
        }
        return failed;
    }
    
    protected Pair<Object,Boolean> extend(Value invoker, Object base, Class extClass, String index) {
        if( !extenders.containsKey(extClass) )return failed;
        Map<String,Convertor> m = extenders.get(extClass);
        if( m.containsKey(index) ){
            Convertor o = m.get(index);
            if( o==null )return failed;
            return new BasicPair<Object, Boolean>(o.convert(base), Boolean.TRUE);
        }
        return failed;
    }
    
    protected static BasicPair<Iterator,Boolean> failedItr = new BasicPair<Iterator, Boolean>(null, false);

    @Override
    public Pair<Iterator, Boolean> extendFor(Value invoker, Object extendedObject) {
        return failedItr;
    }

    @Override
    public Pair<Object, Boolean> extendOperator(Value invoker, OperatorName opName, Object... args) {
        return faild;
    }

    @Override
    public Pair<Integer, Boolean> extendCompare(Value invoker, Object arg1, Object arg2) {
        return faildCompare;
    }
}
