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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("all")
public class lang2Parser extends AbstractParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CHAR", "COMMENT", "ESC_SEQ", 
		"EXPONENT", "FLOAT", "FUNCTION", "HEX_DIGIT", "ID", "INT", "NOT", "OCTAL_ESC", 
		"STRING", "UNICODE_ESC", "WS", "'!='", "'%'", "'&'", "'('", "')'", "'*'", 
		"'+'", "','", "'-'", "'.'", "'/'", "':'", "';'", "'<'", "'<='", "'<>'", 
		"'='", "'=='", "'>'", "'>='", "'?'", "'['", "']'", "'^'", "'and'", "'break'", 
		"'catch'", "'continue'", "'else'", "'false'", "'for'", "'if'", "'in'", 
		"'null'", "'or'", "'return'", "'throw'", "'true'", "'try'", "'var'", "'while'", 
		"'xor'", "'{'", "'|'", "'}'"
	};
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
	public AbstractParser[] getDelegates() {
		return new AbstractParser[] {};
	}

	// delegators


	public lang2Parser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public lang2Parser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		this.state.ruleMemo = new HashMap[82+1];


	}

	@Override public String[] getTokenNames() { return lang2Parser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g"; }


	protected static class expressions_scope {
		java.util.ArrayList<Value> body;
		Boolean _finaled;
		Boolean _e2Exists;
	}
	protected Stack<expressions_scope> expressions_stack = new Stack<expressions_scope>();


	// $ANTLR start "expressions"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:21:1: expressions returns [Value value] : e1= expression ( ( ';' )? e2= expression )* (f= ';' )? ;
	public final Value expressions() throws RecognitionException {
		expressions_stack.push(new expressions_scope());
		Value value = null;

		int expressions_StartIndex = input.index();

		Token f=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;


				expressions_stack.peek().body = new java.util.ArrayList<Value>();
				expressions_stack.peek()._finaled = false;
				expressions_stack.peek()._e2Exists = false;
			
		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return value; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:34:2: (e1= expression ( ( ';' )? e2= expression )* (f= ';' )? )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:35:3: e1= expression ( ( ';' )? e2= expression )* (f= ';' )?
			{
			pushFollow(FOLLOW_expression_in_expressions68);
			e1=expression();
			state._fsp--;
			if (state.failed) return value;
			if ( state.backtracking==0 ) { expressions_stack.peek().body.add((e1!=null?((lang2Parser.expression_return)e1).value:null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:2: ( ( ';' )? e2= expression )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==30) ) {
					int LA2_1 = input.LA(2);
					if ( ((LA2_1 >= FLOAT && LA2_1 <= FUNCTION)||(LA2_1 >= ID && LA2_1 <= NOT)||LA2_1==STRING||LA2_1==21||LA2_1==26||LA2_1==39||LA2_1==43||LA2_1==45||(LA2_1 >= 47 && LA2_1 <= 49)||LA2_1==51||(LA2_1 >= 53 && LA2_1 <= 58)||LA2_1==60) ) {
						alt2=1;
					}

				}
				else if ( ((LA2_0 >= FLOAT && LA2_0 <= FUNCTION)||(LA2_0 >= ID && LA2_0 <= NOT)||LA2_0==STRING||LA2_0==21||LA2_0==26||LA2_0==39||LA2_0==43||LA2_0==45||(LA2_0 >= 47 && LA2_0 <= 49)||LA2_0==51||(LA2_0 >= 53 && LA2_0 <= 58)||LA2_0==60) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:4: ( ';' )? e2= expression
					{
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:4: ( ';' )?
					int alt1=2;
					int LA1_0 = input.LA(1);
					if ( (LA1_0==30) ) {
						alt1=1;
					}
					switch (alt1) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:37:4: ';'
							{
							match(input,30,FOLLOW_30_in_expressions78); if (state.failed) return value;
							}
							break;

					}

					pushFollow(FOLLOW_expression_in_expressions84);
					e2=expression();
					state._fsp--;
					if (state.failed) return value;
					if ( state.backtracking==0 ) { expressions_stack.peek().body.add((e2!=null?((lang2Parser.expression_return)e2).value:null)); }
					if ( state.backtracking==0 ) { expressions_stack.peek()._e2Exists = true; }
					}
					break;

				default :
					break loop2;
				}
			}

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:42:2: (f= ';' )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==30) ) {
				alt3=1;
			}
			switch (alt3) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:42:4: f= ';'
					{
					f=(Token)match(input,30,FOLLOW_30_in_expressions107); if (state.failed) return value;
					if ( state.backtracking==0 ) { expressions_stack.peek()._finaled = true; }
					}
					break;

			}

			if ( state.backtracking==0 ) { value = factory().Expressions( expressions_stack.peek().body ); }
			if ( state.backtracking==0 ) {
						if( expressions_stack.peek()._finaled ){
							if( expressions_stack.peek()._e2Exists ){
								location(value,(e1!=null?(e1.start):null),f);
							}else{
								location(value,(e1!=null?(e1.start):null),f);
							}
						}else{
							if( expressions_stack.peek()._e2Exists ){
								location(value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null));
							}else{
								location(value,(e1!=null?(e1.start):null));
							}
						}
					}
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 1, expressions_StartIndex); }

			expressions_stack.pop();
		}
		return value;
	}
	// $ANTLR end "expressions"


	public static class expression_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "expression"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:62:1: expression returns [Value value] : (vde= varDefine |wce= whileCycle |fce= forCycle |ae= assign |be= block |fe= flow );
	public final lang2Parser.expression_return expression() throws RecognitionException {
		lang2Parser.expression_return retval = new lang2Parser.expression_return();
		retval.start = input.LT(1);
		int expression_StartIndex = input.index();

		ParserRuleReturnScope vde =null;
		ParserRuleReturnScope wce =null;
		ParserRuleReturnScope fce =null;
		ParserRuleReturnScope ae =null;
		ParserRuleReturnScope be =null;
		ParserRuleReturnScope fe =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:63:2: (vde= varDefine |wce= whileCycle |fce= forCycle |ae= assign |be= block |fe= flow )
			int alt4=6;
			switch ( input.LA(1) ) {
			case 57:
				{
				alt4=1;
				}
				break;
			case 58:
				{
				alt4=2;
				}
				break;
			case 48:
				{
				alt4=3;
				}
				break;
			case FLOAT:
			case FUNCTION:
			case ID:
			case INT:
			case NOT:
			case STRING:
			case 21:
			case 26:
			case 39:
			case 47:
			case 51:
			case 55:
				{
				alt4=4;
				}
				break;
			case 60:
				{
				int LA4_5 = input.LA(2);
				if ( (LA4_5==ID) ) {
					int LA4_7 = input.LA(3);
					if ( (LA4_7==29) ) {
						alt4=4;
					}
					else if ( ((LA4_7 >= FLOAT && LA4_7 <= FUNCTION)||(LA4_7 >= ID && LA4_7 <= NOT)||LA4_7==STRING||(LA4_7 >= 18 && LA4_7 <= 21)||(LA4_7 >= 23 && LA4_7 <= 24)||(LA4_7 >= 26 && LA4_7 <= 28)||(LA4_7 >= 30 && LA4_7 <= 39)||(LA4_7 >= 41 && LA4_7 <= 43)||LA4_7==45||(LA4_7 >= 47 && LA4_7 <= 49)||(LA4_7 >= 51 && LA4_7 <= 62)) ) {
						alt4=5;
					}

					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						int nvaeMark = input.mark();
						try {
							for (int nvaeConsume = 0; nvaeConsume < 3 - 1; nvaeConsume++) {
								input.consume();
							}
							NoViableAltException nvae =
								new NoViableAltException("", 4, 7, input);
							throw nvae;
						} finally {
							input.rewind(nvaeMark);
						}
					}

				}
				else if ( ((LA4_5 >= FLOAT && LA4_5 <= FUNCTION)||(LA4_5 >= INT && LA4_5 <= NOT)||LA4_5==STRING||LA4_5==21||LA4_5==26||LA4_5==39||LA4_5==43||LA4_5==45||(LA4_5 >= 47 && LA4_5 <= 49)||LA4_5==51||(LA4_5 >= 53 && LA4_5 <= 58)||LA4_5==60) ) {
					alt4=5;
				}

				else {
					if (state.backtracking>0) {state.failed=true; return retval;}
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 5, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

				}
				break;
			case 43:
			case 45:
			case 49:
			case 53:
			case 54:
			case 56:
				{
				alt4=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}
			switch (alt4) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:63:4: vde= varDefine
					{
					pushFollow(FOLLOW_varDefine_in_expression139);
					vde=varDefine();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (vde!=null?((lang2Parser.varDefine_return)vde).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(vde!=null?(vde.start):null),(vde!=null?(vde.stop):null)); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:66:4: wce= whileCycle
					{
					pushFollow(FOLLOW_whileCycle_in_expression155);
					wce=whileCycle();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (wce!=null?((lang2Parser.whileCycle_return)wce).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(wce!=null?(wce.start):null),(wce!=null?(wce.stop):null)); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:69:4: fce= forCycle
					{
					pushFollow(FOLLOW_forCycle_in_expression171);
					fce=forCycle();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (fce!=null?((lang2Parser.forCycle_return)fce).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(fce!=null?(fce.start):null),(fce!=null?(fce.stop):null)); }
					}
					break;
				case 4 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:72:4: ae= assign
					{
					pushFollow(FOLLOW_assign_in_expression187);
					ae=assign();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (ae!=null?((lang2Parser.assign_return)ae).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(ae!=null?(ae.start):null),(ae!=null?(ae.stop):null)); }
					}
					break;
				case 5 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:75:4: be= block
					{
					pushFollow(FOLLOW_block_in_expression203);
					be=block();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (be!=null?((lang2Parser.block_return)be).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(be!=null?(be.start):null),(be!=null?(be.stop):null)); }
					}
					break;
				case 6 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:78:4: fe= flow
					{
					pushFollow(FOLLOW_flow_in_expression218);
					fe=flow();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (fe!=null?((lang2Parser.flow_return)fe).value:null); }
					if ( state.backtracking==0 ) { location(retval.value,(fe!=null?(fe.start):null),(fe!=null?(fe.stop):null)); }
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 2, expression_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class varDefine_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "varDefine"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:86:1: varDefine returns [Value value] : b= 'var' id= ID ( '=' e2= expression )? ;
	public final lang2Parser.varDefine_return varDefine() throws RecognitionException {
		lang2Parser.varDefine_return retval = new lang2Parser.varDefine_return();
		retval.start = input.LT(1);
		int varDefine_StartIndex = input.index();

		Token b=null;
		Token id=null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:87:2: (b= 'var' id= ID ( '=' e2= expression )? )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:87:4: b= 'var' id= ID ( '=' e2= expression )?
			{
			b=(Token)match(input,57,FOLLOW_57_in_varDefine247); if (state.failed) return retval;
			id=(Token)match(input,ID,FOLLOW_ID_in_varDefine251); if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().VarDefine((id!=null?id.getText():null)); }
			if ( state.backtracking==0 ) { location(retval.value,b,id); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:90:3: ( '=' e2= expression )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==34) ) {
				int LA5_1 = input.LA(2);
				if ( (synpred9_lang2()) ) {
					alt5=1;
				}
			}
			switch (alt5) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:90:5: '=' e2= expression
					{
					match(input,34,FOLLOW_34_in_varDefine266); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_varDefine270);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().VarDefine((id!=null?id.getText():null),(e2!=null?((lang2Parser.expression_return)e2).value:null)); }
					if ( state.backtracking==0 ) { location(retval.value,b,(e2!=null?(e2.stop):null)); }
					}
					break;

			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 3, varDefine_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "varDefine"


	public static class whileCycle_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "whileCycle"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:96:1: whileCycle returns [Value value] : b= 'while' '(' e1= expression ')' e2= expression ;
	public final lang2Parser.whileCycle_return whileCycle() throws RecognitionException {
		lang2Parser.whileCycle_return retval = new lang2Parser.whileCycle_return();
		retval.start = input.LT(1);
		int whileCycle_StartIndex = input.index();

		Token b=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:97:2: (b= 'while' '(' e1= expression ')' e2= expression )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:97:4: b= 'while' '(' e1= expression ')' e2= expression
			{
			b=(Token)match(input,58,FOLLOW_58_in_whileCycle305); if (state.failed) return retval;
			match(input,21,FOLLOW_21_in_whileCycle307); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_whileCycle311);
			e1=expression();
			state._fsp--;
			if (state.failed) return retval;
			match(input,22,FOLLOW_22_in_whileCycle313); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_whileCycle317);
			e2=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().While( (e1!=null?((lang2Parser.expression_return)e1).value:null), (e2!=null?((lang2Parser.expression_return)e2).value:null) ); }
			if ( state.backtracking==0 ) { location(retval.value,b,(e2!=null?(e2.stop):null)); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 4, whileCycle_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "whileCycle"


	public static class forCycle_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "forCycle"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:101:1: forCycle returns [Value value] : b= 'for' '(' var= ID 'in' src= expression ')' body= expression ;
	public final lang2Parser.forCycle_return forCycle() throws RecognitionException {
		lang2Parser.forCycle_return retval = new lang2Parser.forCycle_return();
		retval.start = input.LT(1);
		int forCycle_StartIndex = input.index();

		Token b=null;
		Token var=null;
		ParserRuleReturnScope src =null;
		ParserRuleReturnScope body =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:102:2: (b= 'for' '(' var= ID 'in' src= expression ')' body= expression )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:102:4: b= 'for' '(' var= ID 'in' src= expression ')' body= expression
			{
			b=(Token)match(input,48,FOLLOW_48_in_forCycle341); if (state.failed) return retval;
			match(input,21,FOLLOW_21_in_forCycle343); if (state.failed) return retval;
			var=(Token)match(input,ID,FOLLOW_ID_in_forCycle347); if (state.failed) return retval;
			match(input,50,FOLLOW_50_in_forCycle349); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_forCycle353);
			src=expression();
			state._fsp--;
			if (state.failed) return retval;
			match(input,22,FOLLOW_22_in_forCycle355); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_forCycle359);
			body=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().For( (var!=null?var.getText():null), (src!=null?((lang2Parser.expression_return)src).value:null), (body!=null?((lang2Parser.expression_return)body).value:null) ); }
			if ( state.backtracking==0 ) { location(retval.value, b, (body!=null?(body.stop):null)); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 5, forCycle_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "forCycle"


	public static class assign_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "assign"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:107:1: assign returns [Value value] : e1= or ( '=' e2= expression | '?' e2= expression ( ':' e3= expression )? )? ;
	public final lang2Parser.assign_return assign() throws RecognitionException {
		lang2Parser.assign_return retval = new lang2Parser.assign_return();
		retval.start = input.LT(1);
		int assign_StartIndex = input.index();

		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;
		ParserRuleReturnScope e3 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:108:2: (e1= or ( '=' e2= expression | '?' e2= expression ( ':' e3= expression )? )? )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:108:4: e1= or ( '=' e2= expression | '?' e2= expression ( ':' e3= expression )? )?
			{
			pushFollow(FOLLOW_or_in_assign385);
			e1=or();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = (e1!=null?((lang2Parser.or_return)e1).value:null); }
			if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e1!=null?(e1.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:111:3: ( '=' e2= expression | '?' e2= expression ( ':' e3= expression )? )?
			int alt7=3;
			int LA7_0 = input.LA(1);
			if ( (LA7_0==34) ) {
				int LA7_1 = input.LA(2);
				if ( (synpred10_lang2()) ) {
					alt7=1;
				}
			}
			else if ( (LA7_0==38) ) {
				int LA7_2 = input.LA(2);
				if ( (synpred12_lang2()) ) {
					alt7=2;
				}
			}
			switch (alt7) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:111:5: '=' e2= expression
					{
					match(input,34,FOLLOW_34_in_assign401); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_assign405);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Assign( retval.value, (e2!=null?((lang2Parser.expression_return)e2).value:null) );  }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:114:5: '?' e2= expression ( ':' e3= expression )?
					{
					match(input,38,FOLLOW_38_in_assign423); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_assign427);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().If((e1!=null?((lang2Parser.or_return)e1).value:null), (e2!=null?((lang2Parser.expression_return)e2).value:null)); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:4: ( ':' e3= expression )?
					int alt6=2;
					int LA6_0 = input.LA(1);
					if ( (LA6_0==29) ) {
						int LA6_1 = input.LA(2);
						if ( (synpred11_lang2()) ) {
							alt6=1;
						}
					}
					switch (alt6) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:6: ':' e3= expression
							{
							match(input,29,FOLLOW_29_in_assign446); if (state.failed) return retval;
							pushFollow(FOLLOW_expression_in_assign450);
							e3=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) { retval.value = factory().If((e1!=null?((lang2Parser.or_return)e1).value:null), (e2!=null?((lang2Parser.expression_return)e2).value:null), (e3!=null?((lang2Parser.expression_return)e3).value:null)); }
							if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e3!=null?(e3.stop):null)); }
							}
							break;

					}

					}
					break;

			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 6, assign_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "assign"


	public static class block_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "block"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:124:1: block returns [Value value] : b= '{' (e1= block |e2= expressions ) e= '}' ;
	public final lang2Parser.block_return block() throws RecognitionException {
		lang2Parser.block_return retval = new lang2Parser.block_return();
		retval.start = input.LT(1);
		int block_StartIndex = input.index();

		Token b=null;
		Token e=null;
		ParserRuleReturnScope e1 =null;
		Value e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:125:2: (b= '{' (e1= block |e2= expressions ) e= '}' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:125:4: b= '{' (e1= block |e2= expressions ) e= '}'
			{
			b=(Token)match(input,60,FOLLOW_60_in_block494); if (state.failed) return retval;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:126:2: (e1= block |e2= expressions )
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==60) ) {
				int LA8_1 = input.LA(2);
				if ( (synpred13_lang2()) ) {
					alt8=1;
				}
				else if ( (true) ) {
					alt8=2;
				}

			}
			else if ( ((LA8_0 >= FLOAT && LA8_0 <= FUNCTION)||(LA8_0 >= ID && LA8_0 <= NOT)||LA8_0==STRING||LA8_0==21||LA8_0==26||LA8_0==39||LA8_0==43||LA8_0==45||(LA8_0 >= 47 && LA8_0 <= 49)||LA8_0==51||(LA8_0 >= 53 && LA8_0 <= 58)) ) {
				alt8=2;
			}

			else {
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 8, 0, input);
				throw nvae;
			}

			switch (alt8) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:126:4: e1= block
					{
					pushFollow(FOLLOW_block_in_block502);
					e1=block();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Block( (e1!=null?((lang2Parser.block_return)e1).value:null) ); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:127:4: e2= expressions
					{
					pushFollow(FOLLOW_expressions_in_block511);
					e2=expressions();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Block( e2 ); }
					}
					break;

			}

			e=(Token)match(input,62,FOLLOW_62_in_block521); if (state.failed) return retval;
			if ( state.backtracking==0 ) { location(retval.value,b,e); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 7, block_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "block"


	public static class flow_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "flow"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:132:1: flow returns [Value value] : (if_= 'if' '(' e1= expression ')' e2= expression ( 'else' e3= expression )? |rb= 'return' (e1= expression )? |bb= 'break' (e1= expression )? |cb= 'continue' (e1= expression )? |th= 'throw' e1= expression |tc= 'try' e1= expression 'catch' '(' cvar= ID ')' e2= expression );
	public final lang2Parser.flow_return flow() throws RecognitionException {
		lang2Parser.flow_return retval = new lang2Parser.flow_return();
		retval.start = input.LT(1);
		int flow_StartIndex = input.index();

		Token if_=null;
		Token rb=null;
		Token bb=null;
		Token cb=null;
		Token th=null;
		Token tc=null;
		Token cvar=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;
		ParserRuleReturnScope e3 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:133:2: (if_= 'if' '(' e1= expression ')' e2= expression ( 'else' e3= expression )? |rb= 'return' (e1= expression )? |bb= 'break' (e1= expression )? |cb= 'continue' (e1= expression )? |th= 'throw' e1= expression |tc= 'try' e1= expression 'catch' '(' cvar= ID ')' e2= expression )
			int alt13=6;
			switch ( input.LA(1) ) {
			case 49:
				{
				alt13=1;
				}
				break;
			case 53:
				{
				alt13=2;
				}
				break;
			case 43:
				{
				alt13=3;
				}
				break;
			case 45:
				{
				alt13=4;
				}
				break;
			case 54:
				{
				alt13=5;
				}
				break;
			case 56:
				{
				alt13=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 13, 0, input);
				throw nvae;
			}
			switch (alt13) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:133:4: if_= 'if' '(' e1= expression ')' e2= expression ( 'else' e3= expression )?
					{
					if_=(Token)match(input,49,FOLLOW_49_in_flow541); if (state.failed) return retval;
					match(input,21,FOLLOW_21_in_flow543); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_flow547);
					e1=expression();
					state._fsp--;
					if (state.failed) return retval;
					match(input,22,FOLLOW_22_in_flow549); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_flow553);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().If((e1!=null?((lang2Parser.expression_return)e1).value:null), (e2!=null?((lang2Parser.expression_return)e2).value:null)); }
					if ( state.backtracking==0 ) { location(retval.value,if_,(e2!=null?(e2.stop):null)); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:136:4: ( 'else' e3= expression )?
					int alt9=2;
					int LA9_0 = input.LA(1);
					if ( (LA9_0==46) ) {
						int LA9_1 = input.LA(2);
						if ( (synpred14_lang2()) ) {
							alt9=1;
						}
					}
					switch (alt9) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:136:6: 'else' e3= expression
							{
							match(input,46,FOLLOW_46_in_flow571); if (state.failed) return retval;
							pushFollow(FOLLOW_expression_in_flow575);
							e3=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) { retval.value = factory().If((e1!=null?((lang2Parser.expression_return)e1).value:null), (e2!=null?((lang2Parser.expression_return)e2).value:null), (e3!=null?((lang2Parser.expression_return)e3).value:null)); }
							if ( state.backtracking==0 ) { location(retval.value,if_,(e3!=null?(e3.stop):null)); }
							}
							break;

					}

					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:140:4: rb= 'return' (e1= expression )?
					{
					rb=(Token)match(input,53,FOLLOW_53_in_flow603); if (state.failed) return retval;
					if ( state.backtracking==0 ) {retval.value = factory().Return(); }
					if ( state.backtracking==0 ) { location(retval.value,rb); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:143:3: (e1= expression )?
					int alt10=2;
					switch ( input.LA(1) ) {
						case 57:
							{
							int LA10_1 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 58:
							{
							int LA10_2 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 48:
							{
							int LA10_3 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 26:
							{
							int LA10_4 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 55:
							{
							int LA10_5 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 47:
							{
							int LA10_6 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 51:
							{
							int LA10_7 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case FLOAT:
							{
							int LA10_8 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case INT:
							{
							int LA10_9 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case STRING:
							{
							int LA10_10 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case ID:
							{
							int LA10_11 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case NOT:
							{
							int LA10_12 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 21:
							{
							int LA10_13 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case FUNCTION:
							{
							int LA10_14 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 60:
							{
							int LA10_15 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 39:
							{
							int LA10_16 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 49:
							{
							int LA10_17 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 53:
							{
							int LA10_18 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 43:
							{
							int LA10_19 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 45:
							{
							int LA10_20 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 54:
							{
							int LA10_21 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
						case 56:
							{
							int LA10_22 = input.LA(2);
							if ( (synpred16_lang2()) ) {
								alt10=1;
							}
							}
							break;
					}
					switch (alt10) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:143:5: e1= expression
							{
							pushFollow(FOLLOW_expression_in_flow621);
							e1=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) {retval.value = factory().Return((e1!=null?((lang2Parser.expression_return)e1).value:null)); }
							if ( state.backtracking==0 ) { location(retval.value,rb,(e1!=null?(e1.stop):null)); }
							}
							break;

					}

					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:147:4: bb= 'break' (e1= expression )?
					{
					bb=(Token)match(input,43,FOLLOW_43_in_flow646); if (state.failed) return retval;
					if ( state.backtracking==0 ) {retval.value = factory().Break(); }
					if ( state.backtracking==0 ) { location(retval.value,bb); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:150:3: (e1= expression )?
					int alt11=2;
					switch ( input.LA(1) ) {
						case 57:
							{
							int LA11_1 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 58:
							{
							int LA11_2 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 48:
							{
							int LA11_3 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 26:
							{
							int LA11_4 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 55:
							{
							int LA11_5 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 47:
							{
							int LA11_6 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 51:
							{
							int LA11_7 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case FLOAT:
							{
							int LA11_8 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case INT:
							{
							int LA11_9 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case STRING:
							{
							int LA11_10 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case ID:
							{
							int LA11_11 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case NOT:
							{
							int LA11_12 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 21:
							{
							int LA11_13 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case FUNCTION:
							{
							int LA11_14 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 60:
							{
							int LA11_15 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 39:
							{
							int LA11_16 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 49:
							{
							int LA11_17 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 53:
							{
							int LA11_18 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 43:
							{
							int LA11_19 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 45:
							{
							int LA11_20 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 54:
							{
							int LA11_21 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
						case 56:
							{
							int LA11_22 = input.LA(2);
							if ( (synpred18_lang2()) ) {
								alt11=1;
							}
							}
							break;
					}
					switch (alt11) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:150:5: e1= expression
							{
							pushFollow(FOLLOW_expression_in_flow664);
							e1=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) {retval.value = factory().Break((e1!=null?((lang2Parser.expression_return)e1).value:null)); }
							if ( state.backtracking==0 ) { location(retval.value,bb,(e1!=null?(e1.stop):null)); }
							}
							break;

					}

					}
					break;
				case 4 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:153:4: cb= 'continue' (e1= expression )?
					{
					cb=(Token)match(input,45,FOLLOW_45_in_flow685); if (state.failed) return retval;
					if ( state.backtracking==0 ) {retval.value = factory().Continue(); }
					if ( state.backtracking==0 ) { location(retval.value,cb); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:156:3: (e1= expression )?
					int alt12=2;
					switch ( input.LA(1) ) {
						case 57:
							{
							int LA12_1 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 58:
							{
							int LA12_2 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 48:
							{
							int LA12_3 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 26:
							{
							int LA12_4 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 55:
							{
							int LA12_5 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 47:
							{
							int LA12_6 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 51:
							{
							int LA12_7 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case FLOAT:
							{
							int LA12_8 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case INT:
							{
							int LA12_9 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case STRING:
							{
							int LA12_10 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case ID:
							{
							int LA12_11 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case NOT:
							{
							int LA12_12 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 21:
							{
							int LA12_13 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case FUNCTION:
							{
							int LA12_14 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 60:
							{
							int LA12_15 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 39:
							{
							int LA12_16 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 49:
							{
							int LA12_17 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 53:
							{
							int LA12_18 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 43:
							{
							int LA12_19 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 45:
							{
							int LA12_20 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 54:
							{
							int LA12_21 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
						case 56:
							{
							int LA12_22 = input.LA(2);
							if ( (synpred20_lang2()) ) {
								alt12=1;
							}
							}
							break;
					}
					switch (alt12) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:156:5: e1= expression
							{
							pushFollow(FOLLOW_expression_in_flow703);
							e1=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) { retval.value = factory().Continue((e1!=null?((lang2Parser.expression_return)e1).value:null)); }
							if ( state.backtracking==0 ) { location(retval.value,cb,(e1!=null?(e1.stop):null)); }
							}
							break;

					}

					}
					break;
				case 5 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:160:4: th= 'throw' e1= expression
					{
					th=(Token)match(input,54,FOLLOW_54_in_flow728); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_flow732);
					e1=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Throw( (e1!=null?((lang2Parser.expression_return)e1).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value, th, (e1!=null?(e1.stop):null)); }
					}
					break;
				case 6 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:163:4: tc= 'try' e1= expression 'catch' '(' cvar= ID ')' e2= expression
					{
					tc=(Token)match(input,56,FOLLOW_56_in_flow747); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_flow751);
					e1=expression();
					state._fsp--;
					if (state.failed) return retval;
					match(input,44,FOLLOW_44_in_flow755); if (state.failed) return retval;
					match(input,21,FOLLOW_21_in_flow757); if (state.failed) return retval;
					cvar=(Token)match(input,ID,FOLLOW_ID_in_flow761); if (state.failed) return retval;
					match(input,22,FOLLOW_22_in_flow763); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_flow767);
					e2=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().TryCatch((e1!=null?((lang2Parser.expression_return)e1).value:null),(cvar!=null?cvar.getText():null),(e2!=null?((lang2Parser.expression_return)e2).value:null)); }
					if ( state.backtracking==0 ) { location(retval.value, tc, (e2!=null?(e2.stop):null)); }
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 8, flow_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "flow"


	public static class or_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "or"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:183:1: or returns [Value value] : e1= xor ( ( '|' | 'or' ) e2= xor )* ;
	public final lang2Parser.or_return or() throws RecognitionException {
		lang2Parser.or_return retval = new lang2Parser.or_return();
		retval.start = input.LT(1);
		int or_StartIndex = input.index();

		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:184:2: (e1= xor ( ( '|' | 'or' ) e2= xor )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:184:4: e1= xor ( ( '|' | 'or' ) e2= xor )*
			{
			pushFollow(FOLLOW_xor_in_or798);
			e1=xor();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) {retval.value = (e1!=null?((lang2Parser.xor_return)e1).value:null);}
			if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e1!=null?(e1.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:186:3: ( ( '|' | 'or' ) e2= xor )*
			loop14:
			while (true) {
				int alt14=2;
				int LA14_0 = input.LA(1);
				if ( (LA14_0==52||LA14_0==61) ) {
					int LA14_2 = input.LA(2);
					if ( (synpred24_lang2()) ) {
						alt14=1;
					}

				}

				switch (alt14) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:186:5: ( '|' | 'or' ) e2= xor
					{
					if ( input.LA(1)==52||input.LA(1)==61 ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_xor_in_or820);
					e2=xor();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Or( retval.value, (e2!=null?((lang2Parser.xor_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;

				default :
					break loop14;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 9, or_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "or"


	public static class xor_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "xor"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:191:1: xor returns [Value value] : e1= and ( ( '^' | 'xor' ) e2= and )* ;
	public final lang2Parser.xor_return xor() throws RecognitionException {
		lang2Parser.xor_return retval = new lang2Parser.xor_return();
		retval.start = input.LT(1);
		int xor_StartIndex = input.index();

		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:192:2: (e1= and ( ( '^' | 'xor' ) e2= and )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:192:4: e1= and ( ( '^' | 'xor' ) e2= and )*
			{
			pushFollow(FOLLOW_and_in_xor852);
			e1=and();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) {retval.value = (e1!=null?((lang2Parser.and_return)e1).value:null);}
			if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e1!=null?(e1.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:194:3: ( ( '^' | 'xor' ) e2= and )*
			loop15:
			while (true) {
				int alt15=2;
				int LA15_0 = input.LA(1);
				if ( (LA15_0==41||LA15_0==59) ) {
					int LA15_2 = input.LA(2);
					if ( (synpred26_lang2()) ) {
						alt15=1;
					}

				}

				switch (alt15) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:194:5: ( '^' | 'xor' ) e2= and
					{
					if ( input.LA(1)==41||input.LA(1)==59 ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_and_in_xor876);
					e2=and();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Xor( retval.value, (e2!=null?((lang2Parser.and_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;

				default :
					break loop15;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 10, xor_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "xor"


	public static class and_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "and"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:199:1: and returns [Value value] : e1= compare ( ( '&' | 'and' ) e2= compare )* ;
	public final lang2Parser.and_return and() throws RecognitionException {
		lang2Parser.and_return retval = new lang2Parser.and_return();
		retval.start = input.LT(1);
		int and_StartIndex = input.index();

		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:200:2: (e1= compare ( ( '&' | 'and' ) e2= compare )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:200:4: e1= compare ( ( '&' | 'and' ) e2= compare )*
			{
			pushFollow(FOLLOW_compare_in_and908);
			e1=compare();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) {retval.value = (e1!=null?((lang2Parser.compare_return)e1).value:null);}
			if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e1!=null?(e1.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:202:3: ( ( '&' | 'and' ) e2= compare )*
			loop16:
			while (true) {
				int alt16=2;
				int LA16_0 = input.LA(1);
				if ( (LA16_0==20||LA16_0==42) ) {
					int LA16_2 = input.LA(2);
					if ( (synpred28_lang2()) ) {
						alt16=1;
					}

				}

				switch (alt16) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:202:5: ( '&' | 'and' ) e2= compare
					{
					if ( input.LA(1)==20||input.LA(1)==42 ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_compare_in_and932);
					e2=compare();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().And( retval.value, (e2!=null?((lang2Parser.compare_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;

				default :
					break loop16;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 11, and_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "and"


	public static class compare_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "compare"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:207:1: compare returns [Value value] : e1= addition ( ( '<>' | '!=' ) e2= addition | '<=' e2= addition | '>=' e2= addition | '==' e2= addition | '<' e2= addition | '>' e2= addition )* ;
	public final lang2Parser.compare_return compare() throws RecognitionException {
		lang2Parser.compare_return retval = new lang2Parser.compare_return();
		retval.start = input.LT(1);
		int compare_StartIndex = input.index();

		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:208:2: (e1= addition ( ( '<>' | '!=' ) e2= addition | '<=' e2= addition | '>=' e2= addition | '==' e2= addition | '<' e2= addition | '>' e2= addition )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:208:4: e1= addition ( ( '<>' | '!=' ) e2= addition | '<=' e2= addition | '>=' e2= addition | '==' e2= addition | '<' e2= addition | '>' e2= addition )*
			{
			pushFollow(FOLLOW_addition_in_compare963);
			e1=addition();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) {retval.value = (e1!=null?((lang2Parser.addition_return)e1).value:null); location(retval.value, (e1!=null?(e1.start):null), (e1!=null?(e1.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:209:3: ( ( '<>' | '!=' ) e2= addition | '<=' e2= addition | '>=' e2= addition | '==' e2= addition | '<' e2= addition | '>' e2= addition )*
			loop17:
			while (true) {
				int alt17=7;
				switch ( input.LA(1) ) {
				case 18:
				case 33:
					{
					int LA17_2 = input.LA(2);
					if ( (synpred30_lang2()) ) {
						alt17=1;
					}

					}
					break;
				case 32:
					{
					int LA17_3 = input.LA(2);
					if ( (synpred31_lang2()) ) {
						alt17=2;
					}

					}
					break;
				case 37:
					{
					int LA17_4 = input.LA(2);
					if ( (synpred32_lang2()) ) {
						alt17=3;
					}

					}
					break;
				case 35:
					{
					int LA17_5 = input.LA(2);
					if ( (synpred33_lang2()) ) {
						alt17=4;
					}

					}
					break;
				case 31:
					{
					int LA17_6 = input.LA(2);
					if ( (synpred34_lang2()) ) {
						alt17=5;
					}

					}
					break;
				case 36:
					{
					int LA17_7 = input.LA(2);
					if ( (synpred35_lang2()) ) {
						alt17=6;
					}

					}
					break;
				}
				switch (alt17) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:209:5: ( '<>' | '!=' ) e2= addition
					{
					if ( input.LA(1)==18||input.LA(1)==33 ) {
						input.consume();
						state.errorRecovery=false;
						state.failed=false;
					}
					else {
						if (state.backtracking>0) {state.failed=true; return retval;}
						MismatchedSetException mse = new MismatchedSetException(null,input);
						throw mse;
					}
					pushFollow(FOLLOW_addition_in_compare981);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareNotEquals( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:211:5: '<=' e2= addition
					{
					match(input,32,FOLLOW_32_in_compare994); if (state.failed) return retval;
					pushFollow(FOLLOW_addition_in_compare1000);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareLessOrEquals( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:213:5: '>=' e2= addition
					{
					match(input,37,FOLLOW_37_in_compare1013); if (state.failed) return retval;
					pushFollow(FOLLOW_addition_in_compare1019);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareMoreOrEquals( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 4 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:215:5: '==' e2= addition
					{
					match(input,35,FOLLOW_35_in_compare1032); if (state.failed) return retval;
					pushFollow(FOLLOW_addition_in_compare1038);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareEquals( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 5 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:217:5: '<' e2= addition
					{
					match(input,31,FOLLOW_31_in_compare1051); if (state.failed) return retval;
					pushFollow(FOLLOW_addition_in_compare1057);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareLess( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 6 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:219:5: '>' e2= addition
					{
					match(input,36,FOLLOW_36_in_compare1070); if (state.failed) return retval;
					pushFollow(FOLLOW_addition_in_compare1076);
					e2=addition();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().CompareMore( retval.value, (e2!=null?((lang2Parser.addition_return)e2).value:null) ); }
					if ( state.backtracking==0 ) { location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;

				default :
					break loop17;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 12, compare_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "compare"


	protected static class addition_scope {
		Boolean unariyMinus;
	}
	protected Stack<addition_scope> addition_stack = new Stack<addition_scope>();

	public static class addition_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "addition"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:224:1: addition returns [Value value] : (um= '-' )? e1= multiple ( '+' e2= multiple | '-' e2= multiple )* ;
	public final lang2Parser.addition_return addition() throws RecognitionException {
		addition_stack.push(new addition_scope());
		lang2Parser.addition_return retval = new lang2Parser.addition_return();
		retval.start = input.LT(1);
		int addition_StartIndex = input.index();

		Token um=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;


				addition_stack.peek().unariyMinus = false;
			
		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:233:2: ( (um= '-' )? e1= multiple ( '+' e2= multiple | '-' e2= multiple )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:234:2: (um= '-' )? e1= multiple ( '+' e2= multiple | '-' e2= multiple )*
			{
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:234:2: (um= '-' )?
			int alt18=2;
			int LA18_0 = input.LA(1);
			if ( (LA18_0==26) ) {
				alt18=1;
			}
			switch (alt18) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:234:4: um= '-'
					{
					um=(Token)match(input,26,FOLLOW_26_in_addition1126); if (state.failed) return retval;
					if ( state.backtracking==0 ) { addition_stack.peek().unariyMinus = true; }
					}
					break;

			}

			pushFollow(FOLLOW_multiple_in_addition1139);
			e1=multiple();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { 	retval.value = (e1!=null?((lang2Parser.multiple_return)e1).value:null);
						if( addition_stack.peek().unariyMinus ) retval.value = factory().UnaryMinus( retval.value );
						if( addition_stack.peek().unariyMinus ) {
							location( retval.value, um, (e1!=null?(e1.stop):null) );
						} else {
							location( retval.value, (e1!=null?(e1.start):null), (e1!=null?(e1.stop):null) ); 
						}
					}
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:244:2: ( '+' e2= multiple | '-' e2= multiple )*
			loop19:
			while (true) {
				int alt19=3;
				int LA19_0 = input.LA(1);
				if ( (LA19_0==26) ) {
					int LA19_2 = input.LA(2);
					if ( (synpred38_lang2()) ) {
						alt19=2;
					}

				}
				else if ( (LA19_0==24) ) {
					int LA19_3 = input.LA(2);
					if ( (synpred37_lang2()) ) {
						alt19=1;
					}

				}

				switch (alt19) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:244:4: '+' e2= multiple
					{
					match(input,24,FOLLOW_24_in_addition1149); if (state.failed) return retval;
					pushFollow(FOLLOW_multiple_in_addition1155);
					e2=multiple();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Add( retval.value, (e2!=null?((lang2Parser.multiple_return)e2).value:null) ); }
					if ( state.backtracking==0 ) {
								if( addition_stack.peek().unariyMinus ) {
									location( retval.value, um, (e2!=null?(e2.stop):null) );
								} else {
									location( retval.value, (e1!=null?(e1.start):null), (e2!=null?(e2.stop):null) ); 
								}
							}
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:252:4: '-' e2= multiple
					{
					match(input,26,FOLLOW_26_in_addition1166); if (state.failed) return retval;
					pushFollow(FOLLOW_multiple_in_addition1172);
					e2=multiple();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Substract( retval.value, (e2!=null?((lang2Parser.multiple_return)e2).value:null) ); }
					if ( state.backtracking==0 ) {
								if( addition_stack.peek().unariyMinus ) {
									location( retval.value, um, (e2!=null?(e2.stop):null) );
								} else {
									location( retval.value, (e1!=null?(e1.start):null), (e2!=null?(e2.stop):null) ); 
								}
							}
					}
					break;

				default :
					break loop19;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 13, addition_StartIndex); }

			addition_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "addition"


	public static class multiple_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "multiple"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:263:1: multiple returns [Value value] : e1= postfix (o= '*' e2= postfix |o= '/' e2= postfix |o= '%' e2= postfix )* ;
	public final lang2Parser.multiple_return multiple() throws RecognitionException {
		lang2Parser.multiple_return retval = new lang2Parser.multiple_return();
		retval.start = input.LT(1);
		int multiple_StartIndex = input.index();

		Token o=null;
		ParserRuleReturnScope e1 =null;
		ParserRuleReturnScope e2 =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:264:2: (e1= postfix (o= '*' e2= postfix |o= '/' e2= postfix |o= '%' e2= postfix )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:264:4: e1= postfix (o= '*' e2= postfix |o= '/' e2= postfix |o= '%' e2= postfix )*
			{
			pushFollow(FOLLOW_postfix_in_multiple1201);
			e1=postfix();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = (e1!=null?((lang2Parser.postfix_return)e1).value:null); location(retval.value,(e1!=null?(e1.start):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:265:3: (o= '*' e2= postfix |o= '/' e2= postfix |o= '%' e2= postfix )*
			loop20:
			while (true) {
				int alt20=4;
				switch ( input.LA(1) ) {
				case 23:
					{
					int LA20_2 = input.LA(2);
					if ( (synpred39_lang2()) ) {
						alt20=1;
					}

					}
					break;
				case 28:
					{
					int LA20_3 = input.LA(2);
					if ( (synpred40_lang2()) ) {
						alt20=2;
					}

					}
					break;
				case 19:
					{
					int LA20_4 = input.LA(2);
					if ( (synpred41_lang2()) ) {
						alt20=3;
					}

					}
					break;
				}
				switch (alt20) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:265:5: o= '*' e2= postfix
					{
					o=(Token)match(input,23,FOLLOW_23_in_multiple1211); if (state.failed) return retval;
					pushFollow(FOLLOW_postfix_in_multiple1217);
					e2=postfix();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Multiple( retval.value, (e2!=null?((lang2Parser.postfix_return)e2).value:null) ); location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:266:5: o= '/' e2= postfix
					{
					o=(Token)match(input,28,FOLLOW_28_in_multiple1227); if (state.failed) return retval;
					pushFollow(FOLLOW_postfix_in_multiple1233);
					e2=postfix();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Divide( retval.value, (e2!=null?((lang2Parser.postfix_return)e2).value:null) ); location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:267:5: o= '%' e2= postfix
					{
					o=(Token)match(input,19,FOLLOW_19_in_multiple1243); if (state.failed) return retval;
					pushFollow(FOLLOW_postfix_in_multiple1249);
					e2=postfix();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Mod( retval.value, (e2!=null?((lang2Parser.postfix_return)e2).value:null) ); location(retval.value,(e1!=null?(e1.start):null),(e2!=null?(e2.stop):null)); }
					}
					break;

				default :
					break loop20;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 14, multiple_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "multiple"


	protected static class postfix_scope {
		java.util.ArrayList<Value> arguments;
	}
	protected Stack<postfix_scope> postfix_stack = new Stack<postfix_scope>();

	public static class postfix_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "postfix"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:271:1: postfix returns [Value value] : ve= value (fb= '.' indexID= ID |ab= '[' indexExpr= expression ae= ']' |cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')' )* ;
	public final lang2Parser.postfix_return postfix() throws RecognitionException {
		postfix_stack.push(new postfix_scope());
		lang2Parser.postfix_return retval = new lang2Parser.postfix_return();
		retval.start = input.LT(1);
		int postfix_StartIndex = input.index();

		Token fb=null;
		Token indexID=null;
		Token ab=null;
		Token ae=null;
		Token cb=null;
		Token ce=null;
		ParserRuleReturnScope ve =null;
		ParserRuleReturnScope indexExpr =null;
		ParserRuleReturnScope arg =null;
		ParserRuleReturnScope arg2 =null;


				postfix_stack.peek().arguments = new java.util.ArrayList<Value>();
			
		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:278:2: (ve= value (fb= '.' indexID= ID |ab= '[' indexExpr= expression ae= ']' |cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')' )* )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:278:4: ve= value (fb= '.' indexID= ID |ab= '[' indexExpr= expression ae= ']' |cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')' )*
			{
			pushFollow(FOLLOW_value_in_postfix1283);
			ve=value();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = (ve!=null?((lang2Parser.value_return)ve).value:null); location(retval.value,(ve!=null?(ve.start):null),(ve!=null?(ve.stop):null)); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:279:3: (fb= '.' indexID= ID |ab= '[' indexExpr= expression ae= ']' |cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')' )*
			loop23:
			while (true) {
				int alt23=4;
				switch ( input.LA(1) ) {
				case 21:
					{
					int LA23_2 = input.LA(2);
					if ( (synpred46_lang2()) ) {
						alt23=3;
					}

					}
					break;
				case 39:
					{
					int LA23_3 = input.LA(2);
					if ( (synpred43_lang2()) ) {
						alt23=2;
					}

					}
					break;
				case 27:
					{
					int LA23_4 = input.LA(2);
					if ( (synpred42_lang2()) ) {
						alt23=1;
					}

					}
					break;
				}
				switch (alt23) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:279:5: fb= '.' indexID= ID
					{
					fb=(Token)match(input,27,FOLLOW_27_in_postfix1293); if (state.failed) return retval;
					indexID=(Token)match(input,ID,FOLLOW_ID_in_postfix1299); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().FieldIndex( retval.value, (indexID!=null?indexID.getText():null) ); location(retval.value,(ve!=null?(ve.start):null),indexID); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:280:5: ab= '[' indexExpr= expression ae= ']'
					{
					ab=(Token)match(input,39,FOLLOW_39_in_postfix1309); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_postfix1315);
					indexExpr=expression();
					state._fsp--;
					if (state.failed) return retval;
					ae=(Token)match(input,40,FOLLOW_40_in_postfix1319); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().ArrayIndex( retval.value, (indexExpr!=null?((lang2Parser.expression_return)indexExpr).value:null) ); location( retval.value, (ve!=null?(ve.start):null), ae ); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:282:5: cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')'
					{
					cb=(Token)match(input,21,FOLLOW_21_in_postfix1334); if (state.failed) return retval;
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:283:4: (arg= expression ( ',' arg2= expression )* )?
					int alt22=2;
					int LA22_0 = input.LA(1);
					if ( ((LA22_0 >= FLOAT && LA22_0 <= FUNCTION)||(LA22_0 >= ID && LA22_0 <= NOT)||LA22_0==STRING||LA22_0==21||LA22_0==26||LA22_0==39||LA22_0==43||LA22_0==45||(LA22_0 >= 47 && LA22_0 <= 49)||LA22_0==51||(LA22_0 >= 53 && LA22_0 <= 58)||LA22_0==60) ) {
						alt22=1;
					}
					switch (alt22) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:283:6: arg= expression ( ',' arg2= expression )*
							{
							pushFollow(FOLLOW_expression_in_postfix1343);
							arg=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) { postfix_stack.peek().arguments.add((arg!=null?((lang2Parser.expression_return)arg).value:null)); }
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:284:5: ( ',' arg2= expression )*
							loop21:
							while (true) {
								int alt21=2;
								int LA21_0 = input.LA(1);
								if ( (LA21_0==25) ) {
									alt21=1;
								}

								switch (alt21) {
								case 1 :
									// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:284:7: ',' arg2= expression
									{
									match(input,25,FOLLOW_25_in_postfix1353); if (state.failed) return retval;
									pushFollow(FOLLOW_expression_in_postfix1357);
									arg2=expression();
									state._fsp--;
									if (state.failed) return retval;
									if ( state.backtracking==0 ) { postfix_stack.peek().arguments.add((arg2!=null?((lang2Parser.expression_return)arg2).value:null)); }
									}
									break;

								default :
									break loop21;
								}
							}

							}
							break;

					}

					ce=(Token)match(input,22,FOLLOW_22_in_postfix1379); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Call(retval.value,postfix_stack.peek().arguments); location( retval.value,(ve!=null?(ve.start):null),ce); }
					}
					break;

				default :
					break loop23;
				}
			}

			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 15, postfix_StartIndex); }

			postfix_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "postfix"


	public static class value_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "value"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:291:1: value returns [Value value] : (cexp= constPrimitive |id= ID |n= NOT ne= expression |b= '(' de= expression e= ')' |fe= functionDefine |oe= objectDefine |ae= arrayDefine );
	public final lang2Parser.value_return value() throws RecognitionException {
		lang2Parser.value_return retval = new lang2Parser.value_return();
		retval.start = input.LT(1);
		int value_StartIndex = input.index();

		Token id=null;
		Token n=null;
		Token b=null;
		Token e=null;
		ParserRuleReturnScope cexp =null;
		ParserRuleReturnScope ne =null;
		ParserRuleReturnScope de =null;
		ParserRuleReturnScope fe =null;
		ParserRuleReturnScope oe =null;
		ParserRuleReturnScope ae =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:292:2: (cexp= constPrimitive |id= ID |n= NOT ne= expression |b= '(' de= expression e= ')' |fe= functionDefine |oe= objectDefine |ae= arrayDefine )
			int alt24=7;
			switch ( input.LA(1) ) {
			case FLOAT:
			case INT:
			case STRING:
			case 47:
			case 51:
			case 55:
				{
				alt24=1;
				}
				break;
			case ID:
				{
				alt24=2;
				}
				break;
			case NOT:
				{
				alt24=3;
				}
				break;
			case 21:
				{
				alt24=4;
				}
				break;
			case FUNCTION:
				{
				alt24=5;
				}
				break;
			case 60:
				{
				alt24=6;
				}
				break;
			case 39:
				{
				alt24=7;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 24, 0, input);
				throw nvae;
			}
			switch (alt24) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:292:4: cexp= constPrimitive
					{
					pushFollow(FOLLOW_constPrimitive_in_value1403);
					cexp=constPrimitive();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (cexp!=null?((lang2Parser.constPrimitive_return)cexp).value:null); location( retval.value, (cexp!=null?(cexp.start):null), (cexp!=null?(cexp.stop):null) ); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:294:4: id= ID
					{
					id=(Token)match(input,ID,FOLLOW_ID_in_value1415); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Variable((id!=null?id.getText():null)); location( retval.value, id ); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:295:4: n= NOT ne= expression
					{
					n=(Token)match(input,NOT,FOLLOW_NOT_in_value1424); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_value1428);
					ne=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Not( (ne!=null?((lang2Parser.expression_return)ne).value:null) ); location( retval.value, n, (ne!=null?(ne.stop):null) ); }
					}
					break;
				case 4 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:296:4: b= '(' de= expression e= ')'
					{
					b=(Token)match(input,21,FOLLOW_21_in_value1437); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_value1441);
					de=expression();
					state._fsp--;
					if (state.failed) return retval;
					e=(Token)match(input,22,FOLLOW_22_in_value1445); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Delegate( (de!=null?((lang2Parser.expression_return)de).value:null)); location( retval.value, b, e ); }
					}
					break;
				case 5 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:297:4: fe= functionDefine
					{
					pushFollow(FOLLOW_functionDefine_in_value1454);
					fe=functionDefine();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (fe!=null?((lang2Parser.functionDefine_return)fe).value:null); location(retval.value,(fe!=null?(fe.start):null),(fe!=null?(fe.stop):null)); }
					}
					break;
				case 6 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:298:4: oe= objectDefine
					{
					pushFollow(FOLLOW_objectDefine_in_value1463);
					oe=objectDefine();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (oe!=null?((lang2Parser.objectDefine_return)oe).value:null); location(retval.value,(oe!=null?(oe.start):null),(oe!=null?(oe.stop):null)); }
					}
					break;
				case 7 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:299:4: ae= arrayDefine
					{
					pushFollow(FOLLOW_arrayDefine_in_value1472);
					ae=arrayDefine();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = (ae!=null?((lang2Parser.arrayDefine_return)ae).value:null); location(retval.value,(ae!=null?(ae.start):null),(ae!=null?(ae.stop):null)); }
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 16, value_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "value"


	public static class constPrimitive_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "constPrimitive"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:302:1: constPrimitive returns [Value value] : ( 'true' | 'false' | 'null' |floatv= FLOAT |intv= INT |str= STRING );
	public final lang2Parser.constPrimitive_return constPrimitive() throws RecognitionException {
		lang2Parser.constPrimitive_return retval = new lang2Parser.constPrimitive_return();
		retval.start = input.LT(1);
		int constPrimitive_StartIndex = input.index();

		Token floatv=null;
		Token intv=null;
		Token str=null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:303:2: ( 'true' | 'false' | 'null' |floatv= FLOAT |intv= INT |str= STRING )
			int alt25=6;
			switch ( input.LA(1) ) {
			case 55:
				{
				alt25=1;
				}
				break;
			case 47:
				{
				alt25=2;
				}
				break;
			case 51:
				{
				alt25=3;
				}
				break;
			case FLOAT:
				{
				alt25=4;
				}
				break;
			case INT:
				{
				alt25=5;
				}
				break;
			case STRING:
				{
				alt25=6;
				}
				break;
			default:
				if (state.backtracking>0) {state.failed=true; return retval;}
				NoViableAltException nvae =
					new NoViableAltException("", 25, 0, input);
				throw nvae;
			}
			switch (alt25) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:303:4: 'true'
					{
					match(input,55,FOLLOW_55_in_constPrimitive1490); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( new Boolean(true) ); }
					}
					break;
				case 2 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:304:4: 'false'
					{
					match(input,47,FOLLOW_47_in_constPrimitive1497); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( new Boolean(false) ); }
					}
					break;
				case 3 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:305:4: 'null'
					{
					match(input,51,FOLLOW_51_in_constPrimitive1504); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( null ); }
					}
					break;
				case 4 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:306:4: floatv= FLOAT
					{
					floatv=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_constPrimitive1513); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( new Double((floatv!=null?floatv.getText():null)) ); }
					}
					break;
				case 5 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:307:4: intv= INT
					{
					intv=(Token)match(input,INT,FOLLOW_INT_in_constPrimitive1522); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( new Integer((intv!=null?intv.getText():null)) ); }
					}
					break;
				case 6 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:308:4: str= STRING
					{
					str=(Token)match(input,STRING,FOLLOW_STRING_in_constPrimitive1531); if (state.failed) return retval;
					if ( state.backtracking==0 ) { retval.value = factory().Const( AbstractParser.parseStringConst((str!=null?str.getText():null)) ); }
					}
					break;

			}
			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 17, constPrimitive_StartIndex); }

		}
		return retval;
	}
	// $ANTLR end "constPrimitive"


	protected static class functionDefine_scope {
		java.util.List<String> args;
	}
	protected Stack<functionDefine_scope> functionDefine_stack = new Stack<functionDefine_scope>();

	public static class functionDefine_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "functionDefine"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:311:1: functionDefine returns [Value value] : b= FUNCTION '(' (id= ID ( ',' id= ID )* )? ')' exp= expression ;
	public final lang2Parser.functionDefine_return functionDefine() throws RecognitionException {
		functionDefine_stack.push(new functionDefine_scope());
		lang2Parser.functionDefine_return retval = new lang2Parser.functionDefine_return();
		retval.start = input.LT(1);
		int functionDefine_StartIndex = input.index();

		Token b=null;
		Token id=null;
		ParserRuleReturnScope exp =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:315:2: (b= FUNCTION '(' (id= ID ( ',' id= ID )* )? ')' exp= expression )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:315:4: b= FUNCTION '(' (id= ID ( ',' id= ID )* )? ')' exp= expression
			{
			b=(Token)match(input,FUNCTION,FOLLOW_FUNCTION_in_functionDefine1556); if (state.failed) return retval;
			if ( state.backtracking==0 ) { functionDefine_stack.peek().args = new java.util.ArrayList<String>(); }
			match(input,21,FOLLOW_21_in_functionDefine1562); if (state.failed) return retval;
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:317:3: (id= ID ( ',' id= ID )* )?
			int alt27=2;
			int LA27_0 = input.LA(1);
			if ( (LA27_0==ID) ) {
				alt27=1;
			}
			switch (alt27) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:317:5: id= ID ( ',' id= ID )*
					{
					id=(Token)match(input,ID,FOLLOW_ID_in_functionDefine1571); if (state.failed) return retval;
					if ( state.backtracking==0 ) { functionDefine_stack.peek().args.add( (id!=null?id.getText():null) ); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:318:4: ( ',' id= ID )*
					loop26:
					while (true) {
						int alt26=2;
						int LA26_0 = input.LA(1);
						if ( (LA26_0==25) ) {
							alt26=1;
						}

						switch (alt26) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:318:5: ',' id= ID
							{
							match(input,25,FOLLOW_25_in_functionDefine1579); if (state.failed) return retval;
							id=(Token)match(input,ID,FOLLOW_ID_in_functionDefine1583); if (state.failed) return retval;
							if ( state.backtracking==0 ) { functionDefine_stack.peek().args.add( (id!=null?id.getText():null) ); }
							}
							break;

						default :
							break loop26;
						}
					}

					}
					break;

			}

			match(input,22,FOLLOW_22_in_functionDefine1601); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_functionDefine1608);
			exp=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().Function(functionDefine_stack.peek().args,(exp!=null?((lang2Parser.expression_return)exp).value:null)); location( retval.value, b, (exp!=null?(exp.stop):null) ); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 18, functionDefine_StartIndex); }

			functionDefine_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "functionDefine"


	protected static class arrayDefine_scope {
		java.util.List arr;
	}
	protected Stack<arrayDefine_scope> arrayDefine_stack = new Stack<arrayDefine_scope>();

	public static class arrayDefine_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "arrayDefine"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:325:1: arrayDefine returns [Value value] : b= '[' (exp= expression ( ',' exp= expression )* )? e= ']' ;
	public final lang2Parser.arrayDefine_return arrayDefine() throws RecognitionException {
		arrayDefine_stack.push(new arrayDefine_scope());
		lang2Parser.arrayDefine_return retval = new lang2Parser.arrayDefine_return();
		retval.start = input.LT(1);
		int arrayDefine_StartIndex = input.index();

		Token b=null;
		Token e=null;
		ParserRuleReturnScope exp =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:329:2: (b= '[' (exp= expression ( ',' exp= expression )* )? e= ']' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:329:4: b= '[' (exp= expression ( ',' exp= expression )* )? e= ']'
			{
			b=(Token)match(input,39,FOLLOW_39_in_arrayDefine1633); if (state.failed) return retval;
			if ( state.backtracking==0 ) { arrayDefine_stack.peek().arr = new java.util.ArrayList(); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:330:3: (exp= expression ( ',' exp= expression )* )?
			int alt29=2;
			int LA29_0 = input.LA(1);
			if ( ((LA29_0 >= FLOAT && LA29_0 <= FUNCTION)||(LA29_0 >= ID && LA29_0 <= NOT)||LA29_0==STRING||LA29_0==21||LA29_0==26||LA29_0==39||LA29_0==43||LA29_0==45||(LA29_0 >= 47 && LA29_0 <= 49)||LA29_0==51||(LA29_0 >= 53 && LA29_0 <= 58)||LA29_0==60) ) {
				alt29=1;
			}
			switch (alt29) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:330:5: exp= expression ( ',' exp= expression )*
					{
					pushFollow(FOLLOW_expression_in_arrayDefine1643);
					exp=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { arrayDefine_stack.peek().arr.add((exp!=null?((lang2Parser.expression_return)exp).value:null)); }
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:331:4: ( ',' exp= expression )*
					loop28:
					while (true) {
						int alt28=2;
						int LA28_0 = input.LA(1);
						if ( (LA28_0==25) ) {
							alt28=1;
						}

						switch (alt28) {
						case 1 :
							// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:331:6: ',' exp= expression
							{
							match(input,25,FOLLOW_25_in_arrayDefine1652); if (state.failed) return retval;
							pushFollow(FOLLOW_expression_in_arrayDefine1656);
							exp=expression();
							state._fsp--;
							if (state.failed) return retval;
							if ( state.backtracking==0 ) { arrayDefine_stack.peek().arr.add((exp!=null?((lang2Parser.expression_return)exp).value:null)); }
							}
							break;

						default :
							break loop28;
						}
					}

					}
					break;

			}

			e=(Token)match(input,40,FOLLOW_40_in_arrayDefine1676); if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().VMArray( arrayDefine_stack.peek().arr ); location(retval.value,b,e); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 19, arrayDefine_StartIndex); }

			arrayDefine_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "arrayDefine"


	protected static class objectDefine_scope {
		java.util.List fields;
	}
	protected Stack<objectDefine_scope> objectDefine_stack = new Stack<objectDefine_scope>();

	public static class objectDefine_return extends ParserRuleReturnScope {
		public Value value;
	};


	// $ANTLR start "objectDefine"
	// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:337:1: objectDefine returns [Value value] : b= '{' id= ID ':' exp= expression ( ',' id= ID ':' exp= expression )* e= '}' ;
	public final lang2Parser.objectDefine_return objectDefine() throws RecognitionException {
		objectDefine_stack.push(new objectDefine_scope());
		lang2Parser.objectDefine_return retval = new lang2Parser.objectDefine_return();
		retval.start = input.LT(1);
		int objectDefine_StartIndex = input.index();

		Token b=null;
		Token id=null;
		Token e=null;
		ParserRuleReturnScope exp =null;

		try {
			if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }

			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:341:2: (b= '{' id= ID ':' exp= expression ( ',' id= ID ':' exp= expression )* e= '}' )
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:341:4: b= '{' id= ID ':' exp= expression ( ',' id= ID ':' exp= expression )* e= '}'
			{
			b=(Token)match(input,60,FOLLOW_60_in_objectDefine1701); if (state.failed) return retval;
			if ( state.backtracking==0 ) { objectDefine_stack.peek().fields = new java.util.ArrayList(); }
			id=(Token)match(input,ID,FOLLOW_ID_in_objectDefine1709); if (state.failed) return retval;
			match(input,29,FOLLOW_29_in_objectDefine1711); if (state.failed) return retval;
			pushFollow(FOLLOW_expression_in_objectDefine1715);
			exp=expression();
			state._fsp--;
			if (state.failed) return retval;
			if ( state.backtracking==0 ) { objectDefine_stack.peek().fields.add( factory().Field( (id!=null?id.getText():null), (exp!=null?((lang2Parser.expression_return)exp).value:null) ) ); }
			// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:343:3: ( ',' id= ID ':' exp= expression )*
			loop30:
			while (true) {
				int alt30=2;
				int LA30_0 = input.LA(1);
				if ( (LA30_0==25) ) {
					alt30=1;
				}

				switch (alt30) {
				case 1 :
					// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:344:3: ',' id= ID ':' exp= expression
					{
					match(input,25,FOLLOW_25_in_objectDefine1725); if (state.failed) return retval;
					id=(Token)match(input,ID,FOLLOW_ID_in_objectDefine1729); if (state.failed) return retval;
					match(input,29,FOLLOW_29_in_objectDefine1731); if (state.failed) return retval;
					pushFollow(FOLLOW_expression_in_objectDefine1735);
					exp=expression();
					state._fsp--;
					if (state.failed) return retval;
					if ( state.backtracking==0 ) { objectDefine_stack.peek().fields.add( factory().Field( (id!=null?id.getText():null), (exp!=null?((lang2Parser.expression_return)exp).value:null) ) ); }
					}
					break;

				default :
					break loop30;
				}
			}

			e=(Token)match(input,62,FOLLOW_62_in_objectDefine1749); if (state.failed) return retval;
			if ( state.backtracking==0 ) { retval.value = factory().VMObject( objectDefine_stack.peek().fields ); location(retval.value,b,e); }
			}

			retval.stop = input.LT(-1);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
			if ( state.backtracking>0 ) { memoize(input, 20, objectDefine_StartIndex); }

			objectDefine_stack.pop();
		}
		return retval;
	}
	// $ANTLR end "objectDefine"

	// $ANTLR start synpred9_lang2
	public final void synpred9_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:90:5: ( '=' e2= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:90:5: '=' e2= expression
		{
		match(input,34,FOLLOW_34_in_synpred9_lang2266); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred9_lang2270);
		e2=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred9_lang2

	// $ANTLR start synpred10_lang2
	public final void synpred10_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:111:5: ( '=' e2= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:111:5: '=' e2= expression
		{
		match(input,34,FOLLOW_34_in_synpred10_lang2401); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred10_lang2405);
		e2=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred10_lang2

	// $ANTLR start synpred11_lang2
	public final void synpred11_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e3 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:6: ( ':' e3= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:6: ':' e3= expression
		{
		match(input,29,FOLLOW_29_in_synpred11_lang2446); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred11_lang2450);
		e3=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred11_lang2

	// $ANTLR start synpred12_lang2
	public final void synpred12_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;
		ParserRuleReturnScope e3 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:114:5: ( '?' e2= expression ( ':' e3= expression )? )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:114:5: '?' e2= expression ( ':' e3= expression )?
		{
		match(input,38,FOLLOW_38_in_synpred12_lang2423); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred12_lang2427);
		e2=expression();
		state._fsp--;
		if (state.failed) return;
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:4: ( ':' e3= expression )?
		int alt32=2;
		int LA32_0 = input.LA(1);
		if ( (LA32_0==29) ) {
			alt32=1;
		}
		switch (alt32) {
			case 1 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:117:6: ':' e3= expression
				{
				match(input,29,FOLLOW_29_in_synpred12_lang2446); if (state.failed) return;
				pushFollow(FOLLOW_expression_in_synpred12_lang2450);
				e3=expression();
				state._fsp--;
				if (state.failed) return;
				}
				break;

		}

		}

	}
	// $ANTLR end synpred12_lang2

	// $ANTLR start synpred13_lang2
	public final void synpred13_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e1 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:126:4: (e1= block )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:126:4: e1= block
		{
		pushFollow(FOLLOW_block_in_synpred13_lang2502);
		e1=block();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred13_lang2

	// $ANTLR start synpred14_lang2
	public final void synpred14_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e3 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:136:6: ( 'else' e3= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:136:6: 'else' e3= expression
		{
		match(input,46,FOLLOW_46_in_synpred14_lang2571); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred14_lang2575);
		e3=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred14_lang2

	// $ANTLR start synpred16_lang2
	public final void synpred16_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e1 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:143:5: (e1= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:143:5: e1= expression
		{
		pushFollow(FOLLOW_expression_in_synpred16_lang2621);
		e1=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred16_lang2

	// $ANTLR start synpred18_lang2
	public final void synpred18_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e1 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:150:5: (e1= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:150:5: e1= expression
		{
		pushFollow(FOLLOW_expression_in_synpred18_lang2664);
		e1=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred18_lang2

	// $ANTLR start synpred20_lang2
	public final void synpred20_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e1 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:156:5: (e1= expression )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:156:5: e1= expression
		{
		pushFollow(FOLLOW_expression_in_synpred20_lang2703);
		e1=expression();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred20_lang2

	// $ANTLR start synpred24_lang2
	public final void synpred24_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:186:5: ( ( '|' | 'or' ) e2= xor )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:186:5: ( '|' | 'or' ) e2= xor
		{
		if ( input.LA(1)==52||input.LA(1)==61 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_xor_in_synpred24_lang2820);
		e2=xor();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred24_lang2

	// $ANTLR start synpred26_lang2
	public final void synpred26_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:194:5: ( ( '^' | 'xor' ) e2= and )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:194:5: ( '^' | 'xor' ) e2= and
		{
		if ( input.LA(1)==41||input.LA(1)==59 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_and_in_synpred26_lang2876);
		e2=and();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred26_lang2

	// $ANTLR start synpred28_lang2
	public final void synpred28_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:202:5: ( ( '&' | 'and' ) e2= compare )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:202:5: ( '&' | 'and' ) e2= compare
		{
		if ( input.LA(1)==20||input.LA(1)==42 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_compare_in_synpred28_lang2932);
		e2=compare();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred28_lang2

	// $ANTLR start synpred30_lang2
	public final void synpred30_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:209:5: ( ( '<>' | '!=' ) e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:209:5: ( '<>' | '!=' ) e2= addition
		{
		if ( input.LA(1)==18||input.LA(1)==33 ) {
			input.consume();
			state.errorRecovery=false;
			state.failed=false;
		}
		else {
			if (state.backtracking>0) {state.failed=true; return;}
			MismatchedSetException mse = new MismatchedSetException(null,input);
			throw mse;
		}
		pushFollow(FOLLOW_addition_in_synpred30_lang2981);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred30_lang2

	// $ANTLR start synpred31_lang2
	public final void synpred31_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:211:5: ( '<=' e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:211:5: '<=' e2= addition
		{
		match(input,32,FOLLOW_32_in_synpred31_lang2994); if (state.failed) return;
		pushFollow(FOLLOW_addition_in_synpred31_lang21000);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred31_lang2

	// $ANTLR start synpred32_lang2
	public final void synpred32_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:213:5: ( '>=' e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:213:5: '>=' e2= addition
		{
		match(input,37,FOLLOW_37_in_synpred32_lang21013); if (state.failed) return;
		pushFollow(FOLLOW_addition_in_synpred32_lang21019);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred32_lang2

	// $ANTLR start synpred33_lang2
	public final void synpred33_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:215:5: ( '==' e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:215:5: '==' e2= addition
		{
		match(input,35,FOLLOW_35_in_synpred33_lang21032); if (state.failed) return;
		pushFollow(FOLLOW_addition_in_synpred33_lang21038);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred33_lang2

	// $ANTLR start synpred34_lang2
	public final void synpred34_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:217:5: ( '<' e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:217:5: '<' e2= addition
		{
		match(input,31,FOLLOW_31_in_synpred34_lang21051); if (state.failed) return;
		pushFollow(FOLLOW_addition_in_synpred34_lang21057);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred34_lang2

	// $ANTLR start synpred35_lang2
	public final void synpred35_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:219:5: ( '>' e2= addition )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:219:5: '>' e2= addition
		{
		match(input,36,FOLLOW_36_in_synpred35_lang21070); if (state.failed) return;
		pushFollow(FOLLOW_addition_in_synpred35_lang21076);
		e2=addition();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred35_lang2

	// $ANTLR start synpred37_lang2
	public final void synpred37_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:244:4: ( '+' e2= multiple )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:244:4: '+' e2= multiple
		{
		match(input,24,FOLLOW_24_in_synpred37_lang21149); if (state.failed) return;
		pushFollow(FOLLOW_multiple_in_synpred37_lang21155);
		e2=multiple();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred37_lang2

	// $ANTLR start synpred38_lang2
	public final void synpred38_lang2_fragment() throws RecognitionException {
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:252:4: ( '-' e2= multiple )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:252:4: '-' e2= multiple
		{
		match(input,26,FOLLOW_26_in_synpred38_lang21166); if (state.failed) return;
		pushFollow(FOLLOW_multiple_in_synpred38_lang21172);
		e2=multiple();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred38_lang2

	// $ANTLR start synpred39_lang2
	public final void synpred39_lang2_fragment() throws RecognitionException {
		Token o=null;
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:265:5: (o= '*' e2= postfix )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:265:5: o= '*' e2= postfix
		{
		o=(Token)match(input,23,FOLLOW_23_in_synpred39_lang21211); if (state.failed) return;
		pushFollow(FOLLOW_postfix_in_synpred39_lang21217);
		e2=postfix();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred39_lang2

	// $ANTLR start synpred40_lang2
	public final void synpred40_lang2_fragment() throws RecognitionException {
		Token o=null;
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:266:5: (o= '/' e2= postfix )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:266:5: o= '/' e2= postfix
		{
		o=(Token)match(input,28,FOLLOW_28_in_synpred40_lang21227); if (state.failed) return;
		pushFollow(FOLLOW_postfix_in_synpred40_lang21233);
		e2=postfix();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred40_lang2

	// $ANTLR start synpred41_lang2
	public final void synpred41_lang2_fragment() throws RecognitionException {
		Token o=null;
		ParserRuleReturnScope e2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:267:5: (o= '%' e2= postfix )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:267:5: o= '%' e2= postfix
		{
		o=(Token)match(input,19,FOLLOW_19_in_synpred41_lang21243); if (state.failed) return;
		pushFollow(FOLLOW_postfix_in_synpred41_lang21249);
		e2=postfix();
		state._fsp--;
		if (state.failed) return;
		}

	}
	// $ANTLR end synpred41_lang2

	// $ANTLR start synpred42_lang2
	public final void synpred42_lang2_fragment() throws RecognitionException {
		Token fb=null;
		Token indexID=null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:279:5: (fb= '.' indexID= ID )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:279:5: fb= '.' indexID= ID
		{
		fb=(Token)match(input,27,FOLLOW_27_in_synpred42_lang21293); if (state.failed) return;
		indexID=(Token)match(input,ID,FOLLOW_ID_in_synpred42_lang21299); if (state.failed) return;
		}

	}
	// $ANTLR end synpred42_lang2

	// $ANTLR start synpred43_lang2
	public final void synpred43_lang2_fragment() throws RecognitionException {
		Token ab=null;
		Token ae=null;
		ParserRuleReturnScope indexExpr =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:280:5: (ab= '[' indexExpr= expression ae= ']' )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:280:5: ab= '[' indexExpr= expression ae= ']'
		{
		ab=(Token)match(input,39,FOLLOW_39_in_synpred43_lang21309); if (state.failed) return;
		pushFollow(FOLLOW_expression_in_synpred43_lang21315);
		indexExpr=expression();
		state._fsp--;
		if (state.failed) return;
		ae=(Token)match(input,40,FOLLOW_40_in_synpred43_lang21319); if (state.failed) return;
		}

	}
	// $ANTLR end synpred43_lang2

	// $ANTLR start synpred46_lang2
	public final void synpred46_lang2_fragment() throws RecognitionException {
		Token cb=null;
		Token ce=null;
		ParserRuleReturnScope arg =null;
		ParserRuleReturnScope arg2 =null;

		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:282:5: (cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')' )
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:282:5: cb= '(' (arg= expression ( ',' arg2= expression )* )? ce= ')'
		{
		cb=(Token)match(input,21,FOLLOW_21_in_synpred46_lang21334); if (state.failed) return;
		// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:283:4: (arg= expression ( ',' arg2= expression )* )?
		int alt39=2;
		int LA39_0 = input.LA(1);
		if ( ((LA39_0 >= FLOAT && LA39_0 <= FUNCTION)||(LA39_0 >= ID && LA39_0 <= NOT)||LA39_0==STRING||LA39_0==21||LA39_0==26||LA39_0==39||LA39_0==43||LA39_0==45||(LA39_0 >= 47 && LA39_0 <= 49)||LA39_0==51||(LA39_0 >= 53 && LA39_0 <= 58)||LA39_0==60) ) {
			alt39=1;
		}
		switch (alt39) {
			case 1 :
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:283:6: arg= expression ( ',' arg2= expression )*
				{
				pushFollow(FOLLOW_expression_in_synpred46_lang21343);
				arg=expression();
				state._fsp--;
				if (state.failed) return;
				// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:284:5: ( ',' arg2= expression )*
				loop38:
				while (true) {
					int alt38=2;
					int LA38_0 = input.LA(1);
					if ( (LA38_0==25) ) {
						alt38=1;
					}

					switch (alt38) {
					case 1 :
						// /home/user/code/dev/nb74/lang2/src/lang2/parser/lang2.g:284:7: ',' arg2= expression
						{
						match(input,25,FOLLOW_25_in_synpred46_lang21353); if (state.failed) return;
						pushFollow(FOLLOW_expression_in_synpred46_lang21357);
						arg2=expression();
						state._fsp--;
						if (state.failed) return;
						}
						break;

					default :
						break loop38;
					}
				}

				}
				break;

		}

		ce=(Token)match(input,22,FOLLOW_22_in_synpred46_lang21379); if (state.failed) return;
		}

	}
	// $ANTLR end synpred46_lang2

	// Delegated rules

	public final boolean synpred14_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred14_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred24_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred24_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred39_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred39_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred32_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred32_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred42_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred42_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred12_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred12_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred26_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred26_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred16_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred16_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred11_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred11_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred37_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred37_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred34_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred34_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred43_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred43_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred33_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred33_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred35_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred35_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred41_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred41_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred31_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred31_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred9_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred9_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred46_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred46_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred40_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred40_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred30_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred30_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred20_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred20_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred38_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred38_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred10_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred10_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred13_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred13_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred18_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred18_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}
	public final boolean synpred28_lang2() {
		state.backtracking++;
		int start = input.mark();
		try {
			synpred28_lang2_fragment(); // can never throw exception
		} catch (RecognitionException re) {
			System.err.println("impossible: "+re);
		}
		boolean success = !state.failed;
		input.rewind(start);
		state.backtracking--;
		state.failed=false;
		return success;
	}



	public static final BitSet FOLLOW_expression_in_expressions68 = new BitSet(new long[]{0x17EBA8804420BB02L});
	public static final BitSet FOLLOW_30_in_expressions78 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_expressions84 = new BitSet(new long[]{0x17EBA8804420BB02L});
	public static final BitSet FOLLOW_30_in_expressions107 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_varDefine_in_expression139 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_whileCycle_in_expression155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_forCycle_in_expression171 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_assign_in_expression187 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_expression203 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_flow_in_expression218 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_57_in_varDefine247 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_varDefine251 = new BitSet(new long[]{0x0000000400000002L});
	public static final BitSet FOLLOW_34_in_varDefine266 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_varDefine270 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_58_in_whileCycle305 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_whileCycle307 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_whileCycle311 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_whileCycle313 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_whileCycle317 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_48_in_forCycle341 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_forCycle343 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_forCycle347 = new BitSet(new long[]{0x0004000000000000L});
	public static final BitSet FOLLOW_50_in_forCycle349 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_forCycle353 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_forCycle355 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_forCycle359 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_or_in_assign385 = new BitSet(new long[]{0x0000004400000002L});
	public static final BitSet FOLLOW_34_in_assign401 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_assign405 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_38_in_assign423 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_assign427 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_29_in_assign446 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_assign450 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_60_in_block494 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_block_in_block502 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_expressions_in_block511 = new BitSet(new long[]{0x4000000000000000L});
	public static final BitSet FOLLOW_62_in_block521 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_49_in_flow541 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_flow543 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow547 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_flow549 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow553 = new BitSet(new long[]{0x0000400000000002L});
	public static final BitSet FOLLOW_46_in_flow571 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow575 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_53_in_flow603 = new BitSet(new long[]{0x17EBA8800420BB02L});
	public static final BitSet FOLLOW_expression_in_flow621 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_43_in_flow646 = new BitSet(new long[]{0x17EBA8800420BB02L});
	public static final BitSet FOLLOW_expression_in_flow664 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_45_in_flow685 = new BitSet(new long[]{0x17EBA8800420BB02L});
	public static final BitSet FOLLOW_expression_in_flow703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_54_in_flow728 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow732 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_flow747 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow751 = new BitSet(new long[]{0x0000100000000000L});
	public static final BitSet FOLLOW_44_in_flow755 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_flow757 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_flow761 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_flow763 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_flow767 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_xor_in_or798 = new BitSet(new long[]{0x2010000000000002L});
	public static final BitSet FOLLOW_set_in_or810 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_xor_in_or820 = new BitSet(new long[]{0x2010000000000002L});
	public static final BitSet FOLLOW_and_in_xor852 = new BitSet(new long[]{0x0800020000000002L});
	public static final BitSet FOLLOW_set_in_xor864 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_and_in_xor876 = new BitSet(new long[]{0x0800020000000002L});
	public static final BitSet FOLLOW_compare_in_and908 = new BitSet(new long[]{0x0000040000100002L});
	public static final BitSet FOLLOW_set_in_and922 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_compare_in_and932 = new BitSet(new long[]{0x0000040000100002L});
	public static final BitSet FOLLOW_addition_in_compare963 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_set_in_compare971 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare981 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_32_in_compare994 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare1000 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_37_in_compare1013 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare1019 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_35_in_compare1032 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare1038 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_31_in_compare1051 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare1057 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_36_in_compare1070 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_compare1076 = new BitSet(new long[]{0x0000003B80040002L});
	public static final BitSet FOLLOW_26_in_addition1126 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_multiple_in_addition1139 = new BitSet(new long[]{0x0000000005000002L});
	public static final BitSet FOLLOW_24_in_addition1149 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_multiple_in_addition1155 = new BitSet(new long[]{0x0000000005000002L});
	public static final BitSet FOLLOW_26_in_addition1166 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_multiple_in_addition1172 = new BitSet(new long[]{0x0000000005000002L});
	public static final BitSet FOLLOW_postfix_in_multiple1201 = new BitSet(new long[]{0x0000000010880002L});
	public static final BitSet FOLLOW_23_in_multiple1211 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_multiple1217 = new BitSet(new long[]{0x0000000010880002L});
	public static final BitSet FOLLOW_28_in_multiple1227 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_multiple1233 = new BitSet(new long[]{0x0000000010880002L});
	public static final BitSet FOLLOW_19_in_multiple1243 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_multiple1249 = new BitSet(new long[]{0x0000000010880002L});
	public static final BitSet FOLLOW_value_in_postfix1283 = new BitSet(new long[]{0x0000008008200002L});
	public static final BitSet FOLLOW_27_in_postfix1293 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_postfix1299 = new BitSet(new long[]{0x0000008008200002L});
	public static final BitSet FOLLOW_39_in_postfix1309 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_postfix1315 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_postfix1319 = new BitSet(new long[]{0x0000008008200002L});
	public static final BitSet FOLLOW_21_in_postfix1334 = new BitSet(new long[]{0x17EBA8800460BB00L});
	public static final BitSet FOLLOW_expression_in_postfix1343 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_25_in_postfix1353 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_postfix1357 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_22_in_postfix1379 = new BitSet(new long[]{0x0000008008200002L});
	public static final BitSet FOLLOW_constPrimitive_in_value1403 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_ID_in_value1415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_NOT_in_value1424 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_value1428 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_21_in_value1437 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_value1441 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_22_in_value1445 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_functionDefine_in_value1454 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_objectDefine_in_value1463 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_arrayDefine_in_value1472 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_55_in_constPrimitive1490 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_47_in_constPrimitive1497 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_51_in_constPrimitive1504 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FLOAT_in_constPrimitive1513 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_INT_in_constPrimitive1522 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_STRING_in_constPrimitive1531 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_FUNCTION_in_functionDefine1556 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_21_in_functionDefine1562 = new BitSet(new long[]{0x0000000000400800L});
	public static final BitSet FOLLOW_ID_in_functionDefine1571 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_25_in_functionDefine1579 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_functionDefine1583 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_22_in_functionDefine1601 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_functionDefine1608 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_39_in_arrayDefine1633 = new BitSet(new long[]{0x17EBA9800420BB00L});
	public static final BitSet FOLLOW_expression_in_arrayDefine1643 = new BitSet(new long[]{0x0000010002000000L});
	public static final BitSet FOLLOW_25_in_arrayDefine1652 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_arrayDefine1656 = new BitSet(new long[]{0x0000010002000000L});
	public static final BitSet FOLLOW_40_in_arrayDefine1676 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_60_in_objectDefine1701 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_objectDefine1709 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_objectDefine1711 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_objectDefine1715 = new BitSet(new long[]{0x4000000002000000L});
	public static final BitSet FOLLOW_25_in_objectDefine1725 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_objectDefine1729 = new BitSet(new long[]{0x0000000020000000L});
	public static final BitSet FOLLOW_29_in_objectDefine1731 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_objectDefine1735 = new BitSet(new long[]{0x4000000002000000L});
	public static final BitSet FOLLOW_62_in_objectDefine1749 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_34_in_synpred9_lang2266 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred9_lang2270 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_34_in_synpred10_lang2401 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred10_lang2405 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_29_in_synpred11_lang2446 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred11_lang2450 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_38_in_synpred12_lang2423 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred12_lang2427 = new BitSet(new long[]{0x0000000020000002L});
	public static final BitSet FOLLOW_29_in_synpred12_lang2446 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred12_lang2450 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_block_in_synpred13_lang2502 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_46_in_synpred14_lang2571 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred14_lang2575 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_synpred16_lang2621 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_synpred18_lang2664 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_expression_in_synpred20_lang2703 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred24_lang2810 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_xor_in_synpred24_lang2820 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred26_lang2864 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_and_in_synpred26_lang2876 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred28_lang2922 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_compare_in_synpred28_lang2932 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_set_in_synpred30_lang2971 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred30_lang2981 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_synpred31_lang2994 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred31_lang21000 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_37_in_synpred32_lang21013 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred32_lang21019 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_35_in_synpred33_lang21032 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred33_lang21038 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_31_in_synpred34_lang21051 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred34_lang21057 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_36_in_synpred35_lang21070 = new BitSet(new long[]{0x108880800420BB00L});
	public static final BitSet FOLLOW_addition_in_synpred35_lang21076 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_24_in_synpred37_lang21149 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_multiple_in_synpred37_lang21155 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_26_in_synpred38_lang21166 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_multiple_in_synpred38_lang21172 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_23_in_synpred39_lang21211 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_synpred39_lang21217 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_28_in_synpred40_lang21227 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_synpred40_lang21233 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_synpred41_lang21243 = new BitSet(new long[]{0x108880800020BB00L});
	public static final BitSet FOLLOW_postfix_in_synpred41_lang21249 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_27_in_synpred42_lang21293 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_ID_in_synpred42_lang21299 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_39_in_synpred43_lang21309 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred43_lang21315 = new BitSet(new long[]{0x0000010000000000L});
	public static final BitSet FOLLOW_40_in_synpred43_lang21319 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_21_in_synpred46_lang21334 = new BitSet(new long[]{0x17EBA8800460BB00L});
	public static final BitSet FOLLOW_expression_in_synpred46_lang21343 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_25_in_synpred46_lang21353 = new BitSet(new long[]{0x17EBA8800420BB00L});
	public static final BitSet FOLLOW_expression_in_synpred46_lang21357 = new BitSet(new long[]{0x0000000002400000L});
	public static final BitSet FOLLOW_22_in_synpred46_lang21379 = new BitSet(new long[]{0x0000000000000002L});
}
