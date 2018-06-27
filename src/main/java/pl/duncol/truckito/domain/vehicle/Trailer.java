package pl.duncol.truckito.domain.vehicle;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import pl.duncol.truckito.domain.disposition.Disposition;

@Entity
@Table(name = "veh_trailers")
@Getter
@Setter
public class Trailer extends Vehicle {
	
	@ManyToMany(mappedBy="trailers")
	private Set<Disposition> dispositions = new LinkedHashSet<>();
	
	public void addDisposition(Disposition disp) {
		dispositions.add(disp);
		disp.addTrailer(this);
	}
}