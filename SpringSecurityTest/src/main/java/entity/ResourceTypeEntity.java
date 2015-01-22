package entity;

import javax.persistence.*;

/**
 * Created by stagiaire on 21/01/2015.
 */
@Entity
@Table(name = "resource_type")
public class ResourceTypeEntity {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
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
