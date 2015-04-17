package tools.ubp;

import entity.ProcessEntity;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tools.threading.AbstractRunningProcess;

/**
 * Created by Nick on 30/03/2015.
 */
@Component
@Scope("prototype")
public class XvtRunningProcess extends AbstractRunningProcess {

    public XvtRunningProcess() {
        //super(entity);
    }

    @Override
    public void doWork() {

        int i = 0;
          while (i < 10 && !Thread.currentThread().isInterrupted()) {
            System.out.println("Hello: " + Thread.currentThread().getName() + " " + i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                break;
            }
              i++;

          }



    }
}
