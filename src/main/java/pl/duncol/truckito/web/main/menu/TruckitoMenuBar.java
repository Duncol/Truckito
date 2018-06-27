package pl.duncol.truckito.web.main.menu;

import static pl.duncol.truckito.web.TruckitoUI.reloadPage;

import org.jboss.logging.Logger;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;

import pl.duncol.truckito.web.login.access.AccessControl;
import pl.duncol.truckito.web.views.CompaniesDisplayView;
import pl.duncol.truckito.web.views.DashboardView;
import pl.duncol.truckito.web.views.DispositionsDisplayView;
import pl.duncol.truckito.web.views.DriversDisplayView;
import pl.duncol.truckito.web.views.SearchView;
import pl.duncol.truckito.web.views.TrailersDisplayView;
import pl.duncol.truckito.web.views.TrucksDisplayView;

public class TruckitoMenuBar extends MenuBar {

	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getLogger(TruckitoMenuBar.class);

	private AccessControl auth;
	
	private MenuItem dashboard;
	
	private MenuItem dispositions;
	private MenuItem drivers;
	private MenuItem trucks;
	private MenuItem trailers;
	private MenuItem companies;
	
	private MenuItem add;
	private MenuItem addDisposition;
	private MenuItem addTruck;
	private MenuItem addTrailer;
	private MenuItem addDriver;
	private MenuItem addCompany;
	
	private MenuItem search;
	
	private MenuItem account;
	private MenuItem accountProfile;
	private MenuItem accountLogout;
	
	public TruckitoMenuBar(AccessControl auth) {
		this.auth = auth;
		setSizeFull();

		dashboard = addItem("Strona główna", e -> {
			navigateTo(DashboardView.NAME);
		});
		dispositions = addItem("Dyspozycje", e -> {
			navigateTo(DispositionsDisplayView.NAME);
		});
		drivers = addItem("Kierowcy", e -> {
			navigateTo(DriversDisplayView.NAME);
		});
		trucks = addItem("Ciężarówki", e -> {
			navigateTo(TrucksDisplayView.NAME);
		});
		trailers = addItem("Naczepy", e -> {
			navigateTo(TrailersDisplayView.NAME);
		});
		companies = addItem("Firmy", e -> {
			navigateTo(CompaniesDisplayView.NAME);
		});
		search = addItem("Szukaj", null, e -> {
			navigateTo(SearchView.NAME);
		}); // TODO add separate view for complex search, and add navigation as comand
		
		// ACCOUNT
		account = addItem(auth.getCurrentUser(), null);
		account.setStyleName("right-item");
		account.addItem("Profil", e -> {
			
		});
		account.addItem("Wyloguj", e -> {
			if (!auth.isUserSignedIn()) {
				log.warn("User is already signed off!");	
			}
			auth.unregisterCurrentUser();
			reloadPage();
		});
	}
	
	private void navigateTo(String viewName) {
		UI.getCurrent().getNavigator().navigateTo(viewName);
	}
}
