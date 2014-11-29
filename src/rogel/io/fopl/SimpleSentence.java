package rogel.io.fopl;

import rogel.io.fopl.terms.Constant;
import rogel.io.fopl.terms.Variable;

public class SimpleSentence implements Unifiable
{
	private Unifiable[] terms;
	
	/**
	 * Constructs a FOPL Simple Sentence. 
	 * First-order predicate calculus requires that the first term of
	 * a simple sentence be a constant. 
	 * 
	 * @param predicateName the name of the predicate
	 * @param args the arguments to the predicate.
	 */
	public SimpleSentence(Constant predicateName, Unifiable... args)
	{
		this.terms = new Unifiable[args.length + 1];
		this.terms[0] = predicateName;
		System.arraycopy(args, 0, this.terms, 1, args.length);
	}
	
	/**
	 * Private constructor of <code>Unifiable</code> arguments.
	 * @param args the arguments of the predicate.
	 */
	private SimpleSentence(Unifiable... args) {
		this.terms = args;
	}
	
	/**
	 * @return the number of terms in <b>this</b> <code>SimpleSentence</code>
	 */
	public int length() {
		return this.terms.length;
	}
	
	/**
	 * Returns the <code>Unifiable</code> term at the parameter index.
	 * Index-based access is backed with an array, and so anything illegal
	 * to do with an array is illegal to do with this method.
	 * @param index the index of the parameter to get
	 * @return the <code>Unifiable</code> term
	 */
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
			sb.append(" ");
		}
		sb.replace(sb.length()-1, sb.length(), ")"); //replace the last space for a bracket
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
			return newSubstitutionSet;
		}
		
		if(expression instanceof Variable) {
			return expression.unify(this, substitutionSet);
		}
		
		return null;
	}

	
	@Override
	public PredicateCalculusExpression replaceVariables(SubstitutionSet substitutionSet) 
	{
		Unifiable[] replacedVariableTerms = new Unifiable[this.terms.length];
		for(int termIndex = 0; termIndex < this.length(); termIndex++)
		{
			//for every term, attempt to replace the inner variables with bindings
			Unifiable thisTerm = this.terms[termIndex];
			replacedVariableTerms[termIndex] = (Unifiable) thisTerm.replaceVariables(substitutionSet);
		}
		
		//creates a new sentence from the results
		return new SimpleSentence(replacedVariableTerms);
	}
	
}
