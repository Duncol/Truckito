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

import pl.duncol.truckito.dao.DispositionDAO;
import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@NormalUIScoped
public class DispositionDisplay extends TruckitoDisplay<Disposition> {
	private static final long serialVersionUID = 5433578465441391238L;

	private Logger log = Logger.getLogger(DispositionDisplay.class);

	@Inject
	private DispositionDAO dispositionDAO;

	@Inject
	private AddingContainerProvider<Disposition> dispositionAdder;

	@Inject
	private EditingContainerProvider<Disposition> dispositionEditor;

	private List<Disposition> dispositions;

	private HorizontalLayout actionTopRow;
	private TruckitoGrid<Disposition> dispositionsGrid;

	@PostConstruct
	private void postConstruct() {
		actionTopRow = new HorizontalLayout();
		actionTopRow.setMargin(false);

		Button dispositionAddButton = forgeAddDispositionButton();
		Button dispositionRemoveSelectedButton = forgeRemoveSelectedDispositionsButton();

		actionTopRow.addComponent(dispositionAddButton);
		actionTopRow.addComponent(dispositionRemoveSelectedButton);

		prepareDispositionsGrid();
	}

	private Button forgeRemoveSelectedDispositionsButton() {
		Button dispositionRemoveSelectedButton = new Button("Usuń zaznaczone dyspozycje");
		dispositionRemoveSelectedButton.addClickListener(this::removeSelectedItems);
		return dispositionRemoveSelectedButton;
	}

	private Button forgeAddDispositionButton() {
		Button dispositionAddButton = new Button("Dodaj dyspozycję");
		dispositionAddButton.addClickListener(this::addDispositionButtonClick);
		return dispositionAddButton;
	}

	private void prepareDispositionsGrid() {
		dispositionsGrid = new TruckitoGrid<>();
		dispositionsGrid.setSizeFull();
		dispositionsGrid.setSelectionMode(SelectionMode.MULTI);
		dispositionsGrid.addColumn(Disposition::getDossierNumber).setCaption("Dossier");
		dispositionsGrid.addColumn(Disposition::getCreateDateForDisplay).setCaption("Data utworzenia");
		dispositionsGrid.addColumn(Disposition::getLastDriver).setCaption("Kierowca");
		dispositionsGrid.addColumn(Disposition::getLastTruck).setCaption("Ciężarówka");
		dispositionsGrid.addColumn(Disposition::getLastTrailer).setCaption("Naczepa");
		dispositionsGrid.addColumn(Disposition::isActive).setCaption("Czy aktywna");

		dispositionsGrid.addItemClickListener(this::dispositionClickHandler);
	}

	private void loadDispositions() {
		dispositions = dispositionDAO.findAll();
		log.infof("Retrieved %d drivers", dispositions.size());
		dispositionsGrid.setItems(dispositions);
	}

	private void addDispositionButtonClick(ClickEvent click) {
		dispositionAdder.setCallback(this::afterDispositionAdding);
		dispositionAdder.buildContainer();
	}

	private void removeSelectedItems(ClickEvent click) {
		Set<Disposition> selectedDispositions = dispositionsGrid.getSelectedItems();
		int selectionSize = selectedDispositions.size();
		if (selectionSize > 0) {
			dispositionsGrid.getSelectedItems().forEach(o -> {
				dispositionDAO.delete(o);
			});
			log.infof("Deleted %d dispositions", selectionSize);

        	TruckitoNotification.builder().setTitle("Usunięto " + selectionSize + " dyspozycji").show();
			reloadPage();
		} else {
        	TruckitoNotification.builder().setTitle("Zaznacz dyspozycje, które chcesz usunąć").show();
		}
	}

	private void dispositionClickHandler(ItemClick<Disposition> itemClick) {
		dispositionEditor.setItem(itemClick.getItem());
		dispositionEditor.setCallback(this::afterCompanyEdit);
		dispositionEditor.buildContainer();
	}

	private void afterDispositionAdding(Disposition disposition) {
		dispositionAdder.close();
		dispositionsGrid.addItem(disposition);
	}

	private void afterCompanyEdit(Disposition disposition) {
		dispositionEditor.close();
		dispositionsGrid.updateItem(disposition);
	}

	@Override
	public void attach() {
		loadDispositions();
		super.attach();

		addComponent(actionTopRow);
		if (!dispositions.isEmpty()) {
			addComponent(dispositionsGrid);
		} else {
			Label emptyListLabel = new Label("Brak dyspozycji do wyświetlenia");
			emptyListLabel.setSizeUndefined();
			addComponent(emptyListLabel);
			setComponentAlignment(emptyListLabel, Alignment.MIDDLE_CENTER);
		}
	}
}
