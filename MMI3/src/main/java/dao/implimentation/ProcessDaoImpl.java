package dao.implimentation;

import dao.interfaces.ProcessDao;
import entity.ProcessEntity;
import entity.ProcessStatusEntity;
import entity.ProfileEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Nick on 09/03/2015.
 */
@Repository
public class ProcessDaoImpl extends GenericDaoImpl<ProcessEntity> implements ProcessDao {
    @PostConstruct
    public void init() {
        setClass(ProcessEntity.class);
    }


    @Override
    public List<ProcessEntity> getPendings() {
        TypedQuery<ProcessEntity> q = em.createNamedQuery("GET_PENDING", ProcessEntity.class);
        return q.getResultList();
    }
}
