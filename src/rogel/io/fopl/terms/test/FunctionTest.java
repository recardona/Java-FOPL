package rogel.io.fopl.terms.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rogel.io.fopl.terms.Function;
import rogel.io.fopl.terms.Term;
import rogel.io.fopl.terms.Variable;

public class FunctionTest {

	private Function father_of;
	private Function mother_of;
	
	private Function cAbe;
	private Function cIsh;
	private Function cZak;
	private Function cSally;
	
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
	public void testMap() {
		
		Function cBob = new Function("Bob");
		father_of.map(cBob, cAbe);
		assertEquals("Bob is Abe's father", cBob, father_of.evaluate(cAbe));
		
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
	
	public void testEquals() {
		assertNotEquals("mother_of and father_of should be different", father_of, mother_of);
	}

}
