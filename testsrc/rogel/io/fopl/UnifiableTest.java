package rogel.io.fopl;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Variable;

public class UnifiableTest {

	// Variables
	private Variable v;
	private Variable w;
	private Variable x;
	private Variable y;
	private Variable z;
	
	// Constants
	private Function cA;
	private Function cB;
	
	// Functions
	private Function f_of_x;
	private Function f_of_y;
	private Function f_of_z;
	private Function f_of_xAndY;
	private Function f_of_f_of_x;
	private Function f_of_f_of_y;
	private Function f_of_g_of_z;
	private Function f_of_g_of_vAndA;
	private Function g_of_v;
	private Function g_of_z;
	private Function h_of_vAndB;
	private Function h_of_wAndW;
	private Function h_of_wAndB;
	
	// Predicates
	private Predicate P_1;
	private Predicate P_2;
	private Predicate P_3;
	private Predicate P_4;
	private Predicate P_5;
	private Predicate P_6;
	private Predicate Q_1;
	private Predicate Q_2;
	private Predicate Q_3;
	
	private Substitution identity;
	private Substitution mostGeneralUnifier;
	private HashMap<Variable, Unifiable> expectedBindings;
	
	@Before
	public void setUp() throws Exception {
		
		v = new Variable("v");
		w = new Variable("w");
		x = new Variable("x");
		y = new Variable("y");
		z = new Variable("z");
		
		cA = new Function("cA");
		cB = new Function("cB");
		
		f_of_x = new Function("f", x);
		f_of_xAndY = new Function("f", x, y);
		f_of_y = new Function("f", y);
		f_of_z = new Function("f", z);
		g_of_v = new Function("g", v);
		g_of_z = new Function("g", z);
		h_of_vAndB = new Function("h", v, cB);
		h_of_wAndB = new Function("h", w, cB);
		h_of_wAndW = new Function("h", w, w);
		
		f_of_f_of_x = new Function("f", f_of_x);
		f_of_f_of_y = new Function("f", f_of_y);
		f_of_g_of_z = new Function("f", g_of_z);
		
		f_of_g_of_vAndA = new Function("f", g_of_v, cA);
		
		// Unify tests will unify the following sets of Predicates:
		
		// Set 1:
		P_1 = new Predicate("P", f_of_x, y); // P(f(x), y)
		P_2 = new Predicate("P", y, f_of_z); // P(y, f(z))
		
		// Set 2:
		P_3 = new Predicate("P", f_of_x, f_of_f_of_y); // P(f(x), f(f(y)))
		P_4 = new Predicate("P", f_of_y, f_of_g_of_z); // P(f(y), f(g(z)))
		
		// Set 3:
		P_5 = new Predicate("P", x, f_of_f_of_x); // P(x, f(f(x)))
		P_6 = new Predicate("P", y, y); // P(y, y)
		
		// Set 4:
		Q_1 = new Predicate("Q", f_of_g_of_vAndA, h_of_wAndB); // Q( f(g(v),a), h(w,b) )
		Q_2 = new Predicate("Q", f_of_xAndY, h_of_wAndW); // Q( f(x,y), h(w,w) )
		Q_3 = new Predicate("Q", f_of_g_of_vAndA, h_of_vAndB); // Q( f(g(v),a), h(v,b) )
		
		identity = new Substitution();
	}

	@Test
	public void testUnify() {
				
		mostGeneralUnifier = f_of_x.unify(f_of_z, identity);
		assertNotNull(mostGeneralUnifier);
		
		expectedBindings = new HashMap<Variable, Unifiable>();
		expectedBindings.put(x, z);
		assertEquals("To make f(x) unify with f(z), we need the binding {z/x} (read 'z' for 'x').", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = f_of_x.unify(y, identity);
		expectedBindings.put(y, f_of_x);
		assertEquals("To make f(x) unify with y, we need the binding {f(x)/y} (read 'f(x)' for 'y')", expectedBindings, mostGeneralUnifier.getBindings());
		
		mostGeneralUnifier = y.unify(f_of_x, identity);
		assertEquals("Unify is commutative, so we should obtain the same result.", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = P_1.unify(P_2, identity);
		expectedBindings.put(y, f_of_x);
		expectedBindings.put(x, z);
		assertEquals("To make P_1 unify with P_2, we need the bindings {f(x)/y, z/x}", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = P_3.unify(P_4, identity);
		assertNull("This unification fails because it reaches a point where the disagreement set contains two terms, neither of which are variables.", mostGeneralUnifier);

		// Occurs check
		mostGeneralUnifier = P_5.unify(P_6, identity);
		assertNull("This unification fails because it fails the 'occurs' check.", mostGeneralUnifier);
		
		// Chaining unifications
		mostGeneralUnifier = Q_1.unify(Q_2, identity);
		mostGeneralUnifier = Q_1.unify(Q_3, mostGeneralUnifier);
		expectedBindings.put(x, g_of_v);
		expectedBindings.put(y, cA);
		expectedBindings.put(v, cB);
		expectedBindings.put(w, cB);
		assertNotNull("This unification sequence does have a most general unifier.", mostGeneralUnifier);
		assertEquals(expectedBindings, mostGeneralUnifier.getBindings());
		
		// Compare with the unify provided by Substitution.
		assertEquals(mostGeneralUnifier, Substitution.unify(Q_1, Q_2, Q_3));
	}
}