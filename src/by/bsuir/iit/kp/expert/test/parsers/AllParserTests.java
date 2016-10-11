package by.bsuir.iit.kp.expert.test.parsers;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllParserTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for by.bsuir.iit.kp.expert.test.parser");
		suite.addTestSuite(ModelParserTest.class);
		suite.addTestSuite(ArithmeticExpressionEvaluatorTest.class);
		suite.addTestSuite(LogicalExpressionEvaluatorTest.class);
		return suite;
	}

}
