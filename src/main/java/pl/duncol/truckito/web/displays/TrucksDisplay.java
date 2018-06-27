package pl.duncol.truckito.web.displays;

import static pl.duncol.truckito.web.TruckitoUI.reloadPage;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import pl.duncol.truckito.dao.TruckDAO;
import pl.duncol.truckito.domain.vehicle.Truck;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@NormalUIScoped
public class TrucksDisplay extends TruckitoDisplay<Truck> {
	private static final long serialVersionUID = -930171261599511003L;

	private Logger log = Logger.getLogger(TrucksDisplay.class);

	@Inject
	private TruckDAO truckDAO;

	@Inject
	private AddingContainerProvider<Truck> truckAdder;

	@Inject
	private EditingContainerProvider<Truck> truckEditor;

	private List<Truck> trucks;

	private HorizontalLayout actionTopRow;
	private TruckitoGrid<Truck> trucksGrid;

	@PostConstruct
	public void postCosntruct() {
		actionTopRow = new HorizontalLayout();
		actionTopRow.setMargin(false);
		Button addTruckButton = new Button("Dodaj ciężarówkę");
		Button removeSelectedTrucksButton = new Button("Usuń zaznaczone ciężarówki");
		actionTopRow.addComponent(addTruckButton);
		actionTopRow.addComponent(removeSelectedTrucksButton);

		prepareTrucksGrid();

		addTruckButton.addClickListener(this::addTruckButtonClick);
		removeSelectedTrucksButton.addClickListener(this::removeSelectedTrailers);
	}

	private void prepareTrucksGrid() {
		trucksGrid = new TruckitoGrid<>();
		trucksGrid.setSizeFull();
		trucksGrid.setSelectionMode(SelectionMode.MULTI);
		trucksGrid.addColumn(Truck::getLicensePlate).setCaption("Nr rejestracyjny");
		trucksGrid.addColumn(Truck::getModel).setCaption("Marka/Model");
		// truckGrid.addColumn(Truck::).setCaption("Czy w użyciu");

		trucksGrid.addItemClickListener(this::truckClickHandler);
	}

	private void loadTrucks() {
		trucks = truckDAO.findAll();
		log.infof("Retrieved %d trucks", trucks.size());
		trucksGrid.setItems(trucks);
	}

	private void removeSelectedTrailers(ClickEvent click) {
		Set<Truck> selectedTrucks = trucksGrid.getSelectedItems();
		int selectionSize = selectedTrucks.size();
		if (selectionSize > 0) {
			trucksGrid.getSelectedItems().forEach(o -> {
				truckDAO.delete(o);
			});
			log.infof("Deleted %d trucks", selectionSize);
        	TruckitoNotification.builder().setTitle("Usunięto " + selectionSize + " ciężarówek").show();
			reloadPage();
		} else {
        	TruckitoNotification.builder().setTitle("Zaznacz ciężarówki, które chcesz usunąć").show();
		}
	}

	private void addTruckButtonClick(ClickEvent click) {
		truckAdder.setCallback(this::afterTruckAdding);
		truckAdder.buildContainer();
	}

	private void truckClickHandler(ItemClick<Truck> itemClick) {
		truckEditor.setObject(itemClick.getItem());
		truckEditor.setCallback(this::afterDriverEdit);
		truckEditor.buildContainer();
	}

	private void afterTruckAdding(Truck truck) {
		truckAdder.close();
		trucksGrid.addItem(truck);
	}

	private void afterDriverEdit(Truck truck) {
		truckEditor.close();
		trucksGrid.updateItem(truck);
	}

	@Override
	public void attach() {
		loadTrucks();
		super.attach();

		addComponent(actionTopRow);

		if (!trucks.isEmpty()) {
			addComponent(trucksGrid);
		} else {
			Label emptyListLabel = new Label("Brak ciężarówek do wyświetlenia");
			addComponent(emptyListLabel);
			setComponentAlignment(emptyListLabel, Alignment.MIDDLE_CENTER);
			;
		}
	}

	@Override
	protected void getRemoveItemsAction(ClickEvent click) {
		// TODO Auto-generated method stub

	}

	@Override
	protected DataProvider<Truck, Void> forgeDataProvider() {
		// TODO Auto-generated method stub
		return null;
	}


}
