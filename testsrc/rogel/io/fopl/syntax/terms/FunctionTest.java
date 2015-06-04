package rogel.io.fopl.syntax.terms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
		mother_of = new Function("mother_of", x);
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
	public void testIsConstant() {
		assertTrue("cAbe is a 0-ary Function, and thus a constant.", cAbe.isConstant());
		assertFalse("father_of is a 1-ary Function, and thus not a constant.", father_of.isConstant());
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
