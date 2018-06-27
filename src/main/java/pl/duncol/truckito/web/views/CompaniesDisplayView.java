package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.CompanyDisplay;

@CDIView(CompaniesDisplayView.NAME)
public class CompaniesDisplayView extends VerticalLayout implements View {
	private static final long serialVersionUID = 4199388293096633760L;

	public static final String NAME = "companies";
	
	@Inject
	private CompanyDisplay display;
	
	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(display);
	}
}
