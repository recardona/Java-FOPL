package rogel.io.fopl.formulas.connectives;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

/**
 * A Disjunction is a BinaryConnective Formula that computes the logical
 * disjunction of its constituent Formulas as its value. 
 * @author recardona
 */
public class Disjunction extends BinaryConnective {

	/**
	 * Constructs a Disjunction over the two Formulas. The value of this
	 * Disjunction is <code>left.getValue() || right.getValue()</code>.
	 * @param left the left operand of this Disjunction.
	 * @param right the right operand of this Disjunction.
	 */
	public Disjunction(Formula left, Formula right) {
		super("or", left, right);
		this.value = left.getValue() || right.getValue();
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Calling replaceVariables on Formulas will return Formula-type 
		// Expressions. Thus, get each connective, replace their Variables and
		// create a new Disjunction out of them.
		Formula newLeft = (Formula) this.getLeft().replaceVariables(substitution);
		Formula newRight = (Formula) this.getRight().replaceVariables(substitution);
		return new Disjunction(newLeft, newRight);
	}
}
