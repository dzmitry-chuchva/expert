package by.bsuir.iit.kp.expert.runtime.eval.logical;

import java.io.StringReader;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.gen.LogicalExpressionLexer;
import by.bsuir.iit.kp.expert.parsers.gen.LogicalExpressionParser;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;


public class LogicalExpressionEvaluator {
	
	public static double evaluate(String expression, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		try {
			String fullExpression = expression + ";";
			LogicalExpressionLexer lexer = new LogicalExpressionLexer(new StringReader(fullExpression));
			LogicalExpressionParser parser = new LogicalExpressionParser(lexer);
			return parser.execute(evaluator);
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		}
	}
	
	public static void check(String expression,IReferencesSource checker) throws ModelException, ModelParserException {
		try {
			String fullExpression = expression + ";";			
			LogicalExpressionLexer lexer = new LogicalExpressionLexer(new StringReader(fullExpression));
			LogicalExpressionParser parser = new LogicalExpressionParser(lexer);
			parser.check(checker);
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getMessage());
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		}			
	}

}
