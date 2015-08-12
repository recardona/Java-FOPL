package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;

/**
 * The PredicateSolutionNode is an AbstractSolutionNode for Predicate Formulas.
 * 
 * @author recardona
 */
public class PredicateSolutionNode extends AbstractSolutionNode {

	/** The goal being solved at this node. */
	private Predicate goal;
	
	/** The next node (subgoal) in this proof tree search space. */
	private AbstractSolutionNode child;
		
	/**
	 * Constructor for the PredicateSolutionNode.
	 * 
	 * @param goal The Predicate resolution goal for this node.
	 * @param rules The RuleSet that defines the logical basis used for resolution, not null.
	 * @param parentSolution The Substitution solution that exists prior to the creation of this
	 *  node, not null.
	 */
	public PredicateSolutionNode(Predicate goal, RuleSet rules, Substitution parentSolution) {
		super(rules, parentSolution);
		this.goal = goal;
		this.child = null;
	}
	
	/**
	 * The Predicate that represents the resolution goal for this node.
	 * 
	 * @return the goal for this node.
	 */
	public Predicate getGoal() {
		return this.goal;
	}
	
	/**
	 * Gets the next node (subgoal) in this proof tree search space.
	 * 
	 * @return the next node (subgoal) in this proof tree search space.
	 */
	public AbstractSolutionNode getChild() {
		return this.child;
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
		if(this.child != null) {
			
			// See if there are any more solutions  
			// in this branch of the search space.
			solution = this.child.nextSolution();
			
			if(solution != null) {
				return solution;
			}
		}
		
		// If no solution has not been found yet, 
		// we begin trying each rule in the RuleSet. 
		this.child = null;
		HornClause rule;
		
		while(this.hasNextRule()) {
			
			// Get the next rule to try.
			rule = this.nextRule();
			
			// Get the rule's head (consequent).
			Predicate head = rule.getConsequent();
			
			// See if we can unify the head of the rule with 
			// the goal of this node.
			solution = this.goal.unify(head, super.parentSolution);
			
			// If there is a solution,
			if(solution != null) {
				
				// Now we need to check whether we need to continue solving for a 
				// rule tail, which is only the case if the HornClause is not a fact.
				if(! rule.isFact()) {
					
					// We need to continue solving recursively for the child, whose Substitution 
					// solution must be consistent with what we have thus far.	
					Formula tail = rule.getAntecedent();
					this.child = SolutionNodeFactory.getSolver(tail, super.rules, solution);
					Substitution childSolution = this.child.nextSolution();
					
					// If the child's solution is not null, return it.
					if(childSolution != null) {
						return childSolution;
					}
				}
				
				else {
					// If there's nothing else to solve for, return the solution.
					return solution;
				}
			}
		}
		
		return null; // There is no next solution for the resolution problem!
	}
}
