package by.bsuir.iit.kp.expert.runtime.statements;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.runtime.IReferencesEvaluator;
import by.bsuir.iit.kp.expert.runtime.IReferencesSource;

public interface IStatement {
	public void check(String argLine, IReferencesSource checker) throws ModelException, ModelParserException;
	public IStatementExecutionResult execute(String argLine, IReferencesEvaluator evaluator) throws ModelException, ModelParserException;
}
