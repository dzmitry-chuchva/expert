package by.bsuir.iit.kp.expert.runtime.rules.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractSimpleRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Utils;

public class SimpleQuestionRuleHandler extends AbstractSimpleRuleHandler implements IRuleHandler {

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		String ref = rule.getTarget();
		String expression = rule.getActionString();
		
		Identificator target = evaluator.getReference(ref);		
		ValuableIdentificator valuableTarget = (ValuableIdentificator)target;
			
		IUserInterface ui = evaluator.getUserInterface();
			
		if (expression == null || expression.trim().length() == 0) {
			expression = Utils.getDefaultSimplePromt(valuableTarget.getId());
		}
			
		Double value = ui.promtForValue(expression,valuableTarget.getConstraints());
		if (value != null) {
			Utils.setIdentificatorValue(valuableTarget, value.doubleValue());
		} // else try another rule
	}

}
