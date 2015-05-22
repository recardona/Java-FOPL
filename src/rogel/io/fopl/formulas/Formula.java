package rogel.io.fopl.formulas;

import rogel.io.fopl.Symbol;

/**
 * Formulas state conditions. Like Terms, they require at least one
 * Symbol to designate what kind of condition they identify as true
 * or false.
 * 
 * There are several types:
 * @see Predicate.java
 * @see NegatedFormula.java
 * @see BinaryConnective.java
 * @see QuantifiedFormula.java
 *  
 * @author recardona
 */
public abstract class Formula 
{
	protected Boolean value; //this formula's truth value
	protected Symbol symbol; //this formula's unique symbol
	
	/**
	 * Defines a null-valued Formula with the given name.  If the name is a String
	 * that did not already exist within the domain of discourse (defined as a
	 * Symbol), then a new Symbol is created.
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
	public Symbol getSymbol() {
		return this.symbol;
	}
	
	/**
	 * @return the truth value of this Formula.
	 */
	public abstract boolean getValue();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public abstract boolean equals();
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public abstract int hashCode();
}
