package ubpartner.xvt.validation.commun;

import java.util.Comparator;
/**
 * Comparator class for the string elements.
 * @author UBPartner
 *
 */
public class ServiceComparator implements Comparator<String> {
    /**
     * Main comparator method. Sorts string by their length.
     * @param sortStr1 - first string to compare.
     * @param sortStr2 - second string to compare.
     * @return -1 - if the first string is longer than the second.
     *         1 - if the first string is shorter than the second.
     *         else return the result of sortStr1.compareTo(sortStr2)
     */
   public final int compare(final String sortStr1, final String sortStr2) {
	if (sortStr1.length() > sortStr2.length()) {
	    return -1;
	} else if (sortStr1.length() < sortStr2.length()) {
	    return 1;
	} else  {
	    return sortStr1.compareTo(sortStr2);
	}
    }
}
