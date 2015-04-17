package tools.threading;

import entity.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import service.interfaces.ProcessService;
import tools.events.ProcessEvent;

import java.util.Calendar;

/**
 * Created by Nick on 09/03/2015.
 */
@Component
public class ProcessEventHandler implements ApplicationListener<ProcessEvent> {

    @Autowired
    ProcessService processService;

    @Override
    public void onApplicationEvent(ProcessEvent event) {
        AbstractRunningProcess rp = (AbstractRunningProcess) event.getSource();
        ProcessEntity pe = rp.getProcessEntity();
        switch(event.getClass().getSimpleName()) {
            case "ProcessRunEvent":
                System.out.println(Thread.currentThread().getName() + " "  + "RUNNING for User: " + rp.getProcessEntity().getUser().getName()+
                " ProcessEntity: " + rp.getProcessEntity().getId());
                pe.setStartedAt(Calendar.getInstance());
                pe.setStatus(processService.getStatus("RUNNING"));
                processService.editProcess(pe);
                break;
            case "ProcessFinishEvent":
                if (rp.isStopped() || rp.isError()) break;
                pe.setEndedAt(Calendar.getInstance());
                int returnCode = rp.getReturnCode();
                if (returnCode <= 1) {
                    pe.setStatus(processService.getStatus("FINISHED"));
                } else {
                    pe.setStatus(processService.getStatus("ERROR"));
                }
                processService.editProcess(pe);
                System.out.println(Thread.currentThread().getName() + " " + "FINISHED for User: " + rp.getProcessEntity().getUser().getName() +
                        " ProcessEntity: " + rp.getProcessEntity().getId());
                break;
            case "ProcessStopEvent":
                System.out.println(Thread.currentThread().getName() + " "  + "STOPPED for User: " + rp.getProcessEntity().getUser().getName()+
                        " ProcessEntity: " + rp.getProcessEntity().getId());
                pe.setEndedAt(Calendar.getInstance());
                pe.setStatus(processService.getStatus("STOPPED"));
                processService.editProcess(pe);
                break;
            case "ProcessErrorEvent":
                System.out.println(Thread.currentThread().getName() + " "  + "Error for User: " + rp.getProcessEntity().getUser().getName()+
                        " ProcessEntity: " + rp.getProcessEntity().getId());
                pe.setEndedAt(Calendar.getInstance());
                pe.setStatus(processService.getStatus("ERROR"));
                processService.editProcess(pe);
                break;
            case "ProcessCreateEvent":

                break;
        }



    }
}
