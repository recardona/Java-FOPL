package rogel.io.fopl.terms;

import rogel.io.fopl.PredicateCalculusExpression;
import rogel.io.fopl.SubstitutionSet;
import rogel.io.fopl.Unifiable;

public class Constant implements Unifiable 
{
	private static int nextID = 1;
	
	private int id;
	private String printName = null;
	
	public Constant() {
		this.id = Constant.nextID; //assign it a new ID
		Constant.nextID++;
	}
	
	
	public Constant(String printName) {
		this();
		this.printName = printName;
	}
	
	
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	
	
	@Override
	public String toString() {
		if(this.printName != null) {
			return this.printName;
		}
		
		else {
			return "constant_"+this.id;
		}
	}
	
	
	@Override
	public SubstitutionSet unify(Unifiable expression, SubstitutionSet substitutionSet) 
	{
		if(this == expression) {
			return new SubstitutionSet(substitutionSet);
		}
			
		if(expression instanceof Variable) {
			return expression.unify(this, substitutionSet);
			//although exp1.unify(exp2,s) and exp2.unify(exp1,s) is equivalent,
			//when we encounter a variable, we need to do variable unification
			//so we delegate the unification to the expression
		}
			
		return null; //we can only unify variables
	}
	
	
	@Override
	public PredicateCalculusExpression replaceVariables(SubstitutionSet substitutionSet) {
		return this; //a constant can't be bound to anything! no variables to replace
	}

}
