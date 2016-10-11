package by.bsuir.iit.kp.expert.runtime.rules.impl;

import java.util.List;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractComplexRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Constants;
import by.bsuir.iit.kp.expert.util.Utils;

public class AlternateRuleHandler extends AbstractComplexRuleHandler implements IRuleHandler {

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		String ref = rule.getTarget();
		String expression = rule.getActionString();
		
		Identificator target = evaluator.getReference(ref);		
		ComplexIdentificator complexTarget = (ComplexIdentificator)target;
		
		List symbols = complexTarget.getChildren();
		
		String[] names = new String[symbols.size()];
		
		for (int i = 0; i < symbols.size(); i++) {
			ValuableIdentificator symbol = (ValuableIdentificator)symbols.get(i);
			names[i] = symbol.getId();
		}
			
		IUserInterface ui = evaluator.getUserInterface();
			
		if (expression == null || expression.trim().length() == 0) {
			expression = Utils.getDefaultAlternatePromt(complexTarget.getId());
		}
		
		int index = ui.chooseValue(expression, names);
		for (int i = 0; i < symbols.size(); i++) {
			ValuableIdentificator symbol = (ValuableIdentificator)symbols.get(i);
			if (index == i) {
				Utils.setIdentificatorValue(symbol, Constants.constrTo);
			} else {
				Utils.setIdentificatorValue(symbol, Constants.constrFrom);
			}
		}
	}

}
