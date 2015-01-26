package entity;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by stagiaire on 19/01/2015.
 */
@Entity
@Table(name = "profile")
public class ProfileEntity extends entity.Entity{


    String params;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey =   @ForeignKey(name = "fk_user_profile"))
    UserEntity user;



    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
