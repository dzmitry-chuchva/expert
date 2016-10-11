package by.bsuir.iit.kp.expert.runtime.eval.arithmetic.functions;

import java.util.Arrays;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.runtime.eval.AbstractFunction;

public class Max extends AbstractFunction implements IArithmeticFunction {

	public int getArgumentsCount() {
		return VARIABLE_ARGUMENT_COUNT;
	}

	protected double innerExecute(double[] args) throws ModelException {
		if (args.length == 0) {
			throw new ModelException("function takes at least one parameter");
		}
		Arrays.sort(args);
		return args[args.length - 1];
	}

}
