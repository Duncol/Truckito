package pl.duncol.truckito.dao;

import javax.enterprise.context.ApplicationScoped;

import pl.duncol.truckito.domain.document.CMRDocument;

@ApplicationScoped
public class CMR_DAO extends GenericDAOImpl<CMRDocument, Long> {

	public CMR_DAO() {
		super(CMRDocument.class);
	}
}
