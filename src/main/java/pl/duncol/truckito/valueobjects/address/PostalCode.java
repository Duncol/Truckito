package pl.duncol.truckito.valueobjects.address;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PostalCode {

	@Column(name = "postal_code", length = 10)
	private String value;
	
	public static PostalCode of(String number) {
		if (number == null) {
			return new PostalCode("");
		}
		return new PostalCode(number);
	}
	
	public static boolean validate(String input) {
//		return input.matches("[0-9]{2}-?[0-9]{3,4}");
		return input.length() <= 10;
	}

	@Override
	public String toString() {
		return this.value;
	}
}