package entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nike on 27/11/14.
 */
@Entity
@Table(name="process", schema ="mmi")

public class ProcessEntity {

    @Id
    @GeneratedValue
    long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="started_at")
    Calendar startedAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ended_at")
    Calendar endedAt;

    String state;

    String type;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "process", fetch = FetchType.LAZY)
    List<LogEntity> messages;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<LogEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<LogEntity> messages) {
        this.messages = messages;
    }

    @PrePersist
    public void onPersist() {
        Calendar cal = Calendar.getInstance();
        startedAt = cal;
    }
}
