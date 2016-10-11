package by.bsuir.iit.kp.expert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import by.bsuir.iit.kp.expert.exceptions.LoaderException;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.parsers.ModelParser;
import by.bsuir.iit.kp.expert.presentation.Model;
import by.bsuir.iit.kp.expert.runtime.ModelRunner;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.ui.UserInterfacesFactory;

public class Loader {
	
	// system wide configuration properties
	public static final String UITYPE_PROP = "ui";
	public static final String DEBUGMODE_PROP = "debug";
	public static final String FILENAME_PROP = "filename";
	public static final String SYMBOL_FROM_PROP = "from";
	public static final String SYMBOL_TO_PROP = "to";
	public static final String SYMBOL_TRUE_IF_GT_PROP = "truth";
	

	// for bool props
	public static final String VALUE_TRUE = "true";
	public static final String VALUE_FALSE = "false";

	private static final String CONFIG_FILENAME = "expert.properties";
		
	private static Properties systemProperties;
	
	static {
		systemProperties = new Properties();
		
		try {
			systemProperties.load(Loader.class.getResourceAsStream(CONFIG_FILENAME));
		} catch (IOException e) {
		}
	}

	private Loader() {
	}

	public static final void setProperties(Properties props) throws LoaderException {
		Properties expertProps = new Properties(systemProperties);
		expertProps.putAll(props);
		systemProperties = expertProps;
	}
	
	public static final void startSystem() throws LoaderException {
		String file = getProperty(FILENAME_PROP);
		if (file != null) {
			startSystem(file);
		} else {
			throw new LoaderException("error: specify file with model on which to operate");
		}
	}
	
	public static final void startSystem(String file) throws LoaderException {
		try {
			Model model = getModelFromFile(file);
			startSystem(model);
		} catch (ModelParserException e) {
			System.out.println("parse error: " + e.getMessage());
		} catch (FileNotFoundException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
	
	public static final void startSystem(Model model) throws LoaderException {
		String uiclass = getProperty(UITYPE_PROP);
		String debug = getProperty(DEBUGMODE_PROP);
		
		UserInterfacesFactory factory = UserInterfacesFactory.getInstance();
		IUserInterface ui = factory.getUserInterface(uiclass);
		if (ui == null) {
			throw new LoaderException("UI not found: " + uiclass);
		}
		
		try {
			ModelRunner runner = new ModelRunner(ui);
			runner.run(model);
			
			if (VALUE_TRUE.equals(debug)) {
				System.out.println(model);
			}
		} catch (ModelException e) {
			ui.showError("runtime error: " + e.getMessage());
		}
	}
	
	public static final String getProperty(String name) {
		return systemProperties.getProperty(name, null);
	}
	
	public static final void setProperty(String name, String value) {
		systemProperties.setProperty(name, value);
	}
	
	private static final Model getModelFromFile(String file) throws FileNotFoundException, ModelParserException {
		ModelParser parser = new ModelParser();
		Model model = parser.parse(new FileInputStream(file));
		return model;
	}
}
