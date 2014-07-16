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

/**
 * Фабрика классов виртуальной машины
 * @author gocha
 */
public interface Factory {
    Value Throw(Value v);
    
    Value TryCatch(Value tryBlock,String catchVarName,Value catchBlock);
    
    Value Expressions(Iterable<Value> body);

    Value Add(Value a, Value b);

    Value And(Value a, Value b);

    Value ArrayIndex(Value value, Value index);

    Value Assign(Value a, Value b);

    Value Block(Value body);

    Value Break();

    Value Break(Value v);

    Value Call(Value function, Iterable<Value> args);

    Value CompareEquals(Value a, Value b);

    Value CompareLess(Value a, Value b);

    Value CompareLessOrEquals(Value a, Value b);

    Value CompareMore(Value a, Value b);

    Value CompareMoreOrEquals(Value a, Value b);

    Value CompareNotEquals(Value a, Value b);

    Value Const(Object value);

    Value Continue();

    Value Continue(Value v);

    Value Delegate(Value value);

    Value Divide(Value a, Value b);

    Value FieldIndex(Value value, String field);

    Value Function(Collection<String> args, Value body);

    Value If(Value c, Value t);

    Value If(Value c, Value t, Value f);

    Value Multiple(Value a, Value b);
    
    Value Mod(Value a, Value b);

    Value Not(Value a);

    Value Or(Value a, Value b);

    Value Return();

    Value Return(Value v);

    Value Substract(Value a, Value b);

    Value UnaryMinus(Value a);

    Value VMArray(Collection data);
    
    Value Field(String id,Value v);

    Value VMObject(Collection fields);

    Value VarDefine(String id);

    Value VarDefine(String id, Value v);

    Value Variable(String id);

    Value While(Value a, Value b);
    
    Value For(String varName, Value src, Value body);

    Value Xor(Value a, Value b);
    
    Method createMethod( Map obj, Function func );
}
