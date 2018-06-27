package pl.duncol.truckito.web.forms;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.dao.TruckDAO;
import pl.duncol.truckito.domain.vehicle.Truck;
import pl.duncol.truckito.utils.ImageReceiver;
import pl.duncol.truckito.valueobjects.id.LicensePlate;
import pl.duncol.truckito.web.TruckitoUI;
import pl.duncol.truckito.web.misc.TruckitoNotification;

@ApplicationScoped
public class TruckForm extends TruckitoSubmitForm<Truck> {
	private static final long serialVersionUID = 3913980987418365237L;

	private static Logger log = Logger.getLogger(TruckForm.class);
	
	private TextField licensePlateField;
	private TextField modelField;
	private Upload imageUpload;
	private Binder<Truck> truckBinder;
	
	private Truck truckInstance;
	
	@Inject
	private TruckDAO truckDAO;

	@PostConstruct
	private void initializeElements() {
		prepareMainLayout();
		addStyleName("p-0");
		
		prepareHeader("Dodawanie ciężarówki");
		VerticalLayout mainInputsLayout = forgeInputsPanel();
		
		mainLayout.addComponent(mainInputsLayout);
	}

	private VerticalLayout forgeInputsPanel() {
		VerticalLayout mainInputsLayout = new VerticalLayout();
		truckBinder = new Binder<>();
		
		licensePlateField = new TextField("Numer rejestracyjny");
		truckBinder.forField(licensePlateField)
						.withConverter(LicensePlate::of, LicensePlate::getNumber)
						.withNullRepresentation(LicensePlate.of(""))
						.bind(Truck::getLicensePlate, Truck::setLicensePlate);
		
		modelField = new TextField("Marka/Model");
		truckBinder.forField(modelField)
						.bind(Truck::getModel, Truck::setModel);
		
		imageUpload = new Upload("Zdjęcie ciężarówki", new ImageReceiver("D:\\tmp\\uploads\\"));

		submitButton = forgeSubmitButton("Zapisz ciężarówkę");
        
		mainInputsLayout.addComponent(licensePlateField);
		mainInputsLayout.addComponent(modelField);
		mainInputsLayout.addComponent(imageUpload);
		mainInputsLayout.addComponent(submitButton);
		
		return mainInputsLayout;
	}
	
	public void setObjectForEdit(Truck truck) {
		if (truck != null) {
			header.setValue("Edytuj ciężarówkę");
			truckBinder.setBean(truck);
		}
	}
	
	@Override
	protected void preSubmit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void completeObject() throws ValidationException {
		truckInstance = new Truck();
		truckBinder.writeBean(truckInstance);
	}
	
	@Override
	public void clearFields() {
		licensePlateField.clear();
		modelField.clear();
	}

	@Override
	protected Truck saveObject() {
		if (truckBinder.isValid()) {
			log.info("Saving truck...");
			if (truckInstance.getId() != null) {
				truckInstance = truckDAO.update(truckInstance); 
			} else {
				truckDAO.create(truckInstance);				
			}
			log.info("Truck saved!");
			TruckitoNotification.builder().setTitle("Ciężarówka dodana").show();
			return truckInstance;
		} else {
			TruckitoNotification.builder().setTitle("Uzupełnij odpowiednio pola!").show();
			return null;
		}
	}
}