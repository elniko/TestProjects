package entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Entity
@Table(name = "user_security")
public class User {

    @Id
    @GeneratedValue
    int id;

    @Column(unique = true)
    String name;

    String password;

    boolean enabled;

    @Column(name = "created_at")
    Calendar createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    Set<UserRole> role;

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

    public Set<UserRole> getRole() {
        return role;
    }

    public void setRole(Set<UserRole> role) {
        this.role = role;
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

    @PrePersist
    private void onPersist() {
        Calendar cal = Calendar.getInstance();
        createdAt = cal;
    }

}
