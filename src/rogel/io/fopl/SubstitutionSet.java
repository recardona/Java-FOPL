package rogel.io.fopl;

import java.util.HashMap;

/**
 * A <code>SubstitutionSet</code> maintains variable bindings used in unification.
 * Variable bindings as a list of <code>Variable</code>/<code>Unifiable</code>
 * pairs. 
 */
public class SubstitutionSet 
{
	private HashMap<Variable, Unifiable> bindings;

	public SubstitutionSet() {
		this.bindings = new HashMap<Variable, Unifiable>();
	}
	
	public SubstitutionSet(SubstitutionSet s) {
		this.bindings = new HashMap<Variable, Unifiable>(s.getBindings());
	}
	
	
	/**
	 * Clears the substitution set.
	 */
	public void clear() {
		this.bindings.clear();
	}
	
	
	/**
	 * Binds the expression to the variable.
	 * @param variable the variable to be bound
	 * @param expression the expression that will be bound to the variable
	 */
	public void add(Variable variable, Unifiable expression) {
		this.bindings.put(variable, expression);
	}
	
	
	/**
	 * Gets the binding for this variable. This binding is a <code>Unifiable</code>
	 * expression. If no such binding exists, this method returns <code>null</code>.
	 * @param variable the variable to lookup
	 * @return the <code>Unifiable</code> expression bound to the variable, or
	 * <code>null</code> if the variable is not bound.
	 */
	public Unifiable getBinding(Variable variable) {
		return (Unifiable) this.bindings.get(variable);
	}
	
	
	/**
	 * @return true if the <code>Variable</code> is bound, false otherwise.
	 */
	public boolean isBound(Variable variable) {
		return (this.bindings.get(variable) != null);
	}
	
	
	/**
	 * @return the bindings
	 */
	public HashMap<Variable, Unifiable> getBindings() {
		return bindings;
	}
	
	@Override
	public String toString() {
		return "Bindings: ["+this.bindings+"]";
	}
}
