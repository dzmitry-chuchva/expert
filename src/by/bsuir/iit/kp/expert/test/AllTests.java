package by.bsuir.iit.kp.expert.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import by.bsuir.iit.kp.expert.test.factories.AllFactoryTests;
import by.bsuir.iit.kp.expert.test.parsers.AllParserTests;
import by.bsuir.iit.kp.expert.test.presentation.AllPresentationTests;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for by.bsuir.iit.kp.expert.test");
		//$JUnit-BEGIN$
		suite.addTest(AllParserTests.suite());
		suite.addTest(AllFactoryTests.suite());
		suite.addTest(AllPresentationTests.suite());
		//$JUnit-END$
		return suite;
	}

}
