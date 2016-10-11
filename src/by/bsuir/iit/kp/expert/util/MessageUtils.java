package by.bsuir.iit.kp.expert.util;

import by.bsuir.iit.kp.expert.exceptions.ModelException;
import by.bsuir.iit.kp.expert.presentation.base.ComplexIdentificator;
import by.bsuir.iit.kp.expert.presentation.base.Identificator;
import by.bsuir.iit.kp.expert.presentation.base.ValuableIdentificator;

public class MessageUtils {
	
	private static final char SUBST_PLACE_SIGN = '%';

	public static final String getStringFromTemplate(String template, Identificator identificator) {
		int position = 0;
		int sign;
		StringBuffer buffer = new StringBuffer();
		int length = template.length();
		while (position < length && (sign = template.indexOf(SUBST_PLACE_SIGN,position)) >= 0) {
			String substring = template.substring(position, sign);
			buffer.append(substring);
			
			int typePos = sign + 1;
			if (typePos < length) {
				switch (template.charAt(typePos)) {
				// name
				case 'n':
					buffer.append(identificator.getId());
					break;
				// value
				case 'v':
					if (identificator instanceof ValuableIdentificator) {
						ValuableIdentificator valuable = (ValuableIdentificator)identificator;
						if (valuable.isValueReady()) {
							try {
								double value = valuable.getValue();
								buffer.append(String.format("%.2f", new Object[] { new Double(value) }));
							} catch (ModelException e) {
							}
						}
					}
					break;
				// full reference of symbol (for number|symbol attrs same as 'n')
				case 'g':
					String child = identificator.getId();
					if (identificator instanceof ValuableIdentificator) {
						ValuableIdentificator valuable = (ValuableIdentificator)identificator;
						ComplexIdentificator parent = valuable.getParent();
						if (parent != null) {
							child = Utils.getFullReference(parent.getId(), child);
						}
					}
					buffer.append(child);
					break;
				// parent id
				case 'p':
					if (identificator instanceof ValuableIdentificator) {
						ValuableIdentificator valuable = (ValuableIdentificator)identificator;
						ComplexIdentificator parent = valuable.getParent();
						if (parent != null) {
							buffer.append(parent.getId());
						}
					}
					break;
				// parent description
				case 'f':
					if (identificator instanceof ValuableIdentificator) {
						ValuableIdentificator valuable = (ValuableIdentificator)identificator;
						ComplexIdentificator parent = valuable.getParent();
						if (parent != null) {
							buffer.append(parent.getDescription());
						}
					}
					break;
				// description
				case 'd':
					buffer.append(identificator.getDescription());
					break;
				case SUBST_PLACE_SIGN:
					buffer.append(SUBST_PLACE_SIGN);
					break;
				default:
					break;
				}
			}
			position = typePos + 1;
		}
		return buffer.toString();
	}
	
	private MessageUtils() {
		
	}

}
