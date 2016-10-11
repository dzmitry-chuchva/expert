package by.bsuir.iit.kp.expert.presentation;

import by.bsuir.iit.kp.expert.presentation.base.ApplicableIdentificator;

public class Rule extends ApplicableIdentificator {
	
	private String target;
	private String actionString;
	private boolean used = false;
	
	public Rule(String id) {
		super(id);		
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getActionString() {
		return actionString;
	}

	public void setActionString(String actionString) {
		this.actionString = actionString;
	}
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
	
	public void reset() {
		super.reset();
		used = false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.insert(0, "rule ");
		buffer.append(": ");
		buffer.append(target);
		if (actionString != null) {
			buffer.append(" = ");
			buffer.append(actionString);
		}
		if (used) {
			buffer.append(" | used");
		}
		return buffer.toString();
	}
		
}
