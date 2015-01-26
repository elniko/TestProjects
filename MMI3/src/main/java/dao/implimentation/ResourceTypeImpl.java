package dao.implimentation;

import dao.interfaces.ResourceTypeDao;
import entity.ResourceTypeEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Query;

import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
@Repository
public class ResourceTypeImpl extends GenericDaoImpl<ResourceTypeEntity> implements ResourceTypeDao {

    @PostConstruct
    public void init() {
        setClass(ResourceTypeEntity.class);
    }

    @Override
    public List<ResourceTypeEntity> findByName(String name) {
        Query q = em.createQuery("From " + ResourceTypeEntity.class + " rt where rt.name=:name");
        q.setParameter("name", name);
        return q.getResultList();
    }
}
