package by.bsuir.iit.kp.expert.runtime.rules.impl;

import java.util.ArrayList;
import java.util.List;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;
import by.bsuir.iit.kp.expert.presentation.base.Constraints;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.rules.AbstractComplexRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Utils;

public class DistributeRuleHandler extends AbstractComplexRuleHandler implements IRuleHandler {

	protected void innerEvaluate(Rule rule, IReferencesEvaluator evaluator) throws ModelException, ModelParserException {
		String ref = rule.getTarget();
		String expression = rule.getActionString();
		
		Identificator target = evaluator.getReference(ref);		
		ComplexIdentificator complexTarget = (ComplexIdentificator)target;
		
		List symbols = complexTarget.getChildren();
		
		List names = new ArrayList();
		List c = new ArrayList();
		
		for (int i = 0; i < symbols.size(); i++) {
			ValuableIdentificator symbol = (ValuableIdentificator)symbols.get(i);
			if (!symbol.isValueReady()) {
				String id = symbol.getId();
				Constraints constraints = symbol.getConstraints();
				
				names.add(id);
				c.add(constraints);
			}
		}
		
		if (!names.isEmpty()) {
			IUserInterface ui = evaluator.getUserInterface();
				
			if (expression == null || expression.trim().length() == 0) {
				expression = Utils.getDefaultDistributePromt(complexTarget.getId());
			}
			
			Double[] values = ui.promtForValues(expression, (String[])names.toArray(new String[0]), (Constraints[])c.toArray(new Constraints[0]));
			int index = 0;
			for (int i = 0; i < symbols.size(); i++) {
				ValuableIdentificator symbol = (ValuableIdentificator)symbols.get(i);
				if (!symbol.isValueReady()) {
					Double value = values[index++];
					if (value != null) {
						Utils.setIdentificatorValue(symbol, value.doubleValue());
					}
				}
			}
		}
	}

}
