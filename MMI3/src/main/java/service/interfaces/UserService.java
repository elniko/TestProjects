package service.interfaces;

import entity.ProfileEntity;
import entity.UserEntity;
import exceptions.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nick on 22/01/2015.
 */
public interface UserService extends UserDetailsService {

    public int addUserWithRole(String name, String pass, String mail, String role) throws RoleNotExistException, UserAlreadyExistException;

    public boolean removeUsers(int id) throws EntityNotExistsException;

    public boolean removeNotAdmins(int id) throws EntityNotExistsException;

    public boolean removeUsers(UserEntity user) throws EntityNotExistsException;

    public int addUser(UserEntity user);

    public List<UserEntity> getAllUsers(int start, int count, String order, String role);

    public ProfileEntity getActiveProfile(String userName) throws BadProfileException, UserNotExistsException;

    public ProfileEntity getProfile(String userName, int id) throws BadProfileException;

    public ProfileEntity getProfile(int id);

    public int addProfile(String userName, String params) throws UserNotExistsException;

    public void editUserProfile(String userName, int id, String params) throws BadProfileException;

    public void editUserProfile(String userName, ProfileEntity profile) throws BadProfileException, UserNotExistsException;

    public void setActiveUserProfile(String userName, int id) throws UserNotExistsException, BadProfileException;

    public boolean removeProfile(int id) throws EntityNotExistsException;

    public boolean removeProfile(ProfileEntity profile) throws EntityNotExistsException;

    public boolean removeProfile(String userName, int id) throws BadProfileException, EntityNotExistsException;

    public boolean removeProfile(String userName, ProfileEntity profile) throws BadProfileException, EntityNotExistsException;

    public List<ProfileEntity> getProfiles(String userName) throws UserNotExistsException;

    public UserEntity getUser(int id) throws UserNotExistsException;

    public UserEntity getUser(String name) throws UserNotExistsException;




}
