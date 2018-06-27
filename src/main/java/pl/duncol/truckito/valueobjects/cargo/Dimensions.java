package pl.duncol.truckito.valueobjects.cargo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.duncol.truckito.valueobjects.cargo.units.DimensionUnit;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Dimensions {

	@Column(name = "dimension_height")
	private Long height;
	@Column(name = "dimension_width")
	private Long width;
	@Column(name = "dimension_length")
	private Long length;

	@Enumerated(EnumType.STRING)
	private DimensionUnit unit;

	@Transient
	private final static String pattern = "";

//	public static Dimensions of(String height, String width, String length) throws ValidationException {
//		if (height.matches(pattern) && width.matches(pattern) && length.matches(pattern)) {
//			return new Dimensions(height, width, length);			
//		} else {
//			throw new ValidationException();
//		}
//	}
//
//	private Dimensions(String height, String width, String length) {
//		this.height = new BigDecimal(height);
//		this.width = new BigDecimal(width);
//		this.length = new BigDecimal(length);
//	}
}