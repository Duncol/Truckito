package pl.duncol.truckito.domain.document;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.cargo.Cargo;
import pl.duncol.truckito.domain.company.Company;
import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.disposition.Load;
import pl.duncol.truckito.domain.disposition.Unload;
import pl.duncol.truckito.domain.vehicle.Trailer;
import pl.duncol.truckito.domain.vehicle.Truck;

@Entity
@Table(name = "cmr_document")
@Getter
@Setter
public class CMRDocument extends Document {
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="disposition_id")
	private Disposition disposition;
	
	@ManyToOne
	@JoinColumn(name="sender_id")
	private Company sender;
	
	@ManyToOne
	@JoinColumn(name="consignee_id")
	private Company consignee; // Subject, that awaits delivery
	
	@ManyToOne
	@JoinColumn(name="carrier_id")
	private Company carrier; // Responsible subject i.e. FREJA
	
	@OneToMany(mappedBy = "cmr", cascade=CascadeType.ALL)
	private List<Cargo> cargoList = new ArrayList<>();
	
	@Column(name = "dgr_information")
	private String dgrInformation;
	
	@Column(name = "special_agreements")
	private String specialAgreements;
	
//	@OneToMany
//	private List<Document> attachedDocuments;
	
//	@OneToOne
//	@JoinColumn(name = "load_id")
//	private Load load;
//	
//	@OneToOne
//	@JoinColumn(name = "unload_id")
//	private Unload unload;
	
	@ManyToOne
	@JoinColumn(name = "truck_id")
	private Truck truck;
	
	@ManyToOne
	@JoinColumn(name = "trailer_id")
	private Trailer trailer;
	
	public void addCargo(Cargo cargo) {
		cargoList.add(cargo);
		cargo.setCmr(this);
	}
	
	public void removeCargo(Cargo cargo) {
		cargoList.remove(cargo);
		cargo.setCmr(null);
	}
}
