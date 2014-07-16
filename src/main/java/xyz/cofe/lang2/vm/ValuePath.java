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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.IfElse;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.collection.Predicate;
import xyz.cofe.text.Text;

/**
 * Путь в дереве Value
 * @author gocha
 */
public class ValuePath implements List<Value> {
    /**
     * Парсинг пути без DebugWrapperValue
     * @param value Значение
     * @return Путь
     */
    public static ValuePath parsePathWODebug(Value value){
        if (value== null) {            
            throw new IllegalArgumentException("value==null");
        }
        List<Value> list = new ArrayList<Value>();
        Value v = value;
        while(true){
            if( v==null )break;
            if( !(v instanceof DebugWrapperValue) ){
                list.add( v );
            }
            v = v.getParent();
        }
        Collections.reverse(list);
        return new ValuePath(list);
    }
    
    protected List<Value> list = null;
    
    public ValuePath(){
        list = new ArrayList<Value>();
    }
    
    /**
     * Конструктор
     * @param v узел OP
     */
    public ValuePath(Value v){
        if (v== null) {            
            throw new IllegalArgumentException("v==null");
        }
        List<Value> res = new ArrayList<Value>();
        while( v!=null ){
            res.add(v);
            v = v.getParent();
        }
        Collections.reverse(res);
        list = res;
    }
    
    /**
     * Конструктор
     * @param path путь 
     */
    public ValuePath(List<Value> path){
        if (path== null) {
            throw new IllegalArgumentException("path==null");
        }
        this.list = path;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int i = -1;
        for( Value v : this ){
            i++;
            if( i>0 )sb.append("/");
            
            int idx = v.getIndex();
            
            String clz = v.getClass().getName();
            if( clz.startsWith("lang2.vm.op.") ){
                clz = Text.trimStart(clz, "lang2.vm.op.");
            }
            
            sb.append(idx);
            sb.append(".");
            sb.append(clz);
        }
        return sb.toString();
    }
    
    public ValuePath diff(ValuePath vp){
        if (vp== null) {
            throw new IllegalArgumentException("vp==null");
        }
        
        int size1 = size();
        int size2 = vp.size();
        int max = size1 > size2 ? size1 : size2;
        
        List<Value> dvp = new ArrayList<Value>();
        
        boolean putv1 = size1 > size2 ? true : false;
        
        for( int i=0; i<max; i++ ){
            Value v1 = i < size1 ? get(i) : null;
            Value v2 = i < size2 ? vp.get(i) : null;
            if( v1!=v2 ){
                dvp.add( putv1 ? v1 : v2 );
            }
        }
        
        return new ValuePath(dvp);
    }
    
    public ValuePath subPath(int fromIndex,int toIndexExc){
        return new ValuePath(list.subList(fromIndex, toIndexExc));
    }
    
    public Value getLast(){
        if( isEmpty() )return null;
        return get(size()-1);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Методы по работе со списком">
    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
    
    @Override
    public Object[] toArray() {
        return list.toArray();
    }
    
    @Override
    public List<Value> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
    
    @Override
    public int size() {
        return list.size();
    }
    
    @Override
    public Value set(int index, Value element) {
        return list.set(index, element);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }
    
    @Override
    public Value remove(int index) {
        return list.remove(index);
    }
    
    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }
    
    @Override
    public ListIterator<Value> listIterator(int index) {
        return list.listIterator(index);
    }
    
    @Override
    public ListIterator<Value> listIterator() {
        return list.listIterator();
    }
    
    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
    
    @Override
    public Iterator<Value> iterator() {
        return list.iterator();
    }
    
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }
    
    @Override
    public int hashCode() {
        return list.hashCode();
    }
    
    @Override
    public Value get(int index) {
        return list.get(index);
    }
    
    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }
    
    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }
    
    @Override
    public void clear() {
        list.clear();
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends Value> c) {
        return list.addAll(index, c);
    }
    
    @Override
    public boolean addAll(Collection<? extends Value> c) {
        return list.addAll(c);
    }
    
    @Override
    public void add(int index, Value element) {
        list.add(index, element);
    }
    
    @Override
    public boolean add(Value e) {
        return list.add(e);
    }
    //</editor-fold>
    
    public static class Predicates {
        private static Predicate<ValuePath> variable = new Predicate<ValuePath>() {
            @Override
            public boolean validate(ValuePath value) {
                Value v = value.getLast();
                return v != null && v instanceof Variable;
            }
        };
        private static Predicate<ValuePath> _if = new Predicate<ValuePath>() {
            @Override
            public boolean validate(ValuePath value) {
                Value v = value.getLast();
                return v != null && v instanceof If;
            }
        };
        private static Predicate<ValuePath> ifElse = new Predicate<ValuePath>() {
            @Override
            public boolean validate(ValuePath value) {
                Value v = value.getLast();
                return v != null && v instanceof IfElse;
            }
        };
        public static Predicate<ValuePath> Variable(){
            return variable;
        }
        public static Predicate<ValuePath> If(){
            return _if;
        }
        public static Predicate<ValuePath> IfElse(){
            return ifElse;
        }
        public static enum Compare {
            Equals,More,Less,MoreOrEquals,LessOrEquals,NotEquals
        }
        public static Predicate<ValuePath> Index(Compare compare,int index){
            final int fi = index;
            final Compare cmp = compare;
            return new Predicate<ValuePath>() {
                @Override
                public boolean validate(ValuePath vp) {
                    Value v = vp.getLast();
                    if( v==null )return false;
                    int idx = v.getIndex();
                    switch(cmp){
                        case Equals:        return idx==fi;
                        case NotEquals:     return idx!=fi;
                        case More:          return idx>fi;
                        case Less:          return idx<fi;
                        case MoreOrEquals:  return idx>=fi;
                        case LessOrEquals:  return idx<=fi;
                    }
                    return false;
                }
            };
        }
        public static Predicate<ValuePath> And(Predicate<ValuePath> ... p){
            return xyz.cofe.collection.Predicates.and(p);
        }
        public static Predicate<ValuePath> Or(Predicate<ValuePath> ... p){
            return xyz.cofe.collection.Predicates.or(p);
        }
        public static Predicate<ValuePath> Parent(Predicate<ValuePath> p){
            final Predicate<ValuePath> fp = p;
            return new Predicate<ValuePath>() {
                @Override
                public boolean validate(ValuePath path) {
                    if( path==null )return false;
                    if( fp==null )return false;
                    if( path.size()<1 )return false;
                    if( path.size()==1 )return fp.validate(new ValuePath());
                    return fp.validate( path.subPath(0, path.size()-1) );
                }
            };
        }
    }
}
