package ubpartner.xvt.commun;

import ubpartner.utils.commun.ConstanteUtils;

/**
 * Constants class.
 */
public final class Constante {

    /**
     * Empty constructor for final class.
     * 
     */
    private Constante() {
	// Class cannot be Instantiated.
    }

    /**
     * Application name.
     */
    public static final String NOMAPPLICATION = "XBRL Validation Tools";
    
    /**
     * Short application name.
     */
    public static final String NOMCOURTAPPLICATION = "xvt";

    /**
     * Application version.
     */

    public static final String VERSIONAPPLICATION = Constante.class.getPackage().getImplementationVersion();


    /**
     * Unknown option message.
     */
    public static final String MSGOPTIONINCONNU = "Unknown option";

    /**
     * Message for file processing.
     */
    public static final String MSGTRAITEMENT = "File processing";

    /**
     * Message for treatment end.
     */
    public static final String MSGFINTRAITEMENT = "End of file processing";
    
    /**
     * Global message for treatment end.
     */
    public static final String MSGNBFICHIERTRAITE = "Processed file(s)";
    
    /**
     * Validation message.
     */
    public static final String MSGVALIDATION = "No validation XBRL and Dimension.";
    
    /**
     * Error message for output path.
     */
    public static final String MSGERROUTPUT = "Error to determine the output path.";
    
    /**
     * Message info FormulaPartitioning.
     */
    public static final String MSGINFOFORMULAPARTITIONING = "FormulaPartitioning :";
    
    /**
     * Message info FormulaActivation.
     */
    public static final String MSGINFOFORMULAACTIVATION = "FormulaActivation : ";
    /**
     * Message error out of memory.
     */
    public static final String MSGOUTOFMEMORY = "The allocated memory is not enough for the instance treatment. "
	     + "Please, change you Xmx/Xms parameters via the bat file or UBPartner XBRL Tools \"User Settings-Advanced\" interface. "; 
    /**
     * Extension XBRL.
     */
    public static final String XBRL = "xbrl";
    /**
     * Extension XML.
     */
    public static final String XML = "xml";
    /**
     * Temporary folder path.
     */
    public static final String TEMPFOLDER = ConstanteUtils.stringIsNull(
            System.getenv("UBP_TEMP"), System.getProperty("java.io.tmpdir"))
            + "/UBPartner/UBPartner XBRL Tools/tmp/XVT/";
    /**
     * Path to the stylesheet for xbrl validation report.
     */
    public static final String STYLEPATH = ConstanteUtils.stringIsNull(
            System.getenv("UBP_STYLE"), "" + "/ubp-stylesheet.xsl");
    /**
     * Path to stylesheet for formula report.
     */
    public static final String XLSTPATH = "/UBPFormulatoHtml.xsl";
    /**
     * Path to stylesheet for formula report.
     */
    public static final String CUSTOMMESSAGE = "/xmldata.xml ";
    /**
     * Message HELP.
     */
    public static final String MSGHELP = NOMAPPLICATION + " "
	    + VERSIONAPPLICATION + System.getProperty("line.separator")
	    + "-i : XBRL document file(s) or folder."
	    + "-c : Configuration file."
	    + "-t : ID taxo."
	    + "-o : Output folder."
	    + "-s : To add a suffix to results file name."
	    + "-x : XSL file from XPE library."
	    + "-cs: configuration file for XPE.";
}
