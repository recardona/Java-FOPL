package rogel.io.fopl;

import rogel.io.fopl.terms.Variable;

/**
 * A Unifiable Object is a logical expression that can be used in unification.
 * @author recardona
 */
public interface Unifiable extends Expression {
	
	/**
	 * Attempts to find the Substitution that unifies (i.e. makes syntactically
	 * equivalent) this Unifiable Object with the parameter Unifiable 
	 * expression, given an existing set of Substitutions. If no such
	 * Substitution exists, this method returns null. 
	 * <p>
	 * This method is commutative: calling <code>u1.unify(u2, s)</code> is
	 * equivalent to calling <code>u2.unify(u1, s)</code>.
	 * @param unifiable the object to attempt to unify with
	 * @param substitution the existing set of Substitutions to work with
	 * @return a Substitution that unifies this Object with the parameter 
	 * 	Unifiable, or null if no Substitution exists
	 */
	public Substitution unify(Unifiable unifiable, Substitution substitution);
	
	/**
	 * Checks to see if this Unifiable expression contains the parameter
	 * Variable. This method accomplishes what is colloquially referred to as 
	 * the "occurs check" during unification.
	 * @param variable the Variable to check for
	 * @return true if this Unifiable Object contains the Variable, false otherwise
	 */
	public boolean containsVariable(Variable variable);
	
}
