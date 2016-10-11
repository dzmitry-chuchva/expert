header {
	package by.bsuir.iit.kp.expert.parsers.gen;
}

{
	import java.util.*;
	import by.bsuir.iit.kp.expert.parsers.*;
	import by.bsuir.iit.kp.expert.presentation.*;
	import by.bsuir.iit.kp.expert.presentation.base.*;	
	import by.bsuir.iit.kp.expert.runtime.*;
	import by.bsuir.iit.kp.expert.runtime.statements.*;
	import by.bsuir.iit.kp.expert.ui.*;
	import by.bsuir.iit.kp.expert.exceptions.*;
	import by.bsuir.iit.kp.expert.util.*;	
}

/* -------------------- PARSER ----------------- */
class PrintStatementParser extends ANTLRBaseModelParser;
options {
   defaultErrorHandler = false;
}
{
	private void displayMessageSimple(IReferencesEvaluator evaluator, ValuableIdentificator valuable, String message, Double level) throws ModelException {
		IUserInterface ui = evaluator.getUserInterface();
		if (valuable.isValueReady()) {
			double value = valuable.getValue();
			if (level != null) {
				if (value >= level.doubleValue()) {
					ui.showMessage(MessageUtils.getStringFromTemplate(message,valuable));
				}
			} else {
				ui.showMessage(MessageUtils.getStringFromTemplate(message,valuable));
			}
		}
	}
	
	private void displayMessage(IReferencesEvaluator evaluator, String ref, String message, Double level) throws ModelException {
		Utils.safeEvaluateReference(evaluator,ref);
		
		IUserInterface ui = evaluator.getUserInterface();
		Identificator identificator = evaluator.getReference(ref);
		if (identificator instanceof ValuableIdentificator) {
			ValuableIdentificator valuable = (ValuableIdentificator)identificator;
			displayMessageSimple(evaluator,valuable,message,level);
		} else if (identificator instanceof ComplexIdentificator) {
			ComplexIdentificator complex = (ComplexIdentificator)identificator;
			List children = complex.getChildren();
			for (Iterator it = children.iterator(); it.hasNext(); ) {
				ValuableIdentificator child = (ValuableIdentificator)it.next();
				displayMessageSimple(evaluator,child,message,level);
			}
		}


	}
}

execute[IReferencesEvaluator evaluator] returns [IStatementExecutionResult res] throws ModelException, ModelParserException
	{ String m; String v = null; Double level = null; boolean withArguments = false; }
	: m=quoted_str (COMMA (v=val COMMA)?
		{
			if (v != null) {
				try {
					level = new Double(v);
				} catch (NumberFormatException e) {
					throw new ModelParserException(v + ": not a valid floating point value");
				}
			}
			withArguments = true;
		}
	execute_refs_list[evaluator,m,level])? SEMICOLON
		{
			if (!withArguments) {
				if (evaluator != null) {
					IUserInterface ui = evaluator.getUserInterface();
					ui.showMessage(m);
				} else {
					throw new ModelException("evaluator == null");
				}
			}
			res = null;
		}
	;
	
execute_refs_list[IReferencesEvaluator evaluator, String messagePattern, Double level] throws ModelException, ModelParserException
	{ String r; }
	: r=ref
		{
			displayMessage(evaluator,r,messagePattern,level);
		}
	  (COMMA r=ref
		{
			displayMessage(evaluator,r,messagePattern,level);
		}
	  )*
	;
	
check[IReferencesSource checker] throws ModelParserException, ModelException
	{ String v = null; }
	: quoted_str (COMMA (v=val COMMA)?
		{
			if (v != null) {
				try {
					Double.parseDouble(v);
				} catch (NumberFormatException e) {
					throw new ModelParserException(v + ": not a valid floating point value");
				}
			}
		}
	check_refs_list[checker])? SEMICOLON
	;
	
check_refs_list[IReferencesSource checker] throws ModelException, ModelParserException
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
class PrintStatementLexer extends ANTLRBaseModelLexer;
options {
	k = 2;
	caseSensitiveLiterals = false;
	charVocabulary = '\u0003'..'\u00FF';
	testLiterals = false;
	importVocab = PrintStatementParser;
}

protected DUMMY: ;