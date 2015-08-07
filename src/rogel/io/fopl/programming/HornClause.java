package rogel.io.fopl.programming;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.formulas.Predicate;

/**
 * A HornClause is a clause with at most one positive (i.e. unnegated) literal. A HornClause has 
 * many forms, which have different names. Although they are typically represented as a disjunction
 * of literals, the explanation that follows represents them as implications (the underlying 
 * formulae are logically equivalent):
 * <ul>
 * 	<li> A HornClause of the form {@code u <- p ^ q ^ r} is called a <i>definite clause</i>. </li>
 *  <li> A HornClause of the form {@code u} is called a <i>fact</i>. </li>
 *  <li> A HornClause of the form {@code p ^ q ^ r} is called a <i>goal clause</i>. </li>
 * </ul>
 * @author recardona
 */
public class HornClause implements Expression {

	/**
	 * This represents the body of the HornClause. e.g. if we had a HornClause
	 * {@code u <- p ^ q ^ r}, the antecedent would be {@code p ^ q ^ r}.
	 */
	private Formula antecedent;
	
	/** 
	 * This represents the assertion of the HornClause. e.g. if we had a HornClause 
	 * {@code u <- p ^ q ^ r}, the consequent would be {@code u}.
	 */
	private Predicate consequent;
		
	/**
	 * Constructs a HornClause with just one positive literal. This is a HornClause <i>fact</i>.
	 * @param fact the Predicate this HornClause asserts.
	 */
	public HornClause(Predicate fact) {
		this(fact, null);
	}
	
	/**
	 * Constructor for a HornClause with one positive literal and a goal clause (which represents
	 * implicitly disjunctive negated literals).
	 * @param fact the Predicate this HornClause aims to prove.
	 * @param goal the GoalClause portion of this HornClause.
	 */
	public HornClause(Predicate fact, Formula goal) {
		this.consequent = fact;
		this.antecedent = goal;
	}
	
	/**
	 * A HornClause is definite if it has both an antecedent and a consequent.
	 * @return true if this HornClause is a definite clause.
	 */
	public boolean isDefiniteClause() {
		return (this.antecedent != null && this.consequent != null);
	}
	
	/**
	 * A HornClause is definite if it has a consequent with no antecedent.
	 * @return true if this HornClause is a fact.
	 */
	public boolean isFact() {
		return (this.antecedent == null && this.consequent != null);
	}
	
	/**
	 * A HornClause is a goal clause if it has an antecedent with no consequent.
	 * @return true if this HornClause is a goal clause.
	 */
	public boolean isGoalClause() {
		return (this.antecedent != null && this.consequent == null);
	}

	/**
	 * @return the antecedent
	 */
	public Formula getAntecedent() {
		return antecedent;
	}
	
	/**
	 * @return the consequent
	 */
	public Predicate getConsequent() {
		return consequent;
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		// TODO
		
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(antecedent == null) {
			return consequent.toString();
		}
		
		else {
			return consequent.toString() + " :- " + antecedent.toString();
		}		
	}

	
	
	

}
