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

import java.util.Collection;
import java.util.Map;
import xyz.cofe.lang2.vm.Method;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.common.Wrapper;

/**
 * Фабрика для пост обработки
 * @author gocha
 */
public class WrapFactory implements Factory, Wrapper<Factory>
{
    protected Factory wrappedFactory = null;

    @Override
    public Factory unwrap() {
        return wrappedFactory;
    }

    public WrapFactory(Factory wrappedFactory){
        if (wrappedFactory== null) {
            throw new IllegalArgumentException("wrappedFactory==null");
        }
        this.wrappedFactory = wrappedFactory;
    }

    protected Value wrap(Value source){
        return source;
    }
    protected Method wrap(Method source){
        return source;
    }

    @Override public Value Xor(Value a, Value b) {
        return wrap(wrappedFactory.Xor(a, b));
    }
    @Override public Value While(Value a, Value b) {
        return wrap(wrappedFactory.While(a, b));
    }
    @Override public Value Variable(String id) {
        return wrap(wrappedFactory.Variable(id));
    }
    @Override public Value VarDefine(String id, Value v) {
        return wrap(wrappedFactory.VarDefine(id, v));
    }
    @Override public Value VarDefine(String id) {
        return wrap(wrappedFactory.VarDefine(id));
    }
    @Override
    public Value Field(String id, Value v) {
        return wrap(wrappedFactory.Field(id, v));
    }
    @Override public Value VMObject(Collection fields) {
        return wrap(wrappedFactory.VMObject(fields));
    }
    @Override public Value VMArray(Collection data) {
        return wrap(wrappedFactory.VMArray(data));
    }
    @Override public Value UnaryMinus(Value a) {
        return wrap(wrappedFactory.UnaryMinus(a));
    }
    @Override public Value Substract(Value a, Value b) {
        return wrap(wrappedFactory.Substract(a, b));
    }
    @Override public Value Return(Value v) {
        return wrap(wrappedFactory.Return(v));
    }
    @Override public Value Return() {
        return wrap(wrappedFactory.Return());
    }
    @Override public Value Or(Value a, Value b) {
        return wrap(wrappedFactory.Or(a, b));
    }
    @Override public Value Not(Value a) {
        return wrap(wrappedFactory.Not(a));
    }
    @Override public Value Multiple(Value a, Value b) {
        return wrap(wrappedFactory.Multiple(a, b));
    }
    @Override public Value Mod(Value a, Value b) {
        return wrap(wrappedFactory.Mod(a, b));
    }
    @Override public Value If(Value c, Value t, Value f) {
        return wrap(wrappedFactory.If(c, t, f));
    }
    @Override public Value If(Value c, Value t) {
        return wrap(wrappedFactory.If(c, t));
    }
    @Override public Value Function(Collection<String> args, Value body) {
        return wrap(wrappedFactory.Function(args, body));
    }
    @Override public Value FieldIndex(Value value, String field) {
        return wrap(wrappedFactory.FieldIndex(value, field));
    }
    @Override public Value Expressions(Iterable<Value> body) {
        return wrap(wrappedFactory.Expressions(body));
    }
    @Override public Value Divide(Value a, Value b) {
        return wrap(wrappedFactory.Divide(a, b));
    }
    @Override public Value Delegate(Value value) {
        return wrap(wrappedFactory.Delegate(value));
    }
    @Override public Value Continue(Value v) {
        return wrap(wrappedFactory.Continue(v));
    }
    @Override public Value Continue() {
        return wrap(wrappedFactory.Continue());
    }
    @Override public Value Const(Object value) {
        return wrap(wrappedFactory.Const(value));
    }
    @Override public Value CompareNotEquals(Value a, Value b) {
        return wrap(wrappedFactory.CompareNotEquals(a, b));
    }
    @Override public Value CompareMoreOrEquals(Value a, Value b) {
        return wrap(wrappedFactory.CompareMoreOrEquals(a, b));
    }
    @Override public Value CompareMore(Value a, Value b) {
        return wrap(wrappedFactory.CompareMore(a, b));
    }
    @Override public Value CompareLessOrEquals(Value a, Value b) {
        return wrap(wrappedFactory.CompareLessOrEquals(a, b));
    }
    @Override public Value CompareLess(Value a, Value b) {
        return wrap(wrappedFactory.CompareLess(a, b));
    }
    @Override public Value CompareEquals(Value a, Value b) {
        return wrap(wrappedFactory.CompareEquals(a, b));
    }
    @Override public Value Call(Value function, Iterable<Value> args) {
        return wrap(wrappedFactory.Call(function, args));
    }
    @Override public Value Break(Value v) { return wrap(wrappedFactory.Break(v)); }
    @Override public Value Break() { return wrap(wrappedFactory.Break()); }
    @Override public Value Block(Value body) { return wrap(wrappedFactory.Block(body)); }
    @Override public Value Assign(Value a, Value b) { return wrap(wrappedFactory.Assign(a, b)); }
    @Override public Value ArrayIndex(Value value, Value index) { 
        return wrap(wrappedFactory.ArrayIndex(value, index)); 
    }
    @Override public Value And(Value a, Value b) { return wrap(wrappedFactory.And(a, b)); }
    @Override public Value Add(Value a, Value b) { return wrap(wrappedFactory.Add(a, b)); }
    @Override public Value Throw(Value v) {return wrap(wrappedFactory.Throw(v));}

    @Override
    public Value TryCatch(Value tryBlock, String catchVarName, Value catchBlock) {
        return wrap(wrappedFactory.TryCatch(tryBlock, catchVarName, catchBlock));
    }

    @Override
    public Value For(String varName, Value src, Value body) {
        return wrap(wrappedFactory.For(varName, src, body));
    }
    
    @Override
    public Method createMethod( Map obj, Function func ){
        return wrap(wrappedFactory.createMethod(obj, func));
    }
}
