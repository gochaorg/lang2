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
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import org.antlr.runtime.Token;

/**
 * Ошибка периода исполнения
 * @author gocha
 */
public class RuntimeError extends Error
{
    /**
     * Конструктор
     * @param message Сообщение
     */
    public RuntimeError(String message){
        super(message);
    }
    
    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message Сообщение
     */
    public RuntimeError(Object source,String message){
        super(source, message+getDebugInfo(source));
    }
    
    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message Сообщение
     * @param parent Вложенная ошибка
     */
    public RuntimeError(Object source,String message,Throwable parent){
        super(source, message+getDebugInfo(source), parent);
    }
    
    /**
     * Конструктор
     * @param message Источник ошибки
     * @param parent Вложенная ошибка
     */
    public RuntimeError(String message,Throwable parent){
        super(message, parent);
    }
    
    /**
     * Возвращает информацию для отладки
     * @param source Источник сообщения
     * @return Информация
     */
    protected static String getDebugInfo(Object source){
        if( source instanceof Value ){
            Value v = (Value)source;
            
            DebugWrapperValue debug = null;
            if( v instanceof DebugWrapperValue ){
                debug = (DebugWrapperValue)v;
            }else if( v.getParent() instanceof DebugWrapperValue ){
                debug = (DebugWrapperValue)v.getParent();
            }
            
            if( debug!=null ){
                Token tok = debug.getStart();
                
                int line = tok.getLine();
                int charPos = tok.getCharPositionInLine() + 1;
                String text = tok.getText();
                
                StringBuilder sb = new StringBuilder();
                sb.append("\n");
                sb.append("line="+line+" pos="+charPos+" text="+text);
                return sb.toString();
            }
        }
        return "";
    }
}
