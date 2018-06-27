package pl.duncol.truckito.web.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.displays.Search;

@CDIView(SearchView.NAME)
public class SearchView extends VerticalLayout implements View {
	private static final long serialVersionUID = -7013003969070046583L;
	
	public static final String NAME = "search";
	
	@Inject Search search;
	
	@PostConstruct
	private void postConstruct() {
		addStyleName("p-0");
		
		addComponent(search);
	}

}
