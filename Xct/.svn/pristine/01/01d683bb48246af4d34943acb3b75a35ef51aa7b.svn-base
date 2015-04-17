package ubpartner.xct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONObject;

import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.commun.ConstanteLevel;
import ubpartner.lst.LstXmlDate;
import ubpartner.rmt.ExecRmt;
import ubpartner.rmt.RmtMainProcess;
import ubpartner.rmt.config.HeaderConfig;
import ubpartner.taxonomymanagement.ConfiguratedTaxonomies;
import ubpartner.taxonomymanagement.ConfiguratedTaxonomy;
import ubpartner.taxonomymanagement.DeclaratifTable;
import ubpartner.taxonomymanagement.ParallelUnzip;
import ubpartner.taxonomymanagement.ZipManagement;
import ubpartner.threads.ThreadRunner;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.commun.CsvInput;
import ubpartner.utils.commun.ReturnModeTaxoTools;
import ubpartner.utils.commun.UnsatisfiedAssertion;
import ubpartner.xct.commun.Constante;
import ubpartner.xct.commun.ConstanteMessages;
import ubpartner.xct.commun.ConstanteXmlMapping;
import ubpartner.xct.integratXvt.IXctXvtXmlInstance;
import ubpartner.xmt.XmtSDK;
import ubpartner.xvt.validation.Validation;
import ubpartner.xvt.validation.commun.ValidationUtils;
import ubpartner.utils.report.HtmlReport;
import ubpartner.utils.xbrl.TaxoTools;
import ubpartner.utils.xbrl.XbrlInstance;
/**
 * Class for launching conversion.
 * @author UBPartner
 *
 */
public class ExecXct {
    /**
     * Arguments declaration array.
     */
    private enum ARGS { c, i, tm, t, em, e, E, dm, sd, ed, o, v, fm, fi,  help , s, ov, sc, u, um, ml, f, lg, ch, cs, h, uo, ti, tim, uc, chp, cm, p, rt, rp};
    /**
     * Taxonomy identifier.
     */
   private String taxoId;
    /**
     * Entity.
     */
    private String entity;
    /**
     * Schema entity.
     */
    private String entityScheme = "";
    /**
     * Start date.
     */    
    private String startDate;
    /**
     * End date.
     */
    private String endDate;
    /**
     * Mapping file.
     *
     */
    private File mappingFile;
    /**
     * Configuration file.
     *
     */
    private File configFile;
    /**
     * Convert all the tabs or not.
     *
     */
    private String subsetConversion = "n";
    /**
     * Contexts file.
     *
     */
    private File contextsFile;
    /**
     * Contexts document.
     *
     */
    private Document contextsDoc;
    /**
     * Excel file with values.
     *
     */
    private File excelFile;
    /**
     * Input folder.
     *
     */
    private File inputDir;
    /**
     * Zip file with settings files.
     *
     */
    private File zipFile;
    /**
     * Output instance file.
     *
     */
    private File outPutFile;
    /**
     * Validation active or not?
     */
    private boolean validate = false;
    /**
     * Folder to unzip files.
     */
    private String temporaryDir;
    /**
     * Taxonomy label input mode.
     */
    private String taxoInputMode;
    /**
     * Entity input mode.
     */
    private String entityInputMode;
    /**
     * Dates input mode.
     */
    private String periodInputMode;
    /**
     * Taxonomy collection from TAXO_config.xml.
     */
    private ConfiguratedTaxonomies taxos;
    /**
     * Current taxonomy.
     */
    private ConfiguratedTaxonomy currTaxo;
    /**
     * Map that contains input Excel files list either by table or by output file name.
     */
    private HashMap<String, String> inputMapById = new HashMap<String, String>();
    /**
     * Configuration file for header.
     */
    private File headerConfig;
    /**
     * Flag to decide add header or not.
     */
    private boolean useHeader = false;
	/**
     * Mapping Object.
     */
    private  MappedFacts mapping = new MappedFacts();
    /**
     * Suffix for validation file naming.
     */
    private String suffix = "";
    /**
     * Monetary units measure.
     */
    private String units = "";
    /**
     * Monetary units measure input mode.
     */
    private  String unitsMode = "input";
    /**
     * DEfault units.
     */  
    private String defaultUnits;
    /**
     * Folder for validation results.
     */
    private File outputFolder;
    /**
    * Object Excel File.
    */
    private XSSFWorkbook excelwbk;
    /**
     * XPE configuration file.
     */
    private File xpeServiceConfig = null;   
    /**
     * Contains all excel files to convert.
     */
    private ArrayList<File> allExcels = new ArrayList<File>();
   /**
    * Declarative flag.
    */
    private int declFlag = 0;
    /**
     * Country of the reporting.
     */
    private String country = "FR";
    /**
     * Map to associate taxonomy Id, mapping and path to excel and excel object.
     */
    private HashMap<String, HashMap<String, ArrayList<Object[]>>> mapTaxoMapping
                  = new HashMap<String, HashMap<String, ArrayList<Object[]>>>();
    /**
     * Table Id for all tables (user input).
     */
    private String tableId;
    /**
     * Table Id mode for all tables (user input: address/name/ID).
     */
    private String tableIdmode;
    /**
     * Mapped fact collection with unique IDs.
     */
    private HashMap<String, ArrayList<MappedFact>> valueMapping = new HashMap<String, ArrayList<MappedFact>>();
    /**
     * Map for Ids of concepts and contexts.
     */
    private HashMap<String, Object> mapId = new HashMap<String, Object>();
    /**
     * Map with id for contexts.
     */
    private LinkedHashMap<String, ArrayList<Element>> contexts = new LinkedHashMap<String, ArrayList<Element>>();
    /**
     * Object to create and store unique Ids.
     */
    private XbrlInstance instCheat = new XbrlInstance();
    /**
     *  Default entity.
     */
    private String unEntity = "";
    /***
     * Default end date.
     */
    private String unEndDate = "";
    /**
     * Xbrl node for instance creation.
     */
    private Element xbrlNode;
    /**
     * Nodes with taxonomy shcema references.
     */
    private  ArrayList<Element> schemaLinkNodes = new ArrayList<Element>();
    /**
     * Current instance file.
     */
    private static File curroutputFile;
    /**
	 * @return the curroutputFile
	 */
	public static final File getCurroutputFile() {
		return curroutputFile;
	}
	/**
     * Template file for Xmt.
     */
    private File gabaritFile;
    /**
     * Current declaratif table.
     */
    private DeclaratifTable workTable = null;
    /**
     * Current CSV object.
     */
    private CsvInput csvFile = null;
    /**
     * Flag for mapping reloading.
     */
    private boolean reloadMap = true;
    /**
     * JSON object for user parameters.
     */
    private static JSONObject userParams;
    /**
     * User parameters file.
     */
    private static File userPropertiesFile;
    /**
     * QNames list.
     */
    private static HashMap<String, HashMap<String, HashMap<String, String>>> mapLabelsQName =
    		new HashMap<String, HashMap<String, HashMap<String, String>>>();
    /**
     * Getter for the QNames concepts and labels map.
     * @return - map with concepts, QNames and labels
     */
    public static HashMap<String, HashMap<String, HashMap<String, String>>> getMapLabelsQName() {
    	return mapLabelsQName;
    }
    /**
     * Duplicate facts list.
     */
    private static HashMap<String, ArrayList<MappedFact>> dplFactsMap = new HashMap<String, ArrayList<MappedFact>>();
    
    /**
	 * @return the dplFactsMap
	 */
	public static final HashMap<String, ArrayList<MappedFact>> getDplFactsMap() {
		return dplFactsMap;
	}
	/**
	 * @param cdplFactsMap the dplFactsMap to set
	 */
	public static final void setDplFactsMap(final HashMap<String, ArrayList<MappedFact>> cdplFactsMap) {
		ExecXct.dplFactsMap = cdplFactsMap;
	}
	/**
     * Instance language.
     */
    private static String repLang = "EN";
    /**
     * Getter for the report language.
     * @return instance label language.
     */
    public static String getRepLang() {
    	return repLang;
    }
    /**
     * Units.
     */
    private ArrayList<Element> unitNodes = new ArrayList<Element>();
    /**
     * Declarative tables ufor current taxonomy.
     */
    private HashMap<String, DeclaratifTable> declTblAll = new HashMap<String, DeclaratifTable>();
    /**
     * Declarative tables used in current submission.
     */
    private HashMap<String, String> declTblIds = new HashMap<String, String>();
    /**
     * Declarative tables not used in current submission.
     */
    private HashMap<String, String> declTblNotConverted = new HashMap<String, String>();
    /**
     * Declarative tables skipped in current submission.
     */
    private HashMap<String, String> declTblSkippedByRemise = new HashMap<String, String>();
    /**
     * Taxonomy namespaces and concepts map.
     */
    private Map<ReturnModeTaxoTools, Object> taxoNmsMap;
    /**
     * Use or not application cache.
     */
    private boolean usecache = false;
    /**
     * Path to cache folder.
     */
    private String cachePath = "";
    /**
     * Custom messages file.
     */
    private File customMessages = null;
    /**
     * Getter for entity.
     * @return String entity
     */
    public final String getEntity() {
        return entity;
    }
    /**
     * Getter for start date.
     * @return String start date as "yyyy-mm-dd"
     */
    public final String getStartDate() {
        return startDate;
    }
    /**
     * Getter for entity URL.
     * @return String entity URL.
     */
    public final String getEntityScheme() {
        return entityScheme;
    }
    /**
     * Getter for end date.
     * @return String end date as "yyyy-mm-dd"
     */
    public  final String getEndDate() {
        return endDate;
    }
    /**
     * Getter for output file.
     * @return File output file.
     */
    public final File getOutputFile() {
        return outPutFile;
    }
    /**
     * Getter for current taxonomy.
     * @return object ConfiguratedTaxonomy
     */
    public final ConfiguratedTaxonomy getCurrTaxo() {
		return currTaxo;
	}
    /**
     * Getter for units.
     * @return String units
     */

    public  final String getUnits() {
        return units;
    }
    /**
	 * @return the mapTaxoMapping
	 */
	public final HashMap<String, HashMap<String, ArrayList<Object[]>>> getMapTaxoMapping() {
		return mapTaxoMapping;
	}
	/**
	 * @param cMapTaxoMapping the mapTaxoMapping to set
	 */
	public final void setMapTaxoMapping(final HashMap<String, HashMap<String, ArrayList<Object[]>>> cMapTaxoMapping) {
		this.mapTaxoMapping = cMapTaxoMapping;
	}
	/**
	 * @return the tableId
	 */
	public final String getTableId() {
		return tableId;
	}
	/**
	 * @param cTableId the tableId to set
	 */
	public final void setTableId(final String cTableId) {
		this.tableId = cTableId;
	}
	/**
	 * @return the tableIdmode
	 */
	public final String getTableIdmode() {
		return tableIdmode;
	}
	/**
	 * @param cTableIdmode the tableIdmode to set
	 */
	public final void setTableIdmode(final String cTableIdmode) {
		this.tableIdmode = cTableIdmode;
	}
	/**
	 * @return the usecache
	 */
	public final boolean isUsecache() {
		return usecache;
	}
	/**
	 * @param usecache the usecache to set
	 */
	public final void setUsecache(boolean usecache) {
		this.usecache = usecache;
	}
	/**
	 * @return the cachePath
	 */
	public final String getCachePath() {
		return cachePath;
	}
	/**
	 * @param cachePath the cachePath to set
	 */
	public final void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	/**
     * Method that launch the conversion with entered parameters.
     * @param sConfigFile - taxonomy configuration file.
     * @param sExcelFile - blank file.
     * @param sTaxoInputMode - taxonomy ID input mode.
     * @param sTaxoId - taxonomy ID.
     * @param sEntityInputMode - entity input mode.
     * @param sEntityValue - entity.
     * @param sEntitySchemeValue - entity URL.
     * @param sPeriodInputMode - period dates input mode.
     * @param sStartDateValue - start date.
     * @param sEndDateValue - end date.
     * @param sOutPutFileValue - output file.
     * @param sValidate - if true - the instance validation is launched.
     * @param sMappingRestricted - if true - restricted mapping is generated.
     * @param sInstanceFull - if true - full instance is generated.
     * @param sXslPath - path to the XPE xsl file.
     * @param sSuffix - suffix to add to the validation results.
     * @param sOutputFolder - folder to save the validation results.
     * @param sSubsetConversionValue - if true - then apply the conversion rules.
     * @param sUnitsValue - units.
     * @param sUnitsModeValue - units input mode.
     * @param sUseHeader - use header or not.
     * @param sHeaderConfig - header configuration file.
     * @param sFeedback - feedback flag for header.
     * @param sEmail - email for header.
     * @param sLang - language for header.
     */
    protected ExecXct(File sConfigFile, File sExcelFile, String sTaxoInputMode,
			String sTaxoId, String sEntityInputMode, String sEntityValue,
			String sEntitySchemeValue, String sPeriodInputMode,
			String sStartDateValue, String sEndDateValue, File sOutPutFileValue,
			boolean sValidate, String sSuffix, File sOutputFolder,
			String sSubsetConversionValue, String sUnitsValue,
			String sUnitsModeValue, boolean sUseHeader, File sHeaderConfig, String stableId, String stableMode,
			boolean cUseCache, String cCachePath, String cXvtXpeConfig, String customMess, String scountry, File spropFile, JSONObject suserParams) {
		this.configFile = sConfigFile;
		this.excelFile = sExcelFile;
		this.taxoInputMode = sTaxoInputMode;
		this.taxoId = sTaxoId;
		this.entityInputMode = sEntityInputMode;
		this.entity = sEntityValue;
		this.entityScheme = sEntitySchemeValue;
		this.periodInputMode = sPeriodInputMode;
		this.startDate = sStartDateValue;
		this.endDate = sEndDateValue;
		this.outPutFile = sOutPutFileValue;
		this.validate = sValidate;
		this.suffix = sSuffix;
		this.outputFolder = sOutputFolder;
		this.subsetConversion = sSubsetConversionValue;
		this.units = sUnitsValue;
		this.unitsMode = sUnitsModeValue;
		this.useHeader = sUseHeader;
		this.headerConfig = sHeaderConfig;
        this.tableId = stableId;
        this.tableIdmode = stableMode;
        this.usecache = cUseCache;
        this.cachePath = cCachePath;
        this.xpeServiceConfig = new File(cXvtXpeConfig);
        this.customMessages = new File(customMess);
        this.country = scountry;
        userParams = suserParams;
        userPropertiesFile = spropFile;
        
		taxos = new ConfiguratedTaxonomies();
		taxos.initialise(configFile);

		String ext = ConstanteUtils.getExtension(outPutFile);
		/*if ((!ext.equals("")) && (!ext.equals(Constante.XBRL)) && (!ext.equals(Constante.XML))) {
        	outPutFile = new File(outPutFile.getParent() + "/" + ConstanteUtils.getRealName(outPutFile) + "." + Constante.XBRL);
        	LogManagement.warn("Wrong output file extension! The output will be changed to " + outPutFile.getPath());
        }*/
        if ((!ext.equals(Constante.XBRL)) && (!ext.equals(Constante.XML))) {
        	outPutFile.mkdirs();
        }
		if (unitsMode.equals("")) {
			unitsMode = "input";
		}
        if (ConstanteUtils.getExtension(this.excelFile).equals("")) {
        	this.inputDir = excelFile;
        } else {
        	this.inputDir = new File(excelFile.getParent());
        }
		preparation();
	}
    
    /**
     * Method that launch the conversion with entered parameters from bat.
     * 
     * @param args
     *      argument: -c - taxonomy config file
     *      argument: -i - inputed Excel file name
     *      argument: -tm - taxonomy ID input mode
     *      argument: -t - taxonomy ID
     *      argument: -em - entity input mode
     *      argument: -e - entity
     *      argument: -E - entity schema
     *      argument: -dm - period dates input mode
     *      argument: -sd - start date 
     *      argument: -ed - end date input
     *      argument: -v - validation active
     *      argument: -o - output file path
     *      argument: -fm - mapping restricted
     *      argument: -fi - instance full
     *      argument: -sc - subset conversion
     *      argument: -u units value
     *      argument: -um units input mode
     *      argument: -x xsl path
     *      argument: -s suffix for validation
     *      argument: -ov folder for XVT results
     *      argument: -ti - table identifier
     *      argument: -tim - table identifier mode
 
     */
    public ExecXct(final String[] args) {
        if (args.length == 0) {
            System.out.println(ConstanteMessages.MSGHELP);
            return;
        } 
        String[] argsVal = args.clone();
        //getting inputed arguments
        for (int i = 0; i < argsVal.length; i++) {
           try {
            String arg = argsVal[i];
            if (arg.charAt(0) == '-') {
            	String argenum = arg.substring(1);
            	ARGS argument = null;
        		try {
        		    argument = ARGS.valueOf(argenum);
        		} catch (IllegalArgumentException e) {
        			LogManagement.debug(ConstanteMessages.MSGOPTIONINCONNU + " -" + arg);
        			argument = ARGS.uo;
        		} catch (Error e) {
        			argument = ARGS.uo;
        			LogManagement.debug(ConstanteMessages.MSGOPTIONINCONNU + " -" + arg);
        		}
        		switch (argument) {
                case c:
                    try {
                        this.configFile = new File(argsVal[i + 1]);
                        taxos = new ConfiguratedTaxonomies();
                        taxos.initialise(configFile);
                    } catch (Exception e) {
                        LogManagement.error("Failed to initialize the taxonomy configuration file! " + e.getMessage());
                    } catch (Error e) {
                    	LogManagement.error("Failed to initialize the taxonomy configuration file! " + e.getMessage());
                    }
                    break;
                case i:
                    this.excelFile = new File(argsVal[i + 1]);
                    break;
                case tm:
                    taxoInputMode = argsVal[i + 1];
                    break;               
                case t:
                    this.taxoId = argsVal[i + 1];
                    break;
                case em:
                    entityInputMode = argsVal[i + 1];
                    break;
                case e:
                    entity = argsVal[i + 1];
                    break;
                case E:
                    entityScheme = argsVal[i + 1];
                    break;
                case dm:
                    periodInputMode = argsVal[i + 1];
                    break;
                case sd:
                    startDate = argsVal[i + 1];
                    break;
                case ed:
                    endDate = argsVal[i + 1];
                    break;
                case o:
                    outPutFile = new File(argsVal[i + 1]);                   
                    String ext = ConstanteUtils.getExtension(outPutFile);
                    /*if ((!ext.equals("")) && (!ext.equals(Constante.XBRL)) && (!ext.equals(Constante.XML))) {
                    	outPutFile = new File(outPutFile.getParent() + "/" + ConstanteUtils.getRealName(outPutFile) + "." + Constante.XBRL);
                    	LogManagement.warn("Wrong output file extension! The output will be changed to " + outPutFile.getPath());
                    }*/
                    if ((!ext.equals(Constante.XBRL)) && (!ext.equals(Constante.XML))) {
                    	outPutFile.mkdirs();
                    }
                    break;
                case v:
                    if (argsVal[i + 1].equals("y")) {
                        this.validate = true;
                    }
                    break;
                case s:
                    this.suffix = argsVal[i + 1];
                    break;
                case ov:
                    this.outputFolder = new File(argsVal[i + 1]);
                    break;
                case sc:
                    subsetConversion = argsVal[i + 1];
                    break;
                case u:
                    units = argsVal[i + 1];
                    break;
                case um:
                    unitsMode = argsVal[i + 1];
                    if (unitsMode.equals("")) {
                    	unitsMode = "input";
                    }
                    break;
                case help:
                    System.out.println(ConstanteMessages.MSGHELP);
                    break;
                case ch:
                    this.headerConfig = new File(argsVal[i + 1]);
                    break;
                case h:
                	if (argsVal[i + 1].equals("y")) {
                	    this.useHeader = true;
                	} else {
                		this.useHeader = false;
                    }
                    break;
                case cs:
                	this.xpeServiceConfig = new File(argsVal[i + 1]);
                	break;
                case ti:
                	this.tableId = argsVal[i + 1];
                	break;
                case tim:
                	this.tableIdmode = argsVal[i + 1];
                	break;
                case uc:
                	i++;
                	if (argsVal[i].equals("y")) {
                	    this.usecache = true;
                	} else {
                		this.usecache = false;
                    }
                    break;
                case chp:
                	i++;
                	this.cachePath = argsVal[i];
                	break;
                case cm:
                	i++;
                	this.customMessages = new File(argsVal[i]);
                	break;
	    		case p:
	    			i++;
	    			country = argsVal[i];
	    			break;
	    		case rp:
	    			i++;
	    			userParams = new JSONObject(argsVal[i]);
	    			break;
	    		case rt:
	    			i++;
	    			userPropertiesFile = new File(argsVal[i]);
	    			break;
                default:
                	LogManagement.debug(ConstanteMessages.MSGOPTIONINCONNU + " -" + arg);
                    break;
                }
            }
           } catch (Exception e) {
               LogManagement.debug("Input arguments are incorrect. Please, verify your bat file.");
           }
        }
        preparation();
    }
    /**
     * Preparation actions (get taxonomy ID (s) to proceed, sort files).
     * @param allInstances - array with Excel files.
     * @param cOutputFile - output file.
     * @return true - if files processed ok.
     */
    private boolean processXlsFiles(final ArrayList<File> allInstances, File cOutputFile) {
    	Date start = new Date();
        String taxonomyId = "";
        String eDate = "";
        String sDate = "";
        File outFile;
        boolean isCsv = false;
        int countFichierTraite = 0;
        //boolean result;
        ConfiguratedTaxonomy cCurrTaxo;
        String currTableId;
        DeclaratifTable currTable = null;
        String currMap;
        for (File child : allInstances) {
          currTableId = "";
          if ((ConstanteUtils.getExtension(child).toLowerCase().equals(Constante.XLSX) && (!child.getName().startsWith("~"))) ||
        		  (ConstanteUtils.getExtension(child).toLowerCase().equals(Constante.CSV) && (!child.getName().startsWith("~")))) {
        	  LogManagement.info(ConstanteMessages.MSGTRAITEMENT  + ": " + child.getName());
	          if (ConstanteUtils.getExtension(child).toLowerCase().equals(Constante.XLSX)) {
        	      try {
	        		  excelwbk = new XSSFWorkbook(new FileInputStream(child));
				  } catch (Exception e) {
				      LogManagement.error("preparation => Error to load " + child.getPath());
					  continue;
				  } catch (Error e1) {
					  LogManagement.fatal("preparation => Error to load " + child.getPath());
					  continue;
				  }
	          } else {
	        	 isCsv = true;
	        	 Date start1 = new Date();
	        	 csvFile = new CsvInput(child);
	        	 currTableId = csvFile.getTableId();
	        	 LogManagement.info("CSV loaded in " + ConstanteUtils.diffDates(start1, new Date()));
	        	 Date start2 = new Date();
	        	 excelwbk = csvFile.convertToExcel(true);
	        	 LogManagement.info("CSV converted in " + ConstanteUtils.diffDates(start2, new Date()));
        	   	 if (excelwbk == null) {
        	   		LogManagement.error("preparation => Error to load " + child.getPath());
					continue; 
        	   	 }
        	  }
		     taxonomyId = ConstanteUtils.getConfigurationParametre(taxoInputMode, excelwbk, this.taxoId, ConstanteXmlMapping.ENTITYTYPE, "taxonomy");
		     sDate = ConstanteUtils.getConfigurationParametre(periodInputMode, excelwbk, this.startDate, Constante.DATETYPE, "start date");
		     eDate = ConstanteUtils.getConfigurationParametre(periodInputMode, excelwbk, this.endDate, Constante.DATETYPE, "end date");
		     LogManagement.info(child.getName() + " " + ConstanteMessages.MSGTAXOID + taxonomyId);
    	     cCurrTaxo = taxos.getTaxoByTaxoLbl(taxonomyId, sDate, eDate);
	         if (cCurrTaxo != null) {
	            if (this.declFlag == 0) {
		            if (cCurrTaxo.isDeclaratif()) {
		            	this.declFlag = 1;
		            	verifyOutput(null, outPutFile, true);
		            	cOutputFile = outPutFile;
		            	int month = ConstanteUtils.getMonthFromStringDate(eDate);
		                HashMap<String, ArrayList<String>> skiped = cCurrTaxo.getDeclTblsByMonth();
		                if (!skiped.isEmpty()) {
		                	ArrayList<String> skipedIds = skiped.get(month + "");
		                	if(skipedIds != null) {
			                	if(!skipedIds.isEmpty()) {
			                		for (String id : skipedIds) {
			                		   this.declTblSkippedByRemise.put(id, "n");
			                		}
			                	}
		                	}
		                }
		            } else {
		            	this.declFlag = 2;
		            	verifyOutput(this.excelFile, outPutFile, false);
		            }
	            }
	            if (this.declFlag == 1) {
	            	if (!isCsv) {
	            	    currTableId = ConstanteUtils.getConfigurationParametre(tableIdmode, excelwbk, this.tableId, ConstanteXmlMapping.ENTITYTYPE, "table");
	            	}
	            	declTblAll = cCurrTaxo.getDeclarTablesById();
	            	currTable = cCurrTaxo.getDeclarTablesById().get(currTableId);
	            	if (currTable == null) {
	            		LogManagement.warn("Table ID: " + currTableId + "not found. File " + child.getPath() + " will be ignored.");
	            		currMap = "";
	            	} else {
	            		   this.workTable = currTable;
	            	       currMap = currTable.getMappingFile(); 
	            	       if (currMap == null) {
	            	    	   currMap = "";
	            	    	   LogManagement.warn("No mapping file for the table " + currTable.getTableId());
	            	       }
	            	      if (!currTable.getMonthsToSubmit().isEmpty()) {
	            	    	  int month = ConstanteUtils.getMonthFromStringDate(eDate);
	            	    	  if (!currTable.getMonthsToSubmit().contains("" + month)) {
	            	    		  if (this.declTblSkippedByRemise.containsKey(currTable.getTableId())) {
	            	    			  this.declTblSkippedByRemise.remove(currTable.getTableId());
	            	    			  this.declTblSkippedByRemise.put(currTable.getTableId(), "y");
	            	    			  this.inputMapById.put(currTable.getTableId(), child.getPath());
	            	    		  }
	            	    		  LogManagement.warn("Table ID: " + currTableId + " is not to be declared on " + month + " month. File " + child.getPath() + " will be ignored.");
	            	    		  currMap = "";
	            	    		  
	            	    	  } else  {
	            	    		  if (declTblIds.containsKey(currTable.getTableId())) {
				            			currMap = "";
				            			LogManagement.warn("Table " + currTable.getTableId() + " is already present in conversion."
				            					+ "File " + child.getPath() + " will be ignored.");
				            	   } else {
				            		   declTblIds.put(currTable.getTableId(), currTable.getLibelle());
				            	   }
	            	    	  }
	            	      } else {
		            	      if (declTblIds.containsKey(currTable.getTableId())) {
			            			currMap = "";
			            			LogManagement.warn("Table " + currTable.getTableId() + " is already present in conversion."
			            					+ "File " + child.getPath() + " will be ignored.");
			            	   } else {
			            		   declTblIds.put(currTable.getTableId(), currTable.getLibelle());
			            	   }
	            	      }
	            	}
	            } else {
	            	currMap = cCurrTaxo.getMappingFile();
	            	if (currMap == null) {
         	    	   currMap = "";
         	    	   LogManagement.warn("No mapping file for the taxonomy " + cCurrTaxo.getTaxoLibelle());
         	       }
	            }
	            if (!currMap.equals("")) {
	            	 if (mapTaxoMapping.containsKey(taxonomyId)) {
	                	 HashMap<String, ArrayList<Object[]>> itemMapping = mapTaxoMapping.get(taxonomyId);
	                	 if (itemMapping.containsKey(currMap)) {
	                		 ArrayList<Object[]> itemXls = itemMapping.get(currMap);
	                		 Object[] it = {child, excelwbk, currTable, csvFile};
	                		 itemXls.add(it);
	                		 itemMapping.remove(currMap);
	                		 itemMapping.put(currMap, itemXls);
	                	 } else {
	                		 ArrayList<Object[]> itemXls = new ArrayList<Object[]>();
	                		 Object[] it = {child, excelwbk, currTable, csvFile};
	                		 itemXls.add(it);
	                		 itemMapping.put(currMap, itemXls);
	                	 }	
	                 } else if (mapTaxoMapping.isEmpty()) {
	                	 HashMap<String, ArrayList<Object[]>> itemMapping = new HashMap<String, ArrayList<Object[]>>();
	                	 ArrayList<Object[]> itemXls = new ArrayList<Object[]>();
                		 Object[] it = {child, excelwbk, currTable, csvFile};
                		 itemXls.add(it);
	                	 itemMapping.put(currMap, itemXls);
	                	 mapTaxoMapping.put(taxonomyId, itemMapping);
	                 }  else if (this.declFlag == 1) {
	                	 LogManagement.warn("All files should belong to the same taxonomy. File " + child.getPath()
	          			           + " will be ignored.");
	                 } else {
	                	 HashMap<String, ArrayList<Object[]>> itemMapping = new HashMap<String, ArrayList<Object[]>>();
	                	 ArrayList<Object[]> itemXls = new ArrayList<Object[]>();
                		 Object[] it = {child, excelwbk, null, csvFile};
                		 itemXls.add(it);
	                	 itemMapping.put(currMap, itemXls);
	                	 mapTaxoMapping.put(taxonomyId, itemMapping);
	                 }
	            	 
	            }
            } else {
            	LogManagement.warn("File " + child + " will be skipped because no taxonomy found");
            }
          }
        }
        //getting list of the non-converted tables.
        if (this.declFlag == 1) {
        	for (Entry<String, DeclaratifTable> currEnt : this.declTblAll.entrySet()) {
        		if (!this.declTblIds.containsKey(currEnt.getKey())) {
        			if (!this.declTblSkippedByRemise.containsKey(currEnt.getKey())) {
        				this.declTblNotConverted.put(currEnt.getKey(), currEnt.getValue().getLibelle());
        			} else {
        				this.declTblIds.put(currEnt.getKey(), currEnt.getValue().getLibelle());
        			}
        		}
        	}
        	this.declTblAll.clear();
        }
        for (Entry<String, HashMap<String, ArrayList<Object[]>>> entry : mapTaxoMapping.entrySet()) {
        	 LogManagement.info("Process all the excel files of taxonomy ID: " + entry.getKey());
        	 this.taxoId = entry.getKey();
        	 if (this.declFlag == 2) {
        		 valueMapping.clear();
        	 }
        	 boolean reload = true;
        	 
        	 HashMap<String, ArrayList<Object[]>> mappingMap = entry.getValue();
        	 for (Entry<String, ArrayList<Object[]>> currMapSet: mappingMap.entrySet()) {
	        	 this.reloadMap = true;
	        	 for (Object[] item : currMapSet.getValue()) { 
	        		 File inputFile = (File) item[0];
	        		 excelwbk = (XSSFWorkbook) item[1];
	        		 DeclaratifTable cTbl = (DeclaratifTable) item[2];
	        		 if (item[3] != null) {
	        			 csvFile = (CsvInput) item[3];
	        		 } else {
	        			 csvFile = null;
	        		 }
	        		 if (cOutputFile == null) {
		        			outFile = new File(outPutFile + "/" + inputFile.getName().replace(".xlsx", ".xbrl"));
		        	 } else {
		        			outFile = cOutputFile;
		        	 } 
	        		 if (cTbl != null) {
		        		 LogManagement.info("Process file for declarative table " + cTbl.getTableId());
		        		 this.inputMapById.put(cTbl.getTableId(), inputFile.getPath());
		        		 HtmlReport.setDeclaratif(true);
		        		 HtmlReport.setFileName(cTbl.getTableId() + "#" + cTbl.getLibelle());
	        		 } else {
	        			 this.inputMapById.put(outFile.getPath(), inputFile.getPath());
	        			 HtmlReport.setFileName(inputFile.getName() + "#" + HtmlReport.getTaxoname());
	        		 }
	        		 
	        		 this.mappingFile = new File(currMapSet.getKey());
	        		 try {
	        		     this.contextsFile = new File(cTbl.getContextsFile());
	        		 } catch (Exception e) {
	        			 this.contextsFile = null;
	        		 }
	        		 try {
	        		     this.gabaritFile = new File(cTbl.getTemplateFile());
	        		 } catch (Exception e) {
	        			 this.gabaritFile = null;
	        		 }
	        		 this.workTable = cTbl;
	        		 curroutputFile = outFile;
	        		 int res = traitement(excelwbk, inputFile, reload);
	        		 if ((res > 0)) {
	        			 if (this.declFlag == 2) {
	        				 posttreatment();
	        			 }
	        		 } else {
	        			 return false;
	        		 }
	        		 this.reloadMap = false;
	        		 reload = false;
	        		 
	                 if (res != -1) {
	                     countFichierTraite++;
	                 }
	        	 }
        	 }
        	 this.reloadMap = true;
        	 if (this.declFlag == 1) {
        		 posttreatment();
        	 }
        }
        if ((countFichierTraite > 0) && (!usecache)) {
	        ZipManagement.deleteDezip(this.temporaryDir);
        }
        ConstanteUtils.purgeTemp("XCT");
  	    HtmlReport.setTblsNotRestituted(this.declTblNotConverted);
  	    HtmlReport.setOutput(curroutputFile.getParent());
	 	HtmlReport.createReport();
  	    
        LogManagement.info(ConstanteMessages.MSGFICHIERTRAITE + countFichierTraite);
        LogManagement.add(ConstanteLevel.INFO, countFichierTraite + " " + "processed files.", true);
       	Date stop = new Date();
       	LogManagement.add(ConstanteLevel.INFO, "  Start : " + start, true);
        LogManagement.add(ConstanteLevel.INFO, "  Stop  : " + stop, true);
        return true;
  }
 
 /**
  * Preparation actions (get taxonomy ID (s) to proceed, sort files).
  */  
 public final void preparation() {
    	
        LogManagement.info(Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION);
        HtmlReport.clearSettings();
        HtmlReport.setNameappli(Constante.NOMAPPLICATION);
	   	if (!StringUtils.isEmpty(Constante.VERSIONAPPLICATION)) {
	   		HtmlReport.setVersionappli(Constante.VERSIONAPPLICATION);
	   	} else {
	   		HtmlReport.setVersionappli("0.0.0");
	   	}
        if (usecache && StringUtils.isEmpty(cachePath)) {
        	LogManagement.error("Path to the user cache is not defined. Please, define it or set usecache to false");
        	curroutputFile = this.outPutFile;
        } else {
        	curroutputFile = this.outPutFile;
	        if (this.excelFile.isDirectory()) {
	        	HtmlReport.setFoldInput(true);
	        	this.inputDir = this.excelFile;
	        	HtmlReport.setInputFolder(this.excelFile.getPath().substring(this.excelFile.getPath().lastIndexOf("\\") + 1));
	            allExcels = ConstanteUtils.findAllInstancesOrExcels(this.excelFile, Constante.XLSX);
	            if (!allExcels.isEmpty()) {
	            	processXlsFiles(allExcels, null);
		        } else {
		        	allExcels = ConstanteUtils.findAllInstancesOrExcels(this.excelFile, Constante.CSV);
		            if (!allExcels.isEmpty()) {
		            	processXlsFiles(allExcels, outPutFile);
			        } else {
		        	    LogManagement.error("No files to convert found in the input directory");
			        }
	            }     
	        } else {
	        	this.inputDir = new File(this.excelFile.getParent());
	            if ((ConstanteUtils.getExtension(this.excelFile).toLowerCase().equals(Constante.XLSX)) || (ConstanteUtils.getExtension(this.excelFile).toLowerCase().equals(Constante.CSV)) ) {
	            	allExcels.add(this.excelFile);
	            	HtmlReport.setInput(this.excelFile.getName());
	            	processXlsFiles(allExcels, outPutFile);
	            } else {
	                LogManagement.warn(ConstanteMessages.MSGEXTENSION + this.excelFile.getName());
	            }
	        } 
        }
    }
 /**
  * Main converter.
  * @param inputBook - single Excel workbook to convert
  * @param inputFile - single file to convert
  * @param reload - reload
  * @return true if conversion was successful
  */
 @SuppressWarnings("unchecked")
 private int traitement(final XSSFWorkbook inputBook, final File inputFile,  final boolean reload) {
    	
     String currEntity;
     String currStartDate;
     String currEndDate;
     String currEntityURL;
     Date cStartDate = new Date();
     ArrayList<Element> xbrlNodes = new ArrayList<Element>();
     if (inputBook == null) {
        return 0;
     }
     currEntity = ConstanteUtils.getConfigurationParametre(entityInputMode, inputBook, entity, ConstanteXmlMapping.ENTITYTYPE, "entity");       
     currStartDate = ConstanteUtils.getConfigurationParametre(periodInputMode, inputBook, startDate, Constante.DATETYPE, "start date");
     currEndDate = ConstanteUtils.getConfigurationParametre(periodInputMode, inputBook, endDate, Constante.DATETYPE, "end date");
     defaultUnits = ConstanteUtils.getConfigurationParametre(unitsMode, inputBook, units, ConstanteXmlMapping.ENTITYTYPE, "units");
     //verify mono period and entity
     if (this.declFlag == 1) {
    	 boolean vrfMono = verifyMonoEntityAndPeriod(currEntity, currEndDate, inputFile.getName());
    	 if (!vrfMono) {
    		 return 0;
    	 }
     }
     boolean vrf = ConfigurationParametres.verifyConfigurationParameters(currEntity, currEndDate, currStartDate);
     
     if (vrf) {
        LogManagement.info(ConstanteMessages.MSGPERIOD + currStartDate + " to " + currEndDate);
        LogManagement.info(ConstanteMessages.MSGENTITY + currEntity);
        //getting taxonomy according to taxonomy ID
        if (taxos == null) {
            LogManagement.warn(ConstanteMessages.MSGERREURFICHIECONGIF + configFile.getPath());
            return 0;
        }
        if (reload) {
            currTaxo = taxos.getTaxoByTaxoLbl(taxoId, currStartDate, currEndDate);
        }
        //getting taxonomy-related parameters
        if (currTaxo != null) {
        	HtmlReport.setTaxoname(currTaxo.getTaxoLibelle());
           try {	
                if (entityScheme.equals("")) {
                	currEntityURL = currTaxo.getEntityURL();
                } else {
                	currEntityURL = entityScheme;
                }
                if (currEntityURL == null) {
                	currEntityURL = "";
                	LogManagement.warn("Entity scheme not found for the taxonomy " + currTaxo.getTaxoLibelle()
                			           + ". Please, verify your configuration file or enter the entity URL manualy via the bat file.");
                }
                if (reload || reloadMap) {  
                    if (this.declFlag != 1) {
                       this.mappingFile = new File(currTaxo.getMappingFile());
                       this.contextsFile = new File(currTaxo.getContextsFile());
                       this.gabaritFile = new File(currTaxo.getTemplateFile());
                    } 
                    if (reload) {
	                    this.zipFile = new File(currTaxo.getZipFile());
	                    //this.xsds = currTaxo.getXsdCollection();
					    this.temporaryDir = ConstanteUtils.getTaxonomyTemporaryDirPath(zipFile, "XCT", usecache, cachePath);
					    File mainxsd = new File(this.temporaryDir + "/" + currTaxo.getXsdAsStringCollection().get(0));
	                    if (!mainxsd.exists()) {
						    LogManagement.info("Unzip settings files");
		                    ParallelUnzip tz = new ParallelUnzip(temporaryDir, zipFile.getCanonicalPath());
		                    if (tz.isDiskSpaceEnough()) {
				     	        ThreadRunner tr = new ThreadRunner(tz.getBigList(), tz);
				     	        tr.setThreads(2);
				     	        tr.run();
		                    } else  {
		                    	LogManagement.error("Not enough disk space to unzip the settings archive");
		                    	LogManagement.error("You need at least " + tz.getOrigSize() + " Mb available on your system drive.");
		                    	if (this.declFlag == 1) {
		                    	   return -1;
		                    	} else {
		                    		return 0;
		                    	}
		                    }
	                    }
	                    LogManagement.info("Finding taxonomy namespaces.");
		     	        this.taxoNmsMap = TaxoTools.foundTaxoNameSpaceAndConceptDetails(mainxsd);
		     	        Map<String, HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>>>mapQnames = (Map<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, String>>>>>) taxoNmsMap.get(ReturnModeTaxoTools.MAPQNAME);
                        reformatQNameMap(mapQnames);
                    }
                    
                    if (reloadMap) {
                    	//reset the unziped files
	                    this.contextsFile = new File(temporaryDir + "/" + this.contextsFile.getPath());
	                    this.mappingFile = new File(temporaryDir + "/" + this.mappingFile.getPath());
	                    this.gabaritFile = new File(temporaryDir + "/" + this.gabaritFile.getPath());
                    	convertLogiqueMapping(inputFile, currTaxo.getXsdAsStringCollection(), mappingFile, gabaritFile, temporaryDir);
	                    //load context file
	                    if (contextsDoc == null) {
	                    	SAXBuilder sb = new SAXBuilder();
	                        this.contextsDoc = sb.build(this.contextsFile); 
	                    } else if (this.declFlag == 1) {
	                    	SAXBuilder sb = new SAXBuilder();
	                        this.contextsDoc = sb.build(this.contextsFile);
	                    }
	                    //loading mapping file
	                    LogManagement.info(ConstanteMessages.MSGTRAITEMENT + this.mappingFile.getPath());
	                    mapping.initialiser(mappingFile, XctMainProcess.getXbrlNode(this.contextsFile));
	                    LogManagement.info(ConstanteMessages.MSGMAPPINGLOADED);
	                    
	               }
                 }
                
                 ArrayList<Element> units = new ArrayList<Element>();
                 Iterator<Element> itUnit = contextsDoc.getRootElement().getDescendants(new ElementFilter());
 	       		 while (itUnit.hasNext()) {
 	        			boolean vrfUnit = false;
 	        			Element element = (Element) itUnit.next();
 	        			if (element.getName().equals("unit")) {
 	        				if (!units.isEmpty()) {
 		         				for (Element unitNode : units) {
 		         					if (unitNode.getAttributeValue("id").equals(element.getAttributeValue("id"))) {
 		         						vrfUnit = true;
 		         					}
 		         				}
 		         				if (!vrfUnit) {
 		             				units.add(element);
 		             			}
 	        				} else {
 	        					units.add(element);
 	        				}
 	        			}
 	       			
 	        	 }
                 //reading Excel file to add values to mapping
                 LogManagement.info(ConstanteMessages.MSGVALUESEARCH);
                 int resCnt = XctMainProcess.getValuesFromExcel(inputBook, mapping.getMappedFactsCollection(), valueMapping,
                		                                     subsetConversion, currEndDate, currTaxo, workTable, contextsDoc, mapId,
                		                                     contexts, instCheat, csvFile, units);
                 
                 if (resCnt == 0) {
                	LogManagement.info("No value found to convert for current file");
                	return 0;
                 } else {
                    LogManagement.info(resCnt + ConstanteMessages.MSGVALUEFOUND);
                 }
                 schemaLinkNodes = new ArrayList<Element>();
                 //get schema refs nodes
                 xbrlNodes.add(contextsDoc.detachRootElement());
                 schemaLinkNodes = XctMainProcess.configureSchemaRef(xbrlNodes, currTaxo.getXsdsRefs());
          		 if (schemaLinkNodes == null) {
          			 return 0;
          		 }
                 if (xbrlNode == null) {
                     xbrlNode = xbrlNodes.get(0);
          		 } else if (!currTaxo.isDeclaratif()) {
          			 xbrlNode = xbrlNodes.get(0);
          		 } else {
          			List<Namespace> additionalNms = xbrlNodes.get(0).getAdditionalNamespaces();
          			for (Namespace nmsCurr : additionalNms) {          			    
          				if (xbrlNode.getNamespace(nmsCurr.getPrefix()) == null) {
          					xbrlNode.addNamespaceDeclaration(nmsCurr);
          				}
          			}
          		 }
          		 
          		//unitNodes
          		 Iterator<Element> itElUnit = xbrlNodes.get(0).getDescendants(new ElementFilter());
 	       		 while (itElUnit.hasNext()) {
 	        			boolean vrfUnit = false;
 	        			Element element = (Element) itElUnit.next();
 	        			if (element.getName().equals("unit")) {
 	        				if (!unitNodes.isEmpty()) {
 		         				for (Element unitNode : unitNodes) {
 		         					if (unitNode.getAttributeValue("id").equals(element.getAttributeValue("id"))) {
 		         						vrfUnit = true;
 		         					}
 		         				}
 		         				if (!vrfUnit) {
 		             				unitNodes.add(element);
 		             			}
 	        				} else {
 	        					unitNodes.add(element);
 	        				}
 	        			}
 	       			
 	        	 }
                  LogManagement.info(ConstanteMessages.MSGCONFIGURINGCONTEXTS);
                 //transform entityName, startdate, enddate, schema in context
                 XctMainProcess.configureContexts(contexts, currEntity, currStartDate, currEndDate, currEntityURL);
                 
             } catch (Exception ex) {
            	 LogManagement.error(ex.getMessage() + ex.getStackTrace());
                 return 0;
             } catch (Error er) {
            	 LogManagement.error(er.getMessage() + er.getStackTrace());
                 return 0;
             }
            Date cEndDate = new Date();
            LogManagement.info("  Start : " + cStartDate);
            LogManagement.info("  Stop  : " + cEndDate);
            return 1;
      
     } else {
           return 0;
     }

  } else {
       LogManagement.error("Error loading configuration parameters!");
       return 0;
   }
}
 /**
  *  Validate instance if necessary.
  * @param taxonomies - list of xsds.
  * @param instFile - instance file.
  */
 private HashMap<String, Object> validation(final ArrayList<File> taxonomies, final File instFile) {
 	   HashMap<String, Object> resMap = new HashMap<String, Object>();
	   if (taxonomies != null) {
            //invoke xvt
            if (this.outputFolder.getPath().equals("")) {
               this.outputFolder =	new File(instFile.getParent());
            }
            if (!this.outputFolder.exists()) {
               this.outputFolder.mkdirs();
            }
            
            LogManagement.info(ConstanteMessages.MSGVALIDATION);
            try {
                Validation validation = new Validation(this.xpeServiceConfig, this.customMessages);
                ValidationUtils.initialize(validation, currTaxo.getTaxoLibelle());
                //ValidationUtils.initialize(validation);
                validation.validate(instFile, taxonomies, true, true, this.suffix, this.outputFolder.getPath(), this.inputDir);
                ValidationUtils.shutdownDTS();
                resMap.put("xbrlKO", validation.getValidationErrors().isEmpty());
                resMap.put("formula", validation.isFormulaOK());
                resMap.put("instance", validation.getInstObj());
                resMap.put("tablesKO", validation.getTablesKO());
                
                updateDuplicateFactsReport(dplFactsMap, validation.getfResultOutput().getPath());
                validation.transformToHtml();
                resMap.put("validOutput", validation.getfResultOutput().getPath());
                
                validation = null;
            } catch (Exception e) {
               	LogManagement.error("traitement => Validation exception: " + e.getMessage() + e.getStackTrace());
            } catch (Error e) {
                LogManagement.fatal("traitement => Validation exception: " + e.getMessage() + e.getStackTrace());
            }            
      } else {
          LogManagement.warn("No xsds found for current taxonomy!");
      }
	  return resMap;
 }
    /**
     * Launch Xmt API to convert logical mapping to physical.
     * @param cinputFile - input Excel file with values.
     * @param cxsds - list of xsds for taxonomy.
     * @param cmappingFile - current logical mapping file.
     * @param cgabaritFile - blank template file.
     * @param ctemporaryDir - unzip folder.
     * @throws Exception - exception.
     */
   public final void convertLogiqueMapping(final File cinputFile, final List<String> cxsds, final File cmappingFile, final File cgabaritFile, final String cinputDir) throws Exception {
	   
	   boolean alErr = false;
	   if (LogManagement.getReturnCode() >= 2) {
	       alErr = true;
	   }
	   
	   String ctmpDir = StringUtils.defaultString(System.getenv("UBP_TEMP"), System.getProperty("java.io.tmpdir"))
               + "/UBPartner/UBPartner XBRL Tools/tmp/XCT/";
	   if (!new File(ctmpDir).exists()) {
		   new File(ctmpDir).mkdirs();
	   }
	   XmtSDK xmt = new XmtSDK();
	   xmt.setExecReturnCode(new Integer(0));
	   xmt.setMapTaxo(taxoNmsMap);
	   xmt.setXbrlInstance(excelwbk);
	   //xmt.setFileInput(cinputFile);
	   xmt.setGabaritFile(cgabaritFile);
	   xmt.setMappingFile(cmappingFile);
	   xmt.setOutputFolder(new File(ctmpDir).getPath());
	   xmt.setPrefix("");
	   ArrayList<File> xsdFiles = new ArrayList<File>();
	   for (String xsd : cxsds) {
		   xsdFiles.add(new File(cinputDir + "/" + xsd));
	   }
	   xmt.setXsdFile(xsdFiles);
	   
	   xmt.xmtSDKrun();
	  if ((xmt.getExecReturnCode() == 0) || (xmt.getExecReturnCode() == 1) || alErr) {
		   
		   File lContext = new File(ctmpDir + "/ContextFile.xml");
           File lMapping = new File(ctmpDir + "/MappingFile.xml");
           
           if (xmt.getIsLogicalMapping()) {
	           if (lContext.exists()) {
	               this.contextsFile = lContext;
	               try {
	                   SAXBuilder sb = new SAXBuilder();
	                   this.contextsDoc = sb.build(this.contextsFile); 	
	                } catch (Exception e) {
	                    throw new Exception("Error building a context file.");
	                }
	           } else {
	        	   LogManagement.debug("convertLogicalMapping => Context file was not created");
	           }
	           if (lMapping.exists()) {
	               this.mappingFile = lMapping;
	           } else {
	        	   LogManagement.debug("convertLogicalMapping => Context file was not created");
	           }
           }
	     
	   } else {
		  throw new Exception("Error converting logical mapping to physical.");
	   }
   }
   /**
    * Verify that all instances in declarative taxonomy are on one entity and period.
    * @param currEntity - current entity
    * @param currED - current end date
    * @param inst - instance file
    * @return true if the current entity matches the default one.
    */
  private boolean verifyMonoEntityAndPeriod(final String currEntity, final String currED, final String inst) {
	     if (this.unEntity.equals("")) {
	         this.unEntity = currEntity;
	     } else if (!this.unEntity.equals(currEntity)) {
	    	 LogManagement.warn("All instances of the decalarative taxonomy should be mono entity."
	                            + " File " + inst + " will be ignored.");
	    	 return false;
	     }
	     if (this.unEndDate.equals("")) {
	         this.unEndDate = currED;
	     } else if (!this.unEndDate.equals(currED)) {
	    	 LogManagement.warn("All instances of the decalarative taxonomy should be mono period. "
	                             + "File " + inst + " will be ignored.");
	    	 return false;
	     }
	     return true;
  }
   /**
    * Create instance, validate and add header.
    * @return true if all operation succeed.
    */
  private boolean posttreatment() {
	  boolean result = false;
	  //if contexts found then writing facts into instance file
      LogManagement.info(ConstanteMessages.MSGCREATINGINSTANCE);
	  try {
	          if (!valueMapping.isEmpty()) {
		           XctMainProcess.writeFactsAndContexts(valueMapping, contexts, xbrlNode, schemaLinkNodes,
	        		  curroutputFile, defaultUnits, unitNodes, outputFolder.getPath(), validate, suffix);
	          } else {
	        	  LogManagement.error("No values found to convert");
	        	  return false;
	          }
	          //if validate option - activate validation
	          HashMap<String, Object> resMap = new HashMap<String, Object>();
	          if (this.validate) {
	            if (LstXmlDate.isWithXvt()) {
	                ArrayList<File> taxonomies = IXctXvtXmlInstance.getXsdsFromConfigFile(currTaxo.getXsdCollection(), this.temporaryDir);
	                resMap = validation(taxonomies, curroutputFile);
	            } else {
	             	LogManagement.warn("Sorry, you do not have rights to validate instance file.");
	            }
	          }
	          if (resMap.isEmpty()) {
	        	  HtmlReport.setNoValidaton(true);
	          }
	          //if header option - add header
	          if (this.useHeader) {
		          HashMap<String, Object> toolParams = new HashMap<String, Object>();
		          toolParams.put("entity", this.unEntity);
		          toolParams.put("country", country);
		          ExecRmt.setUserParams(userParams);
		          ExecRmt.setUserPropertiesFile(userPropertiesFile);
		          ExecRmt.setCountry(country);
		          toolParams.putAll(ExecRmt.getUserParameters());
			      
				  File baseOutputFile = curroutputFile;
				  /*if (currTaxo.getHeaderParams().get(country) != null) {
					 curroutputFile = RmtMainProcess.changeOutPut("xml", curroutputFile, curroutputFile);
				  }*/
				  String resName = "";
		          if (resMap.isEmpty()) {
		        	  resName = RmtMainProcess.addHeaderOneFile(baseOutputFile, curroutputFile.getPath(), this.headerConfig, toolParams, currTaxo);
		          } else {
		        	  if (resMap.get("instance") != null) {
		        		  XbrlInstance cinstObj = (XbrlInstance) resMap.get("instance");
		        		  cinstObj.updateSchemaRef(currTaxo.getXsdsRefs());
		        		  cinstObj.setInstFile(baseOutputFile);
		        		  HeaderConfig headers = new HeaderConfig(this.headerConfig);
		        		  resName = RmtMainProcess.addHeaderOneFile(cinstObj, curroutputFile.getPath(), headers, toolParams, currTaxo);
		        	  } else {
		        		  resName = RmtMainProcess.addHeaderOneFile(baseOutputFile, curroutputFile.getPath(), this.headerConfig, toolParams, currTaxo);
		        	  }
		          }
		          
		          if (StringUtils.isEmpty(resName)) {
		        	  result = false;
		          } else {
		        	  if (!new File(resName).getPath().equals(curroutputFile.getPath())) {
			        	  if (!resMap.isEmpty()) {
			        		  File validOut = new File((String) resMap.get("validOutput"));
			        		  String newName = (String) resMap.get("validOutput");
			        		  newName = newName.replace(curroutputFile.getName(), new File(resName).getName());
			        		  if (new File(newName).exists()) {
			        			  new File(newName).delete();
			        		  }
			        		  validOut.renameTo(new File(newName));
			        		  resMap.remove("validOutput");
			        		  resMap.put("validOutput", newName);
			        	  }
			        	  curroutputFile = new File(resName);
			        	  result = true;
		        	  } else {
		        		  result = false;
		        	  }
		          }
		          
		          if (result && !ConstanteUtils.getExtension(curroutputFile).toLowerCase().equals("xbrl")) {
						if (baseOutputFile.exists()) {
							baseOutputFile.delete();
						}
				  }
	          }
	          HtmlReport.setInput(curroutputFile.getName());
	          createReportItem(resMap, dplFactsMap.isEmpty());
	     } catch (Exception e) {
	    	 LogManagement.error("Failed to create or validate output=> " + e.getMessage());
	     } catch (Error e) {
	    	 LogManagement.fatal("Failed to create or validate output=> " + e.getMessage());
	     }
      return result;
  }
  
  /**
   * Verify output path.
   * @param cInputFile - input file/folder
   * @param cOutputFile - output file/folder
   * @param decl - declarative taxonomy flag
   */
  private void verifyOutput(final File cInputFile, File cOutputFile, final boolean decl) {
	  
	  if (cOutputFile == null) {
		  cOutputFile = cInputFile;
	  }
	  if (decl) {
		  if (!ConstanteUtils.getExtension(cOutputFile).equals("xbrl") && !ConstanteUtils.getExtension(cOutputFile).equals("xml")) {
			  String ext = "xbrl";
 			  curroutputFile = new File(this.outPutFile.getPath() + "/declarative_report." + ext);
			  this.outPutFile =  new File(this.outPutFile.getPath() + "/declarative_report." + ext);
			  LogManagement.debug("Output will be changed to: " + outPutFile);
		  }
	  } else {
		  if (cInputFile.isDirectory()) {
			  if (ConstanteUtils.getExtension(cOutputFile).equals("xbrl") || ConstanteUtils.getExtension(cOutputFile).equals("xml")) {
				  this.outPutFile =  new File(cOutputFile.getParent());
				  LogManagement.debug("Output will be changed to: " + outPutFile);
	          }
		  } else {
			  String ext = ConstanteUtils.getExtension(cOutputFile);
	          if (!ConstanteUtils.getExtension(cOutputFile).equals("xbrl") && !ConstanteUtils.getExtension(cOutputFile).equals("xml"))  {
	        	  this.outPutFile = new File(cOutputFile.getPath() + "/" + this.excelFile.getName().substring(0, this.excelFile.getName().length() - 5) + "." + Constante.XBRL);
	        	  LogManagement.debug("Output will be changed to: " + outPutFile);
	          } else if (ext.equals(Constante.XML)) {
	          	  this.outPutFile = new File(cOutputFile.getParent() + "/" + ConstanteUtils.getRealName(cOutputFile) + "." + Constante.XBRL);
	          }
		  }
	  }
   }
  /**
   * Reformats the map with QName from the TaxoTools to be used with XCT.
   * @param mapQnames - map with concepts-QNames-labels.
   */
  private void reformatQNameMap(final Map<String, HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>>> mapQnames) {
	  
	  HashMap<String, String> lblQName = new HashMap<String, String>();
	  HashMap<String, HashMap<String, String>> langLblQname = new HashMap<String, HashMap<String, String>>();
	  
	  for (Entry<String, HashMap<String, HashMap<String,HashMap<String, HashMap<String, String>>>>> ent : mapQnames.entrySet()) {
		   String conceptNms = ent.getKey();
		   for (Entry<String, HashMap<String,HashMap<String, HashMap<String, String>>>> ent1 : mapQnames.get(conceptNms).entrySet()) {
			    String conceptNm = ent1.getKey();
			    for (Entry<String, HashMap<String, HashMap<String, String>>> ent2 : ent1.getValue().entrySet()) {
				     String qnameNms = ent2.getKey();
				     for (Entry<String, HashMap<String, String>> ent3 : ent2.getValue().entrySet()) {
					      String qnameId = ent3.getKey();
					      for (Entry<String, String> ent4 : ent3.getValue().entrySet()) {
						       String lang = ent4.getKey();
						       String label = ent4.getValue();
						       if (mapLabelsQName.containsKey(conceptNms + "#" + conceptNm)) {
						    	   if (mapLabelsQName.get(conceptNms + "#" + conceptNm).containsKey(lang)) {
						    		   if (!mapLabelsQName.get(conceptNms + "#" + conceptNm).get(lang).containsKey(label)) {
						    			   lblQName = mapLabelsQName.get(conceptNms + "#" + conceptNm).get(lang);
						    			   lblQName.put(label, qnameNms + "#" + qnameId);
						    			   langLblQname = mapLabelsQName.get(conceptNms + "#" + conceptNm);
						    			   langLblQname.remove(lang);
						    			   langLblQname.put(lang, lblQName);
						    			   mapLabelsQName.remove(conceptNms + "#" + conceptNm);
						    			   mapLabelsQName.put(conceptNms + "#" + conceptNm, langLblQname);
						    		   }
						    	   } else {
						    		   lblQName = new HashMap<String, String>();
							    	   lblQName.put(label, qnameNms + "#" + qnameId);
							    	   langLblQname = mapLabelsQName.get(conceptNms + "#" + conceptNm);
							    	   langLblQname.put(lang, lblQName);
							    	   mapLabelsQName.remove(conceptNms + "#" + conceptNm);
							    	   mapLabelsQName.put(conceptNms + "#" + conceptNm, langLblQname);
						    	   }
						       } else {
						    	   lblQName = new HashMap<String, String>();
						    	   lblQName.put(label, qnameNms + "#" + qnameId);
						    	   langLblQname = new HashMap<String, HashMap<String, String>>();
						    	   langLblQname.put(lang, lblQName);
						    	   mapLabelsQName.put(conceptNms + "#" + conceptNm, langLblQname);
						       }
					      }
				     }
			    }
		    }
	   }
  }
  /**
   * Creates an report item.
   * @param resMap - validation summary map
   * @param dblValid - duplicate facts flag
   */
  @SuppressWarnings("unchecked")
private void createReportItem(final HashMap<String, Object> resMap, final boolean dblValid) {
	  
	  //pour la version avec les details des assertions dans Index, a faire
	   ArrayList<String> dfTabsList = new ArrayList<String>();
	   ArrayList<UnsatisfiedAssertion> koTabsList = new ArrayList<UnsatisfiedAssertion>();
	   
	   //find declarative table concernés par faits dupliqués
	   ArrayList<String> koFd = new ArrayList<String>();
	   if (!dblValid && this.currTaxo.isDeclaratif()) {
		   for (Entry<String, ArrayList<MappedFact>> cEnt : dplFactsMap.entrySet()) {
			   ArrayList<MappedFact> dpls = cEnt.getValue();
			   for(MappedFact dpl : dpls) {
				   String mapId = dpl.getIdMappingRef();
				   if (!StringUtils.isEmpty(mapId)) {
					   String tableId = ConstanteUtils.getTableIdFromFact(mapId);
					   if (!tableId.equals("")) {
						   if (!koFd.contains(tableId)) {
							   koFd.add(tableId);
						   }
					   }
				   }
			   }
		   }
	   }
	   
	   if (this.currTaxo.isDeclaratif()) {
		   for (Entry<String, String> currEnt : this.declTblIds.entrySet()) {
			   if (!this.declTblNotConverted.containsKey(currEnt.getKey())) {
					   HashMap<String, Object> indexMap = new HashMap<String, Object>();
					   HashMap<String, String> summMap = new HashMap<String, String>();
					   
					   summMap.put("taxonomy", HtmlReport.getTaxoname());
					   summMap.put("entity", this.unEntity);
					   summMap.put("endDate", this.unEndDate);
					   summMap.put("currency", this.defaultUnits);
					   summMap.put("instance", curroutputFile.getPath());
					   summMap.put("referenceFile", this.inputMapById.get(currEnt.getKey()));
					   
					   
					   indexMap.put("fileName", currEnt.getKey() + "#" + currEnt.getValue());
					   indexMap.put("summary", summMap);
					  if (!this.declTblSkippedByRemise.containsKey(currEnt.getKey())) {
						  if (koFd.contains(currEnt.getKey())) {
						      indexMap.put("duplicateKO", false);
						  } else {
						     indexMap.put("duplicateKO", true);
						  }
						   if (!resMap.isEmpty()) {
							   indexMap.put("validationFileName", (String) resMap.get("validOutput"));
							   indexMap.put("xbrlKo", (boolean) resMap.get("xbrlKO"));
							   HashMap<String, ArrayList<UnsatisfiedAssertion>> tablesKo = (HashMap<String, ArrayList<UnsatisfiedAssertion>>) resMap.get("tablesKO");
							   if (tablesKo != null) {
								   if (!tablesKo.isEmpty()) {
									   if (tablesKo.containsKey(currEnt.getKey())) {
										   indexMap.put("formulaKO", false);
										   koTabsList = tablesKo.get(currEnt.getKey());
										   indexMap.put("koTabsList", koTabsList);
									   } else {
										   indexMap.put("formulaKO", true);
									   }
								   } else {
									   indexMap.put("formulaKO", (boolean) resMap.get("formula"));
								   }
							   }  else {
								   indexMap.put("formulaKO", (boolean) resMap.get("formula"));
							   }
						   }
						   indexMap.put("dfTabsList", dfTabsList);
						   indexMap.put("expected", "y");
						   HtmlReport.addItem(indexMap);
					  } else if (this.declTblSkippedByRemise.get(currEnt.getKey()).equals("y")){
						  indexMap.put("expected", "n");
						  HtmlReport.addItem(indexMap);
					  }
					  //HtmlReport.addItem(indexMap);
			   }
		   }
	   } else {
		   HashMap<String, Object> indexMap = new HashMap<String, Object>();
		   HashMap<String, String> summMap = new HashMap<String, String>();
		   
		   summMap.put("taxonomy", HtmlReport.getTaxoname());
		   summMap.put("entity", this.unEntity);
		   summMap.put("endDate", this.unEndDate);
		   summMap.put("currency", this.defaultUnits);
		   summMap.put("instance", curroutputFile.getPath());
		   summMap.put("referenceFile", this.inputMapById.get(HtmlReport.getFileName().split("#") [0]));
		   
		   
		   indexMap.put("duplicateKO", dblValid);
		   indexMap.put("fileName", HtmlReport.getFileName());
		   indexMap.put("summary", summMap);
		   if (!resMap.isEmpty()) {
			   indexMap.put("xbrlKo", (boolean) resMap.get("xbrlKO"));
			   indexMap.put("validationFileName", (String) resMap.get("validOutput"));
			   indexMap.put("formulaKO", resMap.get("formula"));
		   }
		   HtmlReport.addItem(indexMap);
	   }
  }
  /**
   * Creates a duplicate facts report.
   * @param dplFcts - duplicate facts report.
   * @param repFilePath - report file path.
   * @param instancePath - instance file path.
   */
  public static void updateDuplicateFactsReport(HashMap<String, ArrayList<MappedFact>> dplFcts, final String repFilePath) {
	    LogManagement.info("Update duplicate facts validation");
        Element root = null;
		Document trace = null;
		SAXBuilder sb = new SAXBuilder();
        boolean noDf = false;
		try {
          trace = sb.build(new File(repFilePath));
          root = trace.getRootElement();
          Element summ = root.getChild("summary");
          Element dplFacts = root.getChild("duplicateFacts");
          if (dplFacts != null) {
          	  dplFacts.removeContent();
          } else {
        	  dplFacts = new Element("duplicateFacts");
        	  noDf = true;
          }  
          int i = 0;
          	for (Entry<String, ArrayList<MappedFact>> currEntr : dplFcts.entrySet()) {
   	        	Element dplfactNode = new Element("DuplicateFact");
   	        	i++;
   	        	for (MappedFact currF : currEntr.getValue()) {
	     				Element iterNpde = new Element("iteration");
	     				iterNpde.setAttribute("concept", currF.getNmnSpace().getURI() + "#" + currF.getConceptName());
	     				iterNpde.setAttribute("context", currF.getContextID());
	     				try {
	     				    iterNpde.setAttribute("sheet", currF.getSheetName());
	     				    iterNpde.setAttribute("rangeRef", currF.getRangeRef());
	     				    iterNpde.setAttribute("value", currF.getValue());
	     				    iterNpde.setAttribute("mapID", currF.getIdMappingRef());
	     				    iterNpde.setAttribute("decimals", currF.getDecimals());
	     				    
	     				} catch (Exception e) {
	     					LogManagement.debug("No tab and range reference for the fact");
	     				}
	     				dplfactNode.addContent(iterNpde);
   	        	}
   	        	dplFacts.addContent(dplfactNode);
          	}
            if (noDf) {
            	root.addContent(dplFacts);
            	summ.setAttribute("duplicateFactsCount", "" + i);
            }
	        Format restitFormat = Format.getPrettyFormat();
	 	    restitFormat.setEncoding("UTF-8");
			XMLOutputter output = new XMLOutputter(restitFormat);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(repFilePath), "UTF-8"));
			output.output(trace, writer);
			writer.close();
		} catch (Exception e) {
			LogManagement.error("Create duplicate facts validation report failed");
		} catch (Error e) {
			LogManagement.fatal("Create duplicate facts validation report failed");
		}
 }
}
