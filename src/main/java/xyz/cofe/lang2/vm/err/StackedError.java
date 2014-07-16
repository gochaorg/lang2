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
package xyz.cofe.lang2.vm.err;

import xyz.cofe.lang2.vm.Value;

/**
 * Ошибка VM, для организации вложенных ошибок
 * @author gocha
 */
public class StackedError extends RuntimeError
{
    protected Value errorValue = null;
    protected StackedError parentError = null;

    /**
     * Значение (описание) связанное с ошибкой
     * @param errorValue Значение
     */
    public void setErrorValue(Value errorValue) {
        this.errorValue = errorValue;
    }

    /**
     * Родительская ошибка
     * @param parentError ошибка или null
     */
    public void setParentError(StackedError parentError) {
        this.parentError = parentError;
    }
    
    /**
     * Конструктор
     * @param message сообщение о ошибке
     */
    public StackedError(String message){
        super(message);
    }

    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message сообщение о ошибке
     */
    public StackedError(Object source,String message) {
        super(message);
        this.source = source;
    }

    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message сообщение о ошибке
     * @param parent ошибка
     */
    public StackedError(Object source,String message,Throwable parent) {
        super(message,parent);
        this.source = source;
    }

    /**
     * Конструктор
     * @param message сообщение о ошибке
     * @param parent ошибка
     */
    public StackedError(String message,Throwable parent){
        super(message, parent);
    }
}
