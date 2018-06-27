package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.DriversDisplay;

@CDIView(DriversDisplayView.NAME)
public class DriversDisplayView extends VerticalLayout implements View {
	private static final long serialVersionUID = -2204028921706774782L;
	public static final String NAME = "drivers";

	@Inject
	private DriversDisplay display;
	
	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(display);
	}
}