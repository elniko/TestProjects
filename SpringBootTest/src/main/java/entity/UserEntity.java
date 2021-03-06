package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Entity
@Table(name = "account")
public class UserEntity extends entity.Entity {


    @NotEmpty
    @Column(unique = true)
    String name;

    @NotEmpty
    String password;

    boolean enabled;

    @Column(name = "created_at")
    Calendar createdAt;


    @Email
    @NotEmpty
    @Column(nullable = true)
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   // @OneToMany(cascade =  CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
   @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name ="fk_role" ))
    RoleEntity role ;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    List<ProcessEntity> processList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    List<ProfileEntity> profileList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    List<ResourceEntity> resourceList = new ArrayList<>();


    public List<ResourceEntity> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceEntity> resourceList) {
        this.resourceList = resourceList;
    }

    public List<ProcessEntity> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessEntity> processList) {
        this.processList = processList;
    }

    public List<ProfileEntity> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<ProfileEntity> profileList) {
        this.profileList = profileList;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    @PrePersist
    private void onPersist() {
        Calendar cal = Calendar.getInstance();
        createdAt = cal;
    }

}
