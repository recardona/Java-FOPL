package rogel.io.fopl;

/**
 * An Expression represents anything that can be expressed in FOPL.	
 * @author recardona
 */
public interface Expression {

	/**
	 * Replaces any Variable in this Expression with its binding in the 
	 * Substitution, if it exists.
	 * @param substitution the Substitution set of Variables
	 * @return an Expression with substituted Variables.
	 */
	public Expression replaceVariables(Substitution substitution);
}
