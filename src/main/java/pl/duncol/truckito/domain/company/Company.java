package pl.duncol.truckito.domain.company;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.valueobjects.address.Address;

@Entity
@Table(name = "company")
@Getter
@Setter
public class Company extends BaseEntity<Long> {
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "short_name", length = 80)
	private String shortName;
	
	@Embedded
	private Address address;
	
	@Override
	public String toString() {
		return shortName;
	}
}