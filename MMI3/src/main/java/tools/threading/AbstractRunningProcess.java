package tools.threading;

import aspects.LogAspect;
import entity.ProcessEntity;
import exceptions.LogAspectNotAppliedException;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tools.annotations.LoggerConfig;
import tools.events.ProcessErrorEvent;
import tools.events.ProcessRunEvent;
import ubpartner.logmanagement.CustomLevel;
import ubpartner.logmanagement.LogManagement;

import java.util.concurrent.RunnableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by Nick on 10/03/2015.
 */

public abstract class AbstractRunningProcess implements Runnable {


    protected Logger logger;

    public AbstractRunningProcess() {
        //processEntity = entity;
        LogAspect.setLogInterceptorExecutor(this::interceptedLog);
    }



    protected ProcessEntity processEntity = null;

    Thread activeThread = null;

    protected boolean stopped = false;

    protected boolean error = false;

    protected Exception exception;

    protected int returnCode = 0;

    //public abstract void publishEvent(ApplicationEvent event);

    public abstract void doWork() throws Exception;

    Consumer<ApplicationEvent> consumer;

    public Exception getException() {
        return exception;
    }

    public boolean isError() {
        return error;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public Thread getActiveThread() {
        return activeThread;
    }

    public void setActiveThread(Thread activeThread) {
        this.activeThread = activeThread;
    }

    public ProcessEntity getProcessEntity () {
        return processEntity;
    }

    public void setProcessEntity(ProcessEntity processEntity) {
        this.processEntity = processEntity;
    }

    public void setCatchCallBack(Consumer<ApplicationEvent> consumer) {
        this.consumer = consumer;
    }


    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    private void interceptedLog(Object level, String msg, boolean flag) {
        CustomLevel customLevel = (CustomLevel) level;
        String strCounter = ThreadContext.get("counter");
        Integer intCounter = Integer.valueOf(strCounter);
        intCounter++;
        ThreadContext.put("counter", String.valueOf(intCounter));
        switch(customLevel.getLevelString()) {
            case "TRACE":
                logger.trace(msg);
                break;
            case "DEBUG":
                logger.debug(msg);
                 break;
            case "INFO":
                logger.info(msg);
                break;
            case "WARN":
            case "VWARN":
                if (returnCode < 1) {
                    returnCode = 1;
                }
                logger.warn(msg);
                break;
            case "ERROR":
                if (returnCode < 2) {
                   returnCode = 2;
                }
                logger.error(msg);
                break;
            case "FATAL":
                if(returnCode < 3) {
                    returnCode = 3;
                }
               logger.fatal(msg);
                break;
        }
    }

    @Override
    public void run() {
        try {
            if(LogManagement.getReturnCode() == 100) {
               logger.info("Aspects are avaliable");
            } else {
               throw new LogAspectNotAppliedException();
            }
            ThreadContext.put("process_id", String.valueOf(processEntity.getId()));
            ThreadContext.put("counter", "0");
            doWork();
        } catch (Exception e) {
            error = true;
            exception = e;
            if (consumer != null) {
                consumer.accept(new ProcessErrorEvent(this));
            }
        }
        finally {
            ThreadContext.clearMap();
        }
    }
}
