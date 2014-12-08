package service;

import entities.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

/**
 * Created by stagiaire on 05/12/2014.
 */
@org.springframework.stereotype.Service
public class ProcessServiceImpl extends GenericService<ProcessEntity> implements ProcessService {

    @PostConstruct
    public void onConstruct() {
        setDao(ProcessEntity.class);
    }


    @Override
    @Transactional
    public void start(ProcessEntity entity) {

    }

    @Override
    public void stop(ProcessEntity entity) {

    }

    @Override
    public void stop(long id) {

    }
}
