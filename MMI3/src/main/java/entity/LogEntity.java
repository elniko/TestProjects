package entity;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by nike on 27/11/14.
 */
@NamedStoredProcedureQueries(value = {@NamedStoredProcedureQuery(name = "addLogOld", procedureName = "mmi.addLogOld",parameters = {
        @StoredProcedureParameter(name = "processId",type = Integer.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "message",type = String.class, mode = ParameterMode.IN),
        @StoredProcedureParameter(name = "lineCount",type = Long.class, mode = ParameterMode.IN)
}),
        @NamedStoredProcedureQuery(name = "addLog", procedureName = "mmi.addLog",parameters = {
                @StoredProcedureParameter(name = "processId",type = Integer.class, mode = ParameterMode.IN),
                @StoredProcedureParameter(name = "message",type = String.class, mode = ParameterMode.IN),
        })})


@Entity
@Table(name="log")
public class LogEntity extends entity.Entity {


    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    Calendar createdAt;

    String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "process_id", foreignKey = @ForeignKey(name = "fk_process_log"))
    ProcessEntity process;


    @Column(name = "line_count")
    long lineCount;



    public long getLineCount() {
        return lineCount;
    }

    public void setLineCount(long lineCount) {
        this.lineCount = lineCount;
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
