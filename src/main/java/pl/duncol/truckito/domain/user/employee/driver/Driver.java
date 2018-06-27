package pl.duncol.truckito.domain.user.employee.driver;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.user.employee.Employee;

@Entity
@Table(name = "emp_drivers")
@Getter
@Setter
public class Driver extends Employee {
	
	@ManyToMany(mappedBy="drivers")
	private Set<Disposition> dispositions = new LinkedHashSet<>();
	
	@Column(name = "id_card_number", length = 20)
	private String idCardNumber;
	
	@Column(name = "is_adr")
	private boolean isADR;
	
	@Column(name = "image_url")
	private String imageURL;
	
	@Enumerated(EnumType.STRING)
	private DriverStatus status = DriverStatus.READY;
	
	public void addDisposition(Disposition disp) {
		dispositions.add(disp);
		disp.addDriver(this);
	}
}
