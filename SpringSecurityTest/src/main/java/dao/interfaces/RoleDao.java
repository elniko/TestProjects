package dao.interfaces;

import entity.UserRoleEntity;

import java.util.List;

/**
 * Created by Nick on 22/01/2015.
 */
public interface RoleDao extends GenericDao<UserRoleEntity> {

    public List<UserRoleEntity> findByRole(String role, String orderBy, int limit);
}
