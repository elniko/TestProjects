package service.implimentations;

import dao.implimentation.UserDao1;

import entity.RoleEntity;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stagiaire on 16/12/2014.
 */

public class UserService1 implements  UserDetailsService {



    UserDao1 dao;

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


    @Transactional
    public List<UserEntity> loadUsers() {
        return dao.loadUsers();
    }

    public List<GrantedAuthority> createUserAutorities(RoleEntity role) {
        List<GrantedAuthority> auth =new  ArrayList<>();

            auth.add(new SimpleGrantedAuthority(role.getRole()));

        return auth;
    }
}
