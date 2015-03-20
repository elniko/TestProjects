package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by stagiaire on 20/01/2015.
 */
@Entity
@Table(name = "property_type")
public class PropertyTypeEntity extends entity.Entity{


    @Column(unique = true)
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
