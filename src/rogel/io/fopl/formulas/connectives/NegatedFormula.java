package rogel.io.fopl.formulas.connectives;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.formulas.Formula;

/**
 * A NegatedFormula is a Formula whose value is the opposite of the
 * value of another Formula it describes.
 * @author recardona
 */
public class NegatedFormula extends Formula {

	/** The Formula this Object negates. */
	private Formula formula;
	
	/**
	 * Constructs a NegatedFormula with the given Symbol, over the given
	 * Formula. The value of this Formula is the opposite of the parameter
	 * Formula (the Formula it describes).
	 * @param formula the Formula this NegatedFormula describes
	 */
	public NegatedFormula(Formula formula) {
		super(formula.getSymbol());
		this.formula = formula;
		this.value = !this.formula.getValue();
	}
	
	@Override
	public String toString() {
		return "(not " + formula + ")";
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		
		// Calling replaceVariables on Formulas will return Formula-type 
		// Expressions. Thus, get the Formula this NegatedFormula describes, 
		// replace its Variables and create a new NegatedFormula out of it.
		Formula replacedVariableFormula = (Formula) this.formula.replaceVariables(substitution);
		return new NegatedFormula(replacedVariableFormula);
	}

	@Override
	public boolean isLiteral() {
		
		// In FOPL, a literal is an atomic Formula or its negation.
		// For a NegatedFormula to be a literal, the Formula it
		// describes must be atomic.
		if(this.formula.isAtomic()) {
			return true;
		}

		return false;
	}
}
