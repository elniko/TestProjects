package dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by nike on 27/11/14.
 */
@Repository
public  class GenericDao<T> implements Dao<T> {

    @PersistenceContext
    EntityManager em;

    @Override
    public void add(T entity) {
        em.persist(entity);
    }

    @Override
    public void edit(T entity) {
        em.merge(entity);
    }
}
