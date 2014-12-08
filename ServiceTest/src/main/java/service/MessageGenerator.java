package service;

import entities.LogEntity;
import entities.ProcessEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.Calendar;

/**
 * Created by stagiaire on 08/12/2014.
 */
@org.springframework.stereotype.Service
@Scope("prototype")
public class MessageGenerator extends Thread{

    private int messageCount = 10000;

    private int sleepingTime = 100;

    private String messagePattern = "Message";

    Logger log = LoggerFactory.getLogger(this.getClass());

    private ProcessEntity entity;


    @Autowired
    LogService logService;

    @Autowired
    ProcessService processService;

    public void setProcessEntity(ProcessEntity entity) {
        this.entity = entity;
    }

    @Transactional(rollbackOn = {Exception.class})
    private void generate() {

        entity = processService.findById(id);
        if(entity.getState().equals("scheduled")) {
            entity.setState("runing");
            processService.edit(entity);
            for(int i= 1; i<= messageCount; i++) {
                LogEntity lm = new LogEntity();
                String msg = messagePattern + " " + i;
                lm.setMessage(msg);
                lm.setLineCount(i);
                lm.setProcess(entity);
                logService.addStored(lm);
                log.info("Message added: " + msg );
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            entity.setState("finished");
            entity.setEndedAt(Calendar.getInstance());
            processService.edit(entity);
        }

    }

    @Override
    public void run() {
         generate();
    }

    @PreDestroy
    public void onDestroy() {
        if(entity.getState().equals("running"))
           processService.edit(entity);
    }

    private int id;
    public void setId(int id) {
        this.id = id;
    }

}
