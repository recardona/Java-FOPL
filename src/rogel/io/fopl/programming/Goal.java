package rogel.io.fopl.programming;

import rogel.io.fopl.Expression;

/**
 * A Goal is an object that may appear as part of a goal clause within a HornClause.
 * Goal clauses contains no positive literals. Expressions that will appear as goals
 * in an and/or graph must implement this interface. 
 * @author recardona
 */
public interface Goal extends Expression {

	
	
}
