package rogel.io.fopl.formulas.operators;

import java.util.HashMap;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.terms.Variable;

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
	 * {@code !formula.getValue()}.
	 * @param operand this operator's operand
	 */
	public NotOperator(Formula operand) {
		this(operand, false);
	}

	/**
	 * Constructs a NotOperator over the operand Formula. The value of this operator is 
	 * {@code !formula.getValue()} if {@code preserveOperandValue == false}, and is
	 * {@code formula.getValue()} otherwise. 
	 * @param operand this operator's operand.
	 * @param preserveOperandValue whether we should preserve the operand's value or invert it.
	 */
	private NotOperator(Formula operand, boolean preserveOperandValue) {
		super("not", operand);
		this.operand = this.operands.get(0);
		
		// If we are preserving the operand value (for example, when using this constructor as a 
		// copy-constructor), we do not invert the value.
		if(preserveOperandValue == true) {
			this.value = this.operand.getValue();
		}
		
		else {
			this.value = !this.operand.getValue();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.Formula#isLiteral()
	 */
	@Override
	public boolean isLiteral() {
		
		// In FOPL, a literal is an atomic Formula or its negation. For a NegatedFormula to be a 
		// literal, the Formula it describes must be atomic.
		if(this.operand.isAtomic()) {
			return true;
		}

		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Calling replaceVariables on Formulas will return Formula-type Expressions. Thus, get the
		// Formula this NotOperator describes, replace its Variables and create a new NotOperator 
		// out of it.
		Formula replacedVariableFormula = (Formula) this.operand.replaceVariables(substitution);		
		return new NotOperator(replacedVariableFormula, true);
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
	 */
	@Override
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
		
		// Standardize Variables for all the operands and use them to create a new NotOperator.
		// Create an empty placeholder for the new operands.
		Formula standardizedVariableFormula = (Formula) this.operand.standardizeVariablesApart(newVariables);
		return new NotOperator(standardizedVariableFormula, true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(not " + this.operand + ")";
	}
}
