package pl.duncol.truckito.web.displays;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.chat.ChatBox;

@NormalUIScoped
public class Dashboard extends VerticalLayout implements View {
	private static final long serialVersionUID = 8651603395091589959L;

	private Logger log = Logger.getLogger(Dashboard.class);
    
    private HorizontalLayout mainLayout;
    
    @Inject
    private ChatBox chatBox;
    
    @PostConstruct
    private void postConstruct() {
    	setSizeFull();
    	addStyleName("dashboard");
    	
    	prepareMainLayout();
    	addComponent(chatBox);
    	addComponents();
    }
    
    private void prepareMainLayout() {
    	mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
    }
    
    private void addComponents() {
		mainLayout.addComponent(chatBox);
		
		addComponent(mainLayout);
    }
}