package exceptions;

/**
 * Created by Nick on 02/02/2015.
 */
public class ExceptionJSONInfo {

    private String url;
    private String exception;
    private String message;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
