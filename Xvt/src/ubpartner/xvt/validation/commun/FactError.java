package ubpartner.xvt.validation.commun;

import ubpartner.logmanagement.LogManagement;
/**
 * Class for fact error objects.
 * @author UBPartner
 *
 */
public class FactError {

    /**
     * Fact ID.
     */
    private String factId;
    /**
     * Sheet name.
     */
    private String sheetName;
    /**
     * Range reference.
     */
    private String rangeRef;
    /**
     * Error message.
     */
    private String mess;
    /**
     * @return the factId
     */
    public final String getFactId() {
        return factId;
    }
    /**
     * @param cFactId the factId to set
     */
    public final void setFactId(final String cFactId) {
        this.factId = cFactId;
    }
    /**
     * @return the sheetName
     */
    public final String getSheetName() {
        return sheetName;
    }
    /**
     * @param cSheetName the sheetName to set
     */
    public final void setSheetName(final String cSheetName) {
        this.sheetName = cSheetName;
    }
    /**
     * @return the rangeRef
     */
    public final String getRangeRef() {
        return rangeRef;
    }
    /**
     * @param cRangeRef the rangeRef to set
     */
    public final void setRangeRef(final String cRangeRef) {
        this.rangeRef = cRangeRef;
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
     * Constructor for fact validation error.
     * @param cFactId - fact ID.
     * @param cMess - error message.
     */
    public FactError(final String cFactId, final String cMess) {
	LogManagement.debug("Initializing fact error for fact ID: " + cFactId);
	this.factId = cFactId;
	this.mess = cMess;
	this.sheetName = "";
	this.rangeRef = "";
    }
    
}
