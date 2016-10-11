package by.bsuir.iit.kp.expert.parsers;

import java.io.InputStream;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.gen.ANTLRModelLexer;
import by.bsuir.iit.kp.expert.parsers.gen.ANTLRModelParser;
import by.bsuir.iit.kp.expert.presentation.Model;

public class ModelParser {

	public Model parse(InputStream input) throws ModelParserException {
		try {
			ANTLRModelLexer lexer = new ANTLRModelLexer(input);
			ANTLRModelParser parser = new ANTLRModelParser(lexer);
			return parser.model();
		} catch (RecognitionException e) {
			throw new ModelParserException(e.getLine(), e.getColumn(), e.getMessage());
		} catch (TokenStreamException e) {
			throw new ModelParserException(e.getMessage());
		}
	}

}
