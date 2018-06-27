package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.forms.DriverForm;

@ApplicationScoped
public class DriverEdit implements EditingContainerProvider<Driver>, Serializable {
	private static final long serialVersionUID = -2793895552842084875L;

	@Inject
	private Instance<DriverForm> formInstances;
	private DriverForm form;
	private TruckitoWindow window;

	private Driver driver;
	private Consumer<Driver> callback;
	
	@Override
	public void buildContainer() {
		if (form != null) {
			formInstances.destroy(form);
		}
		form = formInstances.get();
		if (driver != null) {
			form.setObjectForEdit(driver);
		}
		if (callback != null) {
			form.setCallback(callback);
		}
		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}
	
	@Override
	public void setItem(Driver driver) {
		this.driver = driver;
	}
	
	public void setCallback(Consumer<Driver> callback) {
		this.callback = callback;
	}

	public void close() {
		window.close();
	}
}
