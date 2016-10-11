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
	import by.bsuir.iit.kp.expert.runtime.eval.logical.*;
	import by.bsuir.iit.kp.expert.runtime.eval.logical.functions.*;
	import by.bsuir.iit.kp.expert.util.*;
}

/* -------------------- PARSER ----------------- */
class LogicalExpressionParser extends ArithmeticExpressionParser;
options {
   defaultErrorHandler = false;
}
{
	private ArithmeticFunctionsFactory afactory = ArithmeticFunctionsFactory.getInstance();
	private LogicalFunctionsFactory lfactory = LogicalFunctionsFactory.getInstance();
}

/* checking */
check[IReferencesSource checker] throws ModelParserException,ModelException
	: check_or_expression[checker] SEMICOLON
	;
	
check_or_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: check_and_expression[checker] (OR check_and_expression[checker])*
	;
check_and_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: check_rel_expression[checker] (AND check_rel_expression[checker])*
	;
check_rel_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: check_primary_logical_expression[checker]
	((NEQ | EQ | LT | GT | LE | GE) check_primary_logical_expression[checker])*
	;
check_primary_logical_expression[IReferencesSource checker] throws ModelParserException,ModelException
	: NOT check_primary_logical_expression[checker]
	| LBRACE check_or_expression[checker] RBRACE
	| check_additive_expression[checker]
	;
	
/* evaluating */
execute[IReferencesEvaluator evaluator] returns [double res] throws ModelException, ModelParserException
	: res=execute_or_expression[evaluator] SEMICOLON
	;
	
execute_or_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException, ModelParserException
	{ double x; }
	: res=execute_and_expression[evaluator]
	(OR x=execute_and_expression[evaluator]
		{
			res = DoubleBoolean.or(res,x);
		}
	)*
	;
execute_and_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException, ModelParserException
	{ double x; }
	: res=execute_rel_expression[evaluator]
	(AND x=execute_rel_expression[evaluator]
		{
			res = DoubleBoolean.and(res,x);
		}	
	)*
	;
execute_rel_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException, ModelParserException
	{ double x; }
	: res=execute_primary_logical_expression[evaluator]
	(NEQ x=execute_primary_logical_expression[evaluator]
		{
			if (Math.abs(res - x) > Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}
	| EQ x=execute_primary_logical_expression[evaluator]
		{
			if (Math.abs(res - x) <= Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}	
	| LT x=execute_primary_logical_expression[evaluator]
		{
			if (x - res > Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}	
	| GT x=execute_primary_logical_expression[evaluator]
		{
			if (res - x > Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}	
	| LE x=execute_primary_logical_expression[evaluator]
		{
			if (x - res >= -Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}	
	| GE x=execute_primary_logical_expression[evaluator]
		{
			if (res - x >= -Constants.EPSILON) {
				res = DoubleBoolean.getTrueValue();
			} else {
				res = DoubleBoolean.getFalseValue();
			}
		}
	)*
		{
			res = DoubleBoolean.valueToBoolean(res);
		}
	;
execute_primary_logical_expression[IReferencesEvaluator evaluator] returns [double res] throws ModelException, ModelParserException
	: NOT res=execute_primary_logical_expression[evaluator]
		{
			res = DoubleBoolean.not(res);
		}
	| LBRACE res=execute_or_expression[evaluator] RBRACE
	| res=execute_additive_expression[evaluator]
	;
	
/* -------------------- LEXER ----------------- */
class LogicalExpressionLexer extends ArithmeticExpressionLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = LogicalExpressionParser;
}

protected DUMMY: ;