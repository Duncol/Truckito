package pl.duncol.truckito.web.forms.trailer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.dao.TrailerDAO;
import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.utils.ImageReceiver;
import pl.duncol.truckito.valueobjects.id.LicensePlate;
import pl.duncol.truckito.web.forms.TruckitoSubmitForm;
import pl.duncol.truckito.web.misc.TruckitoNotification;

public class TrailerForm extends TruckitoSubmitForm<Trailer> {
	private static final long serialVersionUID = -7867284375694360806L;

	private static Logger log = Logger.getLogger(TrailerForm.class);
	
	private TextField licensePlateField;
	private TextField modelField;
	private Upload imageUpload;
	private Binder<Trailer> trailerBinder;

	private Trailer trailerInstance;
	
	public TrailerForm() {
		initializeElements();
	}

	private void initializeElements() {
		prepareMainLayout();
		addStyleName("p-0");
		
		prepareHeader("Dodawanie naczepy");
		VerticalLayout mainInputsLayout = forgeInputsPanel();
		
		mainLayout.addComponent(mainInputsLayout);
	}
	
	private VerticalLayout forgeInputsPanel() {
		VerticalLayout mainInputsLayout = new VerticalLayout();
		trailerBinder = new Binder<>();
		
		licensePlateField = new TextField("Numer rejestracyjny");
		trailerBinder.forField(licensePlateField)
						.withConverter(LicensePlate::of, LicensePlate::getNumber)
						.withNullRepresentation(LicensePlate.of(""))
						.bind(Trailer::getLicensePlate, Trailer::setLicensePlate);
		
		modelField = new TextField("Marka/Model");
		trailerBinder.forField(modelField)
						.bind(Trailer::getModel, Trailer::setModel);
		
		imageUpload = new Upload("Zdjęcie naczepy", new ImageReceiver("D:\\tmp\\uploads\\")); // TODO FIXME XD
		
		submitButton = forgeSubmitButton("Zapisz naczepę");
        
		mainInputsLayout.addComponent(licensePlateField);
        mainInputsLayout.addComponent(modelField);
        mainInputsLayout.addComponent(imageUpload);
        mainInputsLayout.addComponent(submitButton);
        
        return mainInputsLayout;
	}
	
	public void setObjectForEdit(Trailer trailer) {
		if (trailer != null) {
			header.setValue("Edytuj naczepę");
			trailerBinder.setBean(trailer);
		}
	}
	
	@Override
	protected void preSubmit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void completeObject() throws ValidationException {
    	trailerInstance = new Trailer();
    	trailerBinder.writeBean(trailerInstance);
	}
	
	@Override
	public void clearFields() {
		trailerBinder.getFields().forEach(HasValue::clear);
//		imageUpload. // TODO
	}
	
	@Override
	protected Trailer saveObject() {
		if (trailerBinder.isValid()) {
			log.info("Saving trailer...");
			if (trailerInstance.getId() != null) {
				trailerInstance = trailerDAO.update(trailerInstance);
			}
			trailerDAO.create(trailerInstance);
			log.info("Trailer saved!");
			TruckitoNotification.builder().setTitle("Naczepa dodana").show();
			return trailerInstance;
		} else {
			TruckitoNotification.builder().setTitle("Uzupełnij odpowiednio pola!").show();
			return null;
		}
	}
}