package by.bsuir.iit.kp.expert.test.presentation;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllPresentationTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for by.bsuir.iit.kp.expert.test.presentation");
		//$JUnit-BEGIN$
		suite.addTestSuite(ValuableIdentificatorTest.class);
		suite.addTestSuite(ConstraintsTest.class);
		//$JUnit-END$
		return suite;
	}

}
