package pl.duncol.truckito.valueobjects.address;

import lombok.Getter;

@Getter
public enum Country {
	POLAND("Polska"), 
	CZECH_REPUBLIC("Czechy"), 
	AUSTRIA("Austria"), 
	SLOVAKIA("Słowacja"), 
	SLOVENIA("Słowenia"), 
	FINLAND("Finlandia"), 
	ESTONIA("Estonia"), 
	ITALY("Włochy"), 
	HUNGARY("Węgry"), 
	ROMANIA("Rumunia");
	
	private String displayName;
	
	Country(String displayName) {
		this.displayName = displayName;
	}
	
	@Override
	public String toString() {
		return displayName;
	}
}
