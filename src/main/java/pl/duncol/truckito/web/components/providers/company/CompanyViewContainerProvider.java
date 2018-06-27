package pl.duncol.truckito.web.components.providers.company;

import javax.enterprise.context.ApplicationScoped;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.ObjectWindowProvider;
import pl.duncol.truckito.web.components.providers.interfaces.ViewingContainerProvider;
import pl.duncol.truckito.web.forms.company2.CompanyViewForm;

@ApplicationScoped
public class CompanyViewContainerProvider extends ObjectWindowProvider implements ViewingContainerProvider<Company>{

	@Override
	public void buildContainer() {
		CompanyViewForm form = new CompanyViewForm();

		if (object != null) {
			form.setObject(object);
		}

		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	@Override
	public void close() {
		window.close();
	}

	@Override
	public Company getObject() {
		return object;
	}

	@Override
	public void setObject(Company object) {
		this.object = object;
	}
}
