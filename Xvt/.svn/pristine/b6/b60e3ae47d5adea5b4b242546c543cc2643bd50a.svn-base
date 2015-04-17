package ubpartner.xvt.validation.commun;

import java.util.ArrayList;
import java.util.Collections;

import org.jdom.Element;

import ubmatrix.xbrl.common.memo.src.IMemo;
import ubpartner.logmanagement.LogManagement;
import ubpartner.utils.commun.ConstanteUtils;
import ubpartner.utils.xbrl.XbrlFact;

/**
 * Class for the facts-unrelated validation errors.
 * @author UBPartner
 *
 */
public class GeneralError {
    /**
     * Error ID.
     */
    private String errId = "";
    /**
     * Error type (concept, linkbase, technical, file).
     */
    private String errType = "";
    /**
     * File-source for error.
     */
    private String fileAffected = "";
    /**
     * Detailed error source.
     */
    private String det = "";
    /**
     * Error message.
     */
    private String mess = "";
    /**
     * Fact id.
     */
    private String fErr = "";
    
    /**
     * Getter for error ID.
     * @return the errId
     */
    public final String getErrId() {
        return errId;
    }
    /**
     * Setter for error ID.
     * @param cErrId the errId to set
     */
    public final void setErrId(final String cErrId) {
        this.errId = cErrId;
    }
    /**
     * Getter for error type.
     * @return the errType
     */
    public final String getErrType() {
        return errType;
    }
    /**
     * @param cErrType the errType to set
     */
    public final void setErrType(final String cErrType) {
        this.errType = cErrType;
    }
    /**
     * @return the fileAffected
     */
    public final String getFileAffected() {
        return fileAffected;
    }
    /**
     * @param cFileAffected the fileAffected to set
     */
    public final void setFileAffected(final String cFileAffected) {
        this.fileAffected = cFileAffected;
    }
    /**
     * @return the det
     */
    public final String getDet() {
        return det;
    }
    /**
     * @param cDet the det to set
     */
    public final void setDet(final String cDet) {
        this.det = cDet;
    }
    /**
     * @return the mess
     */
    public final String getMess() {
        return mess;
    }
    /**
     * @param cMess the mess to set
     */
    public final void setMess(final String cMess) {
        this.mess = cMess;
    }
    /**
     * @return the fErr
     */
    public final String getfErr() {
	return fErr;
    }
    /**
     * @param cFErr the fErr to set
     */
    public final void setfErr(final String cFErr) {
	this.fErr = cFErr;
    }
    /**
     * Constructor for the error.
     * @param cMemo - array with memo parts.
     * @param cMess - error message.
     */
    public GeneralError(final IMemo cMemo, final String cMess, ArrayList<String> factIds,
    		ArrayList<String> conceptIds, ArrayList<String> contextIds) {
		try {
	    	LogManagement.debug("Initializing error message");
			this.mess = cMess;
			this.errId = cMemo.getMemoURI();
			String [] cParts = (String[]) cMemo.getParticles();
			boolean isFactErr = false;
			//Parsing memos
			for (int i = 0; i < cParts.length; i++) {
			    if (cParts[i].endsWith(".xml") || cParts[i].endsWith(".xbrl") || cParts[i].endsWith(".xsd")) {
					this.fileAffected = cParts[i];
				} else if (!ConstanteUtils.isLongNumber(cParts[i])) {
					if (containsId(cParts[i], factIds, "Fact related error")) {		    
			        	isFactErr = true;
			        	LogManagement.debug("Fact error found");
					} else {
					//} else if (containsId(cParts[i], conceptIds, "Concept/Dimension related error")) {*/
			        	this.errType = "General error";
			        	this.det = "";
			        	LogManagement.debug("General error");
			        }
			    }
			}
			if (isFactErr) {
			    this.fErr = this.det;
			}
		} catch (Exception e) {
			LogManagement.debug("Failed to initialize error message: " + e.getMessage());
		}
    }
    /**
     * Verifies whether the string contains one or several of the strings from array.
     * @param partMess - message to verify.
     * @param ids - array with facts/contexts/concepts ID.
     * @param type - type of error.
     * @return true - if one of the IDs is in the message string.
     */
    @SuppressWarnings("rawtypes")
    public final boolean containsId(final String partMess, final ArrayList ids, final String type) {
	LogManagement.debug("Verify string: " + partMess);
	boolean res = false;
	String tmp = partMess;
	String idTmp;
	for (Object id : ids) {
	    if (id instanceof String) {
		idTmp = (String) id;
        	if (type.startsWith("Fact")) {
        	    idTmp = "[ID: " + idTmp + "]";
        	}
        	if (tmp.contains(idTmp)) {
        	   if (this.det.equals("")) {
                       this.det = (String) id;
                       this.errType = type;
                       tmp = tmp.replace(idTmp, "");
                       res = true;
                       LogManagement.debug("Fact id: " + this.det + " found");
        	   } else {
        	       tmp = tmp.replace(idTmp, "");
        	       this.det = this.det + ", " + (String) id;
        	   }        		
        	}
	    } else if (id instanceof XbrlFact) {
		idTmp = "'" + ((XbrlFact) id).getValue() + "'";
		if (tmp.contains(idTmp)) {
	           
	            this.det = ((XbrlFact) id).getId();
	            this.errType = type;
	            tmp = tmp.replace(idTmp, "");
	            res = true;
	            return res;
	        }
	    }
	}
	return res;
    }
    /**
     * Sorts array by elements length.
     * @param sortArr - array to sort.
     * @return sorted array.
     */
    public static final ArrayList<String> sortArrayByLength(final ArrayList<String> sortArr) {
	LogManagement.debug("Sorting array");
	ArrayList<String> resArr = sortArr;
	ServiceComparator cmp = new ServiceComparator();
	Collections.sort(resArr, cmp);
	return resArr;
    }
    /**
     * Transforms object to element.
     * @return error memo node.
     */
    public final Element toNode() {
	try {
            Element currNode;
            currNode = new Element("error");
            currNode.setAttribute("id", errId);
            currNode.setAttribute("type", errType);
            currNode.setAttribute("file", fileAffected);
            if (det == null) {
        	this.det = "";
            }
            currNode.setAttribute("source", det);
            currNode.setAttribute("message", mess);
            return currNode;
	} catch (Exception e) {
	    LogManagement.error("GeneralError=> Failed to create error message node: " + e.getMessage());
	} catch (Error e) {
	    LogManagement.fatal("GeneralError=> Failed to create error message node: " + e.getMessage());
	}
	return null;
    }
    
}
