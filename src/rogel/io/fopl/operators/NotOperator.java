package rogel.io.fopl.operators;

import java.util.ArrayList;

import rogel.io.fopl.Goal;

public class NotOperator extends AbstractLogicalOperator 
{
	public NotOperator(Goal... operands) {
		super(operands);
	}
	
	public NotOperator(ArrayList<Goal> operands) {
		super(operands);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(NOT ");
		
		for(Goal op : this.operands) {
			sb.append(op.toString());
		}
		
		sb.append(")");
		return sb.toString();
	}
}
