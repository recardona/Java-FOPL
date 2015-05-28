package rogel.io.fopl.programming.horn;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

/**
 * A Clause is an Expression formed from a finite collection of 
 * @author recardona
 */
public abstract class Clause extends Formula {

	protected Clause() {
		super("nary-or");
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}

}
