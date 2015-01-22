package dao.implimentation;

import dao.interfaces.RoleDao;
import entity.UserRoleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Nick on 22/01/2015.
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<UserRoleEntity> implements RoleDao {
    @Override
    public List<UserRoleEntity> findByRole(String role, String orderBy, int limit) {
        Query query = em.createQuery("from " + clazz.getName() + " " + orderBy);
        if(limit != 0) {
            query.setMaxResults(limit);
        }
       return query.getResultList();
    }
}
