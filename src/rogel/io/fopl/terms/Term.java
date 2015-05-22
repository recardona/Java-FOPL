package rogel.io.fopl.terms;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import rogel.io.fopl.Symbol;

/**
 * Terms name objects or things. As such, Terms require at least one Symbol,
 * to uniquely identify the Term within the domain of discourse.
 * There are two types: Functions and Variables.
 * @author recardona
 */
public abstract class Term {
	
	/** The Symbol that denotes this Term within the domain of discourse. */
	protected Symbol symbol;
	
	/**
	 * Generates a two-dimensional array of Terms by flattening the parameter
	 * Set of Terms. Each row represents a different Term, with their 
	 * associated columns containing all the Symbols that Term contains.
	 * <p> 
	 * For instance, for the Set of Terms <code>{f(x), y}</code> (a Function
	 * and a Variable, respectively), the following two-dimensional array 
	 * represents the flattened version: 
	 * <code>[ [f(x), x] [y, <b>null</b>] ]</code>. 
	 *  
	 * @param terms the Set of Terms to flatten
	 * @return a two-dimensional array of Terms that represents the parameter 
	 * terms in flattened form
	 */
	public static Term[][] flatten(Set<Term> terms) {
		
		// Find the Term within the Set with the greatest number of Symbols.
		// The lowest it could be is 1 (instead of 0) because a Term must be a 
		// Function or Variable, and Variables always have a Symbol count of 1.
		int maxSymbolCount = 1;
		for(Term t : terms) {
			
			// Only check Functions, because Variables
			// cannot be of greater Symbol count.
			if(t instanceof Function) {
				
				Function functionTerm = (Function) t;
				
				// The number of Symbols for a function is equal to 1 (the 
				// Function Symbol itself) + the Symbols for each of the 
				// Function's arguments (given by the Function's arity).
				int symbolCount = 1 + functionTerm.getArity();

				// Update the max if we found a higher number of Symbols.
				if(symbolCount > maxSymbolCount) {
					maxSymbolCount = symbolCount;
				}
			}
		}
		
		// Get the parameter terms as an array.
		Term[] parameterTerms = terms.toArray(new Term[terms.size()]);
		
		// Prepare the flattenedTerms two-dimensional array.
		Term[][] flattenedTerms = new Term[terms.size()][maxSymbolCount];
		for(int termIndex = 0; termIndex < terms.size(); termIndex++) {
			
			// The parameter Term goes in the first column.
			Term t = parameterTerms[termIndex];
			flattenedTerms[termIndex][0] = t; 
			
			// If the Term is a non-constant Function, however, we 
			// need to put all its Terms into the other columns.
			if(t instanceof Function) {
				
				Function functionTerm = (Function) t;
				if(! functionTerm.isConstant()) {
					
					// Iterate through the Function argument terms, and place them
					// in the flattenedTerms.
					List<Term> functionArguments = functionTerm.getArguments();
					
					for(int argIndex = 0; argIndex < functionArguments.size(); argIndex++) {
						
						// Can't start at functionArgumentIndex, because
						// index 0 is the Function Term itself.
						flattenedTerms[termIndex][argIndex+1] = functionArguments.get(argIndex); 
					}
				}
			}
		}
		
		return flattenedTerms;
	}
	
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
	 * discourse
	 */
	protected Term(Symbol symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * @return the symbol
	 */
	public Symbol getSymbol() {
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
	
	// The Comparator<Term> singleton.
	private static LexicographicTermComparator singletonLexicographicTermComparator;

	/**
	 * Returns a LexicographicTermComparator(), used to compare two Terms by the
	 * Symbols they contain. 
	 * @return a Comparator of Terms
	 */
	public static Comparator<Term> getLexicographicTermComparator() {
		// lazy initialization
		if(singletonLexicographicTermComparator == null) {
			singletonLexicographicTermComparator = new LexicographicTermComparator();
		}
		return singletonLexicographicTermComparator;
	}
	
	/**
	 * A Term Comparator which compares the Symbols it contains. 
	 */
	private static class LexicographicTermComparator implements Comparator<Term> {
		@Override
		public int compare(Term o1, Term o2) {
			return Symbol.getLexicographicSymbolComparator().compare(o1.symbol, o2.symbol);
		}
	}
	
}
