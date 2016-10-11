package by.bsuir.iit.kp.expert.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Model;
import by.bsuir.iit.kp.expert.presentation.NumberAttribute;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.ScenarioStatement;
import by.bsuir.iit.kp.expert.presentation.Symbol;
import by.bsuir.iit.kp.expert.presentation.SymbolAttribute;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.RuleHandlersFactory;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.StatementsFactory;
import by.bsuir.iit.kp.expert.util.Utils;

public class ModelBuilder implements IReferencesSource {
	
	// ordering not required
	private Map nAttributes = new HashMap();
	private Map sAttributes = new HashMap();
	private Set assertions = new HashSet();
	
	// ordering is meaningful
	private Set rulesIds = new HashSet();
	private Set statementsLabels = new HashSet();
	private List rules = new ArrayList();
	private List statements = new ArrayList();
	
	// all references in map for fast access
	private Map modelObjs = new HashMap();
	
	private static final RuleHandlersFactory ruleHandlersFactory = RuleHandlersFactory.getInstance();
	private static final StatementsFactory statementsFactory = StatementsFactory.getInstance();
	
	public ModelBuilder() {
	}
	
	public void addNumberAttribute(NumberAttribute attr) throws ModelException {
		if (uniqueReferenceable(attr)) {
			nAttributes.put(attr.getId(), attr);
			modelObjs.put(attr.getId(), attr);
		} else {
			throw new ModelException("attribute/assertion with such id already exists: " + attr.getId());
		}
	}
	
	public void addSymbolAttribute(SymbolAttribute attr) throws ModelException {
		if (uniqueReferenceable(attr)) {
			String parent = attr.getId();
			
			List symbols = attr.getChildren();
			for (Iterator it = symbols.iterator(); it.hasNext(); ) {
				Symbol symbol = (Symbol)it.next();
				
				String child = symbol.getId();
				String assertionName = symbol.getAssertionName();
				
				if (assertionName != null) {
					if (!referenceExists(assertionName)) {
						assertions.add(assertionName);
						modelObjs.put(assertionName, symbol);						
					} else {
						throw new ModelException("attribute/assertion with such id already exists: " + assertionName);
					}
				}
				
				String fullRef = Utils.getFullReference(parent, child);
				modelObjs.put(fullRef, symbol);
			}
			sAttributes.put(attr.getId(), attr);
			modelObjs.put(parent, attr);
		} else {
			throw new ModelException("attribute/assertion with such id already exists: " + attr.getId());
		}
	}
	
	public void addRule(Rule rule) throws ModelException {
		String id = rule.getId();
		
		IRuleHandler handler = ruleHandlersFactory.getRule(id);
		if (handler == null) {
			throw new ModelException("rule handler implementation not found for rule: " + id);
		}
		
		boolean uniqueRule = uniqueRule(rule);
		if (uniqueRule) {
			// will throw an exception if error
			try {
				handler.check(rule, this);
			} catch (ModelParserException e) {
				throw new ModelException(e.getMessage());
			}
				
			rulesIds.add(id);
			rules.add(rule);
		} else {
			if (!uniqueRule) {
				throw new ModelException("rule with such id alreay exists: " + id);
			}
		}
	}
	
	public void addScenarioStatement(ScenarioStatement statement) throws ModelException {
		String argLine = statement.getArgumentLine();
		String applicabilityExpression = statement.getApplicabilityExpression();
		String id = statement.getId();
		IStatement statementHandler = statementsFactory.getStatement(id);
		if (statementHandler == null) {
			throw new ModelException("statement implementation not found: " + id);
		}
		
		if (uniqueStatement(statement)) {
			// will throw an exception if error
			try {
				statementHandler.check(argLine, this);
				Utils.checkLogicalExpression(applicabilityExpression, this);
			} catch (ModelParserException e) {
				throw new ModelException(e.getMessage());
			}
			
			statementsLabels.add(statement.getLabel());
			statements.add(statement);
		} else {
			throw new ModelException("label already exists: " + statement.getLabel());
		}
	}
	
	public Model getModel() {
		Model model = new Model();
		
		List nattrs = new ArrayList(nAttributes.values());
		List sattrs = new ArrayList(sAttributes.values());
		
		model.setRules(rules);
		model.setNumberAttributes(nattrs);
		model.setSymbolAttributes(sattrs);
		model.setScenario(statements);
		
		return model;
	}
	
	public void reset() {
		assertions.clear();
		nAttributes.clear();
		sAttributes.clear();
		rules.clear();
		statements.clear();
		rulesIds.clear();
		statementsLabels.clear();
		modelObjs.clear();		
	}
	
	private boolean uniqueReferenceable(Identificator identificator) {
		String id = identificator.getId();
		return !modelObjs.containsKey(id);
	}
	
	private boolean uniqueRule(Rule rule) {
		String id = rule.getId();
		return !rulesIds.contains(id);
	}
	
	private boolean uniqueStatement(ScenarioStatement statement) {
		String id = statement.getLabel();
		return !statementsLabels.contains(id);
	}
	
	public boolean referenceExists(String ref) {
		if (ref != null) {
			String stripped = Utils.stripWhitespace(ref);
			return modelObjs.containsKey(stripped);
		}
		return false;
	}
	
	public Identificator getReference(String ref) {
		if (ref != null && referenceExists(ref)) {
			return (Identificator)modelObjs.get(ref);
		}
		return null;
	}
}
