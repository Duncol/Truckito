package pl.duncol.truckito.valueobjects.cargo.units;

public enum AmountUnit {
	PIECES("szt.");
	
	private String unitTag;
	
	private AmountUnit(String unitTag) {
		this.unitTag = unitTag;
	}
}
