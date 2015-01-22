package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stagiaire on 19/01/2015.
 */
@Entity
@Table(name = "resource")
public class ResourceEntity {

    @Id
    @GeneratedValue
    int id;

    //@ElementCollection
    //@CollectionTable(name="resource_type", joinColumns=@JoinColumn(name="type_id"), foreignKey = @ForeignKey(name="resource_type"))
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_type_resource"))
    ResourceTypeEntity type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="fk_user_resource"))
    UserEntity user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resourceList")
    Set<ProcessEntity> processList = new HashSet<>();

    @Column(nullable = false)
    @Lob
    byte[] resource;

    public Set<ProcessEntity> getProcessList() {
        return processList;
    }

    public void setProcessList(Set<ProcessEntity> processList) {
        this.processList = processList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ResourceTypeEntity getType() {
        return type;
    }

    public void setType(ResourceTypeEntity type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public byte[] getResource() {
        return resource;
    }

    public void setResource(byte[] resource) {
        this.resource = resource;
    }
}
