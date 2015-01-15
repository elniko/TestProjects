package dao;

import entity.UserEntity;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Repository
public class UserDao {



    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {

    }


    public UserEntity findByUserName(String name) {
       Query query = em.createQuery("SELECT u FROM  User u  WHERE u.name = :name");
       query.setParameter("name", name);
       List<UserEntity> users = query.getResultList();
       if(users.size() >0) {
           return users.get(0);
       } else {
           return users.get(0);
       }

    }

    public void addUser(UserEntity user) {
        em.persist(user);
    }


    public List<UserEntity> loadUsers() {
        Query query = em.createQuery("SELECT u FROM User u INNER JOIN FETCH u.role");
        List<UserEntity> users = query.getResultList();
        return users;
    }


}
