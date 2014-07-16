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

import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.op.AbstractTreeNode;
import xyz.cofe.collection.iterators.TreeWalk;
import xyz.cofe.text.IndentStackWriter;
import xyz.cofe.types.TypesUtil;
import xyz.cofe.types.ValueController;

/**
 * Класс отображение дерева
 * @author gocha
 */
public class SyntaxTreeDump
{
    public void printTree(Value code){
        IndentStackWriter w = new IndentStackWriter(new OutputStreamWriter(System.out));
        printTree(w, code);
    }
    
    protected String getClassNameOf(Value v){
        String cls = v.getClass().getName();
        return cls;
    }
    
    protected boolean isShowAttribute(Value value,String attribute){
        return true;
    }
    
    protected void printHeader(IndentStackWriter log){
    }
    
    public void printTree(IndentStackWriter log, Value codeV){
        printHeader(log);
        for( TreeWalk<Value> tw : AbstractTreeNode.tree(codeV) ){
            int level = tw.currentLevel();
            if( level>=0 )log.level(level);
            Value v = tw.currentNode();
            if( v==null ){
                log.println( "node is null" );
                continue;
            }
            int idx = v.getIndex();
            
            String cls = getClassNameOf( v );
            
            log.template("{0}. {1}", idx, cls);
            log.println();
            
            Iterable<ValueController> props = TypesUtil.Iterators.propertiesOf(v);
            log.incLevel();
            for( ValueController vc : props ){
                String attr = vc.getName();
                
//                if( attr.equals("parent") )continue;
                if( !isShowAttribute(v, attr) )continue;
                
                try{
                    Object val = vc.getValue();
					printAttribute( log, attr, val );
                }catch( Throwable ex ){
                    log.template("Exception ! {0}", ex.getMessage());
                    log.println();
                }
            }
        }
        log.level(0);
        log.flush();
    }
	
	public void printAttribute( IndentStackWriter log, String attr, Object value ){
		if( value!=null ){
			Class cval = value.getClass();
			if( cval.isArray() ){
				log.template("{0} = ", attr);
				printArray(log, cval, value);
			}else{
				log.template("{0} = ", attr);
				printValue(log, value);
			}
			log.println();
		}else{
			log.template("{0} is null", attr);
			log.println();
		}
	}
	
	public void printValue( IndentStackWriter log, Object value ){
		if( value==null ){
			log.template("null");
		}else{
			Class c = value.getClass();
			if( c.isArray() ){
				printArray(log, c, value);
			}else{
				log.template("{0}", value);
			}			
		}
	}
	
	public void printArray( IndentStackWriter log, Class valueClass, Object value ){
		int length = Array.getLength(value);
		log.print("{");
		for( int idx = 0; idx<length; idx++ ){
			if( idx>0 ){
				log.print(", ");
			}
			Object item = Array.get(value, idx);
			printValue(log, item);
		}
		log.print("}");
	}
}
