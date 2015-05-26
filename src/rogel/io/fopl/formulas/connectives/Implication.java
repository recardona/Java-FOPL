package rogel.io.fopl.formulas.connectives;

import rogel.io.fopl.formulas.BinaryConnective;
import rogel.io.fopl.formulas.Formula;

/**
 * An Implication is a BinaryConnective Formula that computes the logical
 * implication of its constituent Formulas as its value. 
 * @author recardona
 */
public class Implication extends BinaryConnective {

	/**
	 * Constructs an Implication over the two Formulas. The value of this
	 * Implication is <code>!left.getValue() || right.getValue()</code>.
	 * @param left the left operand of this Implication.
	 * @param right the right operand of this Implication.
	 */
	public Implication(Formula left, Formula right) {
		super("implies", left, right);
		this.value = !left.getValue() || right.getValue();
	}
}
