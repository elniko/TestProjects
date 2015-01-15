package service;

import entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by stagiaire on 16/12/2014.
 */
public interface IUserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String s) ;

    public UserEntity loadByUserName(String s);

    public void addUser(UserEntity user);

    public List<UserEntity> loadUsers();

}
