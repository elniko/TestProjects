package log4j2;

import org.apache.logging.log4j.core.LogEvent;
import org.springframework.context.ApplicationContext;

/**
 * Created by stagiaire on 18/12/2014.
 */
public interface DBaseProvider {
    public void execQuery(LogEvent event);

    public void setContext(ApplicationContext ctx);

}
