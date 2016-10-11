package by.bsuir.iit.kp.expert.ui.impl;

import java.util.Arrays;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Constants;


public class StubUserInterface implements IUserInterface {

	public int chooseValue(String promt, String[] names) {
		return 0;
	}

	public Double promtForValue(String promt) {
		return Double.valueOf(Constants.constrFrom);
	}

	public Double promtForValue(String promt, Constraints constraints) {
		try {
			if (constraints.hasMinimum()) {
				return Double.valueOf(constraints.getMinimum());
			} else if (constraints.hasMaximum()) {
				return Double.valueOf(constraints.getMaximum());
			}
		} catch (ModelException e) {
		}
		return Double.valueOf(Constants.constrFrom);
	}

	public Double[] promtForValues(String promt, String[] names) {
		Constraints[] c = new Constraints[names.length];
		Arrays.fill(c, Constraints.EmptyConstraints);
		return promtForValues(promt, names, c); 
	}

	public Double[] promtForValues(String promt, String[] names, Constraints[] constraints) {
		Double[] res = new Double[names.length];
		Arrays.fill(res, null);
		return res;
	}

	public void showError(String errorMessage) {
	}

	public void showMessage(String message) {
	}

}
