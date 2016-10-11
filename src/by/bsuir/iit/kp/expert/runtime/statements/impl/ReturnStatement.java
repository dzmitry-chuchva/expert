package by.bsuir.iit.kp.expert.runtime.statements.impl;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;

public class ReturnStatement implements IStatement {

	public void check(String argLine, IReferencesSource checker)
			throws ModelException, ModelParserException {
		if (argLine != null && argLine.trim().length() > 0) {
			throw new ModelParserException("statement does not require arguments");
		}
	}

	public IStatementExecutionResult execute(String argLine,
			IReferencesEvaluator evaluator) throws ModelException,
			ModelParserException {
		return new StatementExecutionResult(StatementExecutionResult.RETURN);
	}

}
