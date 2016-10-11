package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllFactoryTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for by.bsuir.iit.kp.expert.test.factories");
		//$JUnit-BEGIN$
		suite.addTestSuite(UserInterfacesFactoryTest.class);
		suite.addTestSuite(RulesFactoryTest.class);
		suite.addTestSuite(StatementsFactoryTest.class);
		suite.addTestSuite(ArithmeticFunctionsFactoryTest.class);
		suite.addTestSuite(LogicalFunctionsFactoryTest.class);
		//$JUnit-END$
		return suite;
	}

}
