package exceptions;

/**
 * Created by Nick on 09/03/2015.
 */
public class BadProcessTypeException extends Exception {

    public BadProcessTypeException(String msg) {
        super("Process type: " + msg + " not exists");
    }

    public BadProcessTypeException() {
        super("This process type not exists");
    }
}
