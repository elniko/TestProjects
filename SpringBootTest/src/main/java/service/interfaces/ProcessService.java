package service.interfaces;

import entity.ProcessEntity;
import entity.ProcessStatusEntity;
import exceptions.BadProcessTypeException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;
//import tools.ProcessRunner;

/**
 * Created by Nick on 05/03/2015.
 */
public interface ProcessService {

    public int schedule(String type, String user) throws BadProcessTypeException, UserNotExistsException;

    public boolean remove(ProcessEntity pe) throws EntityNotExistsException;

    public boolean remove(int id) throws EntityNotExistsException;

    public void runScheduled(int id) throws EntityNotExistsException;

    public int runScheduled(ProcessEntity pe);

    public int[] runAllScheduled();

    public int getActiveProcess();

    public ProcessEntity[] getProcess(String type, int start, int count, String order);

    public ProcessEntity[] getAllProcess(int start, int count, String order);

    //public ProcessRunner getProcessRunner() ;

    public void editProcess(ProcessEntity pe) ;

    public ProcessStatusEntity getStatus(String status);
}

