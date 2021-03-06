package tools;


import org.springframework.beans.factory.annotation.Autowired;
import tools.threading.CoreThreads;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Nick on 12/03/2015.
 */
public class AppContextListener implements ServletContextListener {
    @Autowired
    CoreThreads coreThreads;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Hello");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        coreThreads.tearDown();
    }
}
