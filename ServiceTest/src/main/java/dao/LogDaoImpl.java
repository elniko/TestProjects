package dao;

import entities.LogEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.Calendar;

/**
 * Created by stagiaire on 08/12/2014.
 */
@Repository
public class LogDaoImpl extends GenericDao<LogEntity> implements  LogDao{

    @Override
    public boolean addStored(LogEntity entity) {
        StoredProcedureQuery storedQ = em.createStoredProcedureQuery("addLog");
        storedQ.registerStoredProcedureParameter( 1, Long.class, ParameterMode.IN );
        storedQ.registerStoredProcedureParameter( 2, String.class, ParameterMode.IN );
        storedQ.registerStoredProcedureParameter( 3, Long.class, ParameterMode.IN );
        //storedQ.registerStoredProcedureParameter( "created_at", Calendar.class, ParameterMode.IN );

        storedQ.setParameter(1, entity.getProcess().getId());
        storedQ.setParameter(2, entity.getMessage());
        storedQ.setParameter(3, entity.getLineCount());
        //storedQ.setParameter("created_at", entity.getCreatedAt());

        return storedQ.execute();


    }
}
