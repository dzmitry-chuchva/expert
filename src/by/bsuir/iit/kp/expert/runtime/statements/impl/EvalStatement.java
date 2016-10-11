package by.bsuir.iit.kp.expert.runtime.statements.impl;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.gen.EvalStatementLexer;
import by.bsuir.iit.kp.expert.parsers.gen.EvalStatementParser;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;

public class EvalStatement implements IStatement {

	public IStatementExecutionResult execute(String argLine, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		try {
			EvalStatementLexer lexer = new EvalStatementLexer(new StringReader(argLine + ";"));
			EvalStatementParser parser = new EvalStatementParser(lexer);
			return parser.execute(evaluator);
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		}
	}

	public void check(String argLine, IReferencesSource checker) throws ModelException, ModelParserException {
		try {
			EvalStatementLexer lexer = new EvalStatementLexer(new StringReader(argLine + ";"));
			EvalStatementParser parser = new EvalStatementParser(lexer);
			parser.check(checker);
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		}		
	}

}
