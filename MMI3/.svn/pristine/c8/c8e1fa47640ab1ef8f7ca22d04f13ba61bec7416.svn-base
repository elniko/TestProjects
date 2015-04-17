package service.implimentations;

import dao.interfaces.*;
import entity.ProfileEntity;
import entity.RoleEntity;
import entity.UserEntity;
import exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import service.interfaces.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nick on 22/01/2015.
 */
@Service(value = "authService")
public class UserServiceImpl  implements UserService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder encoder;


    @Override
    @Transactional
    public int addUserWithRole(String name, String pass, String mail, String role) throws RoleNotExistException, UserAlreadyExistException {
        List<RoleEntity> roles = roleDao.findByRole(role);
        if(roles == null || roles.size() == 0 ){
            throw new RoleNotExistException(role);
        }
        List<UserEntity> users = userDao.findByUserName(name);
        if(users.size() != 0) {
            throw  new UserAlreadyExistException(name);
        }

        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(encodePassword(pass));
        user.setEmail(mail);
        user.setRole(roles.get(0));
        user.setEnabled(true);
        userDao.saveEntity(user);
        return user.getId();
    }


    @Override
    @Transactional
    public int addUser(UserEntity user) {
        user.setPassword(encodePassword(user.getPassword()));
        userDao.saveEntity(user);
        return user.getId();
    }


    @Override
    @Transactional
    public boolean removeUsers(int id) throws EntityNotExistsException {
            return userDao.remove(id);
    }

    @Override
    @Transactional
    public boolean removeUsers(UserEntity user) throws EntityNotExistsException {
            return userDao.remove(user);
    }

    private String encodePassword(String pass) {
        return encoder.encode(pass);
        //return pass;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) {
        List<UserEntity> users =  userDao.findByUserName(s);
        if (users.size() == 0) {
            throw new UsernameNotFoundException(s);
        }
        UserEntity user = users.get(0);

        return new User(user.getName(), user.getPassword(), createUserAutorities(user.getRole()));
    }

    @Override
    @Transactional
    public List<UserEntity> getAllUsers(int start, int count, String order, String role) {
        if(!role.equals("")) {
            return  userDao.findAllUserRole(start, count, order , role);
        } else {
            return (List<UserEntity>) userDao.getAllEntities(start, count, order);
        }
    }


    @Override
    @Transactional
    public boolean removeNotAdmins(int id) throws EntityNotExistsException {
        UserEntity ue = userDao.getUserRole(id);
        if (ue.getRole().getRole() == "ROLE_USER" || ue.getRole().getRole() == "ROLE_GUEST") {
            return removeUsers(ue);
        } else {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
        }
    }

    public List<GrantedAuthority> createUserAutorities(RoleEntity role) {
        List<GrantedAuthority> auth = new ArrayList<>();

        auth.add(new SimpleGrantedAuthority(role.getRole()));

        return auth;
    }

    @Override
    @Transactional
    public ProfileEntity getActiveProfile(String userName) throws BadProfileException, UserNotExistsException {
        List<ProfileEntity> profiles = userDao.getProfiles(userName);
        if(profiles.size() == 0) {
            throw new BadProfileException(String.format("User userName: %s have no any profile", userName));
        }
        for(ProfileEntity profile : profiles) {
            if (profile.isActive())
                return profile;
        }
        throw new BadProfileException(String.format("User userName: %s have no any active profile", userName));
    }

    @Override
    @Transactional
    public ProfileEntity getProfile(String userName, int id) throws BadProfileException {
        return userDao.getProfile(userName, id);
    }

    @Override
    @Transactional
    public int addProfile(String userName, String params) throws UserNotExistsException {
        List<UserEntity> userList = userDao.findByUserName(userName);
        if (userList.size() == 0) {
            throw new UserNotExistsException(userName);
        }
        ProfileEntity profile = new ProfileEntity();
        profile.setUser(userList.get(0));
        profile.setActive(false);
        profile.setParams(params);
        userDao.saveProfile(profile);
        return profile.getId();
    }

    @Override
    @Transactional
    public void editUserProfile(String userName, int id, String params) throws BadProfileException {
        ProfileEntity profile = userDao.getProfile(userName, id);
        profile.setParams(params);
        userDao.editProfile(profile);
    }

    @Override
    @Transactional
    public void editUserProfile(String userName, ProfileEntity profile) throws BadProfileException, UserNotExistsException {
        userDao.getProfile(userName, profile.getId());
        List<UserEntity> userList = userDao.findByUserName(userName);
        if (userList.size() == 0) {
            throw new UserNotExistsException(userName);
        }
        profile.setUser(userList.get(0));
        userDao.editProfile(profile);
    }

    @Override
    @Transactional
    public void setActiveUserProfile(String userName, int id) throws UserNotExistsException, BadProfileException {
        List<UserEntity> userList = userDao.findByUserName(userName);
        if(userList.size() == 0) {
            throw new UserNotExistsException(userName);
        }
        List<ProfileEntity> profiles = userDao.getProfiles(userName);
        if(profiles.size() == 0) {
            throw new BadProfileException(String.format("User userName: %s have no any profile", userName));
        }
        for(ProfileEntity profile : profiles) {
            if (profile.isActive()) {
                profile.setActive(false);
            }
        }
        UserEntity user = userList.get(0);
        for(ProfileEntity profile : profiles) {
            if (profile.getId() == id) {
                profile.setActive(true);
                user.setProfileList(profiles);
                userDao.updateEntity(user);
                return;
            }
        }
        throw new BadProfileException(String.format("User userName: %s have no any profile with Id: %s", userName, id));
    }

    @Override
    @Transactional
    public boolean removeProfile(String userName, int id) throws BadProfileException, EntityNotExistsException {
        ProfileEntity prof = userDao.getProfile(userName, id);

        return userDao.removeProfile(id);
    }

    @Override
    public boolean removeProfile(String userName, ProfileEntity profile) throws BadProfileException, EntityNotExistsException {
        ProfileEntity prof = userDao.getProfile(userName, profile.getId());
        return userDao.removeProfile(prof);
    }

    @Override
    @Transactional
    public boolean removeProfile(int id) throws EntityNotExistsException {
        return userDao.removeProfile(id);
    }

    @Override
    @Transactional
    public boolean removeProfile(ProfileEntity profile) throws EntityNotExistsException {
        return userDao.removeProfile(profile);
    }

    @Override
    @Transactional
    public ProfileEntity getProfile(int id) {
        return userDao.getProfile(id);
    }

    @Override
    @Transactional
    public List<ProfileEntity> getProfiles(String userName) throws UserNotExistsException {
        List<ProfileEntity> profiles = userDao.getProfiles(userName);
        return profiles;
    }

    @Override
    @Transactional
    public UserEntity getUser(int id) throws UserNotExistsException {
        UserEntity user = userDao.getEntityById(id);
        if (user == null) {
            throw new UserNotExistsException("id: " + id);
        }
        return user;
    }

    @Override
    @Transactional
    public UserEntity getUser(String name) throws UserNotExistsException {
        List<UserEntity> list =  userDao.findByUserName(name);
        if (list.size() == 0) {
            throw new UserNotExistsException(name);
        }
        return list.get(0);
    }
}
