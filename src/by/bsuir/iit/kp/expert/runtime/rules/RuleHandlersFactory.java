package by.bsuir.iit.kp.expert.runtime.rules;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import by.bsuir.iit.kp.expert.util.Utils;

public class RuleHandlersFactory {
	
	private static final String PROPS_FILE = "rulehandlers.properties";
	private static RuleHandlersFactory factory;
	
	private Properties rulehandlers; 
	
	private RuleHandlersFactory() {
		initialize();
	}
	
	private void initialize() {
		rulehandlers = new Properties();
		
		try {
			rulehandlers.load(RuleHandlersFactory.class.getResourceAsStream(PROPS_FILE));
		} catch (IOException e) {
			// TODO:
			e.printStackTrace();
		}
	}
	
	public static RuleHandlersFactory getInstance() {
		if (factory == null) {
			factory = new RuleHandlersFactory();
		}
		return factory;
	}
	
	public IRuleHandler getRule(String ruleId) {
		IRuleHandler rulehandler = null;
		try {
			for (Enumeration en = rulehandlers.keys(); en.hasMoreElements(); ) {
				String existingPrefix = (String)en.nextElement();
				if (ruleId.startsWith(existingPrefix)) {
					String className = rulehandlers.getProperty(existingPrefix);
					rulehandler = (IRuleHandler)Utils.loadInstance(className);
					break;
				}
			}
		} catch (Exception e) {
			// TODO:
			e.printStackTrace();
		}
		return rulehandler;
	}	

}
