package pl.duncol.truckito.domain.user.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.user.User;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Employee extends User {
	
	@Column(name ="first_name")
	String firstName;
	
	@Column(name ="last_name")
	String lastName;
	
	@Column(name ="phone_number")
	String phoneNumber;
	
	@Override
	public String toString() {
		return firstName + " " + lastName; 
	}
}
