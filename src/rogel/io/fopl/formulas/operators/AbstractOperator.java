package rogel.io.fopl.formulas.operators;

import java.util.ArrayList;
import java.util.Arrays;

import rogel.io.fopl.Symbol;
import rogel.io.fopl.formulas.Formula;
import rogel.io.util.VarargsUtils;

/**
 * An AbstractOperator is a Formula whose value is defined by the type of operator the class describes. 
 * There are three types of operators:
 * <ol>
 * 	<li> conjunction (the 'and' connective)
 * 	<li> disjunction (the 'or' connective)
 * 	<li> negation (the 'not' connective)
 * </ol>
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
	
	/**
	 * @return the number of operands this operator is applied to.
	 */
	public int operandCount() {
		return this.operands.size();
	}
	
	/**
	 * @return true if there are no operands this operator is being applied to.
	 */
	public boolean isEmpty() {
		return this.operands.isEmpty();
	}
	
	/**
	 * Gets the Formula that is at the given index in the list of operands.
	 * @param index the index of the operand Formula to get.
	 * @return the operand Formula at the given index.
	 */
	public Formula getOperand(int index) {
		return this.operands.get(index);
	}
	
	/**
	 * Gets the operand Formula at the head of the list of operands. This method is equivalent to
	 * calling {@code this.getOperand(0)}.
	 * @return the operand Formula at index 0.
	 */
	public Formula getOperatorHead() {
		return this.operands.get(0);
	}
	
	/**
	 * Constructs a new operator applied to {@code this.operandCount() - 1} operands: the operands
	 * obtained by removing the operand Formula returned by {@code this.getOperatorHead()}.
	 * @return a new operator applied to the tail of this operator.
	 */
	public abstract AbstractOperator getOperatorTail();
	
}
