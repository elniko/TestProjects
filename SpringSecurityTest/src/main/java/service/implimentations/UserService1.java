package service.implimentations;

import dao.implimentation.UserDao;

import entity.UserEntity;
import entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.interfaces.IUserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Service("authService")
public class UserService1 implements  UserDetailsService, IUserService {


    @Autowired
    UserDao dao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity user =  dao.findByUserName(s);
        return new User(user.getName(), user.getPassword(), createUserAutorities(user.getRole()));

    }


    @Transactional
    public UserEntity loadByUserName(String s) {
      return dao.findByUserName(s);
    }

    @Transactional
    public void addUser(UserEntity user) {
        dao.addUser(user);
    }

    @Override
    @Transactional
    public List<UserEntity> loadUsers() {
        return dao.loadUsers();
    }

    public List<GrantedAuthority> createUserAutorities(UserRoleEntity role) {
        List<GrantedAuthority> auth =new  ArrayList<>();

            auth.add(new SimpleGrantedAuthority(role.getRole()));

        return auth;
    }
}
