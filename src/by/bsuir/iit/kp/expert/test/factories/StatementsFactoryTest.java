package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.runtime.statements.StatementsFactory;

public class StatementsFactoryTest extends TestCase {
	
	private static final String STATEMENT1 = "eval";

	public void testStatementsFactory() {
		StatementsFactory factory = StatementsFactory.getInstance();
		assertNotNull(factory);
		
		StatementsFactory factory2 = StatementsFactory.getInstance();
		assertNotNull(factory2);
		
		assertEquals(factory, factory2);
		
		assertNotNull(factory.getStatement(STATEMENT1));		
	}
	
}
