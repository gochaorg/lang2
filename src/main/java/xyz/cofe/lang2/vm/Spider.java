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
package xyz.cofe.lang2.vm;

import xyz.cofe.common.Wrapper;
import xyz.cofe.lang2.vm.op.DebugWrapperLValue;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.lang2.vm.op.ExecuteFlowValue;
import xyz.cofe.lang2.vm.op.ExecuteFlow;
import xyz.cofe.lang2.vm.op.FieldIndex;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.Operator;
import xyz.cofe.lang2.vm.op.OperatorName;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.op.While;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.Delegate;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.lang2.vm.op.Call;
import xyz.cofe.lang2.vm.op.Block;
import xyz.cofe.lang2.vm.op.ArrayIndex;
import java.util.Map;
import java.util.Stack;
import xyz.cofe.lang2.vm.op.CreateArray;
import xyz.cofe.lang2.vm.op.CreateField;
import xyz.cofe.lang2.vm.op.CreateObject;
import xyz.cofe.lang2.vm.op.For;
import xyz.cofe.lang2.vm.op.IfElse;
import xyz.cofe.lang2.vm.op.TryCatch;

/**
 * Производит обход по дереву выражений vm 
 * @author gocha
 */
public abstract class Spider 
{
    protected Stack<Object> path = null;

    protected Stack<Object> getPath(){
        if( path!=null )return path;
        path = new Stack<Object>();
        return path;
    }

    protected void beginGo(Object o){
    }

    protected void endGo(Object o){
    }

	public void go(Object o){
		if( o==null )return;

        if( getPath().size()==0 )beginGo(o);

        getPath().push(o);
		if( o instanceof DebugWrapperLValue )goDebugWrapperLValue((DebugWrapperLValue)o);
		else if( o instanceof DebugWrapperValue )goDebugWrapperValue((DebugWrapperValue)o);
        else if(o instanceof Wrapper)goWrapper((Wrapper) o);
        else if(o instanceof Operator)goOperator((Operator) o);
        else if(o instanceof TryCatch)goTryCatchFinally((TryCatch) o);
//        else if( o instanceof VMObject )goObject((VMObject)o);
//        else if( o instanceof VMArray )goArray((VMArray)o);
        else if( o instanceof CreateObject )goCreateObject((CreateObject)o);
        else if( o instanceof CreateField )goCreateField((CreateField)o);
        else if( o instanceof CreateArray )goCreateArray((CreateArray)o);
        else if( o instanceof Method )goMethod((Method)o);
        else if( o instanceof Function )goFunction((Function)o);
        else if( o instanceof Block )goBlock((Block)o);
        else if( o instanceof ExecuteFlowValue )goExecuteFlow((ExecuteFlowValue)o);
        else if( o instanceof If )goIf((If)o);
        else if( o instanceof IfElse )goIfElse((IfElse)o);
        else if( o instanceof Call )goCall((Call)o);
        else if( o instanceof Const )goConst((Const)o);
        else if( o instanceof FieldIndex )goField((FieldIndex)o);
		else if( o instanceof ArrayIndex )goArrayIndex((ArrayIndex)o);
		else if( o instanceof VariableDeffine )goVariableDefine((VariableDeffine)o);
		else if( o instanceof Variable )goVariable((Variable)o);
		else if( o instanceof Delegate )goDelegate((Delegate)o);
        else if( o instanceof While )goWhile((While)o);
        else if( o instanceof For )goFor((For)o);
        else if( o instanceof ExpressionList )goExpressionList((ExpressionList)o);
        Object _o = getPath().pop();

        if( getPath().size()==0 )endGo(_o);
	}
    
    protected void goTryCatchFinally(TryCatch tryCatchFinallyOp){
        Value tryB = tryCatchFinallyOp.getTryBlock();
        Value catchB = tryCatchFinallyOp.getCatchBlock();
        
        if( tryB!=null )goTryBlock( tryB );
        if( catchB!=null )goCatchBlock( tryCatchFinallyOp.getVariable(), catchB );
    }
    
    protected void goTryBlock(Value tryBlock){
        go( tryBlock );
    }
    
    protected void goCatchBlock(String variableName, Value catchBlock){
        go( catchBlock );
    }
    
    protected void goDebugWrapperLValue(DebugWrapperLValue wrapper){
        go( wrapper.unwrap() );
    }

    protected void goDebugWrapperValue(DebugWrapperValue wrapper){
        go( wrapper.unwrap() );
    }

    protected void goWrapper(Wrapper wrapper){
        go( wrapper.unwrap() );
    }

    protected void goExpressionList(ExpressionList el){
        goExpressionList(el, el==null ? null : el.getValues());
    }
	
    protected void goExpressionList(ExpressionList el,Value[] elBody){
        if( elBody!=null ){
            for( Value v : elBody ){
                go( v );
            }
        }
    }
    
    protected void goFor(For _for){
        go(_for.getSrc());
        go(_for.getBody());
    }
    
	protected void goWhile(While _while){
        go(_while.getCondition());
        go(_while.getBody());
	}

	protected void goCreateObject(CreateObject obj){
		for( Object _entry : obj.getFields() ){
            if( _entry instanceof CreateField ){
                go(_entry);
            }
        }
	}

    protected void goCreateField(CreateField field){
        if( field!=null )go( field.getFieldValue() );
    }

//	protected void goArray(VMArray array){
//		for(Object o: array)go(o);
//	}
    
    protected void goCreateArray(CreateArray createArray){
        for(Value child:createArray.getChildren()){
            go(child);
        }
    }

	protected void goFunction(Function fun){
		goFunction( fun, fun.getParameterNames(), fun.getBody() );
	}

	protected void goFunction(Function fun, String[] arguments, Value body){
		go( fun.getBody() );
	}

	protected void goMethod(Method meth){
		goMethod(meth, meth.getOwner(), meth.getParameterNames(), meth.getBody());
	}

	protected void goMethod(Method meth, Map owner, String[] arguments, Value body){
		go( meth.getBody() );
	}

	protected void goBlock(Block block){
		goBlock(block, block.getValues());
	}

	protected void goBlock(Block block, Value[] blockBody){
        if( blockBody!=null ){
            for(Object o:blockBody)go(o);
        }
	}

	protected void goExecuteFlow(ExecuteFlowValue execFlow){
        goExecuteFlow( execFlow, execFlow.getExecuteFlowTarget(), execFlow.isHasFlowResult(), execFlow.getValue() );
	}

	protected void goExecuteFlow(ExecuteFlowValue execFlow,ExecuteFlow.Target target,boolean hasValue, Value value){
        go(value);
	}

	protected void goField(FieldIndex value){
        goField(value, value.getBaseValue(), value.getIndexValue());
	}

	protected void goField(FieldIndex value,Value base,Value field){
		go(base);
		go(field);
	}

    protected void goIf(If _if){
        goIf(_if, _if.getCondition(), _if.getTrueExpression());
	}

    protected void goIfElse(IfElse _if){
        goIfElse(_if, _if.getCondition(), _if.getTrueExpression(), _if.getFalseExpression());
	}
	
    protected void goIf(If _if, Value condition, Value trueExp){
        go( condition );
        go( trueExp );
    }

	protected void goIfElse(IfElse _if, Value condition, Value trueExp, Value falseExp){
        go( condition );
        go( trueExp );
        go( falseExp );
    }

	protected void goCall(Call call){
        goCall(call,call.getFunction(),call.getArguments());
	}

    protected void goCall(Call call,Value function,Value ... arguments){
        go( function );
        for( Object o : arguments )go( arguments );
    }

	protected void goOperator(Operator operator){
        goOperator(operator, operator.getOperatorName(), operator.getOperands());
	}

    protected void goOperator(Operator op,OperatorName opName,Value[] operands)
    {
        for( Object o : operands )go( o );
    }
	
	protected void goConst(Const value){
        go( value.getConstValue() );
	}

	protected void goArrayIndex(ArrayIndex value){
        goArrayIndex(value,value.getBaseValue(),value.getIndexValue());
	}

	protected void goArrayIndex(ArrayIndex value,Value base,Value index){
		go(base);
		go(index);
	}

	protected void goVariableDefine(VariableDeffine value){
		goVariableDefine(value, value.getVariable(), value.getValue());
	}

	protected void goVariableDefine(VariableDeffine varDef,String id,Value value){
		go(value);
	}

	protected void goVariable(Variable value){
        goVariable( value, value.getVariable() );
	}

	protected void goVariable(Variable value, String varName){
        go(varName);
	}

	protected void goDelegate(Delegate value){
		go(value.getSourceValue());
	}
}