package tools.threading;

import entity.ProcessEntity;
import exceptions.ProcessNotExistsException;
import org.springframework.context.ApplicationEvent;
import tools.events.ProcessFinishEvent;
import tools.events.ProcessRunEvent;
import tools.events.ProcessStopEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by Nick on 24/03/2015.
 */
public abstract class ProcessThreadPoolExecutor extends ThreadPoolExecutor {

    int activeThreads;

    List<ProcessEntity> pendings = new ArrayList<>();

    Map<String, List<AbstractRunningProcess>> userProc = new ConcurrentHashMap<>();

    public abstract AbstractRunningProcess getRunningProcess(ProcessEntity processEntity);

    //public abstract void publishEvent(ApplicationEvent event);

    Consumer<ApplicationEvent> eventPublisher;

    public void setEventPublisher(Consumer<ApplicationEvent> eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        AbstractRunningProcess rp = (AbstractRunningProcess) r;
        eventPublisher.accept(new ProcessFinishEvent(rp));
        //publishEvent(new ProcessFinishEvent(rp));
        String user = rp.getProcessEntity().getUser().getName();
        int val = userProc.getOrDefault(user, new ArrayList<>()).size();
        if (val == 0) {
            userProc.remove(user);
        } else {
            userProc.compute(user, (key, value) -> {
                value.remove(rp);
                return value;
            });
        }
    }

    @Override
    protected synchronized void beforeExecute(Thread t, Runnable r) {
        AbstractRunningProcess rp = (AbstractRunningProcess) r;
        eventPublisher.accept(new ProcessRunEvent(rp));
        //publishEvent(new ProcessRunEvent(rp));
        String user = rp.getProcessEntity().getUser().getName();
        rp.setActiveThread(t);
        if (!userProc.containsKey(user)) {
            List<AbstractRunningProcess> list = new ArrayList<>();
            list.add(rp);
            userProc.put(user, list);
        } else {
            userProc.get(user).add(rp);
        }
        notifyAll();
    }


    public synchronized void executePendings() throws InterruptedException {
        while (hasFreeThreads() && pendings.size() > 0) {
            Collections.sort(pendings, (o1, o2) -> {
                int o1count = userProc.getOrDefault(o1.getUser().getName(), new ArrayList<>()).size();
                int o2count = userProc.getOrDefault(o2.getUser().getName(), new ArrayList<>()).size();
                if (o1count > o2count) {
                    return 1;
                } else if (o1count < o2count) {
                    return -1;
                } else {
                    if (o1.getWaitingAt().after(o2.getWaitingAt())) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            super.execute(getRunningProcess(pendings.get(0)));
            wait(1000);
            pendings.remove(0);
        }
    }

    public Map<String, List<AbstractRunningProcess>> getUserProc() {
        return userProc;
    }

    static int tCount = 0;

    public ProcessThreadPoolExecutor(int activeThreads) {
        super(activeThreads, activeThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), (r) -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("MMIWorkingThread-" + tCount);
            tCount++;
            return t;
        });
        //Executors.newCachedThreadPool()
        this.activeThreads = activeThreads;
    }

    public void cancelThread(String user, int pid) throws ProcessNotExistsException {
        if (!userProc.containsKey(user)) throw new ProcessNotExistsException(user, pid);
        for (AbstractRunningProcess rp : userProc.get(user)) {
            if (rp.getProcessEntity().getId() == pid) {
                rp.activeThread.interrupt();
                rp.setStopped(true);
                eventPublisher.accept(new ProcessStopEvent(rp));
                //publishEvent(new ProcessStopEvent(rp));
                return;
            }
        }
        throw new ProcessNotExistsException(user, pid);
    }

    public boolean hasFreeThreads() {
        if (getActiveCount() < activeThreads) return true;
        return false;
    }

    public void setPendings(List<ProcessEntity> pendings) {
        this.pendings = pendings;
    }

    public List<ProcessEntity> getPendings() {
        return pendings;
    }
}
