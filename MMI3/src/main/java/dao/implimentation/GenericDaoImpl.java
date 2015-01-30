package dao.implimentation;

import dao.interfaces.GenericDao;
import entity.Entity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

/**
 * Created by Nick on 21/01/2015.
 */
@Repository
@Scope(value = "prototype")
public class GenericDaoImpl<T extends Entity> implements GenericDao<T> {

    protected Class<T> clazz;

    @Override
    public void setClass(Class<T> cl) {
        clazz = cl;
    }

    @PersistenceContext
    protected  EntityManager em;


    @Override
    public Collection<T> getAllEntities(int start, int count, String order) {
        if (!order.equals("")) {
            order = " order by " + order;
        }
        Query query = em.createQuery("from " + clazz.getName() + order);
        query.setFirstResult(start);
        if (count > 0) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }

    @Override
    public Collection<T> getAllEntities() {
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public T getEntityById(int id) {
        return em.find(clazz, id);
    }

    @Override
    public void saveEntity(Entity entity) {
        em.persist(entity);
    }

    @Override
    public void updateEntity(T entity) {
        em.merge(entity);
    }

    @Override
    public void remove(int id) {
        T entity = getEntityById(id);
        remove(entity);
    }

    @Override
    public void remove(Entity entity) {
        em.remove(entity);
    }



}
