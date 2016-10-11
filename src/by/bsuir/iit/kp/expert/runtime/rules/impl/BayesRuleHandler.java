package by.bsuir.iit.kp.expert.runtime.rules.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractSimpleRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;

public class BayesRuleHandler extends AbstractSimpleRuleHandler implements IRuleHandler {
	
	public void check(Rule rule, IReferencesSource checker) throws ModelException, ModelParserException {
		super.check(rule, checker);
		
		throw new ModelException("rule is not implemented");
	}

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
	}

}
