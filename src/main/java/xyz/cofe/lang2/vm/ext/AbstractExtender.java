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

import java.util.Iterator;
import xyz.cofe.lang2.vm.op.ArrayIndex;
import xyz.cofe.lang2.vm.op.For;
import xyz.cofe.lang2.vm.op.Operator;
import xyz.cofe.collection.BasicPair;

/**
 * Расширение - Абстрактный класс
 */
public abstract class AbstractExtender implements ArrayIndex.Extender, For.Extender, Operator.Extender {
    /**
     * Возвращаемое значение обозначающее отсуствие расширения
     */
    public static final BasicPair<Object,Boolean> faild = new BasicPair<Object, Boolean>(null, false);
    
    /**
     * Возвращаемое значение обозначающее отсуствие расширения
     */
    public static final BasicPair<Integer,Boolean> faildCompare = new BasicPair<Integer, Boolean>(0, false);

    /**
     * Возвращаемое значение обозначающее отсуствие расширения
     */
    public static final BasicPair<Iterator,Boolean> faildForExtend = new BasicPair<Iterator, Boolean>(null, false);
    
    /**
     * Формирует результат расширения
     * @param res Результат
     * @return Результат возвращаемый методом extend
     */
    protected BasicPair<Object,Boolean> result(Object res){
        return new BasicPair<Object, Boolean>(res, true);
    }
}
