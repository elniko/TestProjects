package service;

import dao.TestDao;
import entities.*;
import entities.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import javax.transaction.Transactional;

/**
 * Created by nike on 27/11/14.
 */
@org.springframework.stereotype.Service
public class TestServiceImpl  {

    @Autowired
    TestDao dao;

    //@Override
    @Transactional
    public void add(entities.Process entity) {
        dao.add(entity);
    }

    //@Override
    @Transactional
    public void edt(Process entity) {
        dao.edit(entity);
    }
}
