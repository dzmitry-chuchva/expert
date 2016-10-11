package by.bsuir.iit.kp.expert.runtime;

import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.exceptions.ModelParserException;
import by.bsuir.iit.kp.expert.presentation.Model;
import by.bsuir.iit.kp.expert.presentation.ScenarioStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatement;
import by.bsuir.iit.kp.expert.runtime.statements.IStatementExecutionResult;
import by.bsuir.iit.kp.expert.runtime.statements.StatementsFactory;
import by.bsuir.iit.kp.expert.runtime.statements.impl.StatementExecutionResult;
import by.bsuir.iit.kp.expert.ui.IUserInterface;
import by.bsuir.iit.kp.expert.util.Utils;

public class ModelRunner {
	
	private IUserInterface ui;
	private static final StatementsFactory factory = StatementsFactory.getInstance();
	private static final String CONSULTATION_STARTED = "---- Consultation started -----";
	private static final String CONSULTATION_FINISHED = "---- Consultation finished ----";
	
	public ModelRunner(IUserInterface ui) {
		this.ui = ui;
	}
	
	public void run(Model model) throws ModelException {
		ModelEvaluator modelEvaluator = new ModelEvaluator(model);
		modelEvaluator.setUserInterface(ui);
		
		ui.showMessage(CONSULTATION_STARTED);
		
		Stack callStack = new Stack();

		int pointer = 0;
		List scenario = model.getScenario();
		int size = scenario.size();
		while (pointer < size) {
			ScenarioStatement scenarioStatement = (ScenarioStatement)scenario.get(pointer);
			IStatementExecutionResult res = run(scenarioStatement,modelEvaluator);
			if (res != null) {
				int type = res.getType();
				String label;
				
				switch (type) {
					case StatementExecutionResult.NEXT:
						pointer++;
						break;
					case StatementExecutionResult.GOTO:
						label = (String)res.getArg();
						pointer = getStatementIndexByLabel(scenario,label);
						break;
					case StatementExecutionResult.CALL:
						label = (String)res.getArg();
						callStack.push(new Integer(pointer));
						pointer = getStatementIndexByLabel(scenario,label); 
						break;
					case StatementExecutionResult.RETURN:
						try {
							Integer p = (Integer)callStack.pop();
							pointer = p.intValue() + 1;
						} catch (EmptyStackException e) {
							throw new ModelException("Runtime error: Call stack underflow",e);
						}
						break;
					case StatementExecutionResult.STOP:
						pointer = size;
						break;
					default:
						pointer++;
				}
			} else {
				pointer++;
			}
		}
		ui.showMessage(CONSULTATION_FINISHED);
	}
	
	public IStatementExecutionResult run(ScenarioStatement scenarioStatement, IReferencesEvaluator evaluator) throws ModelException {
		IStatement statement = factory.getStatement(scenarioStatement.getId());
		if (statement == null) {
			throw new ModelException("statement implementation not found: " + scenarioStatement.getId());
		}
		
		String expression = scenarioStatement.getApplicabilityExpression();
		IStatementExecutionResult result = null;
		try {
			boolean applicable = Utils.isApplicable(expression,evaluator);
			if (applicable) {
				result = statement.execute(scenarioStatement.getArgumentLine(), evaluator);
			}
		} catch (ModelParserException e) {
			throw new ModelException(e.getMessage());
		}
		return result;
	}
	
	private static int getStatementIndexByLabel(List scenario, String label) throws ModelException {
		for (int i = 0; i < scenario.size(); i++) {
			ScenarioStatement statement = (ScenarioStatement)scenario.get(i);
			String statementLabel = statement.getLabel();
			if (statementLabel.equals(label)) {
				return i;
			}
		}
		throw new ModelException("Unable to find statement with label " + label);
	}

}
