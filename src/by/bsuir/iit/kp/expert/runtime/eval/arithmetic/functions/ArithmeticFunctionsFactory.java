package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import java.io.IOException;
import java.util.Properties;

import by.bsuir.iit.kp.expert.util.Utils;

public class ArithmeticFunctionsFactory {
	private static final String PROPS_FILE = "afunctions.properties";
	private static ArithmeticFunctionsFactory factory;
	
	private Properties functions; 
	
	private ArithmeticFunctionsFactory() {
		initialize();
	}
	
	private void initialize() {
		functions = new Properties();
		
		try {
			functions.load(ArithmeticFunctionsFactory.class.getResourceAsStream(PROPS_FILE));
		} catch (IOException e) {
			// TODO:
			e.printStackTrace();
		}
	}
	
	public static ArithmeticFunctionsFactory getInstance() {
		if (factory == null) {
			factory = new ArithmeticFunctionsFactory();
		}
		return factory;
	}
	
	public IArithmeticFunction getFunction(String function) {
		IArithmeticFunction state = null;
		try {
			String className = functions.getProperty(function);
			state = (IArithmeticFunction)Utils.loadInstance(className);
		} catch (Exception e) {
			// TODO:
			e.printStackTrace();
		}
		return state;
	}
}
