package entity;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

/**
 * Created by Nick on 26/01/2015.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Entity {
    @Id
    @GeneratedValue
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
