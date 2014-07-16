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

//import java.util.HashMap;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.lang2.vm.SourceRange;
import xyz.cofe.lang2.vm.Value;
import xyz.cofe.common.Wrapper;

import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

/**
 * Абстрактный парсер, 
 * содержит такие свойства как:
 * factory - для создания объектов синтаксического дерева,
 * memory - для памяти
 * @author gocha
 */
public abstract class AbstractParser extends org.antlr.runtime.Parser
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static void logFine(String message,Object ... args){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.FINE, message, args);
    }
    
    private static void logFiner(String message,Object ... args){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.FINER, message, args);
    }
    
    private static void logInfo(String message,Object ... args){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.INFO, message, args);
    }

    private static void logWarning(String message,Object ... args){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.WARNING, message, args);
    }
    
    private static void logSevere(String message,Object ... args){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.SEVERE, message, args);
    }

    private static void logException(Throwable ex){
        Logger.getLogger(AbstractParser.class.getName()).log(Level.SEVERE, null, ex);
    }
    //</editor-fold>

    public AbstractParser(TokenStream input) 
    {
        this(input, new RecognizerSharedState());
    }
    
    public AbstractParser(TokenStream input, RecognizerSharedState state) 
    {
        super(input, state);
    }

    public void location(Object ast,Token start,Token stop){
        if( ast!=null && ast instanceof SourceRange && start!=null && stop!=null ){
            SourceRange sl = (SourceRange)ast;
            sl.setStart(start);
            sl.setStop(stop);
        }
    }

    public void location(Object ast,Token src){
        if( ast!=null && ast instanceof SourceRange && src!=null ){
            SourceRange sl = (SourceRange)ast;
            sl.setStart(src);
            sl.setStop(src);
        }
    }

    private Factory factory = null;

    //TODO Mem Работа с памятью
    public Factory factory(){
        if( factory!=null )return factory;
        factory = new L2Factory(memory);
        return factory;
    }
//	public void setFactory(Factory factory){
//		this.factory = factory;
//	}
    
    protected Map<String,Object> memory = null;
    public Map<String,Object> memory(){ return memory; }

    //TODO Mem Работа с памятью
    public void memory(Map<String,Object> memory){
        this.memory = memory;
        if( factory!=null )
        {
            Object o = factory;
            while( true ){
                if( o instanceof L2Factory ){
                    ((L2Factory)o).initMemory(memory);
                }
                if( o instanceof Wrapper ){
                    Object unwrap = ((Wrapper)o).unwrap();
                    if( unwrap == null )break;
                    o = unwrap;
                    continue;
                }
                break;
            }
        }
    }

    public static String parseStringConst(String text){
    	if( text==null )return null;

    	StringBuilder sb = new StringBuilder();
    	
    	final int BEGIN=0; //начальное
    	final int READ_CHAR=1; //внутри константы
    	final int FINISHED=2; //завершено чтение
    	final int ESCAPE=3; //экранированные символы
    	final int UNICODE=4; //чтение первого unicode-hex кода
    	final int OCTAL=5; //чтение второго ocatl кода
    	final int OCTAL2=6; //чтение третьего ocatl кода
    	final int UNICODE2=7; //чтение второго unicode-hex кода
    	final int UNICODE3=8; //чтение третьего unicode-hex кода
    	final int UNICODE4=9; //чтение четвертого unicode-hex кода
    	final int ERROR=-1; //Ошибка
    	
    	int state = BEGIN;
    	
    	char cOctal1 = 0;
    	char cOctal2 = 0;
    	char cOctal3 = 0;
    	
    	char cU1 = 0;
    	char cU2 = 0;
    	char cU3 = 0;
    	char cU4 = 0;
    	
    	for( int idx = 0; idx < text.length(); idx++ ){
    		char c = text.charAt(idx);
    		switch(state){
	    		case BEGIN:
		    		switch(c){
			    		case '"': state=READ_CHAR; break;
			    		}
		    		break;
	    		case READ_CHAR:
	    			switch(c){
	    				case '"': state=FINISHED; break;
	    				case '\\': state=ESCAPE; break;
	    				default: sb.append(c);
	    			}
	    			break;
	    		case ESCAPE:
	    			switch(c){
	    				case '\\': sb.append(c); state=READ_CHAR; break;
	    				case '"': sb.append(c); state=READ_CHAR; break;
	    				case '\'': sb.append(c); state=READ_CHAR; break;
	    				case 'b': sb.append("\b"); state=READ_CHAR; break;
	    				case 't': sb.append("\t"); state=READ_CHAR; break;
	    				case 'n': sb.append("\n"); state=READ_CHAR; break;
	    				case 'f': sb.append("\f"); state=READ_CHAR; break;
	    				case 'u': state=UNICODE; break;
	    				case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': 
	    					state=OCTAL;
	    					cOctal1 = c;
	    					break;
	    				default: state=ERROR;
	    			}
	    			break;
	    		case OCTAL:
	    			switch(c){
	    				case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
	    					cOctal2 = c;
	    					state=OCTAL2;
	    					break;
	    				case '"': 
	    					state=FINISHED;
	    					Character cc = octalChar(cOctal1);
	    					if( cc==null )state = ERROR;else sb.append( cc );
	    					break;
	    				case '\\': 
	    					state=ESCAPE;
	    					Character cc2 = octalChar(cOctal1);
	    					if( cc2==null )state = ERROR;else sb.append( cc2 );
	    					break;
	    				default: 
	    					state=READ_CHAR; 
	    					Character cc3 = octalChar(cOctal1);
	    					if( cc3==null )state = ERROR; else sb.append( cc3 );
	    					sb.append(c); 
	    					break;
	    			}
	    			break;
	    		case OCTAL2:
	    			switch(c){
	    				case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
	    					cOctal3 = c;
	    					state=READ_CHAR;
	    					Character cc = octalChar(cOctal1,cOctal2,cOctal3);
	    					if( cc==null )state = ERROR;else sb.append( cc );
	    					break;
	    				case '"': 
	    					state=FINISHED;
	    					Character cc1 = octalChar(cOctal1,cOctal2);
	    					if( cc1==null )state = ERROR;else sb.append( cc1 );
	    					break;
	    				case '\\': 
	    					state=ESCAPE;
	    					Character cc2 = octalChar(cOctal1,cOctal2);
	    					if( cc2==null )state = ERROR;else sb.append( cc2 );
	    					break;
	    				default: 
	    					state=READ_CHAR; 
	    					Character cc3 = octalChar(cOctal1,cOctal2);
	    					if( cc3==null )state = ERROR;else sb.append( cc3 );
	    					sb.append(c); 
	    					break;
	    			}
	    			break;
	    		case UNICODE:
	    			switch( c ){
		    			case '0': case '1': case '2': case '3': case '4':
		    			case '5': case '6': case '7': case '8': case '9':
		    			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
		    			case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
		    				cU1 = c;
		    				state = UNICODE2;
		    				break;
	    				default:
	    					state = ERROR;
	    					break;
	    			}
	    			break;
	    		case UNICODE2:
	    			switch( c ){
		    			case '0': case '1': case '2': case '3': case '4':
		    			case '5': case '6': case '7': case '8': case '9':
		    			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
		    			case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
		    				cU2 = c;
		    				state = UNICODE3;
		    				break;
	    				default:
	    					state = ERROR;
	    					break;
	    			}
	    			break;
	    		case UNICODE3:
	    			switch( c ){
		    			case '0': case '1': case '2': case '3': case '4':
		    			case '5': case '6': case '7': case '8': case '9':
		    			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
		    			case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
		    				cU3 = c;
		    				state = UNICODE4;
		    				break;
	    				default:
	    					state = ERROR;
	    					break;
	    			}
	    			break;
	    		case UNICODE4:
	    			switch( c ){
		    			case '0': case '1': case '2': case '3': case '4':
		    			case '5': case '6': case '7': case '8': case '9':
		    			case 'a': case 'b': case 'c': case 'd': case 'e': case 'f':
		    			case 'A': case 'B': case 'C': case 'D': case 'E': case 'F':
		    				cU4 = c;
		    				state = READ_CHAR;
		    				Character cc = hexUnicodeChar(cU1, cU2, cU3, cU4);
	    					if( cc==null )state = ERROR;else sb.append( cc );
		    				break;
	    				default:
	    					state = ERROR;
	    					break;
	    			}
	    			break;
    		}
    	}
    	
    	if( state==FINISHED )return sb.toString();
    	return null;
    }
    
    private static int hexDigit(char c){
    	switch(c){
    	case '0': return (char)0;
    	case '1': return (char)1;
    	case '2': return (char)2;
    	case '3': return (char)3;
    	case '4': return (char)4;
    	case '5': return (char)5;
    	case '6': return (char)6;
    	case '7': return (char)7;
    	case '8': return (char)8;
    	case '9': return (char)9;
    	case 'a': case 'A': return (char)10;
    	case 'b': case 'B': return (char)11;
    	case 'c': case 'C': return (char)12;
    	case 'd': case 'D': return (char)13;
    	case 'e': case 'E': return (char)14;
    	case 'f': case 'F': return (char)15;
    	}
    	return -1;
    }
    
    private static Character hexUnicodeChar(char c1,char c2,char c3,char c4){
    	int i1 = hexDigit(c1);
    	int i2 = hexDigit(c2);
    	int i3 = hexDigit(c3);
    	int i4 = hexDigit(c4);
    	if( i1<0 || i2<0 || i3<0 || i4<0 )return null;
    	return (char)((int)(i1*4096+i2*256+i3*16+i4));
    }
    
    private static int octalDigit(char c){
    	switch(c){
    	case '0': return (char)0;
    	case '1': return (char)1;
    	case '2': return (char)2;
    	case '3': return (char)3;
    	case '4': return (char)4;
    	case '5': return (char)5;
    	case '6': return (char)6;
    	case '7': return (char)7;
    	}
    	return -1;
    }
    
    private static Character octalChar(char c){
    	int digit = octalDigit(c);
    	if( digit<0 )return null;
    	byte b = (byte)((int)(digit));
    	String s = new String( new byte[]{ b } );
    	if( s.length()>0 )return s.charAt(0);
    	return null;
    }

    private static Character octalChar(char c1,char c2){
    	int i1 = octalDigit(c1);
    	int i2 = octalDigit(c2);
    	if( i1<0 || i2<0 )return null;
    	byte b = (byte)((int)(i1*8+i2));
    	String s = new String( new byte[]{ b } );
    	if( s.length()>0 )return s.charAt(0);
    	return null;
    }
    
    private static Character octalChar(char c1,char c2,char c3){
    	int i1 = octalDigit(c1);
    	int i2 = octalDigit(c2);
    	int i3 = octalDigit(c3);
    	if( i1<0 || i2<0 || i3<0 || i1>3 )return null;

    	byte b = (byte)((int)(i1*64+i2*8+i3));
    	String s = new String( new byte[]{ b } );
    	if( s.length()>0 )return s.charAt(0);
    	return null;
    }
}