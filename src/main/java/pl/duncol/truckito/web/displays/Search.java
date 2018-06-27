package pl.duncol.truckito.web.displays;

import javax.annotation.PostConstruct;

import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.navigator.View;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@NormalUIScoped
public class Search extends VerticalLayout implements View {
	private static final long serialVersionUID = 9138482935630552389L;

	@PostConstruct
	private void postConstruct() {
		Label temp = new Label("Szukajcie, a znajdziecie... (coming soon...)");
		addComponent(temp);
		setComponentAlignment(temp, Alignment.MIDDLE_CENTER);
	}
}
