package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Symbol;


/**
 * A Formula is an Expression made up from a Symbol and a boolean value. This class is the base 
 * class for all FOPL Formulas.
 * @author recardona
 */
public abstract class Formula implements Expression {
		
	/** This Formula's truth value. */
	protected Boolean value;
	
	/** The Symbol that uniquely identifies this Formula. */
	protected Symbol symbol;
	
	/**
	 * Defines a null-valued Formula with the given name.  If the name is a {@code String} that did
	 * not already exist within the domain of discourse (defined as a {@code Symbol}), then a new 
	 * {@code Symbol} is created.
	 * @param the name of this Formula, not null.
	 */
	protected Formula(String name) {
		this(Symbol.get(name));
	}
	
	/**
	 * Defines a null-valued Formula with the given Symbol. 
	 * @param symbol the Symbol that represents this Formula within the domain of discourse, not null.
	 */
	protected Formula(Symbol symbol) {
		this.symbol = symbol;
		this.value = null;
	}
	
	/**
	 * Returns true if and only if this Formula is a literal. A literal is defined to be an atomic
	 * Formula or its negation.
	 * @return true if this Formula is atomic or is the negation of an atomic 
	 * 	Formula, false otherwise
	 */
	public abstract boolean isLiteral();
	
	/**
	 * Returns true if and only if this Formula is atomic. An atomic Formula (or equivalently, an 
	 * 'atom') is a Formula that contains no logical connectives or equivalently a Formula that has
	 * no sub-formulas.
	 * @return true if this Formula is atomic, false otherwise.
	 */
	public abstract boolean isAtomic();
	
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}	
}
