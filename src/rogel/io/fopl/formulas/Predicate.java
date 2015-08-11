package rogel.io.fopl.formulas;

import java.util.Arrays;
import java.util.HashMap;
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
 * <p>
 * A Predicate may be thought of as an Expression that may be true or false depending on the values
 * of the terms it describes. It can also be thought of as an operator or function that returns a 
 * boolean value. 
 * <p>
 * {@code Predicate: X -> [true, false] }
 * <p>
 * In this implementation, each Predicate that is declared (i.e. constructed) is true by virtue of 
 * its construction. 
 * @author recardona
 */
public final class Predicate extends Formula implements Unifiable {
	
	/** The number of terms this Predicate describes. */
	private int arity;
	
	/** The terms this Predicate describes. */
	private List<Term> terms;
	
	/**
	 * Constructs an n-ary true-valued Predicate with the given name. A 0-ary Predicate is used to 
	 * represent a propositional symbol. The arity of this Predicate depends on the number of Terms
	 * added. 
	 * <p>
	 * If the name is a String that did not already exist within the domain of discourse (i.e. was 
	 * already defined as a Symbol), then a new Symbol is created and added to the domain of 
	 * discourse. 
	 * 
	 * @param name the name of the Predicate, not null.
	 * @param terms the terms this Predicate describes, not null.
	 */
	public Predicate(String name, Term... terms) {
		this(Symbol.get(name), terms);
	}
	
	/**
	 * Constructs an n-ary true-valued Predicate with the given Symbol. A 0-ary Predicate is used 
	 * to represent a propositional symbol. The arity of this Predicate depends on the number of 
	 * Terms added.
	 * 
	 * @param symbol the Symbol that represents this Predicate within the domain of discourse, not null.
	 * @param terms the terms this Predicate describes, not null.
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
	 * 
	 * @return true if this Predicate describes no Terms.
	 */
	public boolean isPropositional() {
		return (this.arity == 0);
	}
	
	/**
	 * Returns the number of Terms this Predicate describes.
	 * 
	 * @return this Predicate's arity.
	 */
	public int getArity() {
		return this.arity;
	}
	
	/**
	 * Returns the terms this Predicate applies to. For example, in the Predicate {@code (cat Ash)}
	 * the term would be {@code Ash}. If this Predicate is propositional, this method returns null.
	 * 
	 * @return this Predicate's terms.
	 */
	public List<Term> getTerms() {
		return this.terms;
	}

	/**
	 * Returns true if and only if this Formula is a literal. Because Predicates are always
	 * literals, this method always returns true.
	 * 
	 * @return true, always.
	 */
	@Override
	public boolean isLiteral() {
		return true;
	}

	/**
	 * Returns true if and only if this Formula is atomic. Because Predicates are always atomic,
	 * this method always returns true.
	 * 
	 * @return true, always.
	 */
	@Override
	public boolean isAtomic() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Unifiable#unify(rogel.io.fopl.Unifiable, rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Unifiable#containsVariable(rogel.io.fopl.terms.Variable, rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
	 */
	@Override
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
		
		// We must return a new Predicate with standardized variables in the Terms.
		Term[] newArguments = new Term[this.terms.size()];
		
		// For each argument Term, standardized its variables.
		for(int termIndex = 0; termIndex < this.terms.size(); termIndex++) {
			Term term = this.terms.get(termIndex);
			Expression standardizedVariableExpression = term.standardizeVariablesApart(newVariables);
			newArguments[termIndex] = (Term) standardizedVariableExpression;
		}
		
		// Create and return the new Predicate with the same Symbol and new arguments.
		Predicate standardizedVariablePredicate = new Predicate(this.symbol, newArguments);
		return standardizedVariablePredicate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.Formula#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.Formula#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + arity;
		result = prime * result + ((terms == null) ? 0 : terms.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(this.symbol.toString());
		
		if(this.isPropositional()) {
			builder.append(")");
		}
		
		else {
			builder.append(" ");
			for(int termIndex = 0; termIndex < this.terms.size(); termIndex++)
			{
				builder.append(this.terms.get(termIndex));
				builder.append(" ");
			}
			builder.replace(builder.length()-1, builder.length(), ")"); //replace last comma+space for closing parenthesis
		}
		
		return builder.toString();
	}

}
