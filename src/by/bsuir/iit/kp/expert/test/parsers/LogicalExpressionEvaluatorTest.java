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
import by.bsuir.iit.kp.expert.runtime.eval.logical.LogicalExpressionEvaluator;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.DoubleBoolean;

public class LogicalExpressionEvaluatorTest extends TestCase {
	
	private static Map fakeReferences;
	
	private static final double TRUE = DoubleBoolean.getTrueValue();
	private static final double FALSE = DoubleBoolean.getFalseValue();
	
	private static final double var1 = TRUE;
	private static final double var2 = FALSE;
	private static final double var3 = 3;
	private static final double var4 = 5;
	
	static {
		fakeReferences = new HashMap();
		fakeReferences.put("true", new Double(var1));
		fakeReferences.put("false", new Double(var2));
		fakeReferences.put("three", new Double(var3));
		fakeReferences.put("five", new Double(var4));
	}
	
	public void testLogicalEvalExecute() {
		try {
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("false", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true & true", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("true & false", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("false & false", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true | true", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true | false", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("false | false", referencesHandler)));
			
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("!true", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("!false", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true | true", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true | false", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("false | false", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true & (true | false)", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("(true | false) & false", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true | (true & false)", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("(true & false) | false", referencesHandler)));

			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("true & !(true | false)", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("!(true | false) & false", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("!true | (true & false)", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("(true & false) | !false", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("(true & (false | true | (!false & false) & true)) | !(false & true)", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three = three", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three != five", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three <= three", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three >= three", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three <= five", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("three < five", referencesHandler)));
			
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("three != three", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("three = five", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("three >= five", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("three > five", referencesHandler)));

			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five = five", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five != three", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five <= five", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five >= five", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five >= three", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("five > three", referencesHandler)));
			
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("five != five", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("five = three", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("five <= three", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("five < three", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 = 3", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 != 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 <= 3", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 >= 3", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 <= 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("3 < 5.0", referencesHandler)));
			
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("3 != 3", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("3 = 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("3 >= 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("3 > 5.0", referencesHandler)));

			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 = 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 != 3", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 <= 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 >= 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 >= 3", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5.0 > 3", referencesHandler)));
			
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("5.0 != 5.0", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("5.0 = 3", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("5.0 <= 3", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("5.0 < 3", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("(5.0 > three) & (3.0 < five)", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("(5.0 > three) & (3.0 > five)", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("(5.0 > three) | (3.0 > five)", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("!(3.0 > five)", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5 + three * five - 10 > 0", referencesHandler)));
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("5 + three * five - 10 >= 10", referencesHandler)));
			
			assertTrue(DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate("true + 10", referencesHandler)));
			assertTrue(DoubleBoolean.isFalse(LogicalExpressionEvaluator.evaluate("false - 10", referencesHandler)));
		} catch (ModelParserException e) {
			fail(e.getMessage());
		} catch (ModelException e) {
			fail(e.getMessage());
		}
	}
	
	public void testLogicalEvalCheck() {
		try {
			LogicalExpressionEvaluator.check("true", referencesHandler);
			LogicalExpressionEvaluator.check("false", referencesHandler);
			
			LogicalExpressionEvaluator.check("true & true", referencesHandler);
			LogicalExpressionEvaluator.check("true & false", referencesHandler);
			LogicalExpressionEvaluator.check("false & false", referencesHandler);
			
			LogicalExpressionEvaluator.check("true | true", referencesHandler);
			LogicalExpressionEvaluator.check("true | false", referencesHandler);
			LogicalExpressionEvaluator.check("false | false", referencesHandler);
			
			LogicalExpressionEvaluator.check("!true", referencesHandler);
			LogicalExpressionEvaluator.check("!false", referencesHandler);
			
			LogicalExpressionEvaluator.check("true | true", referencesHandler);
			LogicalExpressionEvaluator.check("true | false", referencesHandler);
			LogicalExpressionEvaluator.check("false | false", referencesHandler);
			
			LogicalExpressionEvaluator.check("true & (true | false)", referencesHandler);
			LogicalExpressionEvaluator.check("(true | false) & false", referencesHandler);
			LogicalExpressionEvaluator.check("true | (true & false)", referencesHandler);
			LogicalExpressionEvaluator.check("(true & false) | false", referencesHandler);

			LogicalExpressionEvaluator.check("true & !(true | false)", referencesHandler);
			LogicalExpressionEvaluator.check("!(true | false) & false", referencesHandler);
			LogicalExpressionEvaluator.check("!true | (true & false)", referencesHandler);
			LogicalExpressionEvaluator.check("(true & false) | !false", referencesHandler);
			
			LogicalExpressionEvaluator.check("three = three", referencesHandler);
			LogicalExpressionEvaluator.check("three != five", referencesHandler);
			LogicalExpressionEvaluator.check("three <= three", referencesHandler);
			LogicalExpressionEvaluator.check("three >= three", referencesHandler);
			LogicalExpressionEvaluator.check("three <= five", referencesHandler);
			LogicalExpressionEvaluator.check("three < five", referencesHandler);
			
			LogicalExpressionEvaluator.check("three != three", referencesHandler);
			LogicalExpressionEvaluator.check("three = five", referencesHandler);
			LogicalExpressionEvaluator.check("three >= five", referencesHandler);
			LogicalExpressionEvaluator.check("three > five", referencesHandler);

			LogicalExpressionEvaluator.check("five = five", referencesHandler);
			LogicalExpressionEvaluator.check("five != three", referencesHandler);
			LogicalExpressionEvaluator.check("five <= five", referencesHandler);
			LogicalExpressionEvaluator.check("five >= five", referencesHandler);
			LogicalExpressionEvaluator.check("five >= three", referencesHandler);
			LogicalExpressionEvaluator.check("five > three", referencesHandler);
			
			LogicalExpressionEvaluator.check("five != five", referencesHandler);
			LogicalExpressionEvaluator.check("five = three", referencesHandler);
			LogicalExpressionEvaluator.check("five <= three", referencesHandler);
			LogicalExpressionEvaluator.check("five < three", referencesHandler);
			
			LogicalExpressionEvaluator.check("3 = 3", referencesHandler);
			LogicalExpressionEvaluator.check("3 != 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("3 <= 3", referencesHandler);
			LogicalExpressionEvaluator.check("3 >= 3", referencesHandler);
			LogicalExpressionEvaluator.check("3 <= 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("3 < 5.0", referencesHandler);
			
			LogicalExpressionEvaluator.check("3 != 3", referencesHandler);
			LogicalExpressionEvaluator.check("3 = 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("3 >= 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("3 > 5.0", referencesHandler);

			LogicalExpressionEvaluator.check("5.0 = 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 != 3", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 <= 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 >= 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 >= 3", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 > 3", referencesHandler);
			
			LogicalExpressionEvaluator.check("5.0 != 5.0", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 = 3", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 <= 3", referencesHandler);
			LogicalExpressionEvaluator.check("5.0 < 3", referencesHandler);
			
			LogicalExpressionEvaluator.check("(5.0 > three) & (3.0 < five)", referencesHandler);
			LogicalExpressionEvaluator.check("(5.0 > three) & (3.0 > five)", referencesHandler);
			LogicalExpressionEvaluator.check("(5.0 > three) | (3.0 > five)", referencesHandler);
			LogicalExpressionEvaluator.check("!(3.0 > five)", referencesHandler);
			
			LogicalExpressionEvaluator.check("5 + three * five - 10 > 0", referencesHandler);
			LogicalExpressionEvaluator.check("5 + three * five - 10 >= 10", referencesHandler);
			
			LogicalExpressionEvaluator.check("true + 10", referencesHandler);
			LogicalExpressionEvaluator.check("false - 10", referencesHandler);
		} catch (ModelParserException e) {
			fail(e.getMessage());
		} catch (ModelException e) {
			fail(e.getMessage());
		}
	}
	
	public void testLogicalEvalExecuteErrors() {
		try {
			LogicalExpressionEvaluator.evaluate("", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("1>", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("<1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("&12", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("!", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator
					.evaluate("((((!0)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("()", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("1 / (1 // 2)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}

		try {
			LogicalExpressionEvaluator
					.evaluate("notexists", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			LogicalExpressionEvaluator.evaluate("true(false)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			LogicalExpressionEvaluator
					.evaluate("1(!1)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
	}
	
	public void testLogicalEvalCheckErrors() {
		try {
			LogicalExpressionEvaluator.check("", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("1>", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("<1", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("&12", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("!", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator
					.check("((((!0)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("()", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}
		try {
			LogicalExpressionEvaluator.check("1 / (1 // 2)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelParserException e) {
		} catch (ModelException e) {
		}

		try {
			LogicalExpressionEvaluator
					.check("notexists", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			LogicalExpressionEvaluator.check("true(false)",
					referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
		try {
			LogicalExpressionEvaluator
					.check("1(!1)", referencesHandler);
			fail("We should got exception, but we did not");
		} catch (ModelException e) {
		} catch (ModelParserException e) {
		}
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
