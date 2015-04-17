package ubpartner;

import ubpartner.logmanagement.LogManagementI;
import ubpartner.lst.LstXmlDate;
import ubpartner.xct.ExecXct;
import ubpartner.xct.commun.Constante;

/**
 * Main class for XBRL converter.
 * Calls the main method to convert Excel into XBRL instance
 * according to the entered args.
 * Is protected with LST.
 * @author UBPartner
 **/
public final class Xct {
    /**
     * Empty constructor for the final class.
     */
    private Xct() {
        //Class can not be instantiated.
    }
    
	/**
	 * Main function.
	 * 
	 * @param args
	 *            arguments.
	 */
	public static void main(final String[] args) {
	   LogManagementI.lmInit(System.getenv("LOGFILE_ACTIVATION"),
				System.getenv("LOGPRINT_ACTIVATION"),
				System.getenv("FILE_LEVEL"), System.getenv("PRINT_LEVEL"),
				System.getenv("FILE_MODE"), System.getenv("PRINT_MODE"),
				Constante.NOMCOURTAPPLICATION, System.getenv("LOGFILE_PATH"),
				System.getenv("POPUP"));
	
	   
		String nomOutil = Constante.NOMCOURTAPPLICATION;	String versionOutil = Constante.VERSIONAPPLICATION;
		boolean result = LstXmlDate.traitement(nomOutil, versionOutil);
		if (result) {
			new ExecXct(args);
		}
		LogManagementI.lmOutputResetConfig();
	}
}
