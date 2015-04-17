package ubpartner;

import ubpartner.logmanagement.LogManagementI;
import ubpartner.lst.LstXmlDate;
import ubpartner.xvt.ExecXvt;
import ubpartner.xvt.commun.Constante;


/**
 * Main class of the XBRL Validation Tool.
 * 
 */
public final class Xvt {

	/**
	 * Empty constructor for the final class.
	 * 
	 */
	private Xvt() {
		// Class cannot be Instantiated.
	}
	
	/**
	 * Main function.
	 * 
	 * @param args arguments transferred from the bat. 
	 */
	public static void main(final String[] args) {
	    
	    String productName = Constante.NOMCOURTAPPLICATION;
 	    String productVersion = Constante.VERSIONAPPLICATION;
 	    LogManagementI.lmInit(System.getenv("LOGFILE_ACTIVATION"),
 			System.getenv("LOGPRINT_ACTIVATION"),
 			System.getenv("FILE_LEVEL"), System.getenv("PRINT_LEVEL"),
 			System.getenv("FILE_MODE"), System.getenv("PRINT_MODE"),
 			Constante.NOMCOURTAPPLICATION, System.getenv("LOGFILE_PATH"),
 			System.getenv("POPUP"));
 	    
	    boolean result = LstXmlDate.traitement(productName, productVersion);
 	    
	    if (result) {
		new ExecXvt(args);
	    }
	    LogManagementI.lmOutputResetConfig();
	}
}
