package rogel.io.fopl;

import java.util.HashMap;

import rogel.io.fopl.terms.Variable;
import rogel.io.util.VarargsUtils;

/**
 * A Substitution is a mapping of a set of Variables to a set of Unifiable 
 * Objects. It serves as a solution of a unification problem.
 * @author recardona
 */
public class Substitution 
{
	/** The mappings between Variables and Unifiables. */
	private HashMap<Variable, Unifiable> bindings;
	
	/**
	 * Attempts to find the Substitution that unifies (i.e. makes syntactically
	 * equivalent) the Unifiable arguments. If no such Substitution exists,
	 * this method returns null. 
	 * <p>
	 * This method is meant as an alternate way to perform the unify method 
	 * outlined by the <code>Unifiable</code> interface. For example, calling
	 * <code>Substitution.unify(u1, u2)</code> is equivalent to calling
	 * <code>u1.unify(u2, substitution)</code>. This method, however, takes
	 * care of providing the initial substitution context, and groups the
	 * chain of method calls that would be needed to unify the method's
	 * arguments sequentially.
	 * @param arg1 the first Unifiable argument to unify
	 * @param arg2 the second Unifiable argument to unify
	 * @param moreArgs additional Unifiable arguments
	 * @return a Substitution that unifies the arguments, or null if no Substitution exists
	 */
	public static Substitution unify(Unifiable arg1, Unifiable arg2, Unifiable... moreArgs) {

		// Declare two Substitutions used for unification.
		Substitution identity = new Substitution();
		Substitution mostGeneralUnifier = null;
		
		// Perform unification for the first two arguments.
		mostGeneralUnifier = arg1.unify(arg2, identity);
		
		// Unify the rest of the arguments.
		VarargsUtils.checkForNull((Object[]) moreArgs);
		for(Unifiable u : moreArgs) {
			mostGeneralUnifier = arg1.unify(u, mostGeneralUnifier);
		}

		// Return the most general unifier.
		return mostGeneralUnifier;
	}
	
	/**
	 * Creates an empty substitution set.
	 */
	public Substitution() {
		this.bindings = new HashMap<Variable, Unifiable>();
	}
	
	/**
	 * Creates a non-empty substitution set, comprised of the bindings in the
	 * parameter Substitution set s.
	 * @param s the Substitution set to initialize with
	 */
	public Substitution(Substitution s) {
		this.bindings = new HashMap<Variable, Unifiable>(s.getBindings());
	}
	
	/**
	 * Clears this Substitution of all bindings.
	 */
	public void clear() {
		this.bindings.clear();
	}
	
	/**
	 * Binds the Term to the Variable.
	 * @param variable the Variable to be bound
	 * @param unifiable the Term that will be bound to the variable
	 */
	public void add(Variable variable, Unifiable unifiable) {
		this.bindings.put(variable, unifiable);
	}
	
	/**
	 * Gets the binding for this Variable. This binding is a Unifiable Object. 
	 * If no such binding exists, this method returns null.
	 * @param variable the Variable to lookup
	 * @return the Unifiable Object bound to the variable, or null if the 
	 * 	variable is not bound.
	 */
	public Unifiable getBinding(Variable variable) {
		return (Unifiable) this.bindings.get(variable);
	}
	
	/**
	 * @return true if the Variable is bound, false otherwise.
	 */
	public boolean isBound(Variable variable) {
		return (this.bindings.get(variable) != null);
	}
	
	/**
	 * A Substitution is "ground" if all terms are ground terms (no variables)
	 * @return true if this Substitution is ground
	 */
	public boolean isGround() {
		for(Unifiable u : this.getBindings().values()) {
			if(u instanceof Variable) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the bindings
	 */
	public HashMap<Variable, Unifiable> getBindings() {
		return this.bindings;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Substitution other = (Substitution) obj;
		if (bindings == null) {
			if (other.bindings != null) {
				return false;
			}
		} else if (!bindings.equals(other.bindings)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bindings == null) ? 0 : bindings.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Bindings: ["+this.bindings+"]";
	}
		
}