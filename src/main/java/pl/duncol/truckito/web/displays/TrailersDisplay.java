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
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import pl.duncol.truckito.dao.TrailerDAO;
import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.web.forms.trailer.TrailerAddForm;
import pl.duncol.truckito.web.forms.trailer.TrailerForm;
import pl.duncol.truckito.web.forms.interfaces.AddForm;
import pl.duncol.truckito.web.forms.interfaces.EditForm;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@NormalUIScoped
public class TrailersDisplay extends TruckitoDisplay<Trailer> {
	private static final long serialVersionUID = -426290712115523485L;
	private Logger log = Logger.getLogger(TrailersDisplay.class);

	@Inject
	private TrailerDAO trailerDAO;

	@PostConstruct
	public void postConstruct() {
		prepareActionTopRow();
		prepareTrailersGrid();
	}

	private void prepareTrailersGrid() {
		super.prepareGrid();
		grid.addColumn(Trailer::getLicensePlate).setCaption("Nr rejestracyjny");
		grid.addColumn(Trailer::getModel).setCaption("Marka/Model");
	}

	private void loadTrailers() {
		elements = trailerDAO.findAll();
		log.infof("Retrieved %d trailers", elements.size());
		grid.setItems(elements);
	}

	@Override
	public void attach() {
		loadTrailers();
		super.attach();

		addComponent(actionTopRow);

		if (!elements.isEmpty()) {
			addComponent(grid);
		} else {
			Label emptyListLabel = new Label("Brak naczep do wyświetlenia");
			addComponent(emptyListLabel);
			setComponentAlignment(emptyListLabel, Alignment.MIDDLE_CENTER);
		}
	}

	@Override
	protected void getRemoveItemsAction(ClickEvent click) {
		Set<Trailer> selectedTrailers = grid.getSelectedItems();
		int selectionSize = selectedTrailers.size();
		if (selectionSize > 0) {
			grid.getSelectedItems().forEach(o -> {
				trailerDAO.delete(o);
			});
			log.infof("Deleted %d trailers", selectionSize);
			TruckitoNotification.builder().setTitle("Usunięto " + selectionSize + " naczep").show();
			reloadPage();
		} else {
			TruckitoNotification.builder().setTitle("Zaznacz naczepy, które chcesz usunąć").show();
		}
	}

	@Override
	protected AddForm<Trailer> forgeAddForm() {
		AddForm<Trailer> form = new TrailerAddForm();
		return null;
	}

	@Override
	protected EditForm<Trailer> forgeEditForm() {
		return null;
	}

	@Override
	protected void saveObject(Trailer object) {
		if (object.getId() != null) {
			trailerDAO.update(object);
		} else {
			trailerDAO.create(object);
		}
	}

	@Override
	protected DataProvider<Trailer, Void> forgeDataProvider() {
		return DataProvider.fromCallbacks(query -> trailerDAO.findAll().stream(), query -> trailerDAO.count());
	}
}
