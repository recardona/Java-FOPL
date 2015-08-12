package rogel.io.fopl.proof;

import java.util.HashMap;

import rogel.io.fopl.terms.Variable;
import rogel.io.util.VarargsUtils;

/**
 * The RuleSet is the logic base, a list of HornClauses that are used for the construction of the 
 * proof tree during resolution. 
 * 
 * @author recardona
 */
public class RuleSet {
	
	/** The HornClauses that define the logic basis for theorem proving. */
	private HornClause[] rules;
	
	/**
	 * Constructs a RuleSet from a list of HornClauses.
	 * 
	 * @param rules A varargs of HornClause objects that collectively define the logic base for
	 * 	resolution, not null.
	 */
	public RuleSet(HornClause... rules) {
		VarargsUtils.throwExceptionOnNull((Object[]) rules);
		this.rules = rules;
	}
	
	/**
	 * Returns the HornClause rule at the given index within the RuleSet.
	 * 
	 * @param index The non-negative index of the rule within the RuleSet to get.
	 * @return a HornClause at the given index.
	 */
	public HornClause getRule(int index) {
		return this.rules[index];
	}
	
	/**
	 * Returns the HornClause rule at the given index within the RuleSet with its Variables 
	 * standardized apart.
	 * 
	 * @param index The non-negative index of the rule within the RuleSet to get.
	 * @return a HornClause with Variables that have been standardized apart.
	 */
	public HornClause getRuleStandardizedApart(int index) {		
		HornClause rule = (HornClause) this.rules[index];
		HashMap<Variable, Variable> standardizedVariables = new HashMap<Variable, Variable>();
		HornClause standardizedApartRule = (HornClause) rule.standardizeVariablesApart(standardizedVariables);
		return standardizedApartRule;
	}
	
	/**
	 * Returns the number of HornClause rules within this RuleSet.
	 * 
	 * @return the number of HornClause rules within this RuleSet.
	 */
	public int getRuleCount() {
		return rules.length;
	}
}
