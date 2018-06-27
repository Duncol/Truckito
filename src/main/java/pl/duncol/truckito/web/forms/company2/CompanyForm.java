package pl.duncol.truckito.web.forms.company2;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.company.CompanyFlatProxy;
import pl.duncol.truckito.valueobjects.address.Country;
import pl.duncol.truckito.valueobjects.address.PostalCode;
import pl.duncol.truckito.web.forms.TruckitoForm;

public abstract class CompanyForm extends TruckitoForm<Company> {
	private static final long serialVersionUID = -6462814863806838251L;

	private static Logger log = Logger.getLogger(CompanyForm.class);

	protected VerticalLayout inputsPanel;

	protected HorizontalLayout firstRow;
	protected HorizontalLayout secondRow;
	protected HorizontalLayout thirdRow;

	protected TextField fullNameTf;
	protected TextField shortNameTf;
	protected ComboBox<Country> countryComboBox;
	protected TextField cityTf;
	protected TextField addressDetailsTf;
	protected TextField postalCodeTf;
	protected Binder<CompanyFlatProxy> companyBinder;

	protected CompanyFlatProxy companyProxy;

	protected Company companyInstance;

	public CompanyForm() {
		initializeElements();
	}

	protected void initializeElements() {
		prepareMainLayout();
		prepareHeader("Dodawanie firmy");

		prepareInputs();

		mainLayout.addComponent(inputsPanel);
	}

	protected void prepareInputs() {
		inputsPanel = new VerticalLayout();
		companyBinder = new Binder<>();

		buildFirstRow();
		buildSecondRow();
		buildThirdRow();

		setWidth(70f, Unit.PERCENTAGE);
		inputsPanel.addComponent(firstRow);
		inputsPanel.addComponent(secondRow);
		inputsPanel.addComponent(thirdRow);
	}

	protected void buildFirstRow() {
		prepareFullNameField();
		prepareShortNameField();

		firstRow = new HorizontalLayout();
		firstRow.setSizeFull();
		firstRow.addComponent(fullNameTf);
		firstRow.setExpandRatio(fullNameTf, 0.7f);
		firstRow.addComponent(shortNameTf);
		firstRow.setExpandRatio(shortNameTf, 0.3f);
	}

	protected void buildSecondRow() {
		prepareAddressDetialsField();

		secondRow = new HorizontalLayout();
		secondRow.setSizeFull();
		secondRow.addComponent(addressDetailsTf);
		secondRow.setExpandRatio(addressDetailsTf, 1.0f);
	}

	protected void buildThirdRow() {
		prepareCountryField();
		prepareCityField();
		preparePostalCodeField();

		thirdRow = new HorizontalLayout();
		thirdRow.setSizeFull();
		thirdRow.addComponent(cityTf);
		thirdRow.setExpandRatio(cityTf, 0.4f);
		thirdRow.addComponent(countryComboBox);
		thirdRow.setExpandRatio(countryComboBox, 0.4f);
		thirdRow.addComponent(postalCodeTf);
		thirdRow.setExpandRatio(postalCodeTf, 0.2f);
	}

	protected void preparePostalCodeField() {
		postalCodeTf = new TextField("Kod pocztowy");
		postalCodeTf.setSizeFull();
		companyBinder.forField(postalCodeTf)
				.withStatusLabel(new Label())
				.withValidator(PostalCode::validate, "Wprowadz poprawny kod pocztowy")
				.withConverter(PostalCode::of, PostalCode::getValue, "Wprowadz poprawny kod pocztowy")
				.withNullRepresentation(new PostalCode(""))
				.bind(CompanyFlatProxy::getAddressPostalCode, CompanyFlatProxy::setAddressPostalCode);
	}

	protected void prepareCityField() {
		cityTf = new TextField("Miejscowość");
		cityTf.setSizeFull();
		companyBinder.forField(cityTf)
						.bind(CompanyFlatProxy::getAddressCity, CompanyFlatProxy::setAddressCity);
	}

	protected void prepareCountryField() {
		countryComboBox = new ComboBox<>("Kraj");
		countryComboBox.setSizeFull();
		countryComboBox.setItems(Country.values());
		companyBinder.forField(countryComboBox)
						.bind(CompanyFlatProxy::getAddressCountry, CompanyFlatProxy::setAddressCountry);
	}

	protected void prepareAddressDetialsField() {
		addressDetailsTf = new TextField("Szczegóły adresowe");
		addressDetailsTf.setSizeFull();
		companyBinder.forField(addressDetailsTf)
						.bind(CompanyFlatProxy::getAddressDetails, CompanyFlatProxy::setAddressDetails);
	}

	protected void prepareShortNameField() {
		shortNameTf = new TextField("Nazwa skrócona");
		shortNameTf.setSizeFull();
		companyBinder.forField(shortNameTf)
						.bind(CompanyFlatProxy::getShortName, CompanyFlatProxy::setShortName);
	}

	protected void prepareFullNameField() {
		fullNameTf = new TextField("Pełna nazwa firmy");
		fullNameTf.setSizeFull();
		companyBinder.forField(fullNameTf)
						.bind(CompanyFlatProxy::getFullName, CompanyFlatProxy::setFullName);
	}
}
