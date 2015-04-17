package ubpartner.logmanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import ubpartner.logmanagement.commun.Constante;
import ubpartner.logmanagement.commun.ConstantePrintLogProperties;
import ubpartner.logmanagement.commun.ConstanteXMLLogProperties;

/**
 * Class to manage log4j configuration.
 */
public final class LMConfiguration {

    /**
     * Output log message level.
     */
    private static String level;
    
    /**
     * Output log message level for printing out.
     */
    private static String printLevel;
    
	/**
     * Activation for generated log file.
     */
	private static boolean logfileActivation;

	/**
     * Activation for printing out log messages.
     */
	private static boolean printActivation;

    /**
     * Output log file path.
     */
    private static String outpath;

    /**
     * Class cannot be Instantiated.
     */
    private LMConfiguration() {
        // Class cannot be Instantiated.
    }

    /**
     * Log message level getter.
     *
     * @return level.
     */
    protected static String getLevel() {
        return level;
    }

    /**
     * Log message level setter.
     *
     * @param levelstr
     *           level string.
     */
    protected static void setLevel(final String levelstr) {
        level = levelstr;
    }

    /**
     * Log message level setter for printing out.
     *
     * @param printLevelstr
     *           level string.
     */
    protected static void setPrintLevel(final String printLevelstr) {
    	printLevel = printLevelstr;
    }

    /**
     * Log message level getter for printing out.
     *
     * @return level.
     */
    protected static String getPrintLevel() {
    	return printLevel;
    }

    /**
     * Generated log file activation setter.
     *
     * @param logfileActivate
     *            if true then activate generated log file.
     */
    protected static void setLogfileActivation(final boolean logfileActivate) {
    	logfileActivation = logfileActivate;
    }

    /**
     * Generated log file activation getter.
     *
     * @return log file activation turned on or off.
     */
    public static boolean getLogfileActivation() {
    	return logfileActivation;
    }

    /**
     * Printing out log messages activation setter.
     *
     * @param printActivate
     *            if true then activate printing out log messages.
     */
    protected static void setPrintActivation(final boolean printActivate) {
    	printActivation = printActivate;
    }

    /**
     * Printing out log messages activation getter.
     *
     * @return Printing out log messages activation turned on or off.
     */
    public static boolean getPrintActivation() {
    	return printActivation;
    }
    

    /**
     * Log file output path getter.
     *
     * @return output path.
     */
    public static String getOutpath() {
        return outpath;
    }

    /**
     * Log file output path setter.
     *
     * @param outpathstr
     *           output path string.
     */
    public static void setOutpath(final String outpathstr) {
        outpath = outpathstr;
    }

    /**
     * Configure properties for file XMLLogProperties.
     * 
     * @return properties.
     */
    private static Properties configXMLLogProperties() {
    	Properties props = new Properties();
    	props.setProperty(ConstanteXMLLogProperties.ROOTLOGGERPROP,
    			ConstanteXMLLogProperties.ROOTLOGGERVALUE);
    	props.setProperty(ConstanteXMLLogProperties.FILEAPPENDERPROP,
                ConstanteXMLLogProperties.FILEAPPENDERVALUE);
        props.setProperty(ConstanteXMLLogProperties.THRESHOLDPROP, level);
        props.setProperty(ConstanteXMLLogProperties.LAYOUTPROP,
                ConstanteXMLLogProperties.LAYOUTVALUE);
        props.setProperty(ConstanteXMLLogProperties.IMMEDIATEFLUSHPROP,
                ConstanteXMLLogProperties.IMMEDIATEFLUSHVALUE);
        props.setProperty(ConstanteXMLLogProperties.APPENDPROP,
                ConstanteXMLLogProperties.APPENDVALUE);
        props.setProperty(ConstanteXMLLogProperties.FILEPROP, outpath);
        return props;
    }

    /**
     * Configure properties for file printLogProperties.
     * 
     * @return properties.
     */
    private static Properties configPrintLogProperties() {
    	Properties props = new Properties();
    	props.setProperty(ConstantePrintLogProperties.ROOTLOGGERPROP,
    			ConstantePrintLogProperties.ROOTLOGGERVALUE);
    	props.setProperty(ConstantePrintLogProperties.CONSOLEPROP,
    			ConstantePrintLogProperties.CONSOLEVALUE);
        props.setProperty(ConstantePrintLogProperties.TARGETPROP,
        		ConstantePrintLogProperties.TARGETVALUE);
        props.setProperty(ConstantePrintLogProperties.CONSOLELAYOUTPROP,
        		ConstantePrintLogProperties.CONSOLELAYOUTVALUE);
        props.setProperty(ConstantePrintLogProperties.CONVERSIONPATTERNPROP,
        		ConstantePrintLogProperties.CONVERSIONPATTERNVALUE);
        props.setProperty(ConstantePrintLogProperties.CONSOLETHRESHOLDPROP, printLevel);
    	return props;
    }

    /**
     * Set property values in configuration file.
     */
    protected static void geneLogConfig() {
    	File tmpdir = new File(Constante.CONFIGPATH);
    	if (!tmpdir.isDirectory()) {
    		if (!tmpdir.mkdirs()) {
    			System.out.println("Unsucessfully creating temp dir for log4j properties.");
    		}
    	}
        try {
        	if (logfileActivation) {
        		FileOutputStream fos = new FileOutputStream(
        				new File(Constante.CONFIGPATH + Constante.XMLLOGCONFIGNAME));
        		Properties props = configXMLLogProperties();
                props.store(fos, ConstanteXMLLogProperties.SETCONFIGCOMMENT);
                fos.close();
        	}
        	if (printActivation) {
        		FileOutputStream fos = new FileOutputStream(
                        new File(Constante.CONFIGPATH + Constante.PRINTLOGCONFIGNAME));
        		Properties props = configPrintLogProperties();
                props.store(fos, ConstantePrintLogProperties.SETCONFIGCOMMENT);
                fos.close();
        	}
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reset property values in configuration file.
     */
    protected static void deleLogConfig() {
    	if (logfileActivation) {
            File config = new File(Constante.CONFIGPATH
                    + Constante.XMLLOGCONFIGNAME);
            if (!config.delete()) {
                System.out.println(Constante.CONFIGPATH
                    + Constante.XMLLOGCONFIGNAME + " is deleted unsuccessfully.");
            }
    	}
    	if (printActivation) {
    		File config = new File(Constante.CONFIGPATH
                    + Constante.PRINTLOGCONFIGNAME);
            if (!config.delete()) {
                System.out.println(Constante.CONFIGPATH
                    + Constante.PRINTLOGCONFIGNAME + " is deleted unsuccessfully.");
            }
    	}
    }
}
