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
    public int addResource(byte[] resource, String type, String user) throws BadResourceTypeException, UserNotExistsException {
        List<ResourceTypeEntity> types = resourceTypeDao.findByName(type);
        if(types.size() == 0) {
            throw new BadResourceTypeException(type);
        }

        List<UserEntity> users = userDao.findByUserName(user);
        if(users.size() == 0) {
            throw new UserNotExistsException(user);
        }

        ResourceEntity entity = new ResourceEntity();
        entity.setType(types.get(0));
        entity.setUser(users.get(0));
        entity.setResource(resource);
        resourceDao.saveEntity(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public int addResource(byte[] resource, int typeId, int userId) throws BadResourceTypeException, UserNotExistsException {
         ResourceTypeEntity type = resourceTypeDao.getEntityById(typeId);
         if(type == null) {
             throw new BadResourceTypeException("ID: " + typeId);
         }
         UserEntity user = userDao.getEntityById(userId);
        if(user == null) {
            throw new UserNotExistsException("ID: " + userId);
        }
        ResourceEntity entity = new ResourceEntity();
        entity.setUser(user);
        entity.setType(type);
        entity.setResource(resource);
        resourceDao.saveEntity(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public List<ResourceEntity> getAllResources(int start, int count, String order) {
        return (List<ResourceEntity>) resourceDao.getAllEntities(start, count, order);
    }


    @Override
    @Transactional
    public void removeResources(int[] ids) {
        resourceDao.removeResources(ids);
    }

}
