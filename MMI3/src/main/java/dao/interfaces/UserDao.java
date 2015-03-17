package dao.interfaces;

import entity.ProfileEntity;
import entity.UserEntity;
import exceptions.BadProfileException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;

import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
public interface UserDao extends GenericDao<UserEntity> {

    public List<UserEntity> findByUserName(String name);

    public boolean checkResource(int resourcId, int userId);

    public List<UserEntity> findAllUserRole(int start, int count, String order, String role);

    public UserEntity getUserRole(int id) throws EntityNotExistsException;

    public ProfileEntity getProfile(String name, int id) throws BadProfileException;

    public ProfileEntity getProfile(int id);

    public void saveProfile(ProfileEntity profile);

    public void editProfile(ProfileEntity profile);

    public List<ProfileEntity> getProfiles(String name) throws UserNotExistsException;

    public boolean removeProfile(int id) throws EntityNotExistsException;

    public boolean removeProfile(ProfileEntity entity) throws EntityNotExistsException;
}
