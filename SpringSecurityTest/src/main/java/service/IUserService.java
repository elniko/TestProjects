package service;

import entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by stagiaire on 16/12/2014.
 */
public interface IUserService extends UserDetailsService {

    public UserDetails loadUserByUsername(String s) ;

    public User loadByUserName(String s);

    public void addUser(User user);

}
