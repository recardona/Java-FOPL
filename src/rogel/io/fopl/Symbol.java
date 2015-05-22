package rogel.io.fopl;

import java.util.Comparator;
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
	
	/**
	 * The domain of discourse is the set of entities over which variables of
	 * interest in some formal language may range. 
	 */
	private static HashMap<String, Symbol> domainOfDiscourse = new HashMap<String, Symbol>(10000);
	
	/**
	 * The String name this Symbol represents.
	 */
	private String name;
	
	/**
	 * Returns a Symbol with the given name. If no such Symbol exists, it creates
	 * a new one and adds it to the domain of discourse for future retrieval.
	 * @param name the name of the Symbol that is sought
	 * @return a Symbol with the given name
	 * @throws IllegalArgumentException if the Symbol name is null or empty
	 */
	public static Symbol get(String name) throws IllegalArgumentException {
		
		if((name == null) || (name.equals(""))) {
			throw new IllegalArgumentException("Attempted to get a Symbol without a name.");
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
	
	// The Comparator<Symbol> singleton.
	private static LexicographicSymbolComparator singletonLexicographicSymbolComparator;
	
	/**
	 * Returns a LexicographicSymbolComparator, used to compare two Symbols by
	 * the Strings they represent.
	 * @return a Comparator of Symbols, which implements the Comparator interface
	 * @see java.util.Comparator
	 */
	public static Comparator<Symbol> getLexicographicSymbolComparator() {
		//lazy initialization
		if(singletonLexicographicSymbolComparator == null) {
			singletonLexicographicSymbolComparator = new LexicographicSymbolComparator();
		}
		return singletonLexicographicSymbolComparator;
	}
	
	/**
	 * A Symbol Comparator which compares the Strings the Symbol represents
	 * lexicographically.
	 */
	private static class LexicographicSymbolComparator implements Comparator<Symbol> {
		@Override
		public int compare(Symbol s1, Symbol s2) {
			return s1.toString().compareTo(s2.toString());
		}
	}	
}
