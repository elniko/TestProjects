package ubpartner.xct.commun;

import java.util.Comparator;

import ubpartner.xct.MappedFact;
/**
 * Class for the fact comparator. Sorts mapped facts by table id.
 * @author UBPartner
 *
 */
public class FactComparator implements Comparator<MappedFact> {
	 
	private final boolean isDigit(char ch) {
		return ch >= 48 && ch <= 57;
	}
	/**
	 * Compares two mapped facts by id.
	 * @param fact1 - first fact to compare.
	 * @param fact2 - second fact to compare.
	 * @return 0 - if both mapping references are empty.
	 *         1 - if mapping reference of first fact is not empty and second mapping reference is empty.
	 *         -1 - if mapping reference of second fact is not empty and first mapping reference is empty.
	 *         else return the result of fact1.getIdMappingRef().compareTo(fact2.getIdMappingRef()).
	 */
	public final int compare(final MappedFact fact1, final MappedFact fact2) {

        if (fact1.getIdMappingRef().equals("") && fact2.getIdMappingRef().equals("")) {
        	return 0;
        } else if (fact1.getIdMappingRef().equals("") && !fact2.getIdMappingRef().equals("")) {
        	return -1;
        } else if (!fact1.getIdMappingRef().equals("") && fact2.getIdMappingRef().equals("")) {
            return 1;
        } else {
		    //return fact1.getIdMappingRef().compareTo(fact2.getIdMappingRef());
        	return compareFacts(fact1, fact2);
        }
	}

    /**
     * Length of string is passed in for improved efficiency (only need to
     * calculate it once)
     **/
	private final String getChunk(String s, int slength, int marker) {
		StringBuilder chunk = new StringBuilder();
		char c = s.charAt(marker);
		chunk.append(c);
		marker++;
		if (isDigit(c)) {
		    while (marker < slength) {
			c = s.charAt(marker);
			if (!isDigit(c))
			    break;
			chunk.append(c);
			marker++;
		    }
		} else {
		    while (marker < slength) {
			c = s.charAt(marker);
			if (isDigit(c))
			    break;
			chunk.append(c);
			marker++;
		    }
		}
		return chunk.toString();
	}
	
	public int compareFacts(final MappedFact fact1, final MappedFact fact2) {
		String s1 = "";
		String s2 = "";
		s1 = fact1.getIdMappingRef();
		s2 = fact2.getIdMappingRef();
		
		int thisMarker = 0;
		int thatMarker = 0;
		int s1Length = s1.length();
		int s2Length = s2.length();

		while (thisMarker < s1Length && thatMarker < s2Length) {
		    String thisChunk = getChunk(s1, s1Length, thisMarker);
		    thisMarker += thisChunk.length();

		    String thatChunk = getChunk(s2, s2Length, thatMarker);
		    thatMarker += thatChunk.length();

		    // If both chunks contain numeric characters, sort them numerically
		    int result = 0;
		    if (isDigit(thisChunk.charAt(0)) && isDigit(thatChunk.charAt(0))) {
			// Simple chunk comparison by length.
			int thisChunkLength = thisChunk.length();
			result = thisChunkLength - thatChunk.length();
			// If equal, the first different number counts
			if (result == 0) {
			    for (int i = 0; i < thisChunkLength; i++) {
				result = thisChunk.charAt(i) - thatChunk.charAt(i);
				if (result != 0) {
				    return result;
				}
			    }
			}
		    } else {
			result = thisChunk.compareToIgnoreCase(thatChunk);
		    }

		    if (result != 0)
			return result;
		}

		return s1Length - s2Length;
	    }
}
