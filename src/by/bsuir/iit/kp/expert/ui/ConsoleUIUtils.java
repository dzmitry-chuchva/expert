package by.bsuir.iit.kp.expert.ui;

import java.text.MessageFormat;

public class ConsoleUIUtils {

	public static final String HINTED_STRING = "[hint: {1}] {0}";
	public static final String ERROR_STRING = "[error]: {0}";
	public static final String VARIANT_STRING = "\t{0}) {1}";
	public static final String NUMBER_PROMT_STRING = "Enter value between {0} and {1}: ";
	
	public static final String VALUE_PROMT_STRING = "\t * {0}: ";
	public static final String CONSTRAINED_VALUE_PROMT_STRING = "\t * {0} [{1}]: ";
	
	public static final String hintString(String promt, String hint) {
		return MessageFormat.format(ConsoleUIUtils.HINTED_STRING, new Object[] { promt, hint });
	}
	public static final String[] hintStrings(String[] names, String[] hints) {
		String[] hinted = new String[names.length];
		for (int i = 0; i < names.length; i++) {
			if (hints.length > i) {
				hinted[i] = ConsoleUIUtils.hintString(names[i], hints[i]);
			} else {
				hinted[i] = names[i];
			}
		}
		return hinted;
	}
	
	public static final String getErrorString(String error) {
		return MessageFormat.format(ERROR_STRING, new Object[] { error });
	}
	
	public static final String getVariantString(int label, String variant) {
		return MessageFormat.format(VARIANT_STRING, new Object[] { new Integer(label), variant });
	}
	
	public static final String getVariantPromtString(int from, int to) {
		return MessageFormat.format(NUMBER_PROMT_STRING, new Object[] { new Integer(from), new Integer(to) });
	}
	
	public static final String getValuePromtString(String name, String hint) {
		if (hint != null) {
			return MessageFormat.format(CONSTRAINED_VALUE_PROMT_STRING, new Object[] { name, hint });
		} else {
			return MessageFormat.format(VALUE_PROMT_STRING, new Object[] { name });
		}
	}
	
	private ConsoleUIUtils() {
		
	}

}
