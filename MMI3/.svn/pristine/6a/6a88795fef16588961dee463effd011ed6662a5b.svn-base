package log4j2;


import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Plugin(name = "SqlQuery", category = "Core", elementType = "appender", printObject = true)
public class SqlQueryAppender extends AbstractAppender {

    //private static DBaseProvider provider;

   // ApplicationContext ctx;

    Consumer<LogEvent> executor;

    public void setExecutor(Consumer<LogEvent> executor) {
        this.executor = executor;
    }



    protected SqlQueryAppender(String name, Filter filter, org.apache.logging.log4j.core.Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    //public void setCtx(ApplicationContext ctx) {
        //this.ctx = ctx;
        //provider.setContext(ctx);
    //}

    @PluginFactory
    public static SqlQueryAppender createAppender(@PluginAttribute("name") String name,
                                                 // @PluginAttribute("provider") String provider,
                                                  @PluginElement("Filter") Filter filter,
                                                  @PluginElement("Layout") Layout layout
                                                  ) {

        if (name == null) {
            LOGGER.error("No name atribute selected");
            return null;
        }

       // if(provider == null) {
         //   LOGGER.error("No provider selected");
           // return null;
        //}

        if(layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new SqlQueryAppender(name, filter, layout);
    }


    @Override
    public void append(LogEvent logEvent) {

        try {
            executor.accept(logEvent);
            //provider.execQuery(logEvent);
        } catch (Exception ex) {
            LOGGER.error("Error:", ex);
        }
    }
}
