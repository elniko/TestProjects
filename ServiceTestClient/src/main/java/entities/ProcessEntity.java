package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Calendar;
import java.util.List;

/**
 * Created by nike on 27/11/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessEntity {


    long id;


    Calendar startedAt;


    Calendar endedAt;

    String state;

    String type;


    List<LogEntity> messages;

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


}
