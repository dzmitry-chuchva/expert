package by.bsuir.iit.kp.expert.util;

public class DoubleBoolean {
	
	public static final double and(double arg1, double arg2) {
		return Math.min(arg1, arg2);
	}

	public static final double booleanWithCoeff(double arg, double coeff) {
		double middle = middle();
		
		double res = middle + Math.abs(arg - middle) * coeff;
		return res;
	}

	public static final double getFalseValue() {
		return Constants.constrFrom;
	}

	public static final double getTrueValue() {
		return Constants.constrTo;
	}

	public static final boolean isFalse(double value) {
		return Double.compare(value, Constants.trueIfGreaterThan) <= 0;
	}

	public static final boolean isTrue(double value) {
		return Double.compare(value, Constants.trueIfGreaterThan) > 0;
	}
	
	public static final double middle() {
		return (Constants.constrTo + Constants.constrFrom) / 2;
	}
	
	public static final double mirror(double arg) {
		double mirror = arg - 2 * (arg - middle());
		return mirror;
	}
	
	public static final double not(double arg) {
		return mirror(arg);
	}
	
	public static final double or(double arg1, double arg2) {
		return Math.max(arg1, arg2);
	}
	
	public static final double valueToBoolean(double val) {
		if (val > Constants.constrTo) {
			val = Constants.constrTo;
		} else if (val < Constants.constrFrom) {
			val = Constants.constrFrom;
		}
		return val;
	}
	
	private DoubleBoolean() {
		
	}

}
