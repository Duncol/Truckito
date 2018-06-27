package pl.duncol.truckito.valueobjects.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class LicensePlate {
	
	@Column(name = "license_plate_number")
	private String number;
	
	public static LicensePlate of(String number) {
		if (number == null) {
			return new LicensePlate("");
		}
		return new LicensePlate(number);
	}
	
	public static boolean validate(String number) {
		return true;
	}
	
	@Override
	public String toString() {
		return number;
	}
}
