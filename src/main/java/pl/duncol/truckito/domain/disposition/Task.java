package pl.duncol.truckito.domain.disposition;

import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.valueobjects.Comment;

//@MappedSuperclass
public abstract class Task extends BaseEntity<Long> {

//	@ManyToOne
//	@JoinColumn(name = "company_id")
	Company relatedCompany;
	
//	@Embedded
	Comment comment;
}