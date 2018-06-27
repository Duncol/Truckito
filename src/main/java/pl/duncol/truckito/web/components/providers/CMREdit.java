package pl.duncol.truckito.web.components.providers;

import java.io.Serializable;
import java.util.function.Consumer;

import javax.enterprise.context.ApplicationScoped;

import com.vaadin.ui.UI;

import pl.duncol.truckito.domain.document.CMRDocument;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.forms.CMRForm;

@ApplicationScoped
public class CMREdit implements EditingContainerProvider<CMRDocument>, Serializable {
	private static final long serialVersionUID = 7023297385453625041L;

	private CMRForm form;
	private TruckitoWindow window;

	private CMRDocument cmr;
	private Consumer<CMRDocument> callback;

	@Override
	public void buildContainer() {
		if (cmr != null) {
			form.setObjectForEdit(cmr);
		}
		if (callback != null) {
			form.setCallback(callback);
		}
		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	@Override
	public void setObject(CMRDocument cmr) {
		this.cmr = cmr;
	}

	@Override
	public void setCallback(Consumer<CMRDocument> callback) {
		this.callback = callback;
	}

	@Override
	public void close() {
		window.close();
	}

	@Override
	public CMRDocument getObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
