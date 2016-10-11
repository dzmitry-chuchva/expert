package by.bsuir.iit.kp.expert.presentation;

import java.util.List;

import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;

public class SymbolAttribute extends ComplexIdentificator {
	
	public SymbolAttribute(String id) {
		super(id);
	}
	
	public SymbolAttribute(String id, List symbols) {
		super(id);
		setChildren(symbols);
	}

	public void addChild(Symbol symbol) {
		super.addChild(symbol);
	}

}
