package rogel.io.fopl.formulas.operators;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

/**
 * An AndOperator is an AbstractOperator Formula that computes the logical conjunction of its 
 * constituent Formulas as its value. 
 * @author recardona
 */
public class AndOperator extends AbstractOperator {

	/**
	 * Constructs an AndOperator over the operand Formulas. The value of this operator is the
	 * logical conjunction of all the operands. 
	 * @param operands the operands of this operator.
	 */
	public AndOperator(Formula... operands) {
		super("and", operands);
		this.value = true; // start true
		for(Formula operand : this.operands) {
			this.value &= operand.getValue();
			
			if(this.value == false) {
				break; // if we ever become false, break
			}
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(and");
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
				
		return new AndOperator(newOperands);
	}
}
