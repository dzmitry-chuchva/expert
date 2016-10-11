package by.bsuir.iit.kp.expert.runtime.eval;

import by.bsuir.iit.kp.expert.exceptions.ModelException;

abstract public class AbstractFunction implements IFunction {

	public double execute(double[] arguments) throws ModelException {
		if (getArgumentsCount() != VARIABLE_ARGUMENT_COUNT && getArgumentsCount() != arguments.length) {
			throw new ModelException("function takes " + getArgumentsCount() + " parameters, but it was passed " + arguments.length + " parameters");
		}
		
		try {
			return innerExecute(arguments);
		} catch (Exception e) {
			throw new ModelException("unable to evaluate function");
		}
	}

	abstract public int getArgumentsCount();
	abstract protected double innerExecute(double[] arguments) throws ModelException;
}
