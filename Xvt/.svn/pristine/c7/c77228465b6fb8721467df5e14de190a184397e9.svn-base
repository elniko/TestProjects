package ubpartner.xvt.validation.commun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ubmatrix.xbrl.common.src.Configuration;
import ubmatrix.xbrl.common.utility.src.CommonUtilities;
import ubmatrix.xbrl.src.Xbrl;
import ubmatrix.xbrl.validation.formula.src.FormulaConfiguration;
import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.xvt.commun.Constante;
import ubpartner.xvt.commun.xml.ConstanteXml;
import ubpartner.xvt.validation.Validation;
import ubpartner.xvt.validation.ValidationException;

/**
 * Class for the service methods for validation.
 * @author UBPartner
 */
public final class ValidationUtils {

    /**
     * Empty constructor for the final class.
     * 
     */
    private ValidationUtils() {
	// Class cannot be Instantiated.
    }

    /**
     * XBRL validation options.
     */
    private static HashMap<String, String> optionXbrl;

    /**
     * Dimension validation options.
     */
    private static HashMap<String, String> optionAgDim;

    /**
     * Path to the XPE processor.
     */
    private static String mBasePath;
    
    /**
     * XPE XBRL object.
     */
    private static Xbrl xbrl;

    /**
     * Path to taxonomy in XPE memory.
     */
    private static String pathTaxoEnMemoire;

    /**
     * Formula validation configuration.
     */
    private static FormulaConfiguration config;
    
    
    /**
     * Getter for the XBRL object.
     * @return the XBRL object.
     */
    public static Xbrl getXbrl() {
	return xbrl;
    }

    /**
     * Getter for formula configuration.
     * @return Formula Configuration.
     */
    public static FormulaConfiguration getConfig() {
	return config;
    }

    /**
     * Getter for XBRL validation option.
     * @return true - if XBRL validation is activated.
     */
    public static HashMap<String, String> getOptionXbrl() {
	return optionXbrl;
    }

    /**
     * Getter for aggregation validation option.
     * @return true - aggregation validation activated.
     */
    public static HashMap<String, String> getOptionAgDim() {
	return optionAgDim;
    }

    /**
     * Initialize the options for validation Aggregation and Dimension.
     * @param currConfig - XPE configuration object.
     */
    private static void initOptionAggDim(final XpeConfig currConfig) {
	LogManagement.debug("Initialize the options for aggregation and dimension validation.");
	optionAgDim = currConfig.getDimOptions();
    }

    /**
     * Initialize the options for validation XBRL.
     * @param currConfig - XPE configuration object.
     */
    private static void initOptionXBRL(final XpeConfig currConfig) {
	LogManagement.debug("Initialize the options for XBRL Validation.");
	optionXbrl = currConfig.getXbrlOptions();
    }

    /**
     * Load instance in XPE.
     * @param in - instance file.
     * @throws Exception - IO exception, JDOM Exception.
     * 
     */
    public static void load(final File in) throws Exception {
	try {
	    if (xbrl.getDTS() != null) {
		xbrl.getDTS().clearValidationData();
	    }
	    LogManagement.debug("File loading");
	    Date d1 = new Date();
	    if (xbrl.load(in.getAbsolutePath())) {
		Date d2 = new Date();
		xbrl.compileFormulas();
		String[] memos = xbrl.getMemos();
		if (null != memos && memos.length > 0) {
		    for (String memo : memos) {
			throw new ValidationException(memo);
		    }
		}
		LogManagement.info("File loading timer " + CommonUtilities.diffDates(d2, d1));
	    } else {
		String[] memos = xbrl.getMemos();
		if (null != memos && memos.length > 0) {
		    for (String memo : memos) {
			throw new ValidationException(memo);
		    }
		}
	    }
	} catch (Exception e) { 
	    if (e.getMessage().contains("memo://ubmatrix.com/Xbrl#OutOfMemoryError")) {
		throw new Exception(Constante.MSGOUTOFMEMORY);
	    } else {
		LogManagement.error("load => " + e.getMessage());
		throw new Exception(e);
	    } 
	} catch (Error e) {
	    if (e.getMessage().contains("memo://ubmatrix.com/Xbrl#OutOfMemoryError")) {
		throw new Error(Constante.MSGOUTOFMEMORY);
	    } else {
		LogManagement.fatal("load => " + e.getMessage());
		throw new Error(e);
	    }
	}
    }

    /**
     * chargerTaxo.
     * 
     * @param taxoPath
     *            taxoPath.
     * 
     */
    public static void chargerTaxo(final String taxoPath) {
	LogManagement.debug("Taxonomies loading : " + taxoPath);
	if (pathTaxoEnMemoire != null && pathTaxoEnMemoire.equals(taxoPath)) {
	    LogManagement.info("Taxonomy already in memory");
	    return;
	} else {
	    closeDTS();
	    LogManagement.warn("The Taxonomy is not in memory");
	}

	config = new FormulaConfiguration();
	try {
	    Date d1 = new Date();
	    if (!xbrl.load(taxoPath)) {
		LogManagement.warn("Taxonomy loading error");
		if (xbrl.getMemos() != null) {
		    for (String memo : xbrl.getMemos()) {
			LogManagement.warn(memo);
		    }
		}
	    } else {
		pathTaxoEnMemoire = taxoPath;
		Date d2 = new Date();
		LogManagement.info("Taxonomy loading timer "
			+ CommonUtilities.diffDates(d2, d1));
	    }
	} catch (Exception e) {
	    LogManagement.error("chargerTaxo => Taxonomy loading error");
	    throw new RuntimeException(new ValidationException(e));
	} catch (Error e) {
	    LogManagement.fatal("chargerTaxo => Taxonomy loading error");
	    throw new Error(e);
	}
    }

    /**
     * Close DTS.
     */
    protected static void closeDTS() {
	LogManagement.debug("Close DTS");
	try {
	    if (!xbrl.close()) {
		LogManagement.warn("Error during taxonomy closure");
	    }
	} catch (Exception e) {
	    LogManagement.error("closeDTS => Error during taxonomy closure");
	} catch (Error e) {
	    LogManagement.fatal("closeDTS => Error during taxonomy closure");
	}
    }

    /**
     * Shutting down the XPE processor.
     */
    public static void shutdownDTS() {
	LogManagement.info("Shutdown DTS");
	try {
	    if (!xbrl.Shutdown()) {
		LogManagement.warn("Error when closing processors XPE");
	    }
	    xbrl = null;
	    config = null;
	} catch (Exception e) {
	    LogManagement.error("shutdownDTS => Error when closing processors XPE");
	} catch (Error e) {
	    LogManagement.fatal("shutdownDTS => Error when closing processors XPE");
	}
    }
    /**
     * Creates a validation report with typed errors.
     * @param instance - instance file.
     * @param validErr - general errors array.
     * @param suffix - suffix to add to output file.
     * @param outputDirectory - output directory.
     * @param append - append the errors to existing file.
     * @param mode - xbrl/dimension.
     * @param instancePath - path to the initial instance file.
     * @throws Exception - exception.
     */
    public static void createResultValidation(final File instance, final ArrayList<GeneralError> validErr,
	   final String suffix, final String outputDirectory, final boolean append, final String mode, final String instancePath) throws Exception {
		LogManagement.debug("Create validation result.");
		Element root = null;
		Document trace = null;
		boolean teststylesheet = false;
		SimpleDateFormat formatdate = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
		String path = instance.getParent();
		if (!StringUtils.isEmpty(outputDirectory)) {
		    path = outputDirectory;
		}
		String name = instance.getName() + "_KO-" + mode + "_" + suffix + ".xml";
		
		Map<String, String> mapInstruction = new HashMap<String, String>();
		String stylesheetPath = ConstanteUtils.getCanonicalPath("./", new File(Constante.STYLEPATH).getPath());
		mapInstruction.put("href", "file:/" + stylesheetPath.replace("\\", "/"));
		mapInstruction.put("type", "text/xsl");
		ProcessingInstruction xmlOutInstruction = new ProcessingInstruction("xml-stylesheet", mapInstruction);
		
		if (!append) {
		    trace = new Document();
		    Comment xmlOutComment = new Comment(ConstanteXml.XMLCOMMENTCREA);
		    trace.addContent(xmlOutComment);
		    trace.addContent(1, xmlOutInstruction);
		    trace.setRootElement(new Element("Result"));
		    root = trace.getRootElement();
		    Element dateNode = new Element("reportDate");
		    dateNode.setText(formatdate.format(new Date()));
		    root.addContent(dateNode);
		    Element docNode = new Element("instance");
		    docNode.setText("file:/" + instancePath.replace("\\", "/"));
		    root.addContent(docNode);
		} else {
		    SAXBuilder sb = new SAXBuilder();
		    trace = sb.build(new File(path + "/" + name));
		    for (Object itElement : trace.getContent()) {
			    try {
				ProcessingInstruction xmlOutInstruction2 = (ProcessingInstruction) itElement;
				if (xmlOutInstruction2.getTarget().equals("xml-stylesheet")) {
				    teststylesheet = true;
				    break;
				}
			    } catch (ClassCastException e) {
				LogManagement.debug("createValidationResults => " + e.getMessage());
			    } catch (Error e) {
				LogManagement.fatal("createValidationResults => " + e.getMessage());
			    }
		    }
		    if (!teststylesheet) {
			trace.addContent(1, xmlOutInstruction);
		    }
		    root = trace.getRootElement();
		    
		}
		Element tmpEl;
		
		for (GeneralError ge : validErr) {
		    tmpEl = ge.toNode();
		    if (tmpEl != null) {
			root.addContent(tmpEl);
		    }
		}
	
		Format restitFormat = Format.getPrettyFormat();
		restitFormat.setEncoding("UTF-8");
	
		XMLOutputter output = new XMLOutputter(restitFormat);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(path + "/" + name), "UTF-8"));
		output.output(trace, writer);
		writer.close();

    }
    /**
     * Delete validation result.
     * 
     * @param instance - instance xbrl.
     * @param mode - mode (xbrl, dim, formula).
     * @param suffix - suffix added to output file.
     * @param outputDirectory - output folder.
     * @return true - if successfully deleted.
     * 
     * @throws Exception
     *             Exception.
     */
    public static boolean deleteResultValidation(final File instance,
	    final String mode, final String suffix, final String outputDirectory) throws Exception {
		LogManagement.debug("Delete validation result.");
		String path = outputDirectory;
		String name = instance.getName() + "_KO-" + mode + "_" + suffix + ".xml";
		File file = new File(path + "/" + name);
		return file.delete();
    }

    /**
     * Update and format XPE trace files.
     * 
     * @param instance
     *            instance xbrl.
     * @param suffix
     *            suffix to add.
     * @param outputDirectory
     *            directory for result.
     * @param instancePath - path to the initial instance.
     * @return true if successfully updated XPE validation report.
     * 
     * @throws Exception
     *             exception.
     */
    public static boolean updateTraceXpe(final File instance, final String suffix,  final String outputDirectory, final String instancePath) throws Exception {
		LogManagement.debug("Update trace XPE.");
		boolean teststylesheet = false;
		
		SAXBuilder sb = new SAXBuilder();
	
		String path = outputDirectory;
		String nameXpe = instance.getName() + "_VALIDATION-RES" + suffix + ".xml";
	
		if (!new File(path + "/" + nameXpe).exists()) {
		    return false;
		}
		//deleting special symbols from addresses.
	    deleteEtFromFormula(new File(path + "/" + nameXpe), "formula");
	    //write XPE report.
		Document traceXpe = sb.build(new File(path + "/" + nameXpe));
		
		Comment xmlOutComment = new Comment(ConstanteXml.XMLCOMMENTCREA);
		traceXpe.addContent(0, xmlOutComment);
		
		for (Object itElement : traceXpe.getContent()) {
		    try {
			ProcessingInstruction xmlOutInstruction2 = (ProcessingInstruction) itElement;
			if (xmlOutInstruction2.getTarget().equals("xml-stylesheet")) {
			    teststylesheet = true;
			    break;
			}
		    } catch (ClassCastException e) {
			LogManagement.debug("updateTraceXpe => " + e.getMessage());
		    } catch (Error e) {
			LogManagement.fatal("updateTraceXpe => " + e.getMessage());
		    }
		}
		
		//update instance path
		try {
	        	Element rootNode = traceXpe.getRootElement();
	        	if (!rootNode.getName().equals("validationTrace")) {
	        	    rootNode = rootNode.getChild("validationTrace");
	        	}
	        	rootNode.setAttribute("baseURI", instancePath.replace("\\", "/"));
	        	rootNode.setAttribute("applicationName", String.valueOf(Constante.NOMAPPLICATION));
	        	rootNode.setAttribute("applicationVersion", String.valueOf(Constante.VERSIONAPPLICATION));
		} catch (Exception e) {
		    LogManagement.warn("Failed to update instance reference in the Formula result file");
		}
		
		Format restitFormat = Format.getPrettyFormat();
		restitFormat.setEncoding("UTF-8");
	
		XMLOutputter output = new XMLOutputter(restitFormat);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(path + "/" + nameXpe), "UTF-8"));
		output.output(traceXpe, writer);
		writer.close();
		sb = null;
		
		return true;
    }
    /**
     * Initialization XPE.
     * @param currValid - validation object.
     */
    public static void initialize(final Validation currValid) {
    	initialize(currValid, "");
    }
    /**
     * Initialization XPE.
     * @param currValid - validation object.
     * @param taxoId - ID of the current taxonomy.
     */
    public static void initialize(final Validation currValid, final String taxoId) {
	LogManagement.info("Initialization XPE");
	Configuration configInstance = Configuration.getInstance();
	/*LogManagement.info("Thread instance");
	Configuration cconfig = Configuration.createThreadInstance();
	cconfig.setModel(Configuration.s_vom);
	LogManagement.info("Core root");
	mBasePath = cconfig.getCoreRoot();
	LogManagement.info("XPE path = [" + mBasePath + "]");
	LogManagement.info("Cache");
	if (StringUtils.isNotEmpty(taxoId)) {
		if (!currValid.getConfigOptions().getCacheLocations().isEmpty()) {
		    	String cache = currValid.getConfigOptions().getCacheLocations().get(taxoId);
		    	if (StringUtils.isNotEmpty(cache)) {
		            cconfig.setProperty(Configuration.c_cacheLocation, mBasePath + "/" + cache);
		    	}
		}
	}
	LogManagement.info("XPE config");
	XpeConfig currXpeConf = currValid.getConfigOptions();
	initOptionXBRL(currXpeConf);
	initOptionAggDim(currXpeConf);
	LogManagement.info("Language");
	cconfig.setLanguage(currXpeConf.getLang());*/
	configInstance.setModel(Configuration.s_vom);
	mBasePath = configInstance.getCoreRoot();
	LogManagement.info("XPE path = [" + mBasePath + "]");
	if (StringUtils.isNotEmpty(taxoId)) {
		if (!currValid.getConfigOptions().getCacheLocations().isEmpty()) {
		    	String cache = currValid.getConfigOptions().getCacheLocations().get(taxoId);
		    	if (StringUtils.isNotEmpty(cache)) {
		    		configInstance.setProperty(Configuration.c_cacheLocation, mBasePath + "/" + cache);
		    		LogManagement.info("Cache location changed to: " + mBasePath + "/" + cache);
		    	}
		}
	}
	XpeConfig currXpeConf = currValid.getConfigOptions();
	initOptionXBRL(currXpeConf);
	initOptionAggDim(currXpeConf);
	configInstance.setLanguage(currXpeConf.getLang());
	xbrl = Xbrl.newInstance();
	try {
	    xbrl.Initialize();
	    config = new FormulaConfiguration();
	} catch (Exception e) {
	    LogManagement.error("initialize => XPE initialisation error");
	    e.printStackTrace();
	    throw new RuntimeException(new ValidationException(e));
	} catch (Error e) {
	    LogManagement.fatal("initialize => XPE initialisation error");
	    throw new Error(e);
	}
	LogManagement.info("Processor initialised");
    }
    
    /**
     * Deactivate formula partitioning if is activated.
     * @return true if partitioning was deactivated.
     * 
     * @exception Exception.
     */
    public static Boolean deactivateFormulaPartitioning() {
	LogManagement.debug("Desactivate formula partitionning if is activated.");
	try {
	    Configuration configInstance = Configuration.getInstance();
	    if (configInstance
		    .getFeature(Configuration.c_useFormulaPartitioning)) {
		configInstance.setFeature(
			Configuration.c_useFormulaPartitioning, false);
		LogManagement.info(ubpartner.xvt.commun.Constante.MSGINFOFORMULAPARTITIONING
				+ configInstance
					.getFeature(Configuration.c_useFormulaPartitioning
						.toString()));
		configInstance.setModel(Configuration.s_vom);
		return true;
	    }
	    return false;
	} catch (Exception e) {
	    LogManagement.error("deactivateFormulaPartitioning => Deactivate formula partitionning error");
	    e.printStackTrace();
	    throw new RuntimeException(new ValidationException(e));
	} catch (Error e) {
	    LogManagement.fatal("deactivateFormulaPartitioning => Deactivate formula partitionning error");
	    throw new Error(e);
	}
    }
    
    /**
     * Activate formula partitioning.
     * 
     * @exception Exception.
     */
    public static void activateFormulaPartitioning() {
	LogManagement.debug("Activate formula partitionning.");
	try {
	    Configuration configInstance = Configuration.getInstance();
	    configInstance.setFeature(Configuration.c_useFormulaPartitioning,
		    true);
	    LogManagement.debug(ubpartner.xvt.commun.Constante.MSGINFOFORMULAPARTITIONING
		    + configInstance
		    .getFeature(Configuration.c_useFormulaPartitioning
			    .toString()));
	    configInstance.setModel(Configuration.s_vom);
	} catch (Exception e) {
	    LogManagement.error("activateFormulaPartitioning => Activate formula partitionning error");
	    e.printStackTrace();
	    throw new RuntimeException(new ValidationException(e));
	} catch (Error e) {
	    LogManagement.fatal("activateFormulaPartitioning => Activate formula partitionning error");
	    throw new Error(e);
	}
    }
    
    /**
     * Replaces an "&" in the input path written in formula file.
     * @param formulaRes - file with formula results.
     * @param fileType - "formula" or "instance".
     */
    public static void deleteEtFromFormula(final File formulaRes, final String fileType) {
	String nextLine;
	String reBaseURI = ".*<formulaTrace.*baseURI=\"(.*.x(br|m)l)\" xsi:schemaLocation.*";
	String reBaseURI2 = ".*<formulaTrace.*baseURI=\"(.*.x(br|m)l)\" xmlns:xlink.*";
	String reBaseURI3 = ".*<formulaTrace.*baseURI=\"(.*.x(br|m)l)\" xmlns:link.*";
	String reInst = ".*<link:schemaRef.*xlink:href=\"(.*.xsd)\" xlink:type=";
	ArrayList <String>contentArr = new ArrayList<String>();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(formulaRes));
	    while ((nextLine = br.readLine()) != null) {
		contentArr.add(nextLine);
	    } 
	    br.close();
	    if (fileType.equals("formula")) {
        	ConstanteUtils.deleteEt(contentArr, reBaseURI2);
        	ConstanteUtils.deleteEt(contentArr, reBaseURI);
        	ConstanteUtils.deleteEt(contentArr, reBaseURI3);
	    } else {
		ConstanteUtils.deleteEt(contentArr, reInst);
	    }
	    BufferedWriter bw = new BufferedWriter(new FileWriter(formulaRes));
	    for (String contLine : contentArr) {
		bw.write(contLine);
	    }
	    bw.flush();
	    bw.close();
	} catch (Exception e) {
	    LogManagement.debug("deleteEtFormFormula => failed to read formula file");
	} catch (Error e) {
	    LogManagement.debug("deleteEtFormFormula => failed to read formula file");    
	}
    }
}
