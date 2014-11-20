package entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by stagiaire on 20/11/2014.
 */
@Entity
@Table(name = "user")
public class User {

    private int id;

    private String name;

    private String password;

    private String email;

    private Calendar createdAt;

    private Calendar updatedAt;

    @Id
    @GeneratedValue
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

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void onCreate() {
        Calendar c =  Calendar.getInstance();
        createdAt = c;
        updatedAt = c;
    }

    @PreUpdate
    public void onUpdate() {
        Calendar c = Calendar.getInstance();
        updatedAt = c;
    }

}
