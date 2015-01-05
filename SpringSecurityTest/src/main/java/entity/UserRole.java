package entity;

import javax.persistence.*;

/**
 * Created by stagiaire on 16/12/2014.
 */
@Entity
@Table(name = "role_security",  uniqueConstraints = @UniqueConstraint(name = "constraint", columnNames = {"role", "user_id"}))
public class UserRole {

    @Id
    @GeneratedValue
    int id;

    @Column(unique = true)
    String role;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
