package pl.duncol.truckito.valueobjects.cargo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.duncol.truckito.valueobjects.cargo.units.WeightUnit;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Weight {
	
	@Column(name = "weight_value")
	private Long value;
	@Column(name = "weight_unit")
	private WeightUnit unit;
	
	private final String pattern = "";
	
//	public Weight(String value, WeightUnit unit) {
//		this.weightValue = new BigDecimal(value);
//		this.weightUnit = unit;
//	}
//	
//	public String getWieghtAsString() {
//		return this.weightValue.toString();
//	}
//	
//	public void setWeight(String value) {
//		if (value.matches(pattern)) {
//			this.weightValue = new BigDecimal(value);
//		}
//	}
}
