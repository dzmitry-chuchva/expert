package by.bsuir.iit.kp.expert.runtime;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.ui.IUserInterface;

public interface IReferencesEvaluator extends IReferencesSource {

	public double evaluate(String ref) throws ModelException;
	public void reset(String ref) throws ModelException;
	public void resetAll();

	public IUserInterface getUserInterface();

}