package entity;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Entity
@Table(name = "role")
public class UserRoleEntity{

    @Id
    @GeneratedValue
    int id;


    String role;



    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id", nullable = false)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "role")
    Set<UserEntity> userList =  new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Set<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(Set<UserEntity> user) {
        this.userList = user;
    }




}