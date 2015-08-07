package rogel.io.fopl.formulas.connectives;

import rogel.io.fopl.Symbol;
import rogel.io.fopl.formulas.Formula;
import rogel.io.fopl.programming.Goal;

/**
 * A BinaryConnective is a Formula whose value is defined by the type of 
 * connective the class describes. There are three types of connectives:
 * 1) conjunction (the 'and' connective),
 * 2) disjunction (the 'or' connective), and
 * 3) implication (the 'implies' connective).
 * @author recardona
 */
public abstract class BinaryConnective extends Formula implements Goal {

	/** The left-hand side of this BinaryConnective. */
	private Formula left;
	
	/** The right-hand side of this BinaryConnective. */
	private Formula right;
	
	/**
	 * Constructs a BinaryConnective with the given name, over the given left
	 * and right Formulas. If the name is a String that did not already exist
	 * within the domain of discourse (i.e. was already defined as a Symbol),
	 * then a new Symbol is created and added to the domain of discourse. The
	 * value of this Formula depends on the type of BinaryConnective, and its
	 * constituent left and right Formulas.
	 * @param name the name of the Formula
	 * @param left the left Formula to the connective
	 * @param right the right Formula to the connective
	 */
	protected BinaryConnective(String name, Formula left, Formula right) {
		super(name);
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Constructs a BinaryConnective with the given name, over the given left
	 * and right Formulas.  The value of this Formula depends on the type of
	 * BinaryConnective, and its constituent left and right Formulas.
	 * @param symbol the Symbol that represents this Formula within the domain
	 *  of discourse
	 * @param left the left Formula to the connective
	 * @param right the right Formula to the connective
	 */
	protected BinaryConnective(Symbol symbol, Formula left, Formula right) {
		super(symbol);
		this.left = left;
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public Formula getLeft() {
		return left;
	}

	/**
	 * @return the right
	 */
	public Formula getRight() {
		return right;
	}
	
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(this.getClass().getSimpleName());
		builder.append(": ");
		builder.append(getLeft());
		builder.append(" ");
		builder.append(this.symbol);
		builder.append(" ");
		builder.append(getRight());
		builder.append(" (");
		builder.append(this.value);
		builder.append(")]");
		return builder.toString();
	}

}
