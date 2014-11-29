package rogel.io.fopl.operators;

import java.util.ArrayList;

import rogel.io.fopl.Goal;
import rogel.io.fopl.PredicateCalculusExpression;
import rogel.io.fopl.SubstitutionSet;

/**
 * Defines the basic methods for accessing the arguments of n-ary operators. 
 */
public abstract class AbstractLogicalOperator implements Goal, Cloneable 
{
	protected ArrayList<Goal> operands;
	
	/**
	 * Constructor for a Logical Operator.
	 * @param operands a varargs list of Goals
	 */
	public AbstractLogicalOperator(Goal... operands)
	{
		this.operands = new ArrayList<Goal>();
		
		Goal[] operandArray = operands;
		for(int operandIndex = 0; operandIndex < operandArray.length; operandIndex++)
		{
			this.operands.add(operandArray[operandIndex]);
		}
	}
	
	
	/**
	 * Constructor for a Logical Operator. 
	 * @param operands an ArrayList of Goals
	 */
	public AbstractLogicalOperator(ArrayList<Goal> operands) {
		this.operands = operands;
	}

	
	/**
	 * @param operands the operands to set for <b>this</b> operator
	 */
	public void setOperands(ArrayList<Goal> operands) {
		this.operands = operands;
	}
	
	
	/**
	 * @return the number of operands for <b>this</b> operator
	 */
	public int operandCount() {
		return this.operands.size();
	}
	
	
	/**
	 * @return whether or not the operands list is empty for <b>this</b> operator
	 */
	public boolean isEmpty() {
		return this.operands.isEmpty();
	}
	
	
	/**
	 * Gets the operands of <b>this</b> operator
	 * @param i the index of the operator to get
	 * @return the operator at the parameter index
	 */
	public Goal getOperand(int i) {
		return this.operands.get(i);
	}
	
	
	/**
	 * @return the first operand of <b>this</b> operator
	 */
	public Goal getOperatorHead() {
		return this.operands.get(0);
	}
	
	
	/**
	 * Copies and returns the tail of <b>this</b> operator (defined as everything
	 * except the element returned by <code>getOperatorHead()</code>).
	 * @return a new AbstractLogicalOperator containing the tail of <b>this</b> operator
	 * @throws CloneNotSupportedException in the event the clone method is not implemented
	 */
	public AbstractLogicalOperator getOperatorTail() throws CloneNotSupportedException 
	{
		ArrayList<Goal> tail = new ArrayList<Goal>(this.operands);
		tail.remove(0);
		AbstractLogicalOperator tailOperator = (AbstractLogicalOperator) this.clone();
		tailOperator.setOperands(tail);
		return tailOperator;
	}
	
	
	@Override
	public PredicateCalculusExpression replaceVariables(SubstitutionSet substitutionSet) throws CloneNotSupportedException
	{
		ArrayList<Goal> operandsWithReplacedVariables = new ArrayList<Goal>();
		for(int operandsIndex = 0; operandsIndex < this.operandCount(); operandsIndex++)
		{
			Goal operand = this.getOperand(operandsIndex);
			operand = (Goal) operand.replaceVariables(substitutionSet);
			operandsWithReplacedVariables.add(operand);
		}
		
		AbstractLogicalOperator copy = (AbstractLogicalOperator) this.clone();
		copy.setOperands(operandsWithReplacedVariables);
		return copy;
	}
		
}
