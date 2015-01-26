package entity;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Created by stagiaire on 21/01/2015.
 */
@Entity
@Table(name = "resource_type")
public class ResourceTypeEntity extends entity.Entity {

    @Column(nullable = false, unique = true)
    String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
