package rogel.io.fopl.terms;

import java.util.List;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;

/**
 * Terms name objects or things. As such, Terms require at least one Symbol,
 * to uniquely identify the Term within the domain of discourse.
 * There are two types: Functions and Variables.
 * @author recardona
 */
public abstract class Term implements Unifiable {
	
	/** The Symbol that denotes this Term within the domain of discourse. */
	protected Symbol symbol;
	
	/**
	 * Defines a Term with the given name. If the name is a String that did not
	 * already exist within the domain of discourse (defined as a Symbol), then
	 * a new Symbol is created.
	 * @param name the name of this Term
	 */
	protected Term(String name) {
		this.symbol = Symbol.get(name);
	}
	
	/**
	 * Defines a Term with the given Symbol.
	 * @param symbol the Symbol that represents this Term within the domain of
	 *   discourse
	 */
	protected Term(Symbol symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return the symbol
	 */
	public final Symbol getSymbol() {
		return this.symbol;
	}
	
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
	
	@Override
	public boolean equals(Object obj) {
		//Term equality is composed of type and symbol equality
		if(!(obj instanceof Term)) {
			return false;
		}
		
		Term other = (Term) obj;
		return this.symbol.equals(other.symbol);		
	}
	
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}
	
	/**
	 * Checks to see if the parameter List of Terms contains the parameter 
	 * Variable, given an existing set of Substitutions (which may involve 
	 * the Variable to check for). This method accomplishes what is 
	 * colloquially referred to as the "occurs check" during unification.
	 * <p>
	 * This method is used by two types of Unifiable Objects which have access
	 * to a List of Unifiable Terms: Predicates and Functions.
	 * @param terms a List of Terms to check through.
	 * @param variable the Variable to check for.
	 * @param substitution the existing set of Substitutions to work with.
	 * @return true if any of the Terms in the List contains the Variable, false otherwise.
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
