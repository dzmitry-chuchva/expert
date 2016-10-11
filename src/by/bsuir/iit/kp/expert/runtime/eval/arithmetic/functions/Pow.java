package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.runtime.eval.AbstractFunction;



/**
 * POW function implementation. (From set of predefined functions.)
 */
public class Pow extends AbstractFunction implements IArithmeticFunction {

	public int getArgumentsCount() {
		return 2;
	}

	protected double innerExecute(double[] args) throws ModelException {
		return Math.pow(args[0],args[1]);
	}

}
