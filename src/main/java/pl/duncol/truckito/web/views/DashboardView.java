package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.Dashboard;

@CDIView(DashboardView.NAME)
public class DashboardView extends VerticalLayout implements View {
	private static final long serialVersionUID = 5367818401533920138L;

	public static final String NAME = "dashboard";
	
	@Inject
	private Dashboard dashboard;
	
	@PostConstruct
	private void postConstruct() {
//		addStyleName("no-padding");
		addStyleName("p-0");
		addComponent(dashboard);
	}
}
