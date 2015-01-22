package service.interfaces;

import entity.UserEntity;
import exceptions.NoRoleException;

/**
 * Created by Nick on 22/01/2015.
 */
public interface UserService extends GenericDaoService<UserEntity> {

    public void addUserWithRole(String name, String pass, String mail, String role) throws NoRoleException;
}
