package rogel.io.fopl.formulas;

import rogel.io.fopl.Symbol;


/**
 * Formulas state conditions. Like Terms, they require at least one Symbol to 
 * designate what kind of condition they identify as true or false.
 * @author recardona
 * @see Predicate.java
 * @see NegatedFormula.java
 * @see BinaryConnective.java
 * @see QuantifiedFormula.java
 */
public abstract class Formula {
		
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
		this.value = null;
		this.symbol = Symbol.get(name);
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
	 * @return the symbol
	 */
	public final Symbol getSymbol() {
		return this.symbol;
	}
	
	/**
	 * @return the truth value of this Formula.
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
