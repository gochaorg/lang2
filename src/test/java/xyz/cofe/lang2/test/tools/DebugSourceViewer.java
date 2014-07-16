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
package xyz.cofe.lang2.test.tools;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import xyz.cofe.lang2.parser.StringStream;
import xyz.cofe.lang2.vm.op.DebugWrapperValue;
import xyz.cofe.lang2.vm.Spider;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import xyz.cofe.text.IndentPrintWriter;
import xyz.cofe.text.Text;

/**
 * @author gocha
 */
public class DebugSourceViewer extends Spider {
    protected IndentPrintWriter writer = null;
    protected String[] sourceLines = null;
    protected StringStream sourceCharStream = null;
    
    public DebugSourceViewer(Writer output){
        if( output==null ){
            writer = new IndentPrintWriter();
        }else{
            writer = new IndentPrintWriter(output);
        }
    }
    
    public static String getSourceOf(Object valueTree){
        if (valueTree== null) {            
            throw new IllegalArgumentException("valueTree==null");
        }
        
        StringWriter swriter = new StringWriter();
        DebugSourceViewer viewer = new DebugSourceViewer(swriter);
        viewer.go(valueTree);
        return swriter.toString();
    }

    @Override
    protected void beginGo(Object o) {
        super.beginGo(o);
    }

    @Override
    protected void endGo(Object o) {
        super.endGo(o);
        writer.flush();
        sourceCharStream = null;
        if( sourceLines!=null ){
            sourceLines = null;
        }
    }

    protected String[] getSourceLines(CharStream cs){
        if (cs== null) {
            throw new IllegalArgumentException("cs==null");
        }
        if( cs==sourceCharStream && sourceLines!=null )return sourceLines;

        if( !(cs instanceof StringStream) )throw new Error(
                "char stream type("+cs.getClass().getName()+")"+
                " is not "+StringStream.class.getName() );

        sourceCharStream = (StringStream)cs;
        String source = sourceCharStream.getDataAsString();
        sourceLines = Text.split(source, ""+sourceCharStream.getLineDelimeter());
        return sourceLines;
    }

    protected String getSubString(Token tokStart,Token tokEnd){
        if (tokStart== null) {
            throw new IllegalArgumentException("tokStart==null");
        }
        if (tokEnd== null) {
            throw new IllegalArgumentException("tokEnd==null");
        }

        CharStream cs = tokStart.getInputStream();
        String[] lines = getSourceLines(cs);

        int cmpRes = comparePosition(tokStart, tokEnd);
        if( cmpRes > 0 ){
            Token tok = tokStart;
            tokStart = tokEnd;
            tokEnd = tok;
        }else if( cmpRes == 0 ){
            String tStart = tokStart.getText();
            String tEnd = tokEnd.getText();
            if( tStart.length()<tEnd.length() )
                return tEnd;
            else
                return tStart;
        }

        StringBuilder sb = new StringBuilder();
        for( int lineNum=tokStart.getLine(); lineNum<=tokEnd.getLine(); lineNum++ ){
            String line = (lineNum>0 && lineNum<=lines.length) ? lines[lineNum-1] : null;

            if( line!=null ){
                boolean fromBeginLine = tokStart.getLine() < lineNum;
                boolean toEndLine = tokEnd.getLine() > lineNum;
                int beginIndex = tokStart.getCharPositionInLine();
                int endIndex = tokEnd.getCharPositionInLine();
                int lineLen = line.length();
                boolean appendEndTokenText = lineNum==tokEnd.getLine();

                if( fromBeginLine && toEndLine ){
                    sb.append(line);
                    sb.append(sourceCharStream.getLineDelimeter());
                }else if( !fromBeginLine && toEndLine ) {
                    if( beginIndex>=lineLen ){
                        sb.append("/* no source */");
                        sb.append(sourceCharStream.getLineDelimeter());
                    }else{
                        sb.append(line.substring(beginIndex));
                        sb.append(sourceCharStream.getLineDelimeter());
                    }
                }else if( fromBeginLine && !toEndLine ){
                    if( endIndex>=lineLen ){
                        sb.append(line.substring(beginIndex));
                        sb.append("/* no source */");
                        if( !appendEndTokenText )sb.append(sourceCharStream.getLineDelimeter());
                    }else{
                        sb.append(line.substring(0,endIndex));
                        if( !appendEndTokenText )sb.append(sourceCharStream.getLineDelimeter());
                    }
                }else{
                    sb.append("/* no source */");
                    sb.append(sourceCharStream.getLineDelimeter());
                }

                if( appendEndTokenText ){
                    sb.append(tokEnd.getText());
                }
            }else{
                sb.append("/* no source */");
                sb.append(sourceCharStream.getLineDelimeter());
            }
        }
        return sb.toString();
    }

    @Override
    protected void goDebugWrapperValue(DebugWrapperValue wrapper) {
        Token tokStart = wrapper.getStart();
        Token tokEnd = wrapper.getStop();

        if( tokStart!=null && tokEnd!=null ){
            writer.println("== object source ======");
            if( tokStart==tokEnd ){
                writer.println( tokStart.getText() );
            }else{
                writer.println( getSubString( tokStart, tokEnd ) );
            }
            writer.println("== object source end ==");
        }else{
            writer.println("no object source");
        }

        writer.incLevel();
        super.goDebugWrapperValue(wrapper);
        writer.decLevel();
    }

    public static final Comparator<Token> positionComparator = new Comparator<Token>() {
        @Override
        public int compare(Token o1, Token o2) {
            return comparePosition(o1, o2);
        }
    };

    public static int comparePosition(Token tokA,Token tokB){
        if( tokA==null && tokB==null )return 0;
        if( tokA!=null && tokB==null )return -1;
        if( tokA==null && tokB!=null )return 1;
        if( tokA.getLine()==tokB.getLine() ){
            if( tokA.getCharPositionInLine()==tokB.getCharPositionInLine() ){
                return 0;
            }else{
                if( tokA.getCharPositionInLine() < tokB.getCharPositionInLine() ){
                    return -1;
                }else{
                    return 1;
                }
            }
        }else{
            if( tokA.getLine() < tokB.getLine() ){
                return -1;
            }else{
                return 1;
            }
        }
    }
}
