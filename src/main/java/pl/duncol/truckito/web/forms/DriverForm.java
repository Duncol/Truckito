package pl.duncol.truckito.web.forms;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.dao.DriverDAO;
import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.domain.user.employee.driver.DriverStatus;
import pl.duncol.truckito.web.TruckitoUI;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@ApplicationScoped
public class DriverForm extends TruckitoSubmitForm<Driver> {
	private static final long serialVersionUID = -467919942055148873L;

	private static Logger log = Logger.getLogger(DriverForm.class);

	private TextField firstNameField;
	private TextField lastNameField;
	private TextField phoneNumberField;
	private TextField idCardNumberField;
	private CheckBox isADR;
	private Binder<Driver> driverBinder;

	private Driver driverInstance;

	@Inject
	private DriverDAO driverDAO;

	@PostConstruct
	private void initializeElements() {
		prepareMainLayout();
		addStyleName("p-0");

		prepareHeader("Dodawanie kierowcy");
		VerticalLayout mainInputsLayout = forgeMainInputsLayout();

		mainLayout.addComponent(mainInputsLayout);
	}

	private VerticalLayout forgeMainInputsLayout() {
		VerticalLayout mainInputLayout = new VerticalLayout();
		driverBinder = new Binder<>();
		
		firstNameField = new TextField("Imię");
		driverBinder.forField(firstNameField)
						.bind(Driver::getFirstName, Driver::setFirstName);
		
		lastNameField = new TextField("Naziwsko");
		driverBinder.forField(lastNameField)
						.bind(Driver::getLastName, Driver::setLastName);
		
		phoneNumberField = new TextField("Numer telefonu");
		driverBinder.forField(phoneNumberField)
						.bind(Driver::getPhoneNumber, Driver::setPhoneNumber);
		
		idCardNumberField = new TextField("Numer dokumentu tożsamości");
		driverBinder.forField(idCardNumberField).bind(Driver::getIdCardNumber, Driver::setIdCardNumber);
		
		isADR = new CheckBox("Czy ADR?");
		driverBinder.forField(isADR)
						.bind(Driver::isADR, Driver::setADR);

		submitButton = forgeSubmitButton("Zapisz kierowcę");

		mainInputLayout.addComponent(firstNameField);
		mainInputLayout.addComponent(lastNameField);
		mainInputLayout.addComponent(phoneNumberField);
		mainInputLayout.addComponent(idCardNumberField);
		mainInputLayout.addComponent(isADR);

		mainInputLayout.addComponent(submitButton);

		return mainInputLayout;
	}

	public void setObjectForEdit(Driver driver) {
		if (driver != null) {
			header.setValue("Edytuj kierowcę");
			driverInstance = driver;

			firstNameField.setValue(driver.getFirstName());
			lastNameField.setValue(driver.getLastName());
			isADR.setValue(driver.isADR());
			phoneNumberField.setValue(driver.getPhoneNumber());
			idCardNumberField.setValue(driver.getIdCardNumber());
		}
	}

	@Override
	protected void completeObject() throws ValidationException {
		driverInstance = new Driver();
		driverBinder.writeBean(driverInstance);

		driverInstance.setStatus(DriverStatus.READY);
	}

	@Override
	protected void preSubmit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearFields() {
		driverBinder.getFields().forEach(HasValue::clear);
	}

	@Override
	protected Driver saveObject() {
		if (driverBinder.isValid()) {
			log.info("Saving driver...");
			if (driverInstance.getId() != null) {
				driverInstance = driverDAO.update(driverInstance);
			} else {
				driverDAO.create(driverInstance);
			}
			log.info("Driver saved!");
			TruckitoNotification.builder().setTitle("Kierowca dodany").show();
			return driverInstance;
		} else {
			TruckitoNotification.builder().setTitle("Uzupełnij odpowiednio pola!").show();
			return null;
		}
	}
}