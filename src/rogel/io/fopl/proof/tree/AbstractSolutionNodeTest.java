package rogel.io.fopl.proof.tree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;
import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Variable;

public class AbstractSolutionNodeTest {

	Function cBill, cAudrey, cMaria, cJoe, cCharles;
	Variable x, y, z;
	Predicate ancestor, parent;
	RuleSet rules;
	
	@Before
	public void setUp() throws Exception {

		cBill = new Function("bill");
		cAudrey = new Function("audrey");
		cMaria = new Function("maria");
		cJoe = new Function("joe");
		cCharles = new Function("charles");
		x = new Variable("X");
		y = new Variable("Y");
		z = new Variable("Z");
		
		
		rules = new RuleSet(
		new HornClause(new Predicate("parent", cBill, cAudrey)),	// Bill is Audrey's parent.
		new HornClause(new Predicate("parent", cMaria, cBill)), 	// Maria is Bill's parent.
		new HornClause(new Predicate("parent", cJoe, cMaria)),		// Joe is Maria's parent.
		new HornClause(new Predicate("parent", cCharles, cJoe)),	// Charles is Joe's parent.
		new HornClause(
						new Predicate("ancestor", x, y), /* <- */ 	// If x is a parent of y,
						new Predicate("parent", x, y)				// then x is an ancestor of y.
					),
		new HornClause(
						new Predicate("ancestor", x, y),  /* <- */	// If x is a parent of z,
						new AndOperator(							// and z is an ancestor of y,
							new Predicate("parent", x, z), /* ^ */  // then x is an ancestor of y.
							new Predicate("ancestor", z, y)
							)
						)
		);
	}

	@Test
	public void testNextSolution() {
		
		// Define the resolution goal.
		Predicate goal = new Predicate("ancestor", cCharles, y); // who is Charles an ancestor of?
		
		// Define the root of the search space.
		Substitution identity = new Substitution();
		AbstractSolutionNode root = SolutionNodeFactory.getSolver(goal, rules, identity);
		
		// Solution:
		Substitution solution;
		
		// Find all the solutions!
		while( (solution = root.nextSolution()) != null) {
			System.out.println("\t" + goal.replaceVariables(solution));
		}
		
		
	}

}
