package by.bsuir.iit.kp.expert.exceptions;

public class UnableToEvaluateException extends ModelException {

	private static final String UNABLE_TO_EVALUATE = "unable to evaluate: ";
	private static final long serialVersionUID = 1L;

	public UnableToEvaluateException() {
		super();
	}

	public UnableToEvaluateException(String arg0, Throwable arg1) {
		super(UNABLE_TO_EVALUATE + arg0, arg1);
	}

	public UnableToEvaluateException(String arg0) {
		super(UNABLE_TO_EVALUATE + arg0);
	}

	public UnableToEvaluateException(Throwable arg0) {
		super(arg0);
	}
	
	
}
