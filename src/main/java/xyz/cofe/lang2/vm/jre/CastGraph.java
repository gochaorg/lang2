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

import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.graph.Edge;
import xyz.cofe.collection.graph.Path;
import xyz.cofe.collection.graph.PathFinder;

/**
 * Граф конвертирования типов
 * @author gocha
 */
public class CastGraph 
//    extends org.gocha.collection.graph.MappedSDGraph<Class,Caster>
    extends xyz.cofe.collection.graph.SimpleSDGraph<Class,Caster>
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CastGraph.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CastGraph.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CastGraph.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CastGraph.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CastGraph.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CastGraph.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Поиск пути преобразования и объекта класса cFrom в объект класса cTo
     * @param cFrom Исходный класс
     * @param cTo Конечный класс
     * @return Путь или null если такого не существует
     */
    public Path<Class,Caster> findPath(Class cFrom, Class cTo){
        if (cFrom== null) {            
            throw new IllegalArgumentException("cFrom==null");
        }
        if (cTo== null) {            
            throw new IllegalArgumentException("cTo==null");
        }
        
        if( !contains(cFrom) )return null;
        if( !contains(cTo) )return null;
        
        PathFinder<Class,Caster> pf = new PathFinder<Class, Caster>(
                this, 
                cFrom, 
                Path.Direction.AB, 
                WeightedCaster.getWeightComparator());
        
        Path<Class,Caster> path = null;
        Path<Class,Caster> res = null;
        while(pf.hasNext()){
            path = pf.next();
            Class last = path.getLastNode();
            if( last.equals(cTo) ){
                res = path;
                break;
            }
        }
        
        return res;
    }
    
    public static Caster createCaster(Path<Class,Caster> path){
        if (path== null) {            
            throw new IllegalArgumentException("path==null");
        }
        
        int pathSize = path.size();
        if( pathSize==0 ){
            throw new IllegalArgumentException("path.size()==0");
        }
        
        if( pathSize==1 )return path.get(0).getEdge();
        
        SequenceCaster sc = new SequenceCaster();
        for( Edge<Class,Caster> e : path ){
            sc = sc.append(e.getEdge());
        }
        
        return sc;
    }
}
