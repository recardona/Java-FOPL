package rogel.io.fopl.terms;

import java.util.List;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;
import rogel.io.fopl.formulas.Predicate;

/**
 * Terms name objects or things. As such, Terms require at least one Symbol, to uniquely identify
 * the Term within the domain of discourse.
 * 
 * @author recardona
 */
public abstract class Term implements Unifiable {
	
	/** The Symbol that denotes this Term within the domain of discourse. */
	protected Symbol symbol;
	
	/**
	 * Constructs a Term with the given name. If the name is a String that did not already exist
	 * within the domain of discourse (defined as a Symbol), then a new Symbol is created.
	 * 
	 * @param name The name of this Term.
	 */
	protected Term(String name) {
		this(Symbol.get(name));
	}
	
	/**
	 * Constructs a Term with the given Symbol.
	 * 
	 * @param symbol The Symbol that represents this Term within the domain of discourse.
	 */
	protected Term(Symbol symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Gets the Symbol associated with this Term.
	 * 
	 * @return the Symbol.
	 */
	public final Symbol getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Compares this Term to the parameter object. The result is true if and only if the argument
	 * is another Term with the same symbol.
	 * 
	 * @param obj The object to compare this Symbol against.
	 * @return true if the given object represents a Term with the same Symbol as this Term, false
	 * 	otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		//Term equality is composed of type and symbol equality
		if(!(obj instanceof Term)) {
			return false;
		}
		
		Term other = (Term) obj;
		return this.symbol.equals(other.symbol);		
	}
	
	/**
	 * Returns a hash code for this Term. The hash code for a Term object is computed as the hash
	 * of the underlying Symbol that represents this term; i.e. {@code getSymbol().hashCode()}
	 * 
	 * @return a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}
	
	/**
	 * Returns a String representation of this Term. Since this class is abstract, the class defers 
	 * to the {@code toString()} methods of the known implementing classes: Function and Variable. 
	 * 
	 * @return a String representation of this object.
	 */
	@Override
	public String toString() {
		if(this instanceof Function) {
			return ((Function) this).toString();
		}
		
		if(this instanceof Variable) {
			return ((Variable) this).toString();
		}
		
		else {
			return "[Term: " + this.symbol.toString() + "]";
		}
	}
	
	/**
	 * Checks to see if the parameter List of Terms contains the parameter Variable, given an 
	 * existing set of Substitutions (which may involve the Variable to check for). This method
	 * accomplishes what is colloquially referred to as the "occurs check" during unification.
	 * <p>
	 * This method is used by two types of Unifiable Objects which have access to a List of 
	 * Unifiable Terms: Predicates and Functions.
	 * 
	 * @param terms A List of Terms to check through, not null.
	 * @param variable The Variable to check for, not null.
	 * @param substitution The existing set of Substitutions to work with, not null.
	 * @return true if any of the Terms in the List contains the Variable, false otherwise.
	 * @see Predicate#containsVariable(Variable, Substitution)
	 * @see Function#containsVariable(Variable, Substitution)
	 */
	public static boolean containsVariable(List<Term> terms, Variable variable, Substitution substitution) {
		
		// Check all the terms individually.
		for(Term term : terms) {

			// Assume it doesn't contain the Variable.
			boolean containsVariable = false;

			// Terms can be Functions or Variables, so delegate to the Term's method.
			containsVariable = term.containsVariable(variable, substitution);

			// If the Term doesn't contain the Variable and is itself a 
			// Variable, and...
			if(!containsVariable && term instanceof Variable) {

				Variable termVariable = (Variable) term;

				// ...if the Term is bound to something, let's check the bound value.
				if(substitution.isBound(termVariable)) {
					Unifiable bindingForTermVariable = substitution.getBinding(termVariable);
					containsVariable = bindingForTermVariable.containsVariable(variable, substitution);
				}

				// ...or if the Variable is bound to something (and we still haven't
				// found a contained variable), let's check the Variable bound value.
				if(!containsVariable && substitution.isBound(variable)) {
					Unifiable bindingForVariable = substitution.getBinding(variable);

					// This check happens in the opposite direction, because we don't
					// know the type of the Unifiable bindingForVariable and can't pass
					// it into the containsVariable method.
					containsVariable = bindingForVariable.containsVariable(termVariable, substitution);
				}
			}

			// If we ever get true, we've found it, so return.
			if(containsVariable == true) {
				return true;
			}
		}

		// This Function does not contain the parameter Variable.
		return false;
	}
}
