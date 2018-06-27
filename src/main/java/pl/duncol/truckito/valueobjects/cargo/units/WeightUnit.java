package pl.duncol.truckito.valueobjects.cargo.units;

import lombok.Getter;

@Getter
public enum WeightUnit {
	TON("t"), KILOGRAM("kg"), POUND("lbs");
	
	private String unitTag;
	
	private WeightUnit(String unitTag) {
		this.unitTag = unitTag;
	}
}
