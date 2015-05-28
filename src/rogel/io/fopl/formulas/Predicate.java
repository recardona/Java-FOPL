package rogel.io.fopl.formulas;

import java.util.Arrays;
import java.util.List;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;
import rogel.io.fopl.terms.Term;
import rogel.io.fopl.terms.Variable;
import rogel.io.util.VarargsUtils;

/**
 * A Predicate names a relation between Terms in a domain of discourse.
 * @author recardona
 */
public class Predicate extends Formula implements Unifiable {
	
	/** The number of terms this Predicate describes. */
	private int arity;
	
	/** The terms this Predicate describes. */
	private List<Term> terms;
	
	/**
	 * Constructs an n-ary Predicate with the given name. If the name is a
	 * String that did not already exist within the domain of discourse
	 * (i.e. was already defined as a Symbol), then a new Symbol is created and
	 * added to the domain of discourse. The arity of this Predicate depends on
	 * the number of Terms added.
	 * <p>
	 * Note: A 0-ary Predicate is used to represent a propositional symbol.
	 * @param name the name of the Predicate
	 * @param terms the terms this Predicate describes
	 */
	public Predicate(String name, Term... terms) {
		super(name);
		VarargsUtils.throwExceptionOnNull((Object []) terms);
		
		this.value = true; // Predicates are true by default.
		this.arity = terms.length;
		
		// If the arity is 0, define the Terms as null.
		if(this.arity == 0) {
			this.terms = null;
		}
		
		else {
			this.terms = Arrays.asList(terms);
		}
	}
	
	/**
	 * Constructs an n-ary Predicate with the given Symbol. The arity of this
	 * Predicate depends on the number of Terms added.
	 * <p>
	 * Note: A 0-ary Predicate is used to represent a propositional symbol.
	 * @param symbol the Symbol that represents this Predicate within the
	 * domain of discourse
	 * @param terms the terms this Predicate describes
	 */
	public Predicate(Symbol symbol, Term... terms) {
		super(symbol);
		VarargsUtils.throwExceptionOnNull((Object[]) terms);
		
		this.value = true; // Predicates are true by default.
		this.arity = terms.length;
		
		// If the arity is 0, define the Terms as null.
		if(this.arity == 0) {
			this.terms = null;
		}
		
		else {
			this.terms = Arrays.asList(terms);
		}
	}
	
	/**
	 * True if this Predicate is propositional. Propositions are 0-ary Predicates.
	 * @return true if this Predicate describes no Terms
	 */
	public boolean isPropositional() {
		return (this.arity == 0);
	}
	
	/**
	 * Returns the number of Terms this Predicate describes.
	 * @return this Predicate's arity
	 */
	public int getArity() {
		return this.arity;
	}
	
	/**
	 * Returns the terms this Predicate applies to. For example, in the
	 * Predicate <code>(cat Ash)</code> the term would be <code>Ash</code>.
	 * If this Predicate is propositional, this method returns null.
	 * @return this Predicate's terms.
	 */
	public List<Term> getTerms() {
		return this.terms;
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public boolean isAtomic() {
		return true;
	}
	
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		if(this.isPropositional()) {
			// A propositional Predicate can't replace variables, because it
			// does not have any Variable Terms. We thus return the Predicate
			// itself (unchanged).
			return this;
		}
		
		else {
			// We must return a new Predicate with replaced Terms.
			Term[] newTerms = new Term[this.terms.size()];
			
			// For each Term, replace variables with the given substitution.
			for(int termIndex = 0; termIndex < this.terms.size(); termIndex++) {
				newTerms[termIndex] = (Term) this.terms.get(termIndex).replaceVariables(substitution);
			}
			
			// Create and return the new Function with the same Symbol and new arguments.
			Predicate substitutedVariablePredicate = new Predicate(this.symbol, newTerms);
			return substitutedVariablePredicate;
		}
	}

	@Override
	public Substitution unify(Unifiable unifiable, Substitution substitution) {

		// If we're trying to unify with a Variable, just delegate to that 
		// Variable's unify method.
		if(unifiable instanceof Variable) {
			return unifiable.unify(this, substitution);
		}
		
		// Otherwise,
		else {
			
			// This implementation of unify does two different things,
			// depending on whether this Predicate is propositional or not.
			if(this.isPropositional()) {
				
				// If they're the same, then any Substitution will make
				// this and that unify.
				if(this.equals(unifiable)) {
					return substitution;
				}
			}
			
			// If this Predicate is not propositional,
			else {
				
				// If unifiable isn't a Predicate, we can't do anything.
				if(unifiable instanceof Predicate) {
					
					Predicate pUnifiable = (Predicate) unifiable;
					
					// If the Predicate Symbols are different, or if they have
					// different arity, they can't be unified.
					if( !this.symbol.equals(pUnifiable.symbol) || this.arity != pUnifiable.arity) {
						return null;
					}
					
					// Otherwise, go through each of the Terms and attempt to unify.
					else {
						
						// Initialize the new Substitution set with existing mappings.
						Substitution theta = new Substitution(substitution);
						for(int termIndex = 0; termIndex < this.terms.size(); termIndex++) {
							
							// Get the next Term from each Predicate.
							Term t1 = this.terms.get(termIndex);
							Term t2 = pUnifiable.terms.get(termIndex);
							
							// Attempt to unify.
							theta = t1.unify(t2, theta);
							
							// If we ever get null, we have failed, so fail.
							if(theta == null) {
								return null;
							}
						}
						
						// We have succeeded in the for loop, so return the new substitutions.
						return theta;
					}
				}
			}
		}
		
		// No luck in finding Substitutions! Thus, none are possible.
		return null;
	}

	@Override
	public boolean containsVariable(Variable variable, Substitution substitution) {
		
		if(this.isPropositional()) {
			// A propositional Predicate cannot contain a Variable.
			return false;
		}
		
		else {
			// Delegate to the static Term method.
			return Term.containsVariable(this.terms, variable, substitution);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Predicate other = (Predicate) obj;
		if (arity != other.arity) {
			return false;
		}
		if (terms == null) {
			if (other.terms != null) {
				return false;
			}
		} else if (!terms.equals(other.terms)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + arity;
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[Predicate: ");
		builder.append(this.symbol.toString());
		
		if(this.isPropositional()) {
			builder.append(" (propositional)]");
		}
		
		else {
			builder.append("(");
			for(int termIndex = 0; termIndex < this.terms.size(); termIndex++)
			{
				builder.append(this.terms.get(termIndex));
				builder.append(", ");
			}
			builder.replace(builder.length()-2, builder.length(), ")]"); //replace last comma+space for closing parenthesis
		}
		
		return builder.toString();
	}

}
