package rogel.io.fopl;

import rogel.io.fopl.terms.Variable;

/**
 * A Unifiable Object is a logical Expression that can be used in unification.
 * @author recardona
 */
public interface Unifiable extends Expression {
    
    /**
     * Attempts to find the Substitution that unifies (makes syntactically equivalent) this 
     * Unifiable Object with the parameter Unifiable Object, given an existing set of 
     * Substitutions. If no such Substitution exists, this method returns null. 
     * <p>
     * This method is commutative: calling {@code u1.unify(u2, s)} is equivalent to calling 
     * {@code u2.unify(u1, s)}.
     * 
     * @param unifiable the Object to attempt to unify with, not null.
     * @param substitution the existing set of Substitutions to work with, not null.
     * @return a Substitution that unifies this Object with the parameter Unifiable, or null if no 
     *     Substitution exists.
     */
    public Substitution unify(Unifiable unifiable, Substitution substitution);
    
    /**
     * Checks to see if this Unifiable expression contains the parameter Variable, given an 
     * existing set of Substitutions (which may involve the Variable to check for). This method
     * accomplishes what is colloquially referred to as the "occurs check" during unification.
     * 
     * @param variable the Variable to check for, not null.
     * @param substitution the existing set of Substitutions to work with, not null.
     * @return true if this Unifiable Object contains the Variable, false otherwise.
     */
    public boolean containsVariable(Variable variable, Substitution substitution);
    
}
