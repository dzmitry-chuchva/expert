package by.bsuir.iit.kp.expert.test.factories;

import junit.framework.TestCase;
import by.bsuir.iit.kp.expert.ui.UserInterfacesFactory;

public class UserInterfacesFactoryTest extends TestCase {
	
	private static final String UICLASS1 = "console";
	private static final String UICLASS2 = "swing";
	
	public void testUIFactory() {
		UserInterfacesFactory factory = UserInterfacesFactory.getInstance();
		assertNotNull(factory);
		
		UserInterfacesFactory factory2 = UserInterfacesFactory.getInstance();
		assertNotNull(factory2);
		
		assertEquals(factory, factory2);
		
		assertNotNull(factory.getUserInterface(UICLASS1));
		assertNotNull(factory.getUserInterface(UICLASS2));
	}

}
