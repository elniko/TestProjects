package service.implimentations;

import dao.interfaces.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.InitializerService;

import javax.persistence.Column;
import javax.transaction.Transactional;
import java.util.Collection;

/**
 * Created by Nick on 23/01/2015.
 */
@Service
public class InitializerServiceImpl implements InitializerService {


    boolean clearFlag = false;

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ResourceTypeDao resTypeDao;

    @Autowired
    ProcessStatusDao procStatusDao;

    @Autowired
    ProcessTypeDao procTypeDao;

    @Autowired
    PropertyTypeDao propTypeDao;


    public RoleDao getRoleDao() {
        return roleDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ResourceTypeDao getResTypeDao() {
        return resTypeDao;
    }

    public ProcessStatusDao getProcStatusDao() {
        return procStatusDao;
    }

    public ProcessTypeDao getProcTypeDao() {
        return procTypeDao;
    }

    public PropertyTypeDao getPropTypeDao() {
        return propTypeDao;
    }


   private void clearAll(GenericDao<? extends Entity> dao) {
       Collection<? extends Entity> entities  = dao.getAllEntities();
       entities.stream().forEach(dao::saveEntity);
    }


    @Override
    @Transactional
    public void initRoles(Object[] roles) {
        for(Object role : roles) {
            RoleEntity r = new RoleEntity();
            r.setRole(role.toString());
            roleDao.saveEntity(r);
        }
    }

    @Override
    @Transactional
    public void initResourceTypes(Object[] resTypes) {
        for(Object rt : resTypes) {
            ResourceTypeEntity rte = new ResourceTypeEntity();
            rte.setName(rt.toString());
            resTypeDao.saveEntity(rte);
        }
    }

    @Override
    @Transactional
    public void initProcessStatuses(Object[] procStatuses) {
        for(Object ps: procStatuses) {
            ProcessStatusEntity prs = new ProcessStatusEntity();
            prs.setName(ps.toString());
            procStatusDao.saveEntity(prs);
        }
    }

    @Override
    @Transactional
    public void initProcessTypes(Object[] procTypes) {
        for(Object ps: procTypes) {
            ProcessTypeEntity prt = new ProcessTypeEntity();
            prt.setName(ps.toString());
            procTypeDao.saveEntity(prt);
        }
    }

    @Override
    @Transactional
    public void initPropertyTypes(Object[] propTypes) {
        for(Object pt: propTypes) {
            PropertyTypeEntity prt = new PropertyTypeEntity();
            prt.setName(pt.toString());
            propTypeDao.saveEntity(prt);
        }
    }

    @Override
    @Transactional
    public void clearRole() {
        clearAll(roleDao);
    }

    @Override
    @Transactional
    public void clearProcessType() {
        clearAll(procTypeDao);
    }

    @Override
    @Transactional
    public void clearProcessStatus() {
        clearAll(procStatusDao);
    }

    @Override
    @Transactional
    public void clearResourceType() {
        clearAll(resTypeDao);
    }

    @Override
    @Transactional
    public void clearPropertyType() {
        clearAll(propTypeDao);
    }

    @Override
    public void clearUsers() {

    }


}
