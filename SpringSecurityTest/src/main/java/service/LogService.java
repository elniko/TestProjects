package service;

import dao.ILogDao;
import entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


/**
 * Created by stagiaire on 22/12/2014.
 */
@Service
public class LogService implements ILogService {

    @Autowired
    ILogDao dao;

    @Override
    @Transactional
    public void addLogMessage(LogEntity entity) {
        dao.addLogMessage(entity);
    }
}
