package dao.interfaces;

import entity.UserEntity;

import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
public interface UserDao extends GenericDao<UserEntity> {

    public List<UserEntity> findByUserName(String name);
}
