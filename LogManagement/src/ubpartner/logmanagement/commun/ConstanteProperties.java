package ubpartner.logmanagement.commun;

/**
 * Class of properties file constant.
 */
public final class ConstanteProperties {

    /**
     * Empty constructor for the final class.
     */
    private ConstanteProperties() {
        // Class cannot be Instantiated.
    }

    /**
     * Set rootLogger key in properties file.
     */
    public static final String ROOTLOGGERPROP = "log4j.rootLogger";

    /**
     * Set appender type key in properties file.
     */
    public static final String FILEAPPENDERPROP = "log4j.appender.FILE";

    /**
     * Set appender type key value in properties file.
     */
    public static final String FILEAPPENDERVALUE =
            "org.apache.log4j.FileAppender";

    /**
     * Set threshold key in properties file.
     */
    public static final String THRESHOLDPROP = "log4j.appender.FILE.Threshold";

    /**
     * Set layout key in properties file.
     */
    public static final String LAYOUTPROP = "log4j.appender.FILE.layout";

    /**
     * Set layout key value in properties file.
     */
    public static final String LAYOUTVALUE = "org.apache.log4j.xml.XMLLayout";

    /**
     * Set immediate flush key in properties file.
     */
    public static final String IMMEDIATEFLUSHPROP =
            "log4j.appender.FILE.ImmediateFlush";

    /**
     * Set immediate flush key value in properties file.
     */
    public static final String IMMEDIATEFLUSHVALUE = "true";

    /**
     * Set append key in properties file.
     */
    public static final String APPENDPROP = "log4j.appender.FILE.append";

    /**
     * Set append key value in properties file.
     */
    public static final String APPENDVALUE = "false";

    /**
     * Set output log path key in properties file.
     */
    public static final String FILEPROP = "log4j.appender.FILE.File";

    /**
     * Commentary when set values in configuration file.
     */
   public static final String SETCONFIGCOMMENT =
           "log.properties generated by LogManagement";

    /**
     * Commentary when reset values in configuration file.
     */
   public static final String RESETCONFIGCOMMENT = "reset log output path";
}