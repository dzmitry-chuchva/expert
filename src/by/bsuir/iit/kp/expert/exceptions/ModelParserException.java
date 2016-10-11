package by.bsuir.iit.kp.expert.exceptions;

public class ModelParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModelParserException() {
	}

	public ModelParserException(String arg0) {
		super(arg0);
	}

	public ModelParserException(Throwable arg0) {
		super(arg0);
	}

	public ModelParserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public ModelParserException(int line, String message) {
		super(line + ": " + message);		
	}
	
	public ModelParserException(int line, int column, String message) {
		super(line + ":" + column + ": " + message);		
	}
	
	public ModelParserException(int line, Throwable cause) {
		super(line + ": Parse failed",cause);		
	}
	
	public ModelParserException(int line, int column, Throwable cause) {
		super(line + ":" + column + ": Parse failed",cause);		
	}	
	
	public ModelParserException(int line, String message, Throwable cause) {
		super(line + ": " + message,cause);		
	}
	
	public ModelParserException(int line, int column, String message, Throwable cause) {
		super(line + ":" + column + ": " + message,cause);		
	}	

}
