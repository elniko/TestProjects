package service.implimentations;

import dao.interfaces.LogDao;
import entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.LogService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Nick on 07/04/2015.
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    LogDao logDao;

    @Override
    public void addMessage(LogEntity message) {
        //System.out.println("TO BD" + message.getMessage() +" --" + message.getProcess().getId() + " number: " + message.getLineCount() );
        logDao.addLogMessageOld(message);
    }

    @Override
    public List<LogEntity> getMessages(int count) {
        return null;
    }
}
