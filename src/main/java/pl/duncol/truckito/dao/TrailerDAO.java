package pl.duncol.truckito.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.vehicle.Trailer;

//@Stateless
@ApplicationScoped
public class TrailerDAO extends GenericDAOImpl<Trailer, Long>{

	public TrailerDAO() {
		super(Trailer.class);
	}
	
	public Set<Trailer> getAllUnoccupiedTrailers() {
		Query q1 = em.createQuery("FROM " + Trailer.class.getCanonicalName() + " trl");
		Query q2 = em.createQuery("FROM " + Disposition.class.getCanonicalName() + " d " + " WHERE d.isActive IS TRUE");

		@SuppressWarnings("unchecked")
		List<Trailer> readyTrailers = (List<Trailer>) q1.getResultList();
		@SuppressWarnings("unchecked")
		Set<Trailer> activeTrailers = ((List<Disposition>) q2.getResultList()).stream()
				.flatMap(e -> e.getTrailers().stream()).collect(Collectors.toSet());

		return readyTrailers.stream().filter(t -> !activeTrailers.contains(t)).collect(Collectors.toSet());
	}
}