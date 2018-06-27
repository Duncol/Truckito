package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.forms.trailer.TrailerForm;

@ApplicationScoped
public class TrailerEdit implements EditingContainerProvider<Trailer>, Serializable {
	private static final long serialVersionUID = 4127516769512231260L;

	@Inject
	private Instance<TrailerForm> formInstances;
	private TrailerForm form;
	private TruckitoWindow window;
	
	private Trailer trailer;
	private Consumer<Trailer> callback;

	@Override
	public void buildContainer() {
		if (form != null) {
			formInstances.destroy(form);
		}
		form = formInstances.get();
		if (trailer != null) {
			form.setObjectForEdit(trailer);
		}
		if (callback != null) {
			form.setCallback(callback);
		}
		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}
	
	@Override
	public void setItem(Trailer trailer) {
		this.trailer = trailer;
	}
	
	public void setCallback(Consumer<Trailer> callback) {
		this.callback = callback;
	}

	public void close() {
		window.close();
	}
}
