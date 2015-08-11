package rogel.io.fopl.formulas.operators;

import java.util.HashMap;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.terms.Variable;

/**
 * An OrOperator is an AbstractOperator Formula that computes the logical disjunction of its 
 * constituent Formulas as its value. 
 * @author recardona
 */
public class OrOperator extends AbstractOperator {

	/**
	 * Constructs an OrOperator over the operand Formulas. The value of this operator is the 
	 * logical disjunction of all the operands.
	 * @param operands the operands of this operator.
	 */
	public OrOperator(Formula... operands) {
		super("or", operands);
		this.value = false; // start false
		for(Formula operand : this.operands) {
			this.value |= operand.getValue();
			
			if(this.value == true) {
				break; // if we ever become true, break
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.Formula#isLiteral()
	 */
	@Override
	public boolean isLiteral() {
		return false; // OrOperators are never literals.
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.operators.AbstractOperator#getOperatorTail()
	 */
	@Override
	public AbstractOperator getOperatorTail() {
		
		// Create the tail operands array, of size-1.
		Formula[] tailOperands = new Formula[this.operands.size()-1];
		
		// Copy all but the first Formula into this array.
		for(int operandIndex = 1; operandIndex < this.operands.size(); operandIndex++) {
			tailOperands[operandIndex] = this.operands.get(operandIndex);
		}
		
		// Create a new OrOperator with this array.
		return new OrOperator(tailOperands);
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Replace Variables for all the operands and use them to create a new OrOperator.
		// Create an empty placeholder for the new operands.
		Formula[] newOperands = new Formula[this.operands.size()];
		
		// For each operand, replace its variables with the substitution and save it
		for(int operandIndex = 0; operandIndex < this.operands.size(); operandIndex++) {
			Formula operand = this.operands.get(operandIndex);
			Expression replacedVariableExpression = operand.replaceVariables(substitution);
			newOperands[operandIndex] = (Formula) replacedVariableExpression;
		}
				
		return new OrOperator(newOperands);
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
	 */
	@Override
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
		
		// Standardize Variables for all the operands and use them to create a new OrOperator.
		// Create an empty placeholder for the new operands.
		Formula[] newOperands = new Formula[this.operands.size()];
		
		// For each operand, replace its variables with the substitution and save it.
		for(int operandIndex = 0; operandIndex < this.operands.size(); operandIndex++) {
			Formula operand = this.operands.get(operandIndex);
			Expression standardizedVariableExpression = operand.standardizeVariablesApart(newVariables);
			newOperands[operandIndex] = (Formula) standardizedVariableExpression;
		}
		
		return new OrOperator(newOperands);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(or");
		for(Formula operand : this.operands) {
			builder.append(" ");
			builder.append(operand.toString());
		}
		builder.append(")");
		return builder.toString();
	}
}
