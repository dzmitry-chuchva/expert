package by.bsuir.iit.kp.expert.runtime.eval;

import by.bsuir.iit.kp.expert.exceptions.ModelException;

public interface IFunction {

	public static final int VARIABLE_ARGUMENT_COUNT = -1;

	public int getArgumentsCount();

	public double execute(double[] arguments) throws ModelException;

}