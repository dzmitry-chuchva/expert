package by.bsuir.iit.kp.expert.builders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.Symbol;
import by.bsuir.iit.kp.expert.presentation.SymbolAttribute;

public class SymbolAttributeBuilder {
	
	private List symbols = new ArrayList();
	private Set symbolsId = new HashSet();
	private Set assertions = new HashSet();
	
	public SymbolAttributeBuilder() {
	}
	
	public void addSymbol(Symbol symbol) throws ModelException {
		String id = symbol.getId();
		String assertionName = symbol.getAssertionName();
		
		if (uniqueSymbol(symbol)) {
			symbols.add(symbol);
			symbolsId.add(id);
			
			if (assertionName != null) {
				assertions.add(assertionName);
			}
		} else {
			throw new ModelException("symbol/assertion in symbolattribute already exists: " + id);
		}
	}
	
	public SymbolAttribute getSymbolAttribute(String withId) {
		List symbs = new ArrayList(symbols);
		SymbolAttribute attr = new SymbolAttribute(withId, symbs);
		for (Iterator it = symbs.iterator(); it.hasNext(); ) {
			Symbol symbol = (Symbol)it.next();
			symbol.setParent(attr);
		}

		return attr;
	}
	
	public void reset() {
		symbols.clear();
		symbolsId.clear();
		assertions.clear();
	}
	
	private boolean uniqueSymbol(Symbol symbol) {
		String id = symbol.getId();
		String assertionName = symbol.getAssertionName();
		
		boolean idExists = symbolsId.contains(id);
		if (idExists) {
			return false;
		}
		
		boolean assertionExists = assertions.contains(assertionName);
		if (assertionExists) {
			return false;
		}
		
		return true;
	}

}
