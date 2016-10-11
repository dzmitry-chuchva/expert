// $ANTLR 2.7.7 (2006-11-01): "expandedANTLRModelParser.g" -> "ANTLRModelParser.java"$

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

	import java.util.*;
	import by.bsuir.iit.kp.expert.builders.*;
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;	

public class ANTLRModelParser extends antlr.LLkParser       implements ANTLRModelParserTokenTypes
 {

	private ModelBuilder builder = new ModelBuilder();
	private SymbolAttributeBuilder sBuilder = new SymbolAttributeBuilder();

protected ANTLRModelParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public ANTLRModelParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected ANTLRModelParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public ANTLRModelParser(TokenStream lexer) {
  this(lexer,1);
}

public ANTLRModelParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final Model  model() throws RecognitionException, TokenStreamException, ModelParserException {
		Model model;
		
		
		{
		int _cnt3=0;
		_loop3:
		do {
			if ((LA(1)==LITERAL_number||LA(1)==LITERAL_symbol)) {
				attribute_description();
			}
			else {
				if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt3++;
		} while (true);
		}
		{
		int _cnt5=0;
		_loop5:
		do {
			if ((LA(1)==LITERAL_rule)) {
				rule_description();
			}
			else {
				if ( _cnt5>=1 ) { break _loop5; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt5++;
		} while (true);
		}
		{
		int _cnt7=0;
		_loop7:
		do {
			if ((LA(1)==INT_VALUE)) {
				scenario_description();
			}
			else {
				if ( _cnt7>=1 ) { break _loop7; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt7++;
		} while (true);
		}
					
					model = builder.getModel();
				
		return model;
	}
	
	public final void attribute_description() throws RecognitionException, TokenStreamException, ModelParserException {
		
		
		switch ( LA(1)) {
		case LITERAL_number:
		{
			number_attribute_description();
			break;
		}
		case LITERAL_symbol:
		{
			symbol_attribute_description();
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
	}
	
	public final void rule_description() throws RecognitionException, TokenStreamException, ModelParserException {
		
		Token  i = null;
		String cond = null; String r; String c = "";
		
		match(LITERAL_rule);
		i = LT(1);
		match(ID);
		match(LITERAL_target);
		r=ref();
		{
		switch ( LA(1)) {
		case LITERAL_if:
		{
			cond=applicability_condition();
			break;
		}
		case INT_VALUE:
		case QUOTED_STRING:
		case LITERAL_rule:
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
		case QUOTED_STRING:
		{
			c=quoted_str();
			break;
		}
		case INT_VALUE:
		case LITERAL_rule:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
					Rule rule = new Rule(i.getText());
					rule.setTarget(r);
					rule.setApplicabilityExpression(cond);
					if (c != null) {
						rule.setActionString(c);
					}
					
					try {
						builder.addRule(rule);
					} catch (ModelException e) {
						throw new ModelParserException(LT(1).getLine(),e.getMessage());
					}
					//System.out.println(rule);	// debug
				
	}
	
	public final void scenario_description() throws RecognitionException, TokenStreamException, ModelParserException {
		
		Token  i = null;
		ScenarioStatement op; String ac = null;
		
		i = LT(1);
		match(INT_VALUE);
		match(COLON);
		op=operator();
		{
		switch ( LA(1)) {
		case LITERAL_if:
		{
			ac=applicability_condition();
			break;
		}
		case SEMICOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEMICOLON);
		
					op.setLabel(i.getText());
					op.setApplicabilityExpression(ac);
					
					try {
						builder.addScenarioStatement(op);
					} catch (ModelException e) {
						throw new ModelParserException(LT(1).getLine(),e.getMessage());
					}
					//System.out.println(op);	// debug
				
	}
	
	public final void number_attribute_description() throws RecognitionException, TokenStreamException, ModelParserException {
		
		Token  i = null;
		String fr = null; String to = null; String def = null; String c = null;
		
		match(LITERAL_number);
		match(LITERAL_attribute);
		i = LT(1);
		match(ID);
		{
		switch ( LA(1)) {
		case QUOTED_STRING:
		{
			c=quoted_str();
			break;
		}
		case LITERAL_number:
		case LITERAL_from:
		case LITERAL_to:
		case LITERAL_default:
		case LITERAL_symbol:
		case LITERAL_rule:
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
		case LITERAL_from:
		{
			match(LITERAL_from);
			fr=val();
			break;
		}
		case LITERAL_number:
		case LITERAL_to:
		case LITERAL_default:
		case LITERAL_symbol:
		case LITERAL_rule:
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
		case LITERAL_to:
		{
			match(LITERAL_to);
			to=val();
			break;
		}
		case LITERAL_number:
		case LITERAL_default:
		case LITERAL_symbol:
		case LITERAL_rule:
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
		case LITERAL_default:
		{
			match(LITERAL_default);
			def=val();
			break;
		}
		case LITERAL_number:
		case LITERAL_symbol:
		case LITERAL_rule:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
					double fromValue = 0.0;
					double toValue = 0.0;
					double defaultValue = 0.0;
					
					NumberAttribute attr = new NumberAttribute(i.getText());
					
					if (c != null) {
						attr.setDescription(c);
					}
				
					try {			
						if (fr != null) {
							fromValue = Double.parseDouble(fr);
						}
						if (to != null) {
							toValue = Double.parseDouble(to);
						}
						
		
						Constraints constraints = null;
						if (fr != null) {
							if (to != null) {
								constraints = new Constraints(fromValue,toValue);
							} else {
								constraints = new Constraints(true,fromValue);
							}
						} else {
							if (to != null) {
								constraints = new Constraints(false,toValue);
							} else {
								constraints = new Constraints();
							}
						}
						
						attr.setConstraints(constraints);
						
						if (def != null) {
							double defValue = Double.parseDouble(def);
							attr.setDefaultValue(defValue);
						}
						
						builder.addNumberAttribute(attr);
						//System.out.println(attr);	// debug
					} catch (ModelException e) {
						throw new ModelParserException(LT(1).getLine(),e.getMessage());
					}
				
	}
	
	public final void symbol_attribute_description() throws RecognitionException, TokenStreamException, ModelParserException {
		
		Token  i = null;
		sBuilder.reset(); String c = null;
		
		match(LITERAL_symbol);
		match(LITERAL_attribute);
		i = LT(1);
		match(ID);
		{
		switch ( LA(1)) {
		case QUOTED_STRING:
		{
			c=quoted_str();
			break;
		}
		case LITERAL_value:
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
		int _cnt17=0;
		_loop17:
		do {
			if ((LA(1)==LITERAL_value)) {
				symbol_description();
			}
			else {
				if ( _cnt17>=1 ) { break _loop17; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt17++;
		} while (true);
		}
		
					SymbolAttribute attr = sBuilder.getSymbolAttribute(i.getText());
					if (c != null) {
						attr.setDescription(c);
					}
				
					try {
						builder.addSymbolAttribute(attr);
					} catch (ModelException e) {
						throw new ModelParserException(LT(1).getLine(),e.getMessage());
					}
					//System.out.println(attr);	// debug
				
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
	
	public final Symbol  symbol_description() throws RecognitionException, TokenStreamException, ModelParserException {
		Symbol s;
		
		Token  i = null;
		Token  aid = null;
		String def = null; String c = null;
		
		match(LITERAL_value);
		i = LT(1);
		match(ID);
		{
		switch ( LA(1)) {
		case QUOTED_STRING:
		{
			c=quoted_str();
			break;
		}
		case LITERAL_number:
		case LITERAL_default:
		case LITERAL_symbol:
		case LITERAL_value:
		case LITERAL_assertion:
		case LITERAL_rule:
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
		case LITERAL_assertion:
		{
			match(LITERAL_assertion);
			match(LITERAL_name);
			aid = LT(1);
			match(ID);
			break;
		}
		case LITERAL_number:
		case LITERAL_default:
		case LITERAL_symbol:
		case LITERAL_value:
		case LITERAL_rule:
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
		case LITERAL_default:
		{
			match(LITERAL_default);
			def=val();
			break;
		}
		case LITERAL_number:
		case LITERAL_symbol:
		case LITERAL_value:
		case LITERAL_rule:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
					s = new Symbol(i.getText());
					if (c != null) {
						s.setDescription(c);
					}
					if (aid != null) {
						s.setAssertionName(aid.getText());
					}
					
					try {
						if (def != null) {
							double defaultValue = Double.parseDouble(def);
							s.setDefaultValue(defaultValue);
						}
						
						sBuilder.addSymbol(s);
					} catch (ModelException e) {
						throw new ModelParserException(LT(1).getLine(),e.getMessage());
					}	
				
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
	
	public final String  applicability_condition() throws RecognitionException, TokenStreamException {
		String s;
		
		
		match(LITERAL_if);
		s=logical_expression();
		return s;
	}
	
	public final ScenarioStatement  operator() throws RecognitionException, TokenStreamException, ModelParserException {
		ScenarioStatement op;
		
		Token  i = null;
		String argline = "";
		
		i = LT(1);
		match(ID);
		{
		switch ( LA(1)) {
		case PLUS:
		case MINUS:
		case FLOAT_VALUE:
		case INT_VALUE:
		case QUOTED_STRING:
		case ID:
		case LBRACKET:
		{
			argline=arg_list();
			break;
		}
		case LITERAL_if:
		case SEMICOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		
					String opid = i.getText();
					op = new ScenarioStatement(opid,argline);
				
		return op;
	}
	
	public final String  arg_list() throws RecognitionException, TokenStreamException {
		String argline;
		
		Token  c = null;
		StringBuffer argbuffer = new StringBuffer(); String ar;
		
		ar=arg();
		argbuffer.append(ar);
		{
		_loop38:
		do {
			if ((LA(1)==COMMA)) {
				c = LT(1);
				match(COMMA);
				ar=arg();
				argbuffer.append(c.getText() + ar);
			}
			else {
				break _loop38;
			}
			
		} while (true);
		}
		
					argline = argbuffer.toString();		
				
		return argline;
	}
	
	public final String  logical_expression() throws RecognitionException, TokenStreamException {
		String s;
		
		
		s=or_expression();
		return s;
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
		_loop42:
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
				break _loop42;
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
		_loop45:
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
				break _loop45;
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
		case INT_VALUE:
		case QUOTED_STRING:
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
		case SEMICOLON:
		case LITERAL_rule:
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
			_loop51:
			do {
				if ((LA(1)==COMMA)) {
					c = LT(1);
					match(COMMA);
					e=additive_expression();
					buffer.append(c.getText()); buffer.append(e);
				}
				else {
					break _loop51;
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
		_loop56:
		do {
			if ((LA(1)==OR)) {
				o = LT(1);
				match(OR);
				e=and_expression();
				buffer.append(o.getText()); buffer.append(e);
			}
			else {
				break _loop56;
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
		_loop59:
		do {
			if ((LA(1)==AND)) {
				a = LT(1);
				match(AND);
				e=rel_expression();
				buffer.append(a.getText()); buffer.append(e);
			}
			else {
				break _loop59;
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
		_loop63:
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
				break _loop63;
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
		"DIGIT",
		"\"number\"",
		"\"attribute\"",
		"\"from\"",
		"\"to\"",
		"\"default\"",
		"\"symbol\"",
		"\"value\"",
		"\"assertion\"",
		"\"name\"",
		"\"rule\"",
		"\"target\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 134384L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
