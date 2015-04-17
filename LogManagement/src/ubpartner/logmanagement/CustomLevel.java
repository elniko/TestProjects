package ubpartner.logmanagement;

import org.apache.log4j.Level;
/**
 * Class to present custom log level messages.
 * @author UBPartner
 *
 */
public class CustomLevel extends Level {

	/**
	 *  Syslog equivalent.
	 */
	private static final  int SYS_LOG = 7;
	
	/**
	 * Default level.
	 */
	private static final int LEVEL = Level.INFO_INT + 2;
	
	 
	/**
	 * Return cod for the level.
	 */
	private int returnCode;
	
	/**
	 * Level string description.
	 */
	private String lString = "";
	
	/**
	 * General constructor.
	 * 
	 * @param level Level
	 * 
	 * @param levelStr Level string description
	 * 
	 * @param syslogEquivalent syslog equivalent
	 * 
	 * @param rc Return code
	 */
	protected CustomLevel(final int level, final String levelStr, final int syslogEquivalent, final int rc) {
		super(level, levelStr, syslogEquivalent);
		lString = levelStr;
		returnCode = rc;
	}
	
	/**
	 * Constructor  without syslog.
	 * 
	 * @param level Level
	 * 
	 * @param levelStr Level string description
	 * 
	 * @param rc Return code
	 */
	public CustomLevel(final int level, final String levelStr, final int rc) {
		this(level, levelStr, SYS_LOG, rc);
		
	}
	
	/**
	 * Constructor  without syslog and level.
	 * 
	 * @param levelStr Level string description
	 * 
	 * @param rc Return code
	 */
	public CustomLevel(final String levelStr, final int rc) {
		this(LEVEL, levelStr, SYS_LOG, rc);
		
	}

	/**
	 * Getter return code.
	 * 
	 * @return return code
	 */
	public final  int getReturnCode() {
		return returnCode;
	}
	
	/**
	 * Getter string description.
	 * 
	 * @return String description
	 */
	public final String getLevelString() {
		return lString;
	}
}
