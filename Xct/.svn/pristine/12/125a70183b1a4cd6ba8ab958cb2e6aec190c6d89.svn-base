package ubpartner.xct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Element;
import org.jdom.Namespace;

import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.xct.commun.ConstanteXmlMapping;

/**
 * @author UBPartner
 * Class for one mapped fact that contains information about:
 * sheet name for mapping
 * cell reference for mapping
 * concept name
 * context ID
 * units
 * decimals
 * scale
 * value
 */
@SuppressWarnings("rawtypes")
public class MappedFact implements Comparable {
	/**
	 * Excel file name.
	 */
	private String excelFile;
	/**
     * Tab name in Excel.
     */
    private String sheetName;
    /**
     * Cell address.
     */
    private String rangeRef;
    /**
     * Reference to mapping in the form of string.
     */
    private String idMappingRef;
    /**
     * Concept name.
     */    
    private String conceptName;
    /**
     * Context ID.
     */
    private String contextID;
    /**
     * Concept units.
     */
    private String units ;
    /**
     * Decimals.
     */
    private String decimals = "";
    /**
     * Scale.
     */
    private String scale;
    /**
     * Value in Excel file that corresponds to the current concept.
     */
    private String value;
    /**
     * Value type (string, date, int).
     */
    private String valueType;
    /**
     * Namespace prefix.
     */
    private Namespace nmsSpace;
    /**
     * Context element associated with context ID.
     */
    private Element contextElem;
    /**
	 * Parent concept + url for declarative facts.
	 */
	private String parentFullName = "";
	/**
	 * Parent concept for declarative facts.
	 */
	private String parentConcept = "";
	/**
	 * Parent concept for declarative facts.
	 */
	private String parentUrl = "";
	/**
	 * Parent concept for declarative facts.
	 */
	private String tableId = "";
	/**
	 * @return the tableId
	 */
	public final String getTableId() {
		return tableId;
	}
	/**
	 * @param ctableId the tableId to set
	 */
	public final void setTableId(final String ctableId) {
		this.tableId = ctableId;
	}
	/**
	 * GUID Id de mapping cell.
	 */
	private String guid = "";
    /**
     * Constructor.
     * @param mappedCell - mapping file element
     * @param xbrlNode - xbrli node of the contexts file.
     * @param parents - map with parent concepts and cells guids.
     */
    public MappedFact(final Element mappedCell, final Element xbrlNode, final HashMap<String, ArrayList<String>> parents) {
    	LogManagement.debug("Map Fact creation.");
        try {
        	this.guid = mappedCell.getChildText("GUID");
        	if (!parents.isEmpty()) {
	        	if (!this.guid.equals("")) {
	        		for (Entry<String, ArrayList<String>> current : parents.entrySet()) {
	        			String currconc = current.getKey();
	        			ArrayList<String> currGuids = current.getValue();
	        			if (ConstanteUtils.isInArray(this.guid, currGuids)) {
	        				this.parentFullName = currconc;
	        				this.parentConcept = currconc.split("#") [1];
	        				this.parentUrl = currconc.split("#") [0];
	        			}
	        		}
	        	}
        	}
	    	this.conceptName = mappedCell.getChildText(
	                ConstanteXmlMapping.CONCEPTNAME).substring(
	                mappedCell.getChildText(
	                ConstanteXmlMapping.CONCEPTNAME).lastIndexOf("#") + 1);
	        this.contextID = mappedCell.getChildText(ConstanteXmlMapping.CONTEXTID);
	        this.sheetName = mappedCell.getChildText(ConstanteXmlMapping.SHEETNAME);
	        this.rangeRef = mappedCell.getChildText(ConstanteXmlMapping.RANGEREF);
	        this.idMappingRef = "Sheet_" + this.sheetName.replaceAll("\\W+", "_")
		            + "_Cell_" + this.rangeRef;
	        this.units = mappedCell.getChildText(ConstanteXmlMapping.UNITS);
	        this.decimals = mappedCell.getChildText(ConstanteXmlMapping.DECIMALS);
	        this.scale = mappedCell.getChildText(ConstanteXmlMapping.SCALE);
	        this.nmsSpace = getCurrentNamespace(mappedCell.getChildText(
	                ConstanteXmlMapping.CONCEPTNAME).substring(0,
	                mappedCell.getChildText(
	                ConstanteXmlMapping.CONCEPTNAME).lastIndexOf("#")), xbrlNode);
	        try {
	           this.value = mappedCell.getChildText("Value");
	        } catch (Exception e) {
	           this.value = "";
	        }
	        this.valueType = "";
        } catch (Exception e) {
        	LogManagement.debug("MappedFact => Mapped fact xml structure contains errors");
        } catch (Error e) {
        	LogManagement.fatal("MappedFact => Mapped fact xml structure contains errors");
        }
        
    }
    /**
	 * @return the parentUrl
	 */
	public final String getParentUrl() {
		return parentUrl;
	}
	/**
     * Getter for the sheet name.
     * @return String sheetName
     */
    public final String getSheetName() {
        return sheetName;
    }
    /**
     * Setter for the sheet name.
     * @param cSheetName - sheet name
     */
    public final void setSheetName(final String cSheetName) {
        this.sheetName = cSheetName;
    }
    /**
     * getter for the range reference property.
     * @return String
     */
    public final String getRangeRef() {
        return rangeRef;
    }
    /**
     * Setter for the range reference property.
     * @param cRangeRef - range reference.
     */
    public final void setRangeRef(final String cRangeRef) {
        this.rangeRef = cRangeRef;
    }
    /**
     * getter for the concept name.
     * @return conceptName
     */
    public final String getConceptName() {
        return conceptName;
    }
    /**
     * Setter for the concept name.
     * @param cConceptName - concept name.
     */
    public final void setConceptName(final String cConceptName) {
        this.conceptName = cConceptName;
    }
    /**
     * getter for context ID property.
     * @return String
     */
    public final String getContextID() {
        return contextID;
    }
    /**
     * Setter for context ID property.
     * @param cContextID - context ID.
     */
    public final void setContextID(final String cContextID) {
        this.contextID = cContextID;
    }
    /**
     * getter for units property.
     * @return String
     */
    public final String getUnits() {
        return units;
    }
    /**
     * Setter for units property.
     * @param cUnits - units.
     */
    public final void setUnits(final String cUnits) {
        this.units = cUnits;
    }
    /**
     * Getter for decimals.
     * @return String
     */
    public final String getDecimals() {
        return decimals;
    }
    /**
     * Setter for decimals.
     * @param cDecimals - decimals.
     */
    public final void setDecimals(final String cDecimals) {
        this.decimals = cDecimals;
    }
    /**
     * getter for the scale property.
     * @return String
     */
    public final String getScale() {
        return scale;
    }
    /**
     * Setter for scale.
     * @param cScale - scale.
     */
    public final void setScale(final String cScale) {
        this.scale = cScale;
    }
    /**
     * getter for the value property.
     * @return String
     */
    public final String getValue() {
        return value;
    }
    /**
     * Setter for value.
     * @param cValue - fact value.
     */
    public final void setValue(final String cValue) {
        this.value = cValue;
    }
    /**
     * getter for the value type property.
     * @return String
     */
    public final String getValueType() {
        return valueType;
    }
    /**
     * setter for the value type property.
     * @param cValueType - value type.
     */
    public final void setValueType(final String cValueType) {
        this.valueType = cValueType;
    }

    /**
     * getter for the namespace property.
     * @return nameSpace
     */
    public final Namespace getNmnSpace() {
        return nmsSpace;
    }
    /**
	 * @return the contextElem
	 */
	public final Element getContextElem() {
		return contextElem;
	}
	/**
	 * @param cContextElem the contextElem to set
	 */
	public final void setContextElem(final Element cContextElem) {
		this.contextElem = cContextElem;
	}
	/**
	 * Getter for the mapping ID reference.
	 * @return the idMappingRef
	 */
	public final String getIdMappingRef() {
		return idMappingRef;
	}
	/**
	 * Setter for the mapping ID reference.
	 * @param cIdMappingRef the idMappingRef to set
	 */
	public final void setIdMappingRef(final String cIdMappingRef) {
		this.idMappingRef = cIdMappingRef;
	}
	/**
	 * @return the excelFile
	 */
	public final String getExcelFile() {
		return excelFile;
	}
	/**
	 * @param excelFile the excelFile to set
	 */
	public final void setExcelFile(String excelFile) {
		this.excelFile = excelFile;
	}
	/**
	 * @return the parent element name and namespace.
	 */
	public final String getParentFullName() {
		return parentFullName;
	}
	/**
	 * @return the parent element name and namespace.
	 */
	public final String getParentConcept() {
		return parentConcept;
	}
	/**
     * Sorting rules for fact collection.
     * By sheet name, then by range reference.
     * 
     * @param compObj - current collection object.
     * @return -1 - object is inferior to the compared object,
     *          1 - it is superior
     *          0 - the objects are equal.
     */
     public final int compareTo(final Object compObj) {
        MappedFact acomparer = (MappedFact) compObj;
        if (this.sheetName.compareTo(acomparer.getSheetName()) < 0) {
            return -1;
        } else if (this.sheetName.compareTo(acomparer.getSheetName()) > 0) {
            return 1;
        } else if (this.rangeRef.compareTo(acomparer.getRangeRef()) < 0) {
            return -1;
        } else {
            return 1;
        }
    }
    /**
     * Defines the way mapped fact is presented in instance file.
     * @return - node Element representing the current MappedFact
     */
    public final Element toNode() {
    	LogManagement.debug("Defines the way mapped fact is presented in instance file.");
        try {
	        Element resNode;
	        if (nmsSpace != null) {
	        	resNode = new Element(this.conceptName, nmsSpace);
	        	resNode.setAttribute("id", this.idMappingRef);
	            resNode.setAttribute("contextRef", this.contextID);
	
		        if (!StringUtils.isEmpty(this.decimals)) {
		            resNode.setAttribute("decimals", this.decimals);
		        }
		        if (!StringUtils.isEmpty(this.units)) {
		            resNode.setAttribute("unitRef", this.units);
		        }
		        resNode.setText(this.value);
		        return resNode;
	        } else {
	        	return null;
	        }
        } catch (Exception e) {
        	LogManagement.error("toNode => Error transforming mapped fact object to node");
        	return null;
        } catch (Error e) {
        	LogManagement.fatal("toNode => Error transforming mapped fact object to node");
        	return null;
        }
    }
    /**
     * Function to get the namespace prefix.
     * According to the whole namespace declaration.
     * @param nmsComplete - whole name of namespace
     * @param xbrlNode - node with namespace declaration from the context file
     * @return namespace prefix
     */
    @SuppressWarnings("unchecked")
	public static Namespace getCurrentNamespace(
			final String nmsComplete, final Element xbrlNode) {
    	LogManagement.debug("Get the namespace prefix.");
    	
    	try {
	    	Namespace currNms = null;
	    	
	    	ArrayList<Namespace> nmsList =
	    			new ArrayList<Namespace>(xbrlNode.getAdditionalNamespaces());
	    	for (Namespace currNamespace : nmsList) {
	    		if (currNamespace.getURI().equals(nmsComplete)) {
	    			currNms = currNamespace;
	    		}
	    	}
	    	if (currNms == null) {
	    		LogManagement.warn("Mapping and context file not corresponding. \n Missing namespace declaration for URL: " + nmsComplete);
	    		currNms = null;    		
	    	}
	    	return currNms;
    	} catch (Exception e) {
    		LogManagement.error("getCurrentNamespace => Error getting namespace for the mapped fact");
        	return null;
    	} catch (Error e) {
    		LogManagement.fatal("getCurrentNamespace => Error getting namespace for the mapped fact");
        	return null;
    	}
    }
    /**
	 * Compares current fact to another.
	 * @param fact - fact to compare.
	 * @return 0 - facts are different
	 *         11 - facts have same concept-context and equal value (current fact is more precise)
	 *         12 - facts have same concept-context and equal value (compared fact is more precise)
	 *         2 - facts have same concept-context and different values
	 */
	public final int equalsByDecimals(final MappedFact fact) {
		
		if (!this.contextID.equals(fact.getContextID())) {
			return 0;
		} else if (!(this.nmsSpace.getURI() + "#" + this.conceptName).equals(fact.getNmnSpace().getURI() + "#" + fact.getConceptName())) {
			return 0;
		} else {
			//verify that both facts have decimals
			if (!StringUtils.isEmpty(this.decimals) && !StringUtils.isEmpty(this.decimals)) {
				int dec = Integer.parseInt(this.decimals);
				int decFact = Integer.parseInt(fact.getDecimals());

					try {
						int scale = 0;
						BigDecimal currVal = new BigDecimal(this.value);
						BigDecimal factVal = new BigDecimal(fact.getValue());
						if (dec < decFact) {
							scale = dec;
						} else {
							scale = decFact;
						}
					    currVal = currVal.setScale(scale, RoundingMode.DOWN);
					    factVal = factVal.setScale(scale, RoundingMode.DOWN);
					    if (currVal.equals(factVal)) {
					    	if (dec > decFact) {
					    		return 11;
					    	} else if (decFact > dec) {
					    		return 12;
					    	} else {
					    		if (this.value.length() > fact.getValue().length()) {
					    			return 11;
					    		} else if (this.value.length() < fact.getValue().length()) {
					    			return 12;
					    		} else {
					    			return 11;
					    		}
					    	}
					    } else {
					    	return 2;
					    }
					} catch (Exception e) {
						return 2;
					}
			} else if (StringUtils.isEmpty(this.decimals) && StringUtils.isEmpty(this.decimals)) {
				if (this.value.equals(fact.getValue())) {
					return 11;
				} else {
					return 2;
				}
			} else {
				LogManagement.warn("One of the facts with equal concept-context: " + this.contextID
						+ " " + this.nmsSpace.getURI() + "#" + this.conceptName + " has no decimals defined.");
				if (this.value.equals(fact.getValue())) {
					return 11;
				} else {
					return 2;
				}
			}
		}
	}
}
