package rogel.io.fopl.formulas.connectives;

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
}
