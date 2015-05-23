package rogel.io.fopl;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.formulas.Predicate;
import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Variable;

public class UnifiableTest {

	private Variable x;
	private Variable y;
	private Variable z;
	
	private Function f_x;
	private Function f_y;
	private Function f_z;
	private Function f_f_x;
	private Function f_f_y;
	private Function g_z;
	private Function f_g_z;
	
	private Predicate P_1;
	private Predicate P_2;
	private Predicate P_3;
	private Predicate P_4;
	private Predicate P_5;
	private Predicate P_6;
	
	private Substitution identity;
	private Substitution mostGeneralUnifier;
	private HashMap<Variable, Unifiable> expectedBindings;
	
	@Before
	public void setUp() throws Exception {
		x = new Variable("x");
		y = new Variable("y");
		z = new Variable("z");
		
		f_x = new Function("f", x);
		f_y = new Function("f", y);
		f_z = new Function("f", z);
		g_z = new Function("g", z);
		
		f_f_x = new Function("f", f_x);
		f_f_y = new Function("f", f_y);
		f_g_z = new Function("f", g_z);
		
		P_1 = new Predicate("P", f_x, y); // P(f(x), y)
		P_2 = new Predicate("P", y, f_z); // P(y, f(z))
		P_3 = new Predicate("P", f_x, f_f_y); // P(f(x), f(f(y)))
		P_4 = new Predicate("P", f_y, f_g_z); // P(f(y), f(g(z)))
		P_5 = new Predicate("P", x, f_f_x); // P(x, f(f(x)))
		P_6 = new Predicate("P", y, y); // P(y, y)
		
		identity = new Substitution();
	}

	@Test
	public void testUnify() {
				
		mostGeneralUnifier = f_x.unify(f_z, identity);
		assertNotNull(mostGeneralUnifier);
		
		expectedBindings = new HashMap<Variable, Unifiable>();
		expectedBindings.put(x, z);
		assertEquals("To make f(x) unify with f(z), we need the binding {z/x} (read 'z' for 'x').", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = f_x.unify(y, identity);
		expectedBindings.put(y, f_x);
		assertEquals("To make f(x) unify with y, we need the binding {f(x)/y} (read 'f(x)' for 'y')", expectedBindings, mostGeneralUnifier.getBindings());
		
		mostGeneralUnifier = y.unify(f_x, identity);
		assertEquals("Unify is commutative, so we should obtain the same result.", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = P_1.unify(P_2, identity);
		expectedBindings.put(y, f_x);
		expectedBindings.put(x, z);
		assertEquals("To make P_1 unify with P_2, we need the bindings {f(x)/y, z/x}", expectedBindings, mostGeneralUnifier.getBindings());
		expectedBindings.clear();
		
		mostGeneralUnifier = P_3.unify(P_4, identity);
		assertNull("This unification fails because it reaches a point where the disagreement set contains two terms, neither of which are variables.", mostGeneralUnifier);

		mostGeneralUnifier = P_5.unify(P_6, identity);
		assertNull("This unification fails because it fails the 'occurs' check.", mostGeneralUnifier);
	}

	@Test
	public void testContainsVariable() {
		fail("Not yet implemented");
	}

}
