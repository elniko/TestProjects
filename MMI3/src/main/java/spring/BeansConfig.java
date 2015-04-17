package spring;

import entity.LogEntity;
import entity.ProcessEntity;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.support.TransactionTemplate;
import tools.log4j2.SqlQueryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import service.interfaces.LogService;
import tools.*;
import tools.threading.AbstractRunningProcess;
import tools.threading.ProcessThreadPoolExecutor;
import tools.ubp.XctRunningProcess;
import tools.ubp.XrtRunningProcess;
import tools.ubp.XvtRunningProcess;

import javax.transaction.TransactionManager;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

/**
 * Created by Nick on 24/03/2015.
 */
@Configuration

public class BeansConfig {

    @Autowired
    ApplicationContext context;




    @Bean
    @Autowired
    BeanPostProcessor loggerConfigurerBeanPostProcessor(LogService logService) {
        LoggerConfigurerBeanPostProcessor loggerConfigurerBeanPostProcessor = new LoggerConfigurerBeanPostProcessor();
        loggerConfigurerBeanPostProcessor.setExecutor((event) -> {
            if (event.getContextMap().containsKey("process_id")) {
                LogEntity e = new LogEntity();
                e.setMessage(event.getMessage().getFormattedMessage());
                ProcessEntity pe = new ProcessEntity();
                int process_id = Integer.valueOf(event.getContextMap().get("process_id"));
                int counter = Integer.valueOf(event.getContextMap().get("counter"));
                pe.setId(process_id);
                e.setLineCount(counter);
                e.setProcess(pe);
                logService.addMessage(e);
            }
        });
        return loggerConfigurerBeanPostProcessor;
    }

    @Bean
    public AppContextListener appContextListener() {
        return new AppContextListener();
    }

    //@Bean
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1);

        executor.setThreadFactory(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("th");
                return t;
            }
        });
        return executor;
    }

    @Bean
    ProcessThreadPoolExecutor processThreadPoolExecutor(@Value("${thread.active}") int activeThreads) {
        ProcessThreadPoolExecutor processThreadPoolExecutor = new ProcessThreadPoolExecutor(activeThreads) {
            @Override
            public AbstractRunningProcess getRunningProcess(ProcessEntity processEntity) {
                AbstractRunningProcess process;
                switch  (processEntity.getType().getName()) {
                    case "XCT":
                        process = context.getBean(XctRunningProcess.class);
                        break;
                    case "XRT":
                        process = context.getBean(XrtRunningProcess.class);
                        break;
                    case "XVT":
                    default:
                        process = context.getBean(XvtRunningProcess.class);
                        break;
                }
                process.setProcessEntity(processEntity);
                process.setCatchCallBack(context::publishEvent);
                return process;
            }
        };
        processThreadPoolExecutor.setEventPublisher(context::publishEvent);
        return processThreadPoolExecutor;
    }

    //@Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster caster = new SimpleApplicationEventMulticaster();
        caster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return caster;
    }
}
