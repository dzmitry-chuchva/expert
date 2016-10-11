package by.bsuir.iit.kp.expert.presentation;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;
import by.bsuir.iit.kp.expert.util.Constants;

public class Symbol extends ValuableIdentificator {
	
	private String assertionName;

	public Symbol(String id) {
		setId(id);
		try {
			setConstraints(Constants.SymbolValueConstraints);
		} catch (ModelException e) {
			// never
		}
	}

	public String getAssertionName() {
		return assertionName;
	}

	public void setAssertionName(String assertionName) {
		this.assertionName = assertionName;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.insert(0, "value ");
		if (assertionName != null) {
			buffer.append(" (assertion \"");
			buffer.append(assertionName);
			buffer.append("\")");
		}
		return buffer.toString();
	}
	
}
