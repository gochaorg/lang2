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
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.tree.TreeNode;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.ValuePath;
import xyz.cofe.collection.BasicVisitor;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Iterators;
import xyz.cofe.collection.NodesExtracter;
import xyz.cofe.collection.Predicate;
import xyz.cofe.collection.Visitor;
import xyz.cofe.collection.iterators.TreeWalk;
import xyz.cofe.collection.iterators.TreeWalkItreator;

/**
 * Абстрактный узел дерева
 * @author gocha
 */
public abstract class AbstractTreeNode<Node> 
    extends xyz.cofe.collection.tree.AbstractTreeNode<Node>
    implements TreeNode<Node>
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(AbstractTreeNode.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    //TODO Doc it!
    protected static NodesExtracter<Value,Value> valueChildren = new NodesExtracter<Value, Value>() {
        @Override
        public Iterable<Value> extract(Value from) {
            if( from==null )return null;
            if( from instanceof TreeNode ){
                TreeNode tn = (TreeNode)from;
                Object[] children = tn.getChildren();
                if( children==null )return null;
                return xyz.cofe.types.TypesUtil.Iterators.isClassFilter(Iterators.array(children), Value.class, false);
//                return Iterators.isClassFilter(Iterators.array(children), Value.class, false);
            }
            return null;
        }
    };
    
    //TODO Doc it!
    public static NodesExtracter<Value,Value> valueChildren(){
        return valueChildren;
    }
    
    /**
     * Возвращает итератор по древу, обход производится от корня к листьям рекурсивно по веткам.
     * @param value Корень
     * @return Узлы дерева
     */
    public static Iterable<TreeWalk<Value>> tree(Value value){
        if (value == null) {            
            throw new IllegalArgumentException("value==null");
        }
        return TreeWalkItreator.createIterable(value, valueChildren());
    }
    
    /**
     * Конвертор TreeWalk&lt;Value&gt; -&gt; currentNode() -&gt; Value 
     */
    public static final Convertor<TreeWalk<Value>,Value> treeWalkValue = new Convertor<TreeWalk<Value>, Value>() {
        @Override
        public Value convert(TreeWalk<Value> from) {
            if( from!=null )return from.currentNode();
            return null;
        }
    };
    
    /**
     * Возвращает итератор по древу, обход производится от корня к листьям рекурсивно по веткам.
     * @param value Корень
     * @return Узлы дерева
     */
    public static Iterable<Value> walk(Value value){
        if (value== null) {
            throw new IllegalArgumentException("value==null");
        }
        return Iterators.<TreeWalk<Value>,Value>convert(tree(value), treeWalkValue);
    }
    
    /**
     * Фильтрует входную последовательность
     * @param src Входная последовательность
     * @param filter Фильтр
     * @return Отфильтрованная последовательность
     */
    public static Iterable<Value> filter(Iterable<Value> src,Predicate<Value> filter){
        if (src== null) {
            throw new IllegalArgumentException("src==null");
        }
        if (filter== null) {
            throw new IllegalArgumentException("filter==null");
        }
        return Iterators.<Value>predicate(src, filter);
    }

    /**
     * Фильтрует входную последовательность исключая null ссылки
     * @param src Входная последовательность
     * @return Отфильтрованная последовательность
     */
    public static Iterable<Value> notNullFilter(Iterable<Value> src){
        if (src== null) {
            throw new IllegalArgumentException("src==null");
        }
        return Iterators.<Value>notNullFilter(src);
    }
    
    /**
     * Конвертирует последовательность в указанный тип
     * @param <A> Тип
     * @param src Последовательность
     * @param convertor Конвертор
     * @return Ковертированная последовательность
     */
    public static <A> Iterable<A> convert(Iterable<Value> src,Convertor<Value,A> convertor){
        return Iterators.<Value,A>convert(src, convertor);
    }
    
    //TODO Doc it!
    public static void visit(Visitor<Value> visitor,Value v){
        BasicVisitor.visit(visitor, v, valueChildren());
    }
    
    /**
     * Возвращает "путь" в дереве
     * @param v Узел в древе
     * @return Путь от корня, до узла
     */
    public static ValuePath getPath(Value v){
        if (v== null) {            
            throw new IllegalArgumentException("v==null");
        }
        List<Value> res = new ArrayList<Value>();
        while( v!=null ){
            res.add(v);
            v = v.getParent();
        }
        Collections.reverse(res);
        ValuePath vp = new ValuePath(res);
        return vp;
    }
}
