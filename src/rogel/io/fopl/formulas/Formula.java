package rogel.io.fopl.formulas;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Symbol;


/**
 * A Formula is an Expression made up from a Symbol and a boolean value. This class is the base 
 * class for all FOPL Formulas.
 * <p>
 * While in FOPL an interpretation is required in order to assign semantic meaning to Formula-based
 * Expressions, this implementation embeds the Formula's interpretation (i.e. its truth value) in
 * the class itself, such that each Formula object has the capacity to carry its own interpretation
 * with it.
 * @author recardona
 * @see <a href="https://en.wikipedia.org/wiki/Interpretation_%28logic%29">https://en.wikipedia.org/wiki/Interpretation_logic</a>
 */
public abstract class Formula implements Expression {
        
    /** This Formula's truth value. */
    protected Boolean value;
    
    /** The Symbol that uniquely identifies this Formula. */
    protected Symbol symbol;
    
    /**
     * Defines a null-valued Formula with the given name.  If the name is a String that did not 
     * already exist within the domain of discourse (defined as a Symbol), then a new Symbol is 
     * created.
     * @param the name of this Formula, not null.
     */
    protected Formula(String name) {
        this(Symbol.get(name));
    }
    
    /**
     * Defines a null-valued Formula with the given Symbol. 
     * @param symbol the Symbol that represents this Formula within the domain of discourse, not null.
     */
    protected Formula(Symbol symbol) {
        this.symbol = symbol;
        this.value = null;
    }
    
    /**
     * Returns true if and only if this Formula is a literal. A literal is defined to be an atomic
     * Formula or its negation.
     * @return true if this Formula is atomic or is the negation of an atomic Formula, false 
     *     otherwise.
     */
    public abstract boolean isLiteral();
    
    /**
     * Returns true if and only if this Formula is atomic. An atomic Formula (or equivalently, an 
     * 'atom') is a Formula that contains no logical connectives or equivalently a Formula that has
     * no sub-formulas.
     * @return true if this Formula is atomic, false otherwise.
     */
    public abstract boolean isAtomic();
    
    /**
     * Gets the Symbol associated with this Formula.
     * @return the symbol.
     */
    public final Symbol getSymbol() {
        return this.symbol;
    }
        
    /**
     * Gets the boolean value associated with this Formula.
     * @return the value of this Formula.
     */
    public boolean getValue() {
        return this.value;
    }
    
    /**
     * Compares this Formula to the parameter object. The result is true if and only if the
     * argument is another Formula of the same Symbol and with the same boolean value as this
     * object.
     * 
     * @param obj The object to compare this Formula against.
     * @return true if the given object represents a Formula denoted by the same Symbol and with
     *     the same boolean value as this Formula, false otherwise. 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Formula other = (Formula) obj;
        if (symbol == null) {
            if (other.symbol != null) {
                return false;
            }
        } else if (!symbol.equals(other.symbol)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this Formula. The hash code for a Formula object is computed as
     * <p>
     * {@code (int) (31 * (31 + (this.getSymbol().hashCode()))) + this.getValue().hashCode() }
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }    
}
