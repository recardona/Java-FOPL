package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;

/**
 * This class defines the basic functionality for every node in a proof tree. The
 * AbstractSolutionNode allows for the traversal of the and/or graph used during proof construction.
 * @author recardona
 */
public abstract class AbstractSolutionNode {

	/** The HornClauses used by all nodes in the proof tree. */
	private RuleSet rules;
	
	/** The current rule under consideration for the proof. */
	private HornClause currentRule;
	
	/** The goal to solve in this node. */
	private Formula goal;
	
	/** The solution to this node's parent. */
	private Substitution parentSolution;
	
	/** The index of the current rule under consideration for solving the goal. */
	private int ruleNumber;
	
	/**
	 * Constructs an AbstractSolutionNode.
	 * @param goal the goal to match for this node.
	 * @param rules the logical basis used for theorem proving.
	 * @param parentSolution the solution to this node's parent.
	 */
	protected AbstractSolutionNode(Formula goal, RuleSet rules, Substitution parentSolution) {
		this.rules = rules;
		this.parentSolution = parentSolution;
		this.goal = goal;
		this.currentRule = null;
		this.ruleNumber = 0;
	}
	
	/**
	 * Computes the next solution to this node, if it exists.
	 * @return the Substitution that serves as the next solution to this node.
	 */
	public abstract Substitution nextSolution();
	
	/**
	 * Resets the proof Substitution to the parameter and the rule number to zero.
	 * @param newParentSubstitution the new solution to set.
	 */
	protected void reset(Substitution newParentSubstitution) {
		this.parentSolution = newParentSubstitution;
		this.ruleNumber = 0;
	}
	
}
