package log4j2;

import entity.LogEntity;
import entity.ProcessEntity;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Component
public class MyDbProvider implements DBaseProvider, ApplicationContextAware {


       ApplicationContext ctx;

    @Override
    public void execQuery(LogEvent event) {
//        ILogService logService = ctx.getBean(ILogService.class);
//        LogEntity e = new LogEntity();
//        e.setMessage(event.getMessage().getFormattedMessage());
//        e.setLineCount(Long.valueOf(event.getContextMap().get("line_count")));
//        ProcessEntity pe = new ProcessEntity();
//        pe.setId(Long.valueOf(event.getContextMap().get("process_id")));
//        e.setProcess(pe);
//        logService.addLogMessage(e);

    }


    public void setContext(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
}
