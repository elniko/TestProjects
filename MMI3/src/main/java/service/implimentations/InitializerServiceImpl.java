package service.implimentations;

import Constants.*;
import dao.interfaces.*;
import entity.*;
import exceptions.EntityNotExistsException;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.interfaces.InitializerService;
import service.interfaces.UserService;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
@Service
public class InitializerServiceImpl implements InitializerService {

    @Value("${app.superadmin.name}")
    String superName;

    @Value("${app.superadmin.pass}")
    String superPassword;

    @Value("${app.superadmin.mail}")
    String superMail;

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

    @Autowired
    UserService userService;

    @Autowired
    private PropertyDao propertyDao;


    private void clearAll(GenericDao<? extends Entity> dao) throws EntityNotExistsException {
       Collection<? extends Entity> entities  = dao.getAllEntities();
       for(Entity entity : entities) {
           dao.remove(entity);
       }
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
    public void clearRole() throws EntityNotExistsException {
        clearAll(roleDao);
    }

    @Override
    @Transactional
    public void clearProcessType() throws EntityNotExistsException {
        clearAll(procTypeDao);
    }

    @Override
    @Transactional
    public void clearProcessStatus() throws EntityNotExistsException {
        clearAll(procStatusDao);
    }

    @Override
    @Transactional
    public void clearResourceType() throws EntityNotExistsException {
        clearAll(resTypeDao);
    }

    @Override
    @Transactional
    public void clearPropertyType() throws EntityNotExistsException {
        clearAll(propTypeDao);
    }

    @Override
    public void clearUsers() {

    }

    @Override
    public void initProperties(Object[] props) {
        for(Object prop : props) {
            Property property = (Property) prop;
            PropertyEntity pe = new PropertyEntity();
            pe.setName(property.name());
            pe.setScope(property.scopeStr());
            List<PropertyTypeEntity> pts = (List<PropertyTypeEntity>) propTypeDao.getAllByCondition("p", "p.name='"+ property.type().name()+"'", 0, 0, "");
            pe.setType(pts.get(0));
            propertyDao.saveEntity(pe);
        }
    }

    @Override
    public void initTypes(boolean clearAll) throws EntityNotExistsException, RoleNotExistException, UserAlreadyExistException {
        if(clearAll) {
            clearRole();
            clearProcessStatus();
            clearProcessType();
            clearPropertyType();
            //initService.clearResourceType();
        }
        userService.addUserWithRole(superName, superPassword, superMail, "ROLE_SUPERADMIN");
        initRoles(Role.values());
        initProcessStatuses(ProcessStatus.values());
        initProcessTypes(ProcessType.values());
        initProperties(Property.values());
        //initService.initResourceTypes(Files.values());
        initPropertyTypes(PropertyType.values());
    }

}
