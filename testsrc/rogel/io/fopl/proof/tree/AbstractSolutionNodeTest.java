package rogel.io.fopl.proof.tree;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.formulas.operators.AndOperator;
import rogel.io.fopl.proof.HornClause;
import rogel.io.fopl.proof.RuleSet;
import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Variable;

public class AbstractSolutionNodeTest {

	Function bill, audrey, maria, joe, charles;
	Variable X, Y, Z;
	Predicate ancestor, parent;
	RuleSet rules;
	
	@Before
	public void setUp() throws Exception {

		bill = new Function("bill");
		audrey = new Function("audrey");
		maria = new Function("maria");
		joe = new Function("joe");
		charles = new Function("charles");
		X = new Variable("X");
		Y = new Variable("Y");
		Z = new Variable("Z");
		
		rules = new RuleSet(
		new HornClause(new Predicate("parent", bill, audrey)),	// Bill is Audrey's parent.
		new HornClause(new Predicate("parent", maria, bill)), 	// Maria is Bill's parent.
		new HornClause(new Predicate("parent", joe, maria)),	// Joe is Maria's parent.
		new HornClause(new Predicate("parent", charles, joe)),	// Charles is Joe's parent.
		new HornClause(
						new Predicate("ancestor", X, Y), /* <- */ 	// If x is a parent of y,
						new AndOperator(
								new Predicate("parent", X, Y)				// then x is an ancestor of y.
								)
					),
		new HornClause(
						new Predicate("ancestor", X, Y),  /* <- */	// If x is a parent of z,
						new AndOperator(							// and z is an ancestor of y,
							new Predicate("parent", X, Z), /* ^ */  // then x is an ancestor of y.
							new Predicate("ancestor", Z, Y)
							)
						)
		);
	}

	@Test
	public void testNextSolution() {
		
		// Define the resolution goal.
		Predicate goal = new Predicate("ancestor", charles, Y); // who is Charles an ancestor of?
		
		// Define the root of the search space.
		Substitution identity = new Substitution();
		AbstractSolutionNode root = SolutionNodeFactory.getSolver(goal, rules, identity);
		
		// Solution
		Substitution solution;
		
		// Solutions
		List<Substitution> solutionList = new ArrayList<Substitution>();
		
		// Expressions
		List<Expression> expressionsList = new ArrayList<Expression>();
		
		// Find all the solutions!
		while( (solution = root.nextSolution()) != null) {
			solutionList.add(solution);
			expressionsList.add(goal.replaceVariables(solution));
			System.out.println(goal.replaceVariables(solution));
		}
		
		assertEquals("There should be four solutions to the query.", 4, solutionList.size());
		
		
	}

}
