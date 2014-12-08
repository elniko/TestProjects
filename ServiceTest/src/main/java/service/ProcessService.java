package service;


import dao.Dao;
import entities.ProcessEntity;

/**
 * Created by stagiaire on 04/12/2014.
 */
public interface ProcessService extends Service<ProcessEntity>{

    public void start(ProcessEntity entity);

    public void stop(ProcessEntity entity);

    public void stop(long id);

    public Dao<ProcessEntity> getDao();

    @Override
    public String sayHello() {
        return "Hello bean";
    }
}
