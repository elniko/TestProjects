package dao.implimentation;


import dao.interfaces.UserDao;
import entity.ProfileEntity;
import entity.ResourceEntity;
import entity.UserEntity;
import exceptions.BadProfileException;
import exceptions.EntityNotExistsException;
import exceptions.UserNotExistsException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Nick on 23/01/2015.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<UserEntity> implements UserDao {

    @PostConstruct
    public void init() {
        setClass(UserEntity.class);
    }

    @Override
    public List<UserEntity> findByUserName(String name) {

        Query q = em.createQuery("from " + UserEntity.class.getName() + " as u  where u.name = :name");
        q.setParameter("name", name);
        return q.getResultList();
    }

    @Override
    public ProfileEntity getProfile(String name, int id) throws BadProfileException {
        Query q = em.createQuery("from " + UserEntity.class.getName() + " as u  join fetch u.profileList as p where u.name = :name and p.id=:id");
        q.setParameter("name", name);
        q.setParameter("id", id);
        List<UserEntity> uList = q.getResultList();
        if (uList.size() == 0) {
            throw new BadProfileException(String.format("Profile for userName: %s and Id profile: %s not exists", name, id));
        }
        if (uList.get(0).getProfileList().size() == 0) {
            throw new BadProfileException(String.format("User userName: %s have no profiles", name));
        }

        ProfileEntity prf = uList.get(0).getProfileList().get(0);
        em.clear();
        return prf;
    }

    @Override
    public List<ProfileEntity> getProfiles(String name) throws UserNotExistsException {
        Query q = em.createQuery("from " + UserEntity.class.getName() + " as u join fetch  u.profileList as p where u.name = :name");
        q.setParameter("name", name);
        List<UserEntity> uList = q.getResultList();
        if (uList.size() == 0) {
            throw new UserNotExistsException(name);
        }
        return uList.get(0).getProfileList();
    }

    @Override
    public boolean checkResource(int resourceId, int userId) {
        Query q = em.createQuery("from " + UserEntity.class.getName() + " as  u inner join u.resourceList as r where u.id = :userId and r.id = :resId");
        q.setParameter("userId", userId);
        q.setParameter("resId", resourceId);
        List list = q.getResultList();
        if (list.size() > 0)
          return true;
        return false;
    }

    @Override
    public List<UserEntity> findAllUserRole(int start, int count, String order, String role) {
        if (!order.equals("")) {
            order = " order by " + order;
        }
        if (role.equals(""))
            throw new IllegalArgumentException();

        role = "ROLE_" + role;
        Query q = em.createQuery("from " + UserEntity.class.getName() + " as u join fetch u.role as r where r.role=:role" + order );
        q.setParameter("role", role);
        q.setFirstResult(start);
        if (count > 0) {
            q.setMaxResults(count);
        }

        return q.getResultList();
    }

    @Override
    public void saveProfile(ProfileEntity profile) {
        em.persist(profile);
    }

    @Override
    public void editProfile(ProfileEntity profile) {
        em.merge(profile);
    }

    @Override
    public ProfileEntity getProfile(int id) {
        return em.find(ProfileEntity.class, id);
    }

    @Override
    public boolean removeProfile(int id) throws EntityNotExistsException {
        ProfileEntity profile = getProfile(id);
        return removeProfile(profile);
    }

    @Override
    public boolean removeProfile(ProfileEntity profile) throws EntityNotExistsException {

        if (profile == null) {
            throw new EntityNotExistsException();
        }
        int id = profile.getId();
        em.remove(profile);
        profile = getProfile(id);
        if (profile == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserEntity getUserRole(int id) throws EntityNotExistsException {
        Query q =  em.createQuery("from " + UserEntity.class.getName() + " as u join fetch u.role as r where u.id= :id", UserEntity.class);
        q.setParameter("id", id);
        List<UserEntity> list = q.getResultList();
        if(list.size() == 0) {
            throw new EntityNotExistsException("Entity not exists, Id: " + id);
        } else {
            return list.get(0);
        }
    }
}
