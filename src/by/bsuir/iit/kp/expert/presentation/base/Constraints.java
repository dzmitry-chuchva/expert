package by.bsuir.iit.kp.expert.presentation.base;

import by.bsuir.iit.kp.expert.exceptions.ModelException;

public class Constraints {
	
	public static final Constraints EmptyConstraints = new Constraints();
		
	private double maximum = 0;
	private double minimum = 0;
	
	private boolean hasMaximum;
	private boolean hasMinimum;
	
	public Constraints() {
		this.hasMaximum = false;
		this.hasMinimum = false;
	}	
	
	public Constraints(boolean settingMinimum, double value) {
		try {
			if (settingMinimum) {
				setMinimum(value);
			} else {
				setMaximum(value);
			}
		} catch (ModelException e) {
			// never
		}
	}
	
	public Constraints(double minimum, double maximum) throws ModelException {
		setMaximum(maximum);
		setMinimum(minimum);
	}
	
	public boolean hasMaximum() {
		return hasMaximum;
	}

	public boolean hasMinimum() {
		return hasMinimum;
	}

	public double getMaximum() throws ModelException {
		if (hasMaximum()) {
			return maximum;
		} else {
			throw new ModelException("Constraints do not have minimum value");
		}
	}

	public void setMaximum(double maximum) throws ModelException {
		if (hasMinimum()) {
			if (maximum < getMinimum()) {
				throw new ModelException("Maximum is lower than minimum"); 
			}
		}
		this.maximum = maximum;
		this.hasMaximum = true;		
	}

	public double getMinimum() throws ModelException {
		if (hasMinimum()) {
			return minimum;
		} else {
			throw new ModelException("Constraints do not have minimum value");
		}
	}

	public void setMinimum(double minimum) throws ModelException {
		if (hasMaximum()) {
			if (minimum > getMaximum()) {
				throw new ModelException("Minimum is greater than maximum"); 
			}
		}
		this.minimum = minimum;
		this.hasMinimum = true;
	}

	public boolean satisfies(double value) {
		try {
			if (hasMaximum()) {
				if (value > getMaximum()) {
					return false;
				}
			}
		
			if (hasMinimum()) {
				if (value < getMinimum()) {
					return false;
				}
			}
		
			return true;
		} catch (ModelException e) {
			// never
			throw new Error(e);
		}
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer("constraints:");
		try {
			if (hasMinimum()) {
				buffer.append(" from ");
				buffer.append(getMinimum());
			}
			
			if (hasMaximum()) {
				buffer.append(" to ");
				buffer.append(getMaximum());
			}
		} catch (ModelException e) {
			// never
		}
		
		if (!hasMaximum() && !hasMinimum()) {
			buffer.append(" none");
		}
		
		return buffer.toString();
	}

}
