package rogel.io.fopl.terms;

import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;

/**
 * Terms name objects or things. As such, Terms require at least one Symbol,
 * to uniquely identify the Term within the domain of discourse.
 * There are two types: Functions and Variables.
 * @author recardona
 */
public abstract class Term implements Unifiable {
	
	/** The Symbol that denotes this Term within the domain of discourse. */
	protected Symbol symbol;
	
	/**
	 * Defines a Term with the given name. If the name is a String that did not
	 * already exist within the domain of discourse (defined as a Symbol), then
	 * a new Symbol is created.
	 * @param name the name of this Term
	 */
	protected Term(String name) {
		this.symbol = Symbol.get(name);
	}
	
	/**
	 * Defines a Term with the given Symbol.
	 * @param symbol the Symbol that represents this Term within the domain of
	 *   discourse
	 */
	protected Term(Symbol symbol) {
		this(symbol.toString());
	}
	
	/**
	 * @return the symbol
	 */
	public final Symbol getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String toString() {
		if(this instanceof Function) {
			return ((Function) this).toString();
		}
		
		if(this instanceof Variable) {
			return ((Variable) this).toString();
		}
		
		else {
			return "[Term: " + this.symbol.toString() + "]";
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		//Term equality is composed of type and symbol equality
		if(!(obj instanceof Term)) {
			return false;
		}
		
		Term other = (Term) obj;
		return this.symbol.equals(other.symbol);		
	}
	
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}
}
