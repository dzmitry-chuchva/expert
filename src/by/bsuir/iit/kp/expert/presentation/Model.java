package by.bsuir.iit.kp.expert.presentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model {
	
	private List numberAttributes = new ArrayList();
	private List symbolAttributes = new ArrayList();
	private List rules = new ArrayList();
	private List scenario = new ArrayList();
	
	public void reset() {
		List nattrs = getNumberAttributes();
		if (nattrs != null) {
			for (Iterator it = nattrs.iterator(); it.hasNext(); ) {
				NumberAttribute attribute = (NumberAttribute)it.next();
				attribute.reset();
			}
		}
		
		List sattrs = getSymbolAttributes();
		if (sattrs != null) {
			for (Iterator it = sattrs.iterator(); it.hasNext(); ) {
				SymbolAttribute attribute = (SymbolAttribute)it.next();
				attribute.reset();
			}
		}
		
		List rules = getRules();
		if (rules != null) {
			for (Iterator it = rules.iterator(); it.hasNext(); ) {
				Rule rule = (Rule)it.next();
				rule.reset();
			}
		}
	}
	
	public List getNumberAttributes() {
		return numberAttributes;
	}

	public void setNumberAttributes(List numberAttributes) {
		this.numberAttributes = numberAttributes;
	}
	
	public void addNumberAttribute(NumberAttribute numberAttribute) {
		if (numberAttributes != null) {
			numberAttributes.add(numberAttribute);
		}
	}

	public List getSymbolAttributes() {
		return symbolAttributes;
	}

	public void setSymbolAttributes(List symbolAttributes) {
		this.symbolAttributes = symbolAttributes;
	}
	
	public void addSymbolAttribute(SymbolAttribute symbolAttribute) {
		if (symbolAttributes != null) {
			symbolAttributes.add(symbolAttribute);
		}
	}

	public List getRules() {
		return rules;
	}

	public void setRules(List rules) {
		this.rules = rules;
	}
	
	public void addRule(Rule rule) {
		if (rules != null) {
			rules.add(rule);
		}
	}

	public List getScenario() {
		return scenario;
	}

	public void setScenario(List scenario) {
		this.scenario = scenario;
	}
	
	public void addScenarioStatement(ScenarioStatement statement) {
		if (scenario != null) {
			scenario.add(statement);
		}
	}
	
	public String toString() {
		List allObjs = new ArrayList();
		allObjs.addAll(numberAttributes);
		allObjs.addAll(symbolAttributes);
		allObjs.addAll(rules);
		allObjs.addAll(scenario);
		
		StringBuffer buffer = new StringBuffer();
		for (Iterator it = allObjs.iterator(); it.hasNext(); ) {
			buffer.append(it.next().toString());
			buffer.append('\n');
		}
		if (buffer.length() == 0) {
			buffer.append("empty");
		} else {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		return buffer.toString();
	}
}
