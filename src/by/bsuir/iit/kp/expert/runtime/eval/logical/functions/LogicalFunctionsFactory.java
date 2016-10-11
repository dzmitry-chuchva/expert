package by.bsuir.iit.kp.expert.runtime.eval.logical.functions;

import java.io.IOException;
import java.util.Properties;

import by.bsuir.iit.kp.expert.util.Utils;

public class LogicalFunctionsFactory {
	private static final String PROPS_FILE = "lfunctions.properties";
	private static LogicalFunctionsFactory factory;
	
	private Properties functions; 
	
	private LogicalFunctionsFactory() {
		initialize();
	}
	
	private void initialize() {
		functions = new Properties();
		
		try {
			functions.load(LogicalFunctionsFactory.class.getResourceAsStream(PROPS_FILE));
		} catch (IOException e) {
			// TODO:
			e.printStackTrace();
		}
	}
	
	public static LogicalFunctionsFactory getInstance() {
		if (factory == null) {
			factory = new LogicalFunctionsFactory();
		}
		return factory;
	}
	
	public ILogicalFunction getFunction(String function) {
		ILogicalFunction state = null;
		try {
			String className = functions.getProperty(function);
			state = (ILogicalFunction)Utils.loadInstance(className);
		} catch (Exception e) {
			// TODO:
			e.printStackTrace();
		}
		return state;
	}
}
