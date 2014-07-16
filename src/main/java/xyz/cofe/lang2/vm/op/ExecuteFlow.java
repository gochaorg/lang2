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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Описывает прерывание/перемешение потока вычислений
 * @author gocha
 */
public interface ExecuteFlow {
    /**
     * Цель куда передается управление
     */
    public enum Target {
        /**
         * Прерываение выполнения цикла
         */
        Break,
        /**
         * Прерываение выполнения текущей итерации цикла и выполнение новой
         */
        Continue,
        /**
         * Прерывания выполнения функции
         */
        Return,
        /**
         * Прерывания выполнения цикла, функци и т.д. пока не дойдет до try...catch
         */
        Throw;
    }

    /**
     * Возвращает уровениь прерывания/перемещение потока вычислений
     * @return Цель куда передается поток вычислений
     */
    Target getExecuteFlowTarget();

    /**
     * Проверяет наличае результата прерывания (например значение return)
     * @return true - результат присуствует / false - отсуствует
     */
    boolean isHasFlowResult();

    /**
     * Возвращает значение прерывания (например значение return)
     * @return Значение
     */
    Object getFlowResult();
}