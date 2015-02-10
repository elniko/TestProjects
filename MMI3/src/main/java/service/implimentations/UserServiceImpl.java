package service.implimentations;

import dao.interfaces.*;
import entity.RoleEntity;
import entity.UserEntity;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.interfaces.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
    public void addUserWithRole(String name, String pass, String mail, String role) throws RoleNotExistException, UserAlreadyExistException {
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
    }


    @Override
    @Transactional
    public void addUser(UserEntity user) {
        user.setPassword(encodePassword(user.getPassword()));
        userDao.saveEntity(user);
    }


    @Override
    public void removeUsers(int[] ids) {
        for(int id : ids) {
            userDao.remove(id);
        }
    }

    @Override
    public void removeUsers(List<UserEntity> users) {
        users.stream().forEach((user) -> userDao.remove(user));
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

    public List<GrantedAuthority> createUserAutorities(RoleEntity role) {
        List<GrantedAuthority> auth = new ArrayList<>();

        auth.add(new SimpleGrantedAuthority(role.getRole()));

        return auth;
    }


}
