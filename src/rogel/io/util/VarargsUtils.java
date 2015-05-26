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
	 * @param o the varargs to check.
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
	
	/**
	 * Checks the parameter varargs for a null reference.
	 * @param o the varargs to check.
	 * @return true if the argument is null or contains a null element, false otherwise.
	 */
	public static boolean containsNull(Object... o) {
		
		try {
			checkForNull(o);
		} 
		
		catch(NullPointerException e) {
			return true;
		}
		
		return false;
	}

}
