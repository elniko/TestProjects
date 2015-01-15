package entity;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Created by nike on 27/11/14.
 */
@NamedStoredProcedureQuery(name = "addLog", procedureName = "mmi.addLog",parameters = {
        @StoredProcedureParameter(name = "processId",type = Long.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "message",type = String.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "lineCount",type = Long.class, mode = ParameterMode.IN)
})
@Entity
@Table(name="log", schema = "mmi")
public class LogEntity {

    @Id
    @GeneratedValue
    long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    Calendar createdAt;


    String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "process_id")
    ProcessEntity process;

    @Column(name = "line_count")
    long lineCount;



    public long getLineCount() {
        return lineCount;
    }

    public void setLineCount(long lineCount) {
        this.lineCount = lineCount;
    }

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

    public ProcessEntity getProcess() {
        return process;
    }

    public void setProcess(ProcessEntity process) {
        this.process = process;
    }
}
