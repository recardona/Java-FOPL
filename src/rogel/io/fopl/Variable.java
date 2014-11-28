package rogel.io.fopl;

public class Variable implements Unifiable 
{
	private static int nextID = 1;
	
	private int id;
	private String printName = null;
	
	public Variable() {
		this.id = Variable.nextID; //assign it a new id
		Variable.nextID++;
	}
	
	
	public Variable(String printName) {
		this();
		this.printName = printName;
	}
	
	
	public Variable(Variable v) {
		this();
		this.printName = v.printName;
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
			return this.printName + "_" + this.id;
		}
		
		return "V"+this.id;
	}
	
	
	@Override
	public SubstitutionSet unify(Unifiable expression, SubstitutionSet substitutionSet) 
	{
		if(this == expression) {
			return substitutionSet;
		}
		
		if(substitutionSet.isBound(this)) { //if this variable is bound in the given substitution set
			Unifiable variableBoundExpression = substitutionSet.getBinding(this);//get the binding
			return variableBoundExpression.unify(expression, substitutionSet);//and unify with the already bound expression
		}
		
		//this case is only reached when no existing substitution exists
		SubstitutionSet newSubstitutionSet = new SubstitutionSet(substitutionSet);
		newSubstitutionSet.add(this, expression);
		return newSubstitutionSet;
	}

	
	@Override
	public PredicateCalculusExpression replaceVariables(SubstitutionSet substitutionSet) 
	{
		if(substitutionSet.isBound(this)) {
			Unifiable variableBoundExpression = substitutionSet.getBinding(this);
			return variableBoundExpression.replaceVariables(substitutionSet);
		}
		
		else {
			return this;
		}
	}
	
}
