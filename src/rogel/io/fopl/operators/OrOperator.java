package rogel.io.fopl.operators;

import java.util.ArrayList;

import rogel.io.fopl.Goal;

public class OrOperator extends AbstractLogicalOperator 
{
	public OrOperator(Goal... operands) {
		super(operands);
	}
	
	public OrOperator(ArrayList<Goal> operands) {
		super(operands);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(OR ");
		
		for(Goal op : this.operands) {
			sb.append(op.toString());
		}
		
		sb.append(")");
		return sb.toString();
	}
}
