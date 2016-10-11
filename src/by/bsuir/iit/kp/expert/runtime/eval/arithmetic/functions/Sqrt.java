package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.runtime.eval.AbstractFunction;



/**
 * SQRT function implementation. (From set of predefined functions.)
 */
public class Sqrt extends AbstractFunction implements IArithmeticFunction {

	public int getArgumentsCount() {
		return 1;
	}

	protected double innerExecute(double[] args) throws ModelException {
		return Math.sqrt(args[0]);
	}

}
