package dao.interfaces;

import entity.Entity;
import entity.UserEntity;
import exceptions.EntityNotExistsException;

import java.util.Collection;

/**
 * Created by Nick on 21/01/2015.
 */
public interface GenericDao<T extends Entity> {

    public Collection<T> getAllEntities(int start, int count, String order);

    public Collection<T> getAllEntities();

    public T getEntityById(int id);

    public void saveEntity(Entity entity);

    public void updateEntity(T entity);

    public boolean remove(int id) throws EntityNotExistsException;

    public boolean remove(Entity entity) throws EntityNotExistsException;

    public void setClass(Class<T> cl);

    public Collection<T> getAllByUserAndCondition(String alias, UserEntity user, String condition, int start, int count, String order);

    public Collection<T> getAllByCondition(String alias, String condition, int start, int count, String order);

}
