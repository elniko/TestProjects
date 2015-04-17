package entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Created by stagiaire on 19/01/2015.
 */
@Entity
@Table(name = "property")
public class PropertyEntity extends entity.Entity{

    String name;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_type_property"))
    PropertyTypeEntity type;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<PropertyValueEntity> valueList;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyTypeEntity getType() {
        return type;
    }

    public void setType(PropertyTypeEntity type) {
        this.type = type;
    }

    public Set<PropertyValueEntity> getValueList() {
        return valueList;
    }

    public void setValueList(Set<PropertyValueEntity> valueList) {
        this.valueList = valueList;
    }
}
