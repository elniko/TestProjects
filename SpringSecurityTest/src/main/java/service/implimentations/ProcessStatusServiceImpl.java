package service.implimentations;

import dao.interfaces.GenericDao;
import entity.ProcessStatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import service.interfaces.ProcessStatusService;

/**
 * Created by Nick on 22/01/2015.
 */
@Repository
public class ProcessStatusServiceImpl extends GenericServiceImpl<ProcessStatusEntity> implements ProcessStatusService{
    @Override
    @Autowired
    public void setGenericDao(GenericDao<ProcessStatusEntity> d) {
        dao = d;
        dao.setClass(ProcessStatusEntity.class);
    }
}
