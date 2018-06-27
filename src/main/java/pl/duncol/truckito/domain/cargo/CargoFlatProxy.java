package pl.duncol.truckito.domain.cargo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.valueobjects.cargo.Dimensions;
import pl.duncol.truckito.valueobjects.cargo.PackageType;
import pl.duncol.truckito.valueobjects.cargo.Volume;
import pl.duncol.truckito.valueobjects.cargo.Weight;
import pl.duncol.truckito.valueobjects.cargo.units.DimensionUnit;
import pl.duncol.truckito.valueobjects.cargo.units.VolumeUnit;
import pl.duncol.truckito.valueobjects.cargo.units.WeightUnit;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CargoFlatProxy {
	
	@Getter
	@Setter
	private Cargo cargo;
	
	public static CargoFlatProxy from(Cargo cargo) {
		if (cargo != null) {
			return new CargoFlatProxy(cargo);
		} else {
			return new CargoFlatProxy(new Cargo());
		}
	}
	
	public String getDescription() {
		return cargo.getDescription();
	}
	
	public void setDescription(String description) {
		cargo.setDescription(description);
	}
	
	public Long getPackageAmount() {
		return cargo.getPackageAmount();
	}
	
	public void setPackageAmount(Long packageAmount) {
		cargo.setPackageAmount(packageAmount);
	}
	
	public String getMarksAndNumbers() {
		return cargo.getMarksAndNumbers();
	}
	
	public void setMarksAndNumbers(String marksAndNumbers) {
		cargo.setMarksAndNumbers(marksAndNumbers);
	}
	
	public Long getHeightValue() {
		if (cargo.getDimensions() == null) {
			return null;
		}
		return cargo.getDimensions().getHeight();
	}
	
	public void setHeightValue(Long height) {
		if (cargo.getDimensions() == null) {
			cargo.setDimensions(new Dimensions());
		}
		cargo.getDimensions().setHeight(height);
	}
	
	public Long getWidthValue() {
		if (cargo.getDimensions() == null) {
			return null;
		}
		return cargo.getDimensions().getWidth();
	}
	
	public void setWidthValue(Long width) {
		if (cargo.getDimensions() == null) {
			cargo.setDimensions(new Dimensions());
		}
		cargo.getDimensions().setWidth(width);
	}
	
	public Long getLengthValue() {
		if (cargo.getDimensions() == null) {
			return null;
		}
		return cargo.getDimensions().getLength();
	}
	
	public void setLengthValue(Long length) {
		if (cargo.getDimensions() == null) {
			cargo.setDimensions(new Dimensions());
		}
		cargo.getDimensions().setLength(length);
	}
	
	public DimensionUnit getDimensionsUnit() {
		if (cargo.getDimensions() == null) {
			return null;
		}
		return cargo.getDimensions().getUnit();
	}
	
	public void setDimensionsUnit(DimensionUnit unit) {
		if (cargo.getDimensions() == null) {
			cargo.setDimensions(new Dimensions());
		}
		cargo.getDimensions().setUnit(unit);
	}
	
	public Long getWeightValue() {
		if (cargo.getWeight() == null) {
			return null;
		}
		return cargo.getWeight().getValue();
	}
	
	public void setWeightValue(Long value) {
		if (cargo.getWeight() == null) {
			cargo.setWeight(new Weight());
		}
		cargo.getWeight().setValue(value);
	}
	
	public WeightUnit getWeightUnit() {
		if (cargo.getWeight() == null) {
			return null;
		}
		return cargo.getWeight().getUnit();
	}
	
	public void setWeightUnit(WeightUnit unit) {
		if (cargo.getWeight() == null) {
			cargo.setWeight(new Weight());
		}
		cargo.getWeight().setUnit(unit);
	}
	
	public Long getVolumeValue() {
		if (cargo.getVolume() == null) {
			return null;
		}
		return cargo.getVolume().getValue();
	}
	
	public void setVolumeValue(Long value) {
		if (cargo.getVolume() == null) {
			cargo.setVolume(new Volume());
		}
		cargo.getVolume().setValue(value);
	}
	
	public VolumeUnit getVolumeUnit() {
		if (cargo.getVolume() == null) {
			return null;
		}
		return cargo.getVolume().getUnit();
	}
	
	public void setVolumeUnit(VolumeUnit unit) {
		if (cargo.getVolume() == null) {
			cargo.setVolume(new Volume());
		}
		cargo.getVolume().setUnit(unit);
	}
	
	public PackageType getPackageType() {
		return cargo.getPackageType();
	}
	
	public void setPackageType(PackageType packageType) {
		cargo.setPackageType(packageType);
	}
}
