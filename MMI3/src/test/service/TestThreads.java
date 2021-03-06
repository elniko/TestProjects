package service;

import entity.ProcessEntity;
import entity.ProcessTypeEntity;
import entity.UserEntity;
import exceptions.ProcessNotExistsException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.interfaces.ProcessService;
import spring.MmiBoot;
import tools.threading.CoreThreads;
import tools.threading.ProcessThreadPoolExecutor;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nick on 25/03/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MmiBoot.class)
@WebIntegrationTest
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Profile("test")
public class TestThreads {



    static List<ProcessEntity> procs = new ArrayList<>();

    @Autowired
    ConfigurableEnvironment env;

    @Autowired
    ProcessThreadPoolExecutor pool;

    @Autowired
    CoreThreads coreThreads;

    @BeforeClass
    public static void initClass() {
        ProcessTypeEntity typeXct = new ProcessTypeEntity();
        typeXct.setName("XCT");

        ProcessTypeEntity typeXrt = new ProcessTypeEntity();
        typeXrt.setName("XRT");

        UserEntity userA = new UserEntity();
        userA.setName("UserA");

        UserEntity userB = new UserEntity();
        userB.setName("UserB");

        UserEntity userC = new UserEntity();
        userC.setName("UserC");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd H:m:s");

        ProcessEntity pe1 = new ProcessEntity();
        pe1.setType(typeXct);
        pe1.setId(1);
        pe1.setUser(userA);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(1000);
        pe1.setWaitingAt(cal);
        procs.add(pe1);



        ProcessEntity pe3 = new ProcessEntity();
        pe3.setType(typeXct);
        pe3.setUser(userB);
        pe3.setId(2);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTimeInMillis(2000);
        pe3.setWaitingAt(cal3);
        procs.add(pe3);

        ProcessEntity pe2 = new ProcessEntity();
        pe2.setType(typeXct);
        pe2.setUser(userA);
        pe2.setId(3);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(1050);
        pe2.setWaitingAt(cal2);
        procs.add(pe2);

        ProcessEntity pe4 = new ProcessEntity();
        pe4.setType(typeXct);
        pe4.setUser(userC);
        pe4.setId(4);
        Calendar cal4 = Calendar.getInstance();
        cal4.setTimeInMillis(1500);
        pe4.setWaitingAt(cal4);
        procs.add(pe4);



    }

    @Before
    public void init() {
      MockitoAnnotations.initMocks(this);
    }

    //@Test
    public void test001Threads() throws InterruptedException, ProcessNotExistsException {
       pool.setPendings(procs);
      pool.executePendings();

        pool.setPendings(procs);
        pool.executePendings();

        pool.setPendings(procs);
        pool.executePendings();
//       Thread.sleep(1000);
//       pool.cancelThread("UserA",1);
//       Thread.sleep(1000);
//       pool.cancelThread("UserC",4);
       Thread.sleep(100000);





    }

    @Test
    public void  test002CoreThreadsTest() throws InterruptedException {
        coreThreads.execute();
        Thread.sleep(1000000);
    }

    @Component
    @Profile("test")
    public static class CoreThreadsTest extends CoreThreads{
        @Autowired
        ProcessService processService;

        @Autowired
        ProcessThreadPoolExecutor pool;
    }

}
