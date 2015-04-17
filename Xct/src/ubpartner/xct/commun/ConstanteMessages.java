package ubpartner.xct.commun;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for messages constants.
 */
public final class ConstanteMessages {
    /**
     * Class cannot be Instantiated.
     */
    private ConstanteMessages() {
        // Class cannot be Instantiated.
    }
    /**
     * Message unknown option.
     */
    public static final String MSGOPTIONINCONNU = "Undefined option ";
    /**
     * Message processing file.
     */
    public static final String MSGTRAITEMENT = "Processing file ";
    /**
     * Message end processing file.
     */
    public static final String MSGFINTRAITEMENT
    = "End of processing file ";
    /**
     * Contexts file error.
     */
    public static final String MSGERREURFICHIERCONTEXT
    = "Error loading contexts ";
    /**
     * Configuration file error.
     */
    public static final String MSGERREURFICHIECONGIF
    = "Error loading taxonomy configuration ";
    /**
     * Mapping file error.
     */
    public static final String MSGERREURFICHIERMAPPING
    = "Error loading mapping ";
    /**
     * Mapping and blank files don't correspond.
     */
    public static final String MSGERREURNOMAPPING
    = "No values found according to existing mapping: "
      + " does mapping file correspond to excel file?";
    /**
     * Contexts and mapping file don't correspond.
     */
    public static final String MSGERREURNOCONTEXT
    = "No contexts found according to existing mapping:"
       + " does mapping file correspond to contexts file?";
    /**
     * Error in zip file.
     */
    public static final String MSGERREURFICHIERZIP
    = "Error loading archive settings : ";
    /**
     * Contexts configuration message.
     */
    public static final String MSGCONFIGURINGCONTEXTS
    = "Configurating the contexts with entity and period";
    /**
     * Message for number of the converted files.
     */
    public static final String MSGFICHIERTRAITE
    = "Number of converted files: ";
    /**
     * Instance file treatment start.
     */
    public static final String MSGCREATINGINSTANCE
    = "Writing facts";
    /**
     * Message for unfound cell.
     */
    public static final String MSGCELLUNREACHABLE = "Unfound cell: ";
    /**
     * Message message HELP.
     */
    public static final String MSGHELP = Constante.NOMAPPLICATION + " "
            + Constante.VERSIONAPPLICATION
            + System.getProperty("line.separator")
            + "-c : taxonomy configuration file path."
            + "\n" + "-i : Excel file path."
            + "\n" + "-t : Taxonomy ID."
            + "\n" + "-tm : Input mode for taxonomy ID."
            + "\n" + "-e : entity."
            + "\n" + "-em :  input mode for entity."
            + "\n" + "-E : url for entity schema."
            + "\n" + "-dm : dates input mode."
            + "\n" + "-sd : start date."
            + "\n" + "-ed : end date."
            + "\n" + "-v  : validation activate (y/n)."
            + "\n" + "-o : output file path."
            + "\n" + "-fm  : create restricted mapping (y/n)."
            + "\n" + "-ov  : validation output."
            + "\n" + "-sc  : partial tabs convertion (y/n)."
            + "\n" + "-fi : full context ID instance generation (y/n).";
    /**
     * Message d'erreur pour le taxonomy ID non-unique.
     */
    public static final String ERREURTAXOID = "Duplicate taxonomy ID: ";
    /**
     * Message d'erreur pour le xsd non-unique.
     */
    public static final String ERREURXSD
    = "Duplicate XSD for the validity period: ";
    /**
     * Error SheetName dans le fichier de mapping Ubpartner.
     */
    public static final String ERRSHEETNAME
    = "Unfound table: ";
    /**
     * Message for taxo ID.
     */
    public static final String MSGTAXOID
    = "Working taxonomy is: ";
    /**
     * Message for entity.
     */
    public static final String MSGENTITY
    = "Current entity is: ";
    /**
     * Message for period.
     */
    public static final String MSGPERIOD
    = "Reporting period is: ";
    /**
     * Message for Parameters.
     */
    public static final String MSGPARAMETRES
    = "Working taxonomy settings";
    /**
     * Message for contexts file.
     */
    public static final String MSGCONTEXTFILE
    = "Context file is: ";
    /**
     * Message for mapping file.
     */
    public static final String MSGMAPPINGFILE
    = "Mapping file is: ";
    /**
     * Message for xsds.
     */
    public static final String MSGXSDS
    = "Schema: ";
    /**
     * Message for bad file extention.
     */
    public static final String MSGEXTENSION
    = "Extension is not supported: ";
    /**
     * Message for mapping count.
     */
    public static final String MSGMAPPINGSFOUND
    = " mapped cells found for current file.";
    /**
     * Message for mapping loaded.
     */
    public static final String MSGMAPPINGLOADED
    = "Mapping successfully loaded ";
    /**
     * Message for value search.
     */
    public static final String MSGVALUESEARCH
    = "Start search for values in Excel file.";
    /**
     * Message for values count.
     */
    public static final String MSGVALUEFOUND
    = " values of mapped cells found in Excel file.";
    /**
     * Message for contexts count.
     */
    public static final String MSGUCFOUND
    = " used contexts found.";
    /**
     * Message pour validation.
     */
    public static final String MSGVALIDATION
    = "Instance validation";
    /**
     * SimpleDateFormat.
     * 
     */
    private static SimpleDateFormat formatdate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    /**
     * Formated start date.
     * 
     */
    private static String dfmrt = formatdate.format(new Date());
    /**
     * Created by comment.
     */
    public static final String CREATEDBY = "Created by "
    	    + Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION
    	    + ": " + dfmrt;
}
