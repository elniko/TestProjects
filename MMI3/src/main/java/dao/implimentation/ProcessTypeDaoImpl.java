package dao.implimentation;

import dao.interfaces.GenericDao;
import dao.interfaces.ProcessTypeDao;
import entity.ProcessTypeEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 26/01/2015.
 */
@Repository
public class ProcessTypeDaoImpl extends GenericDaoImpl<ProcessTypeEntity> implements ProcessTypeDao {
    @PostConstruct
    public void init() {
        setClass(ProcessTypeEntity.class);
    }
}
