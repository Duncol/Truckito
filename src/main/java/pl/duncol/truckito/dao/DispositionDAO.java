package pl.duncol.truckito.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

import pl.duncol.truckito.domain.disposition.Disposition;

@ApplicationScoped
public class DispositionDAO extends GenericDAOImpl<Disposition, Long> {
	public DispositionDAO() {
		super(Disposition.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Disposition> getActiveDispositions(boolean sortDescByDate) {
		String orderDirection = sortDescByDate ? "DESC" : "ASC";
		Query q = em.createQuery("FROM " + Disposition.class.getCanonicalName() + " d "
								+ " WHERE d.isActive IS TRUE "
								+ " ORDER BY d.createDate " + orderDirection
							, Disposition.class);
		
		return q.getResultList();
	}
}