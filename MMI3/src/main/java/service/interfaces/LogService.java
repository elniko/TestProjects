package service.interfaces;

import entity.LogEntity;

import java.util.List;

/**
 * Created by Nick on 07/04/2015.
 */
public interface LogService {

    void addMessage(LogEntity message);

    List<LogEntity> getMessages(int count);

}
