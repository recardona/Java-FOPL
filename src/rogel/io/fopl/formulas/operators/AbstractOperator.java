package rogel.io.fopl.formulas.operators;

import java.util.ArrayList;
import java.util.Arrays;

import rogel.io.fopl.Symbol;
import rogel.io.fopl.formulas.Formula;
import rogel.io.util.VarargsUtils;

/**
 * An AbstractOperator is a Formula whose value is defined by the type of operator the class describes. 
 * There are three types of operators:
 * 1) conjunction (the 'and' connective),
 * 2) disjunction (the 'or' connective),
 * 3) negation (the 'not' connective).
 * @author recardona
 */
public abstract class AbstractOperator extends Formula {

	/** The underlying objects this operator applies to. */
	protected ArrayList<Formula> operands;
	
	/**
	 * Constructs an AbstractOperator with the given name, over the given operands. If the name is
	 * a String that did not already exist within the domain of discourse (i.e. was already defined
	 * as a Symbol), then a new Symbol is created and added to the domain of discourse. The value
	 * of this Formula depends on the type of AbstractOperator and its constituent operands.
	 * @param name the name of the Formula.
	 * @param operands the Goal objects this operator operates over.
	 */
	protected AbstractOperator(String name, Formula... operands) {
		this(Symbol.get(name), operands);
	}
	
	/**
	 * Constructs an AbstractOperator with the given symbol, over the given operands. The value of 
	 * this Formula depends on the type of AbstractOperator and its constituent operands.
	 * @param symbol the Symbol that represents this Formula within the domain of discourse.
	 * @param operands the Goal objects this operator operates over.
	 */
	protected AbstractOperator(Symbol symbol, Formula... operands) {
		super(symbol);
		VarargsUtils.throwExceptionOnNull((Object[]) operands);
		this.operands = (ArrayList<Formula>) Arrays.asList(operands);
	}
}
