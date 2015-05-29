package rogel.io.fopl.programming.horn;

import java.util.Arrays;
import java.util.List;

import rogel.io.fopl.syntax.Expression;
import rogel.io.fopl.syntax.Substitution;
import rogel.io.fopl.syntax.formulas.Formula;
import rogel.io.fopl.syntax.formulas.connectives.Disjunction;
import rogel.io.util.VarargsUtils;

/**
 * A Clause is an Expression formed from a Disjunction of a finite collection 
 * of literals. It is thus a Formula whose value is true whenever at least one
 * of the literals that form it is true.
 * @author recardona
 */
public abstract class Clause extends Formula {

	/** The literals that make up this Clause. */
	protected List<Formula> literals;
	
	/**
	 * Constructs a Clause from the parameter literal Formulas. If any of the
	 * Formulas is not literal, this constructor throws an Exception.
	 * @param literal1 the first literal Formula of the Clause.
	 * @param literal2 the second literal Formula of the Clause.
	 * @param moreLiterals additional literal Formulas.
	 */
	protected Clause(Formula literal1, Formula literal2, Formula... moreLiterals) {
		super("nary-or");
		
		// Check the arguments for non-literal Formulas.
		throwExceptionOnNonLiteral(literal1, literal2);
		throwExceptionOnNonLiteral(moreLiterals);
		
		// Copy all the literals into an array.
		Formula[] literals = new Formula[2 + moreLiterals.length];
		literals[0] = literal1;
		literals[1] = literal2;
		for(int literalIndex = 2; literalIndex < literals.length; literalIndex++) {
			literals[literalIndex] = moreLiterals[literalIndex - 2];
		}
		
		// Set the Clause literals instance field as a List.
		this.literals = Arrays.asList(literals);
		
		// Set the value of this Formula as the disjunction of all literals.
		this.value = false;
		for(Formula literal : this.literals) {
			this.value |= literal.getValue(); // 'or'-equals.
		}
	}

	@Override
	public Expression replaceVariables(Substitution substitution) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Throws an Exception if any of the parameter Formulas is not literal.
	 * @param formulas the Formulas to check.
	 * @throws NullPointerException if either the Formulas argument is null or 
	 * 	contains a null element.
	 * @throws IllegalArgumentException if any of the Formulas is not a literal.
	 */
	private static void throwExceptionOnNonLiteral(Formula... formulas) throws NullPointerException, IllegalArgumentException {
		
		// Check for null arguments. This throws the NullPointerException.
		VarargsUtils.throwExceptionOnNull((Object[]) formulas);
		
		// Check each formula and see if you find a non-literal.
		for(Formula formula : formulas) {
			if(!formula.isLiteral()) {
				throw new IllegalArgumentException("Formula " + formula + " is not a literal.");
			}
		}
	}

}
