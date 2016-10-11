package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import by.bsuir.iit.kp.expert.runtime.eval.AbstractFunction;


/**
 * ABS function implementation. (From set of predefined functions.)
 */
public class Abs extends AbstractFunction implements IArithmeticFunction {
	
	protected double innerExecute(double[] args) {
		return Math.abs(args[0]);
	}

	public int getArgumentsCount() {
		return 1;
	}

}
