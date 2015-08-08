package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Symbol;


/**
 * The base class for FOPL Formulas. Formulas are Expressions made up of a
 * Symbol that identifies the Formula and a Boolean value. 
 * @author recardona
 * @see rogel.io.fopl.True.java
 * @see rogel.io.fopl.False.java
 * @see rogel.io.fopl.Predicate.java
 * @see rogel.NotOperator.io.fopl.formulas.connectives.NegatedFormula.java
 * @see rogel.io.fopl.formulas.operators.AbstractOperator.java
 * @see rogel.io.fopl.QuantifiedFormula.java
 */
public abstract class Formula implements Expression {
		
	/** This Formula's truth value. */
	protected Boolean value;
	
	/** The Symbol that uniquely identifies this Formula. */
	protected Symbol symbol;
	
	/**
	 * Defines a null-valued Formula with the given name.  If the name is a 
	 * String that did not already exist within the domain of discourse 
	 * (defined as a Symbol), then a new Symbol is created.
	 * @param the name of this Formula
	 */
	protected Formula(String name) {
		this(Symbol.get(name));
	}
	
	/**
	 * Defines a null-valued Formula with the given Symbol. 
	 * @param symbol the Symbol that represents this Formula within the domain
	 * of discourse
	 */
	protected Formula(Symbol symbol) {
		this.value = null;
		this.symbol = symbol;
	}
	
	/**
	 * A literal is an atomic Formula or its negation.
	 * @return true if this Formula is atomic or is the negation of an atomic 
	 * 	Formula, false otherwise
	 */
	public boolean isLiteral() {
		//default to false. (Let subclasses override if necessary)
		return false; 
	}
	
	/**
	 * An atomic Formula is a Predicate.
	 * @return true if this Formula is a Predicate, false otherwise.
	 */
	public boolean isAtomic() {
		//default to false. (Let subclasses override if necessary)
		return false;
	}
	
	/**
	 * @return the symbol.
	 */
	public final Symbol getSymbol() {
		return this.symbol;
	}
		
	/**
	 * @return the value of this Formula.
	 */
	public boolean getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Formula other = (Formula) obj;
		if (symbol == null) {
			if (other.symbol != null) {
				return false;
			}
		} else if (!symbol.equals(other.symbol)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}	
}