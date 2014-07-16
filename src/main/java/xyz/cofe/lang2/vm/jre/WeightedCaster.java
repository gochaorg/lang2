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

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.graph.Edge;
import xyz.cofe.collection.graph.Path;

/**
 * Взвешенный Caster
 * @author gocha
 */
public class WeightedCaster implements Caster, EdgeWeight
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(WeightedCaster.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    protected Caster delegateTarget = null;
    protected int weight = 0;
    
    /**
     * Конструктор
     * @param delegateTarget Кому делегируются обращения cast(..)
     * @param weight Вес
     */
    public WeightedCaster(Caster delegateTarget, int weight){
        if (delegateTarget== null) {            
            throw new IllegalArgumentException("delegateTarget==null");
        }
        this.delegateTarget = delegateTarget;
        this.weight = weight;
    }

    /* (non-Javadoc) @see Caster */
    @Override
    public Object cast(Object from) {
        return delegateTarget.cast(from);
    }

    /* (non-Javadoc) @see EdgeWeight */
    @Override
    public int getEdgeWeight() {
        return weight;
    }
    
    @Override public String toString(){
        return "WeightedCaster("+weight+"):"+delegateTarget.toString();
    }

    /**
     * Возвращает вес пути
     * @param path Путь
     * @return Вес
     */
    public static int getWeightOf(Path<Class,Caster> path){
        if (path== null) {            
            throw new IllegalArgumentException("path==null");
        }
        
        int weight = 0;
        for(Edge<Class,Caster> e : path){
            if( e==null )continue;
            
            Caster c = e.getEdge();
            if( c==null )continue;
            
            if( c instanceof EdgeWeight ){
                int w = ((EdgeWeight)c).getEdgeWeight();
                weight += w;
            }
        }
        
        return weight;
    }

    private static final Comparator<Path<Class,Caster>> comparator = new Comparator<Path<Class, Caster>>() {
        @Override
        public int compare(Path<Class, Caster> p1, Path<Class, Caster> p2) {            
            int weight1 = getWeightOf(p1);
            int weight2 = getWeightOf(p2);
            return (weight1==weight2 ? 0 : (weight1>weight2 ? 1 : -1));
        }
    };
    
    /**
     * Сравнение веса путей
     * @return Сравнение
     */
    public static Comparator<Path<Class,Caster>> getWeightComparator() {
        return comparator;
    }
}
