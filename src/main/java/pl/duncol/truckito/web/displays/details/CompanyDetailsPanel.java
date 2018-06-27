package pl.duncol.truckito.web.displays.details;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import pl.duncol.truckito.domain.company.Company;

public class CompanyDetailsPanel extends VerticalLayout {
	private static final long serialVersionUID = 2688624986774183895L;
	
	private HorizontalLayout firstRow;
	private Label fullName;
	
	private HorizontalLayout secondRow;
	private Label addressDetails;
	
	private HorizontalLayout thirdRow;
	private Label country;
	private Label city;
	private Label postalCode;
	
	public CompanyDetailsPanel() {
		setVisible(false);
		addStyleName("no-padding");
		
		forgeFirstRow();
		forgeSecondRow();
		forgeThirdRow();
		
		addComponent(firstRow);
		addComponent(secondRow);
		addComponent(thirdRow);
	}
	
	public void fillContent(Company source) {
		if (source != null) {
			fullName.setValue(source.getFullName());
			addressDetails.setValue(source.getAddress().getDetails());
			country.setValue(source.getAddress().getCountry().toString());
			city.setValue(source.getAddress().getCity());
			postalCode.setValue(source.getAddress().getPostalCode().getValue());
			if (!isVisible()) {
				setVisible(true);
			}
		} else {
			fullName.setValue("");
			addressDetails.setValue("");
			country.setValue("");
			city.setValue("");
			postalCode.setValue("");
			if (isVisible()) {
				setVisible(false);
			}
		}
	}
	
	private void forgeFirstRow() {
		firstRow = new HorizontalLayout();
		firstRow.addStyleName("no-padding");
		
		fullName = new Label();
		
		firstRow.addComponent(fullName);
	}
	
	private void forgeSecondRow() {
		secondRow = new HorizontalLayout();
		secondRow.addStyleName("no-padding");
		
		addressDetails = new Label();
		
		secondRow.addComponent(addressDetails);
	}
	
	private void forgeThirdRow() {
		thirdRow = new HorizontalLayout();
		thirdRow.addStyleName("no-padding");
		
		country = new Label();
		city = new Label();
		postalCode = new Label();
		
		thirdRow.addComponent(country);
		thirdRow.addComponent(city);
		thirdRow.addComponent(postalCode);
	}
}
