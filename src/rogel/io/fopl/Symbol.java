package rogel.io.fopl;

import java.util.HashMap;

/**
 * A Symbol follows the Herbrand interpretation, and is merely a (Java)
 * String that represents itself. There is only <b>one</b> Symbol with
 * any given name (they are memory-unique objects). 
 * 
 * @see http://en.wikipedia.org/wiki/Herbrand_interpretation
 * @author recardona
 */
public class Symbol 
{
	//In the formal sciences, the domain of discourse is the set of entities over which 
	//variables of interest in some formal language may range.
	private static HashMap<String, Symbol> domainOfDiscourse = new HashMap<String, Symbol>(10000);
	private String name;
	
	/**
	 * Returns a Symbol with the given name. If no such Symbol exists, it creates
	 * a new one and adds it to the domain of discourse for future retrieval.
	 * @param name the name of the Symbol that is sought (or the name of the newly
	 * created Symbol if none with the parameter name exists)
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Symbol get(String name) throws IllegalArgumentException
	{
		if((name == null) || (name.equals(""))) {
			throw new IllegalArgumentException("Attempted to get a Symbol without a name");
		}
		
		if(domainOfDiscourse.containsKey(name)) {
			return domainOfDiscourse.get(name);
		}
		
		Symbol newSymbol = new Symbol(name);
		domainOfDiscourse.put(name, newSymbol);
		return newSymbol;
	}
	
	/**
	 * Private constructor
	 * @param name the name of the symbol
	 * @throws IllegalArgumentException if aName is null or empty  
	 */
	private Symbol(String name) throws IllegalArgumentException
	{
		if((name == null) || (name.equals(""))) {
			throw new IllegalArgumentException("Symbol names must be non-null, non-empty strings");
		}
		
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
		return (this.hashCode() == other.hashCode());
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
}
