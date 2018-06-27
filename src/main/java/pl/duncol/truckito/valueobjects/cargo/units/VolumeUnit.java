package pl.duncol.truckito.valueobjects.cargo.units;

import lombok.Getter;

@Getter
public enum VolumeUnit {
	LITER("L"), CUBIC_METER("m^3");
	
	private String unitTag;
	
	private VolumeUnit(String unitTag) {
		this.unitTag = unitTag;
	}
}
