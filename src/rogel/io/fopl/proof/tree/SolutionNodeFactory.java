package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.RuleSet;

/**
 * The SolutionNodeFactory provides instances of AbstractSolutionNodes as needed for proof tree
 * construction during resolution. 
 * 
 * @author recardona
 */
public class SolutionNodeFactory {

	private SolutionNodeFactory() { /* Private constructor to avoid initialization. */ }
	
	/**
	 * This method generates a new child goal node appropriate for the parameter goal Formula:
	 * <ul>
	 * 	<li> If the Formula is a Predicate, this method returns a PredicateSolutionNode.
	 * 	<li> If the Formula is an AndOperator, this method returns an AndSolutionNode.
	 * </ul>
	 * 
	 * @param goal The Formula for which a solution node is desired, not null.
	 * @param rules The RuleSet that defines the logical basis used for resolution, not null.
	 * @param parentSolution The Substitution solution that exists prior to the creation of this
	 *  node, not null.
	 * @return an AbstractSolutionNode appropriate for the type of the parameter goal Formula.
	 */
	public static AbstractSolutionNode getSolver(Formula goal, RuleSet rules, Substitution parentSolution) {
		
		if(goal instanceof Predicate) {
			return new PredicateSolutionNode((Predicate) goal, rules, parentSolution); 
		}
		
		if(goal instanceof AndOperator) {
			return new AndSolutionNode((AndOperator) goal, rules, parentSolution);
		}
		
		// TODO:
//		if(goal instanceof OrOperator) {
//			 return new OrSolutionNode((OrOperator) goal, rules, parentSolution);
//		}
//		
//		if(goal instanceof NotOperator) {
//			 return new NotSolutionNode((NotOperator) goal, rules, parentSolution);
//		}
		
		// ERROR:
		throw new IllegalArgumentException("Goal " +goal.toString()+ " is of unrecognized type for the solver.");		
	}
	
}
