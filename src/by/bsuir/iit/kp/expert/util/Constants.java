package by.bsuir.iit.kp.expert.util;

import java.util.regex.Pattern;

import by.bsuir.iit.kp.expert.Loader;
import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;

public class Constants {
	
	public static final Pattern complexReferencePattern = Pattern.compile("\\[([^\\.]+)\\.([^\\.]+)\\]");
	public static double constrFrom;
	public static double constrTo;
	
	public static final String DEFAULT_ALTERNATE_PROMT = "Choose value of attribute {0}:";
	public static final String DEFAULT_DISTRIBUTE_PROMT = "Define probabilities of values of attribute {0}:";
	public static final String DEFAULT_SIMPLE_PROMT = "Enter value of attribute {0}:";
	public static final String FULL_REFERENCE_FORMAT = "[{0}.{1}]";
	
	public static double EPSILON = 0.001;
	
	public static Constraints SymbolValueConstraints;

	public static double trueIfGreaterThan;
	static {
		try {
			constrFrom = Double.parseDouble(Loader.getProperty(Loader.SYMBOL_FROM_PROP));
			constrTo = Double.parseDouble(Loader.getProperty(Loader.SYMBOL_TO_PROP));
			trueIfGreaterThan = Double.parseDouble(Loader.getProperty(Loader.SYMBOL_TRUE_IF_GT_PROP));
		} catch (RuntimeException e) {
			throw new RuntimeException("error: configuration of value constraints not set or invalid");
		}
	}
	static {
		try {
			SymbolValueConstraints = new Constraints(constrFrom,constrTo);
		} catch (ModelException e) {
			SymbolValueConstraints = null;
		}
	}
	
	private Constants() {
		
	}

}
