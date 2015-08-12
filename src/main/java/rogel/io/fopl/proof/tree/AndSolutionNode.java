package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.operators.AbstractOperator;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.RuleSet;

/**
 * The AndSolutionNode is an AbstractSolutionNode for AndOperators. Because in our implementation,
 * AndOperators are n-ary, this class must handle resolution for each of the individual arguments.
 * This class is implemented recursively, getting the head of the n-ary arguments, and requesting
 * solutions for the tail until the operands are exhausted.
 * 
 * @author recardona
 */
public class AndSolutionNode extends AbstractSolutionNode {

	/** The solution node for the first argument of the AndOperator this node represents. */
	private AbstractSolutionNode headSolutionNode = null;
	
	/** The tail arguments of the AndOperator this node represents.*/
	private AbstractOperator operatorTail = null;
	
	/** The solution node for the tail arguments of the AndOperator this node represents. */
	private AbstractSolutionNode tailSolutionNode = null;
	
	/**
	 * Constructor for the AndSolutionNode.
	 * 
	 * @param goal The goal for this node, not null.
	 * @param rules The RuleSet that defines the logical basis used for resolution, not null.
	 * @param parentSolution The Substitution solution that exists prior to the creation of this
	 *  node, not null.
	 */
	protected AndSolutionNode(AndOperator goal, RuleSet rules, Substitution parentSolution) {
		super(rules, parentSolution);
		this.headSolutionNode = SolutionNodeFactory.getSolver(goal.getOperatorHead(), rules, parentSolution);
		this.operatorTail = goal.getOperatorTail(); // store the operator tail to create the tailSolutionNode lazily.
		this.tailSolutionNode = null;
	}

	/**
	 * Gets this AndSolutionNode's head solution node; i.e. the solution node for the Formula at
	 * the head of this node's goal AndOperator.
	 * 
	 * @return the AbstractSolutionNode for the Formula at the head of this node's goal AndOperator.
	 */
	public AbstractSolutionNode getHeadSolutionNode() {
		return this.headSolutionNode;
	}

	/**
	 * Gets this AndSolutionNode's tail solution node; i.e. the solution node for the Formula at 
	 * the tail of this node's goal AndOperator.
	 * 
	 * @return the tailSolutionNode the AbstractSolutionNode for the Formula at the tail of this
	 * 	node's goal AndOperator.
	 */
	public AbstractSolutionNode getTailSolutionNode() {
		return this.tailSolutionNode;
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.proof.tree.AbstractSolutionNode#nextSolution()
	 */
	@Override
	public Substitution nextSolution() {
		
		Substitution solution;
		
		// First check if the child is not null (which could
		// be the case if we are resuming a previous search).
		if(this.tailSolutionNode != null) {
			
			// See if there are any more solutions
			// in this branch of the search space.
			solution = this.tailSolutionNode.nextSolution();
			
			// If the solution in this branch is not null, 
			// return it.
			if(solution != null) {
				return solution;
			}
		}
		
		// We must test for further solutions to the head goal.
		while( (solution = this.headSolutionNode.nextSolution()) != null) {
			
			// If there isn't a tail to the head, we can return the solution we find.
			if(this.operatorTail == null || this.operatorTail.isEmpty()) {
				return solution;
			}
			
			// Otherwise, we need to find the solution to the tail as well.
			else {
				this.tailSolutionNode = SolutionNodeFactory.getSolver(this.operatorTail, rules, solution);
				Substitution tailSolution = this.tailSolutionNode.nextSolution();
				
				if(tailSolution != null) {
					return tailSolution;
				}
			}
		}
		
		return null; // There is no next solution for the resolution problem!
	}

}
