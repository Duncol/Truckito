package pl.duncol.truckito.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

import pl.duncol.truckito.domain.disposition.Disposition;
import pl.duncol.truckito.domain.user.employee.driver.Driver;
import pl.duncol.truckito.domain.user.employee.driver.DriverStatus;

//@Stateless
@ApplicationScoped
public class DriverDAO extends GenericDAOImpl<Driver, Long>{
	
	public DriverDAO() {
		super(Driver.class);
	}
	
	public Set<Driver> getAllUnoccupiedDrivers() {
		Query q1 = em.createQuery("FROM " + Driver.class.getCanonicalName() + " drv "
								+ " WHERE drv.status = '" + DriverStatus.READY + "'");
		Query q2 = em.createQuery("FROM " + Disposition.class.getCanonicalName() + " d "
								+ " WHERE d.isActive IS TRUE");
		
		@SuppressWarnings("unchecked")
		List<Driver> readyDrivers = (List<Driver>) q1.getResultList();
		@SuppressWarnings("unchecked")
		Set<Driver> activeDrivers = ((List<Disposition>) q2.getResultList()).stream()
										.flatMap(e -> e.getDrivers().stream())
										.collect(Collectors.toSet());
		
		return readyDrivers.stream().filter(d -> !activeDrivers.contains(d)).collect(Collectors.toSet());
	}
}