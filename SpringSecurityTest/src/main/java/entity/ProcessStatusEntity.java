package entity;

import javax.persistence.*;

/**
 * Created by stagiaire on 20/01/2015.
 */
@Entity
@Table(name="process_status")
public class ProcessStatusEntity {
    @Id
    @GeneratedValue
    int id;

    @Column(unique = true)
    String name;

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
}
