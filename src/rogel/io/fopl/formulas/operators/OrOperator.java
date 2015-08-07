package rogel.io.fopl.formulas.operators;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		Formula[] newOperands = new Formula[this.operands.size()];
		for(int operandIndex = 0; operandIndex < this.operands.size(); operandIndex++) {
			newOperands[operandIndex] = (Formula) this.operands.get(operandIndex).replaceVariables(substitution);
		}
				
		return new OrOperator(newOperands);
	}
}
