package rogel.io.fopl.terms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;
import rogel.io.util.VarargsUtils;

/**
 * A Function is a function between a set of inputs and a set of outputs. In
 * FOPL, the inputs and outputs are all Symbols. 
 * @author recardona
 */
public class Function extends Term {
		
	/** The number of arguments this Function has. */
	private int arity; 
	
	/** The signature of this Function, defined as the pair: (Symbol, arity).*/
	private Pair<Symbol, Integer> signature;
	
	/** The placeholder terms this Function applies to; e.g. in "f(x)" the argument would be "x". */
	private List<Term> arguments; 
	
	/** The underlying relation of this Function object. */
	private HashMap<List<Term>, Term> relation;
	
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
			this.relation = new HashMap<List<Term>, Term>();	// Declare a new relation.	
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
				
		// Place the mapping in this relation.
		this.relation.put(argumentList, value);		
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
						
			// Return the mapping you find there given the argument List.
			return this.relation.get(argumentList);
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Unifiable#unify(rogel.io.fopl.Unifiable, rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Unifiable#containsVariable(rogel.io.fopl.terms.Variable, rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
	 */
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
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
	 */
	@Override
	public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
		
		// This method does one of two things, depending 
		// on whether this is a constant Function or not.
		
		if(this.isConstant()) {
			// Each constant returns itself.
			return this;
		}
		
		else {
			// We must return a new Function with standardized variables in the Terms.
			Term[] newArguments = new Term[this.arguments.size()];
			
			// For each argument Term, standardized its variables.
			for(int argumentIndex = 0; argumentIndex < this.arguments.size(); argumentIndex++) {
				Term argument = this.arguments.get(argumentIndex);
				Expression standardizedVariableExpression = argument.standardizeVariablesApart(newVariables);
				newArguments[argumentIndex] = (Term) standardizedVariableExpression;
			}
			
			// Create and return the new Function with the same Symbol and new arguments.
			Function standardizedVariableFunction = new Function(this.symbol, newArguments);
			return standardizedVariableFunction;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Function.clone() is not supported.");
	}

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.terms.Term#equals(java.lang.Object)
	 */
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

	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.terms.Term#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((signature == null) ? 0 : signature.hashCode());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.terms.Term#toString()
	 */
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
