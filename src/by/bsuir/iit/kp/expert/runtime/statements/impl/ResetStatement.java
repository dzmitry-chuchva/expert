package by.bsuir.iit.kp.expert.runtime.statements.impl;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.gen.ResetStatementLexer;
import by.bsuir.iit.kp.expert.parsers.gen.ResetStatementParser;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;

public class ResetStatement implements IStatement {

	public void check(String argLine, IReferencesSource checker)
			throws ModelException, ModelParserException {
		try {
			ResetStatementLexer lexer = new ResetStatementLexer(new StringReader(argLine + ";"));
			ResetStatementParser parser = new ResetStatementParser(lexer);
			parser.check(checker);
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		}
	}

	public IStatementExecutionResult execute(String argLine,
			IReferencesEvaluator evaluator) throws ModelException,
			ModelParserException {
		try {
			ResetStatementLexer lexer = new ResetStatementLexer(new StringReader(argLine + ";"));
			ResetStatementParser parser = new ResetStatementParser(lexer);
			return parser.execute(evaluator);
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		}
	}

}
