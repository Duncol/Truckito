package pl.duncol.truckito.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import lombok.Setter;
import pl.duncol.truckito.domain.BaseEntity;

public class GenericDAOImpl<T extends BaseEntity<ID>, ID extends Serializable> implements GenericDAO<T, ID> {

	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(GenericDAOImpl.class);

	@PersistenceContext(unitName = "TruckitoPU")
	EntityManager em;
	@Setter
	Class<T> entityClass;

	public GenericDAOImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		if (entityClass == null) {
			// only works if one extends BaseDao, we will take care of it with
			// CDI
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}

	@Override
	@Transactional
	public void create(T entity) {
		em.persist(entity);
	}

	@Override
	@Transactional
	public T read(ID id) {
		T person = em.find(this.entityClass, id);
		return person;
	}

	@Override
	@Transactional
	public T update(T entity) {
		log.infof("Updating entity, id: %s", entity.getId());
		entity = em.merge(entity);
		return entity;
	}

	@Override
	@Transactional
	public void delete(T entity) {
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String hql = "select t from " + entityClass.getSimpleName() + " t";
		List<T> result = em.createQuery(hql).getResultList();
		return result != null ? result : new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Deprecated
	public List<T> findLatest(Integer page, Integer amount) {
		int firstResultIndex = page*amount;
		int maxResultIndex = (page*amount)+amount;
		String hql = "from " + entityClass.getSimpleName() + " e \n"
					+ "order by e.postDate asc";
		Query query = em.createQuery(hql);
		query.setFirstResult(firstResultIndex);
		query.setMaxResults(maxResultIndex);
		List<T> result = query.getResultList();
		return result != null ? result : new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(int offset, int limit) {
		log.infof("Fetching " + entityClass.getSimpleName() + "'s. Offset: %d. Limit: %d", offset, limit);
	    String hql = "from " + entityClass.getSimpleName();
	    Query query = em.createQuery(hql)
	            .setFirstResult(offset)
	            .setMaxResults(limit);
	    return query.getResultList();
	}

	public int count() {
	    String hql = "select count(*) from " + entityClass.getSimpleName();
	    Query q = em.createQuery(hql);
	    int result = ((Long) q.getResultList().get(0)).intValue();
	    log.infof("Counting " + entityClass.getSimpleName() + "'s in database. Result: %d", result);
	    return result;
	}
}