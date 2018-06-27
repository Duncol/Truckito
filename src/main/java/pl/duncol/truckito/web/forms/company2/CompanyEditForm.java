package pl.duncol.truckito.web.forms.company2;

import com.vaadin.data.ValidationException;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.company.CompanyFlatProxy;
import pl.duncol.truckito.web.forms.interfaces.EditForm;

public class CompanyEditForm extends CompanyForm implements EditForm<Company> {

	private static final long serialVersionUID = 5717099266081657823L;

	protected Button submitButton;

	@Override
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
	public void setObject(Company company) {
		if (company != null) {
			companyProxy = CompanyFlatProxy.from(company);
			companyBinder.setBean(companyProxy);
		}
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

	public void enableSubmitButton() {
		submitButton.setEnabled(true);
	}
}
