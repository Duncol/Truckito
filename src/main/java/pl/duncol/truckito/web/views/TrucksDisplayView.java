package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.TrucksDisplay;

@CDIView(TrucksDisplayView.NAME)
public class TrucksDisplayView extends VerticalLayout implements View {
	private static final long serialVersionUID = -5130066714984862937L;
	public static final String NAME = "trucks";
	
	@Inject
	private TrucksDisplay display;
	
	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(display);
	}
}
