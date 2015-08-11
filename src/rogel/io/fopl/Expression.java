package rogel.io.fopl;

import java.util.HashMap;

import rogel.io.fopl.terms.Variable;

/**
 * An Expression represents anything that can be expressed in FOPL.	
 * 
 * @author recardona
 */
public interface Expression {

	/**
	 * Replaces any Variable in this Expression with its binding in the Substitution, if it exists.
	 * 
	 * @param substitution the Substitution set of Variables created thus far.
	 * @return an Expression with substituted Variables.
	 */
	public Expression replaceVariables(Substitution substitution);
	
	/**
	 * Replaces the Variables in this Expression with new copies of the Variable that have the same 
	 * name. The copies are the Variables that have been <i>standardized</i>.
	 * 
	 * @param newVariables the set of Variables that have been copied thus far.
	 * @return an Expression with standardized Variables.
	 */
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables);
	
}
