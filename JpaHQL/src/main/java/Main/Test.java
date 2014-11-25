package Main;

import entities.Groupp;
import entities.StudentEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.GroupService;
import service.StudentService;
import spring.SpringConfig;

import java.util.Calendar;
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
        st.setDate(Calendar.getInstance());
       // st.setGroupId(1);


        Groupp grp = new Groupp();
        grp.setName("GROUP1");
        st.setGroup(grp);


        //GroupService gs = context.getBean(GroupService.class);
        //gs.addGroup(grp);


        StudentService ss =  context.getBean(StudentService.class);

        ss.addStudent(st);
    }
}
