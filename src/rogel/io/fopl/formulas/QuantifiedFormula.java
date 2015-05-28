package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.terms.Variable;

/**
 * A QuantifiedFormula is a Formula that provides additional meaning over the
 * Variables it quantifies over. There are only two types of quantified formulas
 * in FOPL: universal ("forall") and existential ("exists").
 * @author recardona
 */
public class QuantifiedFormula extends Formula {

	/** The Variable this Formula quantifies with. */
	private Variable quantifierVariable;
	
	/** The Formula this Formula quantifies over. */
	private Formula quantifiedFormula;
	
	/**
	 * Constructs a new universally QuantifiedFormula with the given Variable
	 * over the given Formula.
	 * @param quantifierVariable the Variable this Formula quantifies with.
	 * @param quantifiedFormula the Formula this Formula quantifies over.
	 * @return the universally QuantifiedFormula.
	 */
	public static QuantifiedFormula newForall(Variable quantifierVariable, Formula quantifiedFormula) {
		return new QuantifiedFormula("forall", quantifierVariable, quantifiedFormula);
	}	

	/**
	 * Constructs a new existentially QuantifiedFormula with the given Variable
	 * over the given Formula.
	 * @param quantifierVariable the Variable this Formula quantifies over.
	 * @param quantifiedFormula the Formula this Formula quantifies over.
	 * @return the existentially Quantified Formula.
	 */
	public static QuantifiedFormula newExists(Variable quantifierVariable, Formula quantifiedFormula) {
		return new QuantifiedFormula("exists", quantifierVariable, quantifiedFormula);
	}

	/**
	 * Private constructor for QuantifiedFormulas.
	 * @param name the name of the Quantifier.
	 * @param quantifierVariable the Variable this Formula quantifies over.
	 * @param quantifiedFormula the Formula this Formula quantifies over.
	 */
	private QuantifiedFormula(String name, Variable quantifierVariable, Formula quantifiedFormula) {
		super(name);
		this.quantifierVariable = quantifierVariable;
		this.quantifiedFormula = quantifiedFormula;
	}
	
	/**
	 * @return the quantifier Variable of this Formula.
	 */
	public Variable getQuantifierVariable() {
		return quantifierVariable;
	}
	
	/**
	 * @return the quantified Formula of this Formula.
	 */
	public Formula getQuantifiedFormula() {
		return quantifiedFormula;
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
