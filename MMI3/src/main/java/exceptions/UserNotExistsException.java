package exceptions;

/**
 * Created by Nick on 29/01/2015.
 */
public class UserNotExistsException extends Exception {
    public UserNotExistsException(String msg) {
        super("User " + msg + " not exists");
    }
}
