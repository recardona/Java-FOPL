package rogel.io.fopl;

public interface Unifiable extends PredicateCalculusExpression
{
	/**
	 * A call to unify takes a Unifiable expression and a SubstitutionSet
	 * containing any bindings from the algorithm so far.
	 * @param expression the expression to unify <b>this</code> with
	 * @param substitutionSet the existing set of substitutions
	 * @return a new SubstitutionSet, adding additional variable bindings
	 * to those passed in the parameters. 
	 */
	public SubstitutionSet unify(Unifiable expression, SubstitutionSet substitutionSet);
}
