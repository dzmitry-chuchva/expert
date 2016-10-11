package by.bsuir.iit.kp.expert.presentation.base;

public class ApplicableIdentificator extends Identificator {
	
	private String applicabilityExpression;
	
	public ApplicableIdentificator() {
		super();
	}

	public ApplicableIdentificator(String id, String description) {
		super(id, description);
	}

	public ApplicableIdentificator(String id) {
		super(id);
	}

	public String getApplicabilityExpression() {
		return applicabilityExpression;
	}

	public void setApplicabilityExpression(String applicabilityExpression) {
		this.applicabilityExpression = applicabilityExpression;
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		if (applicabilityExpression != null) {
			buffer.append(" if ");
			buffer.append(applicabilityExpression);
		}
		return buffer.toString();
	}

}
