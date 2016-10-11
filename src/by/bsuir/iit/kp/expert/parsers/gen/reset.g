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
class ResetStatementParser extends ANTLRBaseModelParser;
options {
   defaultErrorHandler = false;
}
{
	private void resetValue(IReferencesEvaluator evaluator, String ref) throws ModelException {
		if (evaluator != null) {
			evaluator.reset(ref);
		} else {
			throw new ModelException("evaluator == null");
		}
	}
}

execute[IReferencesEvaluator evaluator] returns [IStatementExecutionResult res] throws ModelException, ModelParserException
	{ boolean fullReset = true; }
	: (execute_refs_list[evaluator] { fullReset = false; })? SEMICOLON
		{
			if (fullReset) {
				if (evaluator != null) {
					evaluator.resetAll();
				} else {
					throw new ModelException("evaluator == null");
				}
			}
			res = null;
		}
	;

execute_refs_list[IReferencesEvaluator evaluator] throws ModelException, ModelParserException
	{ String r; }
	: r=ref
		{
			resetValue(evaluator,r);
		}
	  (COMMA r=ref
		{
			resetValue(evaluator,r);
		}
	  )*
	;
	
check[IReferencesSource checker] throws ModelParserException, ModelException
	: (check_refs_list[checker])? SEMICOLON
	;
	
check_refs_list[IReferencesSource checker] throws ModelParserException, ModelException
	{ String r; }
	: r=ref
		{
			Utils.safeCheckReference(checker,r);
		}
	  (COMMA r=ref
	  	{
			Utils.safeCheckReference(checker,r);
		}
	  )*
	;
	
/* -------------------- LEXER ----------------- */
class ResetStatementLexer extends ANTLRBaseModelLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = ResetStatementParser;
}

protected DUMMY: ;