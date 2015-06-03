package rogel.io.fopl.semantics.terms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import rogel.io.fopl.syntax.terms.Function;
import rogel.io.fopl.syntax.terms.Term;
import rogel.io.util.VarargsUtils;

/**
 * A FunctionInterpreation is a concrete function over a domain of discourse. It is a relation 
 * between a set of inputs and a set of outputs, with each input related to exactly one output. 
 * @author recardona
 */
public class FunctionInterpretation {
	
	/** The collection of all input-output pairs for this FunctionInterpreation. */
	private HashMap<List<Term>, Term> graph;
	
	/** The Function this Object serves as an interpretation for. */
	private Function function;
	
	/** The number of arguments this FunctionInterpretation admits. */
	private int arity;
	
	/**
	 * Constructs a FunctionInterpretation for the given Function.
	 * @param function the Function for which this interpretation is built.
	 */
	public FunctionInterpretation(Function function) {
		
		if(function == null) {
			throw new IllegalArgumentException("Cannot construct FunctionInterpretation for null "
					+ "Function.");
		}
		
		this.graph = new HashMap<List<Term>, Term>();
		this.function = function;
		this.arity = function.getArity();
	}
	
	/**
	 * Associates the argument(s) to the value. This operation expands this 
	 * FunctionInterpretation's domain by the arguments and co-domain by the value.
	 * <p>
	 * For example, if {@code x} and {@code y} are Terms, and {@code f} is a 
	 * FunctionInterpretation, calling {@code f.map(y, x)} implies that subsequently calling {@code
	 * f.evaluate(x)} will return {@code y}.
	 * @param value a Term that will be in the co-domain of this FunctionInterpretation mapped to 
	 * 	the argument Terms.
	 * @param firstArgument a Term that will be in the domain of this FunctionInterpretation.
	 * @param otherArguments a varargs of Terms that will serve as additional arguments to this 
	 * 	Function.
	 * @throws IllegalArgumentException if the number of arguments does not match the arity of this
	 * 	FunctionInterpretation.
	 * @throws UnsupportedOperationException if the underlying Function is a constant (i.e. a 0-ary
	 * 	Function).
	 * @see {@code FunctionInterpretation#evaluate(Term...)}
	 */
	public void map(Term value, Term firstArgument, Term... otherArguments) {
		
		if(this.function.isConstant()) {
			throw new UnsupportedOperationException("Cannot map arguments to values for constant "
					+ "Functions.");
		}
		
		if( (1 + otherArguments.length) != this.arity ) {
			throw new IllegalArgumentException("Number of arguments (" + (1 + 
					otherArguments.length) + ") does not match this FunctionInterpretation's "
							+ "defined arity of " + this.arity);
		}
		
		if(value == null) {
			throw new IllegalArgumentException("Attempted to map to a null value in "
					+ "FunctionInterpretation's co-domain.");
		}
		
		if(firstArgument == null || VarargsUtils.containsNull( (Object[]) otherArguments)) {
			throw new IllegalArgumentException("Arguments in FunctionInterpretation's domain cannot"
					+ "be null.");
		}
		
		// Prepare the argument list. (arity should be 1 + otherArguments.length)
		List<Term> argumentList = new ArrayList<Term>(this.arity);
		argumentList.add(firstArgument);
		argumentList.addAll(Arrays.asList(otherArguments));
		
		// Register the relation in this FunctionInterpretation's graph.
		this.graph.put(argumentList, value);
	}
	
	/**
	 * Evaluates the FunctionInterpretation on the parameter argument(s).
	 * <p>
	 * If the underlying Function is constant, the parameters are ignored, and the method returns
	 * the Function itself. Otherwise, this method returns the value mapped to the parameter
	 * arguments.
	 * @param arguments
	 * @return
	 */
	public Term evaluate(Term... arguments) {
		return null;
	}
	
	
	
}
