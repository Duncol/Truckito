package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.DispositionDisplay;

@CDIView(DispositionsDisplayView.NAME)
public class DispositionsDisplayView extends VerticalLayout implements View {
	private static final long serialVersionUID = -6731840365340381241L;
	public static final String NAME = "dispositions";
	
	@Inject
	private DispositionDisplay display;
	
	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(display);
	}
}
