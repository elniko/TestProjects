package dao.implimentation;

import dao.interfaces.PropertyTypeDao;
import entity.PropertyTypeEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 26/01/2015.
 */
@Repository
public class PropertyTypeDaoImpl extends GenericDaoImpl<PropertyTypeEntity> implements PropertyTypeDao{
    @PostConstruct
    public void init() {
        setClass(PropertyTypeEntity.class);
    }
}
