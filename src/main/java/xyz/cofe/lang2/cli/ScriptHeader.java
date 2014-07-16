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


import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;
import xyz.cofe.text.Text;

/**
* Описывает заголовок файла со скриптом.<br/>
* Скрипт файл в начале может содержать коментарии в стиле Unix-Shell скриптов.
* В этих коментариях можно указать кодировку файла.
* Эти коментарии при чтении будут заменены на соот. пробелы.<br/>
* Пример:<br/>
* <div style="border-left:4px solid black;padding-left:3px;margin-left:10px;margin-top:4px;margin-bottom:6px">
* <code>
* #!/bin/sh <br/>
* exec java -cp lang2.jar lang2.cli.CLI -f "$0" "$@" <br/>
* !# <br/>
* # hello<br/>
* # coding: utf-8<br/>
* var a = 1;<br/>
* </code>
* </div>
* Такие коментраии должны начинатся с решетки и быть в начале файла.<br/>
* <br/>
* Коментарий в данном примере, начиная с символов #! и до !# интерпретируется программой /bin/sh.<br/>
* Возможно указывать следующие программы:<br/>
* /bin/sh<br/>
* /bin/csh<br/>
* /bin/bash<br/>
* /usr/bin/perl<br/>
* /usr/bin/env<br/>
* <br/>
* Коментарий <code># coding:utf-8</code> - указывает на кодировку исходника.
 * @author nt.gocha@gmail.com
 */
public class ScriptHeader {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(ScriptHeader.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    private static String[] shellShebangs = new String[]{
        "#!/bin/bash",
        "#!/bin/csh",
        "#!/bin/sh",
        "#!/usr/bin/perl",
        "#!/usr/bin/env"
    };
    
    private static final String[] shellShebangEndMarkers = 
        new String[]{ "#!shebang", "!#" };
    
    private static Pair<String,String> matchShebangEnd( String source, int fromIndex ){
        for( String sh : shellShebangEndMarkers ){
            if( Text.matchText(source, sh, fromIndex, false) ){
                return new BasicPair<String, String>(source.substring(fromIndex, fromIndex+sh.length()), sh);
            }
        }
        return null;
    }
    
    private static String matchShebangs(String firstLine){
        for( String sh : shellShebangs ){
            if( firstLine.startsWith(sh) )return sh;
        }

//                        if( line.length()>2 && line.charAt(1)=='!' ){
//                            List<File> checkFiles = new ArrayList<File>();
//                            
//                            String line2 = line.substring(2);
//                            checkFiles.add( new File( line2 ) );
//                            
//                            if( line2.contains(" ") ){
//                                int i = line2.indexOf(" ");
//                                String line3 = line2.substring(0, i);
//                                checkFiles.add( new File(line3) );
//                            }
//                            
//                            boolean hasExeFile = false;
//                            for( File f : checkFiles ){
//                                if( f.exists() ){
//                                    hasExeFile = true;
//                                    break;
//                                }
//                            }
//                            
//                            if( hasExeFile ){
//                                state = 1;
//                            }else{
//                                state = 10;
//                                res.add( line );
//                            }
        
        return null;
    }
    
    private boolean hasShebang = false;
    private ArrayList<String> shebangLines = null;
    private String shebangEnd = null;
    private ArrayList<String> comments = null;

    /**
     * Анализирует заголовок скрипта
     * @param unformatedHeader заголовок скрипта
     * @return заголовок
     */
    public static ScriptHeader parse( String unformatedHeader ){
        if( unformatedHeader==null )throw new IllegalArgumentException( "unformatedHeader==null" );
        ScriptHeader header = new ScriptHeader();
        header.comments = new ArrayList<String>();
        header.shebangLines = new ArrayList<String>();
        header.shebangEnd = "";

        String[] lines = Text.splitNewLines(unformatedHeader);
        int state = 0;
        for( String line : lines ){
            switch( state ){
                case 0:
                    boolean shebangMatched = matchShebangs(line)!=null;
                    if( shebangMatched ){
                        state = 1;
                        header.hasShebang = true;;
                        header.shebangLines.add( line );
                    }else if( line.startsWith("#") ){
                        state = 10;
                        header.comments.add( line );
                    }else{
                        state = 99;
                    }
                    break;
                case 1:
                    Pair<String,String> shebangEnd = matchShebangEnd(line,0);
                    if( shebangEnd!=null ){
                        state = 10;
                        header.shebangEnd = shebangEnd.A();
                    }else{
                        state = 1;
                        header.shebangLines.add( line );
                    }
                    break;
                case 10:
                    if( line.startsWith("#") ){
                        state = 10;
                        header.comments.add( line );
                    }else{
                        state = 99;
                    }
                    break;
                case 99:
                    break;
            }
        }

        return header;
    }

    public boolean hasShebang() {
        return hasShebang;
    }

    public ArrayList<String> getShebangLines() {
        return shebangLines;
    }

    public String getShebangEnd() {
        return shebangEnd;
    }

    /**
     * Перечень коментариев в UNIX стиле
     * @return кометарии
     */
    public List<String> getComments() {
        return comments;
    }

    private static Map<String,String> getHeaderKeyValueMap(List<String> lines){
        Map<String,String> res = new LinkedHashMap<String, String>();
        for( String line : lines ){
            String ln = line;
            if( ln.startsWith("#") && ln.length()>1 )ln = ln.substring(1);
            if( ln.contains(":") ){
                String[] kvPair = ln.split(":");
                if( kvPair.length==2 ){
                    String key = kvPair[0].trim();
                    String val = kvPair[1].trim();
                    if( key.length()>0 && val.length()>0 ){
                        res.put(key, val);
                    }
                }
            }
        }
        return res;
    }

    private Map<String,String> headerKeys = null;
    
    /**
     * Читает ключ/значение из заголовка исходника.<br/>
     * Ключ значение задается при помощи комментариев решетки, далее следует ключ, потом двоеточние и за ним значение.<br/>
     * Пример:<br/>
     * <code># coding : utf-8</code><br/>
     * У Ключа и значения удаляются концевые пробелы.
     * @return Ключ значение
     */
    public Map<String,String> getHeaderKeyValueMap(){
        if( headerKeys!=null )return headerKeys;
        headerKeys = getHeaderKeyValueMap(getComments());            
        return headerKeys;
    }

    private Charset sourceCharset = null;
    private boolean resolvSrcCS = true;
    /**
     * Ищет в исходнике указание на кодировку файла.<br/>
     * Кодировка задется в начале при помощи коментариев.<br/>
     * Пример:<br/>
     * <code># coding: utf-8</code>
     * @return кодировка или null
     */
    public Charset getSourceCharset(){
        if( sourceCharset!=null )return sourceCharset;
        if( !resolvSrcCS )return null;
        resolvSrcCS = false;
        if( getHeaderKeyValueMap().containsKey("coding") ){
            String coding = getHeaderKeyValueMap().get("coding");
            try{
                sourceCharset = Charset.forName(coding);
                return sourceCharset;
            }catch( UnsupportedCharsetException ex ){
                // TODO detail log + file name + charset name
                logException(ex);
            }catch( IllegalCharsetNameException ex ){
                // TODO detail log + file name + charset name
                logException(ex);
            }
        }
        return sourceCharset;
    }
}