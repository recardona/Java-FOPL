package rogel.io.fopl.proof.tree;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.RuleSet;

public class AndSolutionNode extends AbstractSolutionNode {

	protected AndSolutionNode(AndOperator goal, RuleSet rules, Substitution parentSolution) {
		super(rules, parentSolution);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Substitution nextSolution() {
		// TODO Auto-generated method stub
		return null;
	}

}
