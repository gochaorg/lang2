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
package xyz.cofe.lang2.lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.cli.CLI;
import xyz.cofe.lang2.cli.ScriptReader;
import xyz.cofe.lang2.parser.CommentReciver;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.res.Resource;
import xyz.cofe.lang2.vm.Callable;
import xyz.cofe.lang2.vm.ExternalFunction;
import xyz.cofe.lang2.vm.op.Function;
//import lang2.vm.op.VMArray;
//import lang2.vm.op.VMObject;
import xyz.cofe.common.Text;
import xyz.cofe.config.SimpleConfig;

/**
 * Функции по работе с Command Line Interface
 * @author gocha
 */
public class CLIFunctions {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CLIFunctions.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Движок
     */
    protected L2Engine engine = null;
    
    /**
     * Конструктор
     * @param engine Движок
     */
    public CLIFunctions(L2Engine engine){
        if (engine== null) {
            throw new IllegalArgumentException("engine==null");
        }
        this.engine = engine;
    }
    
    //<editor-fold defaultstate="collapsed" desc="scriptReader">
    private ScriptReader scriptReader = null;
    
    public synchronized ScriptReader getScriptReader() {
        if( scriptReader==null ){
            if( engine.getMemory().containsKey("cli") ){
                Object v = engine.getMemory().get("cli");
                if( v instanceof CLI ){
                    scriptReader = ((CLI)v).getScriptReader();
                }
            }
        }
        if( scriptReader==null )scriptReader = new ScriptReader();
        return scriptReader;
    }
    
    public synchronized void setScriptReader(ScriptReader scriptReader) {
        this.scriptReader = scriptReader;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Event">
    /**
     * Событие движка
     */
    public static class Event extends EventObject {
        /**
         * Конструктор
         * @param source источник события
         */
        public Event(Object source){
            super(source);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ExitEvent">
    /**
     * Выход из программы
     */
    public static class ExitEvent extends Event {
        protected int exitCode = 0;
        /**
         * Констркутор
         * @param source Источник
         * @param exitCode Код выхода
         */
        public ExitEvent(Object source,int exitCode){
            super(source);
            this.exitCode = exitCode;
        }
        /**
         * Код выхода
         * @return код выхода
         */
        public int getExitCode(){ return exitCode; }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ErrorEvent">
    /**
     * Ошибка исполнения программы
     */
    public static class ErrorEvent extends Event{
        protected Throwable error = null;
        
        /**
         * Констркутор
         * @param source Кто вызвал
         * @param err Ошибка
         */
        public ErrorEvent(Object source,Throwable err){
            super(source);
            this.error = err;
        }
        
        /**
         * Возвращает ошибку
         * @return ошибка
         */
        public Throwable getError() {
            return error;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PrintCalledEvent">
    /**
     * Событие вызова функции print
     */
    public static class PrintCalledEvent extends Event {
        protected Object[] args = null;
        protected boolean endl = false;
        public PrintCalledEvent(Object source,boolean endl,Object ... args){
            super(source);
            this.args = args;
            this.endl = endl;
        }
        
        public Object[] getArgs() {
            return args;
        }
        
        public boolean isEndl() {
            return endl;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ReadBeginEvent">
    /**
     * Начало пользовательского ввода
     */
    public static class ReadBeginEvent extends Event {
        /**
         * Констркутор
         * @param source Источник
         */
        public ReadBeginEvent(Object source){
            super(source);
        }
    }
    /**
     * Конец пользовательского ввода
     */
    public static class ReadEndEvent extends Event {
        /**
         * Констркутор
         * @param source Источник
         */
        public ReadEndEvent(Object source){
            super(source);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Listener">
    /**
     * Подписчик на события
     */
    public interface Listener
    {
        /**
         * Событие движка
         * @param event Событие
         */
        public void cliFunctionsEvent(Event event);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Adapter">
    /**
     * Адаптер подписчика
     */
    public static class Adapter implements Listener {
        @Override
        public void cliFunctionsEvent(Event event) {
            if( event instanceof ExitEvent )exit(((ExitEvent)event).getExitCode());
            if( event instanceof ErrorEvent )error(((ErrorEvent)event).getError());
            if( event instanceof ReadBeginEvent )readBegin();
            if( event instanceof ReadEndEvent )readEnd();
        }
        
        /**
         * Выход из приложения
         * @param code Код выхода
         */
        protected void exit(int code){
        }
        
        /**
         * Ошибка выполнения приложения
         * @param err Ошибка
         */
        protected void error(Throwable err){
        }
        
        protected void readBegin(){
        }

        protected void readEnd(){
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="listeners, fireEvent">
    /**
     * Подписчики
     */
    protected Collection<Listener> listeners = new LinkedHashSet<Listener>();
    
    /**
     * Подписчики на события
     */
    public Collection<Listener> getListeners(){
        return listeners;
    }
    
    /**
     * Уведомляет подписчиков о событии
     * @param e Событие
     */
    protected void fireEvent(Event e){
        if( e==null )return;
        for( Listener l : getListeners() ){
            if( l!=null )l.cliFunctionsEvent(e);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="memObjects">
    protected Map<String,Object> memObjects = null;
    
    /**
     * Объекты для работы скриптов:<br/>
     * exit(), print(), println(), readln()
     * @return функии
     */
    public Map<String,Object> getMemObjects(){
        if( memObjects!=null )return memObjects;
        memObjects = new HashMap<String, Object>();
        memObjects.put("exit", exitInteractiveFun);
        memObjects.put("print", print);
        memObjects.put("println", println);
        memObjects.put("readln", readln);
        memObjects.put("memory", memory);
        memObjects.put("typeof", typeof);
        memObjects.put("eval", eval);
        memObjects.put("help", help);
        memObjects.put("java", java);
        memObjects.put("evalFile", evalFile);
        memObjects.put("desc", desc());
        return memObjects;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="exit()">
    /**
     * Функция выхода их приложения
     */
    public final Callable exitInteractiveFun = new ExternalFunction( funHelp("exit") ) {
        @Override
        public Object call(Object... arguments) {
            int exitCode = 0;
            if( arguments.length>0 ){
                if( arguments[0] instanceof Number ){
                    exitCode = ((Number)arguments[0]).intValue();
                }
            }
            fireEvent(new ExitEvent(CLIFunctions.this, exitCode));
            return null;
        }
    };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="output">
    /**
     * Поток вывода символьных данных
     */
    protected Writer output = null;
    
    /**
     * Указывает поток вывода
     * @return Вывод
     */
    public Writer getOutput(){
        if( output!=null )return output;
        output = new OutputStreamWriter(System.out);
        return output;
    }
    
    /**
     * Указывает поток вывода
     * @param output Вывод
     */
    public void setOutput(Writer output){
        this.output = output;
        this.endlDetectorWriter.setWriter(output);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="input">
    /**
     * Поток ввода символьных данных
     */
    protected Reader input = null;
    
    /**
     * Поток ввода символьных данных
     * @return Поток ввода
     */
    public Reader getInput(){
        if( input!=null )return input;
        input = new InputStreamReader(System.in);
        return input;
    }
    
    /**
     * Поток ввода символьных данных
     * @param reader Поток
     */
    public void setInput(Reader reader){
        this.input = reader;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="print() sprint, print">
    private Print _print = null;
    
    /**
     * Объект занимающийся выводом на экран/stdio
     * @return Вывод на экран ...
     */
    public Print getPrint(){
        if( _print!=null ){
            return _print;
        }
        SimpleConfig sc = new SimpleConfig();
        _print = new Print(conf().subset("print."));
        return _print;
    }
    
    /**
     * Выводит на печать (getOutput()) переданные аргументы
     * @param arguments Аргументы
     */
    public void print(Object ... arguments){
        if( arguments!=null ){
            for( Object arg : arguments ){
                try {
                    getOutput().write(getPrint().sprint(arg));
                } catch (IOException ex) {
                    fireEvent(new ErrorEvent(CLIFunctions.this, ex));
                }
            }
            try {
                getOutput().flush();
            } catch (IOException ex) {
                fireEvent(new ErrorEvent(CLIFunctions.this, ex));
            }
        }
    }

    /**
     * Функция печати
     */
    public final Callable print = new ExternalFunction( funHelp("print") ) {
        @Override
        public Object call(Object... arguments) {
            print( arguments );
            fireEvent(new PrintCalledEvent(this, false, arguments));
            return null;
        }
    };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="endl">
    /**
     * Символы перевода строки
     */
    protected String endl = null;
    
    /**
     * Указывает символы перевода строки
     * @return Символы перевода строки
     */
    public String getEndl(){
        if( endl!=null )return endl;
        endl = System.getProperty("line.separator", "\n");
        return endl;
    }
    
    /**
     * Указывает символы перевода строки
     * @param endl Символы перевода строки
     */
    public void setEndl(String endl){
        this.endl = endl;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="println()">
    /**
     * Выводит на стд. поток ввода-вывода переданные аргументы
     * @param arguments Аргументы
     */
    public void println(Object ... arguments){
        if( arguments!=null ){
            for( Object arg : arguments ){
                try {
                    getOutput().write(getPrint().sprint(arg));
                } catch (IOException ex) {
                    fireEvent(new ErrorEvent(CLIFunctions.this, ex));
                }
            }
            try {
                getOutput().write(getEndl());
                getOutput().flush();
            } catch (IOException ex) {
                fireEvent(new ErrorEvent(CLIFunctions.this, ex));
            }
        }
    }
    
    /**
     * Функция печати с переводом строки
     */
    public final Callable println = new ExternalFunction( funHelp("println") ) {
        @Override
        public Object call(Object... arguments) {
            println( arguments );
            fireEvent(new PrintCalledEvent(this, true, arguments));
            return null;
        }
    };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="prompt">
    protected String prompt = null;
    
    /**
     * Указывает подсказку при вызове функции readln
     * @return Подсказка
     */
    public String getPrompt() {
        if( prompt==null )prompt = conf().get("prompt", ">>> ");
        return prompt;
    }
    
    /**
     * Указывает подсказку при вызове функии readln
     * @param prompt Подсказка
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Свойства /lang2/res/functions.xml">
    private static SimpleConfig conf = null;
    private static SimpleConfig conf(){
        if( conf!=null )return conf;
        conf = new SimpleConfig("functions.xml",true);
        return conf;
    }
    
    protected static Resource helps = new Resource("functions.help.xml", true);
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="funHelp">
    /**
     * Возвращает справку по функции из файла /lang2/res/functions.xml , ключ help.function.funName
     * @param funName Имя функции
     * @return Справка
     */
    protected static String funHelp(String funName){
        if (funName== null) {
            throw new IllegalArgumentException("funName==null");
        }
        String key = funName;
        String help = null;
        if( helps.containsKey(key) ){
            help = helps.get(key);
        }else{
            help = "Отсуствует справка по функции "+funName;
        }
        return CommentReciver.formatHelp(help);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="readln()">
    /**
     * Функция читающая из стандартного потока ввода
     */
    public final Callable readln = new ExternalFunction( funHelp("readln") ) {
        @Override
        public Object call(Object... arguments) {
            fireEvent(new ReadBeginEvent(CLIFunctions.this));
            Scanner s = new Scanner(getInput());
            String line = null;
            try{
                if( arguments!=null && arguments.length>0 ){
                    print( arguments[0] );
                }else{
                    print( getPrompt() );
                }
                line = s.nextLine();
                fireEvent(new ReadEndEvent(CLIFunctions.this));
                return line;
            }catch(NoSuchElementException ex){
                fireEvent(new ErrorEvent(CLIFunctions.this, ex));
            }catch(IllegalStateException ex){
                fireEvent(new ErrorEvent(CLIFunctions.this, ex));
            }
            fireEvent(new ReadEndEvent(CLIFunctions.this));
            return null;
        }
    };
    //</editor-fold>

    //TODO Mem Работа с памятью
    //<editor-fold defaultstate="collapsed" desc="memory()">
    /**
     * Возвращает содержимое памяти
     */
    public final Callable memory = new ExternalFunction( funHelp("memory") ){
        @Override
        public Object call(Object... arguments) {
            Map mem = new LinkedHashMap();
            if( engine!=null ){
                for( String k : engine.getMemory().keySet() ){
                    if( k==null )continue;
                    if( k.contains("$") )continue;
                    mem.put(k, engine.getMemory().get(k));
                }
            }
            return mem;
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="help()">
    /**
     * Справка
     */
    public final Callable help = new ExternalFunction( funHelp("help") ){
        @Override
        public Object call(Object... arguments) {
            if( arguments.length==0 ){
                String helpCli = CLI.help(CLI.HELP_MAIN);
                println.call(helpCli);
                return null;
            }
            if( engine==null ){
                println.call("Нет возможности получить справку");
            }
            if( arguments[0]!=null ){
                String varName = arguments[0].toString();
                if( engine.getMemory().containsKey(varName) ){
                    Object oVal = engine.getMemory().get( varName );
                    String help = null;
                    if( oVal instanceof ExternalFunction ){
                        help = ((ExternalFunction)oVal).getComment();
                    }else if( oVal instanceof Function ){
                        help = ((Function)oVal).getComment();
                    }else{
                        help = "Указанная переменная не является функцией";
                    }
                    if( help==null ){
                        help = "У данной функции отсуствует справка";
                    }
                    println.call(help);
                }else{
                    println.call(
                        Text.template(
                            "Указанная функция {0} не найдена",
                            varName
                        ));
                }
            }else{
                println.call("Укажите в первом аргументе имя функции");
            }
            return null;
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eval()">
    /**
     * Выполнение кода
     */
    public final Callable eval = new ExternalFunction( funHelp("eval") ){
        @Override
        public Object call(Object... arguments) {
            if( arguments.length==0 ){
                return null;
            }
            if( engine==null ){
                return null;
            }
            Object _code = arguments[0];
            if( _code!=null ){
                String code = _code.toString();
                return engine.eval(code);
            }
            return null;
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="eval()">
    /**
     * Выполнение кода
     */
    public final Callable evalFile = new ExternalFunction( funHelp("evalFile") ){
        @Override
        public Object call(Object... arguments) {
            if( arguments.length==0 ){
                return null;
            }
            if( engine==null ){
                return null;
            }
            Object arg0 = arguments[0];
            if( arg0!=null ){
                String script = null;
                File file = null;
                Charset cs = null;
                if( arg0 instanceof File )file = (File)arg0;
                if( arg0 instanceof String )file = new File((String)arg0);
                if( arguments.length>1 && arguments[1] instanceof Charset ){
                    cs = (Charset)arguments[1];
                }
                ScriptReader reader = getScriptReader();
                if( file!=null ){
                    script = reader.readScriptFile(file, cs);
                    if( script==null ){
                        logSevere("Не могу прочесть файл {0}", file);
                        return false;
                    }
                }
                if( script==null )return null;
                return engine.eval(script);
            }
            return null;
        }
    };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="typeof()">
    public static final String UNDEFINED = "undefined";
//    public static final String VMOBJECT = "object";
//    public static final String VMARRAY = "list";
    public static final String STRING = "string";
    public static final String BOOLEAN = "bool";
    public static final String INTEGER = "int";
    public static final String BYTE = "byte";
    public static final String SHORT = "short";
    public static final String LONG = "long";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String NULL = "null";
    public static final String JREMAP = "object";
    public static final String JRELIST = "list";
    public static final String JREOBJECT = "JREObject";
    public static final String FUNCTION = "function";
    
    /**
     * Возвращает базовую информацию о типе
     */
    public final Callable typeof = new ExternalFunction( funHelp("typeof()") ){
        @Override
        public Object call(Object... arguments) {
            if( arguments.length==0 )return UNDEFINED;
            Object arg = arguments[0];
            if( arg==null )return NULL;
            if( arg instanceof String )return STRING;
            if( arg instanceof Boolean )return BOOLEAN;
            
            if( arg instanceof Integer )return INTEGER;
            if( arg instanceof Short )return SHORT;
            if( arg instanceof Long )return LONG;
            if( arg instanceof Byte )return BYTE;
            if( arg instanceof Float )return FLOAT;
            if( arg instanceof Double )return DOUBLE;
            
//            if( arg instanceof Function )return FUNCTION;
            if( arg instanceof Callable )return FUNCTION;
//            if( arg instanceof VMObject )return VMOBJECT;
//            if( arg instanceof VMArray )return VMARRAY;
            
            if( arg instanceof Map )return JREMAP;
            if( arg instanceof List )return JRELIST;
            return JREOBJECT;
        }
    };
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="java()">
    /**
     * Создает объект java
     */
    public JavaFunction java = new JavaFunction( funHelp("java") );
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="desc()">
    private EndlDetectorWriter endlDetectorWriter = null;
    private EndlDetectorWriter getEndlDetectorWriter(){
        if( endlDetectorWriter!=null )return endlDetectorWriter;
        EndlDetectorWriter detector = new EndlDetectorWriter(getOutput());
        endlDetectorWriter = detector;
        return detector;
    }
    
    /**
     * Отображает информацию о объекте
     */
    
    private static class EndlDetectorWriter extends Writer {
        private Writer writer = null;
        
        public EndlDetectorWriter(Writer src){
            if (src== null) {                
                throw new IllegalArgumentException("src==null");
            }
            this.writer = src;
        }

        public Writer getWriter() {
            return writer;
        }

        public void setWriter(Writer writer) {
            if (writer== null) {                
                throw new IllegalArgumentException("writer==null");
            }
            this.writer = writer;
        }
        
        private char last0 = (int)0;
        private char last1 = (int)0;
        
        public boolean hasEndl(){
            if( last0=='\n' && last1=='\r' )return true;
            if( last0=='\r' && last1=='\n' )return true;
            if( last1=='\n' )return true;
            if( last1=='\r' )return true;
            return false;
        }

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            writer.write(cbuf, off, len);
            if( len>1 ){
                last0 = cbuf[off+len-2];
                last1 = cbuf[off+len-1];
            }else if(len>0){
                last0 = last1;
                last1 = cbuf[off+len-1];
            }
        }

        @Override
        public void flush() throws IOException {
//            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void close() throws IOException {
//            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private static Desc desc = null;
    public Desc desc(){
        if( desc!=null )return desc;
        desc = new Desc(conf().subset("desc.")){
            @Override
            public Object call(Object... arguments) {
                Object res = super.call(arguments);
                fireEvent(new PrintCalledEvent(desc(), getEndlDetectorWriter().hasEndl(), arguments));
                return res;
            }
        };
        desc.setOut(getEndlDetectorWriter());
        return desc;
    }
    //</editor-fold>
}
