package by.bsuir.iit.kp.expert.runtime.rules.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.ArithmeticExpressionEvaluator;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractSimpleRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.util.Utils;

public class ArithmeticRuleHandler extends AbstractSimpleRuleHandler implements IRuleHandler {

	public void check(Rule rule, IReferencesSource checker)
			throws ModelException, ModelParserException {
		super.check(rule, checker);
		
		String expression = rule.getActionString();
		if (expression != null && expression.trim().length() > 0) {
			ArithmeticExpressionEvaluator.check(expression, checker);
		} else {
			throw new ModelException("rule of this type requires action string");
		}
	}

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator)
			throws ModelException, ModelParserException {
		String ref = rule.getTarget();
		String expression = rule.getActionString();
		
		Identificator target = evaluator.getReference(ref);
		ValuableIdentificator valuableTarget = (ValuableIdentificator)target;
			
		double value = ArithmeticExpressionEvaluator.evaluate(expression, evaluator);
		Utils.setIdentificatorValue(valuableTarget, value);
	}

}
