package by.bsuir.iit.kp.expert.test.parsers;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Model;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.ArithmeticExpressionEvaluator;
import by.bsuir.iit.kp.expert.ui.IUserInterface;

public class ArithmeticExpressionEvaluatorTest extends TestCase {
	
	private static final double EPSILON = 0.01;

	private static Map fakeReferences;
	
	private static double var1 = 0;
	private static double var2 = -10;
	private static double var3 = 10;
	private static double var4 = 5.5234;
	
	static {
		fakeReferences = new HashMap();
		fakeReferences.put("var1", new Double(var1));
		fakeReferences.put("var2", new Double(var2));
		fakeReferences.put("var3", new Double(var3));
		fakeReferences.put("var4", new Double(var4));
	}
	
	public void testArithmeticEvalExecute() {
		try {
			assertEquals(ArithmeticExpressionEvaluator.evaluate("0", referencesHandler),
									0, EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("1 + 1.5", referencesHandler),
									1 + 1.5, EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("- 5 - 10", referencesHandler),
					- 5 - 10, EPSILON);			
			assertEquals(ArithmeticExpressionEvaluator.evaluate("2 * (-3.0 - 4.1/2)", referencesHandler),
									2 * (-3.0 - 4.1/2), EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("(+4 + 8.0 / -2 - (4 * (10.5 - 5) * (4 / 2) / +2) / 2) + 10", referencesHandler),
									(+4 + 8.0 / -2 - (4 * (10.5 - 5) * (4 / 2) / +2) / 2) + 10, EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("var1", referencesHandler),
									var1, EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("2 * var1 - (-var2) / +var3", referencesHandler),
									2 * var1 - (-var2) / +var3, EPSILON);
			assertEquals(ArithmeticExpressionEvaluator.evaluate("(-1 / abs(var2) * sqrt(4 / 10 + 12 + var4 - pow(2+2, abs(-var1)) + 10) + 12) - 1", referencesHandler),
									(-1 / Math.abs(var2) * Math.sqrt(4 / 10 + 12 + var4 - Math.pow(2+2, Math.abs(-var1)) + 10) + 12) - 1, EPSILON);
		} catch (ModelParserException e) {
			fail(e.getMessage());
		} catch (ModelException e) {
			fail(e.getMessage());
		}
	}
	
	public void testArithmeticEvalCheck() {
		try {
			ArithmeticExpressionEvaluator.check("0", referencesHandler);
			ArithmeticExpressionEvaluator.check("1 + 1.5", referencesHandler);
			ArithmeticExpressionEvaluator.check("2 * (-3.0 - 4.1/2)", referencesHandler);
			ArithmeticExpressionEvaluator.check("(+4 + 8.0 / -2 - (4 * (10.5 - 5) * (4 / 2) / +2) / 2) + 10", referencesHandler);
			ArithmeticExpressionEvaluator.check("var1", referencesHandler);
			ArithmeticExpressionEvaluator.check("2 * var1 - (-var2) / +var3", referencesHandler);
			ArithmeticExpressionEvaluator.check("(-1 / abs(var2) * sqrt(4 / 10 + 12 + var4 - pow(2+2, abs(-var1)) + 10) + 12) - 1", referencesHandler);
		} catch (ModelParserException e) {
			fail(e.getMessage());
		} catch (ModelException e) {
			fail(e.getMessage());
		}
	}
	
	public void testArithmeticEvalExecuteErrors() {
		try {
			ArithmeticExpressionEvaluator.evaluate("", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("--1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("1-", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("1,5", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("++1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator
					.evaluate("((((0)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("()", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("1 / (1 // 2)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}

		try {
			ArithmeticExpressionEvaluator
					.evaluate("asdfas", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("abs + sqrt",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("var1(1)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			ArithmeticExpressionEvaluator
					.evaluate("pow(1)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			ArithmeticExpressionEvaluator.evaluate("max()", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
	}
	
	public void testArithmeticEvalCheckErrors() {
		try {
			ArithmeticExpressionEvaluator.check("", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("--1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("1-", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("1,5", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("++1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("((((0)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("()", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}
		try {
			ArithmeticExpressionEvaluator.check("1 / (1 // 2)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {}

		try {
			ArithmeticExpressionEvaluator.check("asdfas", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {}
		try {
			ArithmeticExpressionEvaluator.check("abs + sqrt", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {}
		try {
			ArithmeticExpressionEvaluator.check("var1(1)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {}
		try {
			ArithmeticExpressionEvaluator.check("pow(1)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {}
	}
	
	private static final FakeReferencesEvaluator referencesHandler = new FakeReferencesEvaluator();
	
	private static class FakeReferencesEvaluator implements IReferencesEvaluator, IReferencesSource {
		public double evaluate(String ref) throws ModelException {
			Double val = (Double)fakeReferences.get(ref);
			if (val != null) {
				return val.doubleValue();
			} else {
				throw new ModelException("undefined reference: " + ref);
			}
		}

		public Model getModel() {
			return null;
		}

		public IUserInterface getUserInterface() {
			return null;
		}

		public Identificator getReference(String ref) {
			if (referenceExists(ref)) {
				ValuableIdentificator valuable = new ValuableIdentificator(ref);
				return valuable;
			} else {
				return null;
			}
		}

		public boolean referenceExists(String ref) {
			return fakeReferences.containsKey(ref);
		}

		public void reset(String ref) throws ModelException {
		}

		public void resetAll() {
		}
	};
	
}
