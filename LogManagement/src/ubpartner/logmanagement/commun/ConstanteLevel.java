package ubpartner.logmanagement.commun;

import org.apache.log4j.Level;

import ubpartner.logmanagement.CustomLevel;

/**
 * Class for the level constants.
 */
public final class ConstanteLevel {

    /**
     * Empty constructor for the final class.
     */
    private ConstanteLevel() {
        // Class cannot be Instantiated.
    }

    /**
     * Log message level "ALL".
     * All levels including custom levels.
     */
    public static final String ALL = "ALL";

    /**
     * Log message level "TRACE".
     * Designates finer-grained informational events than the DEBUG.
     */
    
    public static final CustomLevel TRACE = new CustomLevel(Level.TRACE_INT, "ERROR", 0);
    /**
     * Log message level "DEBUG".
     * Designates fine-grained informational events that
     * are most useful to debug an application.
     */
   
    public static final CustomLevel DEBUG = new CustomLevel(Level.DEBUG_INT, "DEBUG", 0);
    /**
     * Log message level "INFO".
     * Designates informational messages that highlight the progress
     * of the application at coarse-grained level.
     */
   
    public static final CustomLevel INFO = new CustomLevel(Level.INFO_INT, "INFO", 0);
    /**
     * Log message level "WARN".
     * Designates potentially harmful situations.
     */
   
    public static final CustomLevel WARN = new CustomLevel(Level.WARN_INT, "WARN", 1);
    /**
     * Log message level "ERROR".
     * Designates error events that might still allow the application
     * to continue running.
     */
    
    public static final CustomLevel ERROR = new CustomLevel(Level.ERROR_INT, "ERROR", 2);
    /**
     * Log message level "FATAL".
     * Designates every severe error events that will presumably lead the
     * application to abort.
     */
  
    public static final CustomLevel FATAL = new CustomLevel(Level.FATAL_INT, "FATAL", 3);
    /**
     * Log message level "OFF".
     * The highest possible rank and is intended to turn off logging.
     */
    public static final String OFF = "OFF";
    
    /**
     * Log message level validation warning.
     * Designates the unsuccessful validation result.
     */
    public static final CustomLevel VWARN = new CustomLevel(Level.INFO_INT + 2, "VWARN", 11);
    
    
   

}
