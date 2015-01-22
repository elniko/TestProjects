package service.implimentations;

import dao.interfaces.GenericDao;
import entity.ResourceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import service.interfaces.ResourceTypeService;

/**
 * Created by Nick on 21/01/2015.
 */
@Repository
public class ResourceTypeServiceImpl extends GenericServiceImpl<ResourceTypeEntity> implements ResourceTypeService {

    @Override
    @Autowired
    public void setGenericDao(GenericDao<ResourceTypeEntity> d) {
        dao = d;
        dao.setClass(ResourceTypeEntity.class);
    }
}
