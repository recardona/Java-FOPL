package rogel.io.fopl.terms;

import java.util.Arrays;
import java.util.HashMap;

import javax.naming.OperationNotSupportedException;

import rogel.io.fopl.Symbol;

/**
 * A Function is a relation between a set of inputs, and a set of outputs. 
 * @author recardona
 */
public class Function extends Term {

	private Term[] arguments; //the placeholder terms this function applies to; e.g. in "f(x)" the argument would be "x"
	private int arity; 
	private HashMap<Symbol[], Symbol> relation;
		
	
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
		this.arguments = this.arity == 0? null : terms;
		this.relation = this.arity == 0? null : new HashMap<Symbol[], Symbol>();
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
		this.arguments = this.arity == 0? null : terms;
		this.relation = this.arity == 0? null : new HashMap<Symbol[], Symbol>();
	}
	
	
	/**
	 * Defines a relation between the argument(s) and the value. This operation
	 * expands this Function's domain by the arguments, and the Function's co-
	 * domain by the value.
	 * <p>
	 * If
	 * <code>x</code> and <code>y</code> are Symbols, and
	 * <code>f</code> is a Function object,
	 * calling <code>f.map(y, x)</code> implies that subsequently 
	 * calling <code>f.evaluate(x)</code> will return <code>y</code>.
	 * 
	 * @param value a Symbol that will be in the co-domain of this Function mapped to the argument Symbol(s)
	 * @param firstArgument a Symbol that will be in the domain of this Function
	 * @param otherArguments a varargs of Symbols that serve as additional arguments to this Function
	 * (this is useful for defining multi-dimensional arguments).
	 * @throws OperationNotSupportedException if this is a constant (i.e. a 0-ary function symbol)
	 * @throws TooManyArgumentsException if the number of arguments does not match the arity of this Function
	 * @see {@code evaluate(Symbol... argument)} 
	 */
	public void map(Symbol value, Symbol firstArgument, Symbol... otherArguments) throws OperationNotSupportedException {
		
		if(this.isConstant()) {
			throw new OperationNotSupportedException("Cannot map arguments to values for a constant Function Symbol");
		}
		
		if(1 + otherArguments.length != this.arity) {
			throw new IllegalArgumentException("Number of arguments does not match this Function's defined arity of "+this.arity);
		}
		
		if(value == null) {
			throw new IllegalArgumentException("Attempted to map to a null value in Function's co-domain");
		}
		
		if(firstArgument == null || Arrays.asList(otherArguments).contains(null)) {
			throw new IllegalArgumentException("Arguments in Function's domain cannot be null");
		}
		
		Symbol[] args = new Symbol[arity]; //arity should be 1 + otherArguments.length
		args[0] = firstArgument;
		for(int otherArgumentsIndex = 0; otherArgumentsIndex < otherArguments.length; otherArgumentsIndex++) {
			args[otherArgumentsIndex + 1] = otherArguments[otherArgumentsIndex];
		}
		
		this.relation.put(args, value);
	}
	
	
	/**
	 * Attempts to evaluate the Function on the parameter argument.
	 * <p> 
	 * If this is a constant Function, the parameters are ignored, and the Symbol
	 * denoting this constant is returned. Otherwise, this method returns the
	 * value mapped to the parameter arguments.
	 * 
	 * @param argument the arguments to this Function
	 * @return the value in this Function's co-domain given the argument, or <code>null</code> if the argument does not form part of this Function's domain
	 * @see {@code map(Symbol value, Symbol firstArgument, Symbol... otherArguments)}
	 */
	public Symbol evaluate(Symbol... argument) {
		
		if(this.isConstant()) {
			return this.symbol;
		}
		
		else {
			if(arguments.length != this.arity) {
				throw new IllegalArgumentException("Number of arguments does not match this Function's defined arity of "+this.arity);
			}
			
			return this.relation.get(argument);
		}
	}
	
	
	/**
	 * Constants are 0-ary function symbols.
	 * @return true if this Function has no arguments attached
	 */
	public boolean isConstant() {
		return (this.arity == 0);
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
			for(Term t : this.arguments) {
				sb.append(t.toString());
				sb.append(", ");
			}
			sb.replace(sb.length()-2, sb.length(), ")]"); //replace last comma+space for closing parenthesis
		}
				
		return sb.toString();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		//Function equality is composed of type, symbol, arity, argument, and relation equality
		
		if(!(obj instanceof Function)) {
			return false;
		}
		
		Function other = (Function) obj;
		
		if(! this.symbol.equals(other.symbol)) {
			return false;
		}
		
		if(this.arity != other.arity) {
			return false;
		}
		
		if(! this.arguments.equals(other.arguments)) {
			return false;
		}
		
		if(! this.relation.equals(other.relation)) {
			return false;
		}
		
		return true;
	}
	
	
	@Override
	public int hashCode() {
		 int hash = 1;
		 hash = hash * 27 + this.symbol.hashCode();
	     hash = hash * 17 + this.arity;
	     hash = hash * 31 + this.arguments.hashCode();
	     hash = hash * 13 + this.relation.hashCode();
	     return hash;
	}
	
}






