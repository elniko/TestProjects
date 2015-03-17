package tools;

import entity.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tools.events.ProcessFinishEvent;
import tools.events.ProcessRunEvent;

/**
 * Created by Nick on 06/03/2015.
 */
@Component
@Scope("prototype")
public class RunnableProcessFactory implements  ApplicationEventPublisherAware {

    ProcessEntity processEntity;

    @Autowired
    ApplicationContext context;

    ApplicationEventPublisher publisher;


    public Runnable getRunnableProcess(ProcessEntity pe) {
        return new Runnable() {
            @Override
            public void run() {
                context.publishEvent(new ProcessRunEvent(pe));
                for(int i = 0 ; i < 10; i++) {
                    try {
                        System.out.println("Running " + pe.getType().getName() + " in thread " + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                context.publishEvent(new ProcessFinishEvent(pe));

            }
        };
    }


    public void run() {
        publisher.publishEvent(new ProcessRunEvent(this));

      //  Thread.currentThread().notifyAll();
    }

    public ProcessEntity getProcessEntity() {
        return processEntity;
    }

    public void setProcessEntity(ProcessEntity processEntity) {
        this.processEntity = processEntity;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }
}
