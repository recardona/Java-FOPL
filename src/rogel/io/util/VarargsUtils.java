package rogel.io.util;

import java.util.Arrays;

/**
 * Contains utility methods to help manage variable argument references.
 * @author recardona
 */
public class VarargsUtils {
	
	private VarargsUtils() { /* Private constructor for no initialization. */ }
	
	/**
	 * Checks the parameter varargs for a null reference. 
	 * @param o the varargs to check
	 * @throws NullPointerException if either the argument is null or contains a null element.
	 */
	public static void checkForNull(Object... o) throws NullPointerException {
		if(o == null) {
			throw new NullPointerException("Parameter varargs " + o + " is null.");
		}
		
		if(Arrays.asList(o).contains(null)) {
			throw new NullPointerException("Parameter varargs " + o + " contains a null element.");
		}
	}

}
