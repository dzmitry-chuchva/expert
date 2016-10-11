package by.bsuir.iit.kp.expert.runtime.rules;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;

abstract public class AbstractComplexRuleHandler extends AbstractRuleHandler implements
		IRuleHandler {

	public void check(Rule rule, IReferencesSource checker) throws ModelException, ModelParserException {
		super.check(rule, checker);
		
		String ref = rule.getTarget();
		Identificator target = checker.getReference(ref);
		if (!(target instanceof ComplexIdentificator)) {
			throw new ModelException("rule is not applicable for target: " + target.getId());
		}
	}

}
