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
public class XrtRunningProcess extends AbstractRunningProcess {
    //XrtSDK xrt;
    public XrtRunningProcess() {
       // super(entity);
       // xrt = new XrtSDK();
    }

    @Override
    public void doWork() throws Exception {

    }
}
