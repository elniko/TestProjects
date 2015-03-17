package tools;

import entity.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by Nick on 03/03/2015.
 */
@Component
public class ProcessRunner  {

    ThreadPoolExecutor pool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ToolsThreadFactory());
    @Autowired
    RunnableProcessFactory processFactory ;

    Map<String, Future> userFuture = new HashMap<>();

    public Future runProcess(ProcessEntity pe) {
        Runnable proc = processFactory.getRunnableProcess(pe);
        Future future =  pool.submit(proc);
        return future;
    }

    public ThreadPoolExecutor getPool() {
        return pool;
    }
}
