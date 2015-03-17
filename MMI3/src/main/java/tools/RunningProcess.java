package tools;

import entity.ProcessEntity;

/**
 * Created by Nick on 10/03/2015.
 */
public class RunningProcess implements Runnable {

    ProcessEntity processEntity;

    public ProcessEntity getProcessEntity() {
        return processEntity;
    }

    public void setProcessEntity(ProcessEntity processEntity) {
        this.processEntity = processEntity;
    }

    public RunningProcess(ProcessEntity pe ) {
        processEntity = pe;
    }

    @Override
    public void run() {
        for(int i= 0; i< 10; i++) {
            System.out.println("Hello: " + Thread.currentThread().getName()+ " " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
