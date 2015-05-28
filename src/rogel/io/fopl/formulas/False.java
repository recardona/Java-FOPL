package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;

/**
 * False (or "bottom") in FOPL is a special kind of Formula, which is unique
 * because its value is always false.
 * @author recardona
 */
public class False extends Formula {

	/** The singleton instance of this class. */
	private static final False SINGLETON = new False();
	
	/**
	 * Gets the singleton Formula that represents False. This Formula is unique
	 * in that its value is always false.
	 * @return False, a Formula whose value is always false.
	 */
	public static False get() {
		return SINGLETON;
	}
	
	/** Private constructor for the False class. */
	private False() {
		super("false");
		this.value = false;
	}

	@Override
	public String toString() {
		return this.symbol.toString();
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		return this; // False can't replace Variables.
	}
	
}