package ubpartner.xvt.validation;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.SAXException;
import ubmatrix.xbrl.common.exception.src.CoreException;
import ubmatrix.xbrl.common.src.Configuration;
import ubmatrix.xbrl.common.src.IDTS;
import ubmatrix.xbrl.common.src.IDTSResultSet;
import ubmatrix.xbrl.common.src.IXbrlDomain;
import ubmatrix.xbrl.common.src.IXbrlFact;
import ubmatrix.xbrl.common.xml.vom.xbrlModel.VomFact;
import ubmatrix.xbrl.validation.formula.src.IFormulaResult;
import ubmatrix.xbrl.validation.formula.src.IFormulaTraceListener;
import ubmatrix.xbrl.formula.impl.engine.debug.src.StandardFormulaTraceListener;
import ubmatrix.xbrl.formula.impl.engine.resource.variable.src.StandardVariableNames;
import ubmatrix.xbrl.validation.formula.assertion.src.IAssertionResult;
import ubmatrix.xbrl.validation.formula.src.FormulaConfiguration;
import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.commun.UnsatisfiedAssertion;
import ubpartner.utils.xbrl.XbrlFact;
import ubpartner.utils.xbrl.XbrlInstance;
import ubpartner.xvt.validation.commun.GeneralError;
import ubpartner.xvt.validation.commun.ValidationUtils;

/**
 * Class to treat validation results.
 * @author UBPartner
 */
public class ResultFormatter extends StandardFormulaTraceListener {
    /**
     * Constant for formula trace tag.
     */
    private static final String TRACESTYLESHEET = "formulaTraceStylesheet";
    /**
     * Constant for maximum number of failed assertions.
     */
    private int maxFailed = 10;
    /**
     * Formula result to parse.
     */
    private IFormulaResult result;
    /**
     * Configuration file.
     */
    private FormulaConfiguration fconfig;
    /**
     * Validation object.
     */
    private Validation currVal = null;
    /**
     * Possible message languages.
     */
    private Set<Locale> locales = new HashSet<Locale>();
    /**
     * Local.
     */
    private Locale locale =  Locale.ENGLISH;

    /**
     * Map for the messages.
     */
    private HashMap <String, HashMap <String, String>> messages = new HashMap <String, HashMap<String, String>>();

    /**
     * Instance file or stream name.
     */
    private String baseURI;
    /**
     * Taxonomy ID.
     */
    private String taxoId = "";
    /**
     * Output stream.
     */
    private PrintStream out = null;
    /**
     * Current instance.
     */
    private XbrlInstance currInst = null;

    /**
     * Trace level.
     */
    private int traceLevel = FormulaConfiguration.c_tracingNormal;

    /**
     * Flag to print only errors.
     */
    private boolean exceptionsOnly = true;
    /**
     * Formula errors map.
     * 
     */
    private Map<String, Map<String, String>> mapErrValidation = new TreeMap<String, Map<String, String>>();
    /**
     * Map for formula ID errors.
     * 
     */
    private Map<String, Map<String, ArrayList<String>>> mapErrIdFormula = new TreeMap<String, Map<String, ArrayList<String>>>();
    /**
     * Formula expressions errors map.
     * 
     */
    private Map<String, Map<String, ArrayList<Map<String, String>>>> mapErrExpressionFormula = new TreeMap<String, Map<String, ArrayList<Map<String, String>>>>();
    /**
     * Map of the variables and correspondent facts ID.
     */
    private HashMap<String, HashMap<String, ArrayList<String>>> mapVariablesFact = new HashMap<String, HashMap<String, ArrayList<String>>>();
    /**
     * Unsatisfied assertion object.
     */
    private UnsatisfiedAssertion unsatAssertion;
    /**
     * List of the declratives tables with errors.
     */
    private HashMap<String, ArrayList<UnsatisfiedAssertion>> tablesKO = new HashMap<String, ArrayList<UnsatisfiedAssertion>>();
    /**
	 * @return the tablesKO
	 */
	public final HashMap<String, ArrayList<UnsatisfiedAssertion>> getTablesKO() {
		return tablesKO;
	}
	/**
     * Unsatisfied assertions map by ID.
     */
    private HashMap<String, UnsatisfiedAssertion> assertionMap = new HashMap<String, UnsatisfiedAssertion>();
    /**
     * Current assertion.
     */
    private IAssertionResult currA = null;
    /**
     * DTS from the XBRL object.
     */
    private IDTS dts = null;
    /**
     *
     * @param cout
     * @param cbaseURI
     * @param cparent
     */
    public ResultFormatter(PrintStream cout, String cbaseURI, IFormulaTraceListener cparent )
    {
        super(cout, cbaseURI, cparent);
        this.out = cout;
        this.baseURI = cbaseURI;
    }

    /**
     * Creates an instance of ResultFormatter from current stream and for current file path.
     * @param cout - output stream.
     * @param cbaseURI - path to instance file.
     */
    public ResultFormatter(final PrintStream cout, final String cbaseURI) {
        super(cout, cbaseURI, null);
        this.out = cout;
        this.baseURI = cbaseURI;
    }
    
    /**
     * Creates an instance of ResultFormatter from current stream and for current file path.
     * @param cout - output stream.
     * @param cbaseURI - path to instance file.
     * @param validation - validation object.
     */
    public ResultFormatter(final PrintStream cout, final String cbaseURI,  final XbrlInstance instance, Validation validation, final String ctaxoId) {
        super(cout, cbaseURI, null);
        this.out = cout;
        this.baseURI = cbaseURI;
        this.currVal = validation;
        this.currInst = instance;
        this.taxoId = ctaxoId;
    }
    /**
     * Create output with default stylesheet.
     * @param config - formula configuration result.
     */
    public final void printResults(final FormulaConfiguration config) {
        printResults(config, null);
    }

    /**
     * Create output with custom stylesheet.
     * @param config - formula configuration result.
     * @param stylesheet - customized stylesheet.
     */
    public final void printResults(final FormulaConfiguration config, final String stylesheet) {
        this.fconfig = config;
        this.result = config.getResult();
        this.dts =  ValidationUtils.getXbrl().getDTS();
        
        //IDTS dts = config.ge
        this.traceLevel = config.getTraceLevel();

        // add default language
        locales.add(Locale.ENGLISH);

        // set stylesheet
        if (stylesheet != null && !stylesheet.isEmpty()) {
            Configuration.getInstance().setProperty(TRACESTYLESHEET, stylesheet);
        }
        
        this.open(config, "validationTrace");
        this.showSummary(result);
        this.printDuplicateFacts();
        this.printXbrlErrors();
        this.handleAssertions(result);
        this.close("validationTrace");
        try {
			dts.close();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.currVal.setMapErrExpressionFormula(mapErrExpressionFormula);
        this.currVal.setMapErrIdFormula(mapErrIdFormula);
        this.currVal.setMapErrValidation(mapErrValidation);
        this.currVal.setMapAssertions(assertionMap);
    }

    /**
     * Print the summary line of the report.
     * @param currResult - formula result object.
     */
    private void showSummary(final IFormulaResult currResult) {

        HashMap<String, String> attrMap = new HashMap<String, String>();
        
        attrMap.put("xbrlkoCount", String.valueOf(this.currVal.getValidationErrors().size()));
       
        if (this.currInst != null) {
        	attrMap.put("duplicateFactsCount", String.valueOf(this.currInst.getDuplFact().size()));
        } else {
        	attrMap.put("duplicateFactsCount", "0");
        }
        
        if (this.currVal.getValidationErrors().isEmpty()) {
	        attrMap.put("notSatisfiedAssertionCount", String.valueOf(currResult.getAssertionNotSatisfiedCount(null)));
	        attrMap.put("firedAssertionCount", String.valueOf(currResult.getAssertionFireCount(null)));
	        attrMap.put("duration", String.valueOf(currResult.getTimeElapsed()));
	        attrMap.put("outputContextCount", String.valueOf(currResult.getContextCreationCount()));
	        attrMap.put("outputUnitCount", String.valueOf(currResult.getUnitCreationCount()));
	        attrMap.put("firedFormulaCount", String.valueOf(currResult.getFormulaFireCount()));
	        attrMap.put("compiledFormulaCount", String.valueOf(currResult.getFormulaCompileCount()));
	        attrMap.put("satisfiedAssertionCount", String.valueOf(currResult.getAssertionSatisfiedCount(null)));
	        attrMap.put("outputFactCount", String.valueOf(currResult.getFactCreationCount()));
	        attrMap.put("compiledAssertionCount", String.valueOf(currResult.getAssertionCompileCount()));
        } else {
        	attrMap.put("notSatisfiedAssertionCount", "NA");
            attrMap.put("firedAssertionCount", "NA");
            attrMap.put("duration", "NA");
            attrMap.put("outputContextCount", "NA");
            attrMap.put("outputUnitCount", "NA");
            attrMap.put("firedFormulaCount", "NA");
            attrMap.put("compiledFormulaCount", "NA");
            attrMap.put("satisfiedAssertionCount", "NA");
            attrMap.put("outputFactCount", "NA");
            attrMap.put("compiledAssertionCount", "NA");
        }
        // print the element
        this.element("summary", attrMap, traceLevel, FormulaConfiguration.c_tracingSummary);

    }

    /**
     * Loop around the assertion result set.
     * Sort the results by id, then separate into non-satisfied and satisfied groups.
     * @param results - formula resuts object.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleAssertions(final IFormulaResult results) {

        // Get fired assertions
        TreeSet<String> firedSet = new TreeSet(results.getFiredAssertionIDs());

        TreeSet<String> satisfiedSet = new TreeSet<String>();
        TreeSet<String> nonSatisfiedSet = new TreeSet<String>();


        for (String assertionId : firedSet) {
            if (results.getAssertionNotSatisfiedCount(assertionId) > 0) {
                nonSatisfiedSet.add(assertionId);
            } else {
                satisfiedSet.add(assertionId);
            } 
        }

        HashMap <String, String> attrMap = new HashMap<String, String>();

        if (!satisfiedSet.isEmpty()) {
            attrMap.put("count", String.valueOf(satisfiedSet.size()));
            this.enter("satisfiedAssertions", attrMap, traceLevel, FormulaConfiguration.c_tracingSummary);
            for (String assertionId : satisfiedSet) {
                handleSingleAssertion(results, assertionId);
            }
            this.leave("satisfiedAssertions", traceLevel, FormulaConfiguration.c_tracingSummary);
        }

        if (!nonSatisfiedSet.isEmpty()) {
            attrMap.put("count", String.valueOf(nonSatisfiedSet.size()));
            this.enter("nonSatisfiedAssertions", attrMap, traceLevel, FormulaConfiguration.c_tracingSummary);
            for (String assertionId : nonSatisfiedSet) {
                handleSingleAssertion(results, assertionId);
            }
            this.leave("nonSatisfiedAssertions", traceLevel, FormulaConfiguration.c_tracingSummary);
        }
    }

    /**
     * Process single assertion.
     * Loop around the evaluations.
     * @param results - formula results.
     * @param assertionId - assertion ID.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleSingleAssertion(final IFormulaResult results, final String assertionId)
    {
        // details first
        HashMap <String, String>attrMap = new HashMap<String, String>();
        unsatAssertion = new UnsatisfiedAssertion(assertionId);
        attrMap.put("name", assertionId);

        IXbrlDomain assertion = results.getAssertionResult(assertionId).getAssertion();
        
        attrMap.putAll(assertion.getAttributeMap());

        // will use count later
        int fired = results.getAssertionFireCount(assertionId);
        int failed = results.getAssertionNotSatisfiedCount(assertionId);
        attrMap.put("fired", String.valueOf(fired));
        unsatAssertion.setFired(String.valueOf(fired));
        attrMap.put("notSatisfied", String.valueOf(failed));
        unsatAssertion.setUnsatisfied(String.valueOf(failed));
        attrMap.put("satisfied", String.valueOf(results.getAssertionSatisfiedCount(assertionId)));
        unsatAssertion.setSatisfied(String.valueOf(results.getAssertionSatisfiedCount(assertionId)));
        // overwrite type
        attrMap.put("type", assertion.getLocalName());

        // look for labels
        String label = this.fetchLabel(assertion, "http://www.xbrl.org/2008/role/verboseLabel", "en");

        // try standard label
        if (label == null || label.isEmpty()) {
            label = this.fetchLabel(assertion, "http://www.xbrl.org/2008/role/label", "en");
        }
        if (label != null && !label.isEmpty()) {
            attrMap.put("assertionLabel", label);
        } else {
        	label = "No description provided";
        	attrMap.put("assertionLabel", label);
        }
        unsatAssertion.setLabel(label);
        //process assertion
        processAssertion(assertionId, assertion, attrMap);
        
        //String assertionMess = this.fetchLabel(assertion, "http://www.xbrl.org/2008/role/label", "http://xbrl.org/arcrole/2010/assertion-unsatisfied-message", "en");
        String assertionMess = "";
        
        this.enter("assertion", attrMap, traceLevel, FormulaConfiguration.c_tracingSummary);
        int eval = 0;
        int failCount = 0;
        Map evalMap = new HashMap<String, String>();
        for (Iterator<IAssertionResult> iter = results.getAssertionResults(assertionId); iter.hasNext();) {
            IAssertionResult assertionResult = iter.next();
            
            // create entry for evaluation
            evalMap.putAll(assertionResult.getAssertion().getAttributeMap());
            evalMap.put("evaluationID", assertionId.concat("_" + String.valueOf(eval)));
            unsatAssertion.setEvaluationId(assertionId.concat("_" + String.valueOf(eval)));
            String test = "";
            try {
               test = (String) evalMap.get("test");
            } catch (Exception e) {
            	test = "";
            }
            unsatAssertion.setTest(test);
            // ensure that ids are unique
            evalMap.put("id", assertionId.concat("_" + String.valueOf(eval)));

            evalMap.put("satisfied", String.valueOf(assertionResult.isSatisfied()));
            evalMap.put("cnt", String.valueOf(eval));

            // override message
            String evalMess = this.getAssertionEvaluationMessage(assertionId, assertionResult, assertionMess);
            evalMap.put("message", evalMess);
            unsatAssertion.setEvalMess(evalMess);

            // print to log file
            // only handle failures by default
            // show all if exceptionsOnly is false
            if (!assertionResult.isSatisfied() || !this.exceptionsOnly) {
            	
                // print to log if limit not reached
                if (eval < this.maxFailed) {
                    // only print evaluation in full report
                    if (this.shouldTrace(traceLevel, FormulaConfiguration.c_tracingFullReport)) {
                    	this.handleEvaluation(evalMap, assertionResult.getVariables(), evalMess);
                    } 
                }

                if (!assertionResult.isSatisfied()) {
                    failCount++;
                }
                // print message when we hit limit
                if (eval == this.maxFailed) {
                    // truncate output
                    evalMap.clear();
                    evalMap.put("cnt", String.valueOf(eval).concat("-").concat(String.valueOf(fired - 1)));

                    if (this.exceptionsOnly) {
                        evalMap.put("satisfied", "false");
                        evalMap.put("message", String.valueOf(failed - eval) + " failed evaluations where detailed data was truncated");
                    } else {
                    	if (failCount != failed) {
                            evalMap.put("satisfied", "false");
                    	} else {
                    		evalMap.put("satisfied", "true");
                    	}
                        evalMap.put("message", String.valueOf(fired - eval) + " evaluations where detailed data was truncated");
                    }

                    this.element("assertionTest", evalMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
                }
                reformatTest();
                eval++;
            }
        }
        this.leave("assertion", traceLevel, FormulaConfiguration.c_tracingSummary);
    }

    /**
     * Get message for the evaluation.
     * Try to get formula messages.
     * Call extension point processAssertionEvaluationMessage() with any message found.
     * @param assertionId - assertion ID.
     * @param assertionResult - assertion result object.
     * @param assertionMess - message for assertion.
     * @return message for the evaluation.
     */
	private String getAssertionEvaluationMessage(final String assertionId, final IAssertionResult assertionResult, final String assertionMess) {
        // set default message
        String defaultMessage = assertionMess;
        if (assertionResult.getMessage() != null) {
        	defaultMessage = assertionResult.getMessage();
        }
        String clocale = this.locale.getLanguage();

        // try to get message map for languages
        Map <String, Set<String>> cmessages = assertionResult.getMessages(locales);

        if (cmessages != null) {
            if (!cmessages.containsKey(clocale)) {
                clocale = Locale.ENGLISH.getLanguage();
            }
            // try to get message
            Set<String> localMessage = cmessages.get(clocale);

            if (localMessage != null && !localMessage.isEmpty()) {
                defaultMessage = localMessage.iterator().next();
            }
        }

        // call message handler
        return processAssertionEvaluationMessage(assertionId, assertionResult, defaultMessage);
    }

    /**
     * Handle single evaluation
     * Print information to log file then work through variables.
     * @param attributeMap - map with attributes.
     * @param variables - map with variables.
     * @param label - error message.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void handleEvaluation(final Map attributeMap, final Map variables, final String label) {
        this.enter("assertionTest", attributeMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
        this.enter("variables", null, traceLevel, FormulaConfiguration.c_tracingFullReport);
        if (variables != null && (variables.size() > 0)) {
            // then we have fact variables in the assertionName
            // use sorted set to get variables in order
            SortedSet<String> keys = new TreeSet<String>(variables.keySet());

            HashMap <String, String>attrMap = new HashMap<String, String>();
            for (String name : keys) {
                // ignore names starting with '_' as they are internal
                if (StandardVariableNames.isStandardName(name)) {
                    continue;
                }
                // get the variable value
                Object var = variables.get(name);

                // determine if it is a QName referencing a parameter
                if (name.contains("#")) {
                	try {
	                    String[] parts = name.split("#");
	                    if (parts.length == 2) {
	                        // see if we can determine a prefix
	
	                        String prefix;
	                        if (dts != null) {
		                        try {
		                            prefix = dts.getPrefixForNamespace(parts[0]);
		                            if (prefix != null) {
		                                name = prefix.concat(":").concat(parts[1]);
		                            }
		                        } catch (CoreException ex) {
		                            LogManagement.error("Failed to parse messages from DTS: " + ex.getMessage());
		                        }
	                        } else {
	                        	LogManagement.debug("No dts in result set.");
	                        }
	                    }
                    } catch (Exception e) {
                    	LogManagement.debug("Failed to process QName variable");
                    }
                }

                attrMap.clear();
                attrMap.put("name", name);
                this.enter("variable", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);


                if (var != null) {
                    // what is is it ?
                    if (var instanceof IDTSResultSet) {
                        // sequence
                        int count =  ((IDTSResultSet) var).getCount();
                        int limit = count;
                        if (count > this.maxFailed) {
                        	limit = this.maxFailed;
                        }

                        attrMap.clear();
                        attrMap.put("count", String.valueOf(count));
                        attrMap.put("displayed", String.valueOf(limit));
                        
                        if (count > limit) {
                            attrMap.put("notDisplayed", String.valueOf(count - limit));
                        }

                        this.enter("sequence", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);

                        // get the iterator
                        Iterator iter = ((IDTSResultSet) var).getEnumerator();

                        while (limit > 0 && iter.hasNext()) {
                            this.handleFact(iter.next(), (String) attributeMap.get("id"), label, name);
                            limit--;
                        }
                        this.leave("sequence", traceLevel, FormulaConfiguration.c_tracingFullReport);
                    } else {
                        this.handleFact(var, (String) attributeMap.get("id"), label, name);
                    }
                }
                this.leave("variable", traceLevel, FormulaConfiguration.c_tracingFullReport);
            }
        }
        this.leave("variables", traceLevel, FormulaConfiguration.c_tracingFullReport);
        this.leave("assertionTest", traceLevel, FormulaConfiguration.c_tracingFullReport);

    }


    /**
     * Print fact information to the log file.
     * Output single fact.
     * @param item - XbrlFact or VOMFact object.
     * @param evalId - evaluation ID.
     * @param label - evaluation/assertion label.
     * @param varName - name of variable.
     */
    private void handleFact(final Object item, final String evalId, final String label, final String varName) {
        String value = "";
        String context = "";
    	String concept = "";
        HashMap <String, String>attrMap = new HashMap<String, String>();
        
        try  {
            if (item instanceof IXbrlFact) {
                IXbrlFact fact = (IXbrlFact) item;

                // single fact
                attrMap.put("element", fact.getConcept().getPrefixedName());
                context = fact.getContext();
                if (!StringUtils.isEmpty(context)) {
                	attrMap.put("context", context);
                }

                String unit = fact.getUnit();
                if (unit != null) {
                	attrMap.put("unit", unit);
                }

                if (fact.isNil()) {
                	attrMap.put("isNil", "true");
                }

                String factid = fact.getAttributeValue("", "id");
                if (factid != null) {
                	String tableId = ConstanteUtils.getTableIdFromFact(factid);
                	if (!tableId.equals("")) {
                		if (tablesKO.containsKey(tableId)) {
                			ArrayList<UnsatisfiedAssertion> assertionsList = tablesKO.get(tableId);
                			boolean vrfua = false;
                			for (UnsatisfiedAssertion ua : assertionsList) {
                				if (ua.getAssertionId().equals(unsatAssertion.getAssertionId())) {
	                				vrfua = true;
	                			}
                			}
                			if (!vrfua) {
                				assertionsList.add(unsatAssertion);
                			}
                			tablesKO.remove(tableId);
                			tablesKO.put(tableId, assertionsList);
                		} else {
                			ArrayList<UnsatisfiedAssertion> assertionsList = new ArrayList<UnsatisfiedAssertion>();
                			assertionsList.add(unsatAssertion);
                			tablesKO.put(tableId, assertionsList);
                		}
                	}
                	attrMap.put("factId", factid);
                } else {
                	factid = "";
                }
                putVariable(evalId, varName, factid);
               
                attrMap.put("value", fact.getValue());
                concept = fact.getFullName();
                value = fact.getValue();
                
                loadEvaluationFacts(context, concept, value, evalId, label);
            }

            if (item instanceof VomFact) {
                VomFact fact = (VomFact) item;

                attrMap.put("element", fact.getQualifiedName());

                context = fact.getContextRef();
                if (context != null) {
                	attrMap.put("context", context);
                }

                String unit = fact.getUnitRef();
                if (unit != null) {
                	attrMap.put("unit", unit);
                }

                if (fact.hasAttribute("nil")) {
                	attrMap.put("isNil", "true");
                }

                String factid = fact.getId();
                if (factid != null) {
                	String tableId = ConstanteUtils.getTableIdFromFact(factid);
                	if (!tableId.equals("")) {
                		if (tablesKO.containsKey(tableId)) {
                			ArrayList<UnsatisfiedAssertion> assertionsList = tablesKO.get(tableId);
                			boolean vrfua = false;
                			for (UnsatisfiedAssertion ua : assertionsList) {
                				if (ua.getAssertionId().equals(unsatAssertion.getAssertionId())) {
	                				vrfua = true;
	                			}
                			}
                			if (!vrfua) {
              				assertionsList.add(unsatAssertion);
                			}
                			tablesKO.remove(tableId);
                			tablesKO.put(tableId, assertionsList);
                		} else {
                			ArrayList<UnsatisfiedAssertion> assertionsList = new ArrayList<UnsatisfiedAssertion>();
                			assertionsList.add(unsatAssertion);
                			tablesKO.put(tableId, assertionsList);
                		}
                	}
                	attrMap.put("factId", factid);
                } else {
                	factid = "";
                }
                putVariable(evalId, varName, factid);

                attrMap.put("value", fact.getValue());
                concept = fact.getFullName();
                value = fact.getValue();
                
                loadEvaluationFacts(context, concept, value, evalId, label);
            }
            if (item instanceof net.sf.saxon.value.AtomicValue) {
            	putVariable(evalId, varName, "No value provided");
                attrMap.put("element", "No value provided");
                attrMap.put("value", ((net.sf.saxon.value.AtomicValue) item).getStringValue());
            }
            this.element("fact", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
        } catch (Exception e) {
        	LogManagement.error("Unhandeled exeption: " + e.getMessage());
        }
    }

    /**
     * Fetch assertion label from the taxonomy if there is one.
     * Look for a verbose label first, then a standard label.
     * @param assertion - assertion object.
     * @param roleUri - role URI.
     * @param lang - language.
     * @return - label or verbose for assertion or empty string if there is no label in the taxonomy.
     */
    @SuppressWarnings("rawtypes")
	private String fetchLabel(final IXbrlDomain assertion, final String roleUri, final String lang) {
        String label = "";
        try {
            IDTSResultSet labels = dts.getToElementsByFromElement(assertion, null, "http://xbrl.org/arcrole/2008/element-label", -1);
            // might not have a label
            if (labels == null) {
                return "";
            }
            label = ((IXbrlDomain) labels.get(0)).getValue();

            // look for language and role
            for (Iterator i = labels.getEnumerator(); i.hasNext();) {
                IXbrlDomain labelObj = (IXbrlDomain) i.next();

                // check language
                if (lang != null && !lang.isEmpty()) {
                    // else check lang
                    String langAttr = labelObj.getAttributeValue(labelObj.getPrefixForNamespace("http://www.w3.org/XML/1998/namespace"), "lang");
                    
                    // is it same lang ?
                    if (langAttr == null || !langAttr.equals(lang)) {
                        // no - go to next label
                        continue;
                    }
                }

                // check arc role
                if (roleUri == null) {
                    return labelObj.getValue();
                }
                if (labelObj.getAttributeValue("", "role").equals(roleUri)) {
                    return  labelObj.getValue();
                } 
                // otherwise continue to look for arc role
            }
        } catch (Exception e) {
           LogManagement.debug("Failed to fetch assertion label in taxonomy: " + e.getMessage());
        }
        return label;
    }

    /**
     * Set failure limit for evaluations and number of items in sequence.
     * By default this value is 10
     * @param cfailed - limit number for failed assertions to print.
     */
    public final void setFailedLimit(final int cfailed) {
        this.maxFailed = cfailed;
    }
    /**
     * Add locale to list.
     * @param clocale - local to add.
     */
    public final void setLocale(final Locale clocale) {
        this.locale = clocale;
        this.locales.clear();
        this.locales.add(locale);
        this.locales.add(Locale.ENGLISH);
    }

    /**
     * By default only non-satisfied assertions are handled.
     * Setting this value to true will cause all assertions to be handled in the logs
     * and via the extension points.
     * @param cexceptions - flag to print or not exceptions.
     */
    public final void setExceptionsOnly(final boolean cexceptions) {
        this.exceptionsOnly = cexceptions;
    }

    /**
     * Process assertion - extension point for an assertion.
     * Is only called once per assertion before all evaluations.
     * Called for all assertions - satisfied or not.
     * By default adds all custom data as attributes in the <assertion> tag.
     * @param assertionId - assertion ID.
     * @param assertion - assertion object.
     * @param attrMap - attributes map.
     */
    public final void processAssertion(final String assertionId, final IXbrlDomain assertion, Map <String, String> attrMap)  {
    	// add all attributes
        if (this.messages.containsKey(assertionId)) {
            attrMap.putAll(this.messages.get(assertionId));
            unsatAssertion.createCustomMess(this.messages.get(assertionId));
        }
    }

    /**
     * Process the evaluation message
     * Can override to add extra information.
     * By default the tag "message" associated with evaluations as this corresponds to the XSLT
     * But any customization is allowed.
     *
     * @param assertionId - assertion ID.
     * @param assertionResult - assertion object.
     * @param defaultMessage current message value
     * @return message that will be printed to the log
     */
    public final String processAssertionEvaluationMessage(final String assertionId, final IAssertionResult assertionResult, final String defaultMessage) {
        // add custom message if there is one
        HashMap <String, String> map = this.messages.get(assertionId);
        if (map != null && map.containsKey("message")) {
            try {
				return defaultMessage + new String(map.get("message").getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				LogManagement.debug(e.getMessage());
			}
        }
        return defaultMessage;
    }


    /**
     * Load custom messages from a file.
     * Assume that the enclosing tag is named "entry".
     * @param fileName - file name.
     * @throws javax.xml.parsers.ParserConfigurationException - configuration exception.
     * @throws org.xml.sax.SAXException - SAXBuilder exception.
     * @throws java.io.IOException - writing into file exception.
     */
    public final void loadCustomMessages(final String fileName) throws ParserConfigurationException, SAXException, IOException {
        loadCustomMessages(fileName, "entry");
    }

    /**
     * Load custom messages from a file.
     *
     * The id attribute is the key.
     * All other child tags are stored as key-value pairs - no checks are performed.
     * Obviously the tags need to match the XSLT
     *
     * @param fileName input XML file
     * @param nodeName name of node containing the information
     * @throws javax.xml.parsers.ParserConfigurationException - configuration exception.
     * @throws org.xml.sax.SAXException - SAXBuilder exception.
     * @throws java.io.IOException - writing into file exception.
     */
    @SuppressWarnings("unchecked")
	public final void loadCustomMessages(final String fileName, final String nodeName) throws ParserConfigurationException, SAXException, IOException {

        File file = new File(fileName);
        SAXBuilder sb = new SAXBuilder();
        Document document;
		try {
			document = sb.build(file);
	        // loop file and read items
	        List<Element> entries = document.getRootElement().getChildren(nodeName);
	        for (Element entry : entries) {
	            String id = entry.getAttributeValue("id");
	
	            // ignore if no id
	            if (StringUtils.isEmpty(id)) {
	                continue;
	            }
	            // get child nodes
	            HashMap<String, String> attrMap = new HashMap<String, String>();
	            String content = null;
	
	            // get the children and put in a map
	
	            List<Element> childNodes = entry.getChildren();
	            if (childNodes == null) {
	                continue;
	            }
	            for (Element child : childNodes) {
	            	String name = child.getName();
	            	content = child.getTextNormalize();
	            	if (!StringUtils.isEmpty(content)) {
	            		attrMap.put(name, content);
	            	}
	            }
	            /*do {
	                String name = child.getNodeName();
	                if (child instanceof org.w3c.dom.Element) {
	                    content = child.getTextContent();
	
	                    if (content != null && !content.isEmpty()) {
	                        attrMap.put(name, content);
	                    }
	                }
	                child = child.getNextSibling();
	            } while (child != null);*/
	
	            // add map to list if it has anything
	            this.messages.put(id, attrMap);
	        }
		} catch (Exception e) {
			LogManagement.warn("Failed to read custom messages file.");
		}
		sb = null;
    }
   
    /**
     * Formula facts details.
     * 
     * @param context - fact context.
     * @param concept - fact concept.
     * @param value - fact value.
     * @param assertionId - evaluation ID.
     * @param mess - evaluation error message.
     */
    public final void loadEvaluationFacts(final String context, final String concept, final String value, final String assertionId, final String mess) {
		LogManagement.debug("Retrieve details of assertion facts.");	

		if (!StringUtils.isEmpty(context)) {
 		    Map<String, String> mapFact = new HashMap<String, String>();
		    Map<String, ArrayList<String>> mapFactIdFormula  = new HashMap<String, ArrayList<String>>();
		    Map<String, ArrayList<Map<String, String>>> mapFactExpressionFormula  = new HashMap<String, ArrayList<Map<String, String>>>();
		    ArrayList<String> listFactIdFormula = new ArrayList<String>();
		    ArrayList<Map<String, String>> listFactExpressionFormula  = new ArrayList<Map<String, String>>();
	
		    Map<String, String> mapExpressionFormula  = new HashMap<String, String>();
		    mapExpressionFormula.put(assertionId, mess);
		    if (mapErrValidation.containsKey(context)) {
				mapFact.putAll(mapErrValidation.get(context));
				mapFact.put(concept, value);
				mapErrValidation.remove(context);
				mapErrValidation.put(context, mapFact);
		    } else {
				mapFact.put(concept, value);
				mapErrValidation.put(context, mapFact);
		    }
	
		    if (mapErrIdFormula.containsKey(context)) {
		    	mapFactIdFormula.putAll(mapErrIdFormula.get(context));
				if (mapFactIdFormula.containsKey(concept)) {
				    listFactIdFormula = mapFactIdFormula.get(concept);
				    if (!listFactIdFormula.contains(assertionId)) {
				        listFactIdFormula.add(assertionId);
				    }
				    mapFactIdFormula.remove(concept);
				    mapFactIdFormula.put(concept, listFactIdFormula);
				} else {
				    listFactIdFormula.add(assertionId);
				    mapFactIdFormula.put(concept, listFactIdFormula);
				}
				mapErrIdFormula.remove(context);
				mapErrIdFormula.put(context, mapFactIdFormula);
		    } else {
				if (mapFactIdFormula.containsKey(concept)) {
				    listFactIdFormula = mapFactIdFormula.get(concept);
				    if (!listFactIdFormula.contains(assertionId)) {
				        listFactIdFormula.add(assertionId);
				    }
				    mapFactIdFormula.remove(concept);
				    mapFactIdFormula.put(concept, listFactIdFormula);
				} else {
				    listFactIdFormula.add(assertionId);
				    mapFactIdFormula.put(concept, listFactIdFormula);
				}
				mapErrIdFormula.put(context, mapFactIdFormula);
		    }
	
		    if (mapErrExpressionFormula.containsKey(context)) {
		        mapFactExpressionFormula.putAll(mapErrExpressionFormula.get(context));
				if (mapFactExpressionFormula.containsKey(concept)) {
				    listFactExpressionFormula = mapFactExpressionFormula.get(concept);
				    listFactExpressionFormula.add(mapExpressionFormula);
				    mapFactExpressionFormula.put(concept, listFactExpressionFormula);
				} else {
				    listFactExpressionFormula.add(mapExpressionFormula);
				    mapFactExpressionFormula.put(concept, listFactExpressionFormula);
				}
				mapErrExpressionFormula.remove(context);
				mapErrExpressionFormula.put(context, mapFactExpressionFormula);
		    } else {
				if (mapFactExpressionFormula.containsKey(concept)) {
				    listFactExpressionFormula = mapFactExpressionFormula.get(concept);
				    listFactExpressionFormula.add(mapExpressionFormula);
				    mapFactExpressionFormula.put(concept, listFactExpressionFormula);
				} else {
				    listFactExpressionFormula.add(mapExpressionFormula);
				    mapFactExpressionFormula.put(concept, listFactExpressionFormula);
				}
				mapErrExpressionFormula.put(context, mapFactExpressionFormula);
		    }
		}
    }
    /**
     * Adds variable with fact to the map.
     * @param evalId - evaluation ID.
     * @param varName - variable name.
     * @param factid - fact ID.
     */
    private void putVariable(final String evalId, final String varName, final String factid) {
        HashMap<String, ArrayList<String>> varMap = new  HashMap<String, ArrayList<String>>();
    	if (mapVariablesFact.containsKey(evalId)) {
        	varMap = mapVariablesFact.get(evalId);
        	if (varMap.containsKey(varName)) {
        		ArrayList<String> facts = varMap.get(varName);
            	if (!facts.contains(factid)) {
            	    facts.add(factid);
            		varMap.remove(varName);
            		varMap.put(varName, facts);
            	}
        	} else {
        		ArrayList<String> facts = new ArrayList<String>();
        		facts.add(factid);
        		varMap.put(varName, facts);
        	}
        	mapVariablesFact.remove(evalId);
        	mapVariablesFact.put(evalId, varMap);
        } else {
        	ArrayList<String> facts = new ArrayList<String>();
    		facts.add(factid);
    		varMap.put(varName, facts);
    		mapVariablesFact.put(evalId, varMap);
        }
    }
    /**
     * Reformats the evaluation test with fact IDs.
     */
    public final void reformatTest() {
    	
    	String evalId = unsatAssertion.getEvaluationId();
    	String test = unsatAssertion.getTest();
    	boolean put = true;
    	if (!StringUtils.isEmpty(test)) {
    	   	
	    	HashMap<String, ArrayList<String>> varFactsMap = mapVariablesFact.get(evalId);
	    	if (varFactsMap != null) {
	    	SortedSet<String> vars = new TreeSet<String>(varFactsMap.keySet());
	    	for (String var : vars) {
	    		ArrayList<String> facts = varFactsMap.get(var);
	    		String replValue = "";
	    		if (facts.size() > 1) {
	    			for (String fact : facts) {
	    				if (replValue.equals("")) {
	    					replValue = fact;
	    				} else {
	    					replValue = replValue + ", " + fact;
	    				}
	    			}
	    		} else {
	    			replValue = facts.get(0);
	    		}
	    		if (!replValue.equals("")) {
	    		    test = test.replaceAll("\\$" + var, "[" + replValue + "]");
	    		}
	    	}
	    	unsatAssertion.setTest(test);
	    	} else {
	    		put = false;
	    	}
    	}
    	if (put) {
	    	UnsatisfiedAssertion clonAssertion = unsatAssertion.cloneTo();
	    	assertionMap.put(evalId, clonAssertion);
    	}
    }
    /**
     * Adds XBRL and Dimension validation errors to the consolidated file.
     */
    private void printXbrlErrors() {
    	HashMap<String, String> attrMap = new HashMap<String, String>();
    	ArrayList<GeneralError> genErr = this.currVal.getValidationErrors();
    	if (!genErr.isEmpty()) {
    		attrMap.put("id", "xbrlErrors");
    		attrMap.put("count", String.valueOf(genErr.size()));
    		this.enter("xbrlErrors", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
    		for (GeneralError ge : genErr) {
    			attrMap.clear();
    			attrMap.put("id", String.valueOf(ge.getErrId()));
    			attrMap.put("type", String.valueOf(ge.getErrType()));
    			attrMap.put("file", String.valueOf(ge.getFileAffected()));
    			attrMap.put("source", String.valueOf(ge.getDet()));
    			attrMap.put("message", String.valueOf(ge.getMess()));
    			this.element("xbrlError", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
    		}
    		this.leave("xbrlErrors", traceLevel, FormulaConfiguration.c_tracingFullReport);
    	}
    }
    /**
     * Adds duplicate facts errors to the consolidated file.
     */
    private void printDuplicateFacts() {
    	HashMap<String, String> attrMap = new HashMap<String, String>();
    	if (this.currInst != null) {
    		HashMap<String, ArrayList<XbrlFact>> duplFacts = this.currInst.getDuplFact();
    		if (!duplFacts.isEmpty()) {
    			attrMap.put("id", "duplicateFacts");
    			attrMap.put("count", String.valueOf(duplFacts.size()));
    			this.enter("duplicateFacts", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
    			for (Entry<String, ArrayList<XbrlFact>> currEnt: duplFacts.entrySet()) {
    				attrMap.clear();
    				this.enter("duplicateFact", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
    				for (XbrlFact duplFact : currEnt.getValue()) {
    					attrMap.clear();
    					attrMap.put("concept", duplFact.getConceptNs().getURI() + "#" + duplFact.getConceptId());
    					attrMap.put("context", duplFact.getContextRef());
    					attrMap.put("sheet", String.valueOf(duplFact.getSheetName()));
    					attrMap.put("rangeRef", String.valueOf(duplFact.getRangeRef()));
    					attrMap.put("value", String.valueOf(duplFact.getValue()));
    					attrMap.put("decimals", String.valueOf(duplFact.getDecimals()));
    					attrMap.put("mapID", String.valueOf(duplFact.getId()));
    					this.element("iteration", attrMap, traceLevel, FormulaConfiguration.c_tracingFullReport);
    				}
    				this.leave("duplicateFact", traceLevel, FormulaConfiguration.c_tracingFullReport);
    			}
    			this.leave("duplicateFacts", traceLevel, FormulaConfiguration.c_tracingFullReport);
    		}
    	}
    }
    
}
