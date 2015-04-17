package ubpartner.xct;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.LogManagementI;
import ubpartner.lst.LstXmlDate;
import ubpartner.xct.commun.Constante;
import ubpartner.xct.commun.ReturnModeXct;

/**
 * XBRL converting tools API.<br/>
 * Example of use (instance creation without reporting characteristics):<br/>
 * <pre>
 * {@code
 * 
 *	XctSDK xct = new XctSDK(true);
 *	//parameter path to the parent of root of demo files directory
 *	String pathToDemo = "d:/Demo/XT_Demo";
 *	String pathToSdk = "d:/Demo/SDK";		 
 *	//setting taxonomy configuration file, input file/folder, output file
 *	xct.setConfigFile(new File(pathToDemo + "/TAXO/TAXO_config.xml")); 
 *	xct.setExcelFile(new File(pathToDemo + "/UC/XCT/CONVERT"));
 *	xct.setOutPutFile(new File(pathToDemo + "/RESULTS/XCT/report.xbrl")); 
 *		 
 *	//setting taxonomy, entity, period, units, table
 *	xct.setModeTaxoid(ReturnModeXct.ADRESSE); 
 *	xct.setTaxoId("'INFO'!C5");
 *	xct.setModeEntity(ReturnModeXct.ADRESSE);
 *	xct.setEntity("'INFO'!C6"); 
 *	xct.setEntityScheme("http:\\www.eba.europa.eu/fr/dummy");
 *	xct.setModeDate(ReturnModeXct.ADRESSE);
 *	xct.setStartdate("'INFO'!C7");
 *	xct.setEnddate("'INFO'!C8");
 *	xct.setModeUnity(ReturnModeXct.ADRESSE);
 *	xct.setUnity("'INFO'!C9");
 *	xct.setTableMode(ReturnModeXct.ADRESSE);
 *	xct.setTableId("'INFO'!C10");
 *		 
 *	//setting validation parameters
 *	xct.setValidate(true); 
 *	xct.setValidationOutputFolder(new File(pathToDemo + "/RESULTS/XCT"));
 *	xct.setCustomMessages(pathToSDK + "/xmldata.xml");
 *	xct.setSuffix("xct");
 *	xct.setXpeXvtConfig(pathToSDK + "/XvtXpeConfig.xml");
 *    
 *	//setting subset conversion 
 *	xct.setSubsetConversion("y");
 *		 
 *	//setting cache params
 *	xct.setUseCache(true);
 *	xct.setCachePath("C:/ProgramData/UBPartner/UBPartner XBRL Tools/cache");
 *	//running conversion
 *	System.out.println("Running Xct SDK...");
 *	xct.xctSDKrun();
 * }
 * </pre>
 * 
 * Example of use (instance creation with reporting characteristics):<br/>
 * <pre>
 * {@code
 *	XctSDK xct = new XctSDK(true);
 *	//parameter path to the parent of root of demo files directory
 *	String pathToDemo = "d:/Demo/XT_Demo";
 *	String pathToSdk = "d:/Demo/SDK";		 
 *	//setting taxonomy configuration file, input file/folder, output file
 *	xct.setConfigFile(new File(pathToDemo + "/TAXO/TAXO_config.xml")); 
 *	xct.setExcelFile(new File(pathToDemo + "/UC/XCT/CONVERT"));
 *	xct.setOutPutFile(new File(pathToDemo + "/RESULTS/XCT/report.xbrl")); 
 *		 
 *	//setting taxonomy, entity, period, units, table
 *	xct.setModeTaxoid(ReturnModeXct.ADRESSE); 
 *	xct.setTaxoId("'INFO'!C5");
 *	xct.setModeEntity(ReturnModeXct.ADRESSE);
 *	xct.setEntity("'INFO'!C6"); 
 *	xct.setEntityScheme("http:\\www.eba.europa.eu/fr/dummy");
 *	xct.setModeDate(ReturnModeXct.ADRESSE);
 *	xct.setStartdate("'INFO'!C7");
 *	xct.setEnddate("'INFO'!C8");
 *	xct.setModeUnity(ReturnModeXct.ADRESSE);
 *	xct.setUnity("'INFO'!C9");
 *	xct.setTableMode(ReturnModeXct.ADRESSE);
 *	xct.setTableId("'INFO'!C10");
 *		 
 *	//setting validation parameters
 *	xct.setValidate(true); 
 *	xct.setValidationOutputFolder(new File(pathToDemo + "/RESULTS/XCT"));
 *	xct.setCustomMessages(pathToSDK + "/xmldata.xml");
 *	xct.setSuffix("xct");
 *	xct.setXpeXvtConfig(pathToSDK + "/XvtXpeConfig.xml");
 *    
 *	//setting subset conversion 
 *	xct.setSubsetConversion("y");
 *	
 *	//setting reporting parameters
 *	xct.setUseReporting(true);
 *	xct.setReportingConfig(new File(pathToSDK + "/envcfg.xml"));
 *	xct.setUserPropertiesFile(new File(pathToSDK + "/reportingconfig.properties"));
 *  
 *	//setting cache parameters
 *	xct.setUseCache(true);
 *	xct.setCachePath("C:/ProgramData/UBPartner/UBPartner XBRL Tools/cache");
 *	//running conversion
 *	System.out.println("Running Xct SDK...");
 *	xct.xctSDKrun();
 * }
 * </pre>
 */
public class XctSDK {
	/**
	 * initLog.
	 */
	private Boolean initLog;
	
	/**
	 * Input String/Cell address/cell name with taxonomy Id.
	 */
	private String taxoId;

	/**
	 * Gets input String/Cell address/cell name with taxonomy Id.
	 * 
	 * @return String with input/cell address/cell name.
	 */
	public final String getTaxoId() {
		return taxoId;
	}

	/**
	 * Sets input String/Cell address/cell name with taxonomy Id.
	 * 
	 * @param cTaxoId
	 *            Input String /Cell address/Cell name to set.
	 */
	public final void setTaxoId(final String cTaxoId) {
		this.taxoId = cTaxoId;
	}

	/**
	 * Input String/Cell address/cell name with entity.
	 */
	private String entity;

	/**
	 * Gets input String/Cell address/cell name with entity.
	 * 
	 * @return String with input text/cell address/cell name.
	 */
	public final String getEntity() {
		return entity;
	}

	/**
	 * Sets input String/Cell address/cell name with entity.
	 * 
	 * @param cEntity
	 *            Input text/Cell address/Cell name to set.
	 */
	public final void setEntity(final String cEntity) {
		this.entity = cEntity;
	}

	/**
	 * URL of the entity schema.
	 */
	private String entityScheme = "";

	/**
	 * Gets entity schema URL.
	 * 
	 * @return String with entity schema URL.
	 */

	public final String getEntityScheme() {
		return entityScheme;
	}

	/**
	 * Sets entity schema URL.
	 * 
	 * @param cEntityScheme
	 *            String with entity schema URL to set.
	 */
	public final  void setEntityScheme(final String cEntityScheme) {
		this.entityScheme = cEntityScheme;
	}

	/**
	 * Input string/Cell address/cell name with start date.
	 */
	private String startdate;

	/**
	 * Gets input string/cell address/cell name with start date.
	 * 
	 * @return String with text/cell address/cell name.
	 */
	public final String getStartdate() {
		return startdate;
	}

	/**
	 * Sets input string/cell address/cell name with start date.
	 * 
	 * @param cStartdate
	 *            Text (yyyy-mm-dd)/Cell address/Cell name to set.
	 */
	public final void setStartdate(final String cStartdate) {
		this.startdate = cStartdate;
	}

	/**
	 * Input string/Cell address/cell name with end date.
	 */
	private String enddate;

	/**
	 * Gets input string/Cell address/cell name with end date.
	 * 
	 * @return String with text/cell address/cell name.
	 */
	public final String getEnddate() {
		return enddate;
	}

	/**
	 * Sets input string/Cell address/cell name with end date.
	 * 
	 * @param cEnddate
	 *            text (yyyy-mm-dd)/Cell address/Cell name to set.
	 */
	public final void setEnddate(final String cEnddate) {
		this.enddate = cEnddate;
	}

	/**
	 * File object representing TAXO_config.xml file.
	 */
	private File configFile;

	/**
	 * Gets File object representing TAXO_config.xml file.
	 * 
	 * @return File Taxo_Config.xml.
	 */
	public final File getConfigFile() {
		return configFile;
	}

	/**
	 * Sets File object representing TAXO_config.xml file.
	 * 
	 * @param cConfigFile
	 *            File Taxo_Config.xml to set.
	 */
	public final void setConfigFile(final File cConfigFile) {
		this.configFile = cConfigFile;
	}

	/**
	 * Flag indicating whether to activate partial conversion.
	 */
	private String subsetConversion;

	/**
	 * Gets the flag for partial tabs conversion.
	 * @return true - if conversion will be based on the rules defined in TAXO_config.xml.
	 *         false - if all tabs will be converted only according to mapping.
	 */
	public final  String getSubsetConversion() {
		return subsetConversion;
	}

	/**
	 *Sets the flag for partial tabs conversion.
	 * @param cSubsetConversion
	 *         true - if conversion should be based on the rules defined in TAXO_config.xml.
	 *         false - if all tabs should be converted only according to mapping.
	 */
	public final void setSubsetConversion(final String cSubsetConversion) {
		this.subsetConversion = cSubsetConversion;
	}
    /**
     * Object representing file or folder with files to convert.
     */
	private File excelFile;

	/**
	 * Gets object representing file or folder with files to convert.
	 * @return excelFile
	 *              Excel file with data.
	 */
	public final File getExcelFile() {
		return excelFile;
	}

	/**
	 * Sets object representing file or folder with files to convert.
	 * @param cExcelFile
	 *            Excel file with data to set.
	 */
	public final void setExcelFile(final File cExcelFile) {
		this.excelFile = cExcelFile;
	}

	/**
	 * File object representing output instance/folder with instances.
	 */
	private File outPutFile;

	/**
	 * Gets file object representing output instance/folder with instances.
	 * @return outPutFile
	 *               output instance/folder with instances.
	 */
	public final File getOutPutFile() {
		return outPutFile;
	}

	/**
	 * Sets file object representing output instance/folder with instances.
	 * @param cOutPutFile
	 *            output instance/folder with instances to set
	 */
	public final void setOutPutFile(final File cOutPutFile) {
		this.outPutFile = cOutPutFile;
	}
	/**
	 * Validation execution flag.
	 */
	private boolean validate = false;

	/**
     * Gets validation execution flag.
     * @return true - if validation is activated.
     *         false - if validation is deactivated.
     */
	public final boolean isValidate() {
		return validate;
	}

	/**
     * Sets validation flag.
     * @param cValidate
     *            Activates/deactivates instance validation.
     */
	public final void setValidate(final boolean cValidate) {
		this.validate = cValidate;
	}

	/**
	 * Taxonomy Id supply mode.
	 */
	private String modeTaxoid;

	/**
	 * Gets mode of taxonomy Id supply.
	 * 
	 * @return "input" - if taxonomy Id is supplied directly as text.
	 *         "address" - if taxonomy Id will be supplied from the cell with
	 *         certain address.
	 *         "name" - if taxonomy Id will be supplied from the
	 *         cell with certain defined name.
	 */
	public final String getModeTaxoid() {
		return modeTaxoid;
	}

	/**
	 * Sets mode of taxonomy Id supply.
	 * 
	 * @param cModeTaxoid
	 *           "input","address","name" - taxonomy supply mode to set.
	 */
	public final void setModeTaxoid(final ReturnModeXct cModeTaxoid) {
		this.modeTaxoid = cModeTaxoid.toString();
	}

	/**
	 * Mode of entity supply.
	 */
	private String modeEntity;

	/**
	 * Gets mode of entity supply.
	 * 
	 * @return "input" - if entity is supplied directly as text.
	 *         "address" - if entity will be supplied from the cell with
	 *         certain address.
	 *         "name" - if entity will be supplied from the
	 *         cell with certain defined name.
	 */
	public final String getModeEntity() {
		return modeEntity;
	}

	/**
	 * Sets mode of entity supply.
	 * 
	 * @param cModeEntity
	 *            "input","address","name" - entity supply mode to set.
	 */
	public final void setModeEntity(final ReturnModeXct cModeEntity) {
		this.modeEntity = cModeEntity.toString();
	}

	/**
	 * Mode of start date and end date supply.
	 */
	private String modeDate;

	/**
	 * Gets mode of start date and end date supply.
	 * 
	 * @return "input" - if dates are supplied directly as text.
	 *         "address" - if dates will be supplied from the cell with
	 *         certain address.
	 *         "name" - if dates will be supplied from the
	 *         cell with certain defined name.
	 */
	public final String getModeDate() {
		return modeDate;
	}

	/**
	 * Sets mode of start date and end date supply.
	 * 
	 * @param cModeDate
	 *            "input","address","name" - start date and end date supply mode to
	 *            set.
	 */
	public final void setModeDate(final ReturnModeXct cModeDate) {
		this.modeDate = cModeDate.toString();
	}
	/**
	 * Suffix for the validation result files.
	 */
	private String suffix;

	/**
	 * Gets suffix for the validation result files.
	 * 
	 * @return String with suffix for the validation result files.
	 */
	public final String getSuffix() {
		return suffix;
	}

	/**
	 * Sets suffix for the validation result files.
	 * 
	 * @param cSuffix
	 *            Suffix for the validation result files to set.
	 */
	public final void setSuffix(final String cSuffix) {
		this.suffix = cSuffix;
	}

	/**
	 * Input value/Cell address/cell name with units.
	 */
	private String unity;

	/**
	 * Gets input value/Cell address/cell name with units.
	 * 
	 * @return String with text/cell address/cell name.
	 */
	public final String getUnity() {
		return unity;
	}

	/**
	 * Sets input value/Cell address/cell name with units.
	 * 
	 * @param cUnity
	 *            text/Cell address/Cell name to set.
	 */
	public final void setUnity(final String cUnity) {
		this.unity = cUnity;
	}

	/**
	 * Mode of units supply.
	 */
	private String modeUnity;

	/**
	 * Gets mode of units supply.
	 * 
	 * @return "input" - if units are supplied directly as text.
	 *         "address" - if units will be supplied from the cell with
	 *         certain address.
	 *         "name" - if units will be supplied from the
	 *         cell with certain defined name.
	 */
	public final String getModeUnity() {
		return modeUnity;
	}

	/**
	 * Sets mode of units supply.
	 * 
	 * @param cModeUnity
	 *            "input","address","name" - units supply mode to set.
	 */
	public final void setModeUnity(final ReturnModeXct cModeUnity) {
		this.modeUnity = cModeUnity.toString();
	}

	/**
	 * Output folder for validation result files.
	 */
	private File validationOutputFolder;

	/**
	 * Gets String representing output folder (for validation result files).
	 * 
	 * @return validation output folder.
	 */
	public final File getValidationOutputFolder() {
		return validationOutputFolder;
	}

	/**
	 * Sets String representing output folder (for validation result files).
	 * 
	 * @param cValidationOutputFolder
	 *            validation output folder to set.
	 */
	public final void setValidationOutputFolder(final File cValidationOutputFolder) {
		this.validationOutputFolder = cValidationOutputFolder;
	}
	
	/**
	 * Use header flag.
	 */
	private boolean useHeader = false;

	/**
	 * Setter for the use header flag.
	 * @param cuseHeader - true - to add reporting characteristics to the instance
	 */
	public final void setUseReporting(final boolean cuseHeader) {
		this.useHeader = cuseHeader;
	}

	/**
	 * Setter for the reporting configuration file.
	 * @param cheaderConfig - reporting configuration file (envcfg.xml).
	 */
	public final void setReportingConfig(final File cheaderConfig) {
		this.headerConfig = cheaderConfig;
	}

	/**
     * Gets header adding flag.
     * @return true - if header is to be added to the final instance.
     *         false - if header is not to be added to the final instance.
     */
	public final boolean isUseReporting() {
		return useHeader;
	}
	/**
	 * File object representing envcfg.xml file.
	 */
	private File headerConfig;

	/**
	 * Gets File object representing encfg.xml file.
	 * 
	 * @return File envcfg.xml.
	 */
	public final File getReportingConfig() {
		return configFile;
	}
	/**
	 * Return code execution.
	 */
	private Integer execReturnCode;

	/**
     * Gets Integer code of execution result.
     * 
     * @return the result of execution.
     * 
     *         0 = OK - 1 = WARNING - 2 = ERROR - 3 = FATAL.
     */
	public final Integer getExecReturnCode() {
		return execReturnCode;
	}
	/**
	 * Table ID address/cell name.
	 */
    private String tableId = "";
    /**
     * Table ID mode.
     */
    private String tableMode = "";
	
    /**
	 * Getter fot the declarative table ID.
	 * @return the tableId - string with table ID.
	 */
	public final String getTableId() {
		return tableId;
	}

	/**
	 * Setter for the declarative table ID.
	 * 
	 * @param cTableId the table Id to set
	 */
	public final void setTableId(final String cTableId) {
		this.tableId = cTableId;
	}

	/**
	 *Getter for the declarative table ID.
	 * @return the tableMode - string with table mode (address/name)
	 */
	public final String getTableMode() {
		return tableMode;
	}

	/**
	 * Setter for the declarative table ID.
	 * @param cTableMode the tableMode to set
	 */
	public final void setTableMode(final ReturnModeXct cTableMode) {
		this.tableMode = cTableMode.toString();
	}
    /**
     * Use cache or not. If true - than taxonomy from the user cache will be used.
     * If false - taxonomy will be dezipped.
     */
	private boolean useCache = false;
	/**
	 * Path of the user cache with dezipped taxonomies.
	 */
	private String cachePath;
	/**
	 * Shows whether the user cache is enabled.
	 * @return true - if taxonomy from the user cache will be used.
     *         false - taxonomy will be dezipped.
	 */
	public final boolean isUseCache() {
		return useCache;
	}

	/**
	 * Sets the use of user cache.
	 * @param useCache - boolean parameter. 
	 *            true - if taxonomy from the user cache should be used.
     *            false - taxonomy should be dezipped.
	 */
	public final void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	/**
	 * Getter for the path of the user cache with dezipped taxonomies.
	 * @return path of the user cache with dezipped taxonomies.
	 */
	public final String getCachePath() {
		return cachePath;
	}

	/**
	 * Setter for the path of the user cache with dezipped taxonomies.
	 * @param cachePath - path of the user cache with dezipped taxonomies.
	 */
	public final void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}
	/**
	 * Path to custom XPE configuration file.
	 */
    private String xvtXpeConfig = "";
    
	/**
	 * Getter for the path to custom XPE configuration file.
	 * @return Path to custom XPE configuration file.
	 */
	public final String getXvtXpeConfig() {
		return xvtXpeConfig;
	}

	/**
	 * Setter for the path to custom XPE configuration file.
	 * @param cXvtXpeConfig - the path to custom XPE configuration file. to set
	 */
	public final void setXvtXpeConfig(final String cXvtXpeConfig) {
		this.xvtXpeConfig = cXvtXpeConfig;
	}
	/**
	 * Sets path to the xsl file in XPE.
	 * 
	 * @param cXslPath
	 *            path to the xsl file in XPE to set.
	 *  @deprecated starting version 1.14 this file is no longer in use.
	 */
	public final void setXslPath(final File cXslPath) {
		//do nothing for deprecated method
	}
	/**
	 * Path to custom messages file.
	 */
	private String customMessages = "";
	 /**
     * JSON object for user parameters.
     */
    private JSONObject userParams;
    /**
     * User parameters file.
     */
    private File userPropertiesFile;
    /**
     * Country of the reporting.
     */
    private String country = "FR";
    /**
	 * Gets path to custom messages file.
	 * @return the customMessages
	 */
	public final String getCustomMessages() {
		return customMessages;
	}

	/**
	 * Sets path to custom messages file.
	 * @param ccustomMessages the customMessages to set
	 */
	public final void setCustomMessages(final String ccustomMessages) {
		this.customMessages = ccustomMessages;
	}
	/**
	 * Getter for the user parameters.
	 * @return user parameters as JSON object.
	 */
	public final JSONObject getUserParams() {
		return userParams;
	}

	/**
	 * Setter for the user parameters.
	 * @param cuserParams user parameters as JSON object to set.
	 */
	public final void setUserParams(final JSONObject cuserParams) {
		this.userParams = cuserParams;
	}

	/**
	 * Getter for the user properties file.
	 * @return File object.
	 */
	public final File getUserPropertiesFile() {
		return userPropertiesFile;
	}

	/**
	 * Setter for the user properties file.
	 * @param cuserPropertiesFile user properties file.
	 */
	public final void setUserPropertiesFile(final File cuserPropertiesFile) {
		this.userPropertiesFile = cuserPropertiesFile;
	}

	/**
	 * Getter for the reporting country.
	 * @return country ISO-code.
	 */
	public final String getCountry() {
		return country;
	}

	/**
	 * Setter for the reporting country.
	 * @param ccountry - country code according to the ISO 3166-1 alpha-2.
	 */
	public final void setCountry(final String ccountry) {
		this.country = ccountry;
	}
	/**
	 * XctSDK constructor.
	 */
	public XctSDK() {
		this(false);
	}
	
	/**
	 * XctSDK constructor.
	 * 
	 * @param reinitLog
	 *            reinitialazing the log.If you use the SDK in a non Ubpartner
	 *            tools, set reinitLog to true.
	 */
	public XctSDK(final Boolean reinitLog) {
		try {
			initLog = reinitLog;
			if (initLog) {
				LogManagementI.lmInit(StringUtils.defaultString(
						System.getenv("LOGFILE_ACTIVATION"), "y"),
						StringUtils.defaultString(
								System.getenv("LOGPRINT_ACTIVATION"), "y"),
								StringUtils.defaultString(
								System.getenv("FILE_LEVEL"), "INFO"),
								StringUtils.defaultString(
								System.getenv("PRINT_LEVEL"), "INFO"),
								StringUtils.defaultString(System.getenv("FILE_MODE"),
								"threshold"), StringUtils.defaultString(
								System.getenv("PRINT_MODE"), "threshold"),
						Constante.NOMCOURTAPPLICATION, StringUtils.defaultString(System.getenv("LOGFILE_PATH"),
										new java.io.File("").getAbsolutePath()
												+ "/"
												+ Constante.NOMAPPLICATION
												+ ".log"), StringUtils.defaultString(System.getenv("POPUP"), "n"));
			}
			execReturnCode = LogManagement.getReturnCode();
		} catch (Exception e) {
			LogManagement.error("XctSDK => " + e.getMessage());
		} catch (Error e) {
			LogManagement.fatal("XctSDK => " + e.getMessage());
		}
	}

	/**
	 * Run the xctSDK.
	 */
	 public final synchronized void xctSDKrun() {
		try {
			LogManagement.info("Run Xct in API mode");
			if (LstXmlDate.processApi(Constante.NOMCOURTAPPLICATION)) {
			new ExecXct(configFile, excelFile, modeTaxoid, taxoId, modeEntity,
					entity, entityScheme, modeDate, startdate, enddate,
					outPutFile, validate, suffix, validationOutputFolder, subsetConversion, unity,
					modeUnity, useHeader, headerConfig, tableId, tableMode, useCache, cachePath, xvtXpeConfig, customMessages,
					country, userPropertiesFile, userParams);

			} else {
				LogManagement.error("Your license is not validated. Please, verify!");
			}

		} catch (Exception e) {
			LogManagement.error("XctSDKrun => " + e.getMessage());
		} catch (Error e) {
			LogManagement.fatal("XctSDKrun => " + e.getMessage());
		} finally {
			outPutFile = ExecXct.getCurroutputFile();
			execReturnCode = LogManagement.getReturnCode();
			if (initLog) {
				LogManagementI.lmOutputResetConfig();
			}
		}
	}
}
