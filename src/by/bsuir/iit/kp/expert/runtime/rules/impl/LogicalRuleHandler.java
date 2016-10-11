package by.bsuir.iit.kp.expert.runtime.rules.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.eval.arithmetic.ArithmeticExpressionEvaluator;
import by.bsuir.iit.kp.expert.runtime.eval.logical.LogicalExpressionEvaluator;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractSimpleRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.util.DoubleBoolean;
import by.bsuir.iit.kp.expert.util.Utils;

public class LogicalRuleHandler extends AbstractSimpleRuleHandler implements IRuleHandler {
	
	public void check(Rule rule, IReferencesSource checker) throws ModelException, ModelParserException {
		super.check(rule, checker);
		
		String applicabilityExpression = rule.getApplicabilityExpression();
		if (applicabilityExpression == null || applicabilityExpression.trim().length() == 0) {
			throw new ModelException("logical rules require applicability expression");
		}
		
		String expression = rule.getActionString();
		if (expression != null && expression.trim().length() > 0) {
			ArithmeticExpressionEvaluator.check(expression, checker);
		}
	}
	
	

	public void evaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		// logical rules rely on applicability expression evaluating of attribute value
		// so we need to hook evaluate method, because default implementation checks if
		// rule is applicable and might not call innerEvaluate
		// also it is not super.evaluate here because of the same reasons
		
		check(rule,evaluator);
		innerEvaluate(rule, evaluator);
	}

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		String applicabilityExpression = rule.getApplicabilityExpression();
		double value = LogicalExpressionEvaluator.evaluate(applicabilityExpression, evaluator);
		
		if (DoubleBoolean.isTrue(value)) {
			double coefficient = 1.0;
			String expression = rule.getActionString();
			if (expression != null && expression.trim().length() > 0) {
				coefficient = ArithmeticExpressionEvaluator.evaluate(expression, evaluator);
				if (Math.abs(coefficient) > 1.0) {
					if (coefficient >= 0.0) {
						coefficient = 1.0;
					} else {
						coefficient = -1.0;
					}
				}
			}
			
			double targetValue = DoubleBoolean.booleanWithCoeff(value, coefficient);
			String ref = rule.getTarget();
			ValuableIdentificator valuableTarget = (ValuableIdentificator)evaluator.getReference(ref);
			Utils.setIdentificatorValue(valuableTarget, targetValue);
		} // else rule cannot evaluate target and model runner must try another rules for this target if any
	}
	
}
