package rogel.io.fopl;

import java.util.HashMap;
import java.util.Map.Entry;

import rogel.io.fopl.terms.Variable;
import rogel.io.util.VarargsUtils;

/**
 * A Substitution is a syntactic transformation on Expressions. It consists of a mapping of a set 
 * of Variables to a set of Unifiable Objects. It serves as a solution of a unification/resolution 
 * problem.
 * 
 * @author recardona
 */
public final class Substitution {
    
    /** The mappings between Variables and Unifiables. */
    private HashMap<Variable, Unifiable> bindings;
    
    /**
     * Attempts to find the Substitution that unifies (makes syntactically equivalent) the Unifiable 
     * arguments. If no such Substitution exists, this method returns null. 
     * <p>
     * This method is meant as an alternate way to perform the unify method outlined by the 
     * Unifiable interface. For example, calling {@code Substitution.unify(u1, u2)} is equivalent to 
     * calling {@code u1.unify(u2, substitution)}. This method, however, takes care of providing the 
     * initial substitution context, and groups the chain of method calls that would be needed to 
     * unify the method's arguments sequentially.
     * 
     * @param arg1 the first Unifiable argument to unify, not null.
     * @param arg2 the second Unifiable argument to unify, not null.
     * @param moreArgs additional Unifiable arguments, not null.
     * @return a Substitution that unifies the arguments, or null if no Substitution exists.
     */
    public static Substitution unify(Unifiable arg1, Unifiable arg2, Unifiable... moreArgs) {

        // Declare two Substitutions used for unification.
        Substitution identity = new Substitution();
        Substitution mostGeneralUnifier = null;
        
        // Perform unification for the first two arguments.
        mostGeneralUnifier = arg1.unify(arg2, identity);
        
        // Unify the rest of the arguments.
        VarargsUtils.throwExceptionOnNull((Object[]) moreArgs);
        for(Unifiable u : moreArgs) {
            mostGeneralUnifier = arg1.unify(u, mostGeneralUnifier);
        }

        // Return the most general unifier.
        return mostGeneralUnifier;
    }
    
    /**
     * Creates an empty Substitution set.
     */
    public Substitution() {
        this.bindings = new HashMap<Variable, Unifiable>();
    }
    
    /**
     * Creates a non-empty Substitution set, comprised of the bindings in the
     * parameter Substitution set.
     * 
     * @param s the Substitution set to initialize with, not null.
     */
    public Substitution(Substitution s) {
        this.bindings = new HashMap<Variable, Unifiable>(s.getBindings());
    }
    
    /**
     * Clears this Substitution of all bindings.
     */
    public void clear() {
        this.bindings.clear();
    }
    
    /**
     * Binds the Term to the Variable.
     * 
     * @param variable the Variable to be bound, not null.
     * @param unifiable the Term that will be bound to the variable, not null.
     */
    public void add(Variable variable, Unifiable unifiable) {
        
        if(variable == null) {
            throw new IllegalArgumentException("Cannot bind to null variable.");
        }
        
        if(unifiable == null) {
            throw new IllegalArgumentException("Cannot bind with null unifiable.");
        }
        
        this.bindings.put(variable, unifiable);
    }
    
    /**
     * Gets the binding for this Variable. This binding is a Unifiable Object. If no such binding
     * exists, this method returns null.
     * 
     * @param variable the Variable to lookup, not null.
     * @return the Unifiable Object bound to the variable, or null if the variable is not bound.
     */
    public Unifiable getBinding(Variable variable) {
        return (Unifiable) this.bindings.get(variable);
    }
    
    /**
     * Checks whether the parameter Variable is bound in this Substitution.
     * 
     * @param variable the Variable to check for bindings, not null.
     * @return true if the Variable is bound, false otherwise.
     */
    public boolean isBound(Variable variable) {
        
        if(variable == null) {
            throw new IllegalArgumentException("Binding against a null variable is not possible.");
        }
        
        return (this.bindings.get(variable) != null);
    }
    
    /**
     * A Substitution is "ground" if all terms are ground terms (no variables).
     *
     * @return true if this Substitution is ground
     */
    public boolean isGround() {
        for(Unifiable u : this.getBindings().values()) {
            if(u instanceof Variable) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the Variable, Unifiable object pairs that are mapped in this Substitution. 
     * 
     * @return the bindings this Substitution represents, as a HashMap of Variables to Unifiable objects.
     */
    public HashMap<Variable, Unifiable> getBindings() {
        return this.bindings;
    }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Symbol.clone() is not supported");
    }
    
    /**
     * Compares this Substitution to the parameter object. The result is true if and only if the 
     * argument is another Substitution that contains the same Variable, Unifiable bindings as
     * this object.
     * 
     * @param obj The object to compare this Substitution against.
     * @return true if the given object represents a Substitution with the same Variable, 
     *     Unifiable bindings as this object, false otherwise.
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
        Substitution other = (Substitution) obj;
        if (bindings == null) {
            if (other.bindings != null) {
                return false;
            }
        } else if (!bindings.equals(other.bindings)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this Substitution. The hash code for a Substitution object is 
     * computed as the hash of the Variable, Unifiable entries in the HashMap used to represent
     * a Substitution set times a prime number: i.e. {@code getBindings().hashCode() * 31}.
     * 
     *  @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((bindings == null) ? 0 : bindings.hashCode());
        return result;
    }
    
    /**
     * Returns a String representation of this Substitution, which is a set of Variable, Unifiable
     * pairs. Historically, Substitutions have been denoted by the greek letter 'theta', which 
     * precedes the set.
     * 
     * @return a String representation of this Substitution.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\u03F4"); // theta
        builder.append(" = {");
        
        for(Entry<Variable, Unifiable> binding : bindings.entrySet()) {
            builder.append(binding.getValue());
            builder.append("/");
            builder.append(binding.getKey());
            builder.append(", ");
        }
        
        builder.replace(builder.length()-2, builder.length(), "}");
        return builder.toString();
    }
}
