package dao.interfaces;

import entity.Entity;
import entity.UserEntity;

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

    public void remove(int id);

    public void remove(Entity  entity);

    public void setClass(Class<T> cl);

    public Collection<T> getAllByUserAndCondition(String alias, UserEntity user ,String condition, int start, int count, String order);


}
