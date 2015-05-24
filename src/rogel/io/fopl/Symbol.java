package rogel.io.fopl;

import java.util.HashMap;

/**
 * A Symbol follows the Herbrand interpretation, and is merely a (Java) String 
 * that represents itself. There is only <b>one</b> Symbol with any given name
 * (they are memory-unique objects). 
 * 
 * @author recardona
 * @see http://en.wikipedia.org/wiki/Herbrand_interpretation
 */
public class Symbol {

	/** The Symbol for the value "true" (top) in FOPL. */
	public static final Symbol TRUE = new Symbol("true");
	
	/** The Symbol for the value "false" (bottom) in FOPL. */
	public static final Symbol FALSE = new Symbol("false");
	
	/**
	 * The domain of discourse is the set of entities over which variables of
	 * interest in some formal language may range. 
	 */
	private static HashMap<String, Symbol> domainOfDiscourse = new HashMap<String, Symbol>(10000);
	static {
		
		// Statically initializing the domain of discourse with the two
		// reserved Symbols: true and false.
		domainOfDiscourse.put("true", TRUE);
		domainOfDiscourse.put("false", FALSE);
	}

	/** The String name this Symbol represents. */
	private String name;
	
	/**
	 * Returns a Symbol with the given name. If no such Symbol exists, it creates
	 * a new one and adds it to the domain of discourse for future retrieval.
	 * @param name the name of the Symbol that is sought
	 * @return a Symbol with the given name
	 * @throws IllegalArgumentException if the Symbol name is null, empty or reserved ('true' or 'false')
	 */
	public static Symbol get(String name) throws IllegalArgumentException {
		
		if((name == null) || (name.equals(""))) {
			throw new IllegalArgumentException("Attempted to get a Symbol without a name.");
		}

		if(name.equals("true") || name.equals("false")) {
			throw new IllegalArgumentException("Attempted to get a Symbol with the reserved keyword: " + name);
		}
		
		if(domainOfDiscourse.containsKey(name)) {
			return domainOfDiscourse.get(name);
		}
		
		Symbol newSymbol = new Symbol(name);
		domainOfDiscourse.put(name, newSymbol);
		return newSymbol;
	}
	
	/**
	 * Constructs a Symbol of the given name.
	 * @param name the name of the symbol
	 */
	private Symbol(String name) {
		this.name = name;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Symbol.clone() is not supported");
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( ! (obj instanceof Symbol)) {
			return false;
		}
		
		Symbol other = (Symbol) obj;
		return (this.name.equals(other.name));
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
