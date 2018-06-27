package pl.duncol.truckito.web.forms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import pl.duncol.truckito.dao.CMR_DAO;
import pl.duncol.truckito.dao.CompanyDAO;
import pl.duncol.truckito.domain.cargo.Cargo;
import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.document.CMRDocument;
import pl.duncol.truckito.web.components.TruckitoComboBox;
import pl.duncol.truckito.web.components.providers.interfaces.AddingContainerProvider;
import pl.duncol.truckito.web.components.providers.interfaces.EditingContainerProvider;
import pl.duncol.truckito.web.displays.details.CompanyDetailsPanel;
import pl.duncol.truckito.web.misc.TruckitoNotification;

// TODO
@ApplicationScoped
public class CMRForm extends TruckitoSubmitForm<CMRDocument> implements View {
	private static final long serialVersionUID = -2872653408869893313L;

	private Logger log = Logger.getLogger(CMRForm.class);
	
	private VerticalLayout mainConent;
	
	private HorizontalLayout topContent;
	
	private TruckitoComboBox<Company> chooseSenderInput;
	private TruckitoComboBox<Company> chooseConsigneeInput;
	private TruckitoComboBox<Company> chooseCarrierInput;
	
	private Accordion cargoAccordion;
	
	private Button addCargoFormButton;
	
	private List<CargoForm> cargoForms;
	
	private Binder<CMRDocument> cmrBinder;
	
	@Inject
	private AddingContainerProvider<Company> companyAdder;
	
	@Inject
	private EditingContainerProvider<Company> companyEditor;
	
	@Inject
	private CompanyDAO companyDAO;
	
	@Inject
	private CMR_DAO cmrDao;
	
	private Set<Company> companySet;
	
	private CMRDocument cmrInstance;
	
	@PostConstruct
	private void initializeElements() {
		companySet = new HashSet<>();
		
		prepareMainLayout();
		prepareHeader("Dodaj CMR");
		prepareMainPanel();
	}

	private void prepareMainPanel() {
		mainConent = new VerticalLayout();
		mainConent.setSizeFull();
		mainConent.addStyleName("pt-0");
		
		cmrBinder = new Binder<>();
		
		HorizontalLayout topPanel = forgeTopPanel();
		VerticalLayout bottomPanel = forgeBottomPanel();
		submitButton = forgeSubmitButton("Zapisz CMR");
		
		mainConent.addComponent(topPanel);
		mainConent.addComponent(bottomPanel);
		mainConent.addComponent(submitButton);
		
		mainLayout.addComponent(mainConent);
		mainLayout.setSizeFull();
	}

	private HorizontalLayout forgeTopPanel() {
		HorizontalLayout topPanel = new HorizontalLayout();
		topPanel.setSizeFull();
		topPanel.addStyleName("pt-0");
		topPanel.addStyleName("pb-0");
		
		VerticalLayout chooseSenderLayout = forgeChooseSender();
		VerticalLayout chooseConsigneeLayout = forgeChooseConsignee();
		VerticalLayout chooseCarrierLayout = forgeChooseCarrier();
		
		topPanel.addComponent(chooseSenderLayout);
		topPanel.addComponent(chooseConsigneeLayout);
		topPanel.addComponent(chooseCarrierLayout);

		return topPanel;
	}
	
	private VerticalLayout forgeChooseSender() {
		VerticalLayout chooseSender = createFullSizeColumn();
		Label senderPanelHeader = new Label("Nadawca");
		
		chooseSenderInput = new TruckitoComboBox<>();
		chooseSenderInput.setSizeFull();
		cmrBinder.forField(chooseSenderInput)
					.bind(CMRDocument::getSender, CMRDocument:: setSender);
		fancinizeInput(senderPanelHeader, chooseSenderInput);
		
		Button addSenderBtn = forgeAddCompanyButton("Dodaj Nadawcę", chooseSenderInput);
		Button editSenderBtn = forgeEditCompanyButton("edytuj", chooseSenderInput);
		
		CompanyDetailsPanel senderDetails = new CompanyDetailsPanel();
		
		chooseSenderInput.addSelectionListener(getCompanySelectionAction(editSenderBtn, senderDetails));
		
		chooseSender.addComponent(senderPanelHeader);
		chooseSender.addComponent(createSelectEditRow(chooseSenderInput, editSenderBtn));
		chooseSender.addComponent(senderDetails);
		chooseSender.addComponent(addSenderBtn);
		
		return chooseSender;
	}
	
	private VerticalLayout forgeChooseConsignee() {
		VerticalLayout chooseConsignee = createFullSizeColumn();
		Label chooseConsigneeHeader = new Label("Odbiorca");
		chooseConsigneeInput = new TruckitoComboBox<>();
		chooseConsigneeInput.setSizeFull();
		cmrBinder.forField(chooseConsigneeInput)
					.bind(CMRDocument::getConsignee, CMRDocument:: setConsignee);
		fancinizeInput(chooseConsigneeHeader, chooseConsigneeInput);
		
		Button addConsigneeBtn = forgeAddCompanyButton("Dodaj Odbiorcę", chooseConsigneeInput);
		Button editConsigneeBtn = forgeEditCompanyButton("edytuj", chooseConsigneeInput);
		
		CompanyDetailsPanel consigneeDetails = new CompanyDetailsPanel();
		
		chooseConsigneeInput.addSelectionListener(getCompanySelectionAction(editConsigneeBtn, consigneeDetails));
		
		chooseConsignee.addComponent(chooseConsigneeHeader);
		chooseConsignee.addComponent((createSelectEditRow(chooseConsigneeInput, editConsigneeBtn)));
		chooseConsignee.addComponent(consigneeDetails);
		chooseConsignee.addComponent(addConsigneeBtn);
		
		return chooseConsignee;
	}
	
	private VerticalLayout forgeChooseCarrier() {
		VerticalLayout chooseCarrier = createFullSizeColumn();
		Label chooseCarrierHeader = new Label("Przewoźnik");
		
		chooseCarrierInput = new TruckitoComboBox<>();
		chooseCarrierInput.setSizeFull();
		cmrBinder.forField(chooseCarrierInput).bind(CMRDocument::getCarrier, CMRDocument:: setCarrier);
		fancinizeInput(chooseCarrierHeader, chooseCarrierInput);
		
		Button addCarrierBtn = forgeAddCompanyButton("Dodaj Przewoźnika", chooseCarrierInput);
		Button editCarrierBtn = forgeEditCompanyButton("edytuj", chooseCarrierInput);
		
		CompanyDetailsPanel carrierDetails = new CompanyDetailsPanel();

		chooseCarrierInput.addSelectionListener(getCompanySelectionAction(editCarrierBtn, carrierDetails));
		
		chooseCarrier.addComponent(chooseCarrierHeader);
		chooseCarrier.addComponent(createSelectEditRow(chooseCarrierInput, editCarrierBtn));
		chooseCarrier.addComponent(carrierDetails);
		chooseCarrier.addComponent(addCarrierBtn);
		
		return chooseCarrier;
	}
	
	private Button forgeAddCompanyButton(String caption, TruckitoComboBox<Company> companySelect) {
		Button addCompanyBtn = new Button(caption);
		addCompanyBtn.addStyleName(ValoTheme.BUTTON_LINK);
		addCompanyBtn.addStyleName("p-0");
		addCompanyBtn.addStyleName("no-borders");
		addCompanyBtn.addStyleName("no-shadow");
		addCompanyBtn.addClickListener(click -> {
			addCompanyAction(companySelect);
		});
		
		return addCompanyBtn;
	}
	
	private Button forgeEditCompanyButton(String caption, TruckitoComboBox<Company> companySelect) {
		Button editCompanyBtn = new Button(caption);
		editCompanyBtn.addStyleName(ValoTheme.BUTTON_LINK);
		editCompanyBtn.addStyleName("p-0");
		editCompanyBtn.setVisible(false);
		editCompanyBtn.addClickListener(click -> {
			companyEditAction(companySelect);
		});

		return editCompanyBtn;
	}
	
	private VerticalLayout forgeBottomPanel() {
		VerticalLayout bottomPanel = new VerticalLayout();
		bottomPanel.addStyleName("pt-0");
		
		cargoForms = new ArrayList<>();
		
		addCargoFormButton = new Button("Dodaj ładunek...");
		addCargoFormButton.addStyleName(ValoTheme.BUTTON_LINK);
		addCargoFormButton.addStyleName("p-0");
		addCargoFormButton.addClickListener(click -> {
			if (cargoAccordion == null) {
				cargoAccordion = new Accordion();
				bottomPanel.addComponent(cargoAccordion, 0);
			}
			CargoForm cargoForm = forgeCargoForm();
			cargoAccordion.addTab(cargoForm);
//			cargoAccordion.
		});
		
		bottomPanel.addComponent(addCargoFormButton);

		return bottomPanel;
	}
	
	public void setObjectForEdit(CMRDocument cmr) {
		if (cmr != null) {
			header.setValue("Edytuj CMR");
			cmrInstance = cmr;
			
			for (Cargo cargo : cmr.getCargoList()) {
				loadCargo(cargo);
			}
			
//			cargoDescriptionInput.setValue(cmr.getCargoDescription());
		}
//		cargoWeightInput.setValue(cmr.getCargo()); // TODO
	}
	
	private void loadCargo(Cargo cargo) {
		if (cargoAccordion == null) {
			cargoAccordion = new Accordion();
		}
		CargoForm cargoForm = forgeCargoForm(cargo);
		cargoAccordion.addTab(cargoForm);
	}
	
	private CargoForm forgeCargoForm() {
		CargoForm cargoForm = new CargoForm();
		return cargoForm;
	}
	
	private CargoForm forgeCargoForm(Cargo cargo) {
		CargoForm cargoForm = new CargoForm(cargo);
		return cargoForm;
	}
	
	private void addCompanyAction(TruckitoComboBox<Company> companySelect) {
		companyAdder.setCallback(item -> {
			afterCompanyAdding(item, companySelect);
		});
		companyAdder.buildContainer();
	}
	
	private void companyEditAction(TruckitoComboBox<Company> companySelect) {
		companyEditor.setItem(companySelect.getSelectedItem().get());
		companyEditor.setCallback(item -> {
			afterCompanyEdit(item, companySelect);
		});
		companyEditor.buildContainer();
	}

	private void afterCompanyAdding(Company company, TruckitoComboBox<Company> companySelect) {
		companyAdder.close();
		chooseSenderInput.addItem(company);
		chooseConsigneeInput.addItem(company);
		chooseCarrierInput.addItem(company);
		
		companySelect.setSelectedItem(company);
	}
	
	private void afterCompanyEdit(Company company, TruckitoComboBox<Company> companySelect) {
		companyEditor.close();
		chooseSenderInput.updateItem(company);
		chooseConsigneeInput.updateItem(company);
		chooseCarrierInput.updateItem(company);
		
		companySelect.setSelectedItem(company);;
	}
	
	public void loadCompanies() {
		companySet = companyDAO.findAll().stream().collect(Collectors.toSet());
		
		log.info("Found " + companySet.size() + " companies.");
		
		chooseSenderInput.setItems(companySet);
		chooseConsigneeInput.setItems(companySet);
		chooseCarrierInput.setItems(companySet);
	}
	
	private SingleSelectionListener<Company> getCompanySelectionAction(Button editButton, CompanyDetailsPanel detailsPanel) {
		return e -> {
			Company selectedCompany = e.getValue();
			detailsPanel.fillContent(selectedCompany);
			if (selectedCompany != null) {
				editButton.setVisible(true);
				editButton.addClickListener(e2 -> {
//					AddingWindowWrapper<Company, CompanyForm> w = new AddingWindowWrapper<>(selectedCompany, companyForm);
//					UI.getCurrent().addWindow(w);
				});
			} else {
				editButton.setVisible(false);
			}
		};
	}
	
	private HorizontalLayout createSelectEditRow(ComboBox<?> select, Button edit) {
		HorizontalLayout chooseEditRow = new HorizontalLayout();
		chooseEditRow.setSizeFull();
		chooseEditRow.addComponent(select);
		chooseEditRow.addComponent(edit);
		chooseEditRow.setExpandRatio(select, 0.9f);
		chooseEditRow.setExpandRatio(edit, 0.1f);
		return chooseEditRow;
	}

	@Override
	protected void preSubmit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void completeObject() throws ValidationException {
		cmrInstance = new CMRDocument();
		cmrInstance.setSender(chooseSenderInput.getValue());
		cmrInstance.setConsignee(chooseConsigneeInput.getValue());
		cmrInstance.setCarrier(chooseCarrierInput.getValue());
		
		for (CargoForm cargoForm : cargoForms) {
			cmrInstance.addCargo(cargoForm.extractCargo());
		}
	}
	
	@Override
	public void clearFields() {
		chooseSenderInput.clear();
		chooseConsigneeInput.clear();
		chooseCarrierInput.clear();
	}
	
	@Override
	protected CMRDocument saveObject() {
		log.info("Saving CMR document...");
		if (cmrInstance.getId() != null) {
			cmrInstance = cmrDao.update(cmrInstance);
			TruckitoNotification.builder().setTitle("Zapisano dokument CMR").show();
		} else {
			cmrDao.create(cmrInstance);
			TruckitoNotification.builder().setTitle("Dodano dokument CMR").show();
		}
		log.info("CMR document saved!");
		return cmrInstance;
	}
	
	@Override
	public void attach() {
		loadCompanies();
		super.attach();
	}
}