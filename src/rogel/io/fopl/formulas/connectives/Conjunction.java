package rogel.io.fopl.formulas.connectives;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.BinaryConnective;
import rogel.io.fopl.formulas.Formula;

/**
 * A Conjunction is a BinaryConnective Formula that computes the logical
 * conjunction of its constituent Formulas as its value. 
 * @author recardona
 */
public class Conjunction extends BinaryConnective {

	/**
	 * Constructs a Conjunction over the two Formulas. The value of this
	 * Conjunction is <code>left.getValue() && right.getValue()</code>.
	 * @param left the left operand of this Conjunction.
	 * @param right the right operand of this Conjunction.
	 */
	public Conjunction(Formula left, Formula right) {
		super("and", left, right);
		this.value = left.getValue() && right.getValue();
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Calling replaceVariables on Formulas will return Formula-type 
		// Expressions. Thus, get each connective, replace their Variables and
		// create a new Conjunction out of them.
		Formula newLeft = (Formula) this.getLeft().replaceVariables(substitution);
		Formula newRight = (Formula) this.getRight().replaceVariables(substitution);
		return new Conjunction(newLeft, newRight);
	}
}
