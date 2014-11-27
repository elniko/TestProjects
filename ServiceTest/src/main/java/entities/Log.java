package entities;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by nike on 27/11/14.
 */
@Entity
@Table(name="log")
public class Log {

    @Id
    @GeneratedValue
    long id;

    @Temporal(TemporalType.DATE)
    Calendar createdAt;


    String message;

    @ManyToOne
    @JoinColumn(name = "process_id")
    Process process;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }
}
