package by.bsuir.iit.kp.expert.presentation;

import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;

public class NumberAttribute extends ValuableIdentificator {

	public NumberAttribute(String id) {
		setId(id);
	}
	
	public String toString() {
		return "number attribute " + super.toString();
	}

}
