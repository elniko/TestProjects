package service.interfaces;

import entity.UserEntity;
import exceptions.RoleNotExistException;
import exceptions.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by Nick on 22/01/2015.
 */
public interface UserService extends UserDetailsService {

    public void addUserWithRole(String name, String pass, String mail, String role) throws RoleNotExistException, UserAlreadyExistException;

    public void removeUsers(int[] ids);

    public void removeUsers(List<UserEntity> users);

    public void addUser(UserEntity user);
}
