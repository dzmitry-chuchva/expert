package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.runtime.rules.RuleHandlersFactory;

public class RulesFactoryTest extends TestCase {
	
	public static final String RULE1 = "Q_age";
	public static final String RULE2 = "N_age";
	public static final String RULE3 = "L_age";
	public static final String RULE4 = "B_age";
	public static final String RULE5 = "A_age";
	public static final String RULE6 = "D_age";
	
	public void testRulesFactory() {
		RuleHandlersFactory factory = RuleHandlersFactory.getInstance();
		assertNotNull(factory);
		
		RuleHandlersFactory factory2 = RuleHandlersFactory.getInstance();
		assertNotNull(factory2);
		
		assertEquals(factory, factory2);
		
		assertNotNull(factory.getRule(RULE1));
		assertNotNull(factory.getRule(RULE2));
		assertNotNull(factory.getRule(RULE3));
		assertNotNull(factory.getRule(RULE4));
		assertNotNull(factory.getRule(RULE5));
		assertNotNull(factory.getRule(RULE6));
	}
	
}
