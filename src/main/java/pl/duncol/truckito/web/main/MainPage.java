package pl.duncol.truckito.web.main;

import org.jboss.logging.Logger;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.web.login.access.AccessControl;
import pl.duncol.truckito.web.main.menu.TruckitoMenuBar;
import pl.duncol.truckito.web.main.navilayout.TruckitoNaviLayout;
import pl.duncol.truckito.web.main.notepad.TruckitoNotepad;
import pl.duncol.truckito.web.views.DashboardView;

public class MainPage extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public static final String CURRENT_VIEW_ATTR_NAME = "currentView";
	
	private Logger log = Logger.getLogger(MainPage.class);
	
	private MenuBar menuBar;
	private Layout naviLayout;
	private TruckitoNotepad notepad;
	
	private Navigator navigator;
	
	private AccessControl auth;
	
	public MainPage(AccessControl auth, CDIViewProvider cdiViewProvider) {
		this.auth = auth;
		setMargin(false);
		
		forgeMenuBar();
		forgeNaviLayout();
		forgeNotepad();
		
		initializeNavigation(cdiViewProvider);
	}
	
	private void forgeMenuBar() {
		menuBar = new TruckitoMenuBar(auth);
		addComponent(menuBar);
	}
	
	private void forgeNaviLayout() {
		naviLayout = new TruckitoNaviLayout();
		addComponent(naviLayout);
	}
	
	private void forgeNotepad() {
		notepad = new TruckitoNotepad();
		addComponent(notepad);
	}
	
	private void initializeNavigation(CDIViewProvider cdiViewProvider) {
		final Navigator navigator = new Navigator(UI.getCurrent(), naviLayout);
		navigator.addProvider(cdiViewProvider);
		if (getLastViewName() != null) {
			UI.getCurrent().getNavigator().navigateTo(getLastViewName());
		} else {			
			UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);			
		}
		UI.getCurrent().getNavigator().addViewChangeListener(new ViewChangeListener() {
			
			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				if (auth.getCurrentUser()!=null) {
					return true;
				}
				return false;
			}
			
			@Override 
			public void afterViewChange(ViewChangeEvent event) {
				try {
					Object o = Class.forName(event.getNewView().getClass().getName()).getField("NAME").get(null);
					if (o instanceof String) {
						log.info("Setting current view in Vaadin session: " + ((String) o));
						setLastViewName((String) o);
					}
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private String getLastViewName() {
		return (String) VaadinSession.getCurrent().getAttribute(CURRENT_VIEW_ATTR_NAME);
	}
	
	private void setLastViewName(String viewName) {
		VaadinSession.getCurrent().setAttribute(CURRENT_VIEW_ATTR_NAME, viewName);
	}
}
