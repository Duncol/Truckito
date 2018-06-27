package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.TrailersDisplay;

@CDIView(TrailersDisplayView.NAME)
public class TrailersDisplayView extends VerticalLayout implements View {
	private static final long serialVersionUID = -7570727667879618874L;
	public static final String NAME = "trailers";

	@Inject
	private TrailersDisplay display;

	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(display);
	}
}
