package tools;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Nick on 09/03/2015.
 */

public class ToolsThreadFactory implements ThreadFactory {


    String threadName = "";

    public ToolsThreadFactory() {

    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
