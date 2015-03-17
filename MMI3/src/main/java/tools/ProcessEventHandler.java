package tools;

import entity.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import service.interfaces.ProcessService;
import tools.events.ProcessEvent;
import tools.events.ProcessRunEvent;

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
        ProcessEntity pe = (ProcessEntity) event.getSource();
        switch(event.getClass().getSimpleName()) {
            case "ProcessRunEvent":
                pe.setStartedAt(Calendar.getInstance());
                pe.setStatus(processService.getStatus("RUNNING"));
                processService.editProcess(pe);
                break;
            case "ProcessFinishEvent":
                pe.setEndedAt(Calendar.getInstance());
                pe.setStatus(processService.getStatus("FINISHED"));
                processService.editProcess(pe);
                break;
        }



    }
}
