package by.bsuir.iit.kp.expert.runtime.rules;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;

public interface IRuleHandler {
	public void check(Rule rule, IReferencesSource checker) throws ModelException, ModelParserException;
	public void evaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException;
}
