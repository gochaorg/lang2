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
package xyz.cofe.lang2.parser;

import xyz.cofe.lang2.vm.SourceRange;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import org.antlr.runtime.Token;

/**
 * Позиция в исходном тексте
 * @author gocha
 */
public class SourceLocation implements Comparable {
    /**
     * Диапазон текста в исходном коде
     */
    public static class Range {
        protected SourceLocation begin = null;
        protected SourceLocation end = null;
        
        /**
         * Конструктор
         * @param begin Начало диапазона
         * @param end Конец диапазона
         */
        public Range(SourceLocation begin,SourceLocation end){
            if (begin== null) {
                throw new IllegalArgumentException("begin==null");
            }
            if (end== null) {
                throw new IllegalArgumentException("end==null");
            }
            if( begin.compareTo(end) > 0 ){
                this.begin = end;
                this.end = begin;
            }else{
                this.begin = begin;
                this.end = end;
            }
        }

        /**
         * Начало диапазона
         * @return 
         */
        public SourceLocation getBegin() {
            return begin;
        }

        /**
         * Конец диапазона
         * @return 
         */
        public SourceLocation getEnd() {
            return end;
        }
        
        /**
         * Возвращает расположение данного узла в исходном тексте
         * @param v Узел
         * @return Диапазон текста или null
         */
        public static Range rangeOf(Value v){
            return rangeOf(v, true);
        }
        
        /**
         * Возвращает расположение данного узла в исходном тексте
         * @param v Узел
         * @param offsetEnd Смещать правую границу к концу лексемы
         * @return Диапазон текста или null
         */
        public static Range rangeOf(Value v,boolean offsetEnd){
            if (v== null) {
                throw new IllegalArgumentException("v==null");
            }
            if(v instanceof SourceRange ){
                SourceRange dv = (SourceRange)v;
                Token tStart = dv.getStart();
                Token tEnd = dv.getStop();
                if( tStart!=null && tEnd!=null ){
                    int yStart = tStart.getLine() - 1;
                    int xStart = tStart.getCharPositionInLine();
                    
                    int yEnd = tEnd.getLine() - 1;
                    int xEnd = tEnd.getCharPositionInLine();
                    
                    String tEndText = tEnd.getText();
                    if( offsetEnd ){
                        if( tEndText!=null ){
                            xEnd += tEndText.length();
                            return new Range(new SourceLocation(yStart, xStart), new SourceLocation(yEnd, xEnd));
                        }
                    }else{
                        return new Range(new SourceLocation(yStart, xStart), new SourceLocation(yEnd, xEnd));
                    }
                }
            }
            Value p = v.getParent();
            if( p instanceof SourceRange ){
                Range r = rangeOf(p, offsetEnd);
                return r;
            }
            return null;
        }
    }
    
    /**
    * Индекс символа в строке от 0..№
    */
    protected int x = 0;

    /**
    * Индекс строки от 0 .. №
    */
    protected int y = 0;

    /**
    * Конструктор
    * @param y Индекс строки от 0 .. №
    * @param x Индекс символа в строке от 0..№
    */
    public SourceLocation(int y, int x){
        this.y = y;
        this.x = x;
    }

    /**
    * Индекс символа в строке от 0..№
    * @return Индекс символа
    */
    public int getX() {
        return x;
    }

    /**
    * Индекс строки от 0 .. №
    * @return Индекс строки
    */
    public int getY() {
        return y;
    }

    @Override
    public int compareTo(Object e) {
        if( e==null )return 1;
        if( e instanceof SourceLocation ){
            SourceLocation c = (SourceLocation)e;
            int cmpY = getY() == c.getY() ? 0 : (getY() < c.getY() ? -1 : 1);
            int cmpX = getX() == c.getX() ? 0 : (getX() < c.getX() ? -1 : 1);
            if( cmpY!=0 )return cmpY;
            return cmpX;
        }
        return 1;
    }
    
    public CommentReciver.Comment createComment(String text){
        if (text== null) {
            throw new IllegalArgumentException("text==null");
        }
        CommentReciver.Comment cmt = new CommentReciver.Comment(y, x, text);
        return cmt;
    }
}
