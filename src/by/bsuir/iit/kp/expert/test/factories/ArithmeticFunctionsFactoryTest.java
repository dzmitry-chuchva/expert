package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions.ArithmeticFunctionsFactory;
import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions.IArithmeticFunction;

public class ArithmeticFunctionsFactoryTest extends TestCase {
	
	public void testArithmeticFactory() {
		ArithmeticFunctionsFactory factory = ArithmeticFunctionsFactory.getInstance();
		assertNotNull(factory);
		
		ArithmeticFunctionsFactory factory2 = ArithmeticFunctionsFactory.getInstance();
		assertNotNull(factory2);
		
		assertEquals(factory, factory2);
		
		IArithmeticFunction abs = factory.getFunction("abs");
		assertNotNull(abs);
		
		assertEquals(abs.getArgumentsCount(), 1);
		try {
			double execute = abs.execute(new double[] { -1 });
			assertEquals(execute, 1.0, 0.001);
		} catch (ModelException e) {
			fail("Got exception: " + e.getMessage());
		}
		
		IArithmeticFunction sqrt = factory.getFunction("sqrt");
		assertNotNull(sqrt);
		assertEquals(sqrt.getArgumentsCount(), 1);
		try {
			double execute = sqrt.execute(new double[] { 4 });
			assertEquals(execute, 2.0, 0.001);
		} catch (ModelException e) {
			fail("Got exception: " + e.getMessage());
		}
		
		IArithmeticFunction pow = factory.getFunction("pow");
		assertNotNull(pow);
		assertEquals(pow.getArgumentsCount(), 2);
		try {
			double execute = pow.execute(new double[] { 2,2 });
			assertEquals(execute, 4.0, 0.001);
		} catch (ModelException e) {
			fail("Got exception: " + e.getMessage());
		}
		
		IArithmeticFunction max = factory.getFunction("max");
		assertNotNull(max);
		try {
			double execute = max.execute(new double[] { 1,2,2,-1,0,10,10,4 });
			assertEquals(execute, 10.0, 0.001);
		} catch (ModelException e) {
			fail("Got exception: " + e.getMessage());
		}
		
		IArithmeticFunction t = factory.getFunction("notexisting");
		assertNull(t);
	}

}
