package by.bsuir.iit.kp.expert.presentation.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ComplexIdentificator extends Identificator {

	protected List children = new ArrayList();

	public ComplexIdentificator() {
		super();
	}
	
	public ComplexIdentificator(String id) {
		super(id);
	}

	public List getChildren() {
		return children;
	}

	public void setChildren(List identificators) {
		this.children = identificators;
	}

	public void addChild(ValuableIdentificator identificator) {
		getChildren().add(identificator);
	}

	public Identificator getChild(String name) {
		List identificators = getChildren();
		for (Iterator it = identificators.iterator(); it.hasNext(); ) {
			Identificator identificator = (Identificator)it.next();
			String id = identificator.getId();
			if (id.equals(name)) {
				return identificator;
			}
		}
		return null;
	}
	
	public void reset() {
		super.reset();
		List identificators = getChildren();
		for (Iterator it = identificators.iterator(); it.hasNext(); ) {
			ValuableIdentificator identificator = (ValuableIdentificator)it.next();
			identificator.reset();
		}
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.insert(0, "complex attribute ");
		buffer.append(": [");
		List identificators = getChildren();
		for (Iterator it = identificators.iterator(); it.hasNext(); ) {
			Identificator identificator = (Identificator)it.next();
			buffer.append(identificator.toString());
			buffer.append("|");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		return buffer.toString();
	}

}