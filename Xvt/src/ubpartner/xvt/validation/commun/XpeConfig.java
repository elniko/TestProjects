package ubpartner.xvt.validation.commun;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import ubpartner.logmanagement.LogManagement;

/**
 * Class to read UBP configuration for XPE.
 * @author UBPartner
 *
 */
public class XpeConfig {
    /**
     * Map with XBRL validation options. 
     */
    private HashMap<String, String> xbrlOptions;
    /**
     * Map with dimension validation options. 
     */
    private HashMap<String, String> dimOptions;
    /**
     * Map with cache locations pour different taxonomies. 
     */
    private HashMap<String, String> cacheLocations = new HashMap<String, String>();
    /**
     * Flag for separate dimension validation.
     */
    private boolean separateDimValidation;
    /**
     * Flag for taxonomy validation.
     */
    private boolean taxoValidation;
    /**
     * Flag to decide whether to deactivate formula partioning for XBRL validation.
     */
    private boolean formulaPartDesactivate;
    /**
     * Report trace level.
     */
    private String traceLevel;
    /**
     * Report language.
     */
    private String lang;
    /**
     * File size below which the DOM activation is forced.
     */
    private Long domForceSize;
    /**
     * Configuration file path.
     */
    private File configFile;
    /**
     * Getter for XBRL validation options map.
     * @return the map with XBRL validation options.
     */
    public final HashMap<String, String> getXbrlOptions() {
        return xbrlOptions;
    }

    /**
     * Setter for XBRL validation options map.
     * @param cXbrlOptions the map with XBRL options to set.
     */
    public final void setXbrlOptions(final HashMap<String, String> cXbrlOptions) {
        this.xbrlOptions = cXbrlOptions;
    }

    /**
     * Getter for dimension validation options map.
     * @return the map with dimension validation options.
     */
    public final HashMap<String, String> getDimOptions() {
        return dimOptions;
    }

    /**
     * Setter for dimension validation options map.
     * @param cDimOptions the map with dimension validation options to set
     */
    public final void setDimOptions(final HashMap<String, String> cDimOptions) {
        this.dimOptions = cDimOptions;
    }

    /**
     * Getter for the separate dimension validation flag.
     * @return true - if the dimension validation is to be run separate from XBRL validation.
     */
    public final boolean isSeparateDimValidation() {
	return separateDimValidation;
    }

    /**
     * Setter for the separate dimension validation flag.
     * @param cSeparateDimValidation true for the dimension validation is to be run separate from XBRL validation.
     */
    public final void setSeparateDimValidation(final boolean cSeparateDimValidation) {
	this.separateDimValidation = cSeparateDimValidation;
    }

    /**
     * Getter for the taxonomy validation flag.
     * @return true if taxonomy validation is active.
     */
    public final boolean isTaxoValidation() {
        return taxoValidation;
    }

    /**
     * Setter for the taxonomy validation flag.
     * @param cTaxoValidation true to activate taxonomy validation.
     */
    public final void setTaxoValidation(final boolean cTaxoValidation) {
        this.taxoValidation = cTaxoValidation;
    }

    /**
     * Getter for the formula partioning deactivation flag.
     * @return true - if formula partioning is to be deactivated.
     */
    public final boolean isFormulaPartDesactivate() {
        return formulaPartDesactivate;
    }

    /**
     * Setter for the formula partioning deactivation flag.
     * @param cFormulaPartDesactivate true - if formula partioning is to be deactivated.
     */
    public final void setFormulaPartDesactivate(final boolean cFormulaPartDesactivate) {
        this.formulaPartDesactivate = cFormulaPartDesactivate;
    }

    /**
     * Getter for the report trace level.
     * @return the report trace level
     */
    public final String getTraceLevel() {
        return traceLevel;
    }

    /**
     * Setter for trace level.
     * @param cTraceLevel report trace level to set.
     */
    public final void setTraceLevel(final String cTraceLevel) {
        this.traceLevel = cTraceLevel;
    }

    /**
     * Getter for the report language.
     * @return language setting.
     */
    public final String getLang() {
	return lang;
    }

    /**
     * Setter for the report language.
     * @param cLang - standard windows language abbreviation.
     */
    public final void setLang(final String cLang) {
	this.lang = cLang;
    }
    /**
     * Getter for the minimum file size which force DOM validation.
     * @return the vomForceSize - file size which force DOM validation. 
     */
    public final Long getDomForceSize() {
	return domForceSize;
    }

    /**
     * Setter for the minimum file size which force DOM validation.
     * @param cDomForceSize - the minimum file size to set.
     */
    public final void setDomForceSize(final Long cDomForceSize) {
	this.domForceSize = cDomForceSize;
    }

    /**
	 * @return the cacheLocations
	 */
	public final HashMap<String, String> getCacheLocations() {
		return cacheLocations;
	}

	/**
	 * @param ccacheLocations the cacheLocations to set
	 */
	public final void setCacheLocations(final HashMap<String, String> ccacheLocations) {
		this.cacheLocations = ccacheLocations;
	}

	/**
      * Constructor for configuration object.
      * @param currConfigFile - UBP config file.
      */
    public XpeConfig(final File currConfigFile) {
	
	if (currConfigFile == null) {
	    initialize(true, null);
        } else if (!currConfigFile.exists()) {
            initialize(true, null);
        } else {
            initialize(false, currConfigFile);
        }
    }
    /**
     * Initialize the object with configuration.
     * @param defParam - if true - default parameters are used.
     *                 - if false - the parameters from configuration file.
     * @param currConfigFile - configuration file object or null.
     */
    private void initialize(final boolean defParam, final File currConfigFile) {
	
	this.configFile = currConfigFile;
	if (defParam) {
	    initDefOptions();
	} else {
	    try {
		initCustomOptions();		
	    } catch (Exception e) {
		LogManagement.debug("Failed to initialize XPE configuration file. The default options will be used.");
		initDefOptions();
	    } catch (Error e) {
		LogManagement.debug("Failed to initialize XPE configuration file. The default options will be used.");
		initDefOptions();
	    }
	}
    }
    
    /**
     * Initialize custom options for XPE.
     * @throws Exception - if exception occurs while reading configuration file.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initCustomOptions() throws Exception {
		LogManagement.debug("Initializing custom parameters for XPE.");
		
		SAXBuilder sb = new SAXBuilder();
		Document config = sb.build(configFile);
		Element rootElem = config.getRootElement();
		
		HashMap<String, String> xbrlMap = new HashMap<String, String>();
		HashMap<String, String> dimMap = new HashMap<String, String>();
		String optNm = "";
		String optVal = "";
		// Initialize dimension options.
		List<Element> currDimOptions = rootElem.getChild("dimensionValidation").getChildren("option");
		if (currDimOptions == null) {
		   dimMap = initDefOptionAggDim();
		} else {
		    for (Element xbrlOpt : currDimOptions) {
		         optNm = xbrlOpt.getAttributeValue("uri");
		         optVal = xbrlOpt.getAttributeValue("enable");
		         dimMap.put(optNm, optVal);
		    }
	        }
		this.dimOptions = dimMap;
		
		try {
	            if (rootElem.getChild("dimensionValidation").getAttributeValue("separate").equals("true")) {
	       	        this.separateDimValidation = true;
	            } else {
	       	        this.separateDimValidation = false;
	            }
		} catch (Exception e) {
		    this.separateDimValidation = false;
	        }
		
		//initialize XBRL options.
		List<Element> currXbrlOptions = rootElem.getChild("xbrlValidation").getChildren("option");
		if (currXbrlOptions == null) {
		    xbrlMap = initDefOptionXBRL();
		} else {
	           for (Element xbrlOpt : currXbrlOptions) {
	                optNm = xbrlOpt.getAttributeValue("uri");
	        	optVal = xbrlOpt.getAttributeValue("enable");
	        	xbrlMap.put(optNm, optVal);
	           }
		}
		if (!this.separateDimValidation) {
		    for (Entry xbrlOption : xbrlMap.entrySet()) {
			if (dimMap.containsKey(xbrlOption.getKey())) {
			    if (dimMap.get(xbrlOption.getKey()).equals("true")) {
				xbrlOption.setValue("true");
			    }
			}
		    }
		}
		this.xbrlOptions = xbrlMap;
			
		//Taxonomy validation.
		try {
	            if (rootElem.getChild("taxonomyValidation").getAttributeValue("activate").equals("true")) {
	        	this.taxoValidation = true;
	        	this.xbrlOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "true");
	        	this.dimOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "true");
	            } else {
	        	this.taxoValidation = false;
	        	this.xbrlOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "false");
	        	this.dimOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "false");
	            }
		} catch (Exception e) {
		    this.taxoValidation = false;
		    this.xbrlOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "false");
		    this.dimOptions.put("validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy", "false");
		}
		
		//Formula partioning activation.
		try {
	             if (rootElem.getChild("formulaPartioning").getAttributeValue("desactivateIfActive").equals("true")) {
	        	 this.formulaPartDesactivate = true;
	             } else {
	        	 this.formulaPartDesactivate = false;
	             }
		} catch (Exception e) {
		    this.formulaPartDesactivate = false;
		}
		//Trace level.
		try {
		    this.traceLevel = rootElem.getChild("tracing").getAttributeValue("level");
		} catch (Exception e) {
		    this.traceLevel = "Full Report";
		}
		//Language.
		try {
		    this.lang = rootElem.getChild("language").getValue().toLowerCase();
		} catch (Exception e) {
		    this.lang = "en";
		}
		//Force DOM validation
		try {
		    this.domForceSize = Long.parseLong(rootElem.getChild("domForceSize").getValue());
		} catch (Exception e) {
		    this.domForceSize = Long.parseLong("20");
		}
		//cache locations
		Element cacheLocs = rootElem.getChild("cacheLocations");
		if (cacheLocs != null) {
			List<Element> currCacheLoc = rootElem.getChild("cacheLocations").getChildren("cacheLocation");
			if (currCacheLoc != null) {
				 for (Element cacheLoc : currCacheLoc) {
			         String taxoId = cacheLoc.getAttributeValue("taxonomy");
			         String cachePath = cacheLoc.getAttributeValue("cache");
			         cacheLocations.put(taxoId, cachePath);
			    }
			}
		}
		
    }
    
    
    /**
     * Initialize default options for XPE.
     */
    private void initDefOptions() {
	LogManagement.debug("Initializing default XPE options.");
	this.xbrlOptions = initDefOptionXBRL();
	this.dimOptions = initDefOptionAggDim();
	this.taxoValidation = false;
	this.separateDimValidation = false;
	this.formulaPartDesactivate = false;
	this.traceLevel = "Full Report";
	this.lang = "en";
	this.domForceSize = Long.parseLong("20");
    }
    /**
     * Initialize the options for validation Aggregation and Dimension.
     * @return map with dimension validation option by default.
     */
    private HashMap<String, String> initDefOptionAggDim() {
	LogManagement.debug("Initialize the default options for aggregation and dimension validation.");
	
	HashMap<String, String> optionAgDim = new HashMap<String, String>();
	String xmlValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#Xml,Xml";
	String enableXmlValidator = "false";
	String taxonomyValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy";
	String enableTaxonomyValidator = "false";
	String instanceValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,InstanceDocument";
	String enableInstanceValidator = "false";
	String linkbaseValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Linkbase";
	String enableLinkbaseValidator = "false";
	String frtaValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#BestPractices,Frta";
	String enableFrtaValidator = "false";
	String dimLinkbaseValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,LinkbaseDimension";
	String enableDimLinkbaseValidator = "false";
	String dimInstanceValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,InstanceDimension";
	String enableDimInstanceValidator = "true";
	String genericLinkValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,GenericLinkbase";
	String enableGenericLinkValidator = "false";
	String formulaValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Formula";
	String enableFormulaValidator = "false";

	optionAgDim.put(xmlValidatorClassificationUri, enableXmlValidator);
	optionAgDim.put(taxonomyValidatorClassificationUri, enableTaxonomyValidator);
	optionAgDim.put(instanceValidatorClassificationUri, enableInstanceValidator);
	optionAgDim.put(linkbaseValidatorClassificationUri, enableLinkbaseValidator);
	optionAgDim.put(formulaValidatorClassificationUri, enableFormulaValidator);
	optionAgDim.put(dimLinkbaseValidatorClassificationUri, enableDimLinkbaseValidator);
	optionAgDim.put(dimInstanceValidatorClassificationUri, enableDimInstanceValidator);
	optionAgDim.put(frtaValidatorClassificationUri, enableFrtaValidator);
	optionAgDim.put(genericLinkValidatorClassificationUri, enableGenericLinkValidator);
	
	return optionAgDim;
    }
    
    /**
     * Initialize the options for validation XBRL.
     * @return map with XBLR validation options.
     */
    private HashMap<String, String> initDefOptionXBRL() {
	LogManagement.debug("Initialize the default options for XBRL Validation.");
	
	HashMap<String, String> optionXbrl = new HashMap<String, String>();
	String xmlValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#Xml,Xml";
	String enableXmlValidator = "true";
	String taxonomyValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Taxonomy";
	String enableTaxonomyValidator = "false";
	String instanceValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,InstanceDocument";
	String enableInstanceValidator = "true";
	String linkbaseValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Linkbase";
	String enableLinkbaseValidator = "false";
	String frtaValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#BestPractices,Frta";
	String enableFrtaValidator = "false";
	String dimLinkbaseValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,LinkbaseDimension";
	String enableDimLinkbaseValidator = "false";
	String dimInstanceValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,InstanceDimension";
	String enableDimInstanceValidator = "false";
	String genericLinkValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,GenericLinkbase";
	String enableGenericLinkValidator = "false";
	String calcValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Calculation";
	String enableCalcValidator = "true";
	String formulaValidatorClassificationUri = "validation://ubmatrix.com/Xbrl/Validation#DTS,Formula";
	String enableFormulaValidator = "false";

	optionXbrl.put(xmlValidatorClassificationUri, enableXmlValidator);
	optionXbrl.put(taxonomyValidatorClassificationUri, enableTaxonomyValidator);
	optionXbrl.put(instanceValidatorClassificationUri, enableInstanceValidator);
	optionXbrl.put(linkbaseValidatorClassificationUri, enableLinkbaseValidator);
	optionXbrl.put(formulaValidatorClassificationUri, enableFormulaValidator);
	optionXbrl.put(dimLinkbaseValidatorClassificationUri, enableDimLinkbaseValidator);
	optionXbrl.put(dimInstanceValidatorClassificationUri, enableDimInstanceValidator);
	optionXbrl.put(frtaValidatorClassificationUri, enableFrtaValidator);
	optionXbrl.put(calcValidatorClassificationUri, enableCalcValidator);
	optionXbrl.put(genericLinkValidatorClassificationUri, enableGenericLinkValidator);
	
	return optionXbrl;
    }
}
