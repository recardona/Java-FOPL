package rogel.io.fopl.terms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.lang.UnsupportedOperationException;

import rogel.io.fopl.Symbol;

/**
 * A Function is a relation between a set of inputs and a set of outputs. In
 * FOPL, the inputs and outputs are all Symbols. 
 * @author recardona
 */
public class Function extends Term {

	/** The number of arguments this Function has. */
	private int arity; 
	
	/** The placeholder terms this function applies to; e.g. in "f(x)" the argument would be "x". */
	private List<Term> arguments; 
	
	/** The underlying relationship between a domain and its co-domain that this Function represents. */
	private HashMap<List<Term>, Term> relation;
		
	/**
	 * Constructs a n-ary Function with the given name. If the name is a String
	 * that did not already exist within the domain of discourse (i.e. was 
	 * already defined as a Symbol), then a new Symbol is created, and added to
	 * the domain of discourse. The arity of this Function depends on the number
	 * of Terms added.
	 * <p>
	 * Note: A 0-ary Function is used to represent a constant symbol.
	 * @param name the name of the Function
	 * @param terms the parameters to this Function
	 */
	public Function(String name, Term... terms) {
		super(name);
		this.arity = terms.length;
		
		//if the arity is 0, define arguments and the function itself as null
		if(this.arity == 0) {
			this.arguments = null;
			this.relation = null;
		}
		
		else {
			this.arguments = Arrays.asList(terms);
			this.relation = new HashMap<List<Term>, Term>();
		}
	}
	
	/**
	 * Constructs a 0-ary Function with the given Symbol. The arity of this Function
	 * depends on the number of Terms added.
	 * <p>
	 * Note: A 0-ary Function is used to represent a constant symbol.
	 * @param symbol the Symbol that represents this Function within the domain of discourse
	 * @param terms the parameters to this Function
	 */
	public Function(Symbol symbol, Term... terms) {
		super(symbol);
		this.arity = terms.length;

		//if the arity is 0, define arguments and the function itself as null
		if(this.arity == 0) {
			this.arguments = null;
			this.relation = null;
		}
		
		else {
			this.arguments = Arrays.asList(terms);
			this.relation = new HashMap<List<Term>, Term>();
		}
	}
	
	/**
	 * Defines a relation between the argument(s) and the value. This operation
	 * expands this Function's domain by the arguments, and the Function's co-
	 * domain by the value.
	 * <p>
	 * If <code>x</code> and <code>y</code> are Symbols, and
	 *    <code>f</code> is a Function object,
	 * calling <code>f.map(y, x)</code> implies that subsequently 
	 * calling <code>f.evaluate(x)</code> will return <code>y</code>.
	 * 
	 * @param value a Symbol that will be in the co-domain of this Function mapped to the argument Symbol(s)
	 * @param firstArgument a Symbol that will be in the domain of this Function
	 * @param otherArguments a varargs of Symbols that serve as additional arguments to this Function
	 * (this is useful for defining multi-dimensional arguments).
	 * @throws UnsupportedOperationException if this is a constant (i.e. a 0-ary function symbol)
	 * @throws TooManyArgumentsException if the number of arguments does not match the arity of this Function
	 * @see {@code evaluate(Symbol... argument)} 
	 */
	public void map(Term value, Term firstArgument, Term... otherArguments) {
		
		if(this.isConstant()) {
			throw new UnsupportedOperationException("Cannot map arguments to values for a constant Function Symbol");
		}
		
		if((1 + otherArguments.length) != this.arity) {
			throw new IllegalArgumentException("Number of arguments (" + (1 + otherArguments.length) + ") does not match this Function's defined arity of "+this.arity);
		}
		
		if(value == null) {
			throw new IllegalArgumentException("Attempted to map to a null value in Function's co-domain");
		}
		
		if(firstArgument == null || Arrays.asList(otherArguments).contains(null)) {
			throw new IllegalArgumentException("Arguments in Function's domain cannot be null");
		}
		
		List<Term> argumentList= new ArrayList<Term>(this.arity); //arity should be 1 + otherArguments.length
		argumentList.add(firstArgument);
		argumentList.addAll(Arrays.asList(otherArguments));
		this.relation.put(argumentList, value);
	}
	
	/**
	 * Attempts to evaluate the Function on the parameter argument.
	 * <p> 
	 * If this is a constant Function, the parameters are ignored, and the method
	 * returns this Function. Otherwise, this method returns the value mapped to
	 * the parameter arguments.
	 * 
	 * @param argument the arguments to this Function
	 * @return the value in this Function's co-domain given the argument, or <code>null</code> if the argument does not form part of this Function's domain
	 * @see {@code map(Symbol value, Symbol firstArgument, Symbol... otherArguments)}
	 */
	public Term evaluate(Term... argument) {
		
		if(this.isConstant()) {
			return this;
		}
		
		else {
			if(argument.length != this.arity) {
				throw new IllegalArgumentException("Number of arguments (" + (1 + argument.length) + ") does not match this Function's defined arity of "+this.arity);
			}
			
			List<Term> argumentList = new ArrayList<Term>(Arrays.asList(argument));
			return this.relation.get(argumentList);
		}
	}
	
	/**
	 * True if this Function is a constant. Constants are 0-ary function symbols.
	 * @return true if this Function has no arguments attached
	 */
	public boolean isConstant() {
		return (this.arity == 0);
	}
	
	/**
	 * Returns an array of all the Symbols for this Function. In the Function
	 * <code>f(x)</code>, this method would return an array of size 2:
	 * <code>[f,x]</code>.
	 * @return an array of all the Symbols for this Function.
	 */
	public Symbol[] getSymbols() {
		
		// Initialize an empty array of Symbols.
		Symbol[] functionSymbols = new Symbol[1+this.arity];
		
		// The first Symbol is this Function's Symbol.
		functionSymbols[0] = this.symbol;
		
		// The rest of the Symbols are the Symbols for each of the argument Terms.
		for(int i = 0; i < this.arity; i++) {
			functionSymbols[i+1] = this.arguments.get(i).symbol;
		}
		
		return functionSymbols;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Function: ");
		sb.append(this.symbol.toString());
		
		if(this.isConstant()) {
			sb.append(" (constant)]");
		}
		
		else {
			sb.append("(");
			for(int argumentIndex = 0; argumentIndex < this.arguments.size(); argumentIndex++)
			{
				sb.append(this.arguments.get(argumentIndex));
				sb.append(", ");
			}
			sb.replace(sb.length()-2, sb.length(), ")]"); //replace last comma+space for closing parenthesis
		}
				
		return sb.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//Function equality is composed of type, symbol, arity, argument, and relation equality.
		if (this == obj) {
			return true;
		}
		
		if (!super.equals(obj)) {
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Function other = (Function) obj;
		if (arguments == null) {
			if (other.arguments != null) {
				return false;
			}
		} 
		else if (!arguments.equals(other.arguments)) {
			return false;
		}
		
		if (arity != other.arity) {
			return false;
		}
		
		if (relation == null) {
			if (other.relation != null) {
				return false;
			}
		} 
		else if (!relation.equals(other.relation)) {
			return false;
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + arity;
		result = prime * result
				+ ((relation == null) ? 0 : relation.hashCode());
		return result;
	}
	
}
