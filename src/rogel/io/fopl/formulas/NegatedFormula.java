package rogel.io.fopl.formulas;

import rogel.io.fopl.Symbol;

/**
 * A NegatedFormula is a Formula whose value is the opposite of the
 * value of another Formula it describes.
 * @author recardona
 */
public class NegatedFormula extends Formula {

	/** The Formula this class negates. */
	private Formula formula;
	
	public NegatedFormula(String name, Formula formula) {
		super(name);
		this.formula = formula; // The Formula this class describes.
		this.value = !this.formula.value; // This class' value is the opposite of the Formula it describes.
	}
	
	public NegatedFormula(Symbol symbol, Formula formula) {
		super(symbol);
		this.formula = formula;
		this.value = !this.formula.value;
	}
	
}
