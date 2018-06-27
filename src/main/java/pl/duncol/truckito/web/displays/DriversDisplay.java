package pl.duncol.truckito.web.displays;

import static pl.duncol.truckito.web.TruckitoUI.reloadPage;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import pl.duncol.truckito.dao.DriverDAO;
import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@NormalUIScoped
public class DriversDisplay extends TruckitoDisplay<Driver> {
	private static final long serialVersionUID = -930171261599511003L;

	private Logger log = Logger.getLogger(DriversDisplay.class);

	@Inject
	private DriverDAO driverDAO;

	@Inject
	private AddingContainerProvider<Driver> driverAdder;

	@Inject
	private EditingContainerProvider<Driver> driverEditor;

	private List<Driver> drivers;

	private HorizontalLayout actionTopRow;
	private TruckitoGrid<Driver> driversGrid;

	@PostConstruct
	private void postConstruct() {
		actionTopRow = new HorizontalLayout();
		actionTopRow.setMargin(false);
		Button addDriverButton = new Button("Dodaj kierowcę");
		Button removeSelectedDriversButton = new Button("Usuń zaznaczonych kierowców");
		actionTopRow.addComponent(addDriverButton);
		actionTopRow.addComponent(removeSelectedDriversButton);

		prepareDriversGrid();

		addDriverButton.addClickListener(this::addDriverButtonClick);
		removeSelectedDriversButton.addClickListener(this::removeSelectedDrivers);
	}

	private void prepareDriversGrid() {
		driversGrid = new TruckitoGrid<>();
		driversGrid.setSizeFull();
		driversGrid.setSelectionMode(SelectionMode.MULTI);
		driversGrid.addColumn(Driver::getFirstName).setCaption("Imię");
		driversGrid.addColumn(Driver::getLastName).setCaption("Nazwisko");
		driversGrid.addColumn(Driver::getPhoneNumber).setCaption("Telefon");
		// driverGrid.addColumn(Driver::).setCaption("Czy w użyciu");

		driversGrid.addItemClickListener(this::driverClickHandler);
	}

	private void loadDrivers() {
		drivers = driverDAO.findAll();
		log.infof("Retrieved %d drivers", drivers.size());
		driversGrid.setItems(drivers);
	}

	private void removeSelectedDrivers(ClickEvent click) {
		Set<Driver> selectedDrivers = driversGrid.getSelectedItems();
		int selectionSize = selectedDrivers.size();
		if (selectionSize > 0) {
			selectedDrivers.forEach(o -> {
				driverDAO.delete(o);
			});
			log.infof("Deleted %d drivers", selectionSize);
        	TruckitoNotification.builder().setTitle("Usunięto " + selectionSize + " kierowców").show();
			reloadPage();
		} else {
        	TruckitoNotification.builder().setTitle("Zaznacz kierowców, których chcesz usunąć").show();
		}
	}

	private void addDriverButtonClick(ClickEvent click) {
		driverAdder.setCallback(this::afterDriverAdding);
		driverAdder.buildContainer();
	}

	private void driverClickHandler(ItemClick<Driver> itemClick) {
		driverEditor.setItem(itemClick.getItem());
		driverEditor.setCallback(this::afterDriverEdit);
		driverEditor.buildContainer();
	}

	private void afterDriverAdding(Driver driver) {
		driverAdder.close();
		driversGrid.addItem(driver);
	}

	private void afterDriverEdit(Driver driver) {
		driverEditor.close();
		driversGrid.updateItem(driver);
	}

	@Override
	public void attach() {
		loadDrivers();
		super.attach();

		addComponent(actionTopRow);

		if (!drivers.isEmpty()) {
			addComponent(driversGrid);
		} else {
			Label emptyListLabel = new Label("Brak kierowców do wyświetlenia");
			addComponent(emptyListLabel);
			setComponentAlignment(emptyListLabel, Alignment.MIDDLE_CENTER);
		}
	}

}
