package exceptions;

/**
 * Created by Nick on 23/01/2015.
 */
public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("This name already in use");
    }

    public UserAlreadyExistException(String msg) {
        super("This name: " + msg + " already in use");
    }

}
