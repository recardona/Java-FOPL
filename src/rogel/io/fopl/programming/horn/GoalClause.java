package rogel.io.fopl.programming.horn;

import rogel.io.fopl.Expression;

/**
 * A GoalClause is a special kind of Horn Clause that contains no positive
 * literals. Expressions that will appear as goals in an and/or graph
 * must implement this interface. 
 * @author recardona
 */
public interface GoalClause extends Expression {

	
	
}
