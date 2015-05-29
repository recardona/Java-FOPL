package rogel.io.fopl.syntax.terms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import rogel.io.fopl.syntax.Expression;
import rogel.io.fopl.syntax.Substitution;
import rogel.io.fopl.syntax.Symbol;
import rogel.io.fopl.syntax.Unifiable;
import rogel.io.util.VarargsUtils;

/**
 * A Function is a function between a set of inputs and a set of outputs. In
 * FOPL, the inputs and outputs are all Symbols. 
 * @author recardona
 */
public class Function extends Term {
	
	/**
	 * The domain of discourse is the set of entities over which variables of 
	 * interest in some formal language may range. For Functions, this domain
	 * captures every Function that has been declared during program execution.
	 */
	private static HashMap<Pair<Symbol, Integer>, Function> functionDomainOfDiscourse = 
			new HashMap<Pair<Symbol, Integer>, Function>(100);
	
	/** 
	 * The Function space is a mapping of Function signatures to Function 
	 * relations. This Map allows distinct Function Objects with equal Function
	 * signatures to perform {@link Function#map(Term, Term, Term...)} and 
	 * {@link Function#evaluate(Term...)} operations over the same domain and
	 * co-domain sets.
	 */
	private static HashMap< Pair<Symbol, Integer>, HashMap<List<Term>, Term>> functionSpace = 
			new HashMap< Pair<Symbol, Integer>, HashMap<List<Term>, Term> >(100);
	
	/** 
	 * The default prefix to use for Variables when generating Functions with 
	 * arity greater than 0.
	 */ 
	private static final String DEFAULT_ARGUMENT_PREFIX = "X"; // I like "X" named variables. :)
	
	/** The number of arguments this Function has. */
	private int arity; 
	
	/** The signature of this Function, defined as the pair: (Symbol, arity).*/
	private Pair<Symbol, Integer> signature;
	
	/** The placeholder terms this function applies to; e.g. in "f(x)" the argument would be "x". */
	private List<Term> arguments; 
	
	/**
	 * Declares a Function identified by the Symbol that represents the String
	 * {@code name}. This Function's signature is defined to be the Pair:
	 * {@code ({@link Symbol#get(name)}, variables.length)}. Like 
	 * {@link Function#get(name, numberOfArguments)}, if no Function with equal
	 * signature exists within the domain of discourse, this method creates a 
	 * new Function and adds it to the domain for future retrieval. 
	 * <p>
	 * However, if a Function with the same signature already exists within the
	 * domain of discourse, then the returned Function is equivalent (as per
	 * {@link Object#equals(Object)}) but not the same (i.e. different Object)
	 * as the existing one. The returned Function Object then replaces the 
	 * existing one in the domain of discourse.
	 * <p>
	 * The Function's underlying relation (as defined by calls to 
	 * {@link Function#map(Term, Term, Term...)}) is preserved across all
	 * Function Objects that share the same signature.
	 * @param name the name of this Function.
	 * @param variables the variables of this Function.
	 * @return a Function whose arguments are the parameter Variables.
	 */
	public static Function declare(String name, Variable... variables) {
		
		// Get the Function Symbol.
		Symbol functionSymbol = Symbol.get(name);
		
		// Create the method signature for recording within the domain of discourse.
		Pair<Symbol, Integer> functionSignature = Pair.of(functionSymbol, variables.length);

		// Create the new Function.
		Function function = new Function(functionSymbol, variables);
		
		// Put/replace the entry of the previous Function.
		Function.functionDomainOfDiscourse.put(functionSignature, function);
		return function;
	}
	
	/**
	 * Returns a Function identified by the Symbol that represents the String 
	 * {@code name}. This Function's signature is defined to be the Pair:
	 * {@code (Symbol.get(name), numberOfArguments)}. If no Function with equal
	 * signature exists within the domain of discourse, this method creates a 
	 * new Function and adds it to the domain for future retrieval.
	 * @param name the name of this Function.
	 * @param numberOfArguments the number of arguments this Function has.
	 * @return a Function with the given name and number of arguments.
	 */
	public static Function get(String name, int numberOfArguments) {
		
		// Get the Function Symbol.
		Symbol functionSymbol = Symbol.get(name);
		
		// See if a Function with this Symbol and argument length has been declared before.
		Pair<Symbol, Integer> functionSignature = Pair.of(functionSymbol, numberOfArguments);
		if(Function.functionDomainOfDiscourse.containsKey(functionSignature)) {
			
			// If it has been declared, return the existing Function.
			return Function.functionDomainOfDiscourse.get(functionSignature);
		}
		
		// Otherwise create a new Function, add it to the domain, & return it.
		// First generate Variables for each argument:
		Variable[] functionArguments = new Variable[numberOfArguments];
		for(int variableIndex = 0; variableIndex < numberOfArguments; variableIndex++) {
			Symbol variableSymbol = Symbol.generateSymbol(Function.DEFAULT_ARGUMENT_PREFIX);
			functionArguments[variableIndex] = new Variable(variableSymbol);
		}
		
		// Next generate the actual Function.
		Function function = new Function(functionSymbol, functionArguments);
		Function.functionDomainOfDiscourse.put(functionSignature, function);
		return function;		
	}
	
	/**
	 * Constructs an n-ary Function with the given name. If the name is a String
	 * that did not already exist within the domain of discourse (i.e. was 
	 * already defined as a Symbol), then a new Symbol is created and added to
	 * the domain of discourse. The arity of this Function depends on the number
	 * of Terms added.
	 * <p>
	 * Note: A 0-ary Function is used to represent a constant symbol.
	 * @param name the name of the Function
	 * @param terms the parameters to this Function
	 */
	public Function(String name, Term... terms) {
		this(Symbol.get(name), terms);
	}
	
	/**
	 * Constructs an n-ary Function with the given Symbol. The arity of this Function
	 * depends on the number of Terms added.
	 * <p>
	 * Note: A 0-ary Function is used to represent a constant symbol.
	 * @param symbol the Symbol that represents this Function within the domain of discourse
	 * @param terms the parameters to this Function
	 */
	private Function(Symbol symbol, Term... terms) {
		super(symbol);
		this.arity = terms.length;
		this.signature = Pair.of(this.symbol, this.arity);
				
		// if the arity is 0, define arguments and the function itself as null.
		if(this.arity == 0) {
			this.arguments = null;
		}
		
		else {
			VarargsUtils.throwExceptionOnNull( (Object[]) terms);
			this.arguments = Arrays.asList(terms);
			
			// Declare a new relation for this signature within the Function space.
			Function.functionSpace.put(this.signature,  new HashMap<List<Term>, Term>());
		}
	}
	
	/**
	 * Defines a relation between the argument(s) and the value. This operation
	 * expands this Function's domain by the arguments, and the Function's 
	 * co-domain by the value.
	 * <p>
	 * For example, if {@code x} and {@code y} are Terms, and {@code f} is a
	 * Function, calling {@code f.map(y, x)} implies that subsequently calling 
	 * {@code f.evaluate(x)} will return {@code y}.
	 * 
	 * @param value a Symbol that will be in the co-domain of this Function mapped to the argument Symbol(s).
	 * @param firstArgument a Symbol that will be in the domain of this Function.
	 * @param otherArguments a varargs of Symbols that serve as additional arguments to this Function
	 * (this is useful for defining multi-dimensional arguments).
	 * @throws UnsupportedOperationException if this is a constant (i.e. a 0-ary function symbol).
	 * @throws IllegalArgumentException if the number of arguments does not match the arity of this Function.
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
		
		if(firstArgument == null || VarargsUtils.containsNull((Object[]) otherArguments)) {
			throw new IllegalArgumentException("Arguments in Function's domain cannot be null");
		}
		
		// Prepare the argument list.
		List<Term> argumentList= new ArrayList<Term>(this.arity); //arity should be 1 + otherArguments.length
		argumentList.add(firstArgument);
		argumentList.addAll(Arrays.asList(otherArguments));
		
		// Find the relation that corresponds to this Function signature.
		HashMap<List<Term>, Term> relation = Function.functionSpace.get(this.getSignature());
		
		// Place the mapping in this relation.
		relation.put(argumentList, value);		
	}
	
	/**
	 * Attempts to evaluate the Function on the parameter argument.
	 * <p> 
	 * If this is a constant Function, the parameters are ignored, and the 
	 * method returns this Function. Otherwise, this method returns the value 
	 * mapped to the parameter arguments.
	 * @param arguments the arguments to this Function.
	 * @return the value in this Function's co-domain given the argument, or 
	 * 	null if the argument does not form part of this Function's domain.
	 * @see {@code map(Symbol value, Symbol firstArgument, Symbol... otherArguments)}
	 */
	public Term evaluate(Term... arguments) {
		
		if(this.isConstant()) {
			return this;
		}
		
		else {
			VarargsUtils.throwExceptionOnNull( (Object[]) arguments );
			if(arguments.length != this.arity) {
				throw new IllegalArgumentException("Number of arguments (" + (1 + arguments.length) + ") does not match this Function's defined arity of "+this.arity);
			}
			
			// Prepare the argument List.
			List<Term> argumentList = new ArrayList<Term>(Arrays.asList(arguments));
			
			// Find the relation that corresponds to this Function signature.
			HashMap<List<Term>, Term> relation = Function.functionSpace.get(this.getSignature());
			
			// Return the mapping you find there given the argument List.
			return relation.get(argumentList);
		}
	}
	
	/**
	 * True if this Function is a constant. Constants are 0-ary Functions.
	 * @return true if this Function has no arguments attached.
	 */
	public boolean isConstant() {
		return (this.arity == 0);
	}

	/**
	 * Returns the number of arguments this Function has.
	 * @return this Function's arity.
	 */
	public int getArity() {
		return this.arity;
	}
	
	/**
	 * Returns a List of placeholder terms this function applies to. For 
	 * example, in the Function {@code f(x)} the argument would be {@code x}.
	 * If this Function is constant, this method returns null.
	 * @return this Function's arguments.
	 */
	public List<Term> getArguments() {
		return this.arguments;
	}
	
	/**
	 * @return this Function's signature, defined as a Pair: (Symbol, getArity()). 
	 */
	public Pair<Symbol, Integer> getSignature() {
		return this.signature;
	}
	
	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		if(this.isConstant()) {
			// A constant Function can't replace variables, because it does not
			// have any Variable Terms. We thus return the Function itself
			// (unchanged).
			return this;
		}
		
		else {
			// We must return a new Function with replaced Terms.
			Term[] newArguments = new Term[this.arguments.size()];
			
			// For each argument Term, replace variables with the given substitution.
			for(int argumentIndex = 0; argumentIndex < this.arguments.size(); argumentIndex++) {
				newArguments[argumentIndex] = (Term) this.arguments.get(argumentIndex).replaceVariables(substitution);
			}
			
			// Create and return the new Function with the same Symbol and new arguments.
			Function substitutedVariableFunction = new Function(this.symbol, newArguments);
			return substitutedVariableFunction;
		}
	}
	
	@Override
	public Substitution unify(Unifiable unifiable, Substitution substitution) {
		
		// If we're trying to unify with a Variable, then just delegate to that
		// Variable's unify method.
		if(unifiable instanceof Variable) {
			return unifiable.unify(this, substitution);
		}
		
		// Otherwise, 
		else {
			
			// This implementation of unify does two different things,
			// depending on whether this Function is a constant or not.
			if(this.isConstant()) {
				
				// If they're the same, then any Substitution will make
				// this and that unify.
				if(this.equals(unifiable)) {
					return substitution;
				}
			}

			// If this Function is not a constant,
			else {
				
				// If unifiable isn't a Function, we can't do anything.
				if(unifiable instanceof Function) {
					
					Function fnUnifiable = (Function) unifiable;
					
					// If the Function Symbols are different, or if they have
					// different arity, they can't be unified.
					if(!this.symbol.equals(fnUnifiable.symbol) || this.arity != fnUnifiable.arity) {
						return null;
					}
					
					// Otherwise, go through each of the Terms and attempt
					// to unify.
					else {

						// Initialize the new Substitution set with existing mappings.
						Substitution theta = new Substitution(substitution);
						for(int argumentIndex = 0; argumentIndex < this.arguments.size(); argumentIndex++) {
							
							// Get the next Term from each Function argument.
							Term t1 = this.arguments.get(argumentIndex);
							Term t2 = fnUnifiable.arguments.get(argumentIndex);
							
							// Attempt to unify.
							theta = t1.unify(t2, theta);
							
							// If we ever get null, we have failed, so fail.
							if(theta == null) {
								return null;
							}
						}
						
						// We have succeeded in the for loop, so return the new substitutions.
						return theta;
					}
				}
			}
		}
		
		// No luck in finding Substitutions! Thus, none are possible.
		return null;
	}
	
	@Override
	public boolean containsVariable(Variable variable, Substitution substitution) {
		
		if(this.isConstant()) {
			// A constant Function cannot contain a Variable.
			return false; 
		}
		
		else {
			// Delegate to the static Term method.
			return Term.containsVariable(this.arguments, variable, substitution);
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Function.clone() is not supported.");
	}

	@Override
	public boolean equals(Object obj) {
		
		// Function equality is determined by Type and signature equality.
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Function other = (Function) obj;
		if (signature == null) {
			if (other.signature != null)
				return false;
		} else if (!signature.equals(other.signature))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((signature == null) ? 0 : signature.hashCode());
		return result;
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
}