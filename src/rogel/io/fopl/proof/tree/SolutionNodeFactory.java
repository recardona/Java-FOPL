package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.RuleSet;

/**
 * The SolutionNodeFactory provides instances of AbstractSolutionNodes as needed during proof 
 * tree construction. 
 * @author recardona
 */
public class SolutionNodeFactory {

	private SolutionNodeFactory() { /* Private constructor to avoid initialization. */ }
	
	/**
	 * This method generates a new child goal node appropriate for the parameter goal Formula:
	 * <ul>
	 * 	<li> If the Formula is a Predicate, this method returns a PredicateSolutionNode.
	 * 	<li> If the Formula is an AndOperator, this method returns an AndSolutionNode.
	 * 	<li> If the Formula is an OrOperator, this method returns an OrSolutionNode.
	 * 	<li> If the Formula is a NotOperator, this method returns a NotSolutionNode.
	 * </ul>
	 * @param goal the Formula for which a solution node is desired.
	 * @param rules the logical basis for resolution.
	 * @param parentSolution the solution that exists prior to the creation of this node.
	 * @return an AbstractSolutionNode appropriate for the type of the parameter goal Formula.
	 */
	public static AbstractSolutionNode getSolver(Formula goal, RuleSet rules, Substitution parentSolution) {
		
		if(goal instanceof Predicate) {
			return new PredicateSolutionNode((Predicate) goal, rules, parentSolution); 
		}
		
		if(goal instanceof AndOperator) {
			return new AndSolutionNode((AndOperator) goal, rules, parentSolution);
		}
		
		// ERROR
		return null;
	}
	
}
