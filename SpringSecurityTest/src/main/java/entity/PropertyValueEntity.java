package entity;

import javax.persistence.*;

/**
 * Created by stagiaire on 20/01/2015.
 */
@Entity
@Table(name="property_value")
public class PropertyValueEntity {

    @Id
    @GeneratedValue
    int id;

    String value;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false, foreignKey = @ForeignKey(name="fk_property_properyvalue"))
    PropertyEntity property;

    @ManyToOne
    @JoinColumn(name = "process_id", nullable = false, foreignKey = @ForeignKey(name="fk_process_propertyvalue"))
    ProcessEntity process;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }
}
