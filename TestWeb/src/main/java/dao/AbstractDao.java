package dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stagiaire on 21/11/2014.
 */
public class AbstractDao<T extends Serializable> implements IGenericDao<T>{

    @Autowired
    SessionFactory sessionFactory;

    private Class<T> clazz;

    public void setClazz(Class<T> classToSet) {
        clazz =  classToSet;
    }

    @Override
    public T findOne(long id) {
        return (T)getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    @Override
    public void create(T entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public T update(T entity) {
      return (T) getCurrentSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        getCurrentSession().delete(findOne(entityId));
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
