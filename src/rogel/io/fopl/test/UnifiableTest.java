package rogel.io.fopl.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.SimpleSentence;
import rogel.io.fopl.SubstitutionSet;
import rogel.io.fopl.Unifiable;
import rogel.io.fopl.terms.Constant;
import rogel.io.fopl.terms.Variable;

public class UnifiableTest {

	ArrayList<Unifiable> expressions;
	Constant friend, bill, george, kate, merry;
	Variable X, Y;
	
	@Before
	public void setUp() throws Exception 
	{
		friend = new Constant("friend");
		bill = new Constant("bill");
		george = new Constant("george");
		kate = new Constant("kate");
		merry = new Constant("merry");
		
		X = new Variable("X");
		Y = new Variable("Y");
		
		expressions = new ArrayList<Unifiable>();
		expressions.add(new SimpleSentence(friend, bill, george)); //(friend bill george) 
		expressions.add(new SimpleSentence(friend, bill, kate));   //(friend bill kate)
		expressions.add(new SimpleSentence(friend, bill, merry));  //(friend bill merry)
		expressions.add(new SimpleSentence(friend, george, bill)); //(friend george bill)
		expressions.add(new SimpleSentence(friend, george, kate)); //(friend george kate)
		expressions.add(new SimpleSentence(friend, kate, merry));  //(friend kate merry)
	}

	@Test
	public void testUnify() {
		
		Unifiable goal = new SimpleSentence(friend, X, Y); //(friend X Y)
		SubstitutionSet solutionSet;
		System.out.println("Goal = " + goal); 
		for(Unifiable expression : this.expressions) {
			solutionSet = expression.unify(goal, new SubstitutionSet());
			
			if(solutionSet != null)
				System.out.println(goal.replaceVariables(solutionSet));
			
			else
				System.out.println("False");
		}		
	}
	
	@Test
	public void testUnify2() {
		
		Unifiable goal = new SimpleSentence(friend, bill, X); //(friend bill Y)
		SubstitutionSet solutionSet;
		System.out.println("Goal = " + goal);
		for(Unifiable expression : this.expressions) {
			solutionSet = expression.unify(goal, new SubstitutionSet());
			
			if(solutionSet != null)
				System.out.println(goal.replaceVariables(solutionSet));
			
			else
				System.out.println("False");
		}
		
	}

}
