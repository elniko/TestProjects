package exceptions;

/**
 * Created by Nick on 22/01/2015.
 */
public class NoRoleException extends Exception {
    public  NoRoleException() {
        super("Role doesn not exist");
    }

    //Constructor that accepts a message
    public  NoRoleException(String message)
    {
        super("Role " + message + " does not exist");
    }
}
