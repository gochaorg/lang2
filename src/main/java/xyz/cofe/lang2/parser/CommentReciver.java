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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.text.Text;

/**
 * Принимает комментарии
 * @author gocha
 */
public class CommentReciver {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CommentReciver.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Описывает комментарий
     */
    public static class Comment extends SourceLocation implements Comparable {
        private String text;

        /**
         * Конструктор
         * @param y Индекс строки от 0 и выше
         * @param x Индекс символа в строке от 0
         * @param text Текст комментария
         */
        public Comment(int y,int x,String text){
            super(y, x);
            this.text = text;
        }

        /**
         * Текст комментария
         * @return текст
         */
        public String getText(){ return text; }
    }

    /**
     * Подписчик на комментарии
     */
    public interface Listener {
        void reciveComment(Comment event);
    }

    private static HashSet<Listener> listeners = new HashSet<Listener>();
    public static Collection<Listener> getListeners(){ return listeners; }

    public static void recive(int tokenIndex,int line,int posInLine,String text){
        Comment e = new Comment(line-1, posInLine, text);
        for( Listener l : getListeners() ){
            if( l==null )continue;
            l.reciveComment(e);
        }
    }
    
    /**
     * Фильтрует комментарии относящиеся к доекументации:<br/>
     * Многострочный комментарий / * * Документация * /
     * @param comments Комментарии
     * @param unComment Убирать символы комментария ( / * *  и * / )
     * @return Комментарии документации
     */
    public static SortedSet<Comment> filterDocComments( SortedSet<Comment> comments, boolean unComment){
        if (comments== null) {
            throw new IllegalArgumentException("comments==null");
        }
        TreeSet<Comment> docComments = new TreeSet<Comment>();
        for( Comment cmnt : comments ){
            if( cmnt==null )continue;
            
            String txt = cmnt.getText();
            if( txt==null )continue;

            if( txt.startsWith("/**") ){
                String doc = txt;
                if( unComment ){
                    doc = unComment(doc);
                    doc = formatHelp(doc);
                }
                
                Comment docCmnt = new Comment(cmnt.getY(), cmnt.getX(), doc);
                docComments.add(docCmnt);
            }
        }
        return docComments;
    }
    
    /**
     * Убирает символы комментария
     */
    public static String unComment(String text){
        if (text== null) {
            throw new IllegalArgumentException("text==null");
        }
        
        if( text.length()>3 ){
            text = text.substring(3);
        }else{
            text = "";
        }

        if( text.endsWith("*/") ){
            if( text.length()>2 ){
                text = text.substring(0,text.length()-2);
            }else{
                text = "";
            }
        }
        
        return text;
    }
    
    /**
     * Убирает начальные/конечные пустые строки
     * @param lines Строки
     * @param fromBegin true - убрать началные; false - убрать конечные
     * @return Строки
     */
    public static ArrayList<String> removeEmptyLines(ArrayList<String> lines,boolean fromBegin){
        if (lines== null) {
            throw new IllegalArgumentException("lines==null");
        }
        ArrayList<String> res = new ArrayList<String>();
        if( fromBegin ){
            int state = 0;
            for( int i=0; i<lines.size(); i++ ){
                String line = lines.get(i);
                switch( state ){
                    case 0:
                        if( line.trim().length()>0 ){
                            res.add(line);
                            state = 1;
                        }
                        break;
                    case 1:
                        res.add(line);
                        break;
                }
            }
        }else{
            int state = 0;
            for( int i=lines.size()-1; i>=0; i-- ){
                String line = lines.get(i);
                switch( state ){
                    case 0:
                        if( line.trim().length()>0 ){
                            res.add(line);
                            state = 1;
                        }
                        break;
                    case 1:
                        res.add(line);
                        break;
                }
            }
        }
        
        if( !fromBegin )Collections.reverse(res);
        return res;
    }
    
    /**
     * Возвращает отступ в строке
     * @param line Строка
     * @return Отступ
     */
    public static String getIndent(String line){
        if (line== null) {
            throw new IllegalArgumentException("line==null");
        }
        StringBuilder sIndent = new StringBuilder();
        for( int i=0; i<line.length(); i++ ){
            char c = line.charAt(i);
            if( Character.isWhitespace(c) ){
                sIndent.append(c);
            }else{
                break;
            }
        }
        return sIndent.toString();
    }
    
    /**
     * Удаляет отступ из строки
     * @param line Строка
     * @param indent Отступ
     * @return Строка
     */
    public static String removeIndent(String line,String indent){
        if (line== null) {
            throw new IllegalArgumentException("line==null");
        }
        if (indent== null) {
            throw new IllegalArgumentException("indent==null");
        }
        if( line.startsWith(indent) && line.length()>indent.length() ){
            line = line.substring(indent.length());
        }
        return line;
    }
    
    /**
     * Удаляет концевые пробельные символы
     * @param line Текст
     * @return Текст
     */
    public static String removeTailWhiteSpace(String line){
        if (line== null) {
            throw new IllegalArgumentException("line==null");
        }
        int state = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=line.length()-1; i>=0; i-- ){
            char c = line.charAt(i);
            switch(state){
                case 0:
                    if( !Character.isWhitespace(c) ){
                        sb.append(c);
                        state = 1;
                    }
                    break;
                case 1:
                    sb.append(c);
                    break;
            }
        }
        return sb.reverse().toString();
    }
    
    /**
     * Разбивает текст на набор строки (по символу перевода строки)
     * @param text Текст
     * @return Строки
     */
    public static ArrayList<String> splitNewLines(String text){
        if (text== null) {
            throw new IllegalArgumentException("text==null");
        }
        String[] srcLines = Text.splitNewLines(text);
        ArrayList<String> res = new ArrayList<String>();
        res.addAll(Arrays.asList(srcLines));
        return res;
    }
    
    /**
     * Объединяет строки
     * @param endl Символ перевода строки
     * @param lines Строки
     * @return Строка
     */
    public static String join(String endl,ArrayList<String> lines){
        if (endl== null) {
            throw new IllegalArgumentException("endl==null");
        }
        if (lines== null) {
            throw new IllegalArgumentException("lines==null");
        }
        int idx = -1;
        StringBuilder sb = new StringBuilder();
        for( String l : lines ){
            idx++;
            if( idx>0 )sb.append(endl);
            sb.append(l);
        }
        
        return sb.toString();
    }
    
    /**
     * Форматирует текст для использования в качестве справки
     * @param help Текст
     * @param endl Символ перевода строки
     * @return Текст
     */
    public static String formatHelp(String help,String endl){
        if (help== null) {
            throw new IllegalArgumentException("help==null");
        }
        if (endl== null) {
            throw new IllegalArgumentException("endl==null");
        }

        ArrayList<String> srcLines = splitNewLines(help);
        srcLines = removeEmptyLines(srcLines, true);
        srcLines = removeEmptyLines(srcLines, false);
        if( srcLines.size()==0 )return "";
        
        String indent = getIndent(srcLines.get(0));
        
        ArrayList<String> lines = new ArrayList<String>();
        for( String line : srcLines ){
            line = removeIndent(line, indent);
            line = removeTailWhiteSpace(line);
            lines.add( line );
        }
        
        return join(endl, lines);
    }
    
    /**
     * Форматирует текст для использования в качестве справки
     * @param help Текст
     * @return Текст
     */
    public static String formatHelp(String help){
        if (help== null) {
            throw new IllegalArgumentException("help==null");
        }
        String endL = System.getProperty("line.separator", "\n");        
        return formatHelp(help, endL);
    }
}
