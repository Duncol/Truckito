package pl.duncol.truckito.domain.cargo;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;
import pl.duncol.truckito.domain.document.CMRDocument;
import pl.duncol.truckito.valueobjects.cargo.Dimensions;
import pl.duncol.truckito.valueobjects.cargo.PackageType;
import pl.duncol.truckito.valueobjects.cargo.Volume;
import pl.duncol.truckito.valueobjects.cargo.Weight;

@Entity
@Table(name="cargo")
@Setter
@Getter
public class Cargo extends BaseEntity<Long> {
	
	private String description;
	
	@Column(name = "package_amount")
	private Long packageAmount;

	@Column(name = "marks_and_numbers")
	private String marksAndNumbers;
	
	@Enumerated(EnumType.STRING)
	private PackageType packageType;
	
	@Embedded
	private Dimensions dimensions;
	
	@Embedded
	private Weight weight;
	
	@Embedded
	private Volume volume;
	
	@ManyToOne
	@JoinColumn(name="cmr_id")
	private CMRDocument cmr;
}