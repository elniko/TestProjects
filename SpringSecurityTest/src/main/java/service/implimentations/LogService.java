package service.implimentations;

import dao.interfaces.ILogDao;
import entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.ILogService;

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
