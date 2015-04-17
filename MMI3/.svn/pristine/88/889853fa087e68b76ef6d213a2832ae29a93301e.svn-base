package dao.implimentation;

import dao.interfaces.GenericDao;
import entity.Entity;
import entity.UserEntity;
import exceptions.EntityNotExistsException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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

    @PersistenceContext()
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

    public Collection<T> getAllByCondition(String alias, String condition, int start, int count, String order) {
        if (!order.equals("")) {
            order = " order by " + order;
        }
        if (!condition.equals("")) {
           condition = " where " + condition;
        }

        Query query = em.createQuery("from " + clazz.getName() + " " + alias +  condition + " "  + order);
        query.setFirstResult(start);
        if (count > 0) {
            query.setMaxResults(count);
        }
        return query.getResultList();
    }


    public Collection<T> getAllByUserAndCondition(String alias, UserEntity user ,String condition, int start, int count, String order) {
        if (!order.equals("")) {
            order = " order by " + order;
        }
        Query query = em.createQuery("from " + clazz.getName() + " " + alias + " where " + alias + ".user=:user " + condition + " "  + order);
        query.setParameter("user", user);
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
    public boolean remove(int id) throws EntityNotExistsException {
        T entity = getEntityById(id);
        if (entity == null)
            throw new EntityNotExistsException("Entity not exists id: " + id);
        return remove(entity);
    }

    @Override
    public boolean remove(Entity entity) throws EntityNotExistsException {
        if (entity == null)
            throw new EntityNotExistsException();

        em.remove(entity);
        entity = getEntityById(entity.getId());
        if(entity != null) {
            return false;
        } else {
            return true;
        }
    }



}
