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
 * @author recardona
 */
public class AndSolutionNode extends AbstractSolutionNode {

	// TODO: DOCUMENTATION NEEDED.
	private AbstractSolutionNode headSolutionNode = null;
	private AbstractSolutionNode tailSolutionNode = null;
	private AbstractOperator operatorTail = null;
	
	/**
	 * Constructor for the AndSolutionNode.
	 * @param goal the goal for this node.
	 * @param rules the logical basis for resolution.
	 * @param parentSolution the solution that exists prior to the creation of this node.
	 */
	protected AndSolutionNode(AndOperator goal, RuleSet rules, Substitution parentSolution) {
		super(rules, parentSolution);
		this.headSolutionNode = SolutionNodeFactory.getSolver(goal.getOperatorHead(), rules, parentSolution);
		this.tailSolutionNode = null;
		this.operatorTail = goal.getOperatorTail();
	}

	/**
	 * @return the headSolutionNode
	 */
	public AbstractSolutionNode getHeadSolutionNode() {
		return this.headSolutionNode;
	}

	/**
	 * @return the tailSolutionNode
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
		
		// TODO: DOCUMENTATION NEEDED

		Substitution solution;
		
		if(this.tailSolutionNode != null) {
			solution = this.tailSolutionNode.nextSolution();
			
			if(solution != null) {
				return solution;
			}
		}
		
		while( (solution = this.headSolutionNode.nextSolution()) != null) {
			
			if(this.operatorTail == null || this.operatorTail.isEmpty()) {
				return solution;
			}
			
			else {
				this.tailSolutionNode = SolutionNodeFactory.getSolver(this.operatorTail, rules, solution);
				Substitution tailSolution = this.tailSolutionNode.nextSolution();
				
				if(tailSolution != null) {
					return tailSolution;
				}
			}
		}
		
		return null;
	}

}
