package rogel.io.fopl.terms;

import java.util.HashMap;

import rogel.io.fopl.Expression;
import rogel.io.fopl.Substitution;
import rogel.io.fopl.Symbol;
import rogel.io.fopl.Unifiable;

/**
 * A Variable is a "placeholder" Term, which can be assigned values through a Substitution set. 
 * Variables also allow FOPL formulas that express quantified ideas. Every Variable that is 
 * constructed is different, even if declared with the same symbolic name.
 * 
 * @author recardona
 */
public final class Variable extends Term {
    
    /** 
     * The Variable class' unique identifier. This number monotonically increases with each
     * constructed Variable object.
     */
    private static int nextId = 1;
    
    /** This Variable's unique identifier. */
    private int id;

    /**
     * Constructs a Variable with the given name. If the name is a String that did not 
     * already exist within the domain of discourse (defined as a Symbol), then a new 
     * Symbol is created.
     * 
     * @param name The name of this Variable.
     */
    public Variable(String name) {
        this(Symbol.get(name));
    }

    /**
     * Constructs a Variable with the given Symbol.
     * 
     * @param symbol The Symbol that represents this Variable within the domain of discourse.
     */    
    public Variable(Symbol symbol) {
        super(symbol);
        this.id = Variable.nextId;
        Variable.nextId++;
    }

    /*
     * (non-Javadoc)
     * @see rogel.io.fopl.Unifiable#unify(rogel.io.fopl.Unifiable, rogel.io.fopl.Substitution)
     */
    @Override
    public Substitution unify(Unifiable unifiable, Substitution substitution) {
        
        if(this.equals(unifiable)) {
            // If they're the same, then anything in the substitution will make
            // this and that unify.
            return substitution;
        }
        
        if(substitution.isBound(this)) {
            // If this Variable is bound within the parameter Substitution,
            // the unification must continue with the bound value.
            return substitution.getBinding(this).unify(unifiable, substitution);
        }
        
        // "Occurs" check:
        if(unifiable.containsVariable(this, substitution)) {
            return null; // Fail!
        }
        
        // At this point, we're attempting to add a new binding. First,
        // initialize a new Substitution with the parameter Substitution.
        Substitution sigma = new Substitution(substitution);
        
        // Add the new binding and return it.
        sigma.add(this, unifiable);
        return sigma;
    }
    
    /*
     * (non-Javadoc)
     * @see rogel.io.fopl.Unifiable#containsVariable(rogel.io.fopl.terms.Variable, rogel.io.fopl.Substitution)
     */
    @Override
    public boolean containsVariable(Variable variable, Substitution substitution) {
        
        if(this.equals(variable)) {
            // If this is equal to the variable, clearly we contain the variable.
            return true;
        }
        
        if(substitution.isBound(this)) {
            // If this Variable is bound within the parameter Substitution,
            // the check must continue with the bound value.
            return substitution.getBinding(this).containsVariable(variable, substitution);
        }
        
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see rogel.io.fopl.Expression#replaceVariables(rogel.io.fopl.Substitution)
     */
    @Override
    public Expression replaceVariables(Substitution substitution) {
        
        // Variables may be bound to other variables, and so if this Variable
        // is bound, this method must search until it finds a constant binding
        // or a final unbound variable.
        if(substitution.isBound(this)) {
            return substitution.getBinding(this).replaceVariables(substitution);
        }
        
        else {
            return this;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see rogel.io.fopl.Expression#standardizeVariablesApart(java.util.HashMap)
     */
    @Override
    public Expression standardizeVariablesApart(HashMap<Variable, Variable> newVariables) {
        
        // Check if the Expression already has a substitute Variable.
        Variable standardizedVariable = newVariables.get(this);
        
        // If not, create one.
        if(standardizedVariable == null) {
            standardizedVariable = new Variable(this.symbol);
            newVariables.put(this, standardizedVariable);
        }
        
        // Return the substitute Variable.
        return standardizedVariable;
    }
    
    /**
     * Compares this Variable to the parameter object. The result is true if and only if the
     * argument is the same exact object (reference-equality).
     * 
     * @param obj The object to compare this Variable against.
     * @return true if the given object is the same exact object (reference-equality).
     */
    @Override
    public boolean equals(Object obj) {
        
        //Variable equality is composed of type, symbol, and id equality.
        
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Variable other = (Variable) obj;
        if (id != other.id)
            return false;
        return true;
    }
    
    /**
     * Returns a hash code for this Variable. The hash code for a Variable object is computed as
     * the integer addition of the Variable's unique id plus a prime number (31).
     * 
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + id;
        return result;
    }

    /**
     * Returns a String representation of this Variable in Lisp-style, with a prepended '?'
     * character and its unique id appended with an underscore.
     * <p>
     * For example, if the variable name is {@code X}, then its String representation will be
     * {@code ?X_1} (assuming only one variable has been declared, namely X itself). 
     * 
     * @return a String representation of this Symbol.
     */
    @Override
    public String toString() {
        return "?"+this.symbol.toString()+"_"+this.id+"";
    }
    
}
