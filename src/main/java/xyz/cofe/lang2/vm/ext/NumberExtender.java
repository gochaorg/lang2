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
package xyz.cofe.lang2.vm.ext;

import java.math.BigDecimal;
import java.math.BigInteger;
import xyz.cofe.lang2.vm.PrimitiveType;
import xyz.cofe.lang2.vm.Type;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.Operator.TypeExtender;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.collection.BasicPair;
import xyz.cofe.collection.Pair;

/**
 * Расширение чисел.<br/>
 * Числа складываются/умнажаются/делятся/вычитаются в соот. со своим типом.<br/>
 * Если типы чисел не совпадают, то они приводятся к типу с большей раздядностю.
 * @author gocha
 */
public class NumberExtender
        extends BasicExtender
        implements TypeExtender
{
    private static BasicPair<Type,Boolean> type(Type t){
        return new BasicPair<Type, Boolean>(t, Boolean.TRUE);
    }
    private static final BasicPair<Type,Boolean> undef =
            type(PrimitiveType.Undefined);
    
    private static final int BYTE=0;
    private static final int SHORT=1;
    private static final int INT=2;
    private static final int LONG=3;
    private static final int FLOAT=4;
    private static final int DOUBLE=5;
    private static final int BIGINT=6;
    private static final int BIGDEC=7;
    
    @Override
    public Pair<Type, Boolean> extendOperatorType(Value invoker, OperatorName opName, Type... argTypes) {
        if( opName==OperatorName.UNARY_MINUS && argTypes.length==1 ){
            Type t = argTypes[0];
            if( t==PrimitiveType.Byte )return type(t);
            if( t==PrimitiveType.Short )return type(t);
            if( t==PrimitiveType.Integer )return type(t);
            if( t==PrimitiveType.Long )return type(t);
            if( t==PrimitiveType.Float )return type(t);
            if( t==PrimitiveType.Double )return type(t);
        }
        if( argTypes.length==2 ){
            Type t1 = argTypes[0];
            Type t2 = argTypes[1];
            if( opName==OperatorName.ADD
                    || opName==OperatorName.SUBSTRACT
                    || opName==OperatorName.MULTIPLE
                    || opName==OperatorName.DIVIDE
            ){
                if( t1==t2 ){
                    return type(t2);
                }
                int c1 = getNumType(t1);
                int c2 = getNumType(t2);
                if( c1<0 || c2<0 )return undef;
                if( c1==c2 )return type(t2);
                if( c1<c2 )return type(t2);
                if( c1>c2 )return type(t1);
            }
        }
        return undef;
    }

    @Override
    public Pair<Object, Boolean> extendOperator(Value invoker, OperatorName opName, Object... args) {
        if( args.length==1 && opName==OperatorName.UNARY_MINUS ){
            Object a = args[0];
            int at = getNumType(a);
            if( at>=0 ){
                Number na = (Number)a;
                return result( unaryMinus(na, at) );
            }
        }
        
        if( args.length==2 ){
            Object a = args[0];
            Object b = args[1];
            int at = getNumType(a);
            int bt = getNumType(b);
            
            Number na = null;
            Number nb = null;
            if( at>=0 && bt>=0 ){
                if( at==bt ){
                    na = (Number)a;
                    nb = (Number)b;
                    switch( opName ){
                        case ADD: return result(addSameType(na, nb, bt));
                        case SUBSTRACT: return result(subSameType(na, nb, bt));
                        case MULTIPLE: return result(mulSameType(na, nb, bt));
                        case DIVIDE: return result(divSameType(na, nb, bt));
                        case MOD: return result(modSameType(na, nb, bt));
                    }
                }else{
                    int t = at > bt ? at : bt;
                    na = castTo((Number)a, t);
                    nb = castTo((Number)b, t);
                    switch( opName ){
                        case ADD: return result(addSameType(na, nb, t));
                        case SUBSTRACT: return result(subSameType(na, nb, t));
                        case MULTIPLE: return result(mulSameType(na, nb, t));
                        case DIVIDE: return result(divSameType(na, nb, t));
                        case MOD: return result(modSameType(na, nb, t));
                    }
                }
            }
        }
        return super.extendOperator(invoker, opName, args);
    }
    
    protected Number unaryMinus(Number n,int t){
        if( t==BYTE )return -( ((Number)n).byteValue() );
        if( t==SHORT )return -( ((Number)n).shortValue() );
        if( t==INT )return -( ((Number)n).intValue() );
        if( t==LONG )return -( ((Number)n).longValue() );
        if( t==FLOAT )return -( ((Number)n).floatValue() );
        if( t==DOUBLE )return -( ((Number)n).doubleValue() );
        if( t==BIGINT ){
            BigInteger v = null;
            if( n instanceof BigInteger ){
                v = (BigInteger)n;
            }else{
                v = BigInteger.valueOf(n.longValue());
            }
            return v.negate();
        }
        if( t==BIGDEC ){
            BigDecimal v = null;
            if( n instanceof BigDecimal ){
                v = (BigDecimal)n;
            }else{
                v = BigDecimal.valueOf(n.doubleValue());
            }
            return v.negate();
        }
        return null;
    }
    
    protected Number castTo(Number n,int t){
        if( t==BYTE )return ((Number)n).byteValue();
        if( t==SHORT )return ((Number)n).shortValue();
        if( t==INT )return ((Number)n).intValue();
        if( t==LONG )return ((Number)n).longValue();
        if( t==FLOAT )return ((Number)n).floatValue();
        if( t==DOUBLE )return ((Number)n).doubleValue();
        if( t==BIGINT )return BigInteger.valueOf(n.longValue());
        if( t==BIGDEC )return BigDecimal.valueOf(n.doubleValue());
        return null;
    }
    
    protected Number addSameType(Number a,Number b,int t){
        if( t==BYTE )return a.byteValue() + b.byteValue();
        if( t==SHORT )return a.shortValue() + b.shortValue();
        if( t==INT )return a.intValue() + b.intValue();
        if( t==LONG )return a.longValue() + b.longValue();
        if( t==FLOAT )return a.floatValue() + b.floatValue();
        if( t==DOUBLE )return a.doubleValue() + b.doubleValue();
        if( t==BIGINT ){
            BigInteger va = a instanceof BigInteger ? (BigInteger)a : BigInteger.valueOf(a.longValue());
            BigInteger vb = b instanceof BigInteger ? (BigInteger)b : BigInteger.valueOf(b.longValue());
            return va.add(vb);
        }
        if( t==BIGDEC ){
            BigDecimal va = a instanceof BigDecimal ? (BigDecimal)a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal vb = b instanceof BigDecimal ? (BigDecimal)b : BigDecimal.valueOf(b.doubleValue());
            return va.add(vb);
        }
        return null;
    }

    protected Number subSameType(Number a,Number b,int t){
        if( t==BYTE )return a.byteValue() - b.byteValue();
        if( t==SHORT )return a.shortValue() - b.shortValue();
        if( t==INT )return a.intValue() - b.intValue();
        if( t==LONG )return a.longValue() - b.longValue();
        if( t==FLOAT )return a.floatValue() - b.floatValue();
        if( t==DOUBLE )return a.doubleValue() - b.doubleValue();
        if( t==BIGINT ){
            BigInteger va = a instanceof BigInteger ? (BigInteger)a : BigInteger.valueOf(a.longValue());
            BigInteger vb = b instanceof BigInteger ? (BigInteger)b : BigInteger.valueOf(b.longValue());
            return va.subtract(vb);
        }
        if( t==BIGDEC ){
            BigDecimal va = a instanceof BigDecimal ? (BigDecimal)a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal vb = b instanceof BigDecimal ? (BigDecimal)b : BigDecimal.valueOf(b.doubleValue());
            return va.subtract(vb);
        }
        return null;
    }

    protected Number mulSameType(Number a,Number b,int t){
        if( t==BYTE )return a.byteValue() * b.byteValue();
        if( t==SHORT )return a.shortValue() * b.shortValue();
        if( t==INT )return a.intValue() * b.intValue();
        if( t==LONG )return a.longValue() * b.longValue();
        if( t==FLOAT )return a.floatValue() * b.floatValue();
        if( t==DOUBLE )return a.doubleValue() * b.doubleValue();
        if( t==BIGINT ){
            BigInteger va = a instanceof BigInteger ? (BigInteger)a : BigInteger.valueOf(a.longValue());
            BigInteger vb = b instanceof BigInteger ? (BigInteger)b : BigInteger.valueOf(b.longValue());
            return va.multiply(vb);
        }
        if( t==BIGDEC ){
            BigDecimal va = a instanceof BigDecimal ? (BigDecimal)a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal vb = b instanceof BigDecimal ? (BigDecimal)b : BigDecimal.valueOf(b.doubleValue());
            return va.multiply(vb);
        }
        return null;
    }

    protected Number divSameType(Number a,Number b,int t){
        if( t==BYTE )return a.byteValue() / b.byteValue();
        if( t==SHORT )return a.shortValue() / b.shortValue();
        if( t==INT )return a.intValue() / b.intValue();
        if( t==LONG )return a.longValue() / b.longValue();
        if( t==FLOAT )return a.floatValue() / b.floatValue();
        if( t==DOUBLE )return a.doubleValue() / b.doubleValue();
        if( t==BIGINT ){
            BigInteger va = a instanceof BigInteger ? (BigInteger)a : BigInteger.valueOf(a.longValue());
            BigInteger vb = b instanceof BigInteger ? (BigInteger)b : BigInteger.valueOf(b.longValue());
            return va.divide(va);
        }
        if( t==BIGDEC ){
            BigDecimal va = a instanceof BigDecimal ? (BigDecimal)a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal vb = b instanceof BigDecimal ? (BigDecimal)b : BigDecimal.valueOf(b.doubleValue());
            return va.divide(vb);
        }
        return null;
    }

    protected Number modSameType(Number a,Number b,int t){
        if( t==BYTE )return a.byteValue() % b.byteValue();
        if( t==SHORT )return a.shortValue() % b.shortValue();
        if( t==INT )return a.intValue() % b.intValue();
        if( t==LONG )return a.longValue() % b.longValue();
        if( t==FLOAT )return a.floatValue() % b.floatValue();
        if( t==DOUBLE )return a.doubleValue() % b.doubleValue();
        if( t==BIGINT ){
            BigInteger va = a instanceof BigInteger ? (BigInteger)a : BigInteger.valueOf(a.longValue());
            BigInteger vb = b instanceof BigInteger ? (BigInteger)b : BigInteger.valueOf(b.longValue());
            BigInteger[] r = va.divideAndRemainder(vb);
            return r[1];
        }
        if( t==BIGDEC ){
            BigDecimal va = a instanceof BigDecimal ? (BigDecimal)a : BigDecimal.valueOf(a.doubleValue());
            BigDecimal vb = b instanceof BigDecimal ? (BigDecimal)b : BigDecimal.valueOf(b.doubleValue());
            BigDecimal[] r = va.divideAndRemainder(vb);
            return r[1];
        }
        return null;
    }
    
    protected int getNumType(Object a){
        if( a instanceof Byte )return BYTE;
        if( a instanceof Short )return SHORT;
        if( a instanceof Integer )return INT;
        if( a instanceof Long )return LONG;
        if( a instanceof Float )return FLOAT;
        if( a instanceof Double )return DOUBLE;
        if( a instanceof BigInteger )return BIGINT;
        if( a instanceof BigDecimal )return BIGDEC;
        return -1;
    }

    protected int getNumType(Type t){
        if( t==PrimitiveType.Byte )return BYTE;
        if( t==PrimitiveType.Short )return SHORT;
        if( t==PrimitiveType.Integer )return INT;
        if( t==PrimitiveType.Long )return LONG;
        if( t==PrimitiveType.Float )return FLOAT;
        if( t==PrimitiveType.Double )return DOUBLE;
        return -1;
    }
}
