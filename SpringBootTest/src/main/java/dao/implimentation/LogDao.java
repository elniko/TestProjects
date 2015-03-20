package dao.implimentation;

import entity.LogEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

/**
 * Created by stagiaire on 22/12/2014.
 */
@Repository
public class LogDao implements dao.interfaces.LogDao {
    @PersistenceContext
    EntityManager em;



    @Transactional
    @Override
    public void addLogMessage(LogEntity entity) {
        StoredProcedureQuery query  = em.createNamedStoredProcedureQuery("addLog");
        query.setParameter("processId", entity.getProcess().getId());
        query.setParameter("message", entity.getMessage());
        query.setParameter("lineCount", entity.getLineCount());
        query.execute();
        em.clear();

    }
}
