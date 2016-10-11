package by.bsuir.iit.kp.expert.test.presentation;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;

public class ConstraintsTest extends TestCase {

	public void testConstraints() {
		Constraints c = new Constraints();
		
		assertFalse(c.hasMaximum());
		assertFalse(c.hasMinimum());
		
		try {
			c.getMaximum();
			fail("Constraints does not have maximum value, but returns it (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			c.getMinimum();
			fail("Constraints does not have minimum value, but returns it (no exception)");
		} catch (ModelException e) {
		}
		
		assertTrue(c.satisfies(0));
		assertTrue(c.satisfies(20));
		assertTrue(c.satisfies(-10));
	}

	public void testConstraintsBooleanDouble() {
		double min = -10;
		Constraints c = new Constraints(true,min);
		
		assertFalse(c.hasMaximum());
		assertTrue(c.hasMinimum());
		
		try {
			c.getMaximum();
			fail("Constraints does not have maximum value, but returns it (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			double retMin = c.getMinimum();
			assertEquals(retMin, min, 0.001);
		} catch (ModelException e) {
			fail("Constraints does have minimum value, but does not return it, exception: " + e.getMessage());
		}
		
		assertTrue(c.satisfies(min));
		assertTrue(c.satisfies(min + 10));
		assertFalse(c.satisfies(min -10));
		
		double max = 10;
		Constraints c2 = new Constraints(false,max);
		
		assertTrue(c2.hasMaximum());
		assertFalse(c2.hasMinimum());
		
		try {
			c2.getMinimum();
			fail("Constraints does not have minimum value, but returns it (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			double retMax = c2.getMaximum();
			assertEquals(retMax, max, 0.001);
		} catch (ModelException e) {
			fail("Constraints does have maximum value, but does not return it, exception: " + e.getMessage());
		}
		
		assertTrue(c2.satisfies(max));
		assertTrue(c2.satisfies(max - 10));
		assertFalse(c2.satisfies(max + 10));
	}

	public void testConstraintsDoubleDouble() {
		double min = -10;
		double max = 10;
		
		Constraints c = null;
		try {
			c = new Constraints(min,max);
		} catch (ModelException e) {
			fail("Could not create constraints with valid values, exception: " + e.getMessage());
		}
		
		assertTrue(c.hasMaximum());
		assertTrue(c.hasMinimum());
		
		try {
			double retMax = c.getMaximum();
			double retMin = c.getMinimum();
			assertEquals(retMin, min, 0.001);
			assertEquals(retMax, max, 0.001);
		} catch (ModelException e) {
			fail("Constraints does have max/min value, but does not returns it, exception: " + e.getMessage());
		}
		
		assertTrue(c.satisfies(min));
		assertTrue(c.satisfies(max));
	
		double middle = (min + max) / 2;
		assertTrue(c.satisfies(middle));
		
		assertFalse(c.satisfies(min - 10));
		assertFalse(c.satisfies(max + 10));
		
		try {
			Constraints c2 = new Constraints(max,min);
			fail("We were able to create constraints with invalid values (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			Constraints c3 = new Constraints(max,max);
		} catch (ModelException e) {
			fail("We are able to create constraints, but exception: " + e.getMessage());
		}		
	}

	public void testSetMaximum() {
		double max = 10;
		Constraints c = new Constraints();
		
		try {
			c.setMaximum(max);
		} catch (ModelException e) {
			fail("We can set maximum, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			double retMax = c.getMaximum();
			assertEquals(retMax, max, 0.001);
		} catch (ModelException e) {
			fail("We can get maximum, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			c.setMaximum(max + 10);
			assertTrue(c.satisfies(max + 5));
			assertFalse(c.satisfies(max + 15));
			c.setMaximum(max - 10);
			assertTrue(c.satisfies(max - 15));
			assertFalse(c.satisfies(max - 5));
		} catch (ModelException e) {
			fail("We can set maximum, but we were unable, exception: " + e.getMessage());
		}
		
		double min = 0;
		Constraints c2 = null;
		try {
			c2 = new Constraints(min,max);
		} catch (ModelException e) {
			fail("We can create object, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			c2.setMaximum(min - 10);
			fail("We cannot set maximum that is below minimum, but we were able (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			double retMax = c2.getMaximum();
			assertEquals(retMax, max, 0.001);
			assertTrue(c2.satisfies((max + min) / 2));
			assertFalse(c2.satisfies(max + 10));
			assertFalse(c2.satisfies(min - 10));
		} catch (ModelException e) {
			fail("We can get maximum, but we were unable, exception: " + e.getMessage());
		}
	}

	public void testSetMinimum() {
		double min = 0;
		Constraints c = new Constraints();
		
		try {
			c.setMinimum(min);
		} catch (ModelException e) {
			fail("We can set minimum, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			double retMin = c.getMinimum();
			assertEquals(retMin, min, 0.001);
		} catch (ModelException e) {
			fail("We can get minimum, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			c.setMinimum(min + 10);
			assertFalse(c.satisfies(min + 5));
			assertTrue(c.satisfies(min + 15));
			c.setMinimum(min - 10);
			assertFalse(c.satisfies(min - 15));
			assertTrue(c.satisfies(min - 5));
		} catch (ModelException e) {
			fail("We can set minimum, but we were unable, exception: " + e.getMessage());
		}
		
		double max = 10;
		Constraints c2 = null;
		try {
			c2 = new Constraints(min,max);
		} catch (ModelException e) {
			fail("We can create object, but we were unable, exception: " + e.getMessage());
		}
		
		try {
			c2.setMinimum(max + 10);
			fail("We cannot set minimum that is above maximum, but we were able (no exception)");
		} catch (ModelException e) {
		}
		
		try {
			double retMin = c2.getMinimum();
			assertEquals(retMin, min, 0.001);
			assertTrue(c2.satisfies((min + max) / 2));
			assertFalse(c2.satisfies(max + 10));
			assertFalse(c2.satisfies(min - 10));
		} catch (ModelException e) {
			fail("We can get minimum, but we were unable, exception: " + e.getMessage());
		}
	}

	public void testSatisfies() {
		Constraints c = new Constraints();
		assertTrue(c.satisfies(0));
		assertTrue(c.satisfies(-10));
		assertTrue(c.satisfies(10));
		
		Constraints c2 = new Constraints(true,0);
		assertTrue(c2.satisfies(0));
		assertFalse(c2.satisfies(-10));
		assertTrue(c2.satisfies(10));
		
		Constraints c3 = new Constraints(false,0);
		assertTrue(c3.satisfies(0));
		assertTrue(c3.satisfies(-10));
		assertFalse(c3.satisfies(10));
		
		try {
			Constraints c4 = new Constraints(-10,10);
			assertTrue(c4.satisfies(0));
			assertTrue(c4.satisfies(-10));
			assertTrue(c4.satisfies(10));
			assertFalse(c4.satisfies(-15));
			assertFalse(c4.satisfies(15));
		} catch (ModelException e) {
			fail("We can create constraints, but we were unable, exception: " + e.getMessage());
		}
	}

}
