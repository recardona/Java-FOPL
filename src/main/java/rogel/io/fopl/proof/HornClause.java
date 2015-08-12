package rogel.io.fopl.proof;

import java.util.HashMap;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.terms.Variable;

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
 * 
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
	 * 
	 * @param fact The Predicate this HornClause asserts.
	 */
	public HornClause(Predicate fact) {
		this(fact, null);
	}
	
	/**
	 * Constructor for a HornClause with one positive literal and a goal clause (which represents
	 * implicitly disjunctive negated literals).
	 * 
	 * @param consequent The Predicate this HornClause aims to prove.
	 * @param antecedent The goal Formula portion of this HornClause.
	 */
	public HornClause(Predicate consequent, Formula antecedent) {
		this.consequent = consequent; // head
		this.antecedent = antecedent; // body
	}
	
	/**
	 * Returns true if this HornClause is a definite clause. A HornClause is definite if it has 
	 * both an antecedent and a consequent.
	 * 
	 * @return true if this HornClause is a definite clause.
	 */
	public boolean isDefiniteClause() {
		return (this.antecedent != null && this.consequent != null);
	}
	
	/**
	 * Returns true if this HornClause is a fact. A HornClause is a fact if it has a consequent 
	 * with no antecedent.
	 * 
	 * @return true if this HornClause is a fact.
	 */
	public boolean isFact() {
		return (this.antecedent == null && this.consequent != null);
	}
	
	/**
	 * Returns true if this HornClause is a goal clause. A HornClause is a goal if it has an
	 * antecedent with no consequent.
	 * 
	 * @return true if this HornClause is a goal clause.
	 */
	public boolean isGoalClause() {
		return (this.antecedent != null && this.consequent == null);
	}

	/**
	 * Gets this HornClause's antecedent if it exists, null otherwise. The antecedent represents 
	 * the body of the HornClause. e.g. for a HornClause {@code u <- p ^ q ^ r}, the antecedent 
	 * would be {@code p ^ q ^ r}.
	 * 
	 * @return the antecedent Formula.
	 */
	public Formula getAntecedent() {
		return this.antecedent;
	}
	
	/**
	 * Gets this HornClause's consequent. The antecedent represents the assertion of the 
	 * HornClause. e.g. for a HornClause {@code u <- p ^ q ^ r}, the consequent would be 
	 * {@code u}.
	 * 
	 * @return the consequent Predicate.
	 */
	public Predicate getConsequent() {
		return this.consequent;
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Replace the Variables in each of these Expressions.
		Predicate newConsequent = null;
		Formula newAntecedent = null;
		
		if(this.consequent != null) {
			newConsequent = (Predicate) this.consequent.replaceVariables(substitution);
		}
		
		if(this.antecedent != null) {
			newAntecedent = (Formula) this.antecedent.replaceVariables(substitution);
		}
		
		return new HornClause(newConsequent, newAntecedent);
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
	 */
	@Override
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
		
		// Standardize the Variables in each of these Expressions.
		Predicate newConsequent = null;
		Formula newAntecedent = null;
		
		if(this.consequent != null) {
			newConsequent = (Predicate) this.consequent.standardizeVariablesApart(newVariables);
		}
		
		if(this.antecedent != null) {
			newAntecedent = (Formula) this.antecedent.standardizeVariablesApart(newVariables);
		}
		
		return new HornClause(newConsequent, newAntecedent);
	}

	/**
	 * Returns a String representation of this HornClause, which is a Prolog-style String.
	 * <ul>
	 * 	<li> For facts, the String appears as {@code consequent}.
	 * 	<li> For goal clauses, the String appears as {@code :- antecedent}.
	 * 	<li> For definite clauses, the String appears as {@code consequent :- antecedent}. 
	 * </ul> 
	 * 
	 * @return a String representation of this object.
	 */
	@Override
	public String toString() {
		if(this.isFact()) {
			return consequent.toString();
		}
		
		if(this.isGoalClause()) {
			return ":- " + antecedent.toString();
		}
		
		else {
			return consequent.toString() + " :- " + antecedent.toString();
		}		
	}

}
