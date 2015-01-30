package dao.implimentation;

import dao.interfaces.ResourceDao;
import entity.Entity;
import entity.ResourceEntity;
import entity.ResourceTypeEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 29/01/2015.
 */
@Repository
public class ResourceDaoImpl extends GenericDaoImpl<ResourceEntity> implements ResourceDao {

    @PostConstruct
    public void init() {
        setClass(ResourceEntity.class);
    }
    @Override
    public void removeResources(int[] ids) {
        for(int id : ids) {
            remove(id);
        }
    }


}
