package by.bsuir.iit.kp.expert.ui;

import java.util.Arrays;

import by.bsuir.iit.kp.expert.presentation.base.Constraints;

abstract public class AbstractConsoleInterface implements IUserInterface {

	public Double promtForValue(String promt) {
		return promtForValue(promt, Constraints.EmptyConstraints);
	}

	public Double[] promtForValues(String promt, String[] names) {
		Constraints[] c = new Constraints[names.length];
		Arrays.fill(c, Constraints.EmptyConstraints);
		return promtForValues(promt, names, c);
	}
}
