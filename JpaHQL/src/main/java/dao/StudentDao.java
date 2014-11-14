package dao;

import entities.StudentEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;

/**
 * Created by stagiaire on 14/11/2014.
 */
@Repository
public class StudentDao {

    @PersistenceContext
    private EntityManager em;

    public void addStudent(StudentEntity student) {

        em.persist(student);

    }


}
