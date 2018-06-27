package pl.duncol.truckito.security;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@ApplicationScoped
public class UserHashedPasswordDAO {
	@PersistenceContext(unitName = "TruckitoPU")
	private EntityManager em;
	
	public UserHashedPassword getHashSaltFor(String userName) {
		Query q = em.createQuery("FROM UserHashedPassword u WHERE u.userLogin = :userName");
		q.setParameter("userName", userName);
		UserHashedPassword result = (UserHashedPassword) q.getSingleResult();
		return result;
	}
}