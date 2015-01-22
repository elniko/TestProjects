package dao.implimentation;

import dao.interfaces.GenericDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by Nick on 21/01/2015.
 */
@Repository
@Scope(value = "prototype")
public class GenericDaoImpl<T> implements GenericDao<T> {

    protected Class<T> clazz;

    @Override
    public void setClass(Class<T> cl) {
        clazz = cl;
    }

    @PersistenceContext
    protected  EntityManager em;


    @Override
    public Collection<T> getAllEntities() {
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public T getEntityById(int id) {
        return em.find(clazz, id);
    }

    @Override
    public void saveEntity(T entity) {
        em.persist(entity);
    }

    @Override
    public void updateEntity(T entity) {
        em.merge(entity);
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void remove(T entity) {

    }
}
