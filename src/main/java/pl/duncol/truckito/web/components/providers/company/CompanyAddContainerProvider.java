package pl.duncol.truckito.web.components.providers.company;

import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.vaadin.data.ValidationException;
import com.vaadin.ui.UI;

import pl.duncol.truckito.dao.CompanyDAO;
import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.utils.AsyncThread;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.ObjectWindowProvider;
import pl.duncol.truckito.web.forms.company2.CompanyEditForm;

@ApplicationScoped
public class CompanyAddContainerProvider extends ObjectWindowProvider implements AddingContainerProvider<Company> {
	private Consumer<Company> callback;

	@Inject
	private CompanyDAO dao;

	@Override
	public void buildContainer() {
		CompanyEditForm form = new CompanyEditForm();

		if (object != null) {
			form.setObject(object);
		}
		form.setButtonAction(e -> {
			try {
				object = form.extractObject();
				UI current = UI.getCurrent();
				current.access(new AsyncThread(() -> {
					UI.setCurrent(current);
					Company company = saveObject(object);
					if (callback != null && company != null) {
						callback.accept(company);
					}
					window.close();
					current.push();
				}));
			} catch (ValidationException ex) {
				ex.printStackTrace();
			} finally {
				form.enableSubmitButton();
			}
		});

		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	@Override
	public void setCallback(Consumer<Company> callback) {
		this.callback = callback;
	}

	@Override
	public void close() {
		window.close();
	}

	@Override
	public Company getObject() {
		return object;
	}

	private Company saveObject(Company company) {
		return dao.update(company);
	}
}
