package dao;

import entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public User findByUserName(String name) {
       Query query = em.createQuery("SELECT u FROM  User u  WHERE name = :name");
       query.setParameter("name", name);
       List<User> users = query.getResultList();
       if(users.size() >0) {
           return users.get(0);
       } else {
           return users.get(0);
       }

    }

    public void addUser(User user) {
        em.persist(user);
    }



}
