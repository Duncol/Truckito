package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.forms.DispositionForm;

@ApplicationScoped
public class DispositionEdit implements EditingContainerProvider<Disposition>, Serializable {
	private static final long serialVersionUID = 7023297385453625041L;

	private DispositionForm form;
	private TruckitoWindow window;

	private Disposition disposition;
	private Consumer<Disposition> callback;

	@Override
	public void buildContainer() {

		if (disposition != null) {
			form.setObjectForEdit(disposition);
		}
		if (callback != null) {
			form.setCallback(callback);
		}
 		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	@Override
	public void setCallback(Consumer<Disposition> callback) {
		this.callback = callback;
	}

	@Override
	public void close() {
		window.close();
	}

	@Override
	public Disposition getObject() {
		return disposition;
	}

	@Override
	public void setObject(Disposition object) {
		disposition = object;
	}
}
