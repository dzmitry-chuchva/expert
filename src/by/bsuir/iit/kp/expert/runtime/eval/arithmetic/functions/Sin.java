package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.runtime.eval.AbstractFunction;

public class Sin extends AbstractFunction implements IArithmeticFunction {

	public int getArgumentsCount() {
		return 1;
	}

	protected double innerExecute(double[] arguments) throws ModelException {
		return Math.sin(arguments[0]);
	}

}
