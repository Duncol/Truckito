package pl.duncol.truckito.web.components.providers.interfaces;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.web.components.TruckitoWindow;

public abstract class ObjectWindowProvider {
	protected TruckitoWindow window;
	protected Company object;

	public void releaseContainer() {
		object = null;
	}
}
