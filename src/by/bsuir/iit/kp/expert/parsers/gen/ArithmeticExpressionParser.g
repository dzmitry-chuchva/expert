header {
	package by.bsuir.iit.kp.expert.parsers.gen;
}

{
	import java.util.*;
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;
	import by.bsuir.iit.kp.expert.runtime.*;
	import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.*;
	import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions.*;
	import by.bsuir.iit.kp.expert.util.*;
}

/* -------------------- PARSER ----------------- */
class ArithmeticExpressionParser extends ANTLRBaseModelParser;
options {
   defaultErrorHandler = false;
}
{
	private ArithmeticFunctionsFactory afactory = ArithmeticFunctionsFactory.getInstance();
}

/* checking */
check[IReferencesSource checker] throws ModelParserException,ModelException
	: check_additive_expression[checker] SEMICOLON
	;
check_additive_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: check_mult_expression[checker] (PLUS check_mult_expression[checker] | MINUS check_mult_expression[checker])*
	;
check_mult_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: check_unary_expression[checker] (MULT check_unary_expression[checker] | DIV check_unary_expression[checker])*
	;
check_unary_expression[IReferencesSource checker] throws ModelParserException,ModelException
	{ String backup = null; boolean notFunction; int count; }
	: (PLUS | MINUS)? { backup = LT(1).getText(); } notFunction=check_primary_expression[checker] count=check_braces[checker]
		{
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
	;

check_braces[IReferencesSource checker] returns [int count] throws ModelParserException,ModelException
	: LBRACE count=check_argument_expression_list[checker] RBRACE
	| /* empty */ { count = -1; }
	;
check_argument_expression_list[IReferencesSource checker] returns [int count] throws ModelParserException,ModelException
	{ count = 0; }
	: check_additive_expression[checker] { count += 1; } (COMMA check_additive_expression[checker] { count += 1; })*
	| /* empty */
	;
	
/* returns true if parsed string is a reference/value, and false - if a function name */
check_primary_expression[IReferencesSource checker] returns [boolean b] throws ModelParserException,ModelException
	{ String r; b = true; }
	: r=ref
		{
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
		}
	| FLOAT_VALUE
	| INT_VALUE
	| LBRACE check_additive_expression[checker] RBRACE
	;


/* evaluating */	
execute[IReferencesEvaluator evaluator] returns [double res] throws ModelException
	: res=execute_additive_expression[evaluator] SEMICOLON
	;

execute_additive_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException
	{ double x; }
	: x=execute_mult_expression[evaluator] { res = x; }
	(PLUS x=execute_mult_expression[evaluator] { res += x; }
	| MINUS x=execute_mult_expression[evaluator] { res -= x; }
	)*
	;
execute_mult_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException
	{ double x; }
	: x=execute_unary_expression[evaluator] { res = x; }
	(MULT x=execute_unary_expression[evaluator] { res *= x; }
	| DIV x=execute_unary_expression[evaluator] { res /= x; }
	)*
	;
execute_unary_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException
	{ boolean negate = false; double x; List args = null; String backup = null; }
	: (PLUS | MINUS { negate = true; })? { backup = LT(1).getText(); } x=execute_primary_expression[evaluator] args=execute_braces[evaluator]
		{
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
		}
	;
execute_braces[IReferencesEvaluator evaluator] returns [List args] throws ModelException
	{ args = null; }
	: LBRACE args=execute_argument_expression_list[evaluator] RBRACE
	| /* empty */
	;
execute_argument_expression_list[IReferencesEvaluator evaluator] returns [List args] throws ModelException
	{ double x; args = new ArrayList(); }
	: x=execute_additive_expression[evaluator] { args.add(new Double(x)); } (COMMA x=execute_additive_expression[evaluator] { args.add(new Double(x)); })*
	| /* empty */
	;
execute_primary_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException
	{ String r; }
	: r=ref
		{
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
		}
	| f:FLOAT_VALUE { res = Double.parseDouble(f.getText()); }
	| i:INT_VALUE { res = Double.parseDouble(i.getText()); }
	| LBRACE res=execute_additive_expression[evaluator] RBRACE
	;
	
/* -------------------- LEXER ----------------- */
class ArithmeticExpressionLexer extends ANTLRBaseModelLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = ArithmeticExpressionParser;
}

protected DUMMY: ;