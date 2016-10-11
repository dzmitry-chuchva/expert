// $ANTLR 2.7.7 (2006-11-01): "expandedLogicalExpressionParser.g" -> "LogicalExpressionParser.java"$

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
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;
	import by.bsuir.iit.kp.expert.runtime.*;
	import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.*;
	import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions.*;
	import by.bsuir.iit.kp.expert.runtime.eval.logical.*;
	import by.bsuir.iit.kp.expert.runtime.eval.logical.functions.*;
	import by.bsuir.iit.kp.expert.util.*;

public class LogicalExpressionParser extends antlr.LLkParser       implements LogicalExpressionParserTokenTypes
 {

	private ArithmeticFunctionsFactory afactory = ArithmeticFunctionsFactory.getInstance();
	private LogicalFunctionsFactory lfactory = LogicalFunctionsFactory.getInstance();

protected LogicalExpressionParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public LogicalExpressionParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected LogicalExpressionParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public LogicalExpressionParser(TokenStream lexer) {
  this(lexer,1);
}

public LogicalExpressionParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void check(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_or_expression(checker);
		match(SEMICOLON);
	}
	
	public final void check_or_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_and_expression(checker);
		{
		_loop4:
		do {
			if ((LA(1)==OR)) {
				match(OR);
				check_and_expression(checker);
			}
			else {
				break _loop4;
			}
			
		} while (true);
		}
	}
	
	public final void check_and_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_rel_expression(checker);
		{
		_loop7:
		do {
			if ((LA(1)==AND)) {
				match(AND);
				check_rel_expression(checker);
			}
			else {
				break _loop7;
			}
			
		} while (true);
		}
	}
	
	public final void check_rel_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_primary_logical_expression(checker);
		{
		_loop11:
		do {
			if (((LA(1) >= NEQ && LA(1) <= GE))) {
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
				check_primary_logical_expression(checker);
			}
			else {
				break _loop11;
			}
			
		} while (true);
		}
	}
	
	public final void check_primary_logical_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		if ((LA(1)==NOT)) {
			match(NOT);
			check_primary_logical_expression(checker);
		}
		else if ((LA(1)==LBRACE)) {
			match(LBRACE);
			check_or_expression(checker);
			match(RBRACE);
		}
		else if ((_tokenSet_0.member(LA(1)))) {
			check_additive_expression(checker);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
	}
	
	public final void check_additive_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_mult_expression(checker);
		{
		_loop26:
		do {
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				check_mult_expression(checker);
				break;
			}
			case MINUS:
			{
				match(MINUS);
				check_mult_expression(checker);
				break;
			}
			default:
			{
				break _loop26;
			}
			}
		} while (true);
		}
	}
	
	public final double  execute(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException,ModelParserException {
		double res;
		
		
		res=execute_or_expression(evaluator);
		match(SEMICOLON);
		return res;
	}
	
	public final double  execute_or_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException,ModelParserException {
		double res;
		
		double x;
		
		res=execute_and_expression(evaluator);
		{
		_loop16:
		do {
			if ((LA(1)==OR)) {
				match(OR);
				x=execute_and_expression(evaluator);
				
							res = DoubleBoolean.or(res,x);
						
			}
			else {
				break _loop16;
			}
			
		} while (true);
		}
		return res;
	}
	
	public final double  execute_and_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException,ModelParserException {
		double res;
		
		double x;
		
		res=execute_rel_expression(evaluator);
		{
		_loop19:
		do {
			if ((LA(1)==AND)) {
				match(AND);
				x=execute_rel_expression(evaluator);
				
							res = DoubleBoolean.and(res,x);
						
			}
			else {
				break _loop19;
			}
			
		} while (true);
		}
		return res;
	}
	
	public final double  execute_rel_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException,ModelParserException {
		double res;
		
		double x;
		
		res=execute_primary_logical_expression(evaluator);
		{
		_loop22:
		do {
			switch ( LA(1)) {
			case NEQ:
			{
				match(NEQ);
				x=execute_primary_logical_expression(evaluator);
				
							if (Math.abs(res - x) > Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			case EQ:
			{
				match(EQ);
				x=execute_primary_logical_expression(evaluator);
				
							if (Math.abs(res - x) <= Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			case LT:
			{
				match(LT);
				x=execute_primary_logical_expression(evaluator);
				
							if (x - res > Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			case GT:
			{
				match(GT);
				x=execute_primary_logical_expression(evaluator);
				
							if (res - x > Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			case LE:
			{
				match(LE);
				x=execute_primary_logical_expression(evaluator);
				
							if (x - res >= -Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			case GE:
			{
				match(GE);
				x=execute_primary_logical_expression(evaluator);
				
							if (res - x >= -Constants.EPSILON) {
								res = DoubleBoolean.getTrueValue();
							} else {
								res = DoubleBoolean.getFalseValue();
							}
						
				break;
			}
			default:
			{
				break _loop22;
			}
			}
		} while (true);
		}
		
					res = DoubleBoolean.valueToBoolean(res);
				
		return res;
	}
	
	public final double  execute_primary_logical_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException,ModelParserException {
		double res;
		
		
		if ((LA(1)==NOT)) {
			match(NOT);
			res=execute_primary_logical_expression(evaluator);
			
						res = DoubleBoolean.not(res);
					
		}
		else if ((LA(1)==LBRACE)) {
			match(LBRACE);
			res=execute_or_expression(evaluator);
			match(RBRACE);
		}
		else if ((_tokenSet_0.member(LA(1)))) {
			res=execute_additive_expression(evaluator);
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		return res;
	}
	
	public final double  execute_additive_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		double res;
		
		double x;
		
		x=execute_mult_expression(evaluator);
		res = x;
		{
		_loop39:
		do {
			switch ( LA(1)) {
			case PLUS:
			{
				match(PLUS);
				x=execute_mult_expression(evaluator);
				res += x;
				break;
			}
			case MINUS:
			{
				match(MINUS);
				x=execute_mult_expression(evaluator);
				res -= x;
				break;
			}
			default:
			{
				break _loop39;
			}
			}
		} while (true);
		}
		return res;
	}
	
	public final void check_mult_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		
		check_unary_expression(checker);
		{
		_loop29:
		do {
			switch ( LA(1)) {
			case MULT:
			{
				match(MULT);
				check_unary_expression(checker);
				break;
			}
			case DIV:
			{
				match(DIV);
				check_unary_expression(checker);
				break;
			}
			default:
			{
				break _loop29;
			}
			}
		} while (true);
		}
	}
	
	public final void check_unary_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		
		String backup = null; boolean notFunction; int count;
		
		{
		switch ( LA(1)) {
		case PLUS:
		{
			match(PLUS);
			break;
		}
		case MINUS:
		{
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
		backup = LT(1).getText();
		notFunction=check_primary_expression(checker);
		count=check_braces(checker);
		
					if (notFunction && count >= 0) {
						throw new ModelParserException(backup + ": not a function");
					} else if (!notFunction) {
						if (count < 0) {
							throw new ModelParserException(backup + ": is a function, pass arguments");
						} else {
							IArithmeticFunction function = afactory.getFunction(backup);
							int argCount = function.getArgumentsCount();
							if (argCount != IArithmeticFunction.VARIABLE_ARGUMENT_COUNT) {
								if (argCount != count) {
									throw new ModelParserException(backup + ": function takes " + argCount + " parameters, but it was passed " + count + " parameters");
								}
							}
						}
					}
				
	}
	
	public final boolean  check_primary_expression(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		boolean b;
		
		String r; b = true;
		
		switch ( LA(1)) {
		case ID:
		case LBRACKET:
		{
			r=ref();
			
						IArithmeticFunction function = afactory.getFunction(r);
						if (function != null) {
							b = false;
						} else {
							if (checker == null) {
								throw new ModelParserException("checker = null");
							}
							
							if (!checker.referenceExists(r)) {
								throw new ModelParserException("undefined reference: " + r);
							} else {
								Identificator identificator = checker.getReference(r);
								if (!(identificator instanceof ValuableIdentificator)) {
									throw new ModelParserException(r + ": using of symbol attributes in expressions is forbidden because they does not have value");
								}
							}
						}	
					
			break;
		}
		case FLOAT_VALUE:
		{
			match(FLOAT_VALUE);
			break;
		}
		case INT_VALUE:
		{
			match(INT_VALUE);
			break;
		}
		case LBRACE:
		{
			match(LBRACE);
			check_additive_expression(checker);
			match(RBRACE);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return b;
	}
	
	public final int  check_braces(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		int count;
		
		
		switch ( LA(1)) {
		case LBRACE:
		{
			match(LBRACE);
			count=check_argument_expression_list(checker);
			match(RBRACE);
			break;
		}
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
		case SEMICOLON:
		{
			count = -1;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return count;
	}
	
	public final int  check_argument_expression_list(
		IReferencesSource checker
	) throws RecognitionException, TokenStreamException, ModelParserException,ModelException {
		int count;
		
		count = 0;
		
		switch ( LA(1)) {
		case PLUS:
		case MINUS:
		case FLOAT_VALUE:
		case INT_VALUE:
		case ID:
		case LBRACKET:
		case LBRACE:
		{
			check_additive_expression(checker);
			count += 1;
			{
			_loop35:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					check_additive_expression(checker);
					count += 1;
				}
				else {
					break _loop35;
				}
				
			} while (true);
			}
			break;
		}
		case RBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return count;
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
	
	public final double  execute_mult_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		double res;
		
		double x;
		
		x=execute_unary_expression(evaluator);
		res = x;
		{
		_loop42:
		do {
			switch ( LA(1)) {
			case MULT:
			{
				match(MULT);
				x=execute_unary_expression(evaluator);
				res *= x;
				break;
			}
			case DIV:
			{
				match(DIV);
				x=execute_unary_expression(evaluator);
				res /= x;
				break;
			}
			default:
			{
				break _loop42;
			}
			}
		} while (true);
		}
		return res;
	}
	
	public final double  execute_unary_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		double res;
		
		boolean negate = false; double x; List args = null; String backup = null;
		
		{
		switch ( LA(1)) {
		case PLUS:
		{
			match(PLUS);
			break;
		}
		case MINUS:
		{
			match(MINUS);
			negate = true;
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
		backup = LT(1).getText();
		x=execute_primary_expression(evaluator);
		args=execute_braces(evaluator);
		
					IArithmeticFunction function = afactory.getFunction(backup);
					if (args != null) {
						if (function == null) {
							throw new ModelException(backup + ": not a function");
						} else {
							double[] dargs = Utils.getPrimitiveDoubleList(args);
							res = function.execute(dargs);
						}
					}
					else {
						if (function != null) {
							throw new ModelException(backup + ": is a function, pass arguments");
						} else {
							res = x;
						}
					}
					
					if (negate) {
						res = -res;
					}
				
		return res;
	}
	
	public final double  execute_primary_expression(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		double res;
		
		Token  f = null;
		Token  i = null;
		String r;
		
		switch ( LA(1)) {
		case ID:
		case LBRACKET:
		{
			r=ref();
			
						IArithmeticFunction function = afactory.getFunction(r);
						if (function != null) {
							res = 0.0;
						} else {
							if (evaluator != null) {
								Identificator identificator = evaluator.getReference(r);
								if (identificator instanceof ValuableIdentificator) {
									res = evaluator.evaluate(r);
								} else {
									throw new ModelException(r + ": evaluating of symbol attributes is undefined");
								}
							} else {
								throw new ModelException("evaluator = null");
							}
						}
					
			break;
		}
		case FLOAT_VALUE:
		{
			f = LT(1);
			match(FLOAT_VALUE);
			res = Double.parseDouble(f.getText());
			break;
		}
		case INT_VALUE:
		{
			i = LT(1);
			match(INT_VALUE);
			res = Double.parseDouble(i.getText());
			break;
		}
		case LBRACE:
		{
			match(LBRACE);
			res=execute_additive_expression(evaluator);
			match(RBRACE);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return res;
	}
	
	public final List  execute_braces(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		List args;
		
		args = null;
		
		switch ( LA(1)) {
		case LBRACE:
		{
			match(LBRACE);
			args=execute_argument_expression_list(evaluator);
			match(RBRACE);
			break;
		}
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
		case SEMICOLON:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return args;
	}
	
	public final List  execute_argument_expression_list(
		IReferencesEvaluator evaluator
	) throws RecognitionException, TokenStreamException, ModelException {
		List args;
		
		double x; args = new ArrayList();
		
		switch ( LA(1)) {
		case PLUS:
		case MINUS:
		case FLOAT_VALUE:
		case INT_VALUE:
		case ID:
		case LBRACKET:
		case LBRACE:
		{
			x=execute_additive_expression(evaluator);
			args.add(new Double(x));
			{
			_loop48:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					x=execute_additive_expression(evaluator);
					args.add(new Double(x));
				}
				else {
					break _loop48;
				}
				
			} while (true);
			}
			break;
		}
		case RBRACE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		return args;
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
		_loop59:
		do {
			if ((LA(1)==COMMA)) {
				c = LT(1);
				match(COMMA);
				ar=arg();
				argbuffer.append(c.getText() + ar);
			}
			else {
				break _loop59;
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
		_loop63:
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
				break _loop63;
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
		_loop66:
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
				break _loop66;
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
			_loop72:
			do {
				if ((LA(1)==COMMA)) {
					c = LT(1);
					match(COMMA);
					e=additive_expression();
					buffer.append(c.getText()); buffer.append(e);
				}
				else {
					break _loop72;
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
		_loop77:
		do {
			if ((LA(1)==OR)) {
				o = LT(1);
				match(OR);
				e=and_expression();
				buffer.append(o.getText()); buffer.append(e);
			}
			else {
				break _loop77;
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
		_loop80:
		do {
			if ((LA(1)==AND)) {
				a = LT(1);
				match(AND);
				e=rel_expression();
				buffer.append(a.getText()); buffer.append(e);
			}
			else {
				break _loop80;
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
		_loop84:
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
				break _loop84;
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
