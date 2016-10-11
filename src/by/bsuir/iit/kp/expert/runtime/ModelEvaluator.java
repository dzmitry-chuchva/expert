package by.bsuir.iit.kp.expert.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.exceptions.UnableToEvaluateException;
import by.bsuir.iit.kp.expert.presentation.Model;
import by.bsuir.iit.kp.expert.presentation.Rule;
import by.bsuir.iit.kp.expert.presentation.Symbol;
import by.bsuir.iit.kp.expert.presentation.SymbolAttribute;
import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.runtime.rules.IRuleHandler;
import by.bsuir.iit.kp.expert.runtime.rules.RuleHandlersFactory;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Utils;

public class ModelEvaluator implements IReferencesEvaluator {
	
	private static final RuleHandlersFactory factory = RuleHandlersFactory.getInstance();
		
	private Model model;
	private IUserInterface ui = Utils.getNullUserInterface();
	
	private Map modelObjs;
	
	public ModelEvaluator(Model model) {
		this.model = model;
		
		cacheModelObjects();
	}

	/* (non-Javadoc)
	 * @see abc.IModelEvaluator#evaluate(java.lang.String)
	 */
	public double evaluate(String ref) throws ModelException {
		Identificator refObj = getReference(ref);
		
		if (refObj instanceof ValuableIdentificator) {
			ValuableIdentificator valuableObj = (ValuableIdentificator) refObj;
			if (valuableObj.isValueReady()) {
				return valuableObj.getValue();
			} else {
				List objRules = getRulesWithTarget(valuableObj);
				
				applyRules(valuableObj, objRules);
				
				if (!valuableObj.isValueReady()) {
					if (valuableObj.hasDefaultValue()) {
						try {
							valuableObj.setValue(valuableObj.getDefaultValue());
						} catch (ModelException e) {
						}
					} else {
						throw new UnableToEvaluateException(ref);
					}
				}
				return valuableObj.getValue();
			}
		} else if (refObj instanceof ComplexIdentificator) {
			// evaluate complex target as evaluating each of childs, return dummy result
			ComplexIdentificator complex = (ComplexIdentificator)refObj;
			String parent = complex.getId();
			List children = complex.getChildren();
			for (Iterator it = children.iterator(); it.hasNext(); ) {
				ValuableIdentificator valuable = (ValuableIdentificator)it.next();
				String child = valuable.getId();
				evaluate(Utils.getFullReference(parent,child));
			}
			return 0.0;
		} else {
			throw new ModelException("Invalid target for evaluating: " + ref);
		}
	}

	private void applyRules(ValuableIdentificator valuableObj, List rules) throws ModelException {
		for (Iterator it = rules.iterator(); it.hasNext(); ) {
			try {
				Rule rule = (Rule)it.next();
				
				// skip already tried rules 
				if (rule.isUsed()) {
					continue;
				} else {
					// else do not use this rule anywhere
					rule.setUsed(true);
				}
				
				IRuleHandler handler = factory.getRule(rule.getId());
				if (handler == null) {
					throw new ModelException("rule handler implementation not found for rule: " + rule.getId());
				}
				
				handler.evaluate(rule,this);
				
				if (valuableObj.isValueReady()) {
					// rule successfully handled target
					break;
				} //else {
				//	// rule was unable to handle target, try next rule
				//	continue;
				//}
			} catch (UnableToEvaluateException e) {
			} catch (ModelParserException e) {
				throw new ModelException(e.getMessage(),e);
			} catch (ModelException e) {
				throw new ModelException(e.getMessage(),e); 
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see abc.IModelEvaluator#getModel()
	 */
	public Model getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see abc.IModelEvaluator#getUserInterface()
	 */
	public IUserInterface getUserInterface() {
		return ui;
	}
	
	public void setUserInterface(IUserInterface ui) {
		this.ui = ui;
	}

	public Identificator getReference(String ref) {
		if (ref != null) {
			String strippedRef = Utils.stripWhitespace(ref);
			if (modelObjs.containsKey(strippedRef)) {
				Identificator id = (Identificator)modelObjs.get(strippedRef);
				return id;
			}
		}
		return null;
	}
	
	private List getRulesWithTarget(Identificator refObj) {
		List rules = new ArrayList();
		List allRules = model.getRules();
		for (Iterator it = allRules.iterator(); it.hasNext(); ) {
			Rule rule = (Rule)it.next();			
			String target = rule.getTarget();
			Identificator targetObj = getReference(target);
			if (targetObj != null) {
				if (targetObj == refObj) {
					rules.add(rule);
				} else if (refObj instanceof ValuableIdentificator) {
					ValuableIdentificator valuable = (ValuableIdentificator) refObj;
					ComplexIdentificator parent = valuable.getParent();
					if (parent != null) {
						if (parent == targetObj) {
							rules.add(rule);
						}
					}
				}
			}
		}
		return rules;
	}
	
	private void cacheModelObjects() {
		modelObjs = new HashMap();
		
		// collect all referenceable objects to this list
		List allReferenceableObjs = new ArrayList();
		
		List nattrs = model.getNumberAttributes();
		List sattrs = model.getSymbolAttributes();
		allReferenceableObjs.addAll(nattrs);
		allReferenceableObjs.addAll(sattrs);
		
		// map ids to objects for faster access
		for (Iterator it = allReferenceableObjs.iterator(); it.hasNext(); ) {
			Identificator identificator = (Identificator)it.next();
			String id = identificator.getId();
			modelObjs.put(id, identificator);
		}
		
		// map symbols with full reference and assertions
		for (Iterator it = sattrs.iterator(); it.hasNext(); ) {
			SymbolAttribute attr = (SymbolAttribute)it.next();
			String parentId = attr.getId();
			List symbols = attr.getChildren();
			for (Iterator sit = symbols.iterator(); sit.hasNext(); ) {
				Symbol symbol = (Symbol)sit.next();
				String childId = symbol.getId();
				
				String assertionName = symbol.getAssertionName();
				String fullRef = Utils.getFullReference(parentId, childId); 
				modelObjs.put(fullRef, symbol);
				modelObjs.put(assertionName, symbol);
			} 
		}
	}

	public boolean referenceExists(String ref) {
		return getReference(ref) != null;
	}

	public void reset(String ref) throws ModelException {
		if (ref != null) {
			if (referenceExists(ref)) {
				Identificator identificator = getReference(ref);
				identificator.reset();
				
				List objRules = getRulesWithTarget(identificator);
				for (Iterator it = objRules.iterator(); it.hasNext(); ) {
					Rule rule = (Rule)it.next();
					rule.reset();
				}
			} else {
				throw new ModelException("reference does not exists: " + ref);
			}
		} else {
			throw new ModelException("ref == null");
		}
	}
	
	public void resetAll() {
		model.reset();
	}
}
