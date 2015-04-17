package tools.log4j2;

import entity.LogEntity;
import entity.ProcessEntity;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.interfaces.LogService;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Component
public class MyDbProvider  {

    @Autowired
    LogService logService;

    //@Override
    public void execQuery(LogEvent event) {
       // System.out.println("test" + event.getMessage());
          //LogService logService = ctx.getBean(ILogService.class);
        LogEntity e = new LogEntity();
        e.setMessage(event.getMessage().getFormattedMessage());
        //  e.setLineCount(Long.valueOf(event.getContextMap().get("line_count")));
        ProcessEntity pe = new ProcessEntity();
        pe.setId(Integer.valueOf(event.getContextMap().get("process_id")));
        e.setProcess(pe);
        logService.addMessage(e);

    }



}
