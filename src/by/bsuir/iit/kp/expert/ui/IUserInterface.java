package by.bsuir.iit.kp.expert.ui;

import by.bsuir.iit.kp.expert.presentation.base.Constraints;



public interface IUserInterface {
	
	public void showMessage(String message);
	
	// simple
	public Double promtForValue(String promt);
	public Double promtForValue(String promt, Constraints constraints);
	
	// choosing
	public int chooseValue(String promt, String[] names);
	
	// multiple
	public Double[] promtForValues(String promt, String[] names);
	public Double[] promtForValues(String promt, String[] names, Constraints[] hints);

	// errors
	public void showError(String errorMessage);
}
