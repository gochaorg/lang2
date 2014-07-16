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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.cli.DynamicClassLoader;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Predicate;
import xyz.cofe.common.Text;
import xyz.cofe.files.FileUtil;
import xyz.cofe.text.Formatter;

/**
 *
 * @author nt.gocha@gmail.com
 */
public class FilePattern {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logFinest(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.FINEST, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(FilePattern.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(FilePattern.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    private Convertor<String,Object> createConv( final Object obj ){
        return new Convertor<String, Object>() {
            @Override
            public Object convert(String from) {
                if( from==null )return null;
                
                String base = from;
                String index = null;
                
                if( from.contains(".") ){
                    int dotIdx = from.indexOf(".");
                    if( dotIdx>0 ){
                        base = from.substring(0,dotIdx);
                        if( dotIdx<(from.length()-1) ){
                            index = from.substring(dotIdx+1);
                        }else{
                            index = null;
                        }
                    }else{
                        base = "";
                        if( from.length()>1 ){
                            index = from.substring(1);
                        }else{
                            index = null;
                        }
                    }
                }
                
                if( obj instanceof File ){
                    File file = (File)obj;
                    if( base.equals("dir") ){
                        if( file.toString().equals(".") ){
                            return file;
                        }
                        File parent = 
                            file.getParentFile();
//                            new File( file, ".." );
                        return parent;
                    }else if( base.equals("canonical") ){
                        try {
                            return file.getCanonicalFile();
                        } catch (IOException ex) {
                            Logger.getLogger(DynamicClassLoader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return file.getAbsoluteFile();
                    }else if( base.equals("absolute") ){
                        return file.getAbsoluteFile();
                    }else if( base.equals("name") ){
                        return file.getName();
                    }
                }
                
                if( obj instanceof Map ){
                    Map m = (Map)obj;
                    if( base!=null ){
                        Object ctx = m.get(base);
                        if( ctx!=null && index!=null ){
                            return createConv(ctx).convert(index);
                        }
                        return ctx;
                    }
                }
                
                return from;
            }
        };
    }

    public Iterable<File> createQuery( String searchPattern, String fileSplitter, final Map<String,Object> context ){
        if( searchPattern==null )throw new IllegalArgumentException( "searchPattern==null" );
        if( fileSplitter==null )throw new IllegalArgumentException( "fileSplitter==null" );
        if( context==null )throw new IllegalArgumentException( "context==null" );
        
        List<String> searchComponents = new ArrayList<String>();
        searchComponents.addAll(
            Arrays.asList(
                Text.split(searchPattern, fileSplitter)
            )
        );
        
        List<String> pathComponents = new ArrayList<String>();
        boolean recursive = false;
        String mask = null;
        
        while( true ){
            if( searchComponents.isEmpty() )break;
            
            String searchCmpt = searchComponents.get(0);
            searchComponents.remove(0);
            
            if( searchCmpt.equals("**") ){
                recursive = true;
                mask = "";
                int maskCmptIdx = -1;
                for( String maskCmpt : searchComponents ){
                    maskCmptIdx++;
                    if( maskCmptIdx>0 )mask += fileSplitter;
                    mask += maskCmpt;
                }
                break;
            }
            
            if( searchCmpt.contains("*") ){
                recursive = false;
                mask = "";
                int maskCmptIdx = -1;
                for( String maskCmpt : searchComponents ){
                    maskCmptIdx++;
                    if( maskCmptIdx>0 )mask += fileSplitter;
                    mask += maskCmpt;
                }
                break;
            }
            
            String pathCmpt = Formatter.format(searchCmpt, createConv(context));
            pathComponents.add(pathCmpt);
        }
        
        String pathRoot = "";
        int pathCmptIdx = -1;
        for( String pathCmpt : pathComponents ){
            pathCmptIdx++;
            if( pathCmptIdx>0 )pathRoot += fileSplitter;
            pathRoot += pathCmpt;
        }
        
        if( mask!=null ){
            String pathMask = pathRoot + fileSplitter + mask;
            Predicate<File> pathPredicate = FileUtil.Predicates.pathMask(pathMask, false, true);
            Iterable<File> rootIterable = 
                recursive ?
                    FileUtil.Iterators.walk(new File(pathRoot))
                : FileUtil.Iterators.list(new File(pathRoot));
            
//            // debug
//            for( File f : rootIterable ){
//                boolean m = pathPredicate.validate(f);
//                System.out.println(""+(m?"1":"0")+" "+f);
//            }
//            //
            
            Iterable<File> filtered = FileUtil.Iterators.filter(rootIterable, pathPredicate);
            return filtered;
        }
        
        Iterable<File> rootIterable = 
            recursive ?
                FileUtil.Iterators.walk(new File(pathRoot))
            : FileUtil.Iterators.list(new File(pathRoot));
        
        return rootIterable;
    }
}
