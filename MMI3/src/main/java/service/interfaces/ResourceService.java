package service.interfaces;

import entity.ResourceEntity;
import exceptions.BadOwnerException;
import exceptions.BadResourceTypeException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;

import java.util.List;

/**
 * Created by Nick on 29/01/2015.
 */
public interface ResourceService {

    public int addResource(byte[] resource, String filename, String ext , String type, String user) throws BadResourceTypeException, UserNotExistsException;

    public void removeResources(int[] ids);

    public int addResource(byte[] resource, String ext , int typeId, String user) throws BadResourceTypeException, UserNotExistsException;

    public List<ResourceEntity> getAllResources(String user, int start, int count, String order) throws UserNotExistsException;

    public boolean removeResource(int id) throws EntityNotExistsException;

    public boolean removeResource(int id, String name) throws EntityNotExistsException, UserNotExistsException, BadOwnerException;
}
