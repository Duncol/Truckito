package pl.duncol.truckito.web.displays;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import pl.duncol.truckito.dao.CompanyDAO;
import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.providers.ObjectContainerProvider;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@ApplicationScoped
public class CompanyDisplay extends TruckitoDisplay<Company> {
	private static final long serialVersionUID = 1624090844253266187L;

	private Logger log = Logger.getLogger(CompanyDisplay.class);

	@Inject
	private CompanyDAO companyDAO;

	@Inject
	private ObjectContainerProvider<Company> companyContainerProvider;

	// private List<Company> companies;
	private DataProvider<Company, Void> dataProvider;

	private HorizontalLayout actionTopRow;
	private TruckitoGrid<Company> grid;

	@PostConstruct
	private void postConstruct() {
		prepareActionTopRow();
		prepareGrid();
	}

	private void prepareActionTopRow() {
		actionTopRow = new HorizontalLayout();
		actionTopRow.setMargin(false);

		Button companyAddButton = forgeAddItemButton();
		Button deselectAll = forgeDeselectAllButton();
		Button removeSelectedCompaniesButton = forgeRemoveItemsButton();

		actionTopRow.addComponent(companyAddButton);
		actionTopRow.addComponent(removeSelectedCompaniesButton);
		actionTopRow.addComponent(deselectAll);
	}

	@Override
	protected void prepareGrid() {
		super.prepareGrid();
		grid.addColumn(Company::getFullName).setCaption("Nazwa pełna");
		grid.addColumn(Company::getShortName).setCaption("Nazwa skrócona");
		// grid.addColumn(Company::).setCaption("");

		grid.addItemClickListener(e -> {
			companyContainerProvider.getEditor().setObject(e.getItem());
		});
	}

	@Override
	protected DataProvider<Company, Void> forgeDataProvider() {
		return DataProvider.fromCallbacks(
			query -> {
				int offset = query.getOffset();
				int limit = query.getLimit();

				List<Company> companies = companyDAO.findAll(offset, limit);

				return companies.stream();
		}, query -> companyDAO.count());
	}



	@Override
	protected void getRemoveItemsAction(ClickEvent click) {
		Set<Company> selectedCompanies = grid.getSelectedItems();
		int selectionSize = selectedCompanies.size();
		if (selectionSize > 0) {
			selectedCompanies.forEach(o -> {
				companyDAO.delete(o);
			});
			log.infof("Deleted %d companies", selectionSize);
			TruckitoNotification.builder().setTitle("Usunięto " + selectionSize + " firm").show();
			dataProvider.refreshAll();
		} else {
			TruckitoNotification.builder().setTitle("Zaznacz firmy, które chcesz usunąć").show();
		}
	}

	@Override
	public void attach() {
		super.attach();
		grid.deselectAll();

		addComponent(actionTopRow);
		if (companyDAO.count() > 0) {
			addComponent(grid);
		} else {
			Label emptyListLabel = new Label("Brak firm do wyświetlenia");
			addComponent(emptyListLabel);
			setComponentAlignment(emptyListLabel, Alignment.MIDDLE_CENTER);
		}
	}
}
