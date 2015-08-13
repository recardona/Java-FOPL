package rogel.io.fopl.terms;

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
    public void testEquals() {
        assertEquals("father_of and father_of should be the same", father_of, father_of);
        assertNotEquals("mother_of and father_of should be different", father_of, mother_of);
    }

}
