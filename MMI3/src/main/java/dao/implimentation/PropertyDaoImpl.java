package dao.implimentation;

import dao.interfaces.PropertyDao;
import dao.interfaces.PropertyTypeDao;
import entity.PropertyEntity;
import entity.PropertyTypeEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by Nick on 13/04/2015.
 */
@Repository
public class PropertyDaoImpl extends GenericDaoImpl<PropertyEntity> implements PropertyDao {
    @PostConstruct
    public void init() {
        setClass(PropertyEntity.class);
    }
}
