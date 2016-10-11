package by.bsuir.iit.kp.expert.presentation.base;

import by.bsuir.iit.kp.expert.exceptions.ModelException;

public class ValuableIdentificator extends Identificator {
	
	private double defaultValue;
	private boolean hasDefaultValue = false;

	private double value;
	private boolean valueReady = false;
	
	private ComplexIdentificator parent = null;
	
	private Constraints constraints = Constraints.EmptyConstraints;
	
	public ValuableIdentificator() {
		super();
	}

	public ValuableIdentificator(String id, String description) {
		super(id, description);
	}

	public ValuableIdentificator(String id) {
		super(id);
	}

	public double getDefaultValue() throws ModelException {
		if (!hasDefaultValue()) {
			throw new ModelException("Object does not have default value");
		}
		return defaultValue;
	}
	
	public double getValue() throws ModelException {
		if (!isValueReady()) {
			throw new ModelException("Value for object is not ready");
		}

		return value;
	}
	
	public Constraints getConstraints() {
		return constraints;
	}

	public void setDefaultValue(double defaultValue) throws ModelException {
		if (constraints.satisfies(defaultValue)) {
			this.defaultValue = defaultValue;
			hasDefaultValue = true;
		} else {
			throw new ModelException("Value does not satisfies constraits");
		}
	}

	public void setValue(double value) throws ModelException {
		if (constraints.satisfies(value)) {
			this.value = value;
			valueReady = true;
		} else {
			throw new ModelException("Value does not satisfies constraints");
		}
	}
	
	public void setConstraints(Constraints constraints) throws ModelException {
		if (hasDefaultValue()) {
			if (!constraints.satisfies(getDefaultValue())) {
				throw new ModelException("Default value of object does not satisfies constraints");
			}
		}
		
		if (isValueReady()) {
			if (!constraints.satisfies(getValue())) {
				throw new ModelException("Value of object does not satisfies constraints");
			}
		}
		
		this.constraints = constraints;
	}
	
	public void reset() {
		super.reset();
		valueReady = false;
	}
	
	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	public boolean isValueReady() {
		return valueReady;
	}
	
	public ComplexIdentificator getParent() {
		return parent;
	}

	public void setParent(ComplexIdentificator parent) {
		this.parent = parent;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(super.toString());
		Constraints constr = getConstraints();
		if (constr != null) {
			buffer.append(", ");
			buffer.append(constr);
		}
		try {
			double defValue = getDefaultValue();
			buffer.append(", default ");
			buffer.append(defValue);
		} catch (ModelException e) {
			// do nothing			
		}
		try {
			double value = getValue();
			buffer.append(", value ");
			buffer.append(value);
		} catch (ModelException e) {
			// do nothing			
		}
		return buffer.toString(); 
	}
	
}
