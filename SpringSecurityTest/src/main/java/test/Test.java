package test;

import entity.User;
import entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.IUserService;
import service.UserService;

/**
 * Created by stagiaire on 16/12/2014.
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(spring.SpringConfig.class);
        IUserService us =  ctx.getBean(IUserService.class);
        User user = new User();
        user.setName("admin");
        user.setEnabled(true);
        UserRole role = new UserRole();
        Logger log = LoggerFactory.getLogger("Log");
        log.info("hello");
       // us.addUser(user);

        user = us.loadByUserName("admin");



    }
}
