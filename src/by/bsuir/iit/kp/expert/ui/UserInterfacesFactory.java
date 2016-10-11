package by.bsuir.iit.kp.expert.ui;

import java.io.IOException;
import java.util.Properties;

import by.bsuir.iit.kp.expert.util.Utils;

public class UserInterfacesFactory {
	
	private static final String PROPS_FILE = "uiclasses.properties";
	private static UserInterfacesFactory factory;
	
	private Properties uiclasses; 
	
	private UserInterfacesFactory() {
		initialize();
	}
	
	private void initialize() {
		uiclasses = new Properties();
		
		try {
			uiclasses.load(UserInterfacesFactory.class.getResourceAsStream(PROPS_FILE));
		} catch (IOException e) {
		}
	}
	
	public static UserInterfacesFactory getInstance() {
		if (factory == null) {
			factory = new UserInterfacesFactory();
		}
		return factory;
	}
	
	public IUserInterface getUserInterface(String name) {
		try {
			String className = uiclasses.getProperty(name);
			IUserInterface ui = (IUserInterface)Utils.loadInstance(className);
			return ui;
		} catch (Exception e) {
			return null;			
		}
	}
	
}
