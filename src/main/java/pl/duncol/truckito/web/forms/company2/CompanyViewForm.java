package pl.duncol.truckito.web.forms.company2;

import javax.annotation.PostConstruct;

import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.company.CompanyFlatProxy;
import pl.duncol.truckito.web.forms.interfaces.ViewForm;

public class CompanyViewForm extends CompanyForm implements ViewForm<Company> {

	private static final long serialVersionUID = 3644132516573538854L;

	@Override
	@PostConstruct
	protected void initializeElements() {
		super.initializeElements();
	}

	@Override
	protected void preparePostalCodeField() {
		super.preparePostalCodeField();
		postalCodeTf.setReadOnly(true);
	}

	@Override
	protected void prepareCityField() {
		super.prepareCityField();
		cityTf.setReadOnly(true);
	}

	@Override
	protected void prepareCountryField() {
		super.prepareCountryField();
		countryComboBox.setReadOnly(true);
	}

	@Override
	protected void prepareAddressDetialsField() {
		super.prepareAddressDetialsField();
		addressDetailsTf.setReadOnly(true);
	}

	@Override
	protected void prepareShortNameField() {
		super.prepareShortNameField();
		shortNameTf.setReadOnly(true);
	}

	@Override
	protected void prepareFullNameField() {
		super.prepareFullNameField();
		fullNameTf.setReadOnly(true);
	}

	@Override
	public void setObject(Company object) {
		if (object != null) {
			companyProxy = CompanyFlatProxy.from(object);
			companyBinder.setBean(companyProxy);
		}
	}
}
