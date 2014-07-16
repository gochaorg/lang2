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

import xyz.cofe.lang2.vm.Value;

import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.lang2.vm.op.ExecuteFlowValue;
import xyz.cofe.lang2.vm.op.FieldIndex;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.Operator;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.lang2.vm.op.While;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.Delegate;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.lang2.vm.op.Call;
import xyz.cofe.lang2.vm.op.Block;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.op.TryCatch;
import xyz.cofe.lang2.vm.op.IfElse;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.MemorySupport;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.op.CreateArray;
import xyz.cofe.lang2.vm.op.CreateField;
import xyz.cofe.lang2.vm.op.CreateObject;
import xyz.cofe.lang2.vm.op.For;

/**
 * Фабрика классов
 * @author gocha
 */
public class L2Factory 
implements Factory, MemorySupport
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(L2Factory.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(L2Factory.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(L2Factory.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(L2Factory.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(L2Factory.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(L2Factory.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    public L2Factory(Map<String, Object> memory)
    {
        initMemory(memory);
    }
    
    // <editor-fold defaultstate="collapsed" desc="initMemory">
    private Map<String, Object> memory = null;

    public void initMemory(Map<String, Object> memory)
    {
        this.memory = memory;
    }
    
    public Map<String,Object> getMemory(){
        return memory;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="expressions">
    @Override
    public Value Expressions(Iterable<Value> body) {
        Value res = new ExpressionList(body);
        return res;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="flowExpression">
    @Override
    public Value TryCatch(Value tryBlock,String catchVarName,Value catchBlock)
    {
        TryCatch tcf = new TryCatch();
        tcf.setTryBlock(tryBlock);
        tcf.setCatchBlock(catchBlock);
        tcf.setVariable(catchVarName);
        tcf.setMemory(memory);
        return tcf;
    }

    @Override
    public Value Throw(Value v)
    {
        return ExecuteFlowValue.createThrow(v);
    }
    
    @Override
    public Value Return()
    {
        return ExecuteFlowValue.createReturn();
    }

    @Override
    public Value Return(Value v)
    {
        return ExecuteFlowValue.createReturn(v);
    }

    @Override
    public Value Break()
    {
        return ExecuteFlowValue.createBreak();
    }

    @Override
    public Value Break(Value v)
    {
        return ExecuteFlowValue.createBreak(v);
    }

    @Override
    public Value Continue()
    {
        return ExecuteFlowValue.createContinue();
    }

    @Override
    public Value Continue(Value v)
    {
        return ExecuteFlowValue.createContinue(v);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="while">
    @Override
    public Value While(Value a, Value b)
    {
        return new While(a, b);
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="for">
    @Override
    public Value For(String varName, Value src, Value body)
    {
        return new For(this, memory, varName, src, body);
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="varDefine">
    @Override
    public Value VarDefine(String id)
    {
        return new VariableDeffine(memory, id);
    }

    @Override
    public Value VarDefine(String id, Value v)
    {
        return new VariableDeffine(memory, id, v);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="block">
    @Override
    public Value Block(Value body)
    {
        return new Block(memory, body);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="assign">
    @Override
    public Value Assign(Value a, Value b)
    {
        return new Operator(OperatorName.ASSIGN, a, b);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="if">
    @Override
    public Value If(Value c, Value t)
    {
        return new If(c, t);
    }

    @Override
    public Value If(Value c, Value t, Value f)
    {
        return new IfElse(c, t, f);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="logic">
    @Override
    public Value Or(Value a, Value b)
    {
        return new Operator(OperatorName.OR, a, b);
    }

    @Override
    public Value And(Value a, Value b)
    {
        return new Operator(OperatorName.AND, a, b);
    }

    @Override
    public Value Xor(Value a, Value b)
    {
        return new Operator(OperatorName.XOR, a, b);
    }

    @Override
    public Value Not(Value a)
    {
        return new Operator(OperatorName.NOT, a);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="compare">
    @Override
    public Value CompareEquals(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_EQUALS, a, b);
    }

    @Override
    public Value CompareNotEquals(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_NOT_EQUALS, a, b);
    }

    @Override
    public Value CompareMoreOrEquals(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_MORE_OR_EQUALS, a, b);
    }

    @Override
    public Value CompareLessOrEquals(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_LESS_OR_EQUALS, a, b);
    }

    @Override
    public Value CompareMore(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_MORE, a, b);
    }

    @Override
    public Value CompareLess(Value a, Value b)
    {
        return new Operator(OperatorName.COMAPRE_LESS, a, b);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="mathematics">
    @Override
    public Value Add(Value a, Value b)
    {
        return new Operator(OperatorName.ADD, a, b);
    }

    @Override
    public Value Substract(Value a, Value b)
    {
        return new Operator(OperatorName.SUBSTRACT, a, b);
    }

    @Override
    public Value Multiple(Value a, Value b)
    {
        return new Operator(OperatorName.MULTIPLE, a, b);
    }

    @Override
    public Value Mod(Value a, Value b)
    {
        return new Operator(OperatorName.MOD, a, b);
    }
    
    @Override
    public Value Divide(Value a, Value b)
    {
        return new Operator(OperatorName.DIVIDE, a, b);
    }

    @Override
    public Value UnaryMinus(Value a)
    {
        return new Operator(OperatorName.UNARY_MINUS, a);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="postfixExpression">
    @Override
    public Value FieldIndex(Value value, String field)
    {
        return new FieldIndex(this, value, new Const(field));
    }

    @Override
    public Value ArrayIndex(Value value, Value index)
    {
        return new FieldIndex(this, value, index);
    }

    @Override
    public Value Call(Value function, Iterable<Value> args)
    {
        return new Call(this, function, args);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="valueExpression">
    @Override
    public Value Const(Object value)
    {
        return new Const(value);
    }

    @Override
    public Value Delegate(Value value)
    {
        return new Delegate(value);
    }

    @Override
    public Value Variable(String id)
    {
        return new Variable(memory, id);
    }

    @Override
    public Value Function(Collection<String> args, Value body)
    {
        Function fun = new Function();
        fun.setMemory(memory);
        fun.setParameterNames(args.toArray(new String[]{}));
        fun.setBody(body);
        return fun;
    }
    
    @Override
    public Value Field(String id,Value v){
        return new CreateField(id, v);
    }
    
    @Override
    public Value VMObject(Collection data){
        return new CreateObject(this,data);
    }

//    @Override
//    public Value VMObject(Map fields)
//    {
//        VMObject obj = new VMObject();
//        if (fields != null) {
//            for (Object _e : fields.entrySet()) {
//                if (_e == null) {
//                    continue;
//
//                }
//                if (_e instanceof Map.Entry) {
//                    Map.Entry e = (Map.Entry) _e;
//                    Object key = e.getKey();
//                    Object val = e.getValue();
//                    if (key == null) {
//                        continue;
//                    }
//                    if( key instanceof Value ){
//                        ((Value)key).setParent(obj);
//                    }
//                    if( val instanceof Value ){
//                        ((Value)val).setParent(obj);
//                    }
//                    obj.put(key, val);
//                }
//            }
//        }
//        return obj;
//    }

    @Override
    public Value VMArray(Collection data)
    {
//        VMArray arr = new VMArray();
//        if (data != null) {
//            arr.addAll(data);
//        }
//        return arr;
        CreateArray arr = new CreateArray(data);
        return arr;
    }// </editor-fold>
    
    @Override
    public Method createMethod( Map obj, Function func ){
        return new Method(obj, func);
    }
}
