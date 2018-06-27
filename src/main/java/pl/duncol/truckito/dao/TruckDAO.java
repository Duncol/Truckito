package pl.duncol.truckito.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;

import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.vehicle.Truck;

//@Stateless
@ApplicationScoped
public class TruckDAO extends GenericDAOImpl<Truck, Long>{
	
	public TruckDAO() {
		super(Truck.class);
	}
	
	@Transactional
	public Set<Truck> getAllUnoccupiedTrucks() {
		Query q = em.createQuery("FROM " + Truck.class.getCanonicalName() + " trc "
								+ " JOIN trc.dispositions d "
								+ " WITH d.isActive IS NOT TRUE ");
		
		Query q1 = em.createQuery("FROM " + Truck.class.getCanonicalName() + " trc ");
		Query q2 = em.createQuery("FROM " + Disposition.class.getCanonicalName() + " d " + " WHERE d.isActive IS TRUE");

		@SuppressWarnings("unchecked")
		List<Truck> readyTrucks = (List<Truck>) q1.getResultList();
		@SuppressWarnings("unchecked")
		Set<Truck> activeTrucks = ((List<Disposition>) q2.getResultList()).stream()
				.flatMap(e -> e.getTrucks().stream()).collect(Collectors.toSet());

		return readyTrucks.stream().filter(t -> !activeTrucks.contains(t)).collect(Collectors.toSet());
	}
}
