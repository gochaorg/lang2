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
import xyz.cofe.lang2.vm.op.Block;
import xyz.cofe.lang2.vm.op.ExecuteFlowValue;
import xyz.cofe.lang2.vm.op.ExpressionList;
import xyz.cofe.lang2.vm.op.Function;
import xyz.cofe.lang2.vm.op.VariableDeffine;
import xyz.cofe.text.IndentStackWriter;
import xyz.cofe.text.Text;

/**
 * Класс отображение дерева
 * @author gocha
 */
public class L2SyntaxTreeDump extends SyntaxTreeDump
{
    @Override
    protected void printHeader(IndentStackWriter log) {
        log.println( "........." );
        log.println( "Tree Walk" );
        log.println( "........." );
        log.level(0);
        log.flush();
    }
    
    @Override
    protected String getClassNameOf(Value v){
        String cls = v.getClass().getName();
        cls = Text.trimStart(cls,"lang2.vm.op.");
        return cls;
    }
    
    @Override
    protected boolean isShowAttribute(Value value,String attribute){
        if( attribute.equals("memory") )return false;
        if( attribute.equals("operands") )return false;
        
        Class cls = value.getClass();

        if( attribute.equals("values") && ( 
                cls.equals(ExpressionList.class) 
                || cls.equals(Block.class) 
                ) )return false;
        
        if( attribute.equals("value") && ( 
                cls.equals(VariableDeffine.class) 
                || cls.equals(ExecuteFlowValue.class) 
                )
                )return false;
        
        if( attribute.equals("body") && ( cls.equals(Function.class) ) )
            return false;
        
        return true;
    }
}
