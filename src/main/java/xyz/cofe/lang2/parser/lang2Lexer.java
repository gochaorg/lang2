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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class lang2Lexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int T__35=35;
	public static final int T__36=36;
	public static final int T__37=37;
	public static final int T__38=38;
	public static final int T__39=39;
	public static final int T__40=40;
	public static final int T__41=41;
	public static final int T__42=42;
	public static final int T__43=43;
	public static final int T__44=44;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int T__57=57;
	public static final int T__58=58;
	public static final int T__59=59;
	public static final int T__60=60;
	public static final int T__61=61;
	public static final int T__62=62;
	public static final int CHAR=4;
	public static final int COMMENT=5;
	public static final int ESC_SEQ=6;
	public static final int EXPONENT=7;
	public static final int FLOAT=8;
	public static final int FUNCTION=9;
	public static final int HEX_DIGIT=10;
	public static final int ID=11;
	public static final int INT=12;
	public static final int NOT=13;
	public static final int OCTAL_ESC=14;
	public static final int STRING=15;
	public static final int UNICODE_ESC=16;
	public static final int WS=17;

	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public lang2Lexer() {} 
	public lang2Lexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public lang2Lexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "/home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g"; }

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:6:7: ( '!=' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:6:9: '!='
			{
			match("!="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "T__19"
	public final void mT__19() throws RecognitionException {
		try {
			int _type = T__19;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:7:7: ( '%' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:7:9: '%'
			{
			match('%'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__19"

	// $ANTLR start "T__20"
	public final void mT__20() throws RecognitionException {
		try {
			int _type = T__20;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:8:7: ( '&' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:8:9: '&'
			{
			match('&'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__20"

	// $ANTLR start "T__21"
	public final void mT__21() throws RecognitionException {
		try {
			int _type = T__21;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:9:7: ( '(' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:9:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__21"

	// $ANTLR start "T__22"
	public final void mT__22() throws RecognitionException {
		try {
			int _type = T__22;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:10:7: ( ')' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:10:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__22"

	// $ANTLR start "T__23"
	public final void mT__23() throws RecognitionException {
		try {
			int _type = T__23;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:11:7: ( '*' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:11:9: '*'
			{
			match('*'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__23"

	// $ANTLR start "T__24"
	public final void mT__24() throws RecognitionException {
		try {
			int _type = T__24;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:12:7: ( '+' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:12:9: '+'
			{
			match('+'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__24"

	// $ANTLR start "T__25"
	public final void mT__25() throws RecognitionException {
		try {
			int _type = T__25;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:13:7: ( ',' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:13:9: ','
			{
			match(','); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__25"

	// $ANTLR start "T__26"
	public final void mT__26() throws RecognitionException {
		try {
			int _type = T__26;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:14:7: ( '-' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:14:9: '-'
			{
			match('-'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__26"

	// $ANTLR start "T__27"
	public final void mT__27() throws RecognitionException {
		try {
			int _type = T__27;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:15:7: ( '.' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:15:9: '.'
			{
			match('.'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__27"

	// $ANTLR start "T__28"
	public final void mT__28() throws RecognitionException {
		try {
			int _type = T__28;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:16:7: ( '/' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:16:9: '/'
			{
			match('/'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__28"

	// $ANTLR start "T__29"
	public final void mT__29() throws RecognitionException {
		try {
			int _type = T__29;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:17:7: ( ':' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:17:9: ':'
			{
			match(':'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__29"

	// $ANTLR start "T__30"
	public final void mT__30() throws RecognitionException {
		try {
			int _type = T__30;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:18:7: ( ';' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:18:9: ';'
			{
			match(';'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__30"

	// $ANTLR start "T__31"
	public final void mT__31() throws RecognitionException {
		try {
			int _type = T__31;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:19:7: ( '<' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:19:9: '<'
			{
			match('<'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__31"

	// $ANTLR start "T__32"
	public final void mT__32() throws RecognitionException {
		try {
			int _type = T__32;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:20:7: ( '<=' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:20:9: '<='
			{
			match("<="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__32"

	// $ANTLR start "T__33"
	public final void mT__33() throws RecognitionException {
		try {
			int _type = T__33;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:21:7: ( '<>' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:21:9: '<>'
			{
			match("<>"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__33"

	// $ANTLR start "T__34"
	public final void mT__34() throws RecognitionException {
		try {
			int _type = T__34;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:22:7: ( '=' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:22:9: '='
			{
			match('='); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__34"

	// $ANTLR start "T__35"
	public final void mT__35() throws RecognitionException {
		try {
			int _type = T__35;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:23:7: ( '==' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:23:9: '=='
			{
			match("=="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__35"

	// $ANTLR start "T__36"
	public final void mT__36() throws RecognitionException {
		try {
			int _type = T__36;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:24:7: ( '>' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:24:9: '>'
			{
			match('>'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__36"

	// $ANTLR start "T__37"
	public final void mT__37() throws RecognitionException {
		try {
			int _type = T__37;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:25:7: ( '>=' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:25:9: '>='
			{
			match(">="); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__37"

	// $ANTLR start "T__38"
	public final void mT__38() throws RecognitionException {
		try {
			int _type = T__38;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:26:7: ( '?' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:26:9: '?'
			{
			match('?'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__38"

	// $ANTLR start "T__39"
	public final void mT__39() throws RecognitionException {
		try {
			int _type = T__39;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:27:7: ( '[' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:27:9: '['
			{
			match('['); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__39"

	// $ANTLR start "T__40"
	public final void mT__40() throws RecognitionException {
		try {
			int _type = T__40;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:28:7: ( ']' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:28:9: ']'
			{
			match(']'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__40"

	// $ANTLR start "T__41"
	public final void mT__41() throws RecognitionException {
		try {
			int _type = T__41;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:29:7: ( '^' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:29:9: '^'
			{
			match('^'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__41"

	// $ANTLR start "T__42"
	public final void mT__42() throws RecognitionException {
		try {
			int _type = T__42;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:30:7: ( 'and' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:30:9: 'and'
			{
			match("and"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__42"

	// $ANTLR start "T__43"
	public final void mT__43() throws RecognitionException {
		try {
			int _type = T__43;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:31:7: ( 'break' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:31:9: 'break'
			{
			match("break"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__43"

	// $ANTLR start "T__44"
	public final void mT__44() throws RecognitionException {
		try {
			int _type = T__44;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:32:7: ( 'catch' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:32:9: 'catch'
			{
			match("catch"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__44"

	// $ANTLR start "T__45"
	public final void mT__45() throws RecognitionException {
		try {
			int _type = T__45;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:33:7: ( 'continue' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:33:9: 'continue'
			{
			match("continue"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__45"

	// $ANTLR start "T__46"
	public final void mT__46() throws RecognitionException {
		try {
			int _type = T__46;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:34:7: ( 'else' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:34:9: 'else'
			{
			match("else"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__46"

	// $ANTLR start "T__47"
	public final void mT__47() throws RecognitionException {
		try {
			int _type = T__47;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:35:7: ( 'false' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:35:9: 'false'
			{
			match("false"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__47"

	// $ANTLR start "T__48"
	public final void mT__48() throws RecognitionException {
		try {
			int _type = T__48;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:36:7: ( 'for' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:36:9: 'for'
			{
			match("for"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__48"

	// $ANTLR start "T__49"
	public final void mT__49() throws RecognitionException {
		try {
			int _type = T__49;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:7: ( 'if' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:9: 'if'
			{
			match("if"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__49"

	// $ANTLR start "T__50"
	public final void mT__50() throws RecognitionException {
		try {
			int _type = T__50;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:38:7: ( 'in' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:38:9: 'in'
			{
			match("in"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__50"

	// $ANTLR start "T__51"
	public final void mT__51() throws RecognitionException {
		try {
			int _type = T__51;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:39:7: ( 'null' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:39:9: 'null'
			{
			match("null"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__51"

	// $ANTLR start "T__52"
	public final void mT__52() throws RecognitionException {
		try {
			int _type = T__52;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:40:7: ( 'or' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:40:9: 'or'
			{
			match("or"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__52"

	// $ANTLR start "T__53"
	public final void mT__53() throws RecognitionException {
		try {
			int _type = T__53;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:41:7: ( 'return' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:41:9: 'return'
			{
			match("return"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__53"

	// $ANTLR start "T__54"
	public final void mT__54() throws RecognitionException {
		try {
			int _type = T__54;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:42:7: ( 'throw' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:42:9: 'throw'
			{
			match("throw"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__54"

	// $ANTLR start "T__55"
	public final void mT__55() throws RecognitionException {
		try {
			int _type = T__55;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:43:7: ( 'true' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:43:9: 'true'
			{
			match("true"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__55"

	// $ANTLR start "T__56"
	public final void mT__56() throws RecognitionException {
		try {
			int _type = T__56;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:44:7: ( 'try' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:44:9: 'try'
			{
			match("try"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__56"

	// $ANTLR start "T__57"
	public final void mT__57() throws RecognitionException {
		try {
			int _type = T__57;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:45:7: ( 'var' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:45:9: 'var'
			{
			match("var"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__57"

	// $ANTLR start "T__58"
	public final void mT__58() throws RecognitionException {
		try {
			int _type = T__58;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:46:7: ( 'while' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:46:9: 'while'
			{
			match("while"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__58"

	// $ANTLR start "T__59"
	public final void mT__59() throws RecognitionException {
		try {
			int _type = T__59;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:47:7: ( 'xor' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:47:9: 'xor'
			{
			match("xor"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__59"

	// $ANTLR start "T__60"
	public final void mT__60() throws RecognitionException {
		try {
			int _type = T__60;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:48:7: ( '{' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:48:9: '{'
			{
			match('{'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__60"

	// $ANTLR start "T__61"
	public final void mT__61() throws RecognitionException {
		try {
			int _type = T__61;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:49:7: ( '|' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:49:9: '|'
			{
			match('|'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__61"

	// $ANTLR start "T__62"
	public final void mT__62() throws RecognitionException {
		try {
			int _type = T__62;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:50:7: ( '}' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:50:9: '}'
			{
			match('}'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__62"

	// $ANTLR start "FUNCTION"
	public final void mFUNCTION() throws RecognitionException {
		try {
			int _type = FUNCTION;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:388:2: ( 'function' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:388:4: 'function'
			{
			match("function"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FUNCTION"

	// $ANTLR start "NOT"
	public final void mNOT() throws RecognitionException {
		try {
			int _type = NOT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:390:5: ( '!' | 'not' )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='!') ) {
				alt1=1;
			}
			else if ( (LA1_0=='n') ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:390:7: '!'
					{
					match('!'); 
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:390:13: 'not'
					{
					match("not"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "NOT"

	// $ANTLR start "ID"
	public final void mID() throws RecognitionException {
		try {
			int _type = ID;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:392:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:392:7: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			{
			if ( (input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:392:31: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( ((LA2_0 >= '0' && LA2_0 <= '9')||(LA2_0 >= 'A' && LA2_0 <= 'Z')||LA2_0=='_'||(LA2_0 >= 'a' && LA2_0 <= 'z')) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop2;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ID"

	// $ANTLR start "INT"
	public final void mINT() throws RecognitionException {
		try {
			int _type = INT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:395:5: ( ( '0' .. '9' )+ )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:395:7: ( '0' .. '9' )+
			{
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:395:7: ( '0' .. '9' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "INT"

	// $ANTLR start "FLOAT"
	public final void mFLOAT() throws RecognitionException {
		try {
			int _type = FLOAT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:400:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:400:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )*
			{
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:400:9: ( '0' .. '9' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			match('.'); 
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:400:25: ( '0' .. '9' )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( ((LA5_0 >= '0' && LA5_0 <= '9')) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop5;
				}
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "FLOAT"

	// $ANTLR start "COMMENT"
	public final void mCOMMENT() throws RecognitionException {
		try {
			int _type = COMMENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:410:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '/*' ( options {greedy=false; } : . )* '*/' )
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0=='/') ) {
				int LA9_1 = input.LA(2);
				if ( (LA9_1=='/') ) {
					alt9=1;
				}
				else if ( (LA9_1=='*') ) {
					alt9=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 9, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 9, 0, input);
				throw nvae;
			}

			switch (alt9) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:410:9: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
					{
					match("//"); 

					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:410:14: (~ ( '\\n' | '\\r' ) )*
					loop6:
					while (true) {
						int alt6=2;
						int LA6_0 = input.LA(1);
						if ( ((LA6_0 >= '\u0000' && LA6_0 <= '\t')||(LA6_0 >= '\u000B' && LA6_0 <= '\f')||(LA6_0 >= '\u000E' && LA6_0 <= '\uFFFF')) ) {
							alt6=1;
						}

						switch (alt6) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
							{
							if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
								input.consume();
							}
							else {
								MismatchedSetException mse = new MismatchedSetException(null,input);
								recover(mse);
								throw mse;
							}
							}
							break;

						default :
							break loop6;
						}
					}

					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:410:28: ( '\\r' )?
					int alt7=2;
					int LA7_0 = input.LA(1);
					if ( (LA7_0=='\r') ) {
						alt7=1;
					}
					switch (alt7) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:410:28: '\\r'
							{
							match('\r'); 
							}
							break;

					}

					match('\n'); 
					_channel=HIDDEN;
					CommentReciver.recive(-1, state.tokenStartLine, state.tokenStartCharPositionInLine, getText());
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:413:9: '/*' ( options {greedy=false; } : . )* '*/'
					{
					match("/*"); 

					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:413:14: ( options {greedy=false; } : . )*
					loop8:
					while (true) {
						int alt8=2;
						int LA8_0 = input.LA(1);
						if ( (LA8_0=='*') ) {
							int LA8_1 = input.LA(2);
							if ( (LA8_1=='/') ) {
								alt8=2;
							}
							else if ( ((LA8_1 >= '\u0000' && LA8_1 <= '.')||(LA8_1 >= '0' && LA8_1 <= '\uFFFF')) ) {
								alt8=1;
							}

						}
						else if ( ((LA8_0 >= '\u0000' && LA8_0 <= ')')||(LA8_0 >= '+' && LA8_0 <= '\uFFFF')) ) {
							alt8=1;
						}

						switch (alt8) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:413:42: .
							{
							matchAny(); 
							}
							break;

						default :
							break loop8;
						}
					}

					match("*/"); 

					_channel=HIDDEN;
					CommentReciver.recive(-1, state.tokenStartLine, state.tokenStartCharPositionInLine, getText());
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "COMMENT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:418:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:418:9: ( ' ' | '\\t' | '\\r' | '\\n' )
			{
			if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			_channel=HIDDEN;
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	// $ANTLR start "STRING"
	public final void mSTRING() throws RecognitionException {
		try {
			int _type = STRING;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:426:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:426:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
			{
			match('\"'); 
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:426:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
			loop10:
			while (true) {
				int alt10=3;
				int LA10_0 = input.LA(1);
				if ( (LA10_0=='\\') ) {
					alt10=1;
				}
				else if ( ((LA10_0 >= '\u0000' && LA10_0 <= '!')||(LA10_0 >= '#' && LA10_0 <= '[')||(LA10_0 >= ']' && LA10_0 <= '\uFFFF')) ) {
					alt10=2;
				}

				switch (alt10) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:426:14: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:426:24: ~ ( '\\\\' | '\"' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					break loop10;
				}
			}

			match('\"'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "STRING"

	// $ANTLR start "CHAR"
	public final void mCHAR() throws RecognitionException {
		try {
			int _type = CHAR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:429:5: ( '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\'' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:429:8: '\\'' ( ESC_SEQ |~ ( '\\'' | '\\\\' ) ) '\\''
			{
			match('\''); 
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:429:13: ( ESC_SEQ |~ ( '\\'' | '\\\\' ) )
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0=='\\') ) {
				alt11=1;
			}
			else if ( ((LA11_0 >= '\u0000' && LA11_0 <= '&')||(LA11_0 >= '(' && LA11_0 <= '[')||(LA11_0 >= ']' && LA11_0 <= '\uFFFF')) ) {
				alt11=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 11, 0, input);
				throw nvae;
			}

			switch (alt11) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:429:15: ESC_SEQ
					{
					mESC_SEQ(); 

					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:429:25: ~ ( '\\'' | '\\\\' )
					{
					if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '&')||(input.LA(1) >= '(' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			match('\''); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "CHAR"

	// $ANTLR start "EXPONENT"
	public final void mEXPONENT() throws RecognitionException {
		try {
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:434:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:434:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
			{
			if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:434:22: ( '+' | '-' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0=='+'||LA12_0=='-') ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:434:33: ( '0' .. '9' )+
			int cnt13=0;
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( ((LA13_0 >= '0' && LA13_0 <= '9')) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
					{
					if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt13 >= 1 ) break loop13;
					EarlyExitException eee = new EarlyExitException(13, input);
					throw eee;
				}
				cnt13++;
			}

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "EXPONENT"

	// $ANTLR start "HEX_DIGIT"
	public final void mHEX_DIGIT() throws RecognitionException {
		try {
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:437:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:
			{
			if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
				input.consume();
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				recover(mse);
				throw mse;
			}
			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "HEX_DIGIT"

	// $ANTLR start "ESC_SEQ"
	public final void mESC_SEQ() throws RecognitionException {
		try {
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:441:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
			int alt14=3;
			int LA14_0 = input.LA(1);
			if ( (LA14_0=='\\') ) {
				switch ( input.LA(2) ) {
				case '\"':
				case '\'':
				case '\\':
				case 'b':
				case 'f':
				case 'n':
				case 'r':
				case 't':
					{
					alt14=1;
					}
					break;
				case 'u':
					{
					alt14=2;
					}
					break;
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
					{
					alt14=3;
					}
					break;
				default:
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 14, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 14, 0, input);
				throw nvae;
			}

			switch (alt14) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:441:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
					{
					match('\\'); 
					if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:442:9: UNICODE_ESC
					{
					mUNICODE_ESC(); 

					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:443:9: OCTAL_ESC
					{
					mOCTAL_ESC(); 

					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "ESC_SEQ"

	// $ANTLR start "OCTAL_ESC"
	public final void mOCTAL_ESC() throws RecognitionException {
		try {
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:448:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
			int alt15=3;
			int LA15_0 = input.LA(1);
			if ( (LA15_0=='\\') ) {
				int LA15_1 = input.LA(2);
				if ( ((LA15_1 >= '0' && LA15_1 <= '3')) ) {
					int LA15_2 = input.LA(3);
					if ( ((LA15_2 >= '0' && LA15_2 <= '7')) ) {
						int LA15_4 = input.LA(4);
						if ( ((LA15_4 >= '0' && LA15_4 <= '7')) ) {
							alt15=1;
						}

						else {
							alt15=2;
						}

					}

					else {
						alt15=3;
					}

				}
				else if ( ((LA15_1 >= '4' && LA15_1 <= '7')) ) {
					int LA15_3 = input.LA(3);
					if ( ((LA15_3 >= '0' && LA15_3 <= '7')) ) {
						alt15=2;
					}

					else {
						alt15=3;
					}

				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 15, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 15, 0, input);
				throw nvae;
			}

			switch (alt15) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:448:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:449:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:450:9: '\\\\' ( '0' .. '7' )
					{
					match('\\'); 
					if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

			}
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OCTAL_ESC"

	// $ANTLR start "UNICODE_ESC"
	public final void mUNICODE_ESC() throws RecognitionException {
		try {
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:455:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:455:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
			{
			match('\\'); 
			match('u'); 
			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			mHEX_DIGIT(); 

			}

		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "UNICODE_ESC"

	@Override
	public void mTokens() throws RecognitionException {
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:8: ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | FUNCTION | NOT | ID | INT | FLOAT | COMMENT | WS | STRING | CHAR )
		int alt16=54;
		alt16 = dfa16.predict(input);
		switch (alt16) {
			case 1 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:10: T__18
				{
				mT__18(); 

				}
				break;
			case 2 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:16: T__19
				{
				mT__19(); 

				}
				break;
			case 3 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:22: T__20
				{
				mT__20(); 

				}
				break;
			case 4 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:28: T__21
				{
				mT__21(); 

				}
				break;
			case 5 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:34: T__22
				{
				mT__22(); 

				}
				break;
			case 6 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:40: T__23
				{
				mT__23(); 

				}
				break;
			case 7 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:46: T__24
				{
				mT__24(); 

				}
				break;
			case 8 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:52: T__25
				{
				mT__25(); 

				}
				break;
			case 9 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:58: T__26
				{
				mT__26(); 

				}
				break;
			case 10 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:64: T__27
				{
				mT__27(); 

				}
				break;
			case 11 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:70: T__28
				{
				mT__28(); 

				}
				break;
			case 12 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:76: T__29
				{
				mT__29(); 

				}
				break;
			case 13 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:82: T__30
				{
				mT__30(); 

				}
				break;
			case 14 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:88: T__31
				{
				mT__31(); 

				}
				break;
			case 15 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:94: T__32
				{
				mT__32(); 

				}
				break;
			case 16 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:100: T__33
				{
				mT__33(); 

				}
				break;
			case 17 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:106: T__34
				{
				mT__34(); 

				}
				break;
			case 18 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:112: T__35
				{
				mT__35(); 

				}
				break;
			case 19 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:118: T__36
				{
				mT__36(); 

				}
				break;
			case 20 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:124: T__37
				{
				mT__37(); 

				}
				break;
			case 21 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:130: T__38
				{
				mT__38(); 

				}
				break;
			case 22 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:136: T__39
				{
				mT__39(); 

				}
				break;
			case 23 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:142: T__40
				{
				mT__40(); 

				}
				break;
			case 24 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:148: T__41
				{
				mT__41(); 

				}
				break;
			case 25 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:154: T__42
				{
				mT__42(); 

				}
				break;
			case 26 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:160: T__43
				{
				mT__43(); 

				}
				break;
			case 27 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:166: T__44
				{
				mT__44(); 

				}
				break;
			case 28 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:172: T__45
				{
				mT__45(); 

				}
				break;
			case 29 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:178: T__46
				{
				mT__46(); 

				}
				break;
			case 30 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:184: T__47
				{
				mT__47(); 

				}
				break;
			case 31 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:190: T__48
				{
				mT__48(); 

				}
				break;
			case 32 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:196: T__49
				{
				mT__49(); 

				}
				break;
			case 33 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:202: T__50
				{
				mT__50(); 

				}
				break;
			case 34 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:208: T__51
				{
				mT__51(); 

				}
				break;
			case 35 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:214: T__52
				{
				mT__52(); 

				}
				break;
			case 36 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:220: T__53
				{
				mT__53(); 

				}
				break;
			case 37 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:226: T__54
				{
				mT__54(); 

				}
				break;
			case 38 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:232: T__55
				{
				mT__55(); 

				}
				break;
			case 39 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:238: T__56
				{
				mT__56(); 

				}
				break;
			case 40 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:244: T__57
				{
				mT__57(); 

				}
				break;
			case 41 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:250: T__58
				{
				mT__58(); 

				}
				break;
			case 42 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:256: T__59
				{
				mT__59(); 

				}
				break;
			case 43 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:262: T__60
				{
				mT__60(); 

				}
				break;
			case 44 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:268: T__61
				{
				mT__61(); 

				}
				break;
			case 45 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:274: T__62
				{
				mT__62(); 

				}
				break;
			case 46 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:280: FUNCTION
				{
				mFUNCTION(); 

				}
				break;
			case 47 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:289: NOT
				{
				mNOT(); 

				}
				break;
			case 48 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:293: ID
				{
				mID(); 

				}
				break;
			case 49 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:296: INT
				{
				mINT(); 

				}
				break;
			case 50 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:300: FLOAT
				{
				mFLOAT(); 

				}
				break;
			case 51 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:306: COMMENT
				{
				mCOMMENT(); 

				}
				break;
			case 52 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:314: WS
				{
				mWS(); 

				}
				break;
			case 53 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:317: STRING
				{
				mSTRING(); 

				}
				break;
			case 54 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:1:324: CHAR
				{
				mCHAR(); 

				}
				break;

		}
	}


	protected DFA16 dfa16 = new DFA16(this);
	static final String DFA16_eotS =
		"\1\uffff\1\53\11\uffff\1\55\2\uffff\1\60\1\62\1\64\4\uffff\15\45\4\uffff"+
		"\1\110\16\uffff\10\45\1\122\1\123\2\45\1\126\6\45\2\uffff\1\136\5\45\1"+
		"\144\1\45\2\uffff\1\45\1\53\1\uffff\3\45\1\152\1\153\1\45\1\155\1\uffff"+
		"\3\45\1\161\1\45\1\uffff\1\45\1\164\2\45\1\167\2\uffff\1\45\1\uffff\1"+
		"\171\1\172\1\45\1\uffff\1\174\1\45\1\uffff\1\45\1\177\1\uffff\1\u0080"+
		"\2\uffff\1\45\1\uffff\1\45\1\u0083\2\uffff\2\45\1\uffff\1\u0086\1\u0087"+
		"\2\uffff";
	static final String DFA16_eofS =
		"\u0088\uffff";
	static final String DFA16_minS =
		"\1\11\1\75\11\uffff\1\52\2\uffff\3\75\4\uffff\1\156\1\162\1\141\1\154"+
		"\1\141\1\146\1\157\1\162\1\145\1\150\1\141\1\150\1\157\4\uffff\1\56\16"+
		"\uffff\1\144\1\145\1\164\1\156\1\163\1\154\1\162\1\156\2\60\1\154\1\164"+
		"\1\60\1\164\1\162\1\165\1\162\1\151\1\162\2\uffff\1\60\1\141\1\143\1\164"+
		"\1\145\1\163\1\60\1\143\2\uffff\1\154\1\60\1\uffff\1\165\1\157\1\145\2"+
		"\60\1\154\1\60\1\uffff\1\153\1\150\1\151\1\60\1\145\1\uffff\1\164\1\60"+
		"\1\162\1\167\1\60\2\uffff\1\145\1\uffff\2\60\1\156\1\uffff\1\60\1\151"+
		"\1\uffff\1\156\1\60\1\uffff\1\60\2\uffff\1\165\1\uffff\1\157\1\60\2\uffff"+
		"\1\145\1\156\1\uffff\2\60\2\uffff";
	static final String DFA16_maxS =
		"\1\175\1\75\11\uffff\1\57\2\uffff\1\76\2\75\4\uffff\1\156\1\162\1\157"+
		"\1\154\1\165\1\156\1\165\1\162\1\145\1\162\1\141\1\150\1\157\4\uffff\1"+
		"\71\16\uffff\1\144\1\145\1\164\1\156\1\163\1\154\1\162\1\156\2\172\1\154"+
		"\1\164\1\172\1\164\1\162\1\171\1\162\1\151\1\162\2\uffff\1\172\1\141\1"+
		"\143\1\164\1\145\1\163\1\172\1\143\2\uffff\1\154\1\172\1\uffff\1\165\1"+
		"\157\1\145\2\172\1\154\1\172\1\uffff\1\153\1\150\1\151\1\172\1\145\1\uffff"+
		"\1\164\1\172\1\162\1\167\1\172\2\uffff\1\145\1\uffff\2\172\1\156\1\uffff"+
		"\1\172\1\151\1\uffff\1\156\1\172\1\uffff\1\172\2\uffff\1\165\1\uffff\1"+
		"\157\1\172\2\uffff\1\145\1\156\1\uffff\2\172\2\uffff";
	static final String DFA16_acceptS =
		"\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\uffff\1\14\1\15\3\uffff"+
		"\1\25\1\26\1\27\1\30\15\uffff\1\53\1\54\1\55\1\60\1\uffff\1\64\1\65\1"+
		"\66\1\1\1\57\1\63\1\13\1\17\1\20\1\16\1\22\1\21\1\24\1\23\23\uffff\1\61"+
		"\1\62\10\uffff\1\40\1\41\2\uffff\1\43\7\uffff\1\31\5\uffff\1\37\5\uffff"+
		"\1\47\1\50\1\uffff\1\52\3\uffff\1\35\2\uffff\1\42\2\uffff\1\46\1\uffff"+
		"\1\32\1\33\1\uffff\1\36\2\uffff\1\45\1\51\2\uffff\1\44\2\uffff\1\34\1"+
		"\56";
	static final String DFA16_specialS =
		"\u0088\uffff}>";
	static final String[] DFA16_transitionS = {
			"\2\47\2\uffff\1\47\22\uffff\1\47\1\1\1\50\2\uffff\1\2\1\3\1\51\1\4\1"+
			"\5\1\6\1\7\1\10\1\11\1\12\1\13\12\46\1\14\1\15\1\16\1\17\1\20\1\21\1"+
			"\uffff\32\45\1\22\1\uffff\1\23\1\24\1\45\1\uffff\1\25\1\26\1\27\1\45"+
			"\1\30\1\31\2\45\1\32\4\45\1\33\1\34\2\45\1\35\1\45\1\36\1\45\1\37\1\40"+
			"\1\41\2\45\1\42\1\43\1\44",
			"\1\52",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\54\4\uffff\1\54",
			"",
			"",
			"\1\56\1\57",
			"\1\61",
			"\1\63",
			"",
			"",
			"",
			"",
			"\1\65",
			"\1\66",
			"\1\67\15\uffff\1\70",
			"\1\71",
			"\1\72\15\uffff\1\73\5\uffff\1\74",
			"\1\75\7\uffff\1\76",
			"\1\100\5\uffff\1\77",
			"\1\101",
			"\1\102",
			"\1\103\11\uffff\1\104",
			"\1\105",
			"\1\106",
			"\1\107",
			"",
			"",
			"",
			"",
			"\1\111\1\uffff\12\46",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"\1\112",
			"\1\113",
			"\1\114",
			"\1\115",
			"\1\116",
			"\1\117",
			"\1\120",
			"\1\121",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\124",
			"\1\125",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\127",
			"\1\130",
			"\1\131\3\uffff\1\132",
			"\1\133",
			"\1\134",
			"\1\135",
			"",
			"",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\137",
			"\1\140",
			"\1\141",
			"\1\142",
			"\1\143",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\145",
			"",
			"",
			"\1\146",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"\1\147",
			"\1\150",
			"\1\151",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\154",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"\1\156",
			"\1\157",
			"\1\160",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\162",
			"",
			"\1\163",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\165",
			"\1\166",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"",
			"\1\170",
			"",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\173",
			"",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\1\175",
			"",
			"\1\176",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"",
			"\1\u0081",
			"",
			"\1\u0082",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			"",
			"\1\u0084",
			"\1\u0085",
			"",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"\12\45\7\uffff\32\45\4\uffff\1\45\1\uffff\32\45",
			"",
			""
	};

	static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
	static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
	static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
	static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
	static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
	static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
	static final short[][] DFA16_transition;

	static {
		int numStates = DFA16_transitionS.length;
		DFA16_transition = new short[numStates][];
		for (int i=0; i<numStates; i++) {
			DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
		}
	}

	protected class DFA16 extends DFA {

		public DFA16(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 16;
			this.eot = DFA16_eot;
			this.eof = DFA16_eof;
			this.min = DFA16_min;
			this.max = DFA16_max;
			this.accept = DFA16_accept;
			this.special = DFA16_special;
			this.transition = DFA16_transition;
		}
		@Override
		public String getDescription() {
			return "1:1: Tokens : ( T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | T__26 | T__27 | T__28 | T__29 | T__30 | T__31 | T__32 | T__33 | T__34 | T__35 | T__36 | T__37 | T__38 | T__39 | T__40 | T__41 | T__42 | T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | FUNCTION | NOT | ID | INT | FLOAT | COMMENT | WS | STRING | CHAR );";
		}
	}

}
