package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;

/**
 * An AbstractSolutionNode is the building-block for logical problem solving. This class defines 
 * the basic functionality for every node in a proof tree. The AbstractSolutionNode allows for the
 * traversal of the and/or graph used during proof construction. Proof trees are implemented as 
 * <a href="https://en.wikipedia.org/wiki/Continuation">continuations</a>, and this class is
 * designed to reflect that.
 * @author recardona
 */
public abstract class AbstractSolutionNode {

	// The variables 'ruleNumber', 'rules', and 'parentSolution' allow the node to resume the
	// search process where it left off during proof tree construction, as defined in the
	// continuation pattern.
	
	/** The index of the current rule under consideration for solving the goal. */
	private int ruleNumber;
	
	/** The HornClauses used by all nodes in the proof tree. */
	protected RuleSet rules;

	/** The Substitution as it existed when this node was created. */
	protected Substitution parentSolution;
	
	/** The current rule under consideration for the proof. */
	private HornClause currentRule;
	
	/**
	 * Constructs an AbstractSolutionNode.
	 * @param rules the logical basis used for resolution.
	 * @param parentSolution the solution that exists prior to the creation of this node.
	 */
	protected AbstractSolutionNode(RuleSet rules, Substitution parentSolution) {
		this.ruleNumber = 0;
		this.rules = rules;
		this.parentSolution = parentSolution;
		this.currentRule = null;
	}
	
	/**
	 * Computes the next solution to this node, if it exists.
	 * @return the Substitution that serves as the next solution to this node.
	 */
	public abstract Substitution nextSolution();
	
	/**
	 * Gets the next rule in the RuleSet, with variables standardized apart.
	 * @return the next rule in the RuleSet.
	 */
	public HornClause nextRule() {
		if(this.hasNextRule()) {
			this.currentRule = this.rules.getRuleStandardizedApart(this.ruleNumber);
			this.ruleNumber++;
		}
		
		else {
			this.currentRule = null;
		}
		
		return this.currentRule;
	}

	/**
	 * @return the rules
	 */
	public RuleSet getRules() {
		return rules;
	}

	/**
	 * @return the parentSolution
	 */
	public Substitution getParentSolution() {
		return parentSolution;
	}
	
	/**
	 * @return the currentRule
	 */
	public HornClause getCurrentRule() {
		return currentRule;
	}

	/**
	 * Resets the state of a solution node to that of a newly created node. This allows this node
	 * to effectively serve again during proof tree construction when picked up during continuation.
	 * @param newParentSubstitution the new solution Substitution to set.
	 */
	protected void reset(Substitution newParentSubstitution) {
		this.parentSolution = newParentSubstitution;
		this.ruleNumber = 0;
	}
	
	/**
	 * @return true if the index we're at is less than the total number of rules in the RuleSet.
	 */
	protected boolean hasNextRule() {
		return (this.ruleNumber < this.rules.getRuleCount());
	}
	
}
