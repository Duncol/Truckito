package pl.duncol.truckito.domain.company;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.valueobjects.address.Address;
import pl.duncol.truckito.valueobjects.address.Country;
import pl.duncol.truckito.valueobjects.address.PostalCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyFlatProxy {
	
	@Getter
	@Setter
	private Company company;
	
	public static CompanyFlatProxy from(Company company) {
		if (company != null) {
			return new CompanyFlatProxy(company);
		} else {
			return new CompanyFlatProxy(new Company());
		}
	}
	
	public String getFullName() {
		return company.getFullName();
	}
	
	public void setFullName(String fullName) {
		company.setFullName(fullName);
	}
	
	public String getShortName() {
		return company.getShortName();
	}
	
	public void setShortName(String shortName) {
		company.setShortName(shortName);
	}
	
	public Country getAddressCountry() {
		if (company.getAddress() == null) {
			return null;
		}
		return company.getAddress().getCountry();
	}
	
	public void setAddressCountry(Country country) {
		if (company.getAddress() == null) {
			company.setAddress(new Address());
		}
		company.getAddress().setCountry(country);
	}
	
	public String getAddressCity() {
		if (company.getAddress() == null) {
			return null;
		}
		return company.getAddress().getCity();
	}
	
	public void setAddressCity(String city) {
		if (company.getAddress() == null) {
			company.setAddress(new Address());
		}
		company.getAddress().setCity(city);
	}
	
	public String getAddressLandSector() {
		if (company.getAddress() == null) {
			return null;
		}
		return company.getAddress().getLandSector();
	}
	
	public void setAddressLandSector(String landSector) {
		if (company.getAddress() == null) {
			company.setAddress(new Address());
		}
		company.getAddress().setLandSector(landSector);
	}
	
	public PostalCode getAddressPostalCode() {
		if (company.getAddress() == null) {
			return null;
		}
		return company.getAddress().getPostalCode();
	}
	
	public void setAddressPostalCode(PostalCode postalCode) {
		if (company.getAddress() == null) {
			company.setAddress(new Address());
		}
		company.getAddress().setPostalCode(postalCode);
	}
	
	public String getAddressDetails() {
		if (company.getAddress() == null) {
			return null;
		}
		return company.getAddress().getDetails();
	}
	
	public void setAddressDetails(String addressDetails) {
		if (company.getAddress() == null) {
			company.setAddress(new Address());
		}
		company.getAddress().setDetails(addressDetails);
	}
}
