package tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import tools.annotations.LoggerConfig;
import tools.log4j2.SqlQueryAppender;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;

/**
 * Created by Nick on 07/04/2015.
 */

public class LoggerConfigurerBeanPostProcessor implements BeanPostProcessor {

    public void setExecutor(Consumer<LogEvent> executor) {
        this.executor = executor;
    }

    Consumer<LogEvent> executor;

    private Logger getLogger(String category) {
        Logger logger = LogManager.getLogger(category);
        org.apache.logging.log4j.core.Logger coreLogger = (org.apache.logging.log4j.core.Logger) logger;
        org.apache.logging.log4j.core.config.Configuration config = coreLogger.getContext().getConfiguration();
        SqlQueryAppender appender = (SqlQueryAppender) config.getAppender("QueryAppender");
        appender.setExecutor(executor);
        coreLogger.addAppender(appender);
        return logger;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields =  o.getClass().getDeclaredFields();
        for(Field field : fields) {
            if(field.isAnnotationPresent(LoggerConfig.class)) {
                field.setAccessible(true);
                Logger logger = getLogger(o.getClass().getName());//LogManager.getLogger(o.getClass().getName());
                ReflectionUtils.setField(field, o, logger);
                break;
            }
        }

        Method[] methods = o.getClass().getDeclaredMethods();
        for(Method method : methods) {
            if(method.isAnnotationPresent(LoggerConfig.class)) {
                method.setAccessible(true);
                ReflectionUtils.invokeMethod(method, o, getLogger(o.getClass().getName()));
            }
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
