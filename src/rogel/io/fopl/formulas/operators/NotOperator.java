package rogel.io.fopl.formulas.operators;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

/**
 * A NotOperator is an AbstractOperator Formula that computes the logical opposite of its 
 * constituent Formula as its value.
 * @author recardona
 */
public class NotOperator extends AbstractOperator {
	
	/** This is the Formula this operator negates. */
	private Formula operand;
	
	/**
	 * Constructs a NotOperator over the operand Formula. The value of this operator is 
	 * {@code !formula.getValue()}
	 * @param operand this operator's operand
	 */
	public NotOperator(Formula operand) {
		super("not", operand);
		this.operand = this.operands.get(0);
		this.value = !this.operand.getValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(not " + this.operand + ")";
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Calling replaceVariables on Formulas will return Formula-type 
		// Expressions. Thus, get the Formula this NegatedFormula describes, 
		// replace its Variables and create a new NegatedFormula out of it.
		Formula replacedVariableFormula = (Formula) this.operand.replaceVariables(substitution);
		return new NotOperator(replacedVariableFormula);
	}

	@Override
	public boolean isLiteral() {
		
		// In FOPL, a literal is an atomic Formula or its negation.
		// For a NegatedFormula to be a literal, the Formula it
		// describes must be atomic.
		if(this.operand.isAtomic()) {
			return true;
		}

		return false;
	}
}
