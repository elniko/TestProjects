package tools.threading;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.interfaces.ProcessService;

import tools.annotations.LoggerConfig;

/**
 * Created by Nick on 12/03/2015.
 */
@Component
public class CoreThreads {

    @LoggerConfig
    //@Autowired
    Logger logger;

    @Autowired
    ProcessService processService;

    @Autowired
    ProcessThreadPoolExecutor pool;

    //@Scheduled(fixedDelayString = "${scheduled.thread.period}")
    public void execute() throws InterruptedException {
       // logger.info("hello");
        if (!pool.hasFreeThreads()) {
            return;
        }
        pool.setPendings(processService.getPendings());
        pool.executePendings();
    }

    //@Scheduled(fixedDelayString = "${scheduled.dbcheck.period}")
    public void checkDatabase() {

    }

    public void tearDown() {
        pool.shutdown();
    }

}
