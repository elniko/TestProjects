package dao;

import entities.*;
import entities.Process;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by nike on 27/11/14.
 */
@Repository
public class TestDaoImpl implements TestDao{
     @PersistenceContext
     EntityManager em;

    @Override
    public void add(entities.Process entity) {
        em.persist(entity);
    }

    @Override
    public void edit(Process entity) {
        em.merge(entity);
    }
}
