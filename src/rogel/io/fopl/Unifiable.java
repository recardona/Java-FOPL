package rogel.io.fopl;

public interface Unifiable extends PredicateCalculusExpression
{
	public SubstitutionSet unify(Unifiable expression, SubstitutionSet substitutionSet);
}
