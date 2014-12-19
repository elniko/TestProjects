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

import java.io.Serializable;

/**
 * Created by stagiaire on 18/12/2014.
 */
@Plugin(name = "SqlQuery", category = "Core", elementType = "appender", printObject = true)
public class SqlQueryAppender extends AbstractAppender {

    private static DBaseProvider provider;


    protected SqlQueryAppender(String name, Filter filter, org.apache.logging.log4j.core.Layout<? extends Serializable> layout, String prov) {
        super(name, filter, layout);
        try {
            Class clx =  Class.forName(prov);
            provider = (DBaseProvider)clx.newInstance();
        } catch (Exception e) {
            LOGGER.error("", e);
        }


    }

    @PluginFactory
    public static SqlQueryAppender createAppender(@PluginAttribute("name") String name,
                                                  @PluginAttribute("provider") String provider,
                                                  @PluginElement("Filter") Filter filter,
                                                  @PluginElement("Layout") Layout layout
                                                  ) {

        if (name == null) {
            LOGGER.error("No name atribute selected");
            return null;
        }

        if(provider == null) {
            LOGGER.error("No provider selected");
            return null;
        }

        if(layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new SqlQueryAppender(name, filter, layout, provider);
    }


    @Override
    public void append(LogEvent logEvent) {
       provider.execQuery(logEvent);
    }
}
