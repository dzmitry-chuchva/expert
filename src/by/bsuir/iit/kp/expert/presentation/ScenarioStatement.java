package by.bsuir.iit.kp.expert.presentation;

import by.bsuir.iit.kp.expert.presentation.base.ApplicableIdentificator;


public class ScenarioStatement extends ApplicableIdentificator {
	
	private String argLine;
	private String label;
	
	public ScenarioStatement(String identificator, String argLine) {
		super(identificator);
		this.argLine = argLine;
	}
	
	public ScenarioStatement(String label, String identificator, String argLine) {
		super(identificator);
		this.argLine = argLine;
		this.label = label;
	}

	public String getArgumentLine() {
		return argLine;
	}

	public void setArgumentLine(String argLine) {
		this.argLine = argLine;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		buffer.insert(0, ": ");
		buffer.insert(0, label);
		buffer.insert(0, "statement ");
		
		buffer.append(" (");
		buffer.append(argLine);
		buffer.append(")");
		return buffer.toString();
	}

}
