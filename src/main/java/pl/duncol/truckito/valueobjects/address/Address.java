package pl.duncol.truckito.valueobjects.address;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@Enumerated(EnumType.STRING)
	private Country country;
	
	@Column(name = "address_land_sector", length = 100)
	private String landSector;
	
	@Column(length = 58) // Llanfairpwllgwyngyllgogerychwyrndrobwllllantysiliogogogoch
	private String city;
	
	@Embedded
	private PostalCode postalCode;
	
	@Column(name = "address_details")
	private String details;
}
