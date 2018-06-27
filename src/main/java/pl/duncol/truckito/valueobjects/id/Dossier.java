package pl.duncol.truckito.valueobjects.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Dossier {
	
	@Column(name = "dossier_number")
	private String number;
	
//	public Dossier() {
//		
//	}
	
	public static Dossier of(String number) {
		return new Dossier(number);
	}
	public static boolean check(String dossier) {
		return true;
	}
	
	@Override
	public String toString() {
		return this.number;
	}
}
