package dao.implimentation;

import dao.interfaces.ProcessStatusDao;
import entity.ProcessStatusEntity;
import entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 26/01/2015.
 */
@Repository
public class ProcessStatusDaoImpl extends GenericDaoImpl<ProcessStatusEntity> implements ProcessStatusDao {
    @PostConstruct
    public void init() {
        setClass(ProcessStatusEntity.class);
    }
}
