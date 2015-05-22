package rogel.io.fopl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Variable;

public class UnifiableTest {

	private Variable x;
	private Variable y;
	private Variable z;
	
	private Function f_x;
	private Function f_z;
	
	@Before
	public void setUp() throws Exception {
		x = new Variable("x");
		y = new Variable("y");
		z = new Variable("z");
		
		f_x = new Function("f", x);
		f_z = new Function("f", z);
	}

	@Test
	public void testUnify() {
		
		Substitution identity = new Substitution();
		
		Substitution mostGeneralUnifier = f_x.unify(f_z, identity);
		assertNotNull(mostGeneralUnifier);
		
		HashMap<Variable, Unifiable> expectedBindings = new HashMap<Variable, Unifiable>();
		expectedBindings.put(x, z);
		assertEquals("To make f(x) unify with f(z), we need the binding {x/z} or {z/x}.", expectedBindings, mostGeneralUnifier.getBindings());

		mostGeneralUnifier = f_x.unify(y, identity);
		expectedBindings.clear();
		expectedBindings.put(y, f_x);
		assertEquals("To make f(x) unify with y, we need the binding {f(x)/y} (read 'f(x)' for 'y')", expectedBindings, mostGeneralUnifier.getBindings());
		
		mostGeneralUnifier = y.unify(f_x, identity);
		assertEquals("Unify is commutative, so we should obtain the same result.", expectedBindings, mostGeneralUnifier.getBindings());
	}

	@Test
	public void testContainsVariable() {
		fail("Not yet implemented");
	}

}
