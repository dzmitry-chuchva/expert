header {
	package by.bsuir.iit.kp.expert.parsers.gen;
}

/* -------------------- PARSER ----------------- */
class ANTLRBaseModelParser extends Parser;
options {
   defaultErrorHandler = false;
}

val returns [String v] { String sign = ""; String vvalue = ""; }
	: (p:PLUS { sign = p.getText(); } | m:MINUS { sign = m.getText(); })?
	  (f:FLOAT_VALUE { vvalue = f.getText(); } | i:INT_VALUE { vvalue = i.getText(); })
	  {
	  	v = sign + vvalue;
	  }
	;
	
quoted_str returns [String s]
	: q:QUOTED_STRING
		{
			StringBuffer buffer = new StringBuffer(q.getText());
			buffer.deleteCharAt(0);
			buffer.deleteCharAt(buffer.length() - 1);
			s = buffer.toString();
		}
	;

applicability_condition returns [String s]
	: "if" s=logical_expression
	;

ref returns [String rf]
	: i:ID { rf = i.getText(); } 
	| l:LBRACKET ii:ID d:DOT iii:ID r:RBRACKET
		{
			rf = l.getText() + ii.getText() + d.getText() +
				iii.getText() + r.getText();
		}
	;
	
arg returns [String ar]
	: ar=ref
	| ar=quoted_str { ar = "\"" + ar + "\""; }
	| ar=val
	;
	
arg_list returns [String argline]
	{ StringBuffer argbuffer = new StringBuffer(); String ar; }
	: ar=arg { argbuffer.append(ar); } (c:COMMA ar=arg { argbuffer.append(c.getText() + ar); })*
		{
			argline = argbuffer.toString();		
		}
	;

// Arithmetical and logical expressions present only for checking syntax,
// real evaulation will be performed on runtime by specialized evaluators.
// Also it is needed because we dont have delimiters for logical expressions,
// which we have in actions (quotes). Action can be also logical expression,
// but we dont parse it, and let evaluator parser to do real parsing. It will
// say whether action, in this case, logical or arithmetical expression is syntacticly
// right or no. With logical/arithmetical expressions in other grammar rules (not actions)
// we does not have quotes surrounding them. So we must explicitly, via full description of
// grammar, define scope of expression.
// Here is an issue - parsers for expressions must be in sync. So if you
// change here something, you must change corresponding evaluator's parser.

/* arithmetical expressions */	
arithmetical_expression returns [String s]
	: s=additive_expression
	;

additive_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=mult_expression { buffer.append(e); }
	(p:PLUS e=mult_expression { buffer.append(p.getText()); buffer.append(e); }
	| m:MINUS e=mult_expression { buffer.append(m.getText()); buffer.append(e); }
	)*
		{
			s = buffer.toString();
		}
	;
mult_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=unary_expression { buffer.append(e); }
	(m:MULT e=unary_expression { buffer.append(m.getText()); buffer.append(e); }
	| d:DIV e=unary_expression { buffer.append(d.getText()); buffer.append(e); }
	)*
		{
			s = buffer.toString();
		}
	;
unary_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; String b; }
	: (p:PLUS | m:MINUS)? e=primary_expression b=braces
		{
			if (p != null) {
				buffer.append(p.getText());
			}
			
			if (m != null) {
				buffer.append(m.getText());
			}
			
			buffer.append(e);
			buffer.append(b);
			s = buffer.toString();
		}
	;
braces returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: l:LBRACE e=argument_expression_list r:RBRACE
		{
			buffer.append(l.getText());
			buffer.append(e);
			buffer.append(r.getText());
			s = buffer.toString();
		}
	| /* empty */
		{
			s = "";
		}
	;
argument_expression_list returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=additive_expression { buffer.append(e); } (c:COMMA e=additive_expression { buffer.append(c.getText()); buffer.append(e); })*
		{
			s = buffer.toString();
		}
	| /* empty */
		{
			s = "";
		}
	;
primary_expression returns [String s]
	{ String e; }
	: s=ref
	| f:FLOAT_VALUE { s = f.getText(); }
	| i:INT_VALUE { s = i.getText(); }
	| l:LBRACE e=additive_expression r:RBRACE
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append(l.getText());
			buffer.append(e);
			buffer.append(r.getText());
			s = buffer.toString();
		}
	;

/* logical expressions */	
logical_expression returns [String s]
	: s=or_expression
	;

or_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=and_expression { buffer.append(e); } (o:OR e=and_expression { buffer.append(o.getText()); buffer.append(e); })*
		{
			s = buffer.toString();
		}
	;
and_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=rel_expression { buffer.append(e); } (a:AND e=rel_expression { buffer.append(a.getText()); buffer.append(e); })*
		{
			s = buffer.toString();
		}	
	;
rel_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: e=primary_logical_expression { buffer.append(e); }
	({ buffer.append(LT(1).getText()); } (NEQ | EQ | LT | GT | LE | GE) e=primary_logical_expression { buffer.append(e); })*
		{
			s = buffer.toString();
		}	
	;
primary_logical_expression returns [String s]
	{ StringBuffer buffer = new StringBuffer(); String e; }
	: n:NOT e=primary_logical_expression
		{
			buffer.append(n.getText()); buffer.append(e);
			s = buffer.toString();
		}
	| l:LBRACE e=logical_expression r:RBRACE
		{
			buffer.append(l.getText());
			buffer.append(e);
			buffer.append(r.getText());
			s = buffer.toString();
		}
	| s=additive_expression
	;
	
/*
operator: "target" refs_list
		| "result" (quoted_str COMMA)? val COMMA refs_list
		| "message" quoted_str (COMMA refs_list)?
		| "goto" INT_VALUE
		| "reset" (refs_list)?
		| "reset_deduced" (refs_list)?
		| "stop" (quoted_str (COMMA refs_list)?)?
		| "load" quoted_str (COMMA val COMMA val)?
		| "save" quoted_str COMMA val COMMA (val COMMA val COMMA)? refs_list
		;
*/

/* -------------------- LEXER ----------------- */
class ANTLRBaseModelLexer extends Lexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
}

DOT:		'.';
COMMA:		',';
COLON:		':';
SEMICOLON:	';';
LBRACE:		'(';
RBRACE:		')';
LBRACKET:	'[';
RBRACKET:	']';
MINUS:		'-';
PLUS:		'+';
MULT:		'*';
DIV:		'/';
OR:			'|';
AND:		'&';
EQ:			'=';
NEQ:		"!=";
NOT:		'!';
GE:			">=";
LE:			"<=";
LT:			'<';
GT:			'>';

ID options { testLiterals = true; }: LETTER (LETTER|DIGIT)*;

INT_VALUE: ('0' | ('1'..'9' ('0'..'9')*)) ('.' ('0'..'9')+ { _ttype = FLOAT_VALUE; })?;
QUOTED_STRING:	'"' (~('"'|'\n'|'\r'))* '"';
COMMENT_STRING:	'#'	(~('\n'|'\r'))*
		{ $setType(Token.SKIP); }
		;

WS: ((' ' | '\t' | "\r\n" { newline(); })+)
		{ $setType(Token.SKIP); }
	;

protected
LETTER:					('A'..'Z' | 'a'..'z' | '_');

protected
DIGIT:					('0'..'9');
