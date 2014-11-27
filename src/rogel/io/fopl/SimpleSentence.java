package rogel.io.fopl;

public class SimpleSentence implements Unifiable
{
	private Unifiable[] terms;
	
	public SimpleSentence(Constant predicateName, Unifiable... args)
	{
		this.terms = new Unifiable[args.length + 1];
		this.terms[0] = predicateName;
		System.arraycopy(args, 0, this.terms, 1, args.length);
	}
	
	private SimpleSentence(Unifiable... args) {
		this.terms = args;
	}
	
	public int length() {
		return this.terms.length;
	}
	
	public Unifiable getTerm(int index) {
		return this.terms[index];
	}
	
	@Override
	public String toString() 
	{	
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		for(Unifiable p : this.terms) {
			sb.append(p.toString());
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	@Override
	public SubstitutionSet unify(Unifiable expression, SubstitutionSet substitutionSet) 
	{
		if(expression instanceof SimpleSentence)
		{
			SimpleSentence otherSimpleSentence = (SimpleSentence) expression;
			if(this.length() != otherSimpleSentence.length()) {
				return null; //unification of different length expressions is not possible!
			}
			
			SubstitutionSet newSubstitutionSet = new SubstitutionSet(substitutionSet);
			for(int termIndex = 0; termIndex < this.length(); termIndex++)
			{
				Unifiable term1 = this.getTerm(termIndex);
				Unifiable term2 = otherSimpleSentence.getTerm(termIndex);
				newSubstitutionSet = term1.unify(term2, newSubstitutionSet);
				
				//if we get a null in any unification, unification is not possible
				if(newSubstitutionSet == null) {
					return null; 
				}
			}			
		}
		
		if(expression instanceof Variable) {
			return expression.unify(this, substitutionSet);
		}
		
		return null;
	}

}
