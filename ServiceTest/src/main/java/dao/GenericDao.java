package dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by nike on 27/11/14.
 */
@Repository
public  class GenericDao<T>  implements Dao<T> {

    @PersistenceContext
    EntityManager em;

    @Override
    public void add(T entity) {
        em.persist(entity);

    }

    @Override
    public T edit(T entity) {
        return em.merge(entity);
    }

    @Override
    public T findById(long id) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    public EntityManager getEntityManager(){
        return em;
    }
}
