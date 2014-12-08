package service;

import entities.LogEntity;
import entities.ProcessEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

/**
 * Created by stagiaire on 08/12/2014.
 */
@org.springframework.stereotype.Service
public class MessageGenerator {

    private int messageCount = 1000;

    private int sleepingTime = 100;

    private String messagePattern = "Message";

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogService logService;


    public void generate(ProcessEntity entity) {
        for(int i= 1; i<= messageCount; i++) {
            LogEntity lm = new LogEntity();
            String msg = messagePattern + " " + i;
            lm.setMessage(msg);
            lm.setLineCount(i);
            lm.setProcess(entity);
            logService.addStored(lm);
            log.info("Message added: " + msg );
        }
    }
}
