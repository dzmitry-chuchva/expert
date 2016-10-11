package by.bsuir.iit.kp.expert.test.parsers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.ModelParser;
import by.bsuir.iit.kp.expert.presentation.Model;

public class ModelParserTest extends TestCase {
	
	public static final String TEST_RESOURCES_DIR = "tests/";
	public static final String GRAMMAR_TEST1_FILE = "grammar_test.txt";

	public void testGrammar1() {
		doTestGrammar(GRAMMAR_TEST1_FILE);
	}
	
	private void doTestGrammar(String file) {
		try {
			ModelParser parser = new ModelParser();
			Model model = parser.parse(new FileInputStream(TEST_RESOURCES_DIR + file));
			assertNotNull(model);
			
			assertNotNull(model.getRules());
			assertNotNull(model.getNumberAttributes());
			assertNotNull(model.getSymbolAttributes());
			assertNotNull(model.getScenario());
			
			assertFalse(model.getScenario().isEmpty());
			assertFalse(model.getRules().isEmpty());
			assertFalse(model.getNumberAttributes().isEmpty());
			assertFalse(model.getSymbolAttributes().isEmpty());
		} catch (ModelParserException e) {
			fail(e.getMessage());
		} catch (FileNotFoundException e) {
			fail(e.getMessage());			
		}
	}

}
