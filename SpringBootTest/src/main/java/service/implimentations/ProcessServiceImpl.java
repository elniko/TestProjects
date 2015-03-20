package service.implimentations;

import dao.interfaces.ProcessDao;
import dao.interfaces.ProcessStatusDao;
import dao.interfaces.ProcessTypeDao;
import dao.interfaces.UserDao;
import entity.ProcessEntity;
import entity.ProcessStatusEntity;
import entity.ProcessTypeEntity;
import entity.UserEntity;
import exceptions.BadProcessTypeException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.ProcessService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Nick on 05/03/2015.
 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService{

    @Autowired
    ProcessDao processDao;

    @Autowired
    ProcessStatusDao processStatusDao;

    @Autowired
    ProcessTypeDao processTypeDao;

    @Autowired
    UserDao userDao;

    //@Autowired
   // ProcessRunner processRunner;

    @Override
    public int schedule(String type, String user) throws BadProcessTypeException, UserNotExistsException {
        List<ProcessTypeEntity> list = (List<ProcessTypeEntity>) processTypeDao.getAllByCondition("t", String.format("t.name='%s'", type), 0, 0, "");
        if(list.size() == 0) {
            throw new BadProcessTypeException(type);
        }
        List<UserEntity> users = userDao.findByUserName(user);
        if(users.size() == 0) {
            throw new UserNotExistsException(user);
        }
        List<ProcessStatusEntity> statuses = (List<ProcessStatusEntity>) processStatusDao.getAllByCondition("s", "s.name='SCHEDULED'", 0, 0, "");
        ProcessEntity pe = new ProcessEntity();
        pe.setUser(users.get(0));
        pe.setType(list.get(0));
        pe.setStatus(statuses.get(0));
        processDao.saveEntity(pe);
        return pe.getId();
    }

    @Override
    public boolean remove(ProcessEntity pe) throws EntityNotExistsException {
        return processDao.remove(pe);
    }

    @Override
    public boolean remove(int id) throws EntityNotExistsException {
        return processDao.remove(id);
    }

    @Override
    public void runScheduled(int id) throws EntityNotExistsException {
        ProcessEntity process = processDao.getEntityById(id);
        if(process == null) {
            throw new EntityNotExistsException("Process entity id: " + id + " not exists");
        }
       // List<ProcessStatusEntity> statuses = (List<ProcessStatusEntity>) processStatusDao.getAllByCondition("s", "s.name='PENDING'", 0, 0, "");
        ProcessStatusEntity status = getStatus("PENDING");
        process.setStatus(status);
        processDao.updateEntity(process);
    }

    @Override
    public ProcessStatusEntity getStatus(String status) {
        List<ProcessStatusEntity> list = (List<ProcessStatusEntity>) processStatusDao.getAllByCondition("s", String.format("s.name='%s'", status), 0, 0, "");
        return list.get(0);
    }


    @Override
    public void editProcess(ProcessEntity pe) {
        processDao.updateEntity(pe);
    }

    @Override
    public int runScheduled(ProcessEntity pe) {
       // processDao.
        return 0;
    }

    @Override
    public int[] runAllScheduled() {
        return new int[0];
    }

    @Override
    public int getActiveProcess() {
        return 0;
    }

    @Override
    public ProcessEntity[] getProcess(String type, int start, int count, String order) {
        return new ProcessEntity[0];
    }

    @Override
    public ProcessEntity[] getAllProcess(int start, int count, String order) {
        return new ProcessEntity[0];
    }

//    @Override
//    public ProcessRunner getProcessRunner() {
//        return null;
//    }


    //@Override

}
