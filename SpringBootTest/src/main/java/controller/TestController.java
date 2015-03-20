package controller;

import entity.UserEntity;
import exceptions.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.UserService;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 20/03/2015.
 */

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"service", "dao", "controller"})
@EntityScan("entity")
public class TestController   {


    @Autowired
    UserService userService;

    @Autowired
    LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @PostConstruct
    public void init() {
        entityManagerFactory.setPackagesToScan("entity");

    }

    @RequestMapping("/user")
    public UserEntity test1() throws UserNotExistsException {
        return userService.getUser(1);
    }
    @RequestMapping("/user/t1")
    public String test2() {
        return "user/t1";
    }
    @RequestMapping("/user/t2")
    public String test3() {
        return "user/t2";
    }
    @RequestMapping("/role")
    public String test4() {
        return "Role";
    }
    @RequestMapping("/role/r1")
    public String test5() {
        return "role/r1";
    }
    @RequestMapping("/role/r2")
    public String test6() {
        return "role/r2";
    }
    @RequestMapping("/role/r3")
    public String test7() {
        return "role/r3";
    }

    public static void main(String[] args) {
        SpringApplication.run(TestController.class, "--debug");
    }


    @Bean(name ="passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new StandardPasswordEncoder();
    }
}
