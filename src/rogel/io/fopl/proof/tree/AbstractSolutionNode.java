package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;

/**
 * An AbstractSolutionNode is the building-block for logical problem solving. This class defines 
 * the basic functionality for every node in a resolution proof tree. The AbstractSolutionNode 
 * allows for the traversal of the and/or graph used during proof construction. Proof trees are 
 * implemented as <a href="https://en.wikipedia.org/wiki/Continuation">continuations</a> and this
 * class is designed to reflect that.
 * 
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
	 * 
	 * @param rules The RuleSet that defines the logical basis used for resolution, not null.
	 * @param parentSolution The Substitution solution that exists prior to the creation of this
	 *  node, not null.
	 */
	protected AbstractSolutionNode(RuleSet rules, Substitution parentSolution) {
		this.ruleNumber = 0;
		this.rules = rules;
		this.parentSolution = parentSolution;
		this.currentRule = null;
	}
	
	/**
	 * Computes the next solution to this node, if it exists.
	 * 
	 * @return the Substitution that serves as the next solution to this node.
	 */
	public abstract Substitution nextSolution();
	
	/**
	 * Gets the next rule in the RuleSet, with variables standardized apart.
	 * 
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
	 * Gets the RuleSet that defines the logical basis for resolution.
	 * 
	 * @return the RuleSet for resolution.
	 */
	public RuleSet getRules() {
		return this.rules;
	}

	/**
	 * Gets the Substitution object that existed prior to the creation of this node.
	 * 
	 * @return the parentSolution the Substitution that exited prior to the creation of this node.
	 */
	public Substitution getParentSolution() {
		return this.parentSolution;
	}
	
	/**
	 * Gets the HornClause that is currently being used as the goal for resolution at this node.
	 * 
	 * @return the HornClause under consideration at this node.
	 */
	public HornClause getCurrentRule() {
		return this.currentRule;
	}

	/**
	 * Resets the state of a solution node to that of a newly created node. This allows this node
	 * to effectively serve again during proof tree construction when picked up during continuation.
	 * 
	 * @param newParentSubstitution the new solution Substitution to set.
	 */
	protected void reset(Substitution newParentSubstitution) {
		this.parentSolution = newParentSubstitution;
		this.ruleNumber = 0;
	}
	
	/**
	 * Returns true if and only if the current index of the RuleSet HornClause rule is less than
	 * the total number of rules in the RuleSet. 
	 * 
	 * @return true if the current HornClause index is less than the total number of rules in the 
	 * 	RuleSet.
	 */
	protected boolean hasNextRule() {
		return (this.ruleNumber < this.rules.getRuleCount());
	}
	
}
