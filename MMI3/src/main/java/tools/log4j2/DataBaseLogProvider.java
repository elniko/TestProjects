package tools.log4j2;

import org.apache.logging.log4j.core.LogEvent;

/**
 * Created by stagiaire on 18/12/2014.
 */
@FunctionalInterface
public interface DataBaseLogProvider {
    public void execQuery(LogEvent event);



}
