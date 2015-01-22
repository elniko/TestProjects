package service.implimentations;

import dao.interfaces.GenericDao;
import dao.interfaces.RoleDao;
import entity.UserEntity;
import entity.UserRoleEntity;
import exceptions.NoRoleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import service.interfaces.UserService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Nick on 22/01/2015.
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<UserEntity> implements UserService, UserDetailsService {

    RoleDao roleDao;

    @Override
    @Autowired
    public void setGenericDao(GenericDao<UserEntity> d) {
        dao = d;
        dao.setClass(UserEntity.class);
    }

    @Autowired
    public void setRoleDao(RoleDao rd) {
        roleDao = rd;
        roleDao.setClass(UserRoleEntity.class);
    }

    @Override
    @Transactional
    public void addUserWithRole(String name, String pass, String mail, String role) throws NoRoleException {
        List<UserRoleEntity> roles = roleDao.findByRole(role, "", 0);
        if(roles == null || roles.size() == 0 ){
            throw new NoRoleException(role);
        }
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setPassword(encodePassword(pass));
        user.setEmail(mail);
        user.setRole(roles.get(0));
        user.setEnabled(true);
        saveEntity(user);

    }

    private String encodePassword(String pass) {
        return pass;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
