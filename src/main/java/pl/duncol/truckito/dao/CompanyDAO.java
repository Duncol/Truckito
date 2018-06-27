package pl.duncol.truckito.dao;

import javax.enterprise.context.ApplicationScoped;

import pl.duncol.truckito.domain.company.Company;

@ApplicationScoped
public class CompanyDAO extends GenericDAOImpl<Company, Long>{
	
	public CompanyDAO() {
		super(Company.class);
	}
}
