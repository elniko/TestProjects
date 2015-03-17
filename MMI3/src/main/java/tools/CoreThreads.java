package tools;

import dao.interfaces.ProcessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nick on 12/03/2015.
 */
//@Component
public class CoreThreads {

    @Autowired
    ProcessDao processDao;

    @Value("${thread.active}")
    int activeThreads;

    int runningThreads;

    List<FutureTask> threads = new ArrayList<>();

    ThreadPoolExecutor pool;

    @PostConstruct
    public void init() {
        pool = new ThreadPoolExecutor(activeThreads, activeThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ToolsThreadFactory());
    }

   // @Scheduled(fixedDelay = 500)
    public void checkAvaliableThreads() {
       if(pool.getActiveCount() < activeThreads) {
            pool.submit(new RunningProcess(null));
       }
    }

    public void tearDown() {
        //pool.shutdown();
    }

}
