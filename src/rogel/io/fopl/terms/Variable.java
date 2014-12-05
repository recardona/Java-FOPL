package rogel.io.fopl.terms;

import rogel.io.fopl.Symbol;

/**
 * A Variable is a "placeholder" Term, which can be assigned values through a
 * Substitution set. Variables also allow FOPL formulas that express quantified
 * ideas.
 * 
 * @author recardona
 */
public class Variable extends Term {
	
	
	/**
	 * Constructs a Variable with the given name. If the name is a String that did
	 * not already exist within the domain of discourse (defined as a Symbol),
	 * then a new Symbol is created.
	 * @param name the name of this Variable
	 */
	public Variable(String name) {
		super(name);
	}

	
	/**
	 * Constructs a Variable with the given Symbol.
	 * @param symbol the Symbol that represents this Variable within the domain of
	 * discourse
	 */	
	public Variable(Symbol symbol) {
		super(symbol);
	}
	
	
	@Override
	public String toString() {
		return "[Variable: "+this.symbol.toString()+"]";
	}
	
	
	@Override
	public boolean equals(Object obj) {
		//Variable equality is composed of type and symbol equality
		
		if(!(obj instanceof Variable)) {
			return false;
		}
		
		Variable other = (Variable) obj;
		return this.symbol.equals(other.symbol);
	}

	
	@Override
	public int hashCode() {
		return this.symbol.hashCode();
	}
}
