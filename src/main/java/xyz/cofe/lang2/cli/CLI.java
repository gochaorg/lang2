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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.lib.CLIFunctions;
import xyz.cofe.lang2.lib.CLIFunctions.Event;
import xyz.cofe.lang2.parser.BasicParser;
import xyz.cofe.lang2.parser.L2Engine;
import xyz.cofe.lang2.parser.ParserOptions;
import xyz.cofe.lang2.res.Resource;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.TypeSupport;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.collection.Convertor;
import xyz.cofe.collection.Predicate;
import xyz.cofe.collection.map.EventMap;
import xyz.cofe.collection.map.EventMapAdapter;
import xyz.cofe.files.FileUtil;
import xyz.cofe.text.EndLine;
import xyz.cofe.text.Text;
import xyz.cofe.text.UnionWriter;
import xyz.cofe.config.SaveConfigThread;
import xyz.cofe.config.SimpleConfig;

// TODO На будущее: Информация о типе - поле type
// TODO На будущее: trigger на иземение переменной
// TODO На будущее: readonly перемененные
// TODO На будущее: Справка по языку
// TODO Присваивание метода другому объекту
// TODO Wrap java object
// TODO apply method

/**
 * Входная точка
 * @author gocha
 */
public class CLI
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(CLI.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(CLI.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(CLI.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(CLI.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="config">
    private SimpleConfig _config = null;
    private SimpleConfig conf(){
        if( _config!=null ){
            return _config;
        }
        _config = new SimpleConfig("cli.properties",false);
        return _config;
    }
    public final SimpleConfig getConfig(){
        return conf();
    }
    //<editor-fold defaultstate="collapsed" desc="get / set">
    private String conf( String key, String def ){
        return conf().get(key, def);
    }
    private String conf( String key, String def, String comment ){
        return conf().get(key, def, comment);
    }
    private void confset( String key, String val ){
        conf().set(key, val);
    }
    private boolean conf( String key, boolean def ){
        return conf().get(key, def);
    }
    private boolean conf( String key, boolean def, String comment ){
        return conf().get(key, def, comment);
    }
    private void confset( String key, boolean val ){
        conf().set(key, val);
    }
    private int conf( String key, int def ){
        return conf().get(key, def);
    }
    private int conf( String key, int def, String comment ){
        return conf().get(key, def, comment);
    }
    private void confset( String key, int val ){
        conf().set(key, val);
    }
    private Charset conf( String key, Charset def ){
        return conf().get(key, def);
    }
    private Charset conf( String key, Charset def,String comment ){
        return conf().get(key, def, comment);
    }
    private void confset( String key, Charset val ){
        conf().set(key, val);
    }
    private EndLine conf( String key, EndLine def ){
        return conf().get(key, def);
    }
    private EndLine conf( String key, EndLine def, String comment ){
        return conf().get(key, def, comment);
    }
    private void confset( String key, EndLine val ){
        conf().set(key, val);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="newConfigKey">
    private String newConfigKey( String tmpl,String mapkey,Map<String,String> mapvalues ){
        String nkey = null;
        int i = 0;
        while( true ){
            i++;
            mapvalues.put(mapkey,Integer.toString(i));
            nkey = Text.template(tmpl, mapvalues);
            if( getConfig().containsKey(nkey) )continue;
            break;
        }
        return nkey;
    }
    private String newConfigKey( String tmpl ){
        Map<String,String> v = new HashMap<String, String>();
        return newConfigKey(tmpl, "i", v);
    }
    //</editor-fold>
    //</editor-fold>
    
    private static final String CONFIG_ARG="--config=";
    private static final String CS="--cs=";
    private static final String FILE="--file=";
    private static final String EXP="--exp=";
    private static final String LOG="--log=";
    private static final String ENDL="--endl=";
    private static final String USER_INIT_SCRIPTS="--userInitScripts=";
    private static final String CONSOLE_CLASS_ARG="--consoleClass=";
    
    /**
     * Файл справки по коммандной строке
     */
    public static final String HELP_MAIN="help.txt";
    
    /**
     * Файл справки по интерактивному режиму
     */
    private static final String HELP_INTERACTIVE="interactive-hello.txt";

    /**
     * Сам движок
     */
    protected L2Engine engine = new L2Engine();
    
    /**
     * Возвращает сам движок
     * @return движок
     */
    public L2Engine getEngine(){ return engine; }

    /**
     * Результат на STD IO
     */
    protected boolean resultOnStd = true;

    /**
     * Интерактивный режим
     */
    protected boolean interactive = false;

    /**
     * Выход из интерактивного режима
     */
    protected boolean exitInteractive = false;

    /**
     * Код выхода
     */
    protected int exitInteractiveCode = 0;

    /**
     * Вывести приветствие
     */
    protected Boolean helloIntractive = null;

    /**
     * Набор функций cli
     */
    protected CLIFunctions cliFunctions = null;
    
    /**
     * Возвращает объект - набор функций
     * @return Объект набор функций
     */
    public CLIFunctions getCLIFunctions(){
        return cliFunctions;
    }

    /**
     * Была вызвана функция print
     */
    protected boolean printCalled = false;

    /**
     * Была вызвана функция println
     */
    protected boolean printCalledEndl = false;
    
    /**
     * Лог
     */
    protected final UnionWriter logWriter = new UnionWriter();
    
    private StringWriter memLogWriter = null;
    private Writer stdErrWriter = null;
    private UnionWriter startLogWriter = null;
    
    private DynamicClassLoader dyncl = null;
    
    //<editor-fold defaultstate="collapsed" desc="log()">
    /**
     * Пишет в лог текст
     * @param text Текст
     * @param linePrefix Префикс в начале каждой строки
     * @param endl Дополнять в конце переводом вконце
     */
    protected void log(String text,String linePrefix,boolean endl){
        if( text==null )return;
        
        String endlText = cliFunctions!=null ? cliFunctions.getEndl() : null;
        if( endlText==null )endlText =
                System.getProperty("line.separator", "\n");
        
        if( linePrefix==null || linePrefix.length()==0 ){
            try {
                logWriter.write(text);
                if( endl ){
                    logWriter.write(endlText);
                }
            } catch (IOException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            String[] lines = Text.splitNewLines(text);
            try {
                for( int i=0; i<lines.length; i++ ){
                    if( i>0 ){
                        logWriter.write(endlText);
                    }
                    logWriter.write(linePrefix);
                    logWriter.write(lines[i]);
                }
                if( endl )logWriter.write(endlText);
            } catch (IOException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            logWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean log_DuplicateStdERR = true;
    
    /**
     * Пишет в лог ошибку
     * @param ex Ошибка
     */
    protected void log(Throwable ex){
        log( ex.toString(), "ERROR> ", true );
        if( log_DuplicateStdERR ){
            String text = Text.indent(ex.toString(), "ERROR> ");
            System.err.println(text);
        }
    }
    
    protected void logInteractiveInput(String prompt,String input){
        String logtext = Text.indent(input, prompt);
        try {
            logWriter.write(logtext);
            logWriter.write(getEndLine().get());
        } catch (IOException ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="evalCommand">
    /**
     * Конманда выполнения введенных данных
     */
    protected String evalCommand = null;
    
    /**
     * Указывает команду выполнения введенных данных
     * @return Ком. выполнения; возможно null - немедленное выполнение
     */
    public String getEvalCommand() {
        return evalCommand;
    }
    
    /**
     * Указывает команду выполнения введенных данных
     * @param evalCommand Ком. выполнения; возможно null - немедленное выполнение
     */
    public void setEvalCommand(String evalCommand) {
        this.evalCommand = evalCommand;
        logFine("setEvalCommand( {0} )", evalCommand);
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="showType">
    /**
     * Отображать тип данных при выводе результата
     */
    protected boolean showType = false;

    /**
     * Указывает: Отображать тип данных при выводе результата
     * @return true - отображать
     */
    protected boolean isShowType() {
        return showType;
    }

    /**
     * Указывает: Отображать тип данных при выводе результата
     * @param showType true - отображать
     */
    protected void setShowType(boolean showType) {
        this.showType = showType;
        logFine("setShowType( {0} )", showType);
    }// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="prompt">
    protected String prompt = null;
    
    /**
     * Подсказака для ввода пользовательских комманд
     * @return Подсказка
     */
    public String getPrompt(){
        if( prompt==null )prompt = conf("prompt","L2> ","Подсказака для ввода пользовательских комманд");
        return prompt;
    }
    
    /**
     * Подсказака для ввода пользовательских комманд
     * @param prompt Подсказка
     */
    public void setPrompt(String prompt){
        this.prompt = prompt;
        if( prompt!=null )confset("prompt",prompt);
        logFine("setPrompt( {0} )", prompt);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="scriptReader">
    private final ScriptReader scriptReader = new ScriptReader();
    /**
     * Механизм чтения скрипт файла
     * @return чтение скрипт файла
     */
    public ScriptReader getScriptReader(){
        return scriptReader;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="var">
    private final Convertor<String,Object> confVar = new Convertor<String, Object>() {
        @Override
        public Object convert(String from) {
            if( from==null )return null;
            if( getConfig().containsKey(from) )return getConfig().get(from);
            return null;
        }
    };
    
    private final Convertor<String,Object> buildVar = new Convertor<String, Object>() {
        @Override
        public Object convert(String from) {
            if( from==null )return null;
            if( getHelpVars().containsKey(from) )return getHelpVars().get(from);
            return null;
        }
    };
    
    private final Convertor<String,Object> envVar = new Convertor<String, Object>() {
        @Override
        public Object convert(String from) {
            if( from==null )return null;
            try{
                return System.getenv(from);
            }catch(SecurityException ex){
                logException(ex);
            }
            return null;
        }
    };
    
    private final Convertor<String,Object> sysVar = new Convertor<String, Object>() {
        @Override
        public Object convert(String from) {
            if( from==null )return null;
            try{
                return System.getProperty(from);
            }catch(SecurityException ex){
                logException(ex);
            }
            return null;
        }
    };
    
    private final Convertor<String,Object> var = new Convertor<String, Object>() {
        @Override
        public Object convert(String from) {
            if( from==null )return null;
            if( from.startsWith("conf.") ){
                return confVar.convert(from.substring(5));
            }
            if( from.startsWith("build.") ){
                return buildVar.convert(from.substring(6));
            }
            if( from.startsWith("env.") ){
                return envVar.convert(from.substring(4));
            }
            if( from.startsWith("sys.") ){
                return sysVar.convert(from.substring(4));
            }
            return null;
        }
    };
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saveConfigThread()">
    private SaveConfigThread _saveConfigThread = null;
    private SaveConfigThread saveConfigThread(){
        if( _saveConfigThread==null ){
            _saveConfigThread = new SaveConfigThread();
        }
        return _saveConfigThread;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SAVE CONFIG">
    private static final String CONFIG_SAVE="config.save";
    
    public boolean isConfigSave(){
        return conf(CONFIG_SAVE,false,"Сохранять конфигурацию при измении");
    }
    
    public void setConfigSave(boolean v){
        confset(CONFIG_SAVE,v);
    }
    
    private static final String CONFIG_SAVE_CHECKTIME="config.save.checkTime";
    public int getConfigCheckTime(){
        return conf(CONFIG_SAVE_CHECKTIME,500,"Время проверки изменений в конфигурации перед ее сохранением");
    }
    public void setConfigCheckTime(int v){
        confset(CONFIG_SAVE_CHECKTIME, v);
    }
    
    private boolean saveConfigListenAttached = false;
    private void attachConfigListen(){
        if( saveConfigListenAttached )return;
        getConfig().addEventMapListener(saveConfigListener, true);
        saveConfigListenAttached = true;
    }
    
    private EventMapAdapter<String,String> saveConfigListener = new EventMapAdapter<String, String>(){
        @Override
        protected void updated(EventMap<String, String> map, String old, String key, String value) {
            if( CONFIG_SAVE.equals(key)
                || CONFIG_SAVE_CHECKTIME.equals(key)
            ){
                onSaveConfigChanged();
            }
        }
        
        @Override
        protected void deleted(EventMap<String, String> map, String key, String value) {
            if( CONFIG_SAVE.equals(key) 
                || CONFIG_SAVE_CHECKTIME.equals(key)
            ){
                onSaveConfigChanged();
            }
        }
        
        @Override
        protected void inserted(EventMap<String, String> map, String key, String value) {
            if( CONFIG_SAVE.equals(key) 
                || CONFIG_SAVE_CHECKTIME.equals(key)
            ){
                onSaveConfigChanged();
            }
        }
    };
    
    private void onSaveConfigChanged(){
        if( isConfigSave() ){
            addConfigInSaveThread();
            if( !saveConfigThread().isAlive() ){
                saveConfigThread().start();
            }
            int v = getConfigCheckTime();
            if( v<0 )v=0;
            saveConfigThread().setCheckTimePeriod(v);
        }else{
            removeConfigInSaveThread();
        }
    }
    
    private boolean isConfigInSaveThread(){
        Object conf = getConfig();
        int i = saveConfigThread().getResources().indexOf(conf);
        return i >= 0;
    }
    
    private void addConfigInSaveThread(){
        if( isConfigInSaveThread() )return;
        
        String configSaveTo = conf("config.saveTo","cli.new.properties",
            "Файл в который производится сохранение конфигурации");
        configSaveTo = Text.template(configSaveTo, var);
        File saveTo = new File(configSaveTo);
        saveConfigThread().put(getConfig(), saveTo);
    }
    
    private void removeConfigInSaveThread(){
        if( !isConfigInSaveThread() )return;
        saveConfigThread().remove(getConfig());
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="consoleClassName">
    private static final String CONSOLE_CLASS_NAME="console.className";
    public String getConsoleClassName(){
        return conf(CONSOLE_CLASS_NAME,BaseConsole.class.getName(),"Java Класс консоли ввода");
    }
    public void setConsoleClassName(String className){
        if( className==null ){
            className=BaseConsole.class.getName();
        }
        confset(CONSOLE_CLASS_NAME, className);
    }
    private void initConsoleClass(){
        String clsn = getConsoleClassName();
        initConsoleClass(clsn);
    }
    private void initConsoleClass(String clsn){
        // Инициализация консоли
        if( clsn!=null ){
            try {
//                log("init console class: "+clsn, "INIT> " ,true);
                Class cl = Class.forName(clsn);
                Constructor constr_arg_conf = null;
                Object ocons = null;
                
                try{
                    constr_arg_conf = cl.getConstructor(SimpleConfig.class);
                    String cpref = cl.getName();
                    ocons = constr_arg_conf.newInstance(
                        getConfig().subset("console."+cpref+".",true,true)
                    );
                }catch(Throwable ex){
                    ocons = cl.newInstance();
                }
                
                if( ocons instanceof Console ){
                    console = (Console)ocons;
                }
            } catch (Throwable ex) {
                log(ex);
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="endLine">
    private static final String ENDLINE = "endline";
    public EndLine getEndLine(){
        return conf(ENDLINE,EndLine.Default,"Перевод строк: default / windows / linux / mac / other");
    }
    public void setEndLine(EndLine e){
        confset(ENDLINE, e==null ? EndLine.Default : e);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="charset">
    private static final String CHARSET = "charset";
    public Charset getCharset(){
        return conf( CHARSET, Charset.defaultCharset(),"Кодировка по умолчанию" );
    }
    public void setCharset(Charset cs){
        confset(CHARSET, cs==null ? Charset.defaultCharset() : cs);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="initScripts">
    private static final String INIT_SCRIPTS = "init.scripts";
    public boolean isInitScripts(){
        return conf( INIT_SCRIPTS, false, 
            "Использовать скрипты инициализации true | false\n"
        +   "Сами скрипты указываются так:\n"
        +   "init.scripts.file.01_etc=/etc/{build.application.programm}.l2\n"
        +   "init.scripts.file.02_home={sys.user.home}/.{build.application.programm}.l2"
        );
    }
    public void setInitScripts(boolean v){
        confset( INIT_SCRIPTS, v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="flushLogOnExit">
    private static final String FLUSH_LOG_ON_EXIT = "flushLogOnExit";
    public boolean isFlushLogOnExit(){
        return conf( FLUSH_LOG_ON_EXIT, true, "flush логов при выходе " );
    }
    public void setFlushLogOnExit(boolean v){
        confset( FLUSH_LOG_ON_EXIT, v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="interactive">
    private static final String INTERACTIVE = "interactive";
    public boolean isInteractive(){
        return conf( INTERACTIVE, false, "Интерактивный режим" );
    }
    public void setInteractive(boolean v){
        confset( INTERACTIVE, v);
    }
    //</editor-fold>
    
    public String getJavaClassLoader(){
        return conf("java.classLoader","default",
                "Загрузчик java классов:\n"
            +   "  default - по умолчанию\n"
            +   "  dynamic - загрузчик lang2.cli.DynamicClassLoader\n"
        );
    }
    
    //<editor-fold defaultstate="collapsed" desc="Запуск">
    public static class StartOptions {
        //<editor-fold defaultstate="collapsed" desc="configs">
        private List<File> configs = new ArrayList<File>();
        
        public List<File> getConfigs() {
            if( configs==null )configs = new ArrayList<File>();
            return configs;
        }
        
        public void setConfigs(List<File> configs) {
            this.configs = configs;
        }
        
        public Runnable applyConfigs( final CLI cli ){
            return new Runnable() {
                @Override
                public void run() {
                    for( File confFile : getConfigs() ){
                        if( confFile.exists() && confFile.isFile() ){
//                            SimpleConfig rconf = SimpleConfig.read(confFile);
//                            if( rconf!=null ){
//                                cli.getConfig().putAll(rconf);
//                            }
                            cli.getConfig().readFrom(confFile);
                        }
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="dynamic class loader">
        private List<String> dynamicCLPaths = null;
        
        public List<String> getDynamicCLPaths() {
            if( dynamicCLPaths==null )dynamicCLPaths = new ArrayList<String>();
            return dynamicCLPaths;
        }
        
        public void setDynamicCLPaths(List<String> dynamicCLPaths) {
            this.dynamicCLPaths = dynamicCLPaths;
        }
        
        private boolean useDynamicClassLoader = false;
        
        public boolean isUseDynamicClassLoader() {
            return useDynamicClassLoader;
        }
        
        public void setUseDynamicClassLoader(boolean dynamicClassLoader) {
            this.useDynamicClassLoader = dynamicClassLoader;
        }
        
        public Runnable applyDynamicClassLoader(final CLI cli){
            return new Runnable() {
                @Override
                public void run() {
                    boolean useDyn = 
                        isUseDynamicClassLoader()
                        || 
                        cli.getJavaClassLoader().equalsIgnoreCase("dynamic");
                    
                    if( !useDyn )return;
                    
                    ClassLoader curCL = Thread.currentThread().getContextClassLoader();
                    
                    DynamicClassLoader dCL = null;
                    if( curCL instanceof DynamicClassLoader ){
                        dCL = (DynamicClassLoader)curCL;
                    }else{
                        dCL = new DynamicClassLoader( 
                            curCL, 
                            cli.getConfig().subset("java.classLoader.dynamic.",true,true) 
                        );
                        Thread.currentThread().setContextClassLoader(dCL);
                    }
                    
                    boolean setDef = true;
                    
                    File startFile = getStartFile();
                    if( startFile!=null ){
//                        Iterable<File> libsForScript = createJarSearch_forScriptFile(startFile);
//                        if( libsForScript!=null ){
//                            if( libsForScript!=null ){
//                                dCL.setJars( libsForScript );
//                                setDef = false;
//                            }
//                        }
                        dCL.evalScriptFile(startFile);
                    }
                    
                    if( setDef ){
//                        dCL.setJars(createJarSearch_forScriptDir(new File(".")));
                        dCL.evalScriptFile(new File("."));
                    }
                    
                    List<String> paths = getDynamicCLPaths();
                    if( paths.size()>0 ){
                        Iterable<File> itr = null;
                        for( String p : paths ){
                            Iterable<File> iFiltered = 
                                DynamicClassLoader.
                                    createJarSearch(new File(p));
                            
                            if( itr==null ){
                                itr = iFiltered;
                            }else{
                                itr = FileUtil.Iterators.join(itr,iFiltered);
                            }
                        }
                        
                        if( itr!=null ){
                            Iterable<File> sitr = dCL.getJars();
                            dCL.setJars(sitr!=null ? FileUtil.Iterators.join(sitr,itr) : itr );
                        }
                    }
                    
                    cli.dyncl = dCL;
                    dCL.setNeedRescan();
                }
            };
        }
//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="start scripts">
        //<editor-fold defaultstate="collapsed" desc="startScripts">
        protected List<String> startScripts = null;
        public List<String> getStartScripts(){
            if( startScripts==null )startScripts = new ArrayList<String>();
            return startScripts;
        }
        public void setStartScripts(List<String> scripts){
            startScripts = scripts;
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="startFile">
        protected File startFile = null;
        
        public File getStartFile() {
            return startFile;
        }
        
        public void setStartFile(File startFile) {
            this.startFile = startFile;
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="startArgs">
        protected List<String> startArgs = null;
        
        public List<String> getStartArgs() {
            if( startArgs==null )startArgs = new ArrayList<String>();
            return startArgs;
        }
        
        public void setStartArgs(List<String> startArgs) {
            this.startArgs = startArgs;
        }
        //</editor-fold>
        
        public Runnable applyStartScripts(final CLI cli) {
            return new Runnable() {
                @Override
                public void run() {
                    cli.evalScripts(getStartScripts(), getStartFile(), getStartArgs());
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="interactive">
        //<editor-fold defaultstate="collapsed" desc="interactive">
        protected Boolean interactive = null;
        
        public Boolean isInteractive() {
            return interactive;
        }
        
        public void setInteractive(Boolean interactive) {
            this.interactive = interactive;
        }
        //</editor-fold>
        
        public Runnable applyInteractive(final CLI cli){
            return new Runnable() {
                @Override
                public void run() {
                    boolean b1 = cli.isInteractive();
                    boolean b2 = isInteractive()!=null ? isInteractive() : false;
                    if( b1 || b2 ){
                        cli.interactive();
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="userInitScript">
        private Boolean userInitScript = null;
        
        public Boolean isUserInitScripts() {
            return userInitScript;
        }
        
        public void setUserInitScripts(Boolean userInitScript) {
            this.userInitScript = userInitScript;
        }
        
        public Runnable applyUserInitScripts( final CLI cli ){
            return new Runnable() {
                @Override
                public void run() {
                    if( cli.isInitScripts() || (isUserInitScripts()!=null && isUserInitScripts()) ){
                        List<String> startupScripts = cli.readInitScripts();
                        if( startupScripts!=null ){
                            getStartScripts().addAll(0, startupScripts);
                        }
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="consoleClassName">
        private String consoleClassName = null;
        
        public String getConsoleClassName() {
            return consoleClassName;
        }
        
        public void setConsoleClassName(String consoleClassName) {
            this.consoleClassName = consoleClassName;
        }
        
        public Runnable applyConsoleClassName( final CLI cli ){
            return new Runnable() {
                @Override
                public void run() {
                    if( consoleClassName!=null ){
                        cli.initConsoleClass(consoleClassName);
                    }else{
                        cli.initConsoleClass();
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="endline">
        private EndLine endLine = null;
        
        public EndLine getEndLine() {
            return endLine;
        }
        
        public void setEndLine(EndLine endLine) {
            this.endLine = endLine;
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="skipHello">
        private Boolean skipHello = null;
        
        public Boolean getSkipHello() {
            return skipHello;
        }
        
        public void setSkipHello(Boolean skipHello) {
            this.skipHello = skipHello;
        }
        
        public Runnable applySkipHello( final CLI cli ){
            return new Runnable() {
                @Override
                public void run() {
                    if( skipHello!=null ){
                        cli.helloIntractive = !skipHello;
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="showType">
        private Boolean showType = null;
        
        public Boolean getShowType() {
            return showType;
        }
        
        public void setShowType(Boolean showType) {
            this.showType = showType;
        }
        
        public Runnable applyShowType( final CLI cli ){
            return new Runnable() {
                @Override
                public void run() {
                    if( showType!=null ){
                        cli.showType = showType;
                    }else{
                        cli.initConsoleClass();
                    }
                }
            };
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="log">
        public static class Log {
            private File logFile = null;
            private boolean append = true;
            
            public File getLogFile() {
                return logFile;
            }
            
            public void setLogFile(File logFine) {
                this.logFile = logFine;
            }
            
            public boolean isAppend() {
                return append;
            }
            
            public void setAppend(boolean append) {
                this.append = append;
            }
            
            public Writer getWriter(){
                if( logFile==null )return null;
                try {
                    FileOutputStream fout = new FileOutputStream(logFile, append);
                    OutputStreamWriter sout = new OutputStreamWriter(fout);
                    return sout;
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }
        }
        
        private List<Log> logEntries = new ArrayList<StartOptions.Log>();
        
        public List<Log> getLogEntries() {
            if( logEntries==null ){
                logEntries = new ArrayList<StartOptions.Log>();
            }
            return logEntries;
        }
        
        public void setLogEntries(List<Log> logEntries) {
            this.logEntries = logEntries;
        }
        
        public Runnable applyLog(final CLI cli){
            return new Runnable() {
                @Override
                public void run() {
                    int co = 0;

                    if( logEntries.size()==0 ){
                        cli.logWriter.getWriters().remove(cli.startLogWriter);
                        cli.logWriter.getWriters().add(cli.stdErrWriter);
                    }else{
                        cli.logWriter.getWriters().remove(cli.startLogWriter);
                        
                        for( Log l : getLogEntries() ){
                            Writer w = l.getWriter();
                            if( w!=null ){
                                cli.logWriter.getWriters().add(w);
                                co++;
                            }
                        }
                        
                        String bootTimeLog = cli.memLogWriter.toString();
                        if( co>0 ){
                            cli.interactiveLogInput = true;
                            if( cli.conf("log.hasLogDest.logBootTime", true,
                                "При наличии лог файлов назначения (ключ -l) копировать сообщения загрузки в log dest"
                            ) ){
                                try {
                                    if( bootTimeLog!=null && bootTimeLog.length()>0 ){
                                        cli.logWriter.append(bootTimeLog);
                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }

                    if( co==0 ){
                        if( cli.conf("log.noLogDest.log.stderr",false, 
                            "При отсуствии лог файлов назначения (ключ -l) записывать логи на stderr"
                        ) ){
                            cli.logWriter.getWriters().add(cli.stdErrWriter);
                        }
                        cli.interactiveLogInput = cli.conf(
                            "log.noLogDest.interactiveLogInput", false,
                            "При отсуствии лог файлов назначения (ключ -l) логировать интерактивный ввод");
                        cli.log_DuplicateStdERR = cli.conf(
                            "log.noLogDest.log_duplicateStdERR", true,
                            "При отсуствии лог файлов назначения (ключ -l) дублировать сообщения о ошибке на stderr");
                        cli.interactiveLogResult = cli.conf(
                            "log.noLogDest.interactiveLogResult", false,
                            "При отсуствии лог файлов назначения (ключ -l) логировать результаты вычислений в интерактивном режиме");
                    }else{
                        cli.interactiveLogInput = cli.conf("log.hasLogDest.interactiveLogInput", true,
                            "При наличии лог файлов назначения (ключ -l) логировать интерактивный ввод");
                        cli.log_DuplicateStdERR = cli.conf("log.hasLogDest.log_duplicateStdERR", false,
                            "При наличии лог файлов назначения (ключ -l) дублировать сообщения о ошибке на stderr");
                        cli.interactiveLogResult = cli.conf("log.hasLogDest.interactiveLogResult", true,
                            "При наличии лог файлов назначения (ключ -l) логировать результаты вычислений в интерактивном режиме");
                    }
                }
            };
        }
        //</editor-fold>
    }
    
    public synchronized void start(StartOptions _opts){
        if( _opts==null ){
            _opts = new StartOptions();
        }
        final StartOptions opts = _opts;
        
        // Чтение конфигов
        opts.applyConfigs(this).run();
        
        // Применение динамической подгрузки классов
        opts.applyDynamicClassLoader(this).run();

        // настройка parser options
        SimpleConfig parserOptions = getConfig().subset("lang2.parserOptions.",true,true);
        ParserOptions popts = 
            this.dyncl==null
                ? new ParserOptions(parserOptions)
                : new ParserOptions(parserOptions,this.dyncl);
        engine.setParserOptions(popts);
        
        // Применение настроек лога
        opts.applyLog(CLI.this).run();

        // Привязать прослушивание конфига
        attachConfigListen();

        // Сохранять конфиг
        if( isConfigSave() ){
            // Чтоб ы перепрочесть и запустить процесс записи
            onSaveConfigChanged();
        }

        // Инициализация консоли
        opts.applyConsoleClassName(CLI.this).run();

        EndLine eline = opts.getEndLine()!=null ? opts.getEndLine() : getEndLine();
        String endlineText = eline.get();

        // Инициализация функций
        initFuncs(endlineText
//            ,getOutputCharset()
        );

        // Скрипты авто запуска
        opts.applyUserInitScripts(CLI.this).run();

        // Выполнеине скриптов
        opts.applyStartScripts(CLI.this).run();

        // Выключение приветствия
        opts.applySkipHello(CLI.this).run();
        
        // Переход в интерактивный режим, если  указан
        opts.applyInteractive(CLI.this).run();

        // Запись лога
        if( isFlushLogOnExit() ){
            try {
                if( logWriter!=null ){
                    logWriter.flush();
                    logWriter.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Код выхода
        if( exitInteractive && exitInteractiveCode>0 ){
            System.exit(exitInteractiveCode);
        }
    }
    
    public static StartOptions parseStartOptions( final CLI cli, final String[] _args ){
        StartOptions opts = new StartOptions();
        if( _args==null || _args.length==0 )return opts;
        
        List<String> args = new ArrayList<String>();
        args.addAll( Arrays.asList(_args) );
        
        EndLine endl = cli.getEndLine();
        Charset cs = cli.getCharset();
        
        String DYNAMICCL="--DynamicCL=";
        String ADDDYNCP="--AddDynCP=";
        
        //<editor-fold defaultstate="collapsed" desc="чтение аргументов">
        while( args.size()>0 ){
            String arg = args.get(0);
            args.remove(0);
            
            //<editor-fold defaultstate="collapsed" desc="config">
            if( arg.equalsIgnoreCase("-c") ){
                String confFileName = args.size()>0 ? args.get(0) : null;
                if( confFileName!=null ){
                    args.remove(0);
                }
                File confFile = confFileName!=null ? new File(confFileName) : null;
                if( confFile!=null && confFile.exists() ){
                    opts.getConfigs().add( confFile );
                }
                continue;
            }
            if( arg.startsWith(CONFIG_ARG) && arg.length()>CONFIG_ARG.length() ){
                String confFileName = arg.substring(CONFIG_ARG.length());
                File confFile = confFileName!=null ? new File(confFileName) : null;
                if( confFile!=null && confFile.exists() ){
                    opts.getConfigs().add( confFile );
                }
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="-h | --help //Справка">
            if( arg.equalsIgnoreCase("-h") || arg.equalsIgnoreCase("--help") ){
                System.out.println(help(HELP_MAIN));
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Перевод строк">
            if( arg.startsWith(ENDL) && arg.length()>ENDL.length() ){
                String endlName = arg.substring(ENDL.length());
                if( endlName.equalsIgnoreCase("windows") ){
                    endl = EndLine.Windows;
                }else if( endlName.equalsIgnoreCase("linux") ){
                    endl = EndLine.Linux;
                }else if( endlName.equalsIgnoreCase("mac") ){
                    endl = EndLine.Mac;
                }else if( endlName.equalsIgnoreCase("other") ){
                    endl = EndLine.Other;
                }else if( endlName.equalsIgnoreCase("default") ){
                    endl = EndLine.Default;
                }
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Лог">
            String logFileName=null;
            if( arg.startsWith(LOG) && arg.length()>LOG.length() ){
                logFileName = arg.substring(LOG.length());
            }else if( arg.equals("-l") && args.size()>0 ){
                logFileName = args.get(0);
                args.remove(0);
            }
            if( logFileName!=null ){
                StartOptions.Log log = new StartOptions.Log();
                log.setAppend(true);
                log.setLogFile(new File(logFileName));
                opts.getLogEntries().add(log);
                continue;
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="--charsets //Кодировки">
            if( arg.equals("--charsets") ){
                charsetList();
                continue;
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="Кодировка">
            if( arg.startsWith(CS) && arg.length()>CS.length() ){
                String csName = arg.substring(CS.length());
                if( csName.equalsIgnoreCase("default") ){
                    cs = Charset.defaultCharset();
                }else{
                    try{
                        Charset _cs = Charset.forName(csName);
                        cs = _cs;
                    }catch(Exception e){
                        System.err.println(e);
                    }
                }
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="-e Скрипт_Выражение">
            if( arg.startsWith(EXP) && arg.length()>EXP.length() ){
                String exp = arg.substring(EXP.length());
                opts.getStartScripts().add(exp);
                continue;
            }
            
            if( arg.equals("-e") && args.size()>0 ){
                String exp = args.get(0);
                args.remove(0);
                opts.getStartScripts().add(exp);
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="-f Скрипт_файл">
            if( arg.startsWith(FILE) && arg.length()>FILE.length() ){
                String fileName = arg.substring(FILE.length());
                
                File f = new File(fileName);
                String code = cli.getScriptReader().readScriptFile(f,cs);
                if( code==null ){
                    if( cli.conf("parseArgs.exitOnFailedReadFile",true,
                        "Завершать с ошибкой при проблеме чтения исходного файла") ){
                        System.exit(1);
                        return null;
                    }
                    else{
                        continue;
                    }
                }
                
                opts.getStartScripts().add(code);
                opts.setStartFile(f);
                continue;
            }
            
            if( arg.equals("-f") && args.size()>0 ){
                String fileName = args.get(0);
                args.remove(0);
                
                File f = new File(fileName);
                String code = cli.getScriptReader().readScriptFile(f,cs);
                if( code==null ){
                    if( cli.conf("parseArgs.exitOnFailedReadFile",true,
                        "Завершать с ошибкой при проблеме чтения исходного файла") ){
                        System.exit(1);
                        return null;
                    }else{
                        continue;
                    }
                }
                
                opts.getStartScripts().add(code);
                opts.setStartFile(f);
                continue;
            }
//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="-i //Интерактивный режим">
            if( arg.equals("-i") || arg.equalsIgnoreCase("--interactive") ){
                opts.setInteractive(true);
                continue;
            }
            //</editor-fold>
            
            if( arg.startsWith(DYNAMICCL) && arg.length()>DYNAMICCL.length()){
                String v = arg.substring(DYNAMICCL.length());
                opts.setUseDynamicClassLoader(!v.equalsIgnoreCase("false"));
                continue;
            }
            
            if( arg.startsWith(ADDDYNCP) && arg.length()>ADDDYNCP.length()){
                String v = arg.substring(ADDDYNCP.length());
                opts.getDynamicCLPaths().add(v);
                continue;
            }
            
            // Невыводить приветствие
            if( arg.equals("--skipHello") ){
                opts.setSkipHello(true);
                continue;
            }
            
            // Показывать типы
            if( arg.equals("--showType") ){
                opts.setShowType(true);
                continue;
            }
            
            // запускать автоматические скрипты
            if( arg.startsWith(USER_INIT_SCRIPTS) && arg.length()>USER_INIT_SCRIPTS.length() ){
                String v = arg.substring(USER_INIT_SCRIPTS.length());
                opts.setUserInitScripts(v.equalsIgnoreCase("true"));
                continue;
            }
            
            //<editor-fold defaultstate="collapsed" desc="класс консоли">
            // класс консоли
            if( arg.startsWith(CONSOLE_CLASS_ARG) && arg.length()>CONSOLE_CLASS_ARG.length() ){
                opts.setConsoleClassName(arg.substring(CONSOLE_CLASS_ARG.length()));
                continue;
            }
            //</editor-fold>
            
            if( arg.equals("--") ){
                break;
            }
            
            // Скрипт файл
            File f = new File(arg);
            if( f.exists() && f.isFile() && f.canRead() ){
                String code = cli.getScriptReader().readScriptFile(f,cs);
                if( code==null ){
                    if( cli.conf("parseArgs.exitOnFailedReadFile",true,
                        "Завершать с ошибкой при проблеме чтения исходного файла") ){
                        System.exit(1);
                    }else{
                        continue;
                    }
                }
                opts.getStartScripts().add(code);
                opts.setStartFile(f);
                break;
            }
            
            // Возвращает аргумент назад в список аргументов скрипта
            args.add( 0,arg );
            break;
        }
        //</editor-fold>
        
        opts.setStartArgs(args);
        opts.setEndLine(endl);
        
        return opts;
    }
    
    /**
     * Конструктор для встраиваемых случаев
     * @param args Аргументы командной строки
     */
    public CLI(String ... args){
        stdErrWriter = new OutputStreamWriter(System.err);
        memLogWriter = new StringWriter();
        startLogWriter = new UnionWriter(stdErrWriter,memLogWriter);
        logWriter.getWriters().add(startLogWriter);
        
        StartOptions sopt = parseStartOptions(this, args);
        start(sopt);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Главная функция">
    /**
     * Выполняется при запуске приложения
     * @param _args  Аргументы
     */
    public static void main(String[] _args){
        if( _args==null )throw new IllegalArgumentException( "_args==null" );
        
        if( _args.length==0 ){
            System.out.println(help(HELP_MAIN));
            return;
        }
        
        new CLI(_args);
    }
    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="readStartupScripts()">
    /**
     * Читает скрипты запуска.<br/>
     * Они заданы в файле ресурсов: /lang2/res/cli.properties, поля init.scripts.file.*<br/>
     * <br/>
     * Пример файла: <br/>
     * init.scripts.file.01_etc=/etc/{build.application.programm}.l2<br/>
     * init.scripts.file.02_home={sys.user.home}/.{build.application.programm}.l2<br/>
     * <br/>
     * <br/>
     * Переменные:<br/>
     * sys.user.home - Домашний каталог пользователя<br/>
     * build.application.programm - Имя программы (обычно lang2)<br/>
     * Другие переменные см. var
     * @return Скрипты
     * @see #var
     */
    public List<String> readInitScripts(){
        ArrayList<String> list = new ArrayList<String>();
        SimpleConfig initFiles = getConfig().subset("init.scripts.file.", false, false);
        
//        String startupEntriesString = conf("startup.exec.entries", "",
//                "Скрипты запуска\n"
//            +   "В этом поле через запятую указаны ключи которые должны быть проинтерпретированы.\n"
//            +   "startup.exec.entries=startup.exec.etc, startup.exec.home"
//        );
        
        for( String fileNameTemplate : initFiles.values() ){
            String fileName = Text.template(fileNameTemplate, var);
            
            File f = new File(fileName);
            if( !f.exists() || !f.isFile() || !f.canRead() )continue;
            
            String code = getScriptReader().readScriptFile(f, getCharset());
            if( code==null )continue;
            list.add(code);
        }
        return list;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="evalScripts()">
    private boolean conf_evalScript_printResult(){
        return conf("evalScript.printResult",false,
                    "Отображать результат вычислений при выполнеии скрипта"
                );
    }
    
    /**
     * Выполняет указанные скрипты
     * @param scripts Скрипты
     * @param execFile Выполняемый файл (Возможно null)
     * @param arguments Аргументы (Возможно null)
     */
    protected void evalScripts(List<String> scripts, File execFile, List<String> arguments){
        // установка параметров
        if( execFile!=null && arguments!=null ){
            ArrayList<String> args = new ArrayList<String>();
            args.add(execFile.getPath());
            args.addAll(arguments);
            engine.getMemory().put(Function.argumentsVar,args);
        }else{
            ArrayList<String> args = new ArrayList<String>();
            args.add(null);
            args.addAll(arguments);
            engine.getMemory().put(Function.argumentsVar,args);
        }
        
        // уведомление о загрузки execFile
        if( dyncl!=null && execFile!=null ){
            dyncl.evalScriptFile(execFile);
        }

        while( scripts.size()>0 ){
            String code = scripts.get(0);
            scripts.remove(0);
            
            try{
                Value v = engine.parse(code);
                if( v==null )
                    throw new Error("Исходник не проанализирован, возможно он содержит ошибки :(");
                
                if( BasicParser.isEmpty(v) )continue;
                
                Object res = v.evaluate();
                
                if( conf_evalScript_printResult() ){
                    printResult(v, res);
                }
            }catch(Throwable e){
//                System.err.println(e);
                log( e );
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="evalScriptFile()">
    /**
     * Выполняет указанный файл
     * @param file Файл
     * @return true - успешное выполнение, false - есть ошибки
     */
    public boolean evalScriptFile(File file){
        if (file== null) {
            throw new IllegalArgumentException("file==null");
        }

        // уведомление о загрузки execFile
        if( dyncl!=null && file!=null ){
            dyncl.evalScriptFile(file);
        }

        String fileContent = getScriptReader().readScriptFile(file,null);
        if( fileContent==null ){
            logSevere("Не могу прочесть файл {0}", file);
            return false;
        }
        
        try{
            Value v = engine.parse(fileContent);
            if( v==null )
                throw new Error("Исходник не проанализирован, возможно он содержит ошибки :(");
            
            Object res = v.evaluate();
            if( conf_evalScript_printResult() ){
                printResult(v, res);
            }
            
            return true;
        }catch(Throwable ex){
//            System.err.println(ex.toString());
            log(ex);
            return false;
        }
    }
    
    /**
     * Выполняет указанный файл
     * @param file Файл
     * @return true - успешное выполнение, false - есть ошибки
     */
    public boolean evalScriptFile(URL file){
        if (file== null) {
            throw new IllegalArgumentException("file==null");
        }

        // уведомление о загрузки execFile
        if( dyncl!=null && file!=null ){
            dyncl.evalScriptFile(file);
        }
        
        String fileContent = getScriptReader().readScriptFile(file,null);
        if( fileContent==null ){
            logSevere("Не могу прочесть файл {0}", file);
            return false;
        }
        
        try{
            Value v = engine.parse(fileContent);
            if( v==null )
                throw new Error("Исходник не проанализирован, возможно он содержит ошибки :(");
            
            v.evaluate();
            return true;
        }catch(Throwable ex){
//            System.err.println(ex.toString());
            log(ex);
            return false;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="printResult()">
    /**
     * Вывод на STDIO результата интерпретации
     * @param v Древо исходного кода
     * @param res Результат интерпретации
     */
    protected void printResult(Value v, Object res) {
        if( !printCalled ){
            if( v instanceof TypeSupport && showType ){
                Type t = ((TypeSupport)v).getType();
                if( t!=null ){
                    String tname = t.getName();
                    cliFunctions.print( tname );
                    cliFunctions.print( " : " );
                }
            }

            if( res instanceof String ){
                cliFunctions.println( Const.encodeString((String)res) );
            }else{
                cliFunctions.println( res );
            }
        }else{
            if( !printCalledEndl ){
                cliFunctions.println();
            }
        }
        
        printCalled = false;
        printCalledEndl = false;
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="initFuncs()">
    /**
     * Инициализация функций
     * @param endl Script output - Перевод строк, возможно null
//     * @param cs Script output - Кодировка, возможно null
     */
    protected void initFuncs(
        String endl
//        ,Charset cs
    ){
//        if( cs==null )cs=Charset.defaultCharset();

        boolean loaddef = true;
        if( dyncl!=null ){
            try {
                Class cl = dyncl.loadClass(CLIFunctions.class.getName());
                Constructor constr = cl.getConstructor(L2Engine.class);
                Object o = constr.newInstance(engine);
                if( o instanceof CLIFunctions ){
                    cliFunctions = (CLIFunctions)o;
                    loaddef = false;
                }
            } catch (InvocationTargetException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if( loaddef ){
            cliFunctions = new CLIFunctions(engine);
        }
        
        if( dyncl!=null && cliFunctions.java!=null ){
            cliFunctions.java.setClassLoader(dyncl);
        }
        
        cliFunctions.getListeners().add(new CLIFunctions.Adapter(){
            @Override
            protected void exit(int code) {
                exitInteractive = true;
                exitInteractiveCode = code;
            }

            @Override
            public void cliFunctionsEvent(Event event) {
                super.cliFunctionsEvent(event);
                if( event instanceof CLIFunctions.PrintCalledEvent ){
                    printCalled = true;
                    printCalledEndl = ((CLIFunctions.PrintCalledEvent)event).isEndl();
                }
            }
        });
        cliFunctions.setScriptReader(getScriptReader());
        
        if( endl!=null )cliFunctions.setEndl(endl);
        
        engine.getMemory().put("cli", this);
        engine.getMemory().putAll(cliFunctions.getMemObjects());
        
        ConsoleUtil consUtil = new ConsoleUtil(console);
        
        // configure output
        Writer log = logWriter;
        Writer console = consUtil.getWriter();
        Writer std = new OutputStreamWriter(System.out);
        
        UnionWriter uw = new UnionWriter();
        
        if( conf("out.log",false,"Выводимый текст (функция print) направлять в лог" ) )uw.getWriters().add(log);
        if( conf("out.std",true,"Выводимый текст (функция print) направлять в стандартный поток" ) )uw.getWriters().add(std);
        if( conf("out.console",false,"Выводимый текст (функция print) направлять в консоль" ) )uw.getWriters().add(console);

        cliFunctions.setOutput(uw);
        
        // configure input
        cliFunctions.setInput(consUtil.getReader());
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="interactiveConfig()">
    private InteractiveConfig interactiveConfig = null;
    private InteractiveConfig interactiveConfig(){
        if( interactiveConfig!=null )return interactiveConfig;
        if( console instanceof GetInteractiveConfig ){
            interactiveConfig = ((GetInteractiveConfig)console).getInteractiveConfig();
        }
        if( interactiveConfig == null )interactiveConfig = new BaseInteractiveConfig(console);
        return interactiveConfig;
    }
    //</editor-fold>
    
    private boolean interactiveLogInput = false;
    private boolean interactiveLogResult = false;
    
    //<editor-fold defaultstate="collapsed" desc="interactive()">
    /**
     * Буфер комманд интерактивного режима
     */
    protected StringBuilder interactiveBuffer = new StringBuilder();
    
//    /**
//     * Записывать приветствие
//     * @return true записывать
//     */
//    private boolean isLogHello(){
//        return true;
//    }
    
    private Console console = new BaseConsole();
    
    private String helpInteractiveText(){
        return conf( "help.interactive",
            "Для завершения работы наберите exit() и нажмите ENTER\n" +
            "Для справки наберите help( \"help\" ) и нажмите ENTER",
            "Справка отображаемая при интерактивном режиме" );
    }
    
    /**
     * Интерактивный режим
     */
    public void interactive(){
        InteractiveConfig conf = interactiveConfig();
        
        exitInteractive = false;
        exitInteractiveCode = 0;
        
        if( (helloIntractive!=null && helloIntractive) || (
            conf("interactive.showHello",true,
                "Отображать справку - приветствие в интерактивном режиме"
            )
        ))
        {
            String hello = helpInteractiveText();
            
            conf.helpBegin();
            console.print(hello);
            console.println();
            conf.helpEnd();
            
            if( conf("interactive.logHello",false,"Лог приветствия") ){
                log( hello, getPrompt(), true );
            }
        }
        //..............
        while(!exitInteractive){
            conf.nextReadIteration();
            
            String line = null;
            String _prompt = getPrompt();
            try{
                conf.setPromptBegin();
                console.setPrompt(_prompt);
                conf.setPromptEnd();
                
                conf.readLineBegin();
                line = console.readLine();
                conf.readLineEnd();
            }catch(Throwable e){
                conf.catchInputExBegin();
                conf.catchInputExEnd();
                return;
            }
            
            if( line==null )break;
            
            if( evalCommand!=null ){
                if( !evalCommand.equals(line) ){
                    interactiveBuffer.append(line);
                    continue;
                }
            }else{
                interactiveBuffer.append(line);
            }
            
            try{
                String code = interactiveBuffer.toString();
                interactiveBuffer.setLength(0);

                if( interactiveLogInput ){
                    logInteractiveInput(_prompt,code);
                }
                
                conf.parseBegin();
                Value v = engine.parse(code);
                conf.parseEnd();
                
                if( v==null )
                    throw new Error("Исходник не проанализирован, возможно он содержит ошибки :(");

                if( BasicParser.isEmpty(v) )continue;

                conf.evalBegin();
                Object res = v.evaluate();
                conf.evalEnd();
                
                Object cout = cliFunctions.getOutput();
                UnionWriter ucout = cout instanceof UnionWriter ? (UnionWriter)cout : null;
                Writer saved = null;
                if( !interactiveLogResult ){
                    if( ucout!=null && ucout.getWriters().contains(logWriter) ){
                        saved = logWriter;
                        ucout.getWriters().remove(logWriter);
                    }
                }
                //.........
                conf.printResultBegin();
                printResult(v, res);
                conf.printResultEnd();
                //.........
                if( !interactiveLogResult ){
                    if( saved!=null && ucout!=null ){
                        ucout.getWriters().add(saved);
                    }
                }
            }catch(Throwable e){
                conf.catchEvalTimeExBegin();
                System.err.println(e);
                log( e );
                conf.catchEvalTimeExEnd();
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Справка">
    /**
     * Выводит список кодировок
     */
    public static void charsetList(){
        Map<String,Charset> csl = Charset.availableCharsets();
        for( Map.Entry<String,Charset> e : csl.entrySet() ){
            String txt = e.getKey();
            Charset cs = e.getValue();
            Set<String> alias = cs.aliases();
            if( alias.size()>0 ){
                for( String csName : alias ){
                    txt += " " + csName;
                }
            }
            System.out.println(txt);
        }
    }

    private static Map<String,String> helps = new HashMap<String, String>();
    
    /**
     * Справка по программе
     * @return Справка
     */
    public static String help(String file){
        if( helps.containsKey(file) )return helps.get(file);
        
        if( !file.contains("/") ){
            file = "/xyz/cofe/lang2/res/"+file;
        }
        String tmpl = FileUtil.readAllText(CLI.class.getResource(file),FileUtil.UTF8());
        
        Map<String,String> vars = new HashMap<String, String>();
        vars.putAll(getHelpVars());
        
        String txt = Text.template(tmpl, vars);
        helps.put(file, txt);
        return txt;
    }
    
    protected static Map<String,String> helpVars = null;
    
    private static ClassLoader CLI_CL = CLI.class.getClassLoader();
    
    /**
     * Переменные для справки.<br/><br/>
     * <b style="font-size:120%">Переменные java </b><br/>
     * (см. java.lang.System.getProperties()) - не полная выписка: <br/><br/>
     * 
     * <table cellspacing="0" cellpadding="3" border="1" >
     * <tr>
     * <td><b>java.version</b>
     * </td><td>версия Java</td>
     * </tr>
     * <tr>
     * <td><b>java.vendor</b></td>
     * <td>Производитель Java</td>
     * </tr>
     * <tr>
     * <td><b>java.home</b></td>
     * <td>Домашний каталог Java</td>
     * </tr>
     * <tr>
     * <td><b>java.class.path</b></td>
     * <td>CLASSPATH текущего экзмепляра JAVA</td>
     * </tr>
     * <tr>
     * <td><b>java.io.tmpdir</b></td>
     * <td>Временный каталог</td>
     * </tr>
     * <tr>
     * <td><b>os.name</b></td>
     * <td>Имя ОС (Windows,Linux,...)</td>
     * </tr>
     * <tr>
     * <td><b>os.arch</b></td>
     * <td>Архитектура ОС</td>
     * </tr>
     * <tr>
     * <td><b>os.version</b></td>
     * <td>Версия ОС</td>
     * </tr>
     * <tr>
     * <td><b>file.separator</b></td>
     * <td>Разделитель имен в пути (на Unix - <b>/</b> или на Windows - <b>\</b>)</td>
     * </tr>
     * <tr>
     * <td><b>path.separator</b></td>
     * <td>Разделитель путей (на Unix - <b>:</b> или на Windows - <b>;</b>)</td>
     * </tr>
     * <tr>
     * <td><b>line.separator</b></td>
     * <td>(\n или \n\r)</td>
     * </tr>
     * <tr>
     * <td><b>user.name</b></td>
     * <td>(Логин пользователя)</td>
     * </tr>
     * <tr>
     * <td><b>user.home</b></td>
     * <td>(Домашная директория)</td>
     * </tr>
     * <tr>
     * <td><b>user.dir</b></td>
     * <td>(Текущая директория)</td>
     * </tr>
     * </table>
     * <br/>
     * 
     * <b style="font-size:120%">Переменные окружения:</b> <br/>
     * Начинаютс с префикса <b>env.</b> (включая точку) <br/><br/>
     * 
     * <b style="font-size:120%">Переменные сборки:</b> <br/>
     * <table cellspacing="0" cellpadding="3" border="1" >
     * <tr>
     * <td><b>gitComment</b></td>
     * <td>....</td></tr><tr>
     * 
     * <td><b>buildDate</b></td>
     * <td>2014-07-17 12:53</td></tr><tr>
     * 
     * <td><b>implementationVersion</b></td>
     * <td>1.0-SNAPSHOT</td></tr><tr>

     * 
     * <td><b>implementationVendorID</b></td>
     * <td>xyz.cofe</td></tr><tr>
     * 
     * <td><b>implementationTitle</b></td>
     * <td>lang2</td></tr>
     * </table>
     * @return Переменные для справки
     */
    public static Map<String,String> getHelpVars(){
        if( helpVars!=null )return helpVars;
        helpVars = new HashMap<String, String>();

        Object buildDate = "2014-07-17 12:08";
        Object gitCommit = "unknow-commit no manifest";
        Object implementationVersion = "1.0-SNAPSHOT";
        Object implementationVendorID = "xyz.cofe";
        Object implementationTitle = "lang2";
        
        Properties buildProps = Resource.readProperties("build.properties");
        
        if( buildProps!=null ){
            gitCommit = buildProps.getProperty("buildNumber","unknow-commit no into manifest");
            buildDate = buildProps.getProperty("build.time","2014-07-17");
            implementationVersion = buildProps.getProperty("project.version","1.0-SNAPSHOT");
            implementationVendorID = buildProps.getProperty("project.groupId","xyz.cofe");
            implementationTitle = buildProps.getProperty("project.artifactId","lang2");
        }
        
        // Системные переменные java
        for( Object key : System.getProperties().keySet() ){
            if( key==null )continue;
            Object val = System.getProperties().get(key);
            if( val==null )continue;
            helpVars.put(key.toString(), val.toString());
        }
        
        // Перменные окружения
        for( String envVar : System.getenv().keySet() ){
            String val = System.getenv(envVar);
            if( val==null || envVar==null )continue;
            helpVars.put("env."+envVar, val);
        }
        
        helpVars.put("gitCommit", gitCommit.toString());
        helpVars.put("buildDate", buildDate.toString());
        helpVars.put("implementationVersion", implementationVersion.toString());
        helpVars.put("implementationVendorID", implementationVendorID.toString());
        helpVars.put("implementationTitle", implementationTitle.toString());
        
        return helpVars;
    }
    //</editor-fold>
}
