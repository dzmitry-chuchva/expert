package by.bsuir.iit.kp.expert.runtime.statements.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;

public class GotoStatement implements IStatement {

	public void check(String argLine, IReferencesSource checker)
			throws ModelException, ModelParserException {
		try {
			Integer.parseInt(argLine);
		} catch (NumberFormatException e) {
			throw new ModelParserException("argument of statement is invalid");
		}
	}

	public IStatementExecutionResult execute(String argLine,
			IReferencesEvaluator evaluator) throws ModelException,
			ModelParserException {
		IStatementExecutionResult result = new StatementExecutionResult(StatementExecutionResult.GOTO,argLine);
		return result;
	}

}
