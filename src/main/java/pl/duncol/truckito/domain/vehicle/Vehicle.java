package pl.duncol.truckito.domain.vehicle;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.valueobjects.id.LicensePlate;

@MappedSuperclass
@Getter
@Setter
public abstract class Vehicle extends BaseEntity<Long> {
	
	@Embedded
	LicensePlate licensePlate;
	
	String model;
	
	@Column(name = "image_url")
	String imageURL;
	
	@Override
	public String toString() {
		return licensePlate.getNumber() + " " + model;
	}
}
