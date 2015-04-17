package ubpartner.xvt.commun.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import ubpartner.xvt.commun.Constante;

/**
 * Class for XML constants.
 * 
 */
public final class ConstanteXml {

    /**
     * Empty constructor for final class.
     * 
     */
    private ConstanteXml() {
	// Class cannot be Instantiated.
    }

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
     * Comment.
     */
    public static final String XMLCOMMENTCREA = "Created by "
	    + Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION
	    + ": " + dfmrt;
 
}