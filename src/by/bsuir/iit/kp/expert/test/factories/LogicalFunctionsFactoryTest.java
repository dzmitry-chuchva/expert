package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.runtime.eval.logical.functions.ILogicalFunction;
import by.bsuir.iit.kp.expert.runtime.eval.logical.functions.LogicalFunctionsFactory;

public class LogicalFunctionsFactoryTest extends TestCase {
	
	public void testLogicalFactory() {
		LogicalFunctionsFactory factory = LogicalFunctionsFactory.getInstance();
		assertNotNull(factory);
		
		LogicalFunctionsFactory factory2 = LogicalFunctionsFactory.getInstance();
		assertNotNull(factory2);
		
		assertEquals(factory, factory2);
		
		ILogicalFunction t = factory.getFunction("notexisting");
		assertNull(t);
	}

}
