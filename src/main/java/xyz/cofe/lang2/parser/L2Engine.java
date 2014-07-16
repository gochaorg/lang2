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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.parser.CommentReciver.Comment;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.ValuePath;
import xyz.cofe.lang2.vm.VrFunction;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.Variable;
import org.antlr.runtime.RecognitionException;

/**
 * Стандартный парсер
 * @author gocha
 */
public class L2Engine
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(L2Engine.class.getName()).log(Level.FINE, message, args);
    }

    private static void logFiner(String message,Object ... args){
        Logger.getLogger(L2Engine.class.getName()).log(Level.FINER, message, args);
    }

    private static void logInfo(String message,Object ... args){
        Logger.getLogger(L2Engine.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(L2Engine.class.getName()).log(Level.WARNING, message, args);
    }

    private static void logSevere(String message,Object ... args){
        Logger.getLogger(L2Engine.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(L2Engine.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /**
     * Опции парсера/интерпретатора
     */
    protected ParserOptions parserOptions = new ParserOptions();

    /**
     * Указывает опции парсера/интерпретатора
     * @return опции парсера/интерпретатора
     */
    public ParserOptions getParserOptions() {
        if( parserOptions==null )parserOptions = new ParserOptions();
        return parserOptions;
    }

    /**
     * Указывает опции парсера/интерпретатора
     * @param parserOptions опции парсера/интерпретатора
     */
    public void setParserOptions(ParserOptions parserOptions) {
        this.parserOptions = parserOptions;
    }

    /**
     * Память
     */
    protected Map<String, Object> memory = new HashMap<String, Object>();

    /**
     * Память интерпретатора
     * @return Память
     */
    public Map<String, Object> getMemory(){
        if( memory==null ){
            memory = new HashMap<String, Object>();
        }
        return memory;
    }

    /**
     * Устанавливает память
     * @param mem память
     */
    public void setMemory(Map<String,Object> mem){
        this.memory = mem;
    }

    protected TreeSet<CommentReciver.Comment> comments = new TreeSet<CommentReciver.Comment>();

    /**
     * Парсинг выражения
     * @param sourceCode Исходный код
     * @return Дерево объектов или null если код не разобран
     */
    public synchronized Value parse(String sourceCode){
        if (sourceCode== null) {
            throw new IllegalArgumentException("sourceCode==null");
        }

        comments.clear();
        CommentReciver.Listener listener = new CommentReciver.Listener() {
            @Override
            public void reciveComment(Comment event) {
//                System.out.println("comment "+event.getText());
                comments.add(event);
            }
        };
        CommentReciver.getListeners().add(listener);

		Value v = null;
		DebugParser parser = new DebugParser(sourceCode,getParserOptions());
        parser.memory(getMemory());
        if( parserOptions!=null )
            parser.getJavaTypeExtender().addExtendFields(parserOptions);

		try {
			// Парсинг
			v = parser.parse();

            CommentReciver.getListeners().remove(listener);

            if( v!=null ){
                // Поиск и замена внешних переменных
                v = extVars(v);

                SortedSet<Comment> docComments = CommentReciver.filterDocComments(comments, true);
                setComments(v, docComments);
            }

			return v;
		} catch (RecognitionException e) {
            CommentReciver.getListeners().remove(listener);
			throw new Error(e.getMessage(), e);
		}
    }

    /**
     * Поиск внешних перменных, и замена соот. функций
     * @param v Код
     * @return  Измененный код
     */
    protected Value extVars(Value v){
        logFine("extVars() begin");

        List<Variable> vars = VrFunction.findVariables(v);
        logFiner( "findVariables ({0}):", vars.size() );
        for( int i=0;i<vars.size();i++ ){
            logFiner( "{0}. var={1} path={2}",
                    i,
                    vars.get(i).getVariable(),
                    new ValuePath(vars.get(i)).toString()
                    );
        }

        Map<Variable,Value> varDefLocations = VrFunction.findVarDefineLocation(vars, false);
        logFiner( "findVarDefineLocation ({0}):", varDefLocations.size() );
        int idx = -1;
        for( Variable var : varDefLocations.keySet() ){
            idx++;
            Value loc = varDefLocations.get(var);
            logFiner( "{0}. var={1} path={2} defLocation={3}",
                    idx,
                    var.getVariable(),
                    new ValuePath(var).toString(),
                    new ValuePath(loc).toString()
                    );
        }

        List<VrFunction.ExternalVar> extVars = VrFunction.filterExternalVariables(varDefLocations);
        logFiner( "filterExternalVariables ({0}):", extVars.size() );
        idx = -1;
        for( VrFunction.ExternalVar evar : extVars ){
            int idx2 = -1;
            StringBuilder sb = new StringBuilder();
            for(Function f : evar.extFunctions){
                idx2++;
                sb.append("\n");
                sb.append(idx2);
                sb.append(".");
                sb.append(f);
            }

            idx++;
            logFiner( "{0}. var={1} path={2} defLocation={3} extFuns={4}",
                    idx,
                    evar.var,
                    new ValuePath(evar.var).toString(),
                    new ValuePath(evar.defineLocation).toString(),
                    sb.toString()
                    );
        }

        logFiner("replaceFunctions()");
        VrFunction.replaceFunctions( extVars );

        logFine("extVars() end");
        return v;
    }

    /**
     * Установка комментариев
     * @param v Дерево исход. кода
     * @param docComments Комментарии
     */
    protected void setComments(Value v,SortedSet<Comment> docComments){
        Function.setComments(v, docComments);
    }

    /**
     * Парсинг и выполнеие выражения
     * @param sourceCode Исходный код
     * @return результат
     */
    public Object eval(String sourceCode){
        if (sourceCode== null) {
            throw new IllegalArgumentException("sourceCode==null");
        }

        Value v = parse(sourceCode);
        if( v==null )
            throw new Error("source not parsed");

        return v.evaluate();
    }
}
