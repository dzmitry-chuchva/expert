package by.bsuir.iit.kp.expert.runtime;

import by.bsuir.iit.kp.expert.presentation.base.Identificator;

public interface IReferencesSource {

	public boolean referenceExists(String ref);
	public Identificator getReference(String ref);

}