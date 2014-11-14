package Main;

import entities.StudentEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.StudentService;
import spring.SpringConfig;

import java.util.Date;

/**
 * Created by stagiaire on 14/11/2014.
 */
public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);


        StudentEntity st = new StudentEntity();
        st.setFirstName("Ivan");
        st.setLastName("Petrov");
        st.setDate(new Date());
        st.setGroupId(1);



        StudentService ss =  context.getBean(StudentService.class);
        ss.addStudent(st);
    }
}
