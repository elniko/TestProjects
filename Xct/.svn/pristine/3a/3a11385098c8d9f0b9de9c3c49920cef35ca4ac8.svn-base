package ubpartner.xct.commun;
/**
 * Class for return code in SDK mode.
 * @author UBPartner
 *
 */
public enum ReturnModeXct {
	/**
	 * Input mode type.
	 */
	INPUT("input"), ADRESSE("address"), NAME("name");
	/**
	 * Input mode variable.
	 */
	private final String mode;
	/**
	 * Setter for the return mode.
	 * @param s - mode to set.
	 */
	private ReturnModeXct(final String s) {
		mode = s;
	}
	/**
	 * Verifies which return mode is set.
	 * @param otherMode - mode to compare with.
	 * @return true - if modes are equal.
	 *         false - if otherMode is null or not equal to the main mode.
	 */
	public boolean equalsName(final String otherMode) {
		return (otherMode == null) ? false : mode.equals(otherMode);
	}
	/**
	 * Renders mode object to String.
	 * @return mode as String.
	 */
	public String toString() {
		return mode;
	}
}
