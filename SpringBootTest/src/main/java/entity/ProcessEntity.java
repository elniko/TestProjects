package entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;

/**
 * Created by nike on 27/11/14.
 */
@Entity
@Table(name="process")

public class ProcessEntity extends entity.Entity{

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    Calendar createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="started_at")
    Calendar startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ended_at")
    Calendar endedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false, foreignKey = @ForeignKey(name = "fk_status_process"))
    ProcessStatusEntity status;

    @Column(name = "thread_name")
    String threadName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="type_id", nullable = false, foreignKey = @ForeignKey(name = "fk_type_process"))
    ProcessTypeEntity type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "process", fetch = FetchType.LAZY)
    List<LogEntity> messageList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, foreignKey =  @ForeignKey(name = "fk_user_process"))
    UserEntity user;


//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "process_resource",  joinColumns = @JoinColumn(name = "process_id"),
//           inverseJoinColumns = {@JoinColumn(name = "resource_id")}, foreignKey = @ForeignKey(name = "fk_process_resource"))
@Transient
    Set<ResourceEntity> resourceList =  new HashSet<>();

//    @OneToMany(mappedBy = "process")
//    @MapKeyJoinColumn(name = "property_id")
    @Transient
    Map<PropertyEntity, PropertyValueEntity> propetyValueList;

    public Set<ResourceEntity> getResourceList() {
        return resourceList;
    }

    public void setResourceList(Set<ResourceEntity> resourceList) {
        this.resourceList = resourceList;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }



    public Calendar getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Calendar startedAt) {
        this.startedAt = startedAt;
    }

    public Calendar getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Calendar endedAt) {
        this.endedAt = endedAt;
    }

    public ProcessStatusEntity getStatus() {
        return status;
    }

    public void setStatus(ProcessStatusEntity status) {
        this.status = status;
    }

    public ProcessTypeEntity getType() {
        return type;
    }

    public void setType(ProcessTypeEntity type) {
        this.type = type;
    }

    public List<LogEntity> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<LogEntity> messageList) {
        this.messageList = messageList;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onPersist() {
        Calendar cal = Calendar.getInstance();
        createdAt = cal;
    }
}
