package dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stagiaire on 21/11/2014.
 */
public interface IGenericDao <T extends Serializable> {

    T findOne(final long id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);

}