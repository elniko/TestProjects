package entity;


import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Email;


import javax.persistence.*;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Entity
@Table(name = "account")
public class UserEntity {

    @Id
    @GeneratedValue
    int id;

    @Column(unique = true)
    String name;

    String password;

    boolean enabled;

    @Column(name = "created_at")
    Calendar createdAt;


    @Email
    @Column(nullable = true)
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   // @OneToMany(cascade =  CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name ="fk_role" ))
    UserRoleEntity role ;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    Set<ProcessEntity> processList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    Set<ProfileEntity> profaileList = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    Set<ResourceEntity> resourceList = new HashSet<>();


    public Set<ResourceEntity> getResourceList() {
        return resourceList;
    }

    public void setResourceList(Set<ResourceEntity> resourceList) {
        this.resourceList = resourceList;
    }

    public Set<ProcessEntity> getProcessList() {
        return processList;
    }

    public void setProcessList(Set<ProcessEntity> processList) {
        this.processList = processList;
    }

    public Set<ProfileEntity> getProfaileList() {
        return profaileList;
    }

    public void setProfaileList(Set<ProfileEntity> profaileList) {
        this.profaileList = profaileList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserRoleEntity getRole() {
        return role;
    }

    public void setRole(UserRoleEntity role) {
        this.role = role;
    }

    @PrePersist
    private void onPersist() {


        Calendar cal = Calendar.getInstance();
        createdAt = cal;



    }

}
