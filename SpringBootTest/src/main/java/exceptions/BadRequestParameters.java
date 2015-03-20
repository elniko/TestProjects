package exceptions;

/**
 * Created by Nick on 29/01/2015.
 */

public class BadRequestParameters extends Exception {
    public BadRequestParameters() {
        super("Request parameters are not correct");
    }
    public BadRequestParameters(String msg) {
        super(msg);
    }
}
