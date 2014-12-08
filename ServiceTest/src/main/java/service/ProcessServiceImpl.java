package service;

import entities.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.transaction.Transactional;

/**
 * Created by stagiaire on 05/12/2014.
 */
@org.springframework.stereotype.Service
public class ProcessServiceImpl extends GenericService<ProcessEntity> implements ProcessService {

    @Autowired
    MessageGenerator generator;

    @Override
    @Transactional
    public void start(ProcessEntity entity) {
        //entity = edit(entity);
        generator.generate(entity);
    }

    @Override
    public void stop(ProcessEntity entity) {

    }

    @Override
    public void stop(long id) {

    }
}
