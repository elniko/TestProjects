package service.interfaces;

import java.util.Collection;

/**
 * Created by Nick on 21/01/2015.
 */
public interface  GenericDaoService<T> {
    public Collection<T> getAllEntities();

    public T getEntityById(int id);

    public void saveEntity(T entity);

    public void updateEntity(T entity);

    public void remove(int id);

    public void remove(T entity);
}
