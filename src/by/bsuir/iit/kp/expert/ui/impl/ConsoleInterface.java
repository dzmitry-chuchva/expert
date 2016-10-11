package by.bsuir.iit.kp.expert.ui.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import by.bsuir.iit.kp.expert.presentation.base.Constraints;
import by.bsuir.iit.kp.expert.ui.AbstractConsoleInterface;
import by.bsuir.iit.kp.expert.ui.ConsoleUIUtils;
import by.bsuir.iit.kp.expert.ui.IUserInterface;

public class ConsoleInterface extends AbstractConsoleInterface implements IUserInterface {
	
	private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public Double promtForValue(String promt, Constraints c) {
		Double ret = null;
		while (true) {
			try {
				System.out.println(ConsoleUIUtils.hintString(promt, c.toString()));
				String answer = reader.readLine();
				if (answer.trim().length() > 0) {
					ret = Double.valueOf(answer);
					if (c.satisfies(ret.doubleValue())) {
						break;
					}
				} else {
					// user does not know
					break;
				}
			} catch (IOException e) {
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}
	
	public void showError(String errorMessage) {
		System.out.println(ConsoleUIUtils.getErrorString(errorMessage));
	}

	public int chooseValue(String promt, String[] names) {
		if (names == null && names.length == 0) {
			return -1;
		}
		
		System.out.println(promt);
		for (int i = 0; i < names.length; i++) {
			System.out.println(ConsoleUIUtils.getVariantString(i + 1, names[i]));
		}
		
		do {
			try {
				System.out.print(ConsoleUIUtils.getVariantPromtString(1, names.length));
				String answer = reader.readLine();
				int chosen = Integer.parseInt(answer);
				if (chosen < 1 || chosen > names.length) {
					throw new NumberFormatException();
				}
				
				return chosen - 1;
			} catch (IOException e) {
			} catch (NumberFormatException e) {
			}
		} while (true);
	}

	public Double[] promtForValues(String promt, String[] names, Constraints[] c) {
		if (names == null && names.length == 0) {
			return new Double[0];
		}
		
		Double[] values = new Double[names.length];
		System.out.println(promt);
		for (int i = 0; i < names.length; i++) {
			do {
				try {
					String hint = null;
					if (c[i] != null) {
						hint = c[i].toString();
					}
					System.out.print(ConsoleUIUtils.getValuePromtString(names[i], hint ));
					String answer = reader.readLine();
					if (answer.trim().length() > 0) {
						Double value = Double.valueOf(answer);
						if (c[i] != null && c[i].satisfies(value.doubleValue())) {
							values[i] = value;
							break;
						}
					} else {
						// user does not know value for this attribute
						values[i] = null;
						break;
					}
				} catch (IOException e) {
				} catch (NumberFormatException e) {
				}
			} while (true);
		}
		return values;
	}

	public void showMessage(String message) {
		System.out.println(message);
	}
}
