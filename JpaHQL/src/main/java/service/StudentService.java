package service;

import dao.StudentDao;

import entities.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by stagiaire on 14/11/2014.
 */
@Service
public class StudentService  {
    @Autowired
    StudentDao dao;

    @Transactional
    public void addStudent(StudentEntity student) {
        dao.addStudent(student);
    }


}
