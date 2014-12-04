package rogel.io.fopl;

import java.util.HashMap;

import rogel.io.fopl.terms.Term;
import rogel.io.fopl.terms.Variable;

/**
 * A Substitution maps a set of Variables to a set of Terms.  
 * 
 * @author recardona
 */
public class Substitution 
{
	//the mappings between the variable and terms
	private HashMap<Variable, Term> bindings;
	
	/**
	 * Creates an empty substitution set.
	 */
	public Substitution() {
		this.bindings = new HashMap<Variable, Term>();
	}
	
	
	/**
	 * Creates a non-empty substitution set, comprised of the bindings in the
	 * parameter Substitution set s.
	 * @param s the Substitution set to initialize with
	 */
	public Substitution(Substitution s) {
		this.bindings = new HashMap<Variable, Term>(s.getBindings());
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
	 * @param term the Term that will be bound to the variable
	 */
	public void add(Variable variable, Term term) {
		this.bindings.put(variable, term);
	}
	
	
	/**
	 * Gets the binding for this Variable. This binding is a Term. If no such
	 * binding exists, this method returns null.
	 * @param variable the Variable to lookup
	 * @return the Term bound to the variable, or null if the variable is not bound.
	 */
	public Term getBinding(Variable variable) {
		return (Term) this.bindings.get(variable);
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
		for(Term t : this.getBindings().values()) {
			if(t instanceof Variable) {
				return false;
			}
		}
		return true;
	}

	
	/**
	 * @return the bindings
	 */
	public HashMap<Variable, Term> getBindings() {
		return this.bindings;
	}

	
	@Override
	public String toString() {
		return "Bindings: ["+this.bindings+"]";
	}
		
}
