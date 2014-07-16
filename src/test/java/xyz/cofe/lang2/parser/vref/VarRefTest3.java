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
package xyz.cofe.lang2.parser.vref;

import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertTrue;
import xyz.cofe.lang2.parser.BaseTest;
import xyz.cofe.lang2.parser.L2SyntaxTreeDump;
import xyz.cofe.lang2.parser.SyntaxTreeDump;
import xyz.cofe.lang2.parser.SyntaxTreeTest;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.lang2.vm.ValuePath;
import xyz.cofe.lang2.vm.op.Variable;
import xyz.cofe.lang2.vm.VrFunction;
import xyz.cofe.lang2.vm.op.Function;
import org.junit.Test;

/**
 *
 * @author gocha
 */
public class VarRefTest3 extends BaseTest
{
    public VarRefTest3(){
    }
    
    @Test
    public void test(){
        String code = resources().read("varref.l2");
        assertTrue(code!=null);
        
        Value codeValue = parseExpressions(code);
        assertTrue(codeValue!=null);
//        
//        log.println("Код");
//        log.println(code);
//        log.println("Результат");
//        log.println(codeValue.evaluate());
        
//        SyntaxTreeTest.printTree(log, code, codeValue);
        
        log.println("Поиск переменных");
        List<Variable> vars = VrFunction.findVariables(codeValue);
        for( Variable var : vars ){
            log.template("Переменная {0} путь {1}",var.getVariable(),new ValuePath(var) );
//            log.println(toString(AbstractTreeNode.getPath(var)));
			log.println();
            log.flush();
        }
		log.flush();
		
		log.println("Поиск мест определения переменных");
		log.incLevel();
		Map<Variable,Value> varDefLocations = VrFunction.findVarDefineLocation(vars, false);
		for( Variable var : varDefLocations.keySet() ){
            log.template("Переменная {0:8} путь {1}",var.getVariable(),new ValuePath(var));
			log.println();
			Value loc = varDefLocations.get(var);
			log.template("Опеределена в       путь {0}", new ValuePath(loc));
			log.println();
			log.println();
            log.flush();
		}
		log.decLevel();
		log.flush();
        
        List<VrFunction.ExternalVar> extVars = VrFunction.filterExternalVariables(varDefLocations);
		log.println("Фильтр внешних переменных");
		log.incLevel();
		for( VrFunction.ExternalVar evar : extVars ){
            log.template("Переменная {0}", evar.var.getVariable());
			log.println();
            
			log.template("Опеределена {0}", new ValuePath(evar.defineLocation));
			log.println();

            for( Function f : evar.extFunctions ){
                ValuePath fpath = new ValuePath(f);
                log.template("Функция {0}", fpath);
                log.println();
            }
            
			log.println();
            log.flush();
		}
		log.decLevel();
		log.flush();
        
        VrFunction.replaceFunctions( extVars );
        SyntaxTreeDump st = new L2SyntaxTreeDump();
        st.printTree(log, codeValue);

        log.println("Код");
        log.println(code);
        log.println("Результат");
        log.println(codeValue.evaluate());
		log.flush();
    }
}
