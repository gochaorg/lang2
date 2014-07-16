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

import xyz.cofe.lang2.vm.op.DebugWrapperLValue;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import java.util.ArrayList;
import java.util.Map;
import xyz.cofe.lang2.vm.op.AbstractTreeNode;
import xyz.cofe.lang2.vm.op.ArrayIndex;
import xyz.cofe.lang2.vm.op.Block;
import xyz.cofe.lang2.vm.op.Call;
import xyz.cofe.lang2.vm.op.Const;
import xyz.cofe.lang2.vm.op.CreateArray;
import xyz.cofe.lang2.vm.op.Delegate;
import xyz.cofe.lang2.vm.op.ExecuteFlowValue;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.FieldIndex;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.If;
import xyz.cofe.lang2.vm.op.IfElse;
import xyz.cofe.lang2.vm.op.Operator;
import xyz.cofe.lang2.vm.op.TryCatch;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.lang2.vm.op.While;
import xyz.cofe.collection.Iterators;
import xyz.cofe.collection.NodesExtracter;
import xyz.cofe.collection.iterators.TreeWalk;
import xyz.cofe.collection.iterators.TreeWalkItreator;
import xyz.cofe.types.ClassNodesExtracterMap;

/**
 * Обход дерева операций из пакета lang2.vm.op.
 * @author gocha
 */
public class OpTreeWalker
{
	protected static final Iterable<Value> emptry = Iterators.<Value>empty();
	protected static Iterable<Value> array(Value ... values){ return Iterators.<Value>array(values); }
	protected static Iterable<Value> single(Value v){ return Iterators.<Value>single(v); }
	protected static Iterable<Value> sequence(Iterable<Value> ... a){ 
		return Iterators.<Value>sequence(a);
	}
	
	protected static NodesExtracter<Value,Value> expressionsOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof ExpressionList ){
				ExpressionList el = (ExpressionList)from;
				return Iterators.<Value>array(el.getValues());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> varDefOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof VariableDeffine ){
				VariableDeffine vd = (VariableDeffine)from;
				Value v = vd.getValue();
				return single(v);
			}
			return emptry;
		}
	};

	protected static NodesExtracter<Value,Value> arrayIndexOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof ArrayIndex ){
				ArrayIndex ai = (ArrayIndex)from;
				return array(ai.getBaseValue(),ai.getIndexValue());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> blockOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Block ){
				Block v = (Block)from;
				return array(v.getValues());
			}
			return emptry;
		}
	};

	protected static NodesExtracter<Value,Value> callOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Call ){
				Call v = (Call)from;
				return sequence(
						single(v.getFunction()), 
						array(v.getArguments())
						);
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> delegateOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Delegate ){
				Delegate v = (Delegate)from;
				return single(v.getSourceValue());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> executeFlowValueOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof ExecuteFlowValue ){
				ExecuteFlowValue v = (ExecuteFlowValue)from;
				return single(v.getValue());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> fieldIndexOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof FieldIndex ){
				FieldIndex v = (FieldIndex)from;
				return array(v.getBaseValue(),v.getIndexValue());
			}
			return emptry;
		}
	};

	protected static NodesExtracter<Value,Value> functionOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Function ){
				Function v = (Function)from;
				return single(v.getBody());
			}
			return emptry;
		}
	};

	protected static NodesExtracter<Value,Value> ifOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof If ){
				If v = (If)from;
				return array(v.getCondition(), v.getTrueExpression());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> ifElseOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof IfElse ){
				IfElse v = (IfElse)from;
				return array(v.getCondition(), v.getTrueExpression(), v.getFalseExpression());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> operatorOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Operator ){
				Operator v = (Operator)from;
				return array(v.getOperands());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> tryCatchFinallyOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof TryCatch ){
				TryCatch v = (TryCatch)from;
				return array(v.getTryBlock(), v.getCatchBlock());
			}
			return emptry;
		}
	};
	
//	protected static NodesExtracter<Value,Value> vmArrayOp = new NodesExtracter<Value, Value>() {
//		@Override
//		public Iterable<Value> extract(Value from) {
//			if( from instanceof VMArray ){
//				VMArray v = (VMArray)from;
//				boolean ei = v.isEvaluateItem();
//				v.setEvaluateItem(false);
//				ArrayList<Value> items = new ArrayList<Value>();
//				for( Object o : v ){
//					if( o instanceof Value ){
//						items.add((Value)o);
//					}else{
//						items.add(null);
//					}
//				}
//				v.setEvaluateItem(ei);
//				return items;
//			}
//			return emptry;
//		}
//	};

	protected static NodesExtracter<Value,Value> createArrayOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof CreateArray ){
				CreateArray v = (CreateArray)from;
//				boolean ei = v.isEvaluateItem();
//				v.setEvaluateItem(false);
				ArrayList<Value> items = new ArrayList<Value>();
				for( Object o : v.getChildren() ){
					if( o instanceof Value ){
						items.add((Value)o);
					}else{
						items.add(null);
					}
				}
//				v.setEvaluateItem(ei);
				return items;
			}
			return emptry;
		}
	};
    
	public static class VMObjectEntry implements Value
	{
		protected Object key = null;
		protected Object value = null;
		
		public VMObjectEntry(Object key,Object val){
			this.key = key;
			this.value = val;
		}
		
		@Override
		public Object evaluate() {
			return this;
		}

		public Object getKey() {
			return key;
		}

		public Object getValue() {
			return value;
		}

        @Override
        public Value deepClone() {
            VMObjectEntry e = new VMObjectEntry(key, value);
            return e;
        }

        @Override
        public Value getParent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setParent(Value tn) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Value[] getChildren() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setChild(int index, Value tn) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

		/* (non-javadoc) @see lang2.vm.TreeNode */
		@Override
		public int getIndex() {
			return AbstractTreeNode.getIndex(this);
		}
		
		/* (non-javadoc) @see lang2.vm.TreeNode */
		@Override
		public Value getSibling(int offset) {
			return (Value)AbstractTreeNode.getSibling(this,offset);
		}
	}
	
	protected static NodesExtracter<Value,Value> vmObjectEntryOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof VMObjectEntry ){
				VMObjectEntry v = (VMObjectEntry)from;
				Value key = v.getKey() instanceof Value ? (Value)v.getKey() : null;
				Value val = v.getValue() instanceof Value ? (Value)v.getValue() : null;
				return array(key,val);
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> varRefOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> whileOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof While ){
				While v = (While)from;
				return array(v.getCondition(),v.getBody());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> constOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> debugWrapperOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof DebugWrapperValue ){
				DebugWrapperValue v = (DebugWrapperValue)from;
				return array(v.unwrap());
			}
			return emptry;
		}
	};
	
	protected static NodesExtracter<Value,Value> methodOp = new NodesExtracter<Value, Value>() {
		@Override
		public Iterable<Value> extract(Value from) {
			if( from instanceof Method ){
				Method v = (Method)from;
				return array(v.getBody());
			}
			return emptry;
		}
	};
	
	protected static ClassNodesExtracterMap treeMap = null;
	public static ClassNodesExtracterMap treeMap(){
		if( treeMap!=null )return treeMap;
		treeMap = new ClassNodesExtracterMap();
		treeMap.getExtractersMap().put(ArrayIndex.class, arrayIndexOp);
		treeMap.getExtractersMap().put(Block.class, blockOp);
		treeMap.getExtractersMap().put(Call.class, callOp);
		treeMap.getExtractersMap().put(Const.class, constOp);
		treeMap.getExtractersMap().put(Delegate.class, delegateOp);
		treeMap.getExtractersMap().put(ExecuteFlowValue.class, executeFlowValueOp);
		treeMap.getExtractersMap().put(ExpressionList.class, expressionsOp);
		treeMap.getExtractersMap().put(FieldIndex.class, fieldIndexOp);
		treeMap.getExtractersMap().put(Function.class, functionOp);
		treeMap.getExtractersMap().put(If.class, ifOp);
		treeMap.getExtractersMap().put(IfElse.class, ifElseOp);
		treeMap.getExtractersMap().put(Operator.class, operatorOp);
		treeMap.getExtractersMap().put(TryCatch.class, tryCatchFinallyOp);
//		treeMap.getExtractersMap().put(VMArray.class, vmArrayOp);
		treeMap.getExtractersMap().put(CreateArray.class, createArrayOp);
		treeMap.getExtractersMap().put(VMObjectEntry.class, vmObjectEntryOp);
		treeMap.getExtractersMap().put(Variable.class, varRefOp);
		treeMap.getExtractersMap().put(VariableDeffine.class, varDefOp);
		treeMap.getExtractersMap().put(While.class, whileOp);

		treeMap.getExtractersMap().put(DebugWrapperValue.class, debugWrapperOp);
		treeMap.getExtractersMap().put(DebugWrapperLValue.class, debugWrapperOp);

//		treeMap.getExtractersMap().put(Wrapper.class, debugWrapper);

		treeMap.getExtractersMap().put(Method.class, methodOp);
		return treeMap;
	}
	
	/**
	 * Создает итератор по древу операндов
	 * @param root Корень дерева
	 * @return Итератор
	 */
	public static Iterable<TreeWalk<Value>> itrable(Value root){
		if (root== null) {			
			throw new IllegalArgumentException("root==null");
		}
		return TreeWalkItreator.<Value>createIterable(root, treeMap());
	}
}
