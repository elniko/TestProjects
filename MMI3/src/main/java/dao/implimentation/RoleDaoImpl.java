package dao.implimentation;

import dao.interfaces.RoleDao;
import entity.RoleEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Nick on 22/01/2015.
 */
@Repository
public class RoleDaoImpl extends GenericDaoImpl<RoleEntity> implements RoleDao {

    @PostConstruct
    public void init() {
        setClass(RoleEntity.class);
    }

    @Override
    public List<RoleEntity> findByRole(String role) {
        Query query = em.createQuery("from " + clazz.getName());

       return query.getResultList();
    }
}
