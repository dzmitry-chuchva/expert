package by.bsuir.iit.kp.expert.runtime.statements.impl;

import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;

public class StatementExecutionResult implements IStatementExecutionResult {
	
	public static final int NEXT = 0;
	public static final int GOTO = 1;
	public static final int CALL = 2;
	public static final int STOP = 3;
	public static final int RETURN = 4;
	
	private int type;
	private String arg; 
	
	public StatementExecutionResult(int type) {
		this.type = type;
	}
	
	public StatementExecutionResult(int type, String arg) {
		this.type = type;
		this.arg = arg;
	}

	/* (non-Javadoc)
	 * @see abc.IStatementExecutionResult#getArg()
	 */
	public Object getArg() {
		return arg;
	}

	/* (non-Javadoc)
	 * @see abc.IStatementExecutionResult#setArg(java.lang.Object)
	 */
	public void setArg(Object arg) {
		this.arg = (String) arg;
	}

	/* (non-Javadoc)
	 * @see abc.IStatementExecutionResult#getType()
	 */
	public int getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see abc.IStatementExecutionResult#setType(int)
	 */
	public void setType(int type) {
		this.type = type;
	}

}
