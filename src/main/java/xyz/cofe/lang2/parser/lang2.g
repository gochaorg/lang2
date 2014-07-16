grammar lang2;
options
{
backtrack=true; 
memoize=true;
superClass=AbstractParser;
}

@header
{
package lang2.parser;

import lang2.vm.*;
}

@lexer::header
{
package lang2.parser;
}

expressions returns [Value value]
	scope
	{
		java.util.ArrayList<Value> body;
		Boolean _finaled;
		Boolean _e2Exists;
	}	
	@init
	{
		$expressions::body = new java.util.ArrayList<Value>();
		$expressions::_finaled = false;
		$expressions::_e2Exists = false;
	}
	:
		e1=expression 
		{ $expressions::body.add($e1.value); }
	(	';' ? e2=expression 
		{ $expressions::body.add($e2.value); }
		{ $expressions::_e2Exists = true; }
	) *
	
	(	f=';' { $expressions::_finaled = true; }
	) ?
		{ $value = factory().Expressions( $expressions::body ); }
		{
			if( $expressions::_finaled ){
				if( $expressions::_e2Exists ){
					location($value,$e1.start,$f);
				}else{
					location($value,$e1.start,$f);
				}
			}else{
				if( $expressions::_e2Exists ){
					location($value,$e1.start,$e2.stop);
				}else{
					location($value,$e1.start);
				}
			}
		}
	;

expression returns [Value value]
	:	vde=varDefine 
		{ $value = $vde.value; }
		{ location($value,$vde.start,$vde.stop); }
	|	wce=whileCycle 
		{ $value = $wce.value; }
		{ location($value,$wce.start,$wce.stop); }
	|	fce=forCycle 
		{ $value = $fce.value; }
		{ location($value,$fce.start,$fce.stop); }
	|	ae=assign 
		{ $value = $ae.value; }
		{ location($value,$ae.start,$ae.stop); }
	|	be=block
		{ $value = $be.value; }
		{ location($value,$be.start,$be.stop); }
	|	fe=flow 
		{ $value = $fe.value; }
		{ location($value,$fe.start,$fe.stop); }
	/*|	oe=or 
		{ $value = $oe.value; }
		{ location($value,$oe.start,$oe.stop); } */
	;

varDefine returns [Value value]
	:	b='var' id=ID 
		{ $value = factory().VarDefine($id.text); }
		{ location($value,$b,$id); }
		(	'=' e2=expression 
			{ $value = factory().VarDefine($id.text,$e2.value); }
			{ location($value,$b,$e2.stop); }
		) ?
	;
	
whileCycle returns [Value value]
	:	b='while' '(' e1=expression ')' e2=expression { $value = factory().While( $e1.value, $e2.value ); }
		{ location($value,$b,$e2.stop); }
	;
	
forCycle returns [Value value]
	:	b='for' '(' var=ID 'in' src=expression ')' body=expression
		{ $value = factory().For( $var.text, $src.value, $body.value ); }
		{ location($value, $b, $body.stop); }
	;
	
assign returns [Value value]
	:	e1=or 
		{ $value = $e1.value; } 
		{ location($value,$e1.start,$e1.stop); }
		(	'=' e2=expression 
			{ $value = factory().Assign( $value, $e2.value );  } 
			{ location($value,$e1.start,$e2.stop); }
		|	'?' e2=expression 
			{ $value = factory().If($e1.value, $e2.value); } 
			{ location($value,$e1.start,$e2.stop); }
			(	':' e3=expression 
				{ $value = factory().If($e1.value, $e2.value, $e3.value); } 
				{ location($value,$e1.start,$e3.stop); }
			) ?
		) ?
	;

block returns [Value value]
	:	b='{' 
	(	e1=block { $value = factory().Block( $e1.value ); }
	|	e2=expressions { $value = factory().Block( $e2.value ); }
	)
	e='}' { location($value,$b,$e); }
	;
	
flow returns [Value value]
	:	if_='if' '(' e1=expression ')' e2=expression
			{ $value = factory().If($e1.value, $e2.value); } 
			{ location($value,$if_,$e2.stop); }
			(	'else' e3=expression 
				{ $value = factory().If($e1.value, $e2.value, $e3.value); } 
				{ location($value,$if_,$e3.stop); }
			) ?
	|	rb='return' 
		{$value = factory().Return(); } 
		{ location($value,$rb); }
		(	e1=expression 
			{$value = factory().Return($e1.value); } 
			{ location($value,$rb,$e1.stop); }
		) ?
	|	bb='break' 
		{$value = factory().Break(); } 
		{ location($value,$bb); }
		(	e1=expression {$value = factory().Break($e1.value); } 
			{ location($value,$bb,$e1.stop); }
		) ?
	|	cb='continue' 
		{$value = factory().Continue(); } 
		{ location($value,$cb); }
		(	e1=expression 
			{ $value = factory().Continue($e1.value); } 
			{ location($value,$cb,$e1.stop); }
		) ?
	|	th='throw' e1=expression
		{ $value = factory().Throw( $e1.value ); }
		{ location($value, $th, $e1.stop); }
	|	tc='try' e1=expression
		'catch' '(' cvar=ID ')' e2=expression
		{ $value = factory().TryCatch($e1.value,$cvar.text,$e2.value); }
		{ location($value, $tc, $e2.stop); }
	/*
	|	tc='try' expression
		(
			(
				( 'catch' '(' ID ')' expression )
				|
				( 'finally' expression )
			)
			|
			(
				( 'catch' '(' ID ')' expression )
				( 'finally' expression )
			)
		)*/
	;
	
or returns [Value value]
	:	e1 = xor {$value = $e1.value;}
		{ location($value,$e1.start,$e1.stop); }
		(	('|'|'or') e2 = xor { $value = factory().Or( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		)*
	;
	
xor	returns [Value value]
	:	e1 = and {$value = $e1.value;}
		{ location($value,$e1.start,$e1.stop); }
		(	('^' | 'xor') e2 = and { $value = factory().Xor( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		)*
	;
	
and returns [Value value]
	:	e1 = compare  {$value = $e1.value;} 
		{ location($value,$e1.start,$e1.stop); }
		(	('&'|'and') e2 = compare { $value = factory().And( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		)*
	;

compare returns [Value value]
	:	e1 = addition {$value = $e1.value; location($value, $e1.start, $e1.stop); }
		(	('<>'|'!=') e2 = addition { $value = factory().CompareNotEquals( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		|	'<=' e2 = addition { $value = factory().CompareLessOrEquals( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		|	'>=' e2 = addition { $value = factory().CompareMoreOrEquals( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		|	'==' e2 = addition { $value = factory().CompareEquals( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		|	'<' e2 = addition { $value = factory().CompareLess( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		|	'>' e2 = addition { $value = factory().CompareMore( $value, $e2.value ); }
			{ location($value,$e1.start,$e2.stop); }
		)*
	;
				
addition	returns [Value value]
	scope
	{
		Boolean unariyMinus;
	}	
	@init
	{
		$addition::unariyMinus = false;
	}
	:
	(	um='-' { $addition::unariyMinus = true; } )?
		e1 = multiple 
		{ 	$value = $e1.value;
			if( $addition::unariyMinus ) $value = factory().UnaryMinus( $value );
			if( $addition::unariyMinus ) {
				location( $value, $um, $e1.stop );
			} else {
				location( $value, $e1.start, $e1.stop ); 
			}
		}
	(	'+' e2 = multiple { $value = factory().Add( $value, $e2.value ); }
		{
			if( $addition::unariyMinus ) {
				location( $value, $um, $e2.stop );
			} else {
				location( $value, $e1.start, $e2.stop ); 
			}
		}
	|	'-' e2 = multiple { $value = factory().Substract( $value, $e2.value ); }
		{
			if( $addition::unariyMinus ) {
				location( $value, $um, $e2.stop );
			} else {
				location( $value, $e1.start, $e2.stop ); 
			}
		}
	)*
	;

multiple	returns [Value value]
	:	e1 = postfix { $value = $e1.value; location($value,$e1.start); }
		(	o='*' e2 = postfix { $value = factory().Multiple( $value, $e2.value ); location($value,$e1.start,$e2.stop); }
		|	o='/' e2 = postfix { $value = factory().Divide( $value, $e2.value ); location($value,$e1.start,$e2.stop); }
		|	o='%' e2 = postfix { $value = factory().Mod( $value, $e2.value ); location($value,$e1.start,$e2.stop); }
		)*
	;
	
postfix returns [Value value]
	scope{
		java.util.ArrayList<Value> arguments;		
	}
	@init{
		$postfix::arguments = new java.util.ArrayList<Value>();
	}
	:	ve=value { $value = $ve.value; location($value,$ve.start,$ve.stop); }
		(	fb='.' indexID = ID { $value = factory().FieldIndex( $value, $indexID.text ); location($value,$ve.start,$indexID); }
		|	ab='[' indexExpr = expression ae=']' 
				{ $value = factory().ArrayIndex( $value, $indexExpr.value ); location( $value, $ve.start, $ae ); }
		|	cb='('
			(	arg=expression { $postfix::arguments.add($arg.value); }
				(	',' arg2=expression { $postfix::arguments.add($arg2.value); }
				)*
			)?
			ce=')' { $value = factory().Call($value,$postfix::arguments); location( $value,$ve.start,$ce); }
		)*
	;

value returns [Value value]
	:	cexp=constPrimitive 
		{ $value = $cexp.value; location( $value, $cexp.start, $cexp.stop ); }
	|	id=ID { $value = factory().Variable($id.text); location( $value, $id ); }
	|	n=NOT ne=expression { $value = factory().Not( $ne.value ); location( $value, $n, $ne.stop ); }
	|	b='(' de=expression e=')' { $value = factory().Delegate( $de.value); location( $value, $b, $e ); }
	|	fe=functionDefine { $value = $fe.value; location($value,$fe.start,$fe.stop); }
	|	oe=objectDefine { $value = $oe.value; location($value,$oe.start,$oe.stop); }
	|	ae=arrayDefine { $value = $ae.value; location($value,$ae.start,$ae.stop); }
	;
	
constPrimitive returns [Value value]
	:	'true' { $value = factory().Const( new Boolean(true) ); }
	|	'false' { $value = factory().Const( new Boolean(false) ); }
	|	'null' { $value = factory().Const( null ); }
	|	floatv=FLOAT { $value = factory().Const( new Double($floatv.text) ); }
	|	intv=INT { $value = factory().Const( new Integer($intv.text) ); }
	|	str=STRING { $value = factory().Const( AbstractParser.parseStringConst($str.text) ); }
	;
	
functionDefine returns [Value value]
	scope {
		java.util.List<String> args;
	}
	:	b=FUNCTION { $functionDefine::args = new java.util.ArrayList<String>(); }
		'(' 
		(	id=ID { $functionDefine::args.add( $id.text ); }
			(',' id=ID { $functionDefine::args.add( $id.text ); }
			)*
		)? 
		')' 
		exp=expression { $value = factory().Function($functionDefine::args,$exp.value); location( $value, $b, $exp.stop ); }
	;
	
arrayDefine returns [Value value]
	scope {
		java.util.List arr;
	}
	:	b='[' { $arrayDefine::arr = new java.util.ArrayList(); }
		(	exp=expression { $arrayDefine::arr.add($exp.value); }
			(	',' exp=expression { $arrayDefine::arr.add($exp.value); }
			)*
		) ?
		e=']' { $value = factory().VMArray( $arrayDefine::arr ); location($value,$b,$e); }
	;
	
objectDefine returns [Value value]
	scope {
		java.util.List fields;
	}
	:	b='{' { $objectDefine::fields = new java.util.ArrayList(); }
		id=ID ':' exp=expression { $objectDefine::fields.add( factory().Field( $id.text, $exp.value ) ); }
		(
		',' id=ID ':' exp=expression { $objectDefine::fields.add( factory().Field( $id.text, $exp.value ) ); }
		)* 
		e='}' { $value = factory().VMObject( $objectDefine::fields ); location($value,$b,$e); }
	;
	
/*
parserDefine returns [Value value]
	:
	'parser'
	'{'
		block ?
		parserRule
		(	',' parserRule
		) *
	'}'
	;
	
parserRule returns [Value value]
	:	ID ':=' parserExpr
	;
	
parserExpr
	:	
	(	ID '=' ) ?
	(	ID
	|	STRING
	|	( 
			'(' parserExpr ')' 
			( '+?' | '*?' | '+' | '*' | '?' ) ?
		)
	|	'|' parserExpr
	|	'!' parserNotExpr
	)
	(	block ) ?
	;
	
parserNotExpr
	:	STRING
	|	'(' parserNotExpr ')'
	|	'|' parserNotExpr
	;
*/
	
FUNCTION
	:	'function';
	
NOT	:	'!' | 'not';
	
ID  :	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
    ;
    
INT :	'0'..'9'+
    ;


FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* 
    ;

/*FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;*/

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' 
    	{$channel=HIDDEN;}
    	{CommentReciver.recive($index, $line, $pos, $text);}
    |   '/*' ( options {greedy=false;} : . )* '*/' 
    	{$channel=HIDDEN;}
    	{CommentReciver.recive($index, $line, $pos, $text);}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;