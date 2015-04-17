package ubpartner.xvt.validation;

/**
 * Class for validation exceptions.
 * @author UBPartner
 *
 */
public class ValidationException extends Exception {
    /**
     * Serial number.
     */
    private static final long serialVersionUID = 2826674578964889751L;
    /**
     * General constructor.
     */
    public ValidationException() {
        super();
    }
    /**
     * Constructor for exception with message and cause.
     * @param message - error message.
     * @param cause - error cause trace.
     */
    public ValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor for exception with message.
     * @param message - error message.
     */
    public ValidationException(final String message) {
        super(message);
    }
    /**
     * Constructor for exception with cause.
     * @param cause - error cause trace.
     */
    public ValidationException(final Throwable cause) {
        super(cause);
    }

}
