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

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.Spider;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.ArrayIndex;
import xyz.cofe.lang2.vm.op.Block;
import xyz.cofe.lang2.vm.op.Call;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.lang2.vm.op.CreateArray;
import xyz.cofe.lang2.vm.op.CreateField;
import xyz.cofe.lang2.vm.op.CreateObject;
import xyz.cofe.lang2.vm.op.Delegate;
import xyz.cofe.lang2.vm.op.ExecuteFlow.Target;
import xyz.cofe.lang2.vm.op.ExecuteFlowValue;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.FieldIndex;
import xyz.cofe.lang2.vm.op.For;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.IfElse;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.lang2.vm.op.Operator;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.lang2.vm.op.While;
import xyz.cofe.text.IndentPrintWriter;

// TODO Проверить на генерацию всех констркуций языка

/**
 * Генерирует исходный код
 * @author gocha
 */
public class SourceGenerator extends Spider
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(SourceGenerator.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    protected IndentPrintWriter writer;

    /**
     * Генерирует исходный код
     * @param tree Объект виртуальной машины
     * @return Исходный код
     */
    public static String generateSource(Object tree)
    {
        if (tree == null) {
            throw new IllegalArgumentException("tree == null");
        }
        StringWriter sw = new StringWriter();
        SourceGenerator sg = new SourceGenerator(sw);
        sg.go(tree);
        String src = sw.toString();
        return src;
    }

    public SourceGenerator(Writer output)
    {
        if (output == null) {
            throw new IllegalArgumentException("output == null");
        }
        this.writer = new IndentPrintWriter(output);
    }

    @Override
    protected void goExpressionList(ExpressionList el, Value[] elBody)
    {
//        int idx = -1;
//        for( Value v : elBody ){
//            idx++;
//            if( idx>0 )writer.print(";\n");
//            go( v );
//        }
        printList(";\n", elBody);
    }

    @Override
    protected void endGo(Object o){
        writer.flush();
    }

    @Override
    protected void goVariable(Variable value, String varName)
    {
        writer.print(varName);
    }

    @Override
    protected void goBlock(Block block, Value[] blockBody)
    {
        writer.println("{");
        writer.incLevel();
        printList(";\n", blockBody);
        writer.println();
        writer.decLevel();
        writer.print("}");
    }

    @Override
    protected void goVariableDefine(VariableDeffine varDef, String id, Value value)
    {
        writer.print("var ");
        writer.print(id);
        if( value!=null ){
            writer.print(" = ");
            go( value );
        }
    }

    @Override
    protected void goFunction(Function fun, String[] arguments, Value body)
    {
        writer.print("function ( ");
        printList(", ",arguments);
        writer.print(" ) ");
        go(body);
    }

    @Override
    protected void goCall(Call call, Value function, Value... arguments)
    {
        go( function );
        writer.print("( ");
        printList(", ", arguments);
        writer.print(" )");
    }

    @Override
    protected void goOperator(Operator op, OperatorName opName, Value[] operands)
    {
        String splitter = "<op>";
        if( opName!=null ){
            switch(opName){
                case ADD: splitter="+"; break;
                case SUBSTRACT: splitter="-"; break;
                case MULTIPLE: splitter="*"; break;
                case DIVIDE: splitter="/"; break;
                case MOD: splitter="%"; break;
                case COMAPRE_EQUALS: splitter="=="; break;
                case COMAPRE_LESS: splitter="<"; break;
                case COMAPRE_LESS_OR_EQUALS: splitter="<="; break;
                case COMAPRE_MORE: splitter=">"; break;
                case COMAPRE_MORE_OR_EQUALS: splitter=">="; break;
                case COMAPRE_NOT_EQUALS: splitter="!="; break;
                case ASSIGN: splitter="="; break;
                case NOT: splitter="!"; break;
                case OR: splitter="|"; break;
                case XOR: splitter="^"; break;
                case AND: splitter="&"; break;
                case UNARY_MINUS: splitter="-"; break;
            }
        }
        if( operands!=null ){
            if( operands.length>1 ){
                printList(splitter, operands);
            }else if( operands.length==1 ){
                writer.print(splitter);
                go(operands[0]);
            }else{
                writer.print(splitter);
            }
        }
    }

    protected void printList( String splitter, Object[] list ){
        int i = -1;
        for( Object o : list ){
            i++;
            if( i>0 && splitter!=null )writer.print(splitter);
            if( o instanceof Value ){
                go( o );
            }else{
                if( o!=null ){
                    writer.print( o );
                }else{
                    writer.print("null");
                }
            }
        }
    }

    @Override
    protected void goConst(Const value)
    {
        Object constV = value.getConstValue();
        if( constV instanceof Boolean )writer.print( ((Boolean)constV) ? "true" : "false");
        else if( constV instanceof Number )writer.print(constV);
        else if( constV instanceof String )writer.print(encodeStringConst((String)constV));
        else go(constV);
    }
	
	protected String encodeStringConst(String text){
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		for(int i=0;i<text.length();i++){
			char c = text.charAt(i);
			switch(c){
				case '"':
				case '\'':
				case '\n':
				case '\b':
				case '\t':
				case '\f':
					sb.append("\\");
					sb.append(c);
					break;
				default:
					sb.append(c);
					break;
			}
		}
		sb.append("\"");
		return sb.toString();
	}

//    @Override
//    protected void goArray(VMArray array)
//    {
//        boolean ei = array.isEvaluateItem();
//        array.setEvaluateItem(false);
//        writer.print("[ ");
//        printList(", ", array.toArray());
//        writer.print(" ]");
//        array.setEvaluateItem(ei);
//    }

    @Override
    protected void goCreateArray(CreateArray createArray) {
        writer.print("[ ");
        printList(", ", createArray.getChildren());
        writer.print(" ]");
    }

    @Override
    protected void goCreateObject(CreateObject obj)
    {
//        boolean ei = obj.isEvaluateItem();
//        obj.setEvaluateItem(false);
        writer.print("{ ");
        int idx = -1;
        for( Object e : obj.getFields() ){
            if( e instanceof CreateField ){
                idx++;
                if( idx>0 )writer.print(", ");
                CreateField cf = (CreateField)e;
                goCreateField(cf);
            }
        }
        writer.print(" }");
//        obj.setEvaluateItem(ei);
    }

    @Override
    protected void goCreateField(CreateField field)
    {
        writer.print(field.getFieldName());
        writer.print(" : ");
        go(field.getFieldValue());
    }

    @Override
    protected void goIf(If _if, Value condition, Value trueExp)
    {
		writer.print( "if( " );
        go( condition );
//        writer.print(" ? ");
		writer.print( ")" );
        go( trueExp );
//        if( falseExp!=null ){
//            writer.print(" : ");
//            go(falseExp);
//        }
    }

    @Override
    protected void goIfElse(IfElse _if, Value condition, Value trueExp, Value falseExp)
    {
		writer.print( "if( " );
        go( condition );
//        writer.print(" ? ");
		writer.print( " ) " );
        go( trueExp );
		writer.println();
        if( falseExp!=null ){
			writer.println(" else ");
//            writer.print(" : ");
            go(falseExp);
        }
    }

    @Override
    protected void goField(FieldIndex value, Value base, Value field)
    {
        go( base );
        writer.print(".");
        Object o = field.evaluate();
        writer.print(o);
    }

    @Override
    protected void goArrayIndex(ArrayIndex value, Value base, Value index)
    {
        go( base );
        writer.print("[");
        go( index );
        writer.print("]");
    }

    @Override
    protected void goDelegate(Delegate value)
    {
        writer.write("( ");
        go(value.getSourceValue());
        writer.write(" )");
    }

    @Override
    protected void goExecuteFlow(ExecuteFlowValue execFlow, Target target, boolean hasValue, Value value)
    {
        switch(target){
            case Break: writer.print("break"); break;
            case Continue: writer.print("continue"); break;
            case Return: writer.print("return"); break;
        }

        if( value!=null ){
            writer.print(" ");
            go( value );
        }
    }

    @Override
    protected void goMethod(Method meth, Map owner, String[] arguments, Value body)
    {
        goFunction(meth, arguments, body);
    }

    @Override
    protected void goWhile(While _while)
    {
        writer.print("while ( ");
        go(_while.getCondition());
        writer.print(" ) ");
        go(_while.getBody());
    }

    @Override
    protected void goFor(For _for) {
        writer.print("for ( ");
        writer.print(_for.getVariable());
        writer.print(" in ");
        go( _for.getSrc() );
        writer.print(" ) ");
        go( _for.getBody() );
    }

	@Override
	protected void goExecuteFlow(ExecuteFlowValue execFlow) {
		ExecuteFlowValue.Target t = execFlow.getExecuteFlowTarget();
		switch( t ){
			case Throw:
				writer.print("throw ");
				break;
			case Continue:
				writer.print("continue ");
				break;
			case Break:
				writer.print("break ");
				break;
			case Return:
				writer.print("return ");
				break;
		}
		super.goExecuteFlow(execFlow);
	}

	@Override
	protected void goCatchBlock(String variableName, Value catchBlock) {
		writer.print("catch (");
		writer.print(variableName);
		writer.print(")");
		super.goCatchBlock(variableName, catchBlock);
	}

	@Override
	protected void goTryBlock(Value tryBlock) {
		writer.print("try ");
		super.goTryBlock(tryBlock);
	}
}
