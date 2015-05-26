package rogel.io.fopl.formulas;

/**
 * True (or "top") in FOPL is a special kind of Formula, which is unique
 * because its value is always true. 
 * @author recardona
 */
public class True extends Formula {

	/** The singleton instance of this class. */
	private static final True SINGLETON = new True();
	
	/**
	 * Gets the singleton Formula that represents True. This Formula is unique
	 * in that its value is always false. 
	 * @return True, a Formula whose value is always true.
	 */
	public static True get() {
		return SINGLETON;
	}

	/** Private constructor for the True class. */
	private True() {
		super("true");
		this.value = true;
	}
	
	@Override
	public String toString() {
		return this.symbol.toString();
	}
}
