package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;

/**
 * A NegatedFormula is a Formula whose value is the opposite of the
 * value of another Formula it describes.
 * @author recardona
 */
public class NegatedFormula extends Formula {

	/** The Formula this Object negates. */
	private Formula formula;
	
	/**
	 * Constructs a NegatedFormula with the given name, over the given Formula.
	 * If the name is a String that did not already exist within the domain of
	 * discourse (i.e. was already defined as a Symbol), then a new Symbol is
	 * created and added to the domain of discourse. The value of this Formula
	 * is the opposite of the parameter Formula (the Formula it describes). 
	 * @param name the name of the Formula
	 * @param formula the Formula this NegatedFormula describes
	 */
	public NegatedFormula(String name, Formula formula) {
		super(name);
		this.formula = formula; // The Formula this class describes.
		this.value = !this.formula.getValue(); // This class' value is the opposite of the Formula it describes.
	}
	
	/**
	 * Constructs a NegatedFormula with the given Symbol, over the given
	 * Formula. The value of this Formula is the opposite of the parameter
	 * Formula (the Formula it describes).
	 * @param symbol the Symbol that represents this NegatedFormula within the
	 *   domain of discourse
	 * @param formula the Formula this NegatedFormula describes
	 */
	public NegatedFormula(Symbol symbol, Formula formula) {
		super(symbol);
		this.formula = formula;
		this.value = !this.formula.getValue();
	}
	
	@Override
	public String toString() {
		return "(not " + formula + ")";
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}
}
