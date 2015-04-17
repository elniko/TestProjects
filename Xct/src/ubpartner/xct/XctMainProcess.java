package ubpartner.xct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.filter.ElementFilter;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import ubpartner.xct.commun.Constante;
import ubpartner.xct.commun.ConstanteMessages;
import ubpartner.xct.commun.ConstanteXmlContext;
import ubpartner.xct.commun.ConstanteXmlMapping;
import ubpartner.xct.commun.FactComparator;
import ubpartner.logmanagement.LogManagement;
import ubpartner.taxonomymanagement.ConfiguratedTaxonomy;
import ubpartner.taxonomymanagement.DeclaratifTable;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.commun.CsvInput;
import ubpartner.utils.xbrl.XbrlContext;
import ubpartner.utils.xbrl.XbrlInstance;
/**
 * Class for main processing methods of the XCT.
 * @author UBPartner
 */
public final class XctMainProcess {
	/**
	 * Map with contexts Ids.
	 */
	private static HashMap<String, Element> mapContexts = new HashMap<String, Element>();
	/**
	 * Map of the additional namespaces in context document.
	 */
    private static HashMap<String, String> mapNms = new HashMap<String, String>();
    /**
     * Map with unidentified QNames.
     */
    private static HashMap <String, ArrayList<String>> mapQNErrors = new HashMap<String, ArrayList<String>> ();
    /**
     * Units measure.
     */
    private static HashMap<String, String> mapUnits = new HashMap<String, String>();
	/**
     * Empty constructor for the final class.
     */
    private XctMainProcess() {
        //Class can not be instantiated.
    }
    /**
     * Function that gets values from Excel file according to mapping.
     * 
     * @param cexcelwbk - Excel file with values.
     * @param mapping - mapped facts collection.
     * @param valueMapping - empty structure for the new mapping with values
     * @param subsetConversion - convert all tabs or not 
     * @param currEndDate - current instance end date.
     * @param currTaxo - current taxonomy object.
     * @return -  true - if at least one value was found according to mapping
     *             false - if no value corresponds to mapping
     */
     @SuppressWarnings("rawtypes")
    public static int getValuesFromExcel(final XSSFWorkbook cexcelwbk,
             TreeSet<MappedFact> mapping, HashMap<String, ArrayList<MappedFact>> valueMapping,
             final String subsetConversion, String currEndDate,
             final ConfiguratedTaxonomy currTaxo, final DeclaratifTable currTable, Document contextsDoc, HashMap<String, Object> mapId,
             LinkedHashMap<String, ArrayList<Element>> contexts, XbrlInstance instCheat, CsvInput csvFile, ArrayList<Element> unitNodes) {
    	LogManagement.info("Geting values from file according to mapping.");
    	int resCnt = 0;
    	long startExecTime = System.currentTimeMillis();
        MappedFact currMF;
        MappedFact mfWithValue = null;
        Iterator it;
        XSSFCell mappedCell;
        String sheetName;
        String rangeRef;
        String cellValue = "";
        String type = "";
        String tabsMode = "";
        String tblConcept = "";
        String tblNms = "";
        boolean vrfDeclFact = false;
        boolean tabsListExists = true;
        boolean vrfConvert = false;
        int month = 0;
        mapQNErrors = new HashMap<String, ArrayList<String>>();
        mapUnits = new HashMap<String, String>();
        ArrayList<String> csvMap = new ArrayList<String>();
        
        if (csvFile != null) {
            csvMap = csvFile.getMappingList();
        }
        HashMap<String, String> ongletConversion = new HashMap<String, String>();
        intializeContextsMap(contextsDoc);
        
        reinitUnits(unitNodes, currTaxo, currTable);
        
    	month = ConstanteUtils.getMonthFromStringDate(currEndDate);
    	try {
    		if (currTable == null) {
    		    ongletConversion = currTaxo.getConfiguredTabsForPeriod(month);
                tabsMode = currTaxo.getTabsMode();
                tabsListExists = true; 
    	    } else {
    	       tabsListExists = false;
    	    }
    	} catch (Exception e) {
     		LogManagement.debug("getValuesFromExcel => Tabs list for conversion not found for the taxonomy "
     				+ currTaxo.getTaxoLibelle());
     		tabsListExists = false;
    	} catch (Error e) {
    		LogManagement.debug("getValuesFromExcel => Tabs list for conversion not found for the taxonomy "
     				+ currTaxo.getTaxoLibelle());
     		tabsListExists = false;
     	}
        if (currTable != null) {
    		tblConcept = currTable.getConceptName();
    		tblNms = currTable.getConceptNms();
    	} else {
    		vrfDeclFact = true;
    	}
        if ((subsetConversion.equals("y")) && (tabsListExists)) {       	
	        if (ongletConversion.isEmpty()) {
	        	LogManagement.warn("No tabs to convert for the current period. Instance file wouldn't be created. Please verify your TAXO_config.xml file");
	        	return 0;
	        } else {
	        	vrfConvert = true;
	        }
        }
        it = mapping.iterator();
        boolean convert;
        LogManagement.info("Treating facts.");
        while (it.hasNext()) {
		      currMF = (MappedFact) it.next();
		      sheetName = currMF.getSheetName();
		      rangeRef = currMF.getRangeRef();
		      if (csvMap.contains(sheetName + "!" + rangeRef)) {
		    	  csvMap.remove(sheetName + "!" + rangeRef);
		      }
		      if (vrfConvert) {
		    	 convert = isToConvert(sheetName, ongletConversion, tabsMode);
		      } else {
		    	  convert = true;
		      }
		      if (convert) {
			      rangeRef = currMF.getRangeRef();
		          
			        if ((currMF.getConceptName().equals(tblConcept))
			        		&& (currMF.getNmnSpace().getURI().equals(tblNms))) {
			        	resCnt ++;
			        	mfWithValue = currMF;
			        	mfWithValue.setValueType(type);
			        	mfWithValue.setValue(currTable.getTupleValue());
			        	mfWithValue.setTableId(currTable.getTableId());
			        	mfWithValue.setIdMappingRef("Table_" + currTable.getTableId() + "_" + mfWithValue.getIdMappingRef());
			        	controleDuplicateFact(mfWithValue, mapId, contexts, instCheat, valueMapping);
				        vrfDeclFact = true;
			        } else {
						mappedCell = ConfigurationParametres.getCellByReference("'" + sheetName + "'!" + rangeRef, cexcelwbk, false);
						if (mappedCell != null) {
				            cellValue = ConstanteUtils.getFormattedCellValue(mappedCell);
						} else {
						    cellValue = "";
						}
                        //pour les cellules avec des valeurs mettre a jour le mapped fact correspondent
					       if (!cellValue.equals("")) {
					    	   cellValue = replaceQName(cellValue, currMF.getConceptName(), currMF.getNmnSpace());
					    	   resCnt ++;
					           mfWithValue = currMF;
					           mfWithValue.setValueType(type);
					           mfWithValue.setValue(cellValue);
					           if (!mapUnits.isEmpty()) {
					        	   if (!StringUtils.isEmpty(mfWithValue.getUnits())) {
					        		   String unitVal = mapUnits.get(mfWithValue.getUnits());
					        		   if (!StringUtils.isEmpty(unitVal)) {
					        			   mfWithValue.setDecimals(unitVal);
					        		   }
					        	   }
					           }
					           if (currTable != null) {
						           mfWithValue.setIdMappingRef("Table_" + currTable.getTableId() + "_" + mfWithValue.getIdMappingRef());
						           mfWithValue.setTableId(currTable.getTableId());
						       }
					           controleDuplicateFact(mfWithValue, mapId, contexts, instCheat, valueMapping);
					       }
			       } 
		        }
		      }
        long endExecTime = System.currentTimeMillis();
        if (!vrfDeclFact) {
        	LogManagement.warn("Declarative fact not mapped for the table " + currTable.getTableId());
        }
        if (csvMap.size() != 0)	{
        	String logStr = "Correspondance in the UBP mapping not found for:";
        	for (String csvMapping : csvMap) {
        		logStr = logStr + " " + csvMapping;
        	}
        	LogManagement.warn(logStr);
        }
        LogManagement.info("Excel file values search completed in: " + String.valueOf(endExecTime - startExecTime) + " ms");
        
        if (!mapQNErrors.isEmpty()) {
        	LogManagement.warn("For several labels in Excel the correspondent QName was not found in the taxonomy.");
        	String errMessComplet = "";
        	for(Entry<String, ArrayList<String>> currEnt : mapQNErrors.entrySet()) {
                errMessComplet = errMessComplet + " For concept [" + currEnt.getKey() + "] following labels have no correspondent QName: [";
        		for (String qnm : currEnt.getValue()) {
        			errMessComplet = errMessComplet + qnm + ", ";
        		}
        		errMessComplet = errMessComplet.substring(0, errMessComplet.length()-2) + "]" + "\n";
        	}
        	LogManagement.warn(errMessComplet);
        }
        return resCnt;
    }
     /**
      * Finds in context files contexts corresponding to mapping with values and copies them to the new instance file.
      *
      * @param contextsFile - contexts file;
      * @param valueMapping - structure with values mapping;
      * @param contexts - collection for used contexts;
      * @param allContexts - collection for all contexts;
      * @param xbrlNodes - collection for root nodes;
      * @param schemaNodes - collection for schemaRef nodes;
      * @return true - if at least one context was found according to mapping.
      */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean getUsedContexts(final File contextsFile,
            TreeSet<MappedFact> valueMapping, ArrayList<Element> contexts,
            ArrayList<Element> allContexts, ArrayList<Element> xbrlNodes,
            ArrayList<Element> schemaNodes) {
    	LogManagement.debug("Find used contexts.");
    	long startExecTime = System.currentTimeMillis();
        boolean result = false;
        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
        	doc = builder.build(contextsFile);
            xbrlNodes.add(doc.detachRootElement());
            ArrayList<Element> tmpallContexts =
                    new ArrayList(xbrlNodes.get(0).getChildren());
            for (Element node : tmpallContexts) {
                if (node.getName().equals(
                        ConstanteXmlContext.CONTEXT)) {
                     allContexts.add(node);
                }
            }
            Iterator it = valueMapping.iterator();
            MappedFact mf;
            int i = 0;
            //getting nodes for schemaRef
            for (Element currNode: tmpallContexts) {
                if (currNode.getName().equals(ConstanteXmlContext.SCHEMANODE)) {
                    schemaNodes.add(currNode);
                }
            }
            //getting used contexts
            for (Element contextNode : allContexts) {
                it = valueMapping.iterator();
                if (contextNode.getName().equals(
                        ConstanteXmlContext.CONTEXT)) {
                    while (it.hasNext()) {
                        mf = (MappedFact) it.next();
                        if (contextNode.getAttributeValue(ConstanteXmlContext.CONTEXTID).equals(mf.getContextID())) {
                            if (!contexts.contains(contextNode)) {
                                contexts.add(contextNode);
                                i++;
                            }
                        }
                    }
                }
            }
            if (i > 0) {
                result = true;
            }
        } catch (JDOMException e) {
            LogManagement.error("getUsedContexts => Error in loading xml: " + e.getMessage());
        } catch (IOException e) {
            LogManagement.error("getUsedContexts => Error of file: " + e.getMessage());
        } catch (Error e) {
        	LogManagement.fatal("getUsedContexts => " + e.getMessage());
        }
        long endExecTime = System.currentTimeMillis();
        
        LogManagement.debug("Used contexts found in: " + String.valueOf(endExecTime - startExecTime) + " ms");
        return result;
    }
    /**
     * Function to write facts into the instance file.
     * 
     * @param valueMapping - value mapping structure
     * @param contexts - collection with contexts
     * @param xbrlNode - root node of the document
     * @param schemaLinkNodes - collection for schemaRef nodes
     * @param instanceFile - instance file with declared contexts
     * @param units - units measure
     * @throws IOException - input/output exception
     * @throws JDOMException - xml exception
     */
    public static void writeFactsAndContexts(HashMap<String, ArrayList<MappedFact>> valueMapping,
            HashMap<String, ArrayList<Element>> contexts, final Element xbrlNode,
            ArrayList<Element> schemaLinkNodes,
            final File instanceFile, final String units, final ArrayList<Element> unitNodes, 
            final String validOutput, final boolean vflag, final String suffix) throws IOException, JDOMException {
    	LogManagement.info("Write facts into the instance file.");
    	HashMap<String, ArrayList<MappedFact>> dplFacts = new HashMap<String, ArrayList<MappedFact>>();
    	HashMap<String, ArrayList<Element>> declFacts = new HashMap<String, ArrayList<Element>>();
    	ArrayList<String> usedUnits = new ArrayList<String>();
    	HashMap<String, String> usedNms = new HashMap<String, String>();
    	
    	long startExecTime = System.currentTimeMillis();
        xbrlNode.removeContent();

        Document outPutDoc;
        outPutDoc = new Document();
        Comment xmlOutComment = new Comment(ConstanteMessages.CREATEDBY);
        outPutDoc.addContent(xmlOutComment);
        outPutDoc.setRootElement(xbrlNode);
        //adding schema references
        int addedCount = 0;
        for (Element schema : schemaLinkNodes) {
            if (schema != null) {
            	xbrlNode.addContent((Element) schema.clone());
            	usedNms.putAll(ConstanteUtils.getElementNamespaces(schema));
                addedCount++;
            }
        }
        LogManagement.info(addedCount + " schema ref nodes written");
        //adding contexts
        addedCount = 0;
        for (Entry<String, ArrayList<Element>> contextList : contexts.entrySet()) {
	        for (Element currCtx : contextList.getValue()) {
	        	if (currCtx != null) {
	                xbrlNode.addContent((Element) currCtx.clone());
	                usedNms.putAll(ConstanteUtils.getElementNamespaces(currCtx));
	                addedCount++;
	            }
	        }
        }
        LogManagement.info(addedCount + " context nodes written");
        //adding units
        addedCount = 0;
        for (Element unit : unitNodes) {
            if (unit != null) {
                if (!units.equals("")) {
            	    replaceUnits(unit, units);
                }
            	xbrlNode.addContent((Element) unit.clone());
                addedCount++;
            }
        }
        LogManagement.info(addedCount + " units nodes written");
        //adding facts   
        addedCount = 0;
        Element tmpNode;
        ArrayList<MappedFact> currMFacts = new ArrayList<MappedFact>();
        for (Entry<String, ArrayList<MappedFact>> mappF : valueMapping.entrySet()) {
        	currMFacts.addAll(mappF.getValue());
        	if (mappF.getValue().size() > 1) {
        		dplFacts.put(mappF.getKey(), mappF.getValue());
        	}
        }
        //sort facts by mapping id
        FactComparator cmp = new FactComparator();
        Collections.sort(currMFacts, cmp);
        
        Element tmpXbrlNode = (Element) xbrlNode.clone();
    	tmpXbrlNode.removeContent();
    	
        for (MappedFact currFact :currMFacts) {
	         if (currFact != null) {
	            	tmpNode = currFact.toNode();
		            	if (tmpNode != null) {
		            		if (currFact.getParentFullName() != "") {
			            		ArrayList<Element> siblingFacts = new ArrayList<Element>();
			            		if (declFacts.containsKey(currFact.getParentFullName())) {
			            			siblingFacts = declFacts.get(currFact.getParentFullName());
			            			siblingFacts.add(tmpNode);
			            			declFacts.remove(currFact.getParentFullName());
			            			declFacts.put(currFact.getParentFullName(), siblingFacts);
			            		} else {
			            			siblingFacts.add(tmpNode);
			            			declFacts.put(currFact.getParentFullName(), siblingFacts);
			            		}
			            	} else {
			            		tmpXbrlNode.addContent(currFact.toNode());
			            		usedNms.putAll(ConstanteUtils.getElementNamespaces(currFact.toNode()));
			            		String cUnit = currFact.getUnits();
			            		if (!usedUnits.contains(cUnit)) {
			            			usedUnits.add(cUnit);
			            		}
			                    addedCount++;
			            	}
		            	} else {
		            		LogManagement.warn("Fact was not written because the namespace is missing.");
		            	}
	
	            }
            }

        if (!declFacts.isEmpty()) {
        	for (Entry<String, ArrayList<Element>> currEntry:declFacts.entrySet()) {
        		String currConceptFullName = currEntry.getKey();
        		ArrayList<Element> currFacts = currEntry.getValue();
        		String currUrl = currConceptFullName.split("#") [0];
        		Namespace currConceptNms = MappedFact.getCurrentNamespace(currUrl, xbrlNode);
        		if (currConceptNms != null) {
	        		Element declNode = writeDeclFact(currConceptFullName, currFacts, currConceptNms, addedCount);
	        		if (declNode != null) {
	        			xbrlNode.addContent(declNode);
	        			usedNms.putAll(ConstanteUtils.getElementNamespaces(declNode));
	        			addedCount++;
	        		}
        		} else {
        			LogManagement.warn("Declaratif concept: " + currConceptFullName + " is missing namespace declaration");
        		}
        	}
        }
        xbrlNode.addContent(tmpXbrlNode.cloneContent());
        LogManagement.info(addedCount + " facts written");
        //removing unused units
        usedNms.putAll(removeUnusedUnits(xbrlNode, usedUnits));
        //removing unused namespace declaration
        removeUnusedNms(xbrlNode, usedNms);
        File outputDir = new File(instanceFile.getParent());
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        if (!dplFacts.isEmpty()) {
        	if (vflag) {
	        	LogManagement.warn("Duplicate facts were generated during the instance creation");
	        	ExecXct.setDplFactsMap(dplFacts);
        	}
        }
        Format instFormat = Format.getPrettyFormat();
        instFormat.setEncoding("UTF-8");

        XMLOutputter output = new XMLOutputter(instFormat);
        Writer writer = new OutputStreamWriter(
                new FileOutputStream(instanceFile.getPath()),
                Charset.forName("UTF-8"));
        output.output(outPutDoc, writer);
        writer.flush();
        writer.close();    
        long endExecTime = System.currentTimeMillis();
        
        LogManagement.info("Instance file created in: " + String.valueOf(endExecTime - startExecTime) + " ms");
    }
    /**
     * Function to replace in contexts entity and period.
     * @param contexts - contexts node
     * @param entity - entity
     * @param startDate - start date
     * @param endDate - end date
     * @param entityScheme - scheme entity
     */
    public static void configureContexts(HashMap<String, ArrayList<Element>> contexts,
            final String entity, final String startDate, final String endDate, final String entityScheme) {
    	LogManagement.debug("Replace in contexts entity and period.");
    	long startExecTime = System.currentTimeMillis();
        for (Entry<String, ArrayList<Element>> context : contexts.entrySet()) {
            for (Element currContext : context.getValue()) {
	        	Element entityNode =
	        			currContext.getChild(ConstanteXmlContext.ENTITY,
	                    		currContext.getNamespace());
	            Element periodNode =
	            		currContext.getChild(ConstanteXmlContext.PERIOD,
	            				currContext.getNamespace());
	            Element identNode =
	                    entityNode.getChild(ConstanteXmlContext.IDENTIFIER,
	                    		currContext.getNamespace());
	
	            identNode.setAttribute(ConstanteXmlContext.SCHEMAREF,
	            		entityScheme);
	            identNode.setText(entity);
	            if (periodNode.getChild(
	                ConstanteXmlContext.INSTANTPERIOD,
	                currContext.getNamespace()) != null) {
	                Element instantNode = periodNode.getChild(
	                        ConstanteXmlContext.INSTANTPERIOD,
	                        currContext.getNamespace());
	                instantNode.setText(endDate);
	            } else {
	                if (periodNode.getChild(
	                    ConstanteXmlContext.STARTDATE,
	                    currContext.getNamespace()) != null) {
	                    Element startDateNode = periodNode.getChild(
	                            ConstanteXmlContext.STARTDATE,
	                            currContext.getNamespace());
	                    startDateNode.setText(startDate);
	                }
	                if (periodNode.getChild(
	                    ConstanteXmlContext.ENDDATE,
	                    currContext.getNamespace()) != null) {
	                    Element endDateNode = periodNode.getChild(
	                                          ConstanteXmlContext.ENDDATE,
	                                          currContext.getNamespace());
	                   endDateNode.setText(endDate);
	                }
	            }
            }
        }
        long endExecTime = System.currentTimeMillis();
        
        LogManagement.debug("Contexts configured with entity and period dates in: " + String.valueOf(endExecTime - startExecTime) + " ms");
    }
    /**
     * Creates mapping file with mapping only for facts with values.
     * @param mappingFile - initial mapping file
     * @param valueMapping - collection of the mapped facts with values
     * @param restrMappingName - restricted mapping file name
     * @return true - if successfully created file.
     * @throws IOException - exception from file system
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean createRestrictedMapping(final File mappingFile,
            final TreeSet<MappedFact> valueMapping, 
            final String restrMappingName) throws IOException {
    	LogManagement.info("Creates mapping file with mapping only for facts with values.");
    	
    	long endExecTime = System.currentTimeMillis();
        boolean result = false;
        //copying content from mapping to new file       
        ConstanteUtils.copyFiles(mappingFile.getPath(), restrMappingName);

        SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            boolean exist = false;
            doc = builder.build(new File(restrMappingName));
            Element rootNode = doc.getRootElement();
            Element cellsNode = rootNode.getChild("cells");
            List<Element> mappedCells
            = cellsNode.getChildren(ConstanteXmlMapping.MAPPEDCELLS);
            Iterator it;
            MappedFact currMf;
            //int size = mappedCells.size();
            for (int i = 0; i < mappedCells.size(); i++) {
                Element mappedCell;
                mappedCell = mappedCells.get(i);
                exist = false;
                it = valueMapping.iterator();
                while (it.hasNext()) {
                    currMf = (MappedFact) it.next();
                    if (currMf != null) {
                        if ((mappedCell.getChildText(
                        		ConstanteXmlMapping.SHEETNAME)
                             .equals(currMf.getSheetName()))
                             && ((mappedCell.getChildText(
                                     ConstanteXmlMapping.RANGEREF)
                                     .equals(currMf.getRangeRef())))) {
                            exist = true;                        
                        }
                    } else {
                        exist = true;
                    }
                }
                if (!exist) {
                    cellsNode.removeContent(mappedCell);
                }
            }
            
            //writing results            
            Format instFormat = Format.getPrettyFormat();
            instFormat.setEncoding("UTF-8");

            XMLOutputter output = new XMLOutputter(instFormat);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(restrMappingName),
                    Charset.forName("UTF-8")));
            output.output(doc, writer);
            writer.flush();
            writer.close();
            result = true;

        } catch (Exception e) {
            LogManagement.error("createRestrictedMapping => " + e.getMessage() + e.getStackTrace());
            result = false;
        } catch (Error e) {
        	LogManagement.fatal("createRestrictedMapping => " + e.getMessage() + e.getStackTrace());
            result = false;
        }
        long startExecTime = System.currentTimeMillis();
        
        LogManagement.info("Restricted mapping file created in: " + String.valueOf(endExecTime - startExecTime) + " ms");
        return result;
    }
    /**
     * Find xbrl schema node in contexts file.
     * @param contextFile -contexts file.
     * @return node Xbrli
     */
    public static Element getXbrlNode(final File contextFile) {
    	LogManagement.debug("Find xbrl node in context file.");
    	
    	Element currXbrlNode = null;
    	SAXBuilder builder = new SAXBuilder();
        Document doc;
        try {
            doc = builder.build(contextFile);
            currXbrlNode = doc.detachRootElement();
        } catch (Exception e) {
        	LogManagement.error("getXbrlNode => Failed to load context file");
        } catch (Error e) {
        	LogManagement.fatal("getXbrlNode => Failed to load context file");
        }
    	return currXbrlNode;
    }
    /**
     * Function verifies that tab is to be converted.
     * @param sheetName - name of the current tab.
     * @param tabsCollection - collection of tabs with conversion flag.
     * @param tabsMode - inclusion or exclusion for tabs conversion.
     * @return true - if tab is in collection
     *                and is to be converted in this period
     */
    public static boolean isToConvert(
       final String sheetName, final HashMap<String, String> tabsCollection, final String tabsMode) {
    	LogManagement.debug("Verify wether tab is to be converted.");
    	
    	boolean result = false;
    	if (tabsMode.equals(Constante.INC)) {
	    	if (tabsCollection.containsKey(sheetName)) {
	    		result = true;
	    	} else {    	 
	    	    result = false;
	    	}
    	} else {
    		if (!tabsCollection.containsKey(sheetName)) {
	    		result = true;
	    	} else {    	 
	    	    result = false;
	    	}
    	}
    	return result;
    }
   
    /**
     * Replace value of units in node.
     * @param defautUnits - units entered by user
     * @param unitNode - unit node
     */
    @SuppressWarnings("unchecked")
	public static void replaceUnits(Element unitNode, final String defautUnits) {
    	
    	try {
	    	Element mesureNode = null;
	    	List<Element> children = unitNode.getChildren();
	    	for (Element childNode : children) {
	    		if (childNode != null) {
	    			if (childNode.getName().equals(Constante.MESURE)) {
	    				mesureNode = childNode;
	    				break;
	    			}
	    		}
	    	}
            String measureText = mesureNode.getText();
            if (measureText.startsWith(Constante.ISO)) {
            	mesureNode.setText(Constante.ISO + ":" + defautUnits);
            }
    	} catch (Exception e) {
    		LogManagement.debug("replaceUnits => No measure node for unit node");
    	} catch (Error e) {
    		LogManagement.debug("replaceUnits => No measure node for unit node");
    	}
    }
    /**
     * Adds contest element definition to the mapped fact. Verify whether the context is not duplicate.
     * @param mfWithValue - mapped fact with value
     * @param mapId - map with elements unique IDs.
     * @param contexts - map with contexts collection.
     * @param instCheat - cheat instance object.
     * @param valueMapping - map with values for facts.
     * @return true - if the fact is not duplicate.
     */
	public static boolean controleDuplicateFact(MappedFact mfWithValue,
    		                 HashMap<String, Object> mapId, HashMap<String, ArrayList<Element>>contexts, XbrlInstance instCheat,
    		                 HashMap<String, ArrayList<MappedFact>> valueMapping) {

		Element elContextContext;
		String idContext = "";
		String idConcept = "";
		String idFact = "";
		boolean vrf = true;
		
    	try {
			 if (mapContexts.containsKey(mfWithValue.getContextID())) {	
				 elContextContext = mapContexts.get(mfWithValue.getContextID());
				 XbrlContext ctxFact = new XbrlContext(elContextContext);
				 mfWithValue.setContextElem(elContextContext);
				 idContext = instCheat.createSuperUniqueIDContextXct(elContextContext);
				 if (!contexts.containsKey(idContext)) {
					 ArrayList<Element> ctxs = new ArrayList<Element>();
					 ctxs.add(elContextContext);
					 contexts.put(idContext, ctxs);
				 } else {
					 ArrayList<Element> ctxs = contexts.get(idContext);
					 boolean pres = false;
					 for (Element ctx : ctxs) {
						  XbrlContext ctxOld = new XbrlContext(ctx);
						  String ctxOldName = ctx.getAttributeValue("id");
						  if (ctxOldName.equals(elContextContext.getAttributeValue("id"))) {
							  pres = true;
						  } else {
							  if (ctxFact.equalTo(ctxOld)) {
								  pres = true;
								  mfWithValue.setContextID(ctxOldName);
							  }
						  }
							  
					 }
					 if (!pres) {
						ctxs.add(elContextContext);
						contexts.put(idContext, ctxs);
					 }
				  }
				  idFact = idContext;
				  ArrayList<MappedFact> mfs = new ArrayList<MappedFact>();
				  //create unique ID for concept
				  idConcept = instCheat.createSuperIdConcept(mfWithValue.getConceptName(), mfWithValue.getNmnSpace());
				  if (!mapId.containsKey(idConcept)) {
					  Object[] it = {mfWithValue.getNmnSpace(), mfWithValue.getConceptName()};
					  mapId.put(idConcept,  it);
				  }
				  idFact = idContext + idConcept;
				  if (valueMapping.containsKey(idFact)) {
					  if (!mfWithValue.getParentFullName().equals("")) {
						  idFact = idFact +  instCheat.setElementNameId(mfWithValue.getValue());
						  mfs.add(mfWithValue);
						  valueMapping.put(idFact, mfs);
					  } else {
						  for (MappedFact currF : valueMapping.get(idFact)) {
							   if (currF.equalsByDecimals(mfWithValue) == 2) {
								   LogManagement.debug("Duplicate facts found for declarative submission for concept "
											            + mfWithValue.getNmnSpace().getURI() + "#" + mfWithValue.getConceptName()
											            + " in context " + mfWithValue.getContextID());
								   vrf = false;
							   }
						  }
						  if (!vrf) {
							  mfs = valueMapping.get(idFact);
							  mfs.add(mfWithValue);
							  valueMapping.put(idFact, mfs);
						  }
					  }
					} else {
						mfs.add(mfWithValue);
						valueMapping.put(idFact, mfs);
					}
				} else {
				LogManagement.warn("Impossible to convert value in cell " + mfWithValue.getSheetName() 
						+ "!" + mfWithValue.getRangeRef() + ". Context " + mfWithValue.getContextID() + " not found in contexts file.");
			    }
    	} catch (Exception e) {
    		LogManagement.error("controleDuplicateFact = > " + e.getMessage());
    	}
		return vrf;
    	
    }
    /**
     * Creates a duplicate facts report.
     * @param dplFcts - duplicate facts report.
     * @param repFilePath - report file path.
     * @param instPath - instance file path.
     */
   public static void createDuplicateFactsReport(final HashMap<String, ArrayList<MappedFact>> dplFcts, final String repFilePath, final String instPath) {
	    LogManagement.info("Creating duplicate facts report.");
	    Element root = null;
		Document trace = null;
		SimpleDateFormat formatdate = new SimpleDateFormat("d MMM yyyy HH:mm:ss");

		try {
			trace = new Document();
		    Comment xmlOutComment = new Comment("Created with " + Constante.NOMAPPLICATION + " " + Constante.VERSIONAPPLICATION);
		    trace.addContent(xmlOutComment);
		    trace.setRootElement(new Element("Result"));
		    root = trace.getRootElement();
		    Element dateNode = new Element("reportDate");
		    dateNode.setText(formatdate.format(new Date()));
		    root.addContent(dateNode);
		    Element docNode = new Element("raport");
		    docNode.setText("file:/" + instPath.replace("\\", "/"));
		    root.addContent(docNode);
		    	    
		    Format restitFormat = Format.getPrettyFormat();
			restitFormat.setEncoding("UTF-8");
	        
			for (Entry<String, ArrayList<MappedFact>> entry : dplFcts.entrySet()) {
				Element dplfactNode = new Element("DuplicateFact");
				for (MappedFact currF : entry.getValue()) {
					try {
						Element iterNode = new Element("iteration");
						iterNode.setAttribute("concept", currF.getNmnSpace().getURI() + "#" + currF.getConceptName());
						iterNode.setAttribute("context", currF.getContextID());
						iterNode.setAttribute("mapID", currF.getIdMappingRef());
						iterNode.setAttribute("sheet", currF.getSheetName());
						iterNode.setAttribute("rangeRef", currF.getRangeRef());
						iterNode.setAttribute("value", currF.getValue());
						dplfactNode.addContent(iterNode);
					} catch (Exception e) {
						LogManagement.debug("No tab and range reference for the fact");
					}
				}
				root.addContent(dplfactNode);
			}
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
   /**
    * Add a declarative fact to parent node.
    * @param declConcept - declarative concept name
    * @param declFacts - declarative facts array
    * @param currNms - namespace for parent node
    * @param addCnt - added facts counter
    * @return declParentNode - return declarative node
    */
   private static Element writeDeclFact(final String declConcept, final ArrayList<Element> declFacts, final Namespace currNms, int addCnt) {
		
	   String currConcept = declConcept.split("#") [1];
	   Element declParentNode = new Element(currConcept, currNms);
	   for (Element declFact:declFacts) {
		   try {
			   declParentNode.addContent(declFact);
			   addCnt++;
		   } catch (Exception e) {
			   LogManagement.warn("Failed to add declarative fact");
		   }
	   }
	   return declParentNode; 
   }
   /**
    * Initialize the contexts id map.
    * @param contextsDoc - contexts document.
    */
   @SuppressWarnings("unchecked")
   private static void intializeContextsMap(final Document contextsDoc) {  
	    String contextIdContext;
	    Element rootContextTmp = (Element) contextsDoc.getRootElement().clone();
		Namespace xbrli = Namespace
				.getNamespace("http://www.xbrl.org/2003/instance");
		Filter fContext = new ElementFilter("context", xbrli);
		Iterator<Element> itContextContexttest = rootContextTmp.getDescendants(fContext);
		mapContexts.clear();
		while (itContextContexttest.hasNext()) {
			Element elContextContext = (Element) itContextContexttest.next();
			contextIdContext = elContextContext.getAttributeValue("id");
			mapContexts.put(contextIdContext, elContextContext);
		}
		List<Namespace> addNms = rootContextTmp.getAdditionalNamespaces();
		for (Namespace addNm : addNms) {
			if (!mapNms.containsKey(addNm.getURI())) {
				mapNms.put(addNm.getURI(), addNm.getPrefix());
			}
		}
   }
   /**
    * Adds schema references nodes from context document to instance.
    * Replace the references with expected schema references.
    * @param xbrlNodes
    * @return
    */
   @SuppressWarnings("unchecked")
   public final static ArrayList<Element> configureSchemaRef (final ArrayList<Element> xbrlNodes, final HashMap<String, String> xsdRefs) {
	   ArrayList<Element> schemaLinkNodes = new ArrayList<Element>();
	   Namespace nsLink = Namespace.getNamespace("http://www.xbrl.org/2003/linkbase");
	   Namespace xLink = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
	   if (xbrlNodes.get(0)==null) {
		   LogManagement.error("XBRL root node is empty");
		   return null;
	   }
	   try {
		   Iterator<Element> itElRoot = xbrlNodes.get(0).getDescendants(new ElementFilter("schemaRef", nsLink));
		   while (itElRoot.hasNext()) {
					Element element = itElRoot.next();
					String currXsd = element.getAttributeValue("href", xLink);
					String xsdName = "";
					if (currXsd.startsWith("http")) {
						xsdName = currXsd.split("//") [currXsd.split("//").length - 1];
					} else {
						xsdName = new File(currXsd).getName();
					}
					if (xsdRefs.containsKey(xsdName)) {
						String expRef = xsdRefs.get(xsdName);
						if (expRef != null) {
							if (!expRef.equals("")) {
						        element.setAttribute("href", expRef, xLink);
							}
						}
					}
					schemaLinkNodes.add(element); 
		  }
	   } catch (Exception e) {
		   LogManagement.error("Failed to configure schema node: " + e.getMessage());
	   }
	   return schemaLinkNodes;
   }
   /**
    * Replace the label with QName in instance.
    * @param cellValue - cell value from the Excel.
    * @param conceptName - the mapped fact concept name.
    * @param nms - the mapped fact concept namespace.
    * @return cell value or the correspondent QName if it exists.
    */
   public final static String replaceQName(final String cellValue, final String conceptName, final Namespace nms) {
	   boolean isQName = false;
	   HashMap<String, HashMap<String, HashMap<String, String>>> mapQnames = ExecXct.getMapLabelsQName();
	   if (mapQnames.containsKey(nms.getURI() + "#" + conceptName)) {
		   isQName = true;
		   HashMap<String, HashMap<String, String>> mapLang = mapQnames.get(nms.getURI() + "#" + conceptName);
		   if (mapLang.containsKey(ExecXct.getRepLang().toLowerCase())) {
			   for (Entry<String, String> currEnt : mapLang.get(ExecXct.getRepLang().toLowerCase()).entrySet()) {
				   if (currEnt.getKey().equals(cellValue)) {
					   String qnameNms = currEnt.getValue().split("#") [0];
					   String qnameNm = currEnt.getValue().split("#") [1];
					   String prefix = mapNms.get(qnameNms);
					   if (!StringUtils.isEmpty(prefix)) {
					        String qnameValue = prefix + ":" + qnameNm;
					        return qnameValue;
					   } else {
						   LogManagement.warn("Namespace for the QName: " + qnameNms + "#" + qnameNm + " is not declared. Verify context configuration");   
					   }
				   } else if(cellValue.contains(":")) {
					   if(cellValue.split(":").length > 1) {
						   String prefix = cellValue.split(":") [0];
						   String value = cellValue.split(":")[1];
						   String cUri = "";
							   if(mapNms.containsValue(prefix)) {
								   for (Entry<String, String> cNms : mapNms.entrySet()) {
									   if (cNms.getValue().equals(prefix)) {
										   cUri = cNms.getKey();
										   break;
									   }
								   }
								   if (currEnt.getValue().equals(cUri + "#" + value)) {
							           return cellValue;
								   }
							   }
					   }
				   }
			   }
		   }
	   }
	   if (isQName) {
			  if (mapQNErrors.containsKey(nms.getURI() + "#" + conceptName)) {
				  ArrayList<String> valList = mapQNErrors.get(nms.getURI() + "#" + conceptName);
				  if (!valList.contains(cellValue)) {
					  valList.add(cellValue);
				  }
				  mapQNErrors.remove(nms.getURI() + "#" + conceptName);
				  mapQNErrors.put(nms.getURI() + "#" + conceptName, valList);
			  } else {
				  ArrayList<String> valList = new ArrayList<String>();
				  valList.add(cellValue);
				  mapQNErrors.put(nms.getURI() + "#" + conceptName, valList);
			  }
		   }
	   return cellValue;
   }
   /**
    * Creates map of units for current table/file.
    * @param unitnodes - list of unit elements from contexts file.
    */
   @SuppressWarnings("unchecked")
public static void reinitUnits(final ArrayList<Element> unitnodes, final ConfiguratedTaxonomy cTaxo, final DeclaratifTable cTbl) {
	   
	   HashMap<String, String> taxoUnits = new HashMap<String, String>();
	   String taxoMonetary = "";
	   String taxoPure = "";
	   if (cTbl != null) {
		   if (!cTbl.getUnits().isEmpty()) {
			   taxoUnits = cTbl.getUnits();
			   taxoMonetary = taxoUnits.get("monetary");
			   taxoPure = taxoUnits.get("pure");
		   } else if (!cTaxo.getUnits().isEmpty()) {
			   taxoUnits = cTaxo.getUnits();
			   taxoMonetary = taxoUnits.get("monetary");
			   taxoPure = taxoUnits.get("pure");
		   } else {
			   return;
		   }
	   } else {
		   if (!cTaxo.getUnits().isEmpty()) {
			   taxoUnits = cTaxo.getUnits();
			   taxoMonetary = taxoUnits.get("monetary");
			   taxoPure = taxoUnits.get("pure");
		   } else {
			   return;
		   }
	   }
	   for (Element unitNode: unitnodes) {
		   try {
		    	Element mesureNode = null;
		    	List<Element> children = unitNode.getChildren();
		    	for (Element childNode : children) {
		    		if (childNode != null) {
		    			if (childNode.getName().equals(Constante.MESURE)) {
		    				mesureNode = childNode;
		    				break;
		    			}
		    		}
		    	}
	            String measureText = mesureNode.getText();
	            if (measureText.startsWith(Constante.ISO)) {
	            	if (!taxoMonetary.equals("")) {
	            	    mapUnits.put(unitNode.getAttributeValue("id"), taxoMonetary);
	            	}
	            } else if (measureText.equals("pure") || measureText.equals("xbrli:pure")) {
	            	if (!taxoMonetary.equals("")) {
	            	    mapUnits.put(unitNode.getAttributeValue("id"), taxoPure);
	            	}
	            }
	    	} catch (Exception e) {
	    		LogManagement.debug("reinitUnits => No measure node for unit node");
	    	} catch (Error e) {
	    		LogManagement.debug("reinitUnits => No measure node for unit node");
	    	}
	   }
   }
   /**
    * Removes unused namespace declaration from instance.
    * @param xbrlNode - complete instance node.
    */
   @SuppressWarnings("unchecked")
   private static void removeUnusedNms(Element xbrlNode, final HashMap<String, String> usedNms) {
	   LogManagement.info("Removing unused namespaces");
	   int cnt = 0;
	   List<Namespace> aNms = xbrlNode.getAdditionalNamespaces();
	   ArrayList<Namespace> unusedNms = new ArrayList<Namespace>();
	   //find unused namespaces
	   for (Namespace currNms : aNms) {
		   if (!currNms.getURI().equals("http://www.w3.org/2001/XMLSchema-instance")) {
			   String prefix = currNms.getPrefix();
			   if (!usedNms.containsKey(prefix)) {
				   unusedNms.add(currNms);
			   }
		   }
	   }
	   //delete unused namespaces
	   if(!unusedNms.isEmpty()){
		   for (Namespace nms: unusedNms) {
			   cnt ++;
			   xbrlNode.removeNamespaceDeclaration(nms);
		   }
	   }
	   if (cnt > 0) {
		   LogManagement.info(cnt + " unused namespace declarations removed.");
	   } else {
		   LogManagement.info("No unused namespace declarations found.");
	   }   
   }
   /**
    * 
    * @param xbrlNode
    * @param usedNms
    */
   @SuppressWarnings("unchecked")
  private static HashMap<String, String> removeUnusedUnits(Element xbrlNode, final ArrayList<String> usedUnits) {
	   LogManagement.info("Removing unused units");
	   HashMap<String, String> usedNms = new HashMap<String, String>();
	   ArrayList<Element> unusedUnits = new ArrayList<Element>();
	   int cnt = 0;
	   Filter fUnit = new ElementFilter("unit");
	   Iterator<Element> itUnit = xbrlNode.getDescendants(fUnit);
	   while(itUnit.hasNext()) {
		   Element cUnit = itUnit.next();
		   String unt = cUnit.getAttributeValue("id");
		   if(!usedUnits.contains(unt)) {
			   unusedUnits.add(cUnit);
			  cnt++;
		   } else {
			   usedNms.putAll(ConstanteUtils.getElementNamespaces(cUnit));
		   }
	   }
	   /*Removing units*/
	   for (Element currUnit: unusedUnits) {
		   xbrlNode.removeContent(currUnit);
	   }
	   if (cnt > 0) {
		   LogManagement.info(cnt + " unused units removed.");
	   } else {
		   LogManagement.info("No unused units found.");
	   }
	   return usedNms;
   }
}

