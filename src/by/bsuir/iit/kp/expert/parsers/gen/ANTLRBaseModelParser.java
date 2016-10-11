// $ANTLR 2.7.7 (2006-11-01): "ANTLRBaseModelParser.g" -> "ANTLRBaseModelParser.java"$

	package by.bsuir.iit.kp.expert.parsers.gen;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class ANTLRBaseModelParser extends antlr.LLkParser       implements ANTLRBaseModelParserTokenTypes
 {

protected ANTLRBaseModelParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public ANTLRBaseModelParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected ANTLRBaseModelParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public ANTLRBaseModelParser(TokenStream lexer) {
  this(lexer,1);
}

public ANTLRBaseModelParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final String  val() throws RecognitionException, TokenStreamException {
		String v;
		
		Token  p = null;
		Token  m = null;
		Token  f = null;
		Token  i = null;
		String sign = ""; String vvalue = "";
		
		{
		switch ( LA(1)) {
		case PLUS:
		{
			p = LT(1);
			match(PLUS);
			sign = p.getText();
			break;
		}
		case MINUS:
		{
			m = LT(1);
			match(MINUS);
			sign = m.getText();
			break;
		}
		case FLOAT_VALUE:
		case INT_VALUE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case FLOAT_VALUE:
		{
			f = LT(1);
			match(FLOAT_VALUE);
			vvalue = f.getText();
			break;
		}
		case INT_VALUE:
		{
			i = LT(1);
			match(INT_VALUE);
			vvalue = i.getText();
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
			  	v = sign + vvalue;
			
		return v;
	}
	
	public final String  quoted_str() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  q = null;
		
		q = LT(1);
		match(QUOTED_STRING);
		
					StringBuffer buffer = new StringBuffer(q.getText());
					buffer.deleteCharAt(0);
					buffer.deleteCharAt(buffer.length() - 1);
					s = buffer.toString();
				
		return s;
	}
	
	public final String  applicability_condition() throws RecognitionException, TokenStreamException {
		String s;
		
		
		match(LITERAL_if);
		s=logical_expression();
		return s;
	}
	
	public final String  logical_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		
		s=or_expression();
		return s;
	}
	
	public final String  ref() throws RecognitionException, TokenStreamException {
		String rf;
		
		Token  i = null;
		Token  l = null;
		Token  ii = null;
		Token  d = null;
		Token  iii = null;
		Token  r = null;
		
		switch ( LA(1)) {
		case ID:
		{
			i = LT(1);
			match(ID);
			rf = i.getText();
			break;
		}
		case LBRACKET:
		{
			l = LT(1);
			match(LBRACKET);
			ii = LT(1);
			match(ID);
			d = LT(1);
			match(DOT);
			iii = LT(1);
			match(ID);
			r = LT(1);
			match(RBRACKET);
			
						rf = l.getText() + ii.getText() + d.getText() +
							iii.getText() + r.getText();
					
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return rf;
	}
	
	public final String  arg() throws RecognitionException, TokenStreamException {
		String ar;
		
		
		switch ( LA(1)) {
		case ID:
		case LBRACKET:
		{
			ar=ref();
			break;
		}
		case QUOTED_STRING:
		{
			ar=quoted_str();
			ar = "\"" + ar + "\"";
			break;
		}
		case PLUS:
		case MINUS:
		case FLOAT_VALUE:
		case INT_VALUE:
		{
			ar=val();
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return ar;
	}
	
	public final String  arg_list() throws RecognitionException, TokenStreamException {
		String argline;
		
		Token  c = null;
		StringBuffer argbuffer = new StringBuffer(); String ar;
		
		ar=arg();
		argbuffer.append(ar);
		{
		_loop10:
		do {
			if ((LA(1)==COMMA)) {
				c = LT(1);
				match(COMMA);
				ar=arg();
				argbuffer.append(c.getText() + ar);
			}
			else {
				break _loop10;
			}
			
		} while (true);
		}
		
					argline = argbuffer.toString();		
				
		return argline;
	}
	
	public final String  arithmetical_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		
		s=additive_expression();
		return s;
	}
	
	public final String  additive_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  p = null;
		Token  m = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		e=mult_expression();
		buffer.append(e);
		{
		_loop14:
		do {
			switch ( LA(1)) {
			case PLUS:
			{
				p = LT(1);
				match(PLUS);
				e=mult_expression();
				buffer.append(p.getText()); buffer.append(e);
				break;
			}
			case MINUS:
			{
				m = LT(1);
				match(MINUS);
				e=mult_expression();
				buffer.append(m.getText()); buffer.append(e);
				break;
			}
			default:
			{
				break _loop14;
			}
			}
		} while (true);
		}
		
					s = buffer.toString();
				
		return s;
	}
	
	public final String  mult_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  m = null;
		Token  d = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		e=unary_expression();
		buffer.append(e);
		{
		_loop17:
		do {
			switch ( LA(1)) {
			case MULT:
			{
				m = LT(1);
				match(MULT);
				e=unary_expression();
				buffer.append(m.getText()); buffer.append(e);
				break;
			}
			case DIV:
			{
				d = LT(1);
				match(DIV);
				e=unary_expression();
				buffer.append(d.getText()); buffer.append(e);
				break;
			}
			default:
			{
				break _loop17;
			}
			}
		} while (true);
		}
		
					s = buffer.toString();
				
		return s;
	}
	
	public final String  unary_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  p = null;
		Token  m = null;
		StringBuffer buffer = new StringBuffer(); String e; String b;
		
		{
		switch ( LA(1)) {
		case PLUS:
		{
			p = LT(1);
			match(PLUS);
			break;
		}
		case MINUS:
		{
			m = LT(1);
			match(MINUS);
			break;
		}
		case FLOAT_VALUE:
		case INT_VALUE:
		case ID:
		case LBRACKET:
		case LBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		e=primary_expression();
		b=braces();
		
					if (p != null) {
						buffer.append(p.getText());
					}
					
					if (m != null) {
						buffer.append(m.getText());
					}
					
					buffer.append(e);
					buffer.append(b);
					s = buffer.toString();
				
		return s;
	}
	
	public final String  primary_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  f = null;
		Token  i = null;
		Token  l = null;
		Token  r = null;
		String e;
		
		switch ( LA(1)) {
		case ID:
		case LBRACKET:
		{
			s=ref();
			break;
		}
		case FLOAT_VALUE:
		{
			f = LT(1);
			match(FLOAT_VALUE);
			s = f.getText();
			break;
		}
		case INT_VALUE:
		{
			i = LT(1);
			match(INT_VALUE);
			s = i.getText();
			break;
		}
		case LBRACE:
		{
			l = LT(1);
			match(LBRACE);
			e=additive_expression();
			r = LT(1);
			match(RBRACE);
			
						StringBuffer buffer = new StringBuffer();
						buffer.append(l.getText());
						buffer.append(e);
						buffer.append(r.getText());
						s = buffer.toString();
					
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return s;
	}
	
	public final String  braces() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  l = null;
		Token  r = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		switch ( LA(1)) {
		case LBRACE:
		{
			l = LT(1);
			match(LBRACE);
			e=argument_expression_list();
			r = LT(1);
			match(RBRACE);
			
						buffer.append(l.getText());
						buffer.append(e);
						buffer.append(r.getText());
						s = buffer.toString();
					
			break;
		}
		case EOF:
		case PLUS:
		case MINUS:
		case COMMA:
		case MULT:
		case DIV:
		case RBRACE:
		case OR:
		case AND:
		case NEQ:
		case EQ:
		case LT:
		case GT:
		case LE:
		case GE:
		{
			
						s = "";
					
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return s;
	}
	
	public final String  argument_expression_list() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  c = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		switch ( LA(1)) {
		case PLUS:
		case MINUS:
		case FLOAT_VALUE:
		case INT_VALUE:
		case ID:
		case LBRACKET:
		case LBRACE:
		{
			e=additive_expression();
			buffer.append(e);
			{
			_loop23:
			do {
				if ((LA(1)==COMMA)) {
					c = LT(1);
					match(COMMA);
					e=additive_expression();
					buffer.append(c.getText()); buffer.append(e);
				}
				else {
					break _loop23;
				}
				
			} while (true);
			}
			
						s = buffer.toString();
					
			break;
		}
		case RBRACE:
		{
			
						s = "";
					
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return s;
	}
	
	public final String  or_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  o = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		e=and_expression();
		buffer.append(e);
		{
		_loop28:
		do {
			if ((LA(1)==OR)) {
				o = LT(1);
				match(OR);
				e=and_expression();
				buffer.append(o.getText()); buffer.append(e);
			}
			else {
				break _loop28;
			}
			
		} while (true);
		}
		
					s = buffer.toString();
				
		return s;
	}
	
	public final String  and_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  a = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		e=rel_expression();
		buffer.append(e);
		{
		_loop31:
		do {
			if ((LA(1)==AND)) {
				a = LT(1);
				match(AND);
				e=rel_expression();
				buffer.append(a.getText()); buffer.append(e);
			}
			else {
				break _loop31;
			}
			
		} while (true);
		}
		
					s = buffer.toString();
				
		return s;
	}
	
	public final String  rel_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		StringBuffer buffer = new StringBuffer(); String e;
		
		e=primary_logical_expression();
		buffer.append(e);
		{
		_loop35:
		do {
			if (((LA(1) >= NEQ && LA(1) <= GE))) {
				buffer.append(LT(1).getText());
				{
				switch ( LA(1)) {
				case NEQ:
				{
					match(NEQ);
					break;
				}
				case EQ:
				{
					match(EQ);
					break;
				}
				case LT:
				{
					match(LT);
					break;
				}
				case GT:
				{
					match(GT);
					break;
				}
				case LE:
				{
					match(LE);
					break;
				}
				case GE:
				{
					match(GE);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				e=primary_logical_expression();
				buffer.append(e);
			}
			else {
				break _loop35;
			}
			
		} while (true);
		}
		
					s = buffer.toString();
				
		return s;
	}
	
	public final String  primary_logical_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		Token  n = null;
		Token  l = null;
		Token  r = null;
		StringBuffer buffer = new StringBuffer(); String e;
		
		if ((LA(1)==NOT)) {
			n = LT(1);
			match(NOT);
			e=primary_logical_expression();
			
						buffer.append(n.getText()); buffer.append(e);
						s = buffer.toString();
					
		}
		else if ((LA(1)==LBRACE)) {
			l = LT(1);
			match(LBRACE);
			e=logical_expression();
			r = LT(1);
			match(RBRACE);
			
						buffer.append(l.getText());
						buffer.append(e);
						buffer.append(r.getText());
						s = buffer.toString();
					
		}
		else if ((_tokenSet_0.member(LA(1)))) {
			s=additive_expression();
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		return s;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"PLUS",
		"MINUS",
		"FLOAT_VALUE",
		"INT_VALUE",
		"QUOTED_STRING",
		"\"if\"",
		"ID",
		"LBRACKET",
		"DOT",
		"RBRACKET",
		"COMMA",
		"MULT",
		"DIV",
		"LBRACE",
		"RBRACE",
		"OR",
		"AND",
		"NEQ",
		"EQ",
		"LT",
		"GT",
		"LE",
		"GE",
		"NOT",
		"COLON",
		"SEMICOLON",
		"COMMENT_STRING",
		"WS",
		"LETTER",
		"DIGIT"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 134384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
