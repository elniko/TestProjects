package service;

import dao.Dao;
import dao.GenericDao;
import dao.ProcessDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created by nike on 27/11/14.
 */
@org.springframework.stereotype.Service
public abstract class GenericService<T> implements Service<T>{



    @Autowired
    Dao<T> dao;

    @Override
    @Transactional
    public void add(T entity) {
          dao.add(entity);
    }

    @Override
    @Transactional
    public void edit(T entity) {
        dao.edit(entity);
    }
}
