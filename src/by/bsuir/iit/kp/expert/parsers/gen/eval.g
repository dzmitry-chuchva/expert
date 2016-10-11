header {
	package by.bsuir.iit.kp.expert.parsers.gen;
}

{
	import java.util.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;	
	import by.bsuir.iit.kp.expert.runtime.*;
	import by.bsuir.iit.kp.expert.runtime.statements.*;
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.util.*;	
}

/* -------------------- PARSER ----------------- */
class EvalStatementParser extends ANTLRBaseModelParser;
options {
   defaultErrorHandler = false;
}

execute[IReferencesEvaluator evaluator] returns [IStatementExecutionResult res] throws ModelException, ModelParserException
	{ String r; }
	: r=ref
		{
			Utils.safeEvaluateReference(evaluator,r);
		}
	  (COMMA r=ref
		{
			Utils.safeEvaluateReference(evaluator,r);
		}
	  )* SEMICOLON
	  	{
	  		res = null;
	  	}
	;
	
check[IReferencesSource checker] throws ModelParserException, ModelException
	{ String r; }
	: r=ref
		{
			Utils.safeCheckReference(checker,r);
		}
	  (COMMA r=ref
	  	{
			Utils.safeCheckReference(checker,r);
		}
	  )* SEMICOLON
	;
	
/* -------------------- LEXER ----------------- */
class EvalStatementLexer extends ANTLRBaseModelLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = EvalStatementParser;
}

protected DUMMY: ;