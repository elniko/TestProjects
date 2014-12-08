package service;

import dao.LogDao;
import entities.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

/**
 * Created by stagiaire on 05/12/2014.
 */
@org.springframework.stereotype.Service
public class LogServiceImpl extends GenericService<LogEntity> implements LogService {

    @Autowired
    LogDao dao;

    @Override
    public boolean addStored(LogEntity entity) {
        return dao.addStored(entity);
    }
}
