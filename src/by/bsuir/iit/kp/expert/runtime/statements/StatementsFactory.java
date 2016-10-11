package by.bsuir.iit.kp.expert.runtime.statements;

import java.io.IOException;
import java.util.Properties;

import by.bsuir.iit.kp.expert.util.Utils;

public class StatementsFactory {
	
	private static final String PROPS_FILE = "statements.properties";
	private static StatementsFactory factory;
	
	private Properties statements; 
	
	private StatementsFactory() {
		initialize();
	}
	
	private void initialize() {
		statements = new Properties();
		
		try {
			statements.load(StatementsFactory.class.getResourceAsStream(PROPS_FILE));
		} catch (IOException e) {
			// TODO:
			e.printStackTrace();
		}
	}
	
	public static StatementsFactory getInstance() {
		if (factory == null) {
			factory = new StatementsFactory();
		}
		return factory;
	}
	
	public IStatement getStatement(String statement) {
		IStatement state = null;
		try {
			String className = statements.getProperty(statement);
			state = (IStatement)Utils.loadInstance(className);
		} catch (Exception e) {
			// TODO:
			e.printStackTrace();
		}
		return state;
	}

}
