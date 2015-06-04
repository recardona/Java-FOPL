package rogel.io.fopl.semantics.terms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.syntax.terms.Function;
import rogel.io.fopl.syntax.terms.Variable;

public class FunctionInterpretationTest {

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
	
	// FunctionInterpreations:
	private FunctionInterpretation father_of_interpretation;
	private FunctionInterpretation mother_of_interpretation;
	private FunctionInterpretation cAbe_interpretation;
	
	@Before
	public void setUp() throws Exception {
		
		cAbe = new Function("Abe");
		cAbe_interpretation = new FunctionInterpretation(cAbe);
		
		cIsh = new Function("Ish");
		cZak = new Function("Zak");
		cSally = new Function("Sally");
		
		x = new Variable("x");

		father_of = new Function("father_of", x);
		father_of_interpretation = new FunctionInterpretation(father_of);
		father_of_interpretation.map(cAbe, cIsh);
		father_of_interpretation.map(cAbe, cZak);
		father_of_interpretation.map(cAbe, cSally);
		
		mother_of = new Function("mother_of", x);
		mother_of_interpretation = new FunctionInterpretation(mother_of);
		mother_of_interpretation.map(cAbe, cIsh);
		mother_of_interpretation.map(cAbe, cZak);
		mother_of_interpretation.map(cAbe, cSally);
		
	}
	
	public void testConstructor() throws Exception {
		try {
			@SuppressWarnings("unused")
			FunctionInterpretation f = new FunctionInterpretation(null);
		}
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
	}

	@Test
	public void testMap() throws Exception {
		
		try { father_of_interpretation.map(null, cAbe); }
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try { father_of_interpretation.map(cAbe, null); }
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try { father_of_interpretation.map(cAbe, null, null); }
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try { father_of_interpretation.map(cAbe, cIsh, cZak); }
		catch(IllegalArgumentException e) { }
		catch(Exception e) { fail("An IllegalArgumentException should have been caught."); }
		
		try { cAbe_interpretation.map(cAbe, cAbe); }
		catch(UnsupportedOperationException e) { }
		catch(Exception e) { fail("An UnsupportedOperationException should have been caught."); }
	}
	
	@Test
	public void testEvaluate() {
		
		assertEquals("Abe is a constant, and when evaluated should return itself.", cAbe, cAbe_interpretation.evaluate());

		try { father_of_interpretation.evaluate(null); }
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try { father_of_interpretation.evaluate(null, null); }
		catch(NullPointerException e) { }
		catch(Exception e) { fail("A NullPointerException should have been caught."); }
		
		try { father_of_interpretation.evaluate(cAbe, cIsh); }
		catch(IllegalArgumentException e) { }
		catch(Exception e) { fail("An IllegalArgumentException should have been caught."); }
	}
	
	@Test
	public void testMapEvaluate() {
		assertEquals("Abe is Ish's father", cAbe, father_of_interpretation.evaluate(cIsh));
		assertEquals("Abe is Zak's father", cAbe, father_of_interpretation.evaluate(cZak));
		assertEquals("Abe is Sally's father", cAbe, father_of_interpretation.evaluate(cSally));
		assertNull("Abe has no father.", father_of_interpretation.evaluate(cAbe));
	}
	
	@Test
	public void testEquals() {
		assertNotEquals("These two FunctionInterpretations should be unequal.", father_of_interpretation, mother_of_interpretation);
		
		FunctionInterpretation alternate_father_of_interpretation = new FunctionInterpretation(father_of);
		alternate_father_of_interpretation.map(cAbe, cIsh);
		alternate_father_of_interpretation.map(cAbe, cZak);
		alternate_father_of_interpretation.map(cAbe, cSally);
		assertEquals("These two FunctionInterpretations should be equal.", alternate_father_of_interpretation, father_of_interpretation);
	}
}
