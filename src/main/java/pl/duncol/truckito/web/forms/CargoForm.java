package pl.duncol.truckito.web.forms;

import org.jboss.logging.Logger;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.domain.cargo.Cargo;
import pl.duncol.truckito.domain.cargo.CargoFlatProxy;
import pl.duncol.truckito.valueobjects.cargo.PackageType;
import pl.duncol.truckito.valueobjects.cargo.units.DimensionUnit;
import pl.duncol.truckito.valueobjects.cargo.units.VolumeUnit;
import pl.duncol.truckito.valueobjects.cargo.units.WeightUnit;

public class CargoForm extends TruckitoForm {
	private static final long serialVersionUID = 2652490185550505504L;

	private Logger log = Logger.getLogger(CargoForm.class);
	
	private TextField cargoDescriptionInput;
	
	private TextField cargoWeightInput;
	private ComboBox<WeightUnit> cargoWeightUnitInput;
	
	private TextField cargoVolumeInput;
	private ComboBox<VolumeUnit> cargoVolumeUnitInput;
	
	private TextField packageCountInput;
	private ComboBox<PackageType> packageTypeInput;
	
	private TextField dimensionHeightInput;
	private TextField dimensionLengthInput;
	private TextField dimensionWidthInput;
	private ComboBox<DimensionUnit> dimensionUnitInput;
	
	private Binder<CargoFlatProxy> cargoBinder;
	
	private CargoFlatProxy cargoProxy;
	
	public CargoForm() {
		cargoProxy = CargoFlatProxy.from(new Cargo());
		log.info("Creating Cargo Form");
		addStyleName("p-0");
		VerticalLayout mainLayout = forgeBottomPanel();
		
		addComponent(mainLayout);
	}
	
	public CargoForm(Cargo cargo) {
		cargoProxy = CargoFlatProxy.from(cargo);
		log.info("Creating Cargo Form");
		addStyleName("p-0");
		VerticalLayout mainLayout = forgeBottomPanel();
		
		addComponent(mainLayout);
	}
	
	public Cargo extractCargo() throws ValidationException {
		cargoBinder.writeBean(cargoProxy);
		return cargoProxy.getCargo();
	}
	
	public boolean isValid() {
		return cargoBinder.isValid();
	}
	
	public void validate() {
		cargoBinder.validate();
	}
	
	private VerticalLayout forgeBottomPanel() {
		VerticalLayout bottomPanel = new VerticalLayout();
		bottomPanel.addStyleName("pt-0");
		cargoBinder = new Binder<>();
		
		HorizontalLayout firstRow = forgeFirstRow();
		HorizontalLayout secondRow = forgeSecondRow();
		HorizontalLayout thirdRow = forgeThirdRow();
		
		bottomPanel.addComponent(firstRow);
		bottomPanel.addComponent(secondRow);
		bottomPanel.addComponent(thirdRow);
		
		return bottomPanel;
	}
	
	private HorizontalLayout forgeFirstRow() {
		HorizontalLayout firstRow = createFullSizeRow();
		
		VerticalLayout cargoDescription = forgeCargoDescriptionInput();
		
		firstRow.addComponent(cargoDescription);
		return firstRow;
	}

	private HorizontalLayout forgeSecondRow() {
		HorizontalLayout secondRow = createFullSizeRow();
		
		VerticalLayout cargoWeight = forgeCargoWeightValueInput();
		VerticalLayout cargoWeightUnit = forgeCargoWeightUnitInput();
	
		VerticalLayout cargoVolume = forgeCargoVolumeValueInput();
		VerticalLayout cargoVolumeUnit = forgeCargoVolumeUnitInput();
		
		VerticalLayout packageCount = forgeCargoPackageCountInput();
		VerticalLayout packageType = forgeCargoPackageTypeInput();
		
		secondRow.addComponent(cargoWeight);
		secondRow.addComponent(cargoWeightUnit);
		secondRow.addComponent(cargoVolume);
		secondRow.addComponent(cargoVolumeUnit);
		secondRow.addComponent(packageCount);
		secondRow.addComponent(packageType);
		
		secondRow.setExpandRatio(cargoWeight, 2);
		secondRow.setExpandRatio(cargoWeightUnit, 1);
		secondRow.setExpandRatio(cargoVolume, 2);
		secondRow.setExpandRatio(cargoVolumeUnit, 1);
		secondRow.setExpandRatio(packageCount, 0.5f);
		secondRow.setExpandRatio(packageType, 0.8f);
		
		return secondRow;
	}

	private HorizontalLayout forgeThirdRow() {
		HorizontalLayout thirdRow = createFullSizeRow();

		VerticalLayout dimensionsHeight = forgeDimensionsHeightValueInput();
		
		VerticalLayout dimensionsLength = forgeDimensionsLengthValueInput();
		
		VerticalLayout dimensionsWidth = forgeDimensionsWidthValueInput();
		VerticalLayout dimensionsUnit = forgeDimensionUnitInput();

		thirdRow.addComponent(dimensionsHeight);
		thirdRow.addComponent(dimensionsLength);
		thirdRow.addComponent(dimensionsWidth);
		thirdRow.addComponent(dimensionsUnit);
		return thirdRow;
	}

	private VerticalLayout forgeCargoDescriptionInput() {
		VerticalLayout cargoDescription = new VerticalLayout();
		cargoDescription.addStyleName("p-0");
		Label cargoDescriptionHeader = new Label("Opis ładunku");
		
		cargoDescriptionInput = new TextField();
		cargoDescriptionInput.setSizeFull();
		cargoBinder.forField(cargoDescriptionInput)
					.bind(CargoFlatProxy::getDescription, CargoFlatProxy::setDescription);
		
		fancinizeInput(cargoDescriptionHeader, cargoDescriptionInput);
		
		cargoDescription.addComponent(cargoDescriptionHeader);
		cargoDescription.addComponent(cargoDescriptionInput);
		return cargoDescription;
	}
	
	private VerticalLayout forgeCargoWeightValueInput() {
		VerticalLayout cargoWeight = new VerticalLayout();
		cargoWeight.addStyleName("p-0");
		Label cargoWeightHeader = new Label("Waga");
		cargoWeightInput = new TextField();
		cargoWeightInput.setSizeFull();
		cargoBinder.forField(cargoWeightInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getWeightValue, CargoFlatProxy::setWeightValue);
		
		fancinizeInput(cargoWeightHeader, cargoWeightInput);
		cargoWeight.addComponent(cargoWeightHeader);
		cargoWeight.addComponent(cargoWeightInput);
		return cargoWeight;
	}
	
	private VerticalLayout forgeCargoWeightUnitInput() {
		VerticalLayout cargoWeightUnit = new VerticalLayout();
		cargoWeightUnit.addStyleName("p-0");
		Label cargoWeightUnitHeader = new Label("Jedn.");
		cargoWeightUnitInput = new ComboBox<WeightUnit>();
		cargoWeightUnitInput.setItems(WeightUnit.values());
		cargoBinder.forField(cargoWeightUnitInput)
					.bind(CargoFlatProxy::getWeightUnit, CargoFlatProxy::setWeightUnit);
		
		fancinizeInput(cargoWeightUnitHeader, cargoWeightUnitInput);
		cargoWeightUnit.addComponent(cargoWeightUnitHeader);
		cargoWeightUnit.addComponent(cargoWeightUnitInput);
		return cargoWeightUnit;
	}
	
	private VerticalLayout forgeCargoVolumeValueInput() {
		VerticalLayout cargoVolume = new VerticalLayout();
		cargoVolume.addStyleName("p-0");
		Label cargoVolumeHeader = new Label("Objętość");
		cargoVolumeInput = new TextField();
		cargoVolumeInput.setSizeFull();
		cargoBinder.forField(cargoVolumeInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getVolumeValue, CargoFlatProxy::setVolumeValue);
		
		fancinizeInput(cargoVolumeHeader, cargoVolumeInput);
		cargoVolume.addComponent(cargoVolumeHeader);
		cargoVolume.addComponent(cargoVolumeInput);
		return cargoVolume;
	}
	
	private VerticalLayout forgeCargoVolumeUnitInput() {
		VerticalLayout cargoVolumeUnit = new VerticalLayout();
		cargoVolumeUnit.addStyleName("p-0");
		Label cargoVolumeUnitHeader = new Label("Jedn.");
		cargoVolumeUnitInput = new ComboBox<VolumeUnit>();
		cargoVolumeUnitInput.setItems(VolumeUnit.values());
		cargoBinder.forField(cargoVolumeUnitInput)
					.bind(CargoFlatProxy::getVolumeUnit, CargoFlatProxy::setVolumeUnit);
		
		fancinizeInput(cargoVolumeUnitHeader, cargoVolumeUnitInput);
		cargoVolumeUnit.addComponent(cargoVolumeUnitHeader);
		cargoVolumeUnit.addComponent(cargoVolumeUnitInput);
		return cargoVolumeUnit;
	}
	
	private VerticalLayout forgeCargoPackageCountInput() {
		VerticalLayout packageCount = new VerticalLayout();
		packageCount.addStyleName("p-0");
		Label packageCountHeader = new Label("Ilość");
		packageCountInput = new TextField();
		packageCountInput.setSizeFull();
		cargoBinder.forField(packageCountInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getPackageAmount, CargoFlatProxy::setPackageAmount);
		
		fancinizeInput(packageCountHeader, packageCountInput);
		packageCount.addComponent(packageCountHeader);
		packageCount.addComponent(packageCountInput);
		return packageCount;
	}
	
	private VerticalLayout forgeCargoPackageTypeInput() {
		VerticalLayout packageType = new VerticalLayout();
		packageType.addStyleName("p-0");
		Label packageTypeHeader = new Label("Rodzaj");
		packageTypeInput = new ComboBox<>();
		packageTypeInput.setItems(PackageType.values());
		cargoBinder.forField(packageTypeInput)
					.bind(CargoFlatProxy::getPackageType, CargoFlatProxy::setPackageType);
		
		fancinizeInput(packageTypeHeader, packageTypeInput);
		packageType.addComponent(packageTypeHeader);
		packageType.addComponent(packageTypeInput);
		return packageType;
	}
	
	private VerticalLayout forgeDimensionsHeightValueInput() {
		VerticalLayout dimensionHeight = new VerticalLayout();
		dimensionHeight.addStyleName("p-0");
		Label dimensionHeightHeader = new Label("Wysokość");
		dimensionHeightInput = new TextField();
		cargoBinder.forField(dimensionHeightInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getHeightValue, CargoFlatProxy::setHeightValue);
		
		fancinizeInput(dimensionHeightHeader, dimensionHeightInput);
		dimensionHeight.addComponent(dimensionHeightHeader);
		dimensionHeight.addComponent(dimensionHeightInput);
		return dimensionHeight;
	}
	
	private VerticalLayout forgeDimensionsLengthValueInput() {
		VerticalLayout dimensionLength = new VerticalLayout();
		dimensionLength.addStyleName("p-0");
		Label dimensionLengthHeader = new Label("Długość");
		dimensionLengthInput = new TextField();
		cargoBinder.forField(dimensionLengthInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getLengthValue, CargoFlatProxy::setLengthValue);
		
		fancinizeInput(dimensionLengthHeader, dimensionLengthInput);
		dimensionLength.addComponent(dimensionLengthHeader);
		dimensionLength.addComponent(dimensionLengthInput);
		return dimensionLength;
	}
	
	private VerticalLayout forgeDimensionsWidthValueInput() {
		VerticalLayout dimensionWidth = new VerticalLayout();
		dimensionWidth.addStyleName("p-0");
		Label dimensionWidthHeader = new Label("Szerokość");
		dimensionWidthInput = new TextField();
		cargoBinder.forField(dimensionWidthInput)
					.withConverter(Long::valueOf, String::valueOf)
					.bind(CargoFlatProxy::getWidthValue, CargoFlatProxy::setWidthValue);
		
		fancinizeInput(dimensionWidthHeader, dimensionWidthInput);
		dimensionWidth.addComponent(dimensionWidthHeader);
		dimensionWidth.addComponent(dimensionWidthInput);
		return dimensionWidth;
	}
	
	private VerticalLayout forgeDimensionUnitInput() {
		VerticalLayout dimensionUnit = new VerticalLayout();
		dimensionUnit.addStyleName("p-0");
		Label dimensionUnitHeader = new Label("Jedn.");
		dimensionUnitInput = new ComboBox<DimensionUnit>();
		dimensionUnitInput.setItems(DimensionUnit.values());
		fancinizeInput(dimensionUnitHeader, dimensionUnitInput);
		dimensionUnit.addComponent(dimensionUnitHeader);
		dimensionUnit.addComponent(dimensionUnitInput);
		return dimensionUnit;
	}
}
