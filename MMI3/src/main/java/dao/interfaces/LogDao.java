package dao.interfaces;

import entity.LogEntity;

/**
 * Created by stagiaire on 22/12/2014.
 */
public interface LogDao {
    void addLogMessage(LogEntity entity);

    void addLogMessageOld(LogEntity entity);
}
