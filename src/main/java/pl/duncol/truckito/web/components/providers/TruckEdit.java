package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.vehicle.Truck;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.forms.TruckForm;

@ApplicationScoped
public class TruckEdit implements EditingContainerProvider<Truck>, Serializable {
	private static final long serialVersionUID = 5560525837918203163L;

	@Inject
	private Instance<TruckForm> formInstances;
	private TruckForm form;
	private TruckitoWindow window;
	
	private Truck truck;
	private Consumer<Truck> callback;

	@Override
	public void buildContainer() {
		if (form != null) {
			formInstances.destroy(form);
		}
		form = formInstances.get();
		if (truck != null) {
			form.setObjectForEdit(truck);
		}
		if (callback != null) {
			form.setCallback(callback);
		}
		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}
	
	public void setItem(Truck truck) {
		this.truck = truck;
	}
	
	public void setCallback(Consumer<Truck> callback) {
		this.callback = callback;
	}

	public void close() {
		window.close();
	}
}
