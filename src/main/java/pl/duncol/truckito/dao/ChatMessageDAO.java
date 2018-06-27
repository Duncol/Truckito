package pl.duncol.truckito.dao;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import pl.duncol.truckito.valueobjects.chat.ChatMessage;

@ApplicationScoped
public class ChatMessageDAO extends GenericDAOImpl<ChatMessage, Long> {

	private Logger log = Logger.getLogger(ChatMessageDAO.class);
	
	public ChatMessageDAO() {
		super(ChatMessage.class);
	}
	
	public List<ChatMessage> findLatest(Integer offset, Integer amount) {
		int firstResultIndex = offset;
		int maxResultIndex = offset + amount;
		log.infof("Fetching messages from %d to %d", firstResultIndex, maxResultIndex);
		
		String hql = "FROM " + ChatMessage.class.getSimpleName() + " m "
					+ "ORDER BY m.timestamp ASC";
		
		Query query = em.createQuery(hql);
		query.setFirstResult(firstResultIndex);
		query.setMaxResults(maxResultIndex);
		List<ChatMessage> result = query.getResultList();
		return result != null ? result : new ArrayList<>();
	}
	
	public List<ChatMessage> findAll(int offset, int limit) {
	    String hql = "FROM " + ChatMessage.class.getSimpleName() + " m ";
	    
	    Query query = em.createQuery(hql)
	            .setFirstResult(offset)
	            .setMaxResults(limit);
	    return query.getResultList();
	}
	
	public int count() {
	    String sql = "SELECT COUNT(*) FROM chat_message m ";
	    return em.createNativeQuery(sql).getFirstResult();
	}
}
