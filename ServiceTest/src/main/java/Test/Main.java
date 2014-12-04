package Test;

import entities.*;
import entities.Process;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.ProcessService;
import service.TestService;
import spring.SpringConfig;

/**
 * Created by nike on 27/11/14.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        entities.Process process = new Process();
        process.setState("started");
        process.setType("xct");

       TestService ps = context.getBean(TestService.class);
        //TestService ps = context.getBean(TestService.class);
        ps.add(process);

    }
}
