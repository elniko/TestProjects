package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stagiaire on 19/01/2015.
 */
@Entity
@Table(name = "resource")
public class ResourceEntity extends entity.Entity{



    //@ElementCollection
    //@CollectionTable(name="resource_type", joinColumns=@JoinColumn(name="type_id"), foreignKey = @ForeignKey(name="resource_type"))
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_type_resource"))
    //ResourceTypeEntity type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name="fk_user_resource"))
    UserEntity user;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resourceList")
    Set<ProcessEntity> processList = new HashSet<>();

    @Column(nullable = false)
    @Lob
    byte[] resource;

    @Column(name = "resource_ext")
    String resourceExt;

    public String getResourceExt() {
        return resourceExt;
    }

    public void setResourceExt(String resourceExt) {
        this.resourceExt = resourceExt;
    }

    public Set<ProcessEntity> getProcessList() {
        return processList;
    }

    public void setProcessList(Set<ProcessEntity> processList) {
        this.processList = processList;
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
