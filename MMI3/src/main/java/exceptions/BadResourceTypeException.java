package exceptions;

/**
 * Created by Nick on 29/01/2015.
 */
public class BadResourceTypeException extends Exception {

    public BadResourceTypeException(String msg) {
        super("Resource type " + msg + " not exists");
    }

    public BadResourceTypeException() {
        super("This resource type not exist");
    }
}
