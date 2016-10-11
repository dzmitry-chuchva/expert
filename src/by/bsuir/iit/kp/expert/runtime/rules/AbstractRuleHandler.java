package by.bsuir.iit.kp.expert.runtime.rules;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.util.Utils;

abstract public class AbstractRuleHandler implements IRuleHandler {

	public void check(Rule rule, IReferencesSource checker) throws ModelException,
			ModelParserException {
		String target = rule.getTarget();
		if (!checker.referenceExists(target)) {
			throw new ModelException("udefined reference used as rule target: " + target);
		}
		
		String applicabilityExpression = rule.getApplicabilityExpression();
		if (applicabilityExpression != null && applicabilityExpression.trim().length() > 0) {
			Utils.checkLogicalExpression(applicabilityExpression, checker);
		}
	}

	public void evaluate(Rule rule, IReferencesEvaluator evaluator)
			throws ModelException, ModelParserException {
		check(rule,evaluator);
		
		String applicabilityExpression = rule.getApplicabilityExpression();
		if (applicabilityExpression != null && applicabilityExpression.trim().length() > 0) {
			if (Utils.isApplicable(applicabilityExpression, evaluator)) {
				innerEvaluate(rule, evaluator);
			}
		} else {
			innerEvaluate(rule, evaluator);
		}
	}
	
	abstract protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException;

}
