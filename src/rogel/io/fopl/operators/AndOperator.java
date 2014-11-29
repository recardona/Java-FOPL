package rogel.io.fopl.operators;

import java.util.ArrayList;

import rogel.io.fopl.Goal;

public class AndOperator extends AbstractLogicalOperator 
{
	public AndOperator(Goal... operands) {
		super(operands);
	}
	
	public AndOperator(ArrayList<Goal> operands) {
		super(operands);
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(AND ");
		
		for(Goal op : this.operands) {
			sb.append(op.toString());
		}
		
		sb.append(")");
		return sb.toString();
	}
}
