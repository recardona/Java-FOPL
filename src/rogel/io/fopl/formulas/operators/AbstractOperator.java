package rogel.io.fopl.formulas.operators;

import java.util.Arrays;
import java.util.List;

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
 * 
 * @author recardona
 */
public abstract class AbstractOperator extends Formula {

	/** The underlying objects this operator applies to. */
	protected List<Formula> operands;
	
	/**
	 * Constructs an AbstractOperator with the given name, over the given operands. If the name is
	 * a String that did not already exist within the domain of discourse (i.e. was already defined
	 * as a Symbol), then a new Symbol is created and added to the domain of discourse. The value
	 * of this Formula depends on the type of AbstractOperator and its constituent operands.
	 * 
	 * @param name The name of the Formula, not null.
	 * @param operands The Formula objects this operator operates over, not null.
	 */
	protected AbstractOperator(String name, Formula... operands) {
		this(Symbol.get(name), operands);
	}
	
	/**
	 * Constructs an AbstractOperator with the given symbol, over the given operands. The value of 
	 * this Formula depends on the type of AbstractOperator and its constituent operands.
	 * 
	 * @param symbol The Symbol that represents this Formula within the domain of discourse, not 
	 * 	null.
	 * @param operands The Formula objects this operator operates over, not null.
	 */
	protected AbstractOperator(Symbol symbol, Formula... operands) {
		super(symbol);
		VarargsUtils.throwExceptionOnNull((Object[]) operands);
		this.operands = Arrays.asList(operands);
	}
	
	/**
	 * Returns the number of operands this operator is applied to.
	 * 
	 * @return the number of operands this operator is applied to. 
	 */
	public final int operandCount() {
		return this.operands.size();
	}
	
	/**
	 * Returns true if there are no operands this operator is being applied to, false otherwise.
	 * 
	 * @return true if there are no operands this operator is being applied to, false otherwise.
	 */
	public final boolean isEmpty() {
		return this.operands.isEmpty();
	}
	
	/**
	 * Gets the Formula that is at the given index in the list of operands.
	 * 
	 * @param index The non-negative index of the operand Formula to get.
	 * @return the operand Formula at the given index.
	 */
	public final Formula getOperand(int index) {
		return this.operands.get(index);
	}
	
	/**
	 * Gets the operand Formula at the head of the list of operands. This method is equivalent to
	 * calling {@code this.getOperand(0)}.
	 * 
	 * @return the operand Formula at index 0.
	 */
	public final Formula getOperatorHead() {
		return this.operands.get(0);
	}
	
	/**
	 * Constructs a new operator applied to {@code this.operandCount() - 1} operands: the operands
	 * obtained by removing the operand Formula returned by {@code this.getOperatorHead()}.
	 * 
	 * @return a new operator applied to the tail of this operator, or null if no tail can exist.
	 */
	public abstract AbstractOperator getOperatorTail();
	
	/**
	 * Returns true if and only if this Formula is atomic. Because Formulae that have operators
	 * applied to them are never atomic, this method always returns false.
	 * 
	 * @return false, always.
	 */
	@Override
	public final boolean isAtomic() {
		return false; // Operators are never atomic!
	}
	
	/*
	 * (non-Javadoc)
	 * @see rogel.io.fopl.formulas.Formula#isLiteral()
	 */
	@Override
	public boolean isLiteral() {
		return false; // AndOperators are not literals.
	}
	
	/**
	 * Returns a String representation of this operator, which is a Lisp-style String of the form
	 * {@code (operator_symbol operand1 operand2 ...)}.
	 * 
	 * @return a String representation of this operator.
	 */
	@Override
	public final String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(this.symbol);
		for(Formula operand : this.operands) {
			builder.append(" ");
			builder.append(operand.toString());
		}
		builder.append(")");
		return builder.toString();
	}
}
