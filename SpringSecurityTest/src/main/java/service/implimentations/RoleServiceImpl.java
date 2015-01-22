package service.implimentations;

import dao.interfaces.GenericDao;
import entity.UserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import service.interfaces.RoleService;

/**
 * Created by Nick on 21/01/2015.
 */
@Repository
public class RoleServiceImpl extends GenericServiceImpl<UserRoleEntity> implements RoleService{
    @Override
    @Autowired
    public void setGenericDao(GenericDao<UserRoleEntity> d) {
        dao = d;
        dao.setClass(UserRoleEntity.class);
    }
}
