package service;

import dao.UserDao;

import entity.UserRole;
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
import java.util.Set;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Service("authService")
public class UserService implements  UserDetailsService{


    @Autowired
    UserDao dao;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        entity.User user =  dao.findByUserName(s);
        return new User(user.getName(), user.getPassword(), createUserAutorities(user.getRole()));

    }


    @Transactional
    public entity.User loadByUserName(String s) {
      return dao.findByUserName(s);
    }

    @Transactional
    public void addUser(entity.User  user) {
        dao.addUser(user);
    }

    public List<GrantedAuthority> createUserAutorities(Set<UserRole> roles) {
        List<GrantedAuthority> auth =new  ArrayList<>();
        for(UserRole role : roles) {
            auth.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return auth;
    }
}
