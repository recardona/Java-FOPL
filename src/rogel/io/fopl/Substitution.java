package rogel.io.fopl;

import java.util.HashMap;

import rogel.io.fopl.terms.Variable;

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
	public String toString() {
		return "Bindings: ["+this.bindings+"]";
	}
		
}