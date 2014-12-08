package service;

import entities.LogEntity;

/**
 * Created by stagiaire on 05/12/2014.
 */
public interface LogService extends Service<LogEntity> {
    public boolean addStored(LogEntity entity);
}
