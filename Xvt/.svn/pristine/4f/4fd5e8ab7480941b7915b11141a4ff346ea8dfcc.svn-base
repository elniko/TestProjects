package ubpartner.xvt.validation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ubmatrix.xbrl.common.memo.src.FlyweightInconsistentMemo;
import ubmatrix.xbrl.common.memo.src.FlyweightWarningMemo;
import ubmatrix.xbrl.common.memo.src.IMemo;
import ubmatrix.xbrl.common.src.IXbrlDomain;
import ubmatrix.xbrl.common.src.IXbrlFact;
import ubmatrix.xbrl.common.utility.src.CommonUtilities;
import ubmatrix.xbrl.common.xml.vom.xbrlModel.VomFact;
import ubmatrix.xbrl.domain.xbrl21Domain.dts.src.DTSResultSet;
import ubmatrix.xbrl.domain.xbrl21Domain.src.XbrlDomainUri;
import ubmatrix.xbrl.src.Xbrl;
import ubmatrix.xbrl.validation.formula.assertion.src.IAssertionResult;
import ubmatrix.xbrl.validation.formula.src.FormulaConfiguration;
import ubmatrix.xbrl.validation.formula.src.IFormulaResult;
import ubpartner.logmanagement.LogManagement;
import ubpartner.logmanagement.commun.ConstanteLevel;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.commun.UnsatisfiedAssertion;

import ubpartner.utils.report.XsltConverter;
import ubpartner.utils.xbrl.XbrlContext;
import ubpartner.utils.xbrl.XbrlFact;
import ubpartner.utils.xbrl.XbrlInstance;
import ubpartner.utils.xbrl.XbrlMappedCells;
import ubpartner.xvt.commun.Constante;
import ubpartner.xvt.validation.commun.GeneralError;
import ubpartner.xvt.validation.commun.ValidationUtils;
import ubpartner.xvt.validation.commun.XpeConfig;


/**
 * Validation class.
 * 
 */
public class Validation {

    /**
     * Formula errors map.
     * 
     */
    private Map<String, Map<String, String>> mapErrValidation 
    = new TreeMap<String, Map<String, String>>();

    /**
     * Map with variables names.
     */    
    private  Map<String, ArrayList<Object[]>> mapNameVariableFull = new TreeMap<String, ArrayList<Object[]>>();
  
    /**
     * Getter for variables map.
     * @return  variables map with context-concept-value objects.
     */
    public final Map<String, ArrayList<Object[]>> getMapNameVariableFull() {
   	return mapNameVariableFull;
    }
    
    /**
     * Formula expressions errors map.
     * 
     */
    private Map<String, Map<String, ArrayList<Map<String, String>>>> 
    mapErrExpressionFormula 
    = new TreeMap<String, Map<String, ArrayList<Map<String, String>>>>();

    /**
     * Map for formula ID errors.
     * 
     */
    private Map<String, Map<String, ArrayList<String>>> mapErrIdFormula 
    = new TreeMap<String, Map<String, ArrayList<String>>>();
    /**
     * Assertions map.
     */
   private HashMap<String, UnsatisfiedAssertion> mapAssertions = new HashMap<String, UnsatisfiedAssertion>();
    /**
     * XBRL errors list.
     */
    private static List<GeneralError> memosXBRL = new ArrayList<GeneralError>();

    /**
     * Dimension errors list.
     */
    private static List<GeneralError> memosDimension = new ArrayList<GeneralError>();

    /**
     * Formula errors list.
     */
    private static List<Element> memosFormula = new ArrayList<Element>();
    /**
     * XPE configuration.
     */
    private XpeConfig configOptions;
    /**
     * Path to file with custom messages.
     */
    private File customMessages = null;
    /**
     * Instance object.
     */
    private XbrlInstance instObj = null;
    /**
	 * @return the instObj
	 */
	public final XbrlInstance getInstObj() {
		return instObj;
	}

	/**
	 * @param cinstObj the instObj to set
	 */
	public final void setInstObj(final XbrlInstance cinstObj) {
		this.instObj = cinstObj;
	}

	/**
     * Validation error objects.
     */
    private ArrayList<GeneralError> validationErrors = new ArrayList<GeneralError>();
    /**
     * Fact validation error objects.
     */
    private static HashMap<String, ArrayList<GeneralError>> factValidationErrors = new HashMap<String, ArrayList<GeneralError>>();
    /**
     * Taxonomy ID.
     */
    private String taxoId;
    /**
     * Output file for validation results.
     */
    private File fResultOutput;
    /**
     * Formula validation passed flag.
     */
    private boolean formulaOK = true;
    /**
     * List of the declarative tables with formula errors for XCT.
     */
    private HashMap<String, ArrayList<UnsatisfiedAssertion>> tablesKO = new HashMap<String, ArrayList<UnsatisfiedAssertion>>();
    
    /**
	 * @return the tablesKO
	 */
	public final HashMap<String, ArrayList<UnsatisfiedAssertion>> getTablesKO() {
		return tablesKO;
	}

	/**
	 * @param ctablesKO the tablesKO to set
	 */
	public final void setTablesKO(final HashMap<String, ArrayList<UnsatisfiedAssertion>> ctablesKO) {
		this.tablesKO = ctablesKO;
	}

	/**
	 * @return the formulaOK
	 */
	public final boolean isFormulaOK() {
		return formulaOK;
	}

	/**
	 * @param cformulaOK the formulaOK to set
	 */
	public final void setFormulaOK(final boolean cformulaOK) {
		this.formulaOK = cformulaOK;
	}

	/**
	 * @return the fResultOutput
	 */
	public final File getfResultOutput() {
		return fResultOutput;
	}

	/**
	 * @param cfResultOutput the fResultOutput to set
	 */
	public final void setfResultOutput(final File cfResultOutput) {
		this.fResultOutput = cfResultOutput;
	}

	/**
	 * @return the mapAssertions
	 */
	public final HashMap<String, UnsatisfiedAssertion> getMapAssertions() {
		return mapAssertions;
	}

	/**
	 * @param cmapAssertions the mapAssertions to set
	 */
	public final void setMapAssertions(final HashMap<String, UnsatisfiedAssertion> cmapAssertions) {
		this.mapAssertions = cmapAssertions;
	}

	/**
     * Getter for XBRL errors.
     * @return XBRL errors list.
     */
    public static final List<GeneralError> getListErrXBRL() {
	return memosXBRL;
    }

    /**
     * Getter for Dimension errors.
     * @return Dimension errors list.
     */
    public static final List<GeneralError> getListErrDimension() {
	return memosDimension;
    }

    /**
     * Getter for formula errors.
     * @return formula errors list.
     */
    public static final List<Element> getListErrFormula() {
	return memosFormula;
    }

    /**
     * Getter for validation errors.
     * @return validation errors map.
     * 
     */
    public final Map<String, Map<String, String>> getMapErrValidation() {
	return mapErrValidation;
    }

    /**
     * Getter for formula expressions errors. 
     * @return formula expressions errors map.
     * 
     */
    public final Map<String, Map<String, ArrayList<Map<String, String>>>>
    getMapErrExpressionFormula() {
	return mapErrExpressionFormula;
    }

    /**
     * Getter for formula IDs errors.
     * @return formula IDs errors map.
     * 
     */
    public final Map<String, Map<String, ArrayList<String>>> 
    getMapErrIdFormula() {
	return mapErrIdFormula;
    }

    /**
     * Getter for XPE configuration options.
     * @return XPE configuration options object.
     */
    public final XpeConfig getConfigOptions() {
        return configOptions;
    }

    /**
     * Setter for XPE configuration options.
     * @param cConfigOptions - options object to set.
     */
    public final void setConfigOptions(final XpeConfig cConfigOptions) {
        this.configOptions = cConfigOptions;
    }
    /**
	 * @param cmapErrValidation the mapErrValidation to set
	 */
	public final void setMapErrValidation(final 
			Map<String, Map<String, String>> cmapErrValidation) {
		this.mapErrValidation = cmapErrValidation;
	}

	/**
	 * @param cmapErrExpressionFormula the mapErrExpressionFormula to set
	 */
	public final void setMapErrExpressionFormula(final
			Map<String, Map<String, ArrayList<Map<String, String>>>> cmapErrExpressionFormula) {
		this.mapErrExpressionFormula = cmapErrExpressionFormula;
	}

	/**
	 * @param cmapErrIdFormula the mapErrIdFormula to set
	 */
	public final void setMapErrIdFormula(final 
			Map<String, Map<String, ArrayList<String>>> cmapErrIdFormula) {
		this.mapErrIdFormula = cmapErrIdFormula;
	}
	/**
	 * @return the customMessages
	 */
	public final File getCustomMessages() {
		return customMessages;
	}

	/**
	 * @param ccustomMessages the customMessages to set
	 */
	public final void setCustomMessages(final File ccustomMessages) {
		this.customMessages = ccustomMessages;
	}    
    /**
     * Constructor for the validation class.
     * @param xpeConfig - file with XPE parameters.
     */
    public Validation(final File xpeConfig) {
        configOptions = new XpeConfig(xpeConfig);
        memosXBRL = new ArrayList<GeneralError>();
        memosDimension = new ArrayList<GeneralError>();
        memosFormula = new ArrayList<Element>();
        factValidationErrors = new HashMap<String, ArrayList<GeneralError>>();
        customMessages = new File(ConstanteUtils.getCanonicalPath("./", new File(Constante.CUSTOMMESSAGE).getPath()));
    }
    
    /**
     * Constructor for the validation class.
     * 
     * @param xpeConfig - file with XPE parameters
     * @param cmapping - mapping object
     */
    public Validation(final File xpeConfig, final XbrlMappedCells cmapping) {
        this(xpeConfig);
        memosXBRL = new ArrayList<GeneralError>();
        memosDimension = new ArrayList<GeneralError>();
        memosFormula = new ArrayList<Element>();
        factValidationErrors = new HashMap<String, ArrayList<GeneralError>>();
        customMessages = new File(ConstanteUtils.getCanonicalPath("./", new File(Constante.CUSTOMMESSAGE).getPath()));
    }
    
    /**
     * Constructor for the validation class.
     * 
     * @param xpeConfig - file with XPE parameters
     * @param customMess - custom messages file.
     */
    public Validation(final File xpeConfig, final File customMess) {
        
    	this(xpeConfig);
        if (customMess != null) {
            customMessages = customMess;
        } else {
        	customMessages = new File(ConstanteUtils.getCanonicalPath("./", new File(Constante.CUSTOMMESSAGE).getPath()));
        }
        
        memosXBRL = new ArrayList<GeneralError>();
        memosDimension = new ArrayList<GeneralError>();
        memosFormula = new ArrayList<Element>();
        factValidationErrors = new HashMap<String, ArrayList<GeneralError>>();
    }
    /**
     * Constructor for the validation class.
     * 
     * @param xpeConfig - file with XPE parameters
     * @param iObj - instance object
     */
    public Validation(final File xpeConfig, XbrlInstance iObj) {
        this(xpeConfig);
        instObj = iObj;
        memosXBRL = new ArrayList<GeneralError>();
        memosDimension = new ArrayList<GeneralError>();
        memosFormula = new ArrayList<Element>();
        factValidationErrors = new HashMap<String, ArrayList<GeneralError>>();
        customMessages = new File(ConstanteUtils.getCanonicalPath("./", new File(Constante.CUSTOMMESSAGE).getPath()));
    }
    
    /**
     * Formula validation according to 2008 norms.
     * 
     * @param f - Instance file.
     * @param suffix - string to add to the output file name.
     * @param outputDirectory - output folder.
     * @return true - if validation successfully passed.
     * 
     * @throws Exception
     *             Exception.
     * 
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    public final boolean validerNormeFormulae2008(final File f, final String suffix, final String outputDirectory) throws Exception {
		Date d1 = new Date();
		LogManagement.info("Formula Validation");
		String[] memosFormulae;
		Xbrl xbrl = ValidationUtils.getXbrl();
		int firedAssertionCount = 0;
		int satisifiedAssertionCount = 0;
		String path = f.getParent();
		if (!StringUtils.isEmpty(outputDirectory)) {
		    path = outputDirectory;
		}
		String name = f.getName();
		BigDecimal dclValue = new BigDecimal(0);
		File fInstanceOutput = new File(path + "/" + name + "_INSTANCE-RES" + suffix + ".xml");
		fResultOutput = new File(path + "/" + name + "_VALIDATION-RES" + suffix + ".xml");
	
		try {
		    if (xbrl != null) {
				ValidationUtils.getConfig().setKeepResultDTSOpenFlag(true);
				int traceLevel = FormulaConfiguration.parseTraceLevel(configOptions.getTraceLevel());
				if (traceLevel == -1) {
				    traceLevel = FormulaConfiguration.c_tracingSummary;
				}
				ValidationUtils.getConfig().setTraceLevel(traceLevel);
		
				fInstanceOutput.delete();
				fResultOutput.delete();
		
				Date d3 = new Date();
		
				if (!xbrl.processFormulas(fInstanceOutput.getPath(), fResultOutput.getPath(), ValidationUtils.getConfig())) {
				    LogManagement.add(ConstanteLevel.VWARN, "Failed to process formulas.");
				    memosFormulae = xbrl.getMemos();
				    if (null != memosFormulae) {
					    int len = memosFormulae.length;
						if (len > 0) {
						    LogManagement.warn("Following memos were generated during processing of formulas");
						    for (int i = 0; i < len; ++i) {
							LogManagement.warn(memosFormulae[i]);
						    }
						}
				    }
			} else {
			   Date d4 = new Date();
	           LogManagement.info("ProcessFormulas => " + CommonUtilities.diffDates(d4, d3));
			}
			LogManagement.info("Handle IFormulaResult");
			IFormulaResult result = ValidationUtils.getConfig().getResult();
	        //IDTS dts = xbrl.getDTS();
	        
	        if (result == null) {
		        LogManagement.info("There are no formulas to process in this DTS");
		        return true;
	        }
	        
	        //deliting empty instance
	        deleteInstanceIfEmpty(fInstanceOutput.getPath(), result.getFactCreationCount());
	        
	        //create formula report UBP
	        Date d5 = new Date();

	        firedAssertionCount = result.getAssertionFireCount(null);
	        satisifiedAssertionCount = result.getAssertionSatisfiedCount(null);
	
			Date d6 = new Date();
			LogManagement.info("Loop assertionReport => " + CommonUtilities.diffDates(d6, d5));
			    
			Date d7 = new Date();
			Element elfactReport = new Element("factReport");
			Iterator<?> fact = result.getFacts().getEnumerator();
			int countFact = 0;
			    for (Iterator<?> iter = fact; iter.hasNext();) {
				countFact++;
				Element elfact = new Element("fact");
				IXbrlDomain iXbrlFact = (IXbrlDomain) iter.next();
				Map<String, String> mapAttribut = iXbrlFact.getAttributeMap();
				for (String attribut : mapAttribut.keySet()) {
				    elfact.setAttribute(attribut.replace("Ref", ""), mapAttribut.get(attribut));
				}
				elfact.setAttribute("element", iXbrlFact.getQName());
				elfact.setAttribute("value", iXbrlFact.getValue());
	
				int intDecimal = Integer.valueOf(StringUtils.defaultString(elfact.getAttributeValue("decimals"), "0"));
				try {
				    dclValue = new BigDecimal(iXbrlFact.getValue());
				} catch (NumberFormatException errDecimal) {
				    dclValue = new BigDecimal(0);
				}
	
				if (intDecimal == 0) {
				    NumberFormat formatter = new DecimalFormat("#0");
				    elfact.setAttribute("effectiveValue", String.valueOf(formatter.format(dclValue)));
				} else if (intDecimal < 0) {
				    NumberFormat formatter = new DecimalFormat("#0");
				    BigDecimal coef = new BigDecimal("1" + StringUtils.repeat("0", (intDecimal * -1)));
				    BigDecimal dclValueCoef = dclValue.divide(coef).setScale(0, dclValue.ROUND_HALF_UP).multiply(coef);
	
				    if (dclValueCoef.equals(new BigDecimal(0))) {
					dclValueCoef = dclValue;
				    }
	
				    elfact.setAttribute("effectiveValue", String.valueOf(formatter.format(dclValueCoef)));
				} else {
				    NumberFormat formatter = new DecimalFormat("#0." + StringUtils.repeat("0", intDecimal));
				    elfact.setAttribute("effectiveValue", String.valueOf(formatter.format(dclValue)));
				}
				elfactReport.addContent(elfact);
			    }
			    elfactReport.setAttribute("count", String.valueOf(countFact));
			    memosFormula.add(elfactReport);
			    Date d8 = new Date();
			    LogManagement.info("Loop Formula => " + CommonUtilities.diffDates(d8, d7));
			    //dts = null;
		    } else {
			    throw new Exception("Failed to load Instance ");
		    }
		} catch (SQLException sqle) {
		    throw sqle;
		} catch (ValidationException e) {
		    //do nothing on formula warnings
		} catch (Exception e) {
			e.printStackTrace();
		    LogManagement.error("validerNormeFormulae2008 => " + e.getMessage());
		} catch (Error e) {
		    LogManagement.fatal("validerNormeFormulae2008 => " + e.getMessage());
		}
		Date d2 = new Date();
		LogManagement.info("Validate in " + CommonUtilities.diffDates(d2, d1));
		
		return (firedAssertionCount == satisifiedAssertionCount);
    }

       /**
     * Validate the XBRL 2.1. norms for the instance.
     * @param natMemos - memos for XBRL.
     * @return true - if successfully validated.
     * 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public final boolean validateXBRL(final ArrayList<IMemo> natMemos) {
	boolean flag = false;	
	LogManagement.info("XBRL Validation");
	Xbrl xbrl = ValidationUtils.getXbrl();	
	try {
	    Date d1 = new Date();
	    if (xbrl.validate(ValidationUtils.getOptionXbrl())) {
		LogManagement.info("Successfully validated XBRL document");
		flag = true;
	    } else {
	    	LogManagement.add(ConstanteLevel.VWARN, "Not successfully validated XBRL document");
	    }
	    Date d2 = new Date();
	    LogManagement.info("Validate in " + CommonUtilities.diffDates(d2, d1));
	    IMemo[] currnatMemos = xbrl.getNativeMemos();
	    
	    //Initializing service arrays
	    ArrayList<String> factIds = new ArrayList<String>();
            ArrayList<String> conceptIds = new ArrayList<String>();
	    ArrayList<String> contextIds = new ArrayList<String>();
	    for (XbrlFact currFact : instObj.getFacts()) {
		factIds.add(currFact.getId());
	    }
	    LogManagement.debug("Getting concepts list");
	    Iterator it = instObj.getConcepts().entrySet().iterator();
	    Namespace tmpNs;
	    ArrayList<String> tmpIds = new ArrayList<String>();
	    while (it.hasNext()) {
		  Map.Entry currConcept = (Map.Entry) it.next();
		  tmpNs = (Namespace) currConcept.getKey();
		  tmpIds = (ArrayList<String>) currConcept.getValue();
                  for (String tmpId : tmpIds) {
		       conceptIds.add(tmpNs.getPrefix() + ":" + tmpId);
		  }
	    }
	    if (!conceptIds.isEmpty()) {
		conceptIds = GeneralError.sortArrayByLength(conceptIds);
	    }
	    LogManagement.debug("Getting contexts list");
	    for (XbrlContext currContext : instObj.getContexts()) {
		 contextIds.add(currContext.getId());
	    }
	    if (!contextIds.isEmpty()) {
		contextIds = GeneralError.sortArrayByLength(contextIds);
	    }
		
	    IMemo currMem;
	    LogManagement.info("Getting validation memos");
	    Date startDate = new Date();
	    for (int i = 0; i < currnatMemos.length; i++) {
		currMem = currnatMemos[i];
		if (currMem != null) {
		    if ((!(currMem instanceof FlyweightWarningMemo)) && (!(currMem instanceof FlyweightInconsistentMemo))) {
        		natMemos.add(currMem);		    
        		GeneralError ge = new GeneralError(currMem, xbrl.localize(currMem), factIds, conceptIds, contextIds);
        		if (!ge.getMess().equals("")) {
        		    memosXBRL.add(ge);
	        		if (ge.getfErr() == null) {
	        		    validationErrors.add(ge);
	        		} else if (ge.getfErr().isEmpty()) {
	        		    validationErrors.add(ge);
	        		} else {
	        		    validationErrors.add(ge);
	        		    if (factValidationErrors.containsKey(ge.getfErr())) {
	        			ArrayList<GeneralError> tmpErr = new ArrayList<GeneralError>();
	        			tmpErr = factValidationErrors.get(ge.getfErr());
	        			factValidationErrors.remove(ge.getfErr());
	        			tmpErr.add(ge);
	        			factValidationErrors.put(ge.getfErr(), tmpErr);
	        		    } else {
	        			ArrayList<GeneralError> tmpErr = new ArrayList<GeneralError>();
	        			tmpErr.add(ge);
	        			factValidationErrors.put(ge.getfErr(), tmpErr);
	        		    }
	        		}
        		}
		    }
		}
	    }
	    Date endDate = new Date();
	    LogManagement.info(validationErrors.size() + " memos retrieved in " + ConstanteUtils.diffDates(startDate, endDate));
	    factIds.clear();
	    conceptIds.clear();
	    contextIds.clear();
	} catch (Exception e) {
	    e.printStackTrace();
	    LogManagement.error("validateXBRL => " + e.getMessage());
	} catch (Error e) {
	    e.printStackTrace();
	    LogManagement.fatal("validateXBRL => " + e.getMessage());
	}
	return flag;

    }
    /**
     * Validation with native memos in return.
     * @param cMemosDimension - native memos array.
     * @return true - if successfully validated Dimension 1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public final boolean validateDimension(final ArrayList<IMemo> cMemosDimension) {
   	boolean flag = false;
   	LogManagement.info("Dimension Validation");
   	Xbrl xbrl = ValidationUtils.getXbrl();
   	try {
   	    Date d1 = new Date();	    
   	    if (xbrl.validate(ValidationUtils.getOptionAgDim())) {
   		LogManagement.info("Successfully validated Dimension document");
   		flag = true;
   	    } else {
   		LogManagement.add(ConstanteLevel.VWARN, "Not successfully validated Dimension document");
   	    }
   	    Date d2 = new Date();
   	    LogManagement.info("Validate in " + CommonUtilities.diffDates(d2, d1));
   	    IMemo[] currnatMemos = xbrl.getNativeMemos();
   	    ArrayList<GeneralError> tmpErr = new ArrayList<GeneralError>();
   	    
   	//Initializing service arrays
	    ArrayList<String> factIds = new ArrayList<String>();
            ArrayList<String> conceptIds = new ArrayList<String>();
	    ArrayList<String> contextIds = new ArrayList<String>();
	    for (XbrlFact currFact : instObj.getFacts()) {
		factIds.add(currFact.getId());
	    }
	    LogManagement.debug("Getting concepts list");
	    Iterator it = instObj.getConcepts().entrySet().iterator();
	    Namespace tmpNs;
	    ArrayList<String> tmpIds = new ArrayList<String>();
	    while (it.hasNext()) {
		Map.Entry currConcept = (Map.Entry) it.next();
		  tmpNs = (Namespace) currConcept.getKey();
		  tmpIds = (ArrayList<String>) currConcept.getValue();
                  for (String tmpId : tmpIds) {
		       conceptIds.add(tmpNs.getPrefix() + ":" + tmpId);
		  }
	    }
	    if (!conceptIds.isEmpty()) {
		conceptIds = GeneralError.sortArrayByLength(conceptIds);
	    }
	    LogManagement.debug("Getting contexts list");
	    for (XbrlContext currContext : instObj.getContexts()) {
		 contextIds.add(currContext.getId());
	    }
	    if (!contextIds.isEmpty()) {
		contextIds = GeneralError.sortArrayByLength(contextIds);
	    }
	    
	    IMemo currMem;
	    for (int i = 0; i < currnatMemos.length; i++) {
		currMem = currnatMemos[i];
		if (currMem != null) {
		    if ((!(currMem instanceof FlyweightWarningMemo)) && (!(currMem instanceof FlyweightInconsistentMemo))) {
        		cMemosDimension.add(currMem);		    
        		GeneralError ge = new GeneralError(currMem, xbrl.localize(currMem), factIds, conceptIds, contextIds);
        		memosDimension.add(ge);
        		if (ge.getfErr().isEmpty()) {
        		    validationErrors.add(ge);
        		} else {
        		    validationErrors.add(ge);
        		    if (factValidationErrors.containsKey(ge.getfErr())) {
        			tmpErr = factValidationErrors.get(ge.getfErr());
        			factValidationErrors.remove(ge.getfErr());
        		        factValidationErrors.put(ge.getfErr(), tmpErr);
        		    } else {
        			tmpErr.add(ge);
        		        factValidationErrors.put(ge.getfErr(), tmpErr);
        		    }
        		}
		    }
		}
	    }
	    factIds.clear();
	    conceptIds.clear();
	    contextIds.clear();
   	} catch (Exception e) {
   	    e.printStackTrace();
   	    LogManagement.error("validateDimension => " + e.getMessage());
   	} catch (Error e) {
   	    e.printStackTrace();
   	    LogManagement.fatal("validateDimension => " + e.getMessage());
   	}
   	return flag;
       }

    /**
     * Formula facts forming.
     * 
     * @param result - validation result object.
     * @param valueAssertionResult - assertion result object.
     * @param factError - error facts.
     * @param cnt - assertion counter
     * 
     * @throws Exception - exception.
     */
    @SuppressWarnings("unchecked")
    public final void getFormulaFacts(final IFormulaResult result, final IAssertionResult valueAssertionResult, final boolean factError, final int cnt)
	    throws Exception {
	LogManagement.debug("Retrieve formula facts - concrete method");
        
	Map<?, ?> variables = valueAssertionResult.getVariables();
        String assertionId = valueAssertionResult.getXmlId();
	for (Iterator<?> i = variables.entrySet().iterator(); i.hasNext();) {
	    Map.Entry<?, ?> entry = (Map.Entry<?, ?>) i.next();
	    String name = (String) entry.getKey();
	    Object v1 = variables.get(name);
	    
	    if (v1 instanceof IXbrlFact) {
			
	    	IXbrlFact f1 = (IXbrlFact) v1;
			
			String context = f1.getContext();
			String concept = f1.getFullName();
			Object [] tmpList = {context, concept, f1.getValue()};
			ArrayList<Object[]> tmpArr = new ArrayList<Object[]>();
			tmpArr.add(tmpList);
		        mapNameVariableFull.put(name + ";" + assertionId + ";" + cnt, tmpArr);
		        
		        ArrayList<String> tmpListValue = new ArrayList<String>();
		        tmpListValue.add(f1.getValue());
			if (factError) {
			    getFormulaFact(f1, valueAssertionResult, cnt);
			}
	    } else if (v1 instanceof VomFact) {
	    	VomFact f1 = (VomFact) v1;
			
			String context = f1.getContextRef();
			String concept = f1.getFullName();
			Object [] tmpList = {context, concept, f1.getValue()};
			ArrayList<Object[]> tmpArr = new ArrayList<Object[]>();
			tmpArr.add(tmpList);
		        mapNameVariableFull.put(name + ";" + assertionId + ";" + cnt, tmpArr);
		        
		        ArrayList<String> tmpListValue = new ArrayList<String>();
		        tmpListValue.add(f1.getValue());
			if (factError) {
			    getFormulaFact(f1, valueAssertionResult, cnt);
			}
	    } else if (v1 instanceof DTSResultSet && !name.startsWith("_")) {
	        DTSResultSet s1 = (DTSResultSet) v1;
	        List<Object> tmpList = s1.getData();
	        if (!tmpList.isEmpty()) {
	            ArrayList<Object[]> currIds = new ArrayList<Object[]>();
	            for (Object o1 : tmpList) {
		        	 if (o1 instanceof IXbrlFact) {
		        	     IXbrlFact f1 = (IXbrlFact) o1;
	        	         String context = f1.getContext();
	        	         String concept = f1.getFullName();
	        	         String value = f1.getValue();
	        	            
	        	         Object [] it = {context, concept, value};
	        	         currIds.add(it);
	        	         if (factError) {
	        		         getFormulaFact(f1, valueAssertionResult, cnt);
	        		     }
		        	 } else if (o1 instanceof VomFact) {
		        		 VomFact f1 = (VomFact) o1;
	        	         String context = f1.getContextRef();
	        	         String concept = f1.getFullName();
	        	         String value = f1.getValue();
	        	            
	        	         Object [] it = {context, concept, value};
	        	         currIds.add(it);
	        	         if (factError) {
	        		         getFormulaFact(f1, valueAssertionResult, cnt);
	        		     }
		        	 }
	            }
	            if (!currIds.isEmpty()) {
        	       mapNameVariableFull.put(name + ";" + assertionId + ";" + cnt, currIds);
	            } else {
	               ArrayList<Object []> tmpListValue = new ArrayList<Object []>();
	               Object [] it = {"", "", "0"};
	               tmpListValue.add(it);
	               mapNameVariableFull.put(name, tmpListValue);
	            }
	        } else {
	            ArrayList<Object []> tmpListValue = new ArrayList<Object []>();
	            Object [] it = {"", "", "0"};
	            tmpListValue.add(it);
	            mapNameVariableFull.put(name, tmpListValue);
	        }
	    } else {
		ArrayList<Object []> tmpListValue = new ArrayList<Object []>();
	        Object [] it = {"", "", "0"};
	        tmpListValue.add(it);
	        mapNameVariableFull.put(name, tmpListValue);
	    }
	}
    }

    /**
     * Formula facts details.
     * 
     * @param fact - XBRL fact.
     * @param valueAssertionResult - assertion result object.
     * @param cnt - assertions counter.
     * @throws Exception
     *             exception.
     */
    private void getFormulaFact(final Object fact, final IAssertionResult valueAssertionResult, final int cnt) throws Exception {
	LogManagement.debug("Retrieve details of formula facts.");
	String contextDO = "";
	String fullName = "";
	String value = "";
	if (fact instanceof IXbrlFact) {
		IXbrlFact currFact = (IXbrlFact) fact;
		if (currFact.isIndexedBy(XbrlDomainUri.c_GlobalItemTempComputedFact)) {
		    LogManagement.warn("Fallback value, no context");
		    return;
		}
		contextDO = currFact.getContext();
		fullName = currFact.getFullName();
		value = currFact.getValue();
	} else {
		VomFact currFact = (VomFact) fact;
		contextDO = currFact.getContextRef();
		fullName = currFact.getFullName();
		value = currFact.getValue();
	}
	
	if (!StringUtils.isEmpty(contextDO)) {
	    Map<String, String> mapFact = new HashMap<String, String>();
	    Map<String, ArrayList<String>> mapFactIdFormula  = new HashMap<String, ArrayList<String>>();
	    Map<String, ArrayList<Map<String, String>>> mapFactExpressionFormula  = new HashMap<String, ArrayList<Map<String, String>>>();
	    ArrayList<String> listFactIdFormula = new ArrayList<String>();
	    ArrayList<Map<String, String>> listFactExpressionFormula  = new ArrayList<Map<String, String>>();

	    String idFormula = valueAssertionResult.getXmlId() + ";" + cnt;
	    String expressionFormula = valueAssertionResult.getExpression();
	    Map<String, String> mapExpressionFormula  = new HashMap<String, String>();
	    mapExpressionFormula.put(idFormula, expressionFormula);
	    if (mapErrValidation.containsKey(contextDO)) {
			mapFact.putAll(mapErrValidation.get(contextDO));
			mapFact.put(fullName, value);
			mapErrValidation.remove(contextDO);
			mapErrValidation.put(contextDO, mapFact);
	    } else {
			mapFact.put(fullName, value);
			mapErrValidation.put(contextDO, mapFact);
	    }

	    if (mapErrIdFormula.containsKey(contextDO)) {
	    	mapFactIdFormula.putAll(mapErrIdFormula.get(contextDO));
			if (mapFactIdFormula.containsKey(fullName)) {
			    listFactIdFormula = mapFactIdFormula.get(fullName);
			    listFactIdFormula.add(idFormula);
			    mapFactIdFormula.put(fullName, listFactIdFormula);
			} else {
			    listFactIdFormula.add(idFormula);
			    mapFactIdFormula.put(fullName, listFactIdFormula);
			}
			mapErrIdFormula.remove(contextDO);
			mapErrIdFormula.put(contextDO, mapFactIdFormula);
	    } else {
			if (mapFactIdFormula.containsKey(fullName)) {
			    listFactIdFormula = mapFactIdFormula.get(fullName);
			    listFactIdFormula.add(idFormula);
			    mapFactIdFormula.put(fullName, listFactIdFormula);
			} else {
			    listFactIdFormula.add(idFormula);
			    mapFactIdFormula.put(fullName, listFactIdFormula);
			}
			mapErrIdFormula.put(contextDO, mapFactIdFormula);
	    }

	    if (mapErrExpressionFormula.containsKey(contextDO)) {
	        mapFactExpressionFormula.putAll(mapErrExpressionFormula.get(contextDO));
			if (mapFactExpressionFormula.containsKey(fullName)) {
			    listFactExpressionFormula = mapFactExpressionFormula.get(fullName);
			    listFactExpressionFormula.add(mapExpressionFormula);
			    mapFactExpressionFormula.put(fullName, listFactExpressionFormula);
			} else {
			    listFactExpressionFormula.add(mapExpressionFormula);
			    mapFactExpressionFormula.put(fullName, listFactExpressionFormula);
			}
			mapErrExpressionFormula.remove(contextDO);
			mapErrExpressionFormula.put(contextDO, mapFactExpressionFormula);
	    } else {
			if (mapFactExpressionFormula.containsKey(fullName)) {
			    listFactExpressionFormula = mapFactExpressionFormula.get(fullName);
			    listFactExpressionFormula.add(mapExpressionFormula);
			    mapFactExpressionFormula.put(fullName, listFactExpressionFormula);
			} else {
			    listFactExpressionFormula.add(mapExpressionFormula);
			    mapFactExpressionFormula.put(fullName, listFactExpressionFormula);
			}
			mapErrExpressionFormula.put(contextDO, mapFactExpressionFormula);
	    }
	}
    }
    
    /**
     * Get tab names for formula.
     * @param formulaCode - formula code.
     * 
     * @return table with names.
     */
    public final List<String> getTableName(final String formulaCode) {
	LogManagement.debug("Get table name");
	List<String> tableaux = new ArrayList<String>();

	if (formulaCode == null || formulaCode.trim().equals("")) {
	    return tableaux;
	}
	int posTiret = formulaCode.indexOf("-");

	if (posTiret == -1) {
	    for (String tab : formulaCode.split("_")) {
		tableaux.add(tab);
	    }
	    return tableaux;
	}
	String subCodeTableax = formulaCode.substring(0, posTiret).trim();
	for (String tab : subCodeTableax.split("_")) {
	    tableaux.add(tab);
	}
	return tableaux;
    }

    /**
     * @return the validationErrors
     */
    public final ArrayList<GeneralError> getValidationErrors() {
        return validationErrors;
    }

    /**
     * @param cValidationErrors the validationErrors to set
     */
    public final void setValidationErrors(final ArrayList<GeneralError> cValidationErrors) {
        this.validationErrors = cValidationErrors;
    }

    /**
     * @return the factValidationErrors
     */
    public static final HashMap<String, ArrayList<GeneralError>> getFactValidationErrors() {
        return factValidationErrors;
    }

    /**
     * @param cFactValidationErrors the factValidationErrors to set
     */
    public final void setFactValidationErrors(final HashMap<String, ArrayList<GeneralError>> cFactValidationErrors) {
        factValidationErrors = cFactValidationErrors;
    }

    /**
     * Start validation for single taxonomy.
     * 
     * @param instance - instance.
     * @param taxonomie - taxonomy schema file.
     * @param xbrl - XBRL validation activate if true.
     * @param formula - formula validation activate if true
     * @param xslPath - path to the XPE stylesheet file for formula.
     * @param inputFolder - input folder.
     * @throws Exception - execution Exception.
     */
    public final void validate(final File instance, final File taxonomie,
	    final boolean xbrl, final boolean formula, final String xslPath, final File inputFolder) throws Exception {
	LogManagement.info("Lance validation - singe taxo.");
	try {
		if (taxonomie.getPath().contains("solvency") || taxonomie.getPath().contains("solvabilite")) {
			taxoId = "S2";
		} else {
			taxoId = "";
		}
	} catch (Exception e) {
		taxoId = "";
	}
	validate(instance, null, taxonomie, xbrl, formula, "", "", null, inputFolder);
    }
    
    /**
     * Start validation for multitaxonomy.
     * 
     * @param instance - instance.
     * @param taxonomies - taxonomy schema files.
     * @param xbrl - XBRL validation activate if true.
     * @param formula - formula validation activate if true
     * @param suffix - string to be added to the output file name.
     * @param outputDirectory - output folder.
     * @param inputFolder - input folder.
     * @throws Exception - execution Exception.
     */
    public final void validate(final File instance,
	    final ArrayList<File> taxonomies,
	    final boolean xbrl, final boolean formula, final String suffix, final String outputDirectory, final File inputFolder) throws Exception {
		LogManagement.debug("Start validation");
		try {
			if (taxonomies.get(0).getPath().contains("solvency") || taxonomies.get(0).getPath().contains("solvabilite")) {
				taxoId = "S2";
			} else {
				taxoId = "";
			}
		} catch (Exception e) {
			taxoId = "";
		}
		File newInstance = (updateSchemaRef(instance, taxonomies));
		validate(instance, newInstance, null, xbrl, formula, suffix, outputDirectory, null, inputFolder);
    }
    /**
     * Start validation for multitaxonomy.
     * 
     * @param instance - instance.
     * @param taxonomies - taxonomy schema files.
     * @param xbrl - XBRL validation activate if true.
     * @param formula - formula validation activate if true
     * @param suffix - string to be added to the output file name.
     * @param outputDirectory - output folder.
     * @param inputFolder - input folder.
     * @throws Exception - execution Exception.
     */
    public final void validate(final File instance, final XbrlInstance instObj,
	    final ArrayList<File> taxonomies,
	    final boolean xbrl, final boolean formula, final String suffix, final String outputDirectory, final File inputFolder) throws Exception {
		LogManagement.debug("Start validation");
		try {
			if (taxonomies.get(0).getPath().contains("solvency") || taxonomies.get(0).getPath().contains("solvabilite")) {
				taxoId = "S2";
			} else {
				taxoId = "";
			}
		} catch (Exception e) {
			taxoId = "";
		}
		File newInstance = (updateSchemaRef(instance, taxonomies));
		validate(instance, newInstance, null, xbrl, formula, suffix, outputDirectory, instObj, inputFolder);
    }
    /**
     * Start validation. Main method.
     * 
     * @param instance - instance.
     * @param newInstance - new instance file.
     * @param taxonomie - taxonomy schema files.
     * @param xbrl - XBRL validation activate if true.
     * @param formula - formula validation activate if true
     * @param csuffix - string to be added to the output file name.
     * @param outputDirectory - output folder.
     * @param cInstObj - current instance object.
     * @param inputFolder - input folder.
     * @throws Exception - execution Exception.
     */
    private  void validate(final File instance, final File newInstance,
	    final File taxonomie, final boolean xbrl,
	    final boolean formula, final String csuffix, final String outputDirectory, final XbrlInstance cInstObj, final File inputFolder) throws Exception {
		LogManagement.info("Start validation");
		//verify necessary arguments
		String suffix = "";
		if (!new File(Constante.TEMPFOLDER).exists()) {
		    new File(Constante.TEMPFOLDER).mkdirs();
		}
		File newinstance = newInstance;
		if (newinstance == null && taxonomie != null) {
		    newinstance = (updateSchemaRef(instance, taxonomie));
		}
		try {
			if (taxonomie.getPath().contains("solvency") || taxonomie.getPath().contains("solvabilite")) {
				taxoId = "S2";
			} else {
				taxoId = "";
			}
		} catch (Exception e) {
			taxoId = "";
		}
		if (!StringUtils.isEmpty(csuffix)) {
		    suffix = csuffix;
		}
		if (!StringUtils.isEmpty(suffix)) {
			if (!suffix.startsWith("_")) {
				suffix = "_" + suffix;
			}
		}
		if (customMessages == null) {
			customMessages = new File(ConstanteUtils.getCanonicalPath("./", new File(Constante.CUSTOMMESSAGE).getPath()));
		}
		
		//verify configuration
		Boolean modifcUseFormulaPartitioning = false;
		Long fileLength = newInstance.length() / (1024 * 1024);
		if (fileLength < configOptions.getDomForceSize()) {
		    ValidationUtils.deactivateFormulaPartitioning();
		    modifcUseFormulaPartitioning = true;
		} else {
	        if (configOptions.isFormulaPartDesactivate()) {
	            if (ValidationUtils.deactivateFormulaPartitioning()) {
	                   modifcUseFormulaPartitioning = true;
	            }
	        } else {
	            ValidationUtils.activateFormulaPartitioning();
	        }
		}
		if (cInstObj == null && this.instObj == null) {
		    this.instObj = new XbrlInstance(newinstance);
		} else if (cInstObj != null) {
		    this.instObj = cInstObj;
		}
/*		if (!configOptions.getCacheLocations().isEmpty()) {
		    if (StringUtils.isNotEmpty(instObj.getTaxoId())) {
		    	String cache = configOptions.getCacheLocations().get(instObj.getTaxoId());
		    	if (StringUtils.isNotEmpty(cache)) {
		    		Configuration config = Configuration.createThreadInstance();
		            config.setProperty(Configuration.c_cacheLocation, cache);
		    	}
		    }
		}*/
		//loading instance
		ValidationUtils.load(newinstance);
		ArrayList<IMemo> natMemos = new ArrayList<IMemo>();
		//XBRL validation
		if (xbrl) {	    
		    validateXBRL(natMemos);	   
		}
		//Dimension validation
		ArrayList<IMemo> natDimMemos = new ArrayList<IMemo>();
		if (configOptions.isSeparateDimValidation()) {
		    validateDimension(natDimMemos);
		}	
		//Formula validation
		if (formula && natMemos.isEmpty()  && natDimMemos.isEmpty()) {
			if (modifcUseFormulaPartitioning) {
				ValidationUtils.activateFormulaPartitioning();
				ValidationUtils.load(newinstance);
			}	
			try {
		    	validerNormeFormulae2008(newinstance, suffix, outputDirectory);
		    	LogManagement.info("Formula validated.");
		    } catch (Exception e) {
		    	LogManagement.add(ConstanteLevel.ERROR, "Errors while processing formulas");
		    }
		}
		if (fResultOutput == null) {
			String path = "";
			if (StringUtils.isEmpty(outputDirectory)) {
				path = newinstance.getParent();
			} else {
				path = outputDirectory;
			}
			fResultOutput = new File(path + "/" + newinstance.getName() + "_VALIDATION-RES" + suffix + ".xml");
		}
		//create validation report,  update it and transform to html.
		createConsolidatedResult(newinstance);
		ValidationUtils.updateTraceXpe(newinstance, suffix, outputDirectory, inputFolder.getPath() + "/" + instance.getName());

		//contains error formulas
		if (!mapErrValidation.isEmpty()) {
			this.formulaOK = false;
			LogManagement.add(ConstanteLevel.VWARN, "Not successfully validated Fomula");
		}
		
		new File(Constante.TEMPFOLDER + instance.getName()).delete();
	
		LogManagement.info(instance.getName() + " done.");
    }

    /**
     * Update schema reference in instance for the real path to taxonomy.
     * 
     * @param instance - instance file.
     * @param taxonomie - taxonomy file.
     * 
     * @return updated instance file. Creates backup.
     * 
     * @throws Exception - execution exception.
     * 
     */
    private File updateSchemaRef(final File instance, final File taxonomie) throws Exception {
		LogManagement.debug("Update link:schemaRef, xlink:href for a instance.");
		try {
		    SAXBuilder sax = new SAXBuilder();
		    Document docInstance = sax.build(instance);
		    Element root = docInstance.getRootElement();
		    Namespace link = root.getNamespace("link");
		    Namespace xlink = root.getNamespace("xlink");
		    Element elShemaref = root.getChild("schemaRef", link);
		    elShemaref.setAttribute("href", taxonomie.getAbsolutePath().replace("\\", "/"), xlink);
	
		    String name = instance.getName();
		    Format restitFormat = Format.getPrettyFormat();
		    restitFormat.setEncoding("UTF-8");
		    XMLOutputter output = new XMLOutputter(restitFormat);
		    if (!new File(Constante.TEMPFOLDER).exists()) {
		    	new File(Constante.TEMPFOLDER).mkdirs();
		    }
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constante.TEMPFOLDER +  name), "UTF-8"));
		    output.output(docInstance, writer);
		    writer.close();
		    return new File(Constante.TEMPFOLDER + name);
		} catch (JDOMException e) {
		    throw new Exception("Error on the instance verification : " + e.getMessage());
		} catch (Exception e2) {
		    throw new Exception("Error on the instance verification : " + e2.getMessage());
		} catch (Error e) {
		    LogManagement.fatal("updateSchemaRef => " + e.getMessage());
		    return null;
		}
    }

    /**
     * Update schema references in instance for the real path to taxonomy.
     * 
     * @param instance - instance file.
     * @param taxonomies - taxonomy files list.
     * 
     * @return updated instance file. Creates backup.
     * 
     * @throws Exception - execution exception.
     */
    private File updateSchemaRef(final File instance, final ArrayList<File> taxonomies) throws Exception {
		LogManagement.info("Update link:schemaRef, xlink:href for an instance.");
		try {
		    SAXBuilder sax = new SAXBuilder();
		    Document docInstance = sax.build(instance);
		    Element root = docInstance.getRootElement();
		    Namespace link = root.getNamespace("link");
		    Namespace xlink = root.getNamespace("xlink");
	
		    if (!taxonomies.isEmpty()) {
				root.removeChildren("schemaRef", link);
				for (File taxonomie : taxonomies) {
				    Element elSchemaRef = new Element("schemaRef", link);
				    elSchemaRef.setAttribute("href", taxonomie.getAbsolutePath().replace("\\", "/"), xlink);
				    elSchemaRef.setAttribute("type", "simple", xlink);
				    root.addContent(0, elSchemaRef);
				}
		    }
	
		    String name = instance.getName();
		    
		    Format restitFormat = Format.getPrettyFormat();
		    restitFormat.setEncoding("UTF-8");
		    
		    XMLOutputter output = new XMLOutputter(restitFormat);
		    if (!new File(Constante.TEMPFOLDER).exists()) {
		    	new File(Constante.TEMPFOLDER).mkdirs();
		    }
		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Constante.TEMPFOLDER +  name), "UTF-8"));
		    output.output(docInstance, writer);
		    writer.close();
		    return new File(Constante.TEMPFOLDER  + name);
		} catch (JDOMException e) {
		    throw new Exception("Error on the instance verification : " + e.getMessage());
		} catch (Exception e2) {
		    throw new Exception("Error on the instance verification : " + e2.getMessage());
		} catch (Error e) {
		    LogManagement.fatal("updateSchemaRef => " + e.getMessage());
		    return null;
		}
    }
    /**
     * Deletes output instance if no facts were generated.
     * @param instPath - formula instance file path.
     * @param factsCount - count of the new facts written.
     */
    private void deleteInstanceIfEmpty(final String instPath, final int factsCount) {
	
		File tmpFile;
		if (factsCount == 0) {
		    tmpFile = new File(instPath);
		    if (!tmpFile.delete()) {
		    	LogManagement.debug("Failed to delete empty output instance.");
		    }
		}
    }
    /**
     * Creates a consolidated validation report.
     * @param inst - instance file.
     */
    private void createConsolidatedResult(final File inst) {
        try {
	    	ResultFormatter hdlr = new ResultFormatter(new PrintStream(fResultOutput, "UTF-8"), inst.getName(), this.instObj, this, taxoId);
	        if (customMessages.exists()) {
	            hdlr.loadCustomMessages(customMessages.getPath());
	        }
	        hdlr.printResults(ValidationUtils.getConfig());
	        this.tablesKO = hdlr.getTablesKO();
	        if (ValidationUtils.getXbrl().getDTS() != null) {
	        	ValidationUtils.getXbrl().getDTS().close();
			 }
	        hdlr = null;
        } catch (Exception e) {
        	LogManagement.error("Failed to create validation report: " + e.getMessage());
        } catch (Error e) {
        	LogManagement.fatal("Failed to create validation report: " + e.getMessage());
        }
    }
    /**
     * Transforms XML validation output to HTML.
     */
    public final void transformToHtml() {
    	
		XsltConverter xslc = new XsltConverter();
		String baseName = FilenameUtils.getBaseName(fResultOutput.getPath());
		File out = new File(fResultOutput.getParent() + "/" + baseName + ".html");
		xslc.transform(fResultOutput, out);
		try {
			fResultOutput.delete();
			fResultOutput = out;
		} catch (Exception e) {
			LogManagement.debug("Failed to delete validation output.");
		}
    }
}
