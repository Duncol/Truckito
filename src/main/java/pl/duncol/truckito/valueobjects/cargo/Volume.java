package pl.duncol.truckito.valueobjects.cargo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.duncol.truckito.valueobjects.cargo.units.VolumeUnit;

/*
 * This class isn't redundant, despite having Dimensions class.
 * That's beacause most of the cases we have volume as derivative,
 * without dimensions as source.
 */
@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Volume {
	
	@Column(name = "volume_value")
	private Long value;
	@Column(name = "volume_unit")
	private VolumeUnit unit;
	
	@Transient
	private final String pattern = "";
	
//	public Volume(String value, VolumeUnit unit) {
//		if (value.matches(pattern)) {
//			this.volumeValue = new BigDecimal(value);
//		}
//		this.volumeUnit = unit;
//	}
//	
//	public Volume(BigDecimal value, VolumeUnit unit) {
//		this.volumeValue = value;
//		this.volumeUnit = unit;
//	}
//	
//	public String getVolumeAsString() {
//		return this.volumeValue.toString();
//	}
//	
//	public void setVolume(String value) {
//		if (value.matches(pattern)) {
//			this.volumeValue = new BigDecimal(value);
//		}
//	}
//	
//	public void setVolume(String value, VolumeUnit unit) {
//		if (value.matches(pattern)) {
//			this.volumeValue = new BigDecimal(value);
//		}
//		this.volumeUnit = unit;
//	}
}
