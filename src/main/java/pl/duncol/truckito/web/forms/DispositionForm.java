package pl.duncol.truckito.web.forms;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.dao.DispositionDAO;
import pl.duncol.truckito.dao.DriverDAO;
import pl.duncol.truckito.dao.TrailerDAO;
import pl.duncol.truckito.dao.TruckDAO;
import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.document.CMRDocument;
import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.domain.vehicle.Truck;
import pl.duncol.truckito.valueobjects.id.Dossier;
import pl.duncol.truckito.web.components.TruckitoGrid;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@ApplicationScoped
public class DispositionForm extends TruckitoSubmitForm<Disposition> {
	private static final long serialVersionUID = 6763667793620881681L;

	private static Logger log = Logger.getLogger(DispositionForm.class);

	private HorizontalLayout contentArea;
	
	private TextField dossierNumberField;
	private ComboBox<Driver> chooseDriver;
	private ComboBox<Trailer> chooseTrailer;
	private ComboBox<Truck> chooseTruck;
	private TextArea commentField;
	private CheckBox isActive;
	private Binder<Disposition> dispositionBinder;
	
	private TruckitoGrid<CMRDocument> cmrGrid;
	
	private Button addCMRWindowButton;
	
	private Disposition dispositionInstance;

	@Inject
	private AddingContainerProvider<CMRDocument> cmrAdder;

	@Inject
	private EditingContainerProvider<CMRDocument> cmrEditor;
	
	@Inject
	private DispositionDAO dispositionDAO;
	
	@Inject
	private DriverDAO driverDAO;

	@Inject
	private TruckDAO truckDAO;

	@Inject
	private TrailerDAO trailerDAO;
	
	@Inject
	private CMRForm CMRForm;

	private Set<CMRDocument> cmrDocuments = new HashSet<>();

	@PostConstruct
	private void initializeElements() {
		prepareMainLayout();
		addStyleName("p-0");
		prepareHeader("Dodawanie dyspozycji");
		prepareContentArea();
	}
	
	private void prepareContentArea() {
		contentArea = new HorizontalLayout();
		contentArea.setSizeFull();
		VerticalLayout mainInputLayout = forgeMainInputLayout();
		VerticalLayout cmrLayout = forgeCMRLayout();

		contentArea.addComponent(mainInputLayout);
		contentArea.setExpandRatio(mainInputLayout, 0.4f);
		contentArea.addComponent(cmrLayout);
		contentArea.setExpandRatio(cmrLayout, 0.6f);
		
		mainLayout.addComponent(contentArea);
	}

	private VerticalLayout forgeMainInputLayout() {
		VerticalLayout mainInputLayout = new VerticalLayout();
		dispositionBinder = new Binder<>();
		
		dossierNumberField = new TextField();
		dossierNumberField.setPlaceholder("Numer Dossier...");
		dispositionBinder.forField(dossierNumberField)
							.withConverter(Dossier::of, Dossier::getNumber)
							.withNullRepresentation(Dossier.of(""))
							.bind(Disposition::getDossierNumber, Disposition::setDossierNumber);
		
		chooseDriver = new ComboBox<>();
		chooseDriver.setPlaceholder("Kierowca...");
		chooseDriver.setItems(driverDAO.getAllUnoccupiedDrivers());
		dispositionBinder.forField(chooseDriver)
							.bind(Disposition::getLastDriver, Disposition::addDriver);
		
		chooseTrailer = new ComboBox<>();
		chooseTrailer.setPlaceholder("Naczepa...");
		chooseTrailer.setItems(trailerDAO.getAllUnoccupiedTrailers());
		dispositionBinder.forField(chooseTrailer)
							.bind(Disposition::getLastTrailer, Disposition::addTrailer);
		
		chooseTruck = new ComboBox<>();
		chooseTruck.setPlaceholder("Ciężarówka...");
		chooseTruck.setItems(truckDAO.getAllUnoccupiedTrucks());
		dispositionBinder.forField(chooseTruck)
							.bind(Disposition::getLastTruck, Disposition::addTruck);

		commentField = new TextArea();
		commentField.setPlaceholder("Komentarz...");
		dispositionBinder.forField(commentField)
//							.withConverter(Comment::of, Comment::getText)
//							.withConverter(Disposition::getConcatenatedComments, Comment::of)
							.bind(Disposition::getConcatenatedComments, Disposition::addComment);
		
		isActive = new CheckBox();
		isActive.setCaption("Czy aktywna");
		dispositionBinder.forField(isActive)
							.bind(Disposition::isActive, Disposition:: setActive);
		
		submitButton = forgeSubmitButton("Zapisz dyspozycję");
		
		mainInputLayout.addComponent(dossierNumberField);
		dossierNumberField.setSizeFull();
		mainInputLayout.addComponent(chooseDriver);
		chooseDriver.setSizeFull();
		mainInputLayout.addComponent(chooseTrailer);
		chooseTrailer.setSizeFull();
		mainInputLayout.addComponent(chooseTruck);
		chooseTruck.setSizeFull();
		mainInputLayout.addComponent(commentField);
		commentField.setSizeFull();
		mainInputLayout.addComponent(submitButton);
		
		return mainInputLayout;
	}
	
	private TruckitoGrid<CMRDocument> forgeCmrGrid() {
		TruckitoGrid<CMRDocument> cmrGrid = new TruckitoGrid<>();
		cmrGrid.setSizeFull();
		cmrGrid.setSelectionMode(SelectionMode.MULTI);
		cmrGrid.addColumn(CMRDocument::getDescription).setCaption("Opis");

		cmrGrid.addItemClickListener(this::cmrClickHandler);
		
		return cmrGrid;
	}
	
	private VerticalLayout forgeCMRLayout() {
		VerticalLayout cmrLayout = new VerticalLayout();
		cmrLayout.addStyleName("p-0");
		Label title = new Label("Dokumenty CMR");
		cmrGrid = forgeCmrGrid();
		
		addCMRWindowButton = forgeCMRWindowButton("Dodaj CMR");
		addCMRWindowButton.addClickListener(this::addCmrButtonClick);
		
		cmrLayout.addComponent(title);
		cmrLayout.addComponent(cmrGrid);
		cmrLayout.addComponent(addCMRWindowButton);
		
		return cmrLayout;
	}

	private Button forgeCMRWindowButton(String caption) {
		Button button = new Button(caption);
		button.setSizeFull();
		return button;
	}
	
	private void addCmrButtonClick(ClickEvent click) {
		cmrAdder.setCallback(this::afterCmrAdding);
		cmrAdder.buildContainer();
	}
	
	private void cmrClickHandler(ItemClick<CMRDocument> itemClick) {
		cmrEditor.setItem(itemClick.getItem());
		cmrEditor.setCallback(this::afterCmrEdit);
		cmrEditor.buildContainer();
	}

	private void afterCmrAdding(CMRDocument company) {
		cmrAdder.close();
		cmrGrid.addItem(company);
	}
	
	private void afterCmrEdit(CMRDocument company) {
		cmrEditor.close();
		cmrGrid.updateItem(company);
	}
	
	public void setObjectForEdit(Disposition disposition) {
		header.setValue("Edytuj firmę");
		dispositionBinder.setBean(disposition);
		
//		companyInstance = company;
//		fullNameTf.setValue(company.getFullName());
//		shortNameTf.setValue(company.getShortName());
//		if (company.getAddress() != null) {
//			countryComboBox.setValue(company.getAddress().getCountry());
//			cityTf.setValue(company.getAddress().getCity());
//			addressDetailsTf.setValue(company.getAddress().getAddressDetails());
//			postalCodeTf.setValue(company.getAddress().getPostalCode());			
//		}
	}
	
	@Override
	protected void preSubmit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void completeObject() throws ValidationException {
		dispositionInstance = new Disposition();
		dispositionBinder.writeBean(dispositionInstance);

//		dispositionInstance.setDossierNumber(Dossier.of(dossierNumberField.getValue()));
		dispositionInstance.setCmrDocuments(cmrDocuments);
		
//		log.info("DRIVER ID: " + chooseTruckDriver.getValue().getId());
//		dispositionInstance.addDriver(chooseDriver.getValue());
//		log.info("TRUCK ID: " + chooseTruck.getValue().getId());
//		dispositionInstance.addTruck(chooseTruck.getValue());
//		log.info("TRAILER ID: " + chooseTrailer.getValue().getId());
//		dispositionInstance.addTrailer(chooseTrailer.getValue());
//		
//		dispositionInstance.addComment(Comment.of(commentField.getValue()));
	}
	
	@Override
	public void clearFields() {
		dispositionBinder.getFields().forEach(HasValue::clear);
		clearCmrGrid();
//		dossierNumberField.clear();
//		chooseDriver.clear();
//		chooseTrailer.clear();
//		chooseTruck.clear();
//		commentField.clear();
	}

	private void clearCmrGrid() {
		cmrDocuments.clear();
		cmrGrid.setItems(cmrDocuments);
	}

	@Override
	protected Disposition saveObject() {
		log.info("Saving disposition...");
//		if (dispositionInstance.getId() != null) {
			dispositionInstance.setActive(true);
			dispositionInstance = dispositionDAO.update(dispositionInstance);
			
			TruckitoNotification.builder().setTitle("Dyspozycja zapisana").show();
//		} else {
//			dispositionInstance.setActive(true);
//			dispositionDAO.create(dispositionInstance);
//			TruckitoUI.notification(new Notification("Dyspozycja dodana"), 2000);
//		}
		log.info("Disposition saved!");
		return dispositionInstance;
	}
}
