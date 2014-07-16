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

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.graph.Edge;
import xyz.cofe.collection.graph.Path;

/**
 * Последовательное преобразование
 * @author gocha
 */
public class SequenceCaster implements Caster, EdgeWeight
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(SequenceCaster.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    /**
     * Массив преобразователей
     */
    protected Caster[] casters = null;
    
    /**
     * Конструктор
     * @param casters Цепочка преобразования
     */
    public SequenceCaster(Caster ... casters){
        if (casters== null) {            
            throw new IllegalArgumentException("casters==null");
        }
        this.casters = casters;
    }
    
    public SequenceCaster(Path<Class,Caster> path){
        if (path== null) {            
            throw new IllegalArgumentException("path==null");
        }
        
        int pathSize = path.size();
        if( pathSize==0 ){
            throw new IllegalArgumentException("path.size()==0");
        }
        
        casters = new Caster[pathSize];
        int idx = -1;
        for( Edge<Class,Caster> e : path ){
            idx++;
            if( idx>=0 && idx<casters.length )casters[idx] = e.getEdge();
        }
    }
    
    /**
     * Преобразовывает объект по цепочке
     * @param from Объект
     * @return Результат преобразования
     */
    @Override
    public Object cast(Object from) {
        Object o = from;
        for( Caster c : casters ){
            if( c==null )continue;
            o = c.cast(o);
        }
        return o;
    }

    /**
     * Вес преобразования.
     * Установите в null что бы заново пересчитать сумарный вес.
     */
    protected Integer weight = null;
    
    /**
     * Возвращает сумарный вес преобразования
     * @return Вес преобразования
     */
    @Override
    public int getEdgeWeight() {
        if( weight!=null )return weight;
        
        weight = 0;
        for( Caster c : casters ){
            if( c==null )continue;
            if( c instanceof EdgeWeight ){
                int w = ((EdgeWeight)c).getEdgeWeight();
                weight += w;
            }
        }
        return weight;
    }
    
    /**
     * Добавляет преобразование в конец
     * @param caster Преобразователь
     * @return Новая последовательность преобразователей
     */
    public SequenceCaster append( Caster caster ){
        if (caster== null) {            
            throw new IllegalArgumentException("caster==null");
        }
        
        Caster[] newCasters = Arrays.copyOf(casters, casters.length+1);
        newCasters[newCasters.length-1] = caster;
        return new SequenceCaster(newCasters);
    }

    @Override
    public String toString() {
        int i = -1;
        StringBuilder sb = new StringBuilder();
        for( Caster c : casters ){
            if( c==null )continue;
            i++;
            if(i>0)sb.append(" -> ");
            sb.append(c.toString());
        }
        return sb.toString();
    }
}
