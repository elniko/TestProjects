package service.implimentations;

import dao.interfaces.GenericDao;
import entity.ProcessTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import service.interfaces.ProcessTypeService;

/**
 * Created by Nick on 22/01/2015.
 */

@Repository
public class ProcessTypeServiceImpl extends GenericServiceImpl<ProcessTypeEntity> implements ProcessTypeService{

    @Override
    @Autowired
    public void setGenericDao(GenericDao<ProcessTypeEntity> d) {
        dao = d;
        dao.setClass(ProcessTypeEntity.class);
    }
}
