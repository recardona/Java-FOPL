package rogel.io.fopl;

public interface PredicateCalculusExpression 
{
	/**
	 * Replaces any bound variable with its binding.
	 * @param substitutionSet the bindings to use in the replacement.
	 * @return a PredicateCalculus expression with bound variables replaced by their bindings.
	 */
	public PredicateCalculusExpression replaceVariables(SubstitutionSet substitutionSet) throws CloneNotSupportedException;
}
