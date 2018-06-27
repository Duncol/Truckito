package pl.duncol.truckito.web.forms.company2;

import javax.annotation.PostConstruct;

import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.company.CompanyFlatProxy;
import pl.duncol.truckito.web.forms.interfaces.AddForm;

public class CompanyAddForm extends CompanyForm implements AddForm<Company> {

	private static final long serialVersionUID = -3866578395689824843L;

	private Button submitButton;

	@Override
	@PostConstruct
	protected void initializeElements() {
		super.initializeElements();
	}

	@Override
	protected void prepareInputs() {
		super.prepareInputs();

		submitButton = forgeSubmitButton("Zapisz firmę");
		inputsPanel.addComponent(submitButton);
	}

	@Override
	public Company extractObject() throws ValidationException {
		CompanyFlatProxy companyProxy = CompanyFlatProxy.from(null);
		companyBinder.writeBean(companyProxy);
		return companyProxy.getCompany();
	}

	@Override
	public void setButtonAction(ClickListener action) {
		submitButton.addClickListener(action);
	}

	public void enableButton() {
		submitButton.setEnabled(true);
	}
}
