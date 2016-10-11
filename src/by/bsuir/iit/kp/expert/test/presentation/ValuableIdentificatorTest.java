package by.bsuir.iit.kp.expert.test.presentation;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.util.Constants;

public class ValuableIdentificatorTest extends TestCase {

	public void testGetDefaultValue() {
		ValuableIdentificator id = new ValuableIdentificator();
		try {
			id.getDefaultValue();
			fail("There must be no default value, but it is (no exception)");
		} catch (ModelException e) {
		}
		
		double val = 0.5;
		try {
			id.setDefaultValue(val);
		} catch (ModelException e) {
			// never
		}
		
		double anotherVal = 0.55;
		try {
			anotherVal = id.getDefaultValue();
		} catch (ModelException e) {
			fail("There must be default value, but exception: " + e.getMessage());
		}
		
		assertEquals(val, anotherVal, 0.001);
	}

	public void testGetValue() {
		ValuableIdentificator id = new ValuableIdentificator();
		try {
			id.getValue();
			fail("There must be no value, but it is (no exception)");
		} catch (ModelException e) {
		}
		
		double val = 0.5;
		
		try {
			id.setValue(val);
		} catch (ModelException e) {
			// never
		}
		
		double anotherVal = 0.55;
		try {
			anotherVal = id.getValue();
		} catch (ModelException e) {
			fail("There must be a value, but exception: " + e.getMessage());
		}
		
		assertEquals(val, anotherVal, 0.001);
	}

	public void testSetDefaultValue() {
		ValuableIdentificator id = new ValuableIdentificator();
		
		double val = 0.5;
		try {
			id.setDefaultValue(val);
		} catch (ModelException e) {
			fail("We can set default value, but exception: " + e.getMessage());
		}
		
		double anotherVal = 0.55;
		try {
			anotherVal = id.getDefaultValue();
		} catch (ModelException e) {
			fail("There must be default value, but exception: " + e.getMessage());
		}
		
		assertEquals(val, anotherVal, 0.001);
		
		ValuableIdentificator id2 = new ValuableIdentificator();
		try {
			id2.setConstraints(new Constraints(10,20));
		} catch (ModelException e) {
			// never
		}
		
		try {
			id2.setDefaultValue(15);
			id2.setDefaultValue(10);
			id2.setDefaultValue(20);
			
			id2.setDefaultValue(15.0);
			id2.setDefaultValue(10.0);
			id2.setDefaultValue(20.0);
		} catch (ModelException e) {
			fail("We can set default value, but exception: " + e.getMessage());
		}
		
		try {
			id2.setDefaultValue(-10);
			fail("We cannot set this default value, but we do (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			id2.setDefaultValue(5);
			fail("We cannot set this default value, but we do (no exception)");			
		} catch (ModelException e) {
		}
		
		try {
			id2.setDefaultValue(25);
			fail("We cannot set this default value, but we do (no exception)");
		} catch (ModelException e) {
		}
	}

	public void testSetValue() {
		ValuableIdentificator id = new ValuableIdentificator();
		
		double val = 0.5;
		try {
			id.setValue(val);
		} catch (ModelException e) {
			fail("We can set value, but exception: " + e.getMessage());
		}
		
		double anotherVal = 0.55;
		try {
			anotherVal = id.getValue();
		} catch (ModelException e) {
			fail("There must be value, but exception: " + e.getMessage());
		}
		
		assertEquals(val, anotherVal, 0.001);
		
		ValuableIdentificator id2 = new ValuableIdentificator();
		try {
			id2.setConstraints(new Constraints(10,20));
		} catch (ModelException e) {
			// never
		}
		
		try {
			id2.setValue(15);
			id2.setValue(10);
			id2.setValue(20);
			
			id2.setValue(15.0);
			id2.setValue(10.0);
			id2.setValue(20.0);
		} catch (ModelException e) {
			fail("We can set value, but exception: " + e.getMessage());
		}
		
		try {
			id2.setValue(-10);
			fail("We cannot set this value, but we do (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			id2.setValue(5);
			fail("We cannot set this value, but we do (no exception)");			
		} catch (ModelException e) {
		}
		
		try {
			id2.setValue(25);
			fail("We cannot set this value, but we do (no exception)");
		} catch (ModelException e) {
		}
	}

	public void testSetConstraints() {
		ValuableIdentificator id = new ValuableIdentificator();
		
		try {
			id.setConstraints(Constants.SymbolValueConstraints);
		} catch (ModelException e) {
			fail("Unable to set constraints, exception: " + e.getMessage());
		}
		
		try {
			id.setConstraints(Constraints.EmptyConstraints);
		} catch (ModelException e) {
			fail("Unable to change constraints, exception: " + e.getMessage());
		}
		
		try {
			id.setConstraints(Constants.SymbolValueConstraints);
		} catch (ModelException e) {
			fail("Unable to change constraints, exception: " + e.getMessage());
		}
		
		try {
			id.setDefaultValue(1.2);
			id.setValue(-0.1);
			fail("We can not set values, but we were able (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			id.setDefaultValue(0.5);
			id.setValue(0.5);
		} catch (ModelException e) {
			fail("We can set values, but we were unable, exception: " + e.getMessage());
		}
		
		ValuableIdentificator id2 = new ValuableIdentificator();
		try {
			id2.setDefaultValue(1.2);
			id2.setValue(-0.1);
		} catch (ModelException e) {
			fail("We can set values, but we were unable, exception: " + e.getMessage());			
		}
		
		try {
			id2.setConstraints(Constants.SymbolValueConstraints);
			fail("We cannot change constraints, because existing values are in conflicting state with new set constraints, but we were able (not exception)");
		} catch (ModelException e) {
		}
		
		try {
			id2.setDefaultValue(0.5);
			id2.setValue(-0.1);
		} catch (ModelException e) {
			fail("We can set values, but we were unable, exception: " + e.getMessage());			
		}
		
		try {
			id2.setConstraints(Constants.SymbolValueConstraints);
			fail("We cannot change constraints, because an existing value is in conflicting state with new set constraints, but we were able (not exception)");
		} catch (ModelException e) {
		}
		
		try {
			id2.setDefaultValue(1.5);
			id2.setValue(0.1);
		} catch (ModelException e) {
			fail("We can set values, but we were unable, exception: " + e.getMessage());			
		}
		
		try {
			id2.setConstraints(Constants.SymbolValueConstraints);
			fail("We cannot change constraints, because an existing default value is in conflicting state with new set constraints, but we were able (not exception)");
		} catch (ModelException e) {
		}
		
		ValuableIdentificator id3 = new ValuableIdentificator();
		try {
			id3.setDefaultValue(0.5);
			id3.setValue(0.5);
		} catch (ModelException e) {
			fail("We can set values, but we were unable, exception: " + e.getMessage());			
		}
		
		try {
			id3.setConstraints(Constants.SymbolValueConstraints);
		} catch (ModelException e) {
			fail("We can change constraints, because existing values are not in conflicting state with new set constraints, but we were unable, exception: " + e.getMessage());
		}		
	}

	public void testReset() {
		ValuableIdentificator id = new ValuableIdentificator();
		
		try {
			id.setConstraints(Constants.SymbolValueConstraints);
			id.setValue(0.5);
			id.setDefaultValue(0.5);
		} catch (ModelException e) {
			// never
		}
		
		id.reset();
		
		try {
			id.getValue();
			fail("There must be no value after reset, but it is (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			id.getDefaultValue();
		} catch (ModelException e) {
			fail("There must be default value after reset, but it does not: exception: " + e.getMessage());
		}
		
		assertEquals(Constants.SymbolValueConstraints, id.getConstraints());
	}

}
