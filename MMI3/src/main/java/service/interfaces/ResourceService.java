package service.interfaces;

import entity.ResourceEntity;
import exceptions.BadResourceTypeException;
import exceptions.UserNotExistsException;

import java.util.List;

/**
 * Created by Nick on 29/01/2015.
 */
public interface ResourceService {

    public int addResource(byte[] resource, String ext , String type, String user) throws BadResourceTypeException, UserNotExistsException;

    public void removeResources(int[] ids);

    public int addResource(byte[] resource, String ext , int typeId, String user) throws BadResourceTypeException, UserNotExistsException;

    public List<ResourceEntity> getAllResources(int start, int count, String order);
}
