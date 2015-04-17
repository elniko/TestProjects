package ubpartner.logmanagement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ubpartner.logmanagement.commun.Constante;
import ubpartner.logmanagement.commun.ConstanteLevel;


/**
 * Class to manage log message.
 */
public final class LogManagement {
	
	/**
     * Activation for generated log file.
     */
	private static boolean logfileActivation;

	/**
     * Activation for printing out log messages.
     */
	private static boolean printActivation;

        
    /**
     * Output log message level for printing out.
     */
    private static String printLevel;

    /**
     * Level mode for generated log file: separate or threshold.
     */
    private static String mode;

    /**
     * Level mode for printing out: separate or threshold.
     */
    private static String printMode;

    /**
     * Flag to present if is separate mode, for generated log file.
     */
    private static boolean isSeparate;

    /**
     * Flag to present if is separate mode, for printing out.
     */
    private static boolean printIsSeparate;
    
    /**
     * UBPARTNER Tool name, for generated log file.
     */
    private static String toolname;

    /**
     * Flag to present if is pop up as reminder, value: mmi/y/n.
     */
    private static String popupstr;

   
    /**
     * Level and flag, message relationship treemap.
     */
    private static ArrayList<String[]> uiMessages = new ArrayList<String[]>();

    /**
     * Logger object for generated log file.
     */
    private static Logger loggerFile;

    /**
     * Logger object for printing log.
     */
    private static Logger loggerPrint;

    /**
     * Class cannot be Instantiated.
     */
    private LogManagement() {
        // Class cannot be Instantiated.
    }

    /**
     * Collection for return codes for all levels.
     */
    private static HashMap<String, Integer> returnCode = new HashMap<String, Integer>();
    /**
     * Level mode setter, mode value: separate or threshold.
     *
     * @param modestr
     *           mode string.
     */
    protected static void setMode(final String modestr) {
        mode = modestr;
        if (mode.equals(Constante.MODESEPARATE)) {
            isSeparate = true;
        } else if (mode.equals(Constante.MODETHRESHOLD)) {
            isSeparate = false;
        }
    }
    
    /**
     * Level mode getter.
     *
     * @return mode.
     */
    protected static String getMode() {
        return mode;
    }

    /**
     * Level mode setter for printing log, mode value: separate or threshold.
     *
     * @param printModestr
     *           mode string.
     */
    protected static void setPrintMode(final String printModestr) {
    	printMode = printModestr;
    	if (printMode.equals(Constante.MODESEPARATE)) {
    		printIsSeparate = true;
    	} else if (printMode.equals(Constante.MODETHRESHOLD)) {
    		printIsSeparate = false;
    	}
    }

    /**
     * Level mode getter for printing log.
     *
     * @return mode.
     */
    protected static String getPrintMode() {
    	return printMode;
    }

    /**
     * UBPARTNER Tool name getter.
     *
     * @return UBPARTNER Tool name.
     */
    public static String getToolname() {
        return toolname;
    }

    /**
     * UBPARTNER Tool name setter.
     *
     * @param name
     *           UBPARTNER Tool name.
     */
    protected static void setToolname(final String name) {
        toolname = name;
    }

    /**
     * pop up getter.
     *
     * @return is pop up.
     */
    protected static String getPopupstr() {
        return popupstr;
    }

    /**
     * pop up setter: true, pop up reminder; false, reminder printed on console.
     *
     * @param popupstring
     *           String, mmi/y/n.
     */
    protected static void setPopupstr(final String popupstring) {
        popupstr = popupstring;
    }

    /**
     * Logger object setter: set configuration file and generate logger.
     */
    protected static void setLogger() {
    	logfileActivation = LMConfiguration.getLogfileActivation();
    	printActivation = LMConfiguration.getPrintActivation();
    	if (logfileActivation) {
          
            PropertyConfigurator.configure(
                    Constante.CONFIGPATH + Constante.XMLLOGCONFIGNAME);
            loggerFile = Logger.getLogger(Constante.XMLLOGGERNAME);
    	}
    	if (printActivation) {
            printLevel = LMConfiguration.getPrintLevel();
            PropertyConfigurator.configure(
            		Constante.CONFIGPATH + Constante.PRINTLOGCONFIGNAME);
            loggerPrint = Logger.getLogger(Constante.PRINTLOGGERNAME);
    	}
    }

    
   /**
    * CLose loggers.
    */
    protected static  void shutdownLoggers() {
    	LogManager.shutdown();
    }
    /**
     * Logger object getter for generated log file.
     *
     * @return logger object.
     */
    protected static Logger getFileLogger() {
        return loggerFile;
    }

    /**
     * Logger object getter for printing log.
     *
     * @return logger object.
     */
    protected static Logger getPrintLogger() {
    	return loggerPrint;
    }

    /**
     * Log Management parameters verification.
     * @param logfileActivate
     *               Activate generating log file.
     * @param printActivate
     *               Activate printing logs.
     * @param levelstr
     *           log level for generating log file.
     * @param printLevelstr
     *           log level for 
     * @param modestr
     *           mode for generating log file.
     * @param printModestr
     *           mode for printing logs.
     * @param toolnamestr
     *           tool name for generating log file.
     * @param outpath
     *           generated log file output path.
     * @param ispopup
     *           generating a prompting or not.
     * @return true, parameters are all verified.
     */
    protected static boolean verifyParameters(final boolean logfileActivate, final boolean printActivate,
    		final String levelstr, final String printLevelstr,
            final String modestr, final String printModestr,
            final String toolnamestr, final String outpath, final String ispopup) {
    	boolean isLogFileActivationVerified = false;
    	boolean isPrintActivationVerified = false;
    	if (logfileActivate) {
    		if (!levelstr.equals(ConstanteLevel.ALL) && !levelstr.equals(ConstanteLevel.TRACE.getLevelString())
    				&& !levelstr.equals(ConstanteLevel.DEBUG.getLevelString()) && !levelstr.equals(ConstanteLevel.INFO.getLevelString())
    				&& !levelstr.equals(ConstanteLevel.WARN.getLevelString()) && !levelstr.equals(ConstanteLevel.ERROR.getLevelString())
    				&& !levelstr.equals(ConstanteLevel.FATAL.getLevelString()) && !levelstr.equals(ConstanteLevel.OFF)) {
    			System.out.println("Log level should be ALL/TRACE/DEBUG/INFO/WARN/ERROR/FATAL/OFF.");
    		} else {
        		if (!modestr.equals(Constante.MODETHRESHOLD) && !modestr.equals(Constante.MODESEPARATE)) {
        			System.out.println("MODE should be threshold/separate.");
        		} else {
        			if (outpath == null || outpath.equals("")) {
        				System.out.println("Log file output path is obligatory. Please configure it.");
        			} else {
        				if (toolnamestr == null || toolnamestr.equals("")) {
        					System.out.println("Tool name is obligatory. Please configure it.");
        				} else {
            				if (!ispopup.equals("y") && !ispopup.equals("n") && !ispopup.equals(Constante.MMIPOPUP)) {
            					System.out.println("Parameter popup should be y/n/mmi.");
            				} else {
            					isLogFileActivationVerified = true;
            				}
        				}
        			}
        		}
    		}
    	} else {
    		isLogFileActivationVerified = true;
    	}
    	if (printActivate) {
    		if (!printLevelstr.equals(ConstanteLevel.ALL) && !printLevelstr.equals(ConstanteLevel.TRACE.getLevelString())
    				&& !printLevelstr.equals(ConstanteLevel.DEBUG.getLevelString()) && !printLevelstr.equals(ConstanteLevel.INFO.getLevelString())
    				&& !printLevelstr.equals(ConstanteLevel.WARN.getLevelString()) && !printLevelstr.equals(ConstanteLevel.ERROR.getLevelString())
    				&& !printLevelstr.equals(ConstanteLevel.FATAL.getLevelString()) && !printLevelstr.equals(ConstanteLevel.OFF)) {
    			System.out.println("Printing Log level should be ALL/TRACE/DEBUG/INFO/WARN/ERROR/FATAL/OFF.");
    		} else {
    			if (!printModestr.equals(Constante.MODETHRESHOLD) && !printModestr.equals(Constante.MODESEPARATE)) {
        			System.out.println("Printing MODE should be threshold/separate.");
        		} else {
        			if (!ispopup.equals("y") && !ispopup.equals("n") && !ispopup.equals(Constante.MMIPOPUP)) {
        				System.out.println("Parameter popup should be y/n/mmi.");
        			} else {
            			isPrintActivationVerified = true;
        			}
        		}
    		}
    	} else {
    		isPrintActivationVerified = true;
    	}
    	return isLogFileActivationVerified && isPrintActivationVerified;
    }

    /**
     * Level and flag, message relationship map getter.
     *
     * @return
     *       flag, message and level relationship treemap.
     */
    public static ArrayList<String[]> getLevelRelationFlagMsg() {
        return uiMessages;
    }

    
    
    /**
     * Return array with return code and description.
     *
     * @return return code.
     */
    public static String[] getReturnCodeArr() {
        
    	String[] res = new String[2];
    	if (returnCode.size() == 0) {
    		res[0] = "OK";
    		res[1] = "0";
    		return res;
    	}
    	
    	if (returnCode.containsKey("FATAL")) {
    		res[0] = "FATAL";
    		res[1] = returnCode.get("FATAL").toString();
    		return res;
    	}
    	
    	if (returnCode.containsKey("ERROR")) {
    		res[0] = "ERROR";
    		res[1] = returnCode.get("ERROR").toString();
    		return res;
    	}
    	
    	if (returnCode.containsKey("WARN")) {
    		res[0] = "WARN";
    		res[1] = returnCode.get("WARN").toString();
    		return res;
    	}
    	
    	
    
    	
    	
    	
    	final List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(returnCode.entrySet());
    	Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
    	    public int compare(final Entry<String, Integer> e1, final Entry<String, Integer> e2) {
    	      return e2.getValue().compareTo(e1.getValue());
    	    }
    	  });
    	
    	res[0] = entries.get(0).getKey();
    	res[1] = entries.get(0).getValue().toString();
    	return res;
    
    	
    }

    /**
     * Gets return code number.
     * @return Return code
     */
	public static int getReturnCode() {
		return Integer.parseInt(getReturnCodeArr()[1]);
    	
	}
    


    /**
     * Output trace level message.
     *
     * @param message
     *           log message.
     */
    public static void trace(final Object message) {
    	 add(ConstanteLevel.TRACE, message.toString(), false);
    }
    
   

    /**
     * Output debug level message.
     *
     * @param message
     *           log message.
     */
    public static void debug(final Object message) {
    	 add(ConstanteLevel.DEBUG, message.toString(), false);
    }
    
   

    /**
     * Output info level message.
     *
     * @param message
     *           log message.
     */
    public static void info(final Object message) {
    	 add(ConstanteLevel.INFO, message.toString(), false);
    }
    
    /**
     * Output warn level message.
     *
     * @param message
     *           log message.
     */
    public static void warn(final Object message) {
        add(ConstanteLevel.WARN, message.toString());
    }
    
    
    /**
     * Output error level message.
     *
     * @param message
     *           log message.
     */
    public static void error(final Object message) {
    	 add(ConstanteLevel.ERROR, message.toString());
    }
    
   

    /**
     * Output fatal level message.
     *
     * @param message
     *           log message.
     */
    public static void fatal(final Object message) {
    	 add(ConstanteLevel.FATAL, message.toString());
    }
    
    
    /**
     * Adds new message to log.
     * 
     * @param level Object of log level
     * @param message Message
     */
    public static void add(final CustomLevel level, final String message) {
    	add(level, message, true);
    }
    
    /**
     *  Adds new message to log (general signature).
     *  
     * @param level level Object of log level
     * @param message Message
     * @param isUi true to save messge for logform
     */
    public static void add(final CustomLevel level, final String message, final boolean isUi) {
    	String msg = "";
    	
    	Date todaysDate = new java.util.Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    	String formattedDate = formatter.format(todaysDate);
    	
    	try {
        	msg = new String(message.getBytes("UTF-8"));
        } catch (Exception e) {
    		System.out.println("warn => " + e.getMessage());
    		return;
    	}
    	
    	msg = formattedDate + " " + msg;
    	
    	if (logfileActivation) {
    		if ((level.equals(ConstanteLevel.WARN.getLevelString()) || level.equals(ConstanteLevel.ALL))  || !isSeparate) {
    			loggerFile.log(level, msg);
    		}
    	}
    	
    	if (printActivation) {
    		if ((printLevel.equals(ConstanteLevel.WARN.getLevelString())	|| level.equals(ConstanteLevel.ALL)) || !printIsSeparate) {
    			loggerPrint.log(level, msg);
    		}
    	}
    	if (level.getReturnCode() != 0) {
    		returnCode.put(level.getLevelString(), level.getReturnCode());
    	}
    	
    	if (isUi) {
    		String[] uiMsg = {level.getLevelString(), msg};
    		uiMessages.add(uiMsg);
    	}
    }
    
    
    public static void returnCodeInit() {
    	returnCode = new HashMap<String, Integer>();
    }
           
  
   
}
