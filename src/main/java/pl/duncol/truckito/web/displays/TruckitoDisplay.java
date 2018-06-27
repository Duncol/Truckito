package pl.duncol.truckito.web.displays;

import java.io.Serializable;
import java.util.List;

import com.vaadin.data.ValidationException;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.utils.AsyncThread;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.TruckitoWindow;
import pl.duncol.truckito.web.forms.interfaces.AddForm;
import pl.duncol.truckito.web.forms.interfaces.EditForm;

public abstract class TruckitoDisplay<O extends BaseEntity<? extends Serializable>> extends VerticalLayout {
	private static final long serialVersionUID = -8469168138603240871L;

	protected HorizontalLayout actionTopRow;
	protected TruckitoGrid<O> grid;
	protected DataProvider<O, Void> dataProvider;
	protected List<O> elements;

	private TruckitoWindow window;

	public TruckitoDisplay() {
		setSizeFull();
	}

	protected void prepareActionTopRow() {
		actionTopRow = new HorizontalLayout();
		actionTopRow.setMargin(false);

		actionTopRow.addComponent(forgeAddItemButton());
		actionTopRow.addComponent(forgeRemoveItemsButton());
	}

	protected void prepareGrid() {
		grid = new TruckitoGrid<>();
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);

		dataProvider = forgeDataProvider();
		grid.setDataProvider(dataProvider);

		grid.addItemClickListener(this::editItemButtonAction);
	}

	protected abstract DataProvider<O, Void> forgeDataProvider();

	protected Button forgeAddItemButton() {
		Button addItemButton = new Button("Dodaj...");
		addItemButton.addClickListener(this::addItemButtonAction);
		return addItemButton;
	}

	protected Button forgeDeselectAllButton() {
		Button deselectAll = new Button("Odznacz wszystko");
		deselectAll.addClickListener(c -> grid.deselectAll());
		return deselectAll;
	}

	protected Button forgeRemoveItemsButton() {
		Button removeSelectedItemsButton = new Button("Usuń zaznaczone");
		removeSelectedItemsButton.addClickListener(this::getRemoveItemsAction);
		return removeSelectedItemsButton;
	}

	protected void addItemButtonAction(ClickEvent click) {
		AddForm<O> form = forgeAddForm();
		form.setButtonAction(e -> {
			try {
				O object = form.extractObject();
				UI current = UI.getCurrent();
				current.access(new AsyncThread(() -> {
					UI.setCurrent(current);
					saveObject(object);
					window.close();
					current.push();
				}));
			} catch (ValidationException ex) {
				ex.printStackTrace();
			} finally {
				form.enableSubmitButton();
			}
		});

		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	protected abstract void getRemoveItemsAction(ClickEvent click);

	protected abstract AddForm<O> forgeAddForm();

	private void editItemButtonAction(ItemClick<O> itemClick) {
		EditForm<O> form = forgeEditForm();
		form.setObject(itemClick.getItem());
		form.setButtonAction(e -> {
			try {
				O object = form.extractObject();
				UI current = UI.getCurrent();
				current.access(new AsyncThread(() -> {
					UI.setCurrent(current);
					saveObject(object);
					window.close();
					current.push();
				}));
			} catch (ValidationException ex) {
				ex.printStackTrace();
			} finally {
				form.enableSubmitButton();
			}
		});

		window = new TruckitoWindow();
		window.setContent(form);
		UI.getCurrent().addWindow(window);
	}

	protected abstract EditForm<O> forgeEditForm();

	protected abstract void saveObject(O object);

	@Override
	public void attach() {
//		removeAllComponents();
		super.attach();
	}

	@Override
	public void detach() {
		removeAllComponents();
		super.detach();
	}
}
