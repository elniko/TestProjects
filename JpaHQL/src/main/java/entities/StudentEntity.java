package entities;


import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by stagiaire on 14/11/2014.
 */
@Entity
@Table(name = "student")
public class StudentEntity  {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   // @Column(name = "group_id")
    //long groupId;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Temporal(TemporalType.DATE)
    Calendar date;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    Calendar createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    Calendar updatedAt;


    @ManyToOne(cascade = CascadeType.ALL)
   // @JoinColumn(name = "group_idd")
    Groupp groupp;

    @PrePersist
    public void onCrerate() {
        createdAt = Calendar.getInstance();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Calendar.getInstance();
    }

    public void setGroupId(long groupId) {
      //  this.groupId = groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Calendar updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Groupp getGroup() {
        return groupp;
    }

    public void setGroup(Groupp group) {
        this.groupp = group;
    }
}
