package AsyncServlet;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Nick on 05/03/2015.
 */

public class AsyncRequestProcessor implements Runnable {
    private AsyncContext asyncContext;
    private int secs;

    public AsyncRequestProcessor() {
    }

    public AsyncRequestProcessor(AsyncContext asyncCtx, int secs) {
        this.asyncContext = asyncCtx;
        this.secs = secs;
    }

    @Override
    public void run() {
        System.out.println("Async Supported? " + asyncContext.getRequest().isAsyncSupported());
        longProcessing(secs);
        System.out.println("Finish");
        try {
            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("Processing done for " + secs + " milliseconds!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //complete the processing
        asyncContext.complete();
    }

    private void longProcessing(int secs) {
        // wait for given time before finishing
        try {
            Thread.sleep(secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
