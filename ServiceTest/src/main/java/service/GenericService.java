package service;

import dao.Dao;
import dao.GenericDao;
import dao.ProcessDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by nike on 27/11/14.
 */
@org.springframework.stereotype.Service
public abstract class GenericService<T> implements Service<T>{



    @Autowired
    GenericDao<T> dao;

    public void setDao( Class<T> clazz ){
        dao.setClazz(clazz);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void add(T entity) {
          dao.add(entity);
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public T edit(T entity) {
       return dao.edit(entity);
    }

    @Override
    @Transactional
    public T findById(long id){
        return dao.findById(id);
    };

    @Override
    @Transactional
    public List<T> getAll() {
        //TODO
        return null;
    }

    public Dao<T> getDao(){
        return dao;
    }

}
