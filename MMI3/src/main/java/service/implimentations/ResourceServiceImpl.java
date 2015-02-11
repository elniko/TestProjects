package service.implimentations;

import dao.interfaces.ResourceDao;
import dao.interfaces.ResourceTypeDao;
import dao.interfaces.UserDao;
import entity.ResourceEntity;
import entity.ResourceTypeEntity;
import entity.UserEntity;
import exceptions.BadResourceTypeException;
import exceptions.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.ResourceService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Nick on 29/01/2015.
 */
@Service
public class ResourceServiceImpl implements ResourceService{

    @Autowired
    ResourceDao resourceDao;

    @Autowired
    ResourceTypeDao resourceTypeDao;

    @Autowired
    UserDao userDao;

    @Transactional
    @Override
    public int addResource(byte[] resource, String ext ,String type, String user) throws BadResourceTypeException, UserNotExistsException {


        List<UserEntity> users = userDao.findByUserName(user);
        if(users.size() == 0) {
            throw new UserNotExistsException(user);
        }

        ResourceEntity entity = new ResourceEntity();
        entity.setResourceExt(ext);
       // entity.setType(types.get(0));
        entity.setUser(users.get(0));
        entity.setResource(resource);
        resourceDao.saveEntity(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public int addResource(byte[] resource, String ext, int typeId, String user) throws BadResourceTypeException, UserNotExistsException {

        List<UserEntity> users = userDao.findByUserName(user);
        if(users.size() == 0) {
            throw new UserNotExistsException(user);
        }
        ResourceEntity entity = new ResourceEntity();
        entity.setUser(users.get(0));
        //entity.setType(type);
        entity.setResource(resource);
        entity.setResourceExt(ext);
        resourceDao.saveEntity(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public List<ResourceEntity> getAllResources(String user, int start, int count, String order) throws UserNotExistsException {
        List<UserEntity> users = userDao.findByUserName(user);
        if(users.size() == 0) {
            throw new UserNotExistsException(user);
        }
        List<ResourceEntity> res = (List<ResourceEntity>) resourceDao.getAllByUserAndCondition("r", users.get(0), "", start,count, order);

        return res;
    }


    @Override
    @Transactional
    public void removeResources(int[] ids) {
        resourceDao.removeResources(ids);
    }

}
