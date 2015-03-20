package dao.implimentation;

import dao.interfaces.ProcessDao;
import entity.ProcessEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 09/03/2015.
 */
@Repository
public class ProcessDaoImpl extends GenericDaoImpl<ProcessEntity> implements ProcessDao {
    @PostConstruct
    public void init() {
        setClass(ProcessEntity.class);
    }
}
