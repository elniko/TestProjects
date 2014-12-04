package Test;

import entities.ProcessEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.MessageLogger;
import service.ProcessService;
import spring.SpringConfig;
import testdao.TestDao;

/**
 * Created by nike on 27/11/14.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        ProcessEntity process = new ProcessEntity();
        process.setState("started");
        process.setType("xct");

       ProcessService ps = context.getBean(ProcessService.class);
        MessageLogger ms = context.getBean(MessageLogger.class);
        ms.setMessagesCount(20);
        ms.setProcessType("xct");
        ms.run();

        //TestDao ps = context.getBean(TestDao.class);
        ps.add(process);

    }
}
