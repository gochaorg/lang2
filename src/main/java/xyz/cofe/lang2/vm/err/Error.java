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

import org.antlr.runtime.Token;
import xyz.cofe.lang2.vm.SourceRange;
import xyz.cofe.lang2.vm.Value;

/**
 * Ошибка VM
 * @author gocha
 */
public class Error extends java.lang.Error
{
    /**
     * Конструктор
     * @param message сообщение о ошибке
     */
    public Error(String message) {
        super(message);
    }

    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message сообщение о ошибке
     */
    public Error(Object source,String message) {
        super(message);
        this.source = source;

        SourceRange srcRange = null;
        if( source instanceof Value ){
            Value v = (Value)source;
            if( source instanceof SourceRange ){
                srcRange = (SourceRange)v;
            }else if( v.getParent() instanceof SourceRange ){
                srcRange = (SourceRange)v.getParent();
            }

            if( srcRange!=null ){
                this.sourceCode = srcRange;
            }
        }
    }

    /**
     * Конструктор
     * @param source Источник ошибки
     * @param message сообщение о ошибке
     * @param parent ошибка
     */
    public Error(Object source,String message,Throwable parent) {
        super(message,parent);
        this.source = source;
        
        SourceRange srcRange = null;
        if( source instanceof Value ){
            Value v = (Value)source;
            if( source instanceof SourceRange ){
                srcRange = (SourceRange)v;
            }else if( v.getParent() instanceof SourceRange ){
                srcRange = (SourceRange)v.getParent();
            }

            if( srcRange!=null ){
                this.sourceCode = srcRange;
            }
        }
    }

    protected Object source = null;

    /**
     * Указывает на объект вызвавший ошибку
     * @return Объект - источник
     */
    public Object getSource(){
        return source;
    }
    
    protected SourceRange sourceCode = null;

    /**
     * Указывает на расположение в исходном коде
     * @return расположение в исходном коде или null
     */
    public SourceRange getSourceCodeRange() {
        return sourceCode;
    }
    
    /**
     * Возвращает индекс (от 0) строки в исходном коде
     * @return Индекс строки или -1
     */
    public int getSourceLineStartIndex(){
        if( sourceCode!=null ){
            Token t = sourceCode.getStart();
            if( t!=null )return t.getLine() - 1;
        }
        return -1;
    }

    /**
     * Возвращает индекс (от 0) символа в начальной строке исходного кода
     * @return Индекс символа или -1
     */
    public int getSourceCharStartIndex(){
        if( sourceCode!=null ){
            Token t = sourceCode.getStart();
            if( t!=null )return t.getCharPositionInLine() - 1;
        }
        return -1;
    }
    
    public String getSourceCodeText() {
        if( sourceCode==null )return null;
        
        Token tStart = sourceCode.getStart();
        if( tStart==null )return null;
        
        return tStart.getText();
    }
    
    /**
     * Конструктор
     * @param message сообщение о ошибке
     * @param parent ошибка
     */
    public Error(String message,Throwable parent){
        super(message, parent);
    }
}
