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
package xyz.cofe.lang2.cli;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.Pair;
import xyz.cofe.files.FileUtil;
import xyz.cofe.text.Text;

/**
 * Загрузка скрипта
 * @author nt.gocha@gmail.com
 */
public class ScriptReader {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ScriptReader.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Читает заголовок исходника
     * @param file Файл исходник
     * @param cs Кодировка
     * @param size Кол-во байт которое надо прочесть
     * @return Заголовок или null если есть проблемы с чтением заголовка
     */
    private static String readScriptHeaderText(URL file,Charset cs,int size){
        if( file==null )return null;
        try {
            InputStream fin = file.openStream();
            byte[] headerBytes = new byte[size];
            int readed = fin.read(headerBytes);
            if( readed>0 ){
                String headerText = new String(headerBytes, 0, readed, cs);
                fin.close();
                return headerText;
            }
            fin.close();
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Читает заголовок исходника
     * @param file Файл исходник
     * @param cs Кодировка
     * @param size Кол-во байт которое надо прочесть
     * @return Заголовок или null если есть проблемы с чтением заголовка
     */
    private static String readScriptHeaderText(File file,Charset cs,int size){
        if( file==null )return null;
        if( !file.exists() || !file.isFile() || !file.canRead() )return null;
        try {
            FileInputStream fin = new FileInputStream(file);
            try {
                byte[] headerBytes = new byte[size];
                int readed = fin.read(headerBytes);
                if( readed>0 ){
                    String headerText = new String(headerBytes, 0, readed, cs);
                    fin.close();
                    return headerText;
                }
                fin.close();
                return null;
            } catch (IOException ex) {
                return null;
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
    
    /**
     * Читает скрипт файл.<br/>
     * Возможен специальный формат заголовка в котором будут указаны параметры как загруджать файл.
     * @param file Путь файла
     * @param cs Кодировка файла, может быть null
     * @return Скрипт или null если не может прочесть
     * @see lang2.cli.ScriptHeader
     */
    public static String readScriptFile(File file,Charset cs){
        if( cs==null )cs = Charset.defaultCharset();
        if (file== null) {            
            throw new IllegalArgumentException("file==null");
        }
        File f = file;
        if( !f.exists() ){
            System.err.println("Файл "+f+" не найден");
            return null;
        }
        if( !f.isFile() ){
            System.err.println("Не является файлом "+f);
            return null;
        }
        
        ScriptHeader header = null;
        
        Charset latin1 = Charset.forName("latin1");
        String headerText = readScriptHeaderText(file,latin1,64*1024);
        if( headerText!=null ){
            header = ScriptHeader.parse(headerText);
        }
        if( header!=null && header.getSourceCharset()!=null ){
            cs = header.getSourceCharset();
        }
        
        String code = FileUtil.readAllText(file, cs);
        if( code==null ){
            System.err.println("Невозможно прочесть файл "+file);
            return null;
        }
        
        String ccode = cleanupScriptHeader(code, header);
        return ccode;
    }
    
    /**
     * Читает скрипт файл.<br/>
     * Возможен специальный формат заголовка в котором будут указаны параметры как загруджать файл.
     * @param file Путь файла
     * @param cs Кодировка файла, может быть null
     * @return Скрипт или null если не может прочесть
     * @see lang2.cli.ScriptHeader
     */
    public static String readScriptFile(URL file,Charset cs){
        if( file==null )throw new IllegalArgumentException( "file==null" );
        if( cs==null )cs = Charset.defaultCharset();

        ScriptHeader header = null;
        
        Charset latin1 = Charset.forName("latin1");
        String headerText = readScriptHeaderText(file,latin1,64*1024);
        if( headerText!=null ){
            header = ScriptHeader.parse(headerText);
        }
        if( header!=null && header.getSourceCharset()!=null ){
            cs = header.getSourceCharset();
        }
        
        String code = FileUtil.readAllText(file, cs);
        if( code==null ){
            System.err.println("Невозможно прочесть файл "+file);
            return null;
        }
        
        String ccode = cleanupScriptHeader(code, header);
        return ccode;
    }
    
    /**
     * Удаляет заголовок BASH из скрипта
     * @param content содержимое скрипт файла
     * @return Очищенное содержимое
     */
    private static String cleanupScriptHeader(String content, ScriptHeader header){
        // skip shebang
        StringBuilder result = new StringBuilder();
        boolean hasShebang = false;
        int shebangBegin = 0;
        int shebangEnd = 0;
        if( header!=null && header.hasShebang() ){
            hasShebang = true;
            shebangBegin = 0;
            int i = content.indexOf(header.getShebangEnd());
                if( i>=0 ){
                    shebangEnd = i + header.getShebangEnd().length();
                }else{
                    hasShebang = false;
                }
        }
        int nextIdx = 0;
        if( hasShebang ){
            for( int i=0; i<shebangEnd - shebangBegin; i++ ){
                char c = content.charAt(i);
                if( c=='\n' || c=='\r' ){
                    result.append(c);
                }else{
                    result.append(' ');
                }
            }
            nextIdx = shebangEnd;
        }
        
        //skip whitespace to next line
        if( nextIdx>0 ){
            Pair<Integer,String> n = Text.nextNewLine(content, nextIdx);
            if( n!=null && n.A()>nextIdx ){
                int skip = n.A() - nextIdx;
                int spaces = 0;
                if( skip>n.B().length() ){
                    spaces = skip - n.B().length();
                }
                if( spaces>0 )result.append( Text.repeat(" ", spaces) );
                result.append(n.B());
                nextIdx = n.A();
            }
        }
        
        //skip bash comments
        StringBuilder sb = result;
        int state = 0;
        for( int i=nextIdx; i<content.length(); i++ ){
            char c0 = content.charAt(i);
            char c1 = i<(content.length()-1) ? content.charAt(i+1) : (char)0;
            switch( state ){
                case 0:
                    if( c0=='#' ){
                        state = 1;
                        sb.append(" ");
                    }else{
                        state = 100;
                        sb.append(c0);
                    }
                    break;
                case 1:
                    if( c0=='\n' && c1=='\r' ){
                        //Acorn BBC, RISC OS
                        state = 2;
                        sb.append("\n");
                    }else if( c0=='\r' && c1=='\n' ){
                        //CR+LF - Windows,Dos
                        state = 3;
                        sb.append("\r");
                    }else if( c0=='\n' && c1!='\r' ){
                        // Unix, linux, ....
                        state = 0;
                        sb.append("\n");
                    }else if( c0=='\r' && c1!='\n' ){
                        // Mac os
                        state = 0;
                        sb.append("\r");
                    }else{
                        state = 1;
                        sb.append(" ");
                    }
                    break;
                case 2:
                    state = 0;
                    sb.append("\r");
                    break;
                case 3:
                    state = 0;
                    sb.append("\n");
                    break;
                case 100:
                    sb.append(c0);
                    break;
            }
        }
        
        return result.toString();
    }
}
