package dao.implimentation;


import dao.interfaces.UserDao;
import entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<UserEntity> implements UserDao {

    @PostConstruct
    public void init() {
        setClass(UserEntity.class);
    }

    @Override
    public List<UserEntity> findByUserName(String name) {

        Query q = em.createQuery("from " + UserEntity.class.getName() + " u where u.name = :name");
        q.setParameter("name", name);
        return q.getResultList();
    }
}
