package service;

import entity.ProcessEntity;
import entity.UserEntity;
import exceptions.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import service.interfaces.ProcessService;
import service.interfaces.UserService;
import spring.SpringConfig;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Nick on 06/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestProcessService {

    static int userId;

    @Autowired
    ProcessService processService;

     @Autowired
     UserService userService;

    static UserService us;


@PostConstruct
public void construct() throws RoleNotExistException, UserAlreadyExistException {
    us = userService;
   // userId = userService.addUserWithRole("user", "password", "mail@mail.com", "ROLE_USER");
}

    @BeforeClass
    public static void init() throws RoleNotExistException, UserAlreadyExistException {


    }

    @AfterClass
    public static void tearDown() throws EntityNotExistsException {
        //us.removeUsers(userId);
    }


   //@Test
    public void test001ScheduleEntity() throws UserNotExistsException, RoleNotExistException, UserAlreadyExistException, BadProcessTypeException {
        Integer res = processService.schedule("XVT","user");
        Assert.isInstanceOf(Integer.class, res);
    }

    @Test
    public void test002RunScheduled() throws EntityNotExistsException, InterruptedException {
        processService.runScheduled(12);

//        processService.runScheduled(10);
//        processService.runScheduled(11);

    }

}
