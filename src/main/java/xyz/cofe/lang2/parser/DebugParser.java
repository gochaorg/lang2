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

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.CommentReciver.Comment;
import xyz.cofe.lang2.vm.Value;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

/**
 * Парсер для отладки
 * @author gocha
 */
public class DebugParser extends BasicParser {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(DebugParser.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(DebugParser.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(DebugParser.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(DebugParser.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(DebugParser.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(DebugParser.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected ParserOptions parserOptions = null;

    /**
     * Конструктор
     * @param source входные данные
     */
    public DebugParser(String source,ParserOptions opts){
    	super( new CommonTokenStream(new lang2Lexer(new StringStream(source))), new RecognizerSharedState(),opts);
        this.parserOptions = opts;
        
//        Factory sfactory = super.factory();
        
    }

//    /**
//     * Конструктор
//     * @param input входные данные
//     * @param state востановление после ошибок
//     */
//    public DebugParser(TokenStream input, RecognizerSharedState state)
//    {
//        super(input, state);
//    }

    private Factory debugFactory = null;

    @Override
    public Factory factory() {
        if( debugFactory!=null )return debugFactory;
        
        Factory l2f = super.factory();
        if( l2f==null )l2f = new L2Factory(memory);
        
        Factory df = new DebugFactory(l2f);
        debugFactory = df;
        return debugFactory;
    }

//    @Override
//    public void setFactory(Factory factory) {
//        super.setFactory(factory);
//        
//        if( debugFactory instanceof Closeable ){
//            try {
//                ((Closeable)debugFactory).close();
//            } catch (IOException ex) {
//                logException(ex);
//            }
//        }
//        debugFactory = null;
//    }

    public static class ParseResult {
        public Value value;
        public Collection<CommentReciver.Comment> comments;
    }

    private static CommentReciver.Listener commentListener(ParseResult reciver){
        final ParseResult pr = reciver;
        CommentReciver.Listener l = new CommentReciver.Listener() {
            @Override
            public void reciveComment(Comment event) {
                pr.comments.add(event);
            }
        };
        return l;
    }

    public static ParseResult parseExpressions(Map<String,Object> memory,String source,ParserOptions opts){
        if (memory== null) {
            throw new IllegalArgumentException("memory==null");
        }
        if (source== null) {
            throw new IllegalArgumentException("source==null");
        }

        ParseResult pr = new ParseResult();
        pr.comments = new ArrayList<Comment>();
        CommentReciver.Listener listener = commentListener(pr);
        CommentReciver.getListeners().add(listener);

        DebugParser parser = new DebugParser(source,opts);
        parser.memory(memory);

        try {
            pr.value = parser.parse();
        } catch (RecognitionException ex) {
            Logger.getLogger(DebugParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            CommentReciver.getListeners().remove(listener);
            listener = null;
        }
        return pr;
    }
}
