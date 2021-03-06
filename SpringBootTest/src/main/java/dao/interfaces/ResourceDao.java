package dao.interfaces;

import entity.Entity;
import entity.ResourceEntity;
import exceptions.EntityNotExistsException;

/**
 * Created by Nick on 23/01/2015.
 */
public interface ResourceDao extends GenericDao<ResourceEntity> {
    public void saveEntity(Entity entity);

    public void removeResources(int[] ids) throws EntityNotExistsException;


}
