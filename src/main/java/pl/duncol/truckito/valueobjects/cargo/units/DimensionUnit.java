package pl.duncol.truckito.valueobjects.cargo.units;

import lombok.Getter;

@Getter
public enum DimensionUnit {
	METER("m"), INCH("\"");
	
	private String unitTag;
	
	private DimensionUnit(String unitTag) {
		this.unitTag = unitTag;
	}
}