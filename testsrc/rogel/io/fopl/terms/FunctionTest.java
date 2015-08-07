package rogel.io.fopl.terms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class FunctionTest {

	// Functions:
	private Function father_of;
	private Function mother_of;
	
	// Constants:
	private Function cAbe;
	private Function cIsh;
	private Function cZak;
	private Function cSally;
	
	// Variables:
	private Variable x;
	
	@Before
	public void setUp() throws Exception {
		cAbe = new Function("Abe");
		cIsh = new Function("Ish");
		cZak = new Function("Zak");
		cSally = new Function("Sally");
		
		x = new Variable("x");

		father_of = new Function("father_of", x);
		father_of.map(cAbe, cIsh);
		father_of.map(cAbe, cZak);
		father_of.map(cAbe, cSally);
		
		mother_of = new Function("mother_of", x);
		mother_of.map(cAbe, cIsh);
		mother_of.map(cAbe, cZak);
		mother_of.map(cAbe, cSally);
	}
	
	@Test
	public void testConstructor() throws Exception {
		try {
			@SuppressWarnings("unused")
			Function f = new Function("f", (Variable) null);
		}
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try {
			@SuppressWarnings("unused")
			Function f = new Function("f", null, null);
		}
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
	}
	
	@Test
	public void testGet() throws Exception {
		
		Function f_of_x = Function.get("f", 1);
		assertNotNull("The Function should have been generated.", f_of_x);
		assertFalse("The Function should not be a constant.", f_of_x.isConstant());
		
		Function f_of_y = Function.get("f", 1);
		assertEquals("These two objects should be equal.", f_of_x, f_of_y);
		assertSame("These two objects should be the exact same Function.", f_of_x, f_of_y);
		
		Function cF = Function.get("f", 0);
		assertNotNull("The Function should have been generated.", cF);
		assertTrue("The Function should be a constant.", cF.isConstant());
		assertNotEquals("Despite having the same Symbol, these are different Functions due to arity.", cF, f_of_x);	
	}
	
	
	@Test
	public void testMap() {
		Function cBob = new Function("Bob");
		father_of.map(cBob, cAbe);
		assertEquals("Bob is Abe's father", cBob, father_of.evaluate(cAbe));
	}
	
	@Test
	public void testIsConstant() {
		assertTrue("cAbe is a 0-ary Function, and thus a constant.", cAbe.isConstant());
		assertFalse("father_of is a 1-ary Function, and thus not a constant.", father_of.isConstant());
	}

	@Test
	public void testEvaluate() {
		Term fatherOfIsh = father_of.evaluate(cIsh);
		assertTrue("Abe is Ish's father", (fatherOfIsh.equals(cAbe)));
		
		Term fatherOfZak = father_of.evaluate(cZak);
		assertTrue("Abe is Zak's father", (fatherOfZak.equals(cAbe)));
		
		Term fatherOfSally = father_of.evaluate(cSally);
		assertTrue("Abe is Sally's father", (fatherOfSally.equals(cAbe)));
	}
	
	@Test
	public void testToString() {
		assertEquals("[Function: father_of([Variable: x])]", father_of.toString());
	}
	
	@Test
	public void testEquals() {
		assertEquals("father_of and father_of should be the same", father_of, father_of);
		assertNotEquals("mother_of and father_of should be different", father_of, mother_of);
	}

}
