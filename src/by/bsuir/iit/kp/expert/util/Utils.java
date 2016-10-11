package by.bsuir.iit.kp.expert.util;

import java.text.MessageFormat;
import java.util.List;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.exceptions.UnableToEvaluateException;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.eval.logical.LogicalExpressionEvaluator;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.ui.impl.StubUserInterface;

public class Utils {
	
	public static final void checkLogicalExpression(String expression, IReferencesSource checker) throws ModelException, ModelParserException {
		if (expression != null && expression.trim().length() > 0) { 
			LogicalExpressionEvaluator.check(expression, checker);
		}
	}
	
	public static final String getDefaultAlternatePromt(String name) {
		return MessageFormat.format(Constants.DEFAULT_ALTERNATE_PROMT, new Object[] { name });
	}
	
	public static final String getDefaultDistributePromt(String name) {
		return MessageFormat.format(Constants.DEFAULT_DISTRIBUTE_PROMT, new Object[] { name });
	}
	
	public static final String getDefaultSimplePromt(String name) {
		return MessageFormat.format(Constants.DEFAULT_SIMPLE_PROMT, new Object[] { name });
	}
	
	public static final String getFullReference(String parent, String child) {
		return MessageFormat.format(Constants.FULL_REFERENCE_FORMAT, new Object[] { parent, child });
	}
	
	public static final IUserInterface getNullUserInterface() {
		return new StubUserInterface();
	}
	
	public static final double[] getPrimitiveDoubleList(List args) {
		Double[] dargs = (Double[])args.toArray(new Double[0]);
		double[] prim = new double[dargs.length];
		for (int i = 0; i < dargs.length; i++) {
			prim[i] = dargs[i].doubleValue();
		}
		return prim;
	}
	
	public static final boolean isApplicable(String expression, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		if (expression != null && expression.trim().length() > 0) {
			try {
				return DoubleBoolean.isTrue(LogicalExpressionEvaluator.evaluate(expression, evaluator));
			} catch (UnableToEvaluateException e) {
				return false;
			}
		} else {
			return true;
		}
	}

	public static final Object loadInstance(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (className != null) {
			ClassLoader loader = ClassLoader.getSystemClassLoader();
			Object instance = null;
			instance = loader.loadClass(className).newInstance();
			return instance;
		} else {
			return null;
		}
	}

	public static final void setIdentificatorValue(ValuableIdentificator identificator, double value) {
		Constraints constraints = identificator.getConstraints();
		try {
			if (constraints.satisfies(value)) {
				identificator.setValue(value);
			} else {
				// if value does not satisfies constraints, than force value to be in constraints range 
				if (constraints.hasMaximum() && value > constraints.getMaximum()) {
					identificator.setValue(constraints.getMaximum());
				} else if (constraints.hasMinimum() && value < constraints.getMinimum()) {
					identificator.setValue(constraints.getMinimum());
				}
			}
		} catch (ModelException e) {
			// never
		}
	}
	
	public static final void safeEvaluateReference(IReferencesEvaluator evaluator, String ref) throws ModelException {
		if (evaluator != null) {
   			try {
   				evaluator.evaluate(ref);
   			} catch (UnableToEvaluateException e) {
   				// silence, it ok :)
			}
		} else {
			throw new ModelException("evaluator = null");
		}
	}
	
	public static final void safeCheckReference(IReferencesSource checker, String ref) throws ModelParserException {
		if (checker == null) {
			throw new ModelParserException("checker = null");
		}
		
		if (!checker.referenceExists(ref)) {
			throw new ModelParserException("undefined reference: " + ref);
		}
	}

	public static final String stripWhitespace(String str) {
		String res = str.replaceAll("\\s", "");
		return res;
	}

	private Utils() {
		
	}

}
