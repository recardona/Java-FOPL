package rogel.io.fopl.formulas;

import java.util.Arrays;
import java.util.List;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;
import rogel.io.fopl.terms.Term;
import rogel.io.fopl.terms.Variable;

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
	 * @param symbol
	 */
	public Predicate(Symbol symbol, Term... terms) {
		super(symbol);
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
	public Expression replaceVariables(Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Substitution unify(Unifiable unifiable, Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsVariable(Variable variable) {
		// TODO Auto-generated method stub
		return false;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Predicate: ");
		sb.append(this.symbol.toString());
		
		if(this.isPropositional()) {
			sb.append(" (propositional)]");
		}
		
		else {
			sb.append("(");
			for(int termIndex = 0; termIndex < this.terms.size(); termIndex++)
			{
				sb.append(this.terms.get(termIndex));
				sb.append(", ");
			}
			sb.replace(sb.length()-2, sb.length(), ")]"); //replace last comma+space for closing parenthesis
		}
		
		return sb.toString();
	}
	
	
	
}
