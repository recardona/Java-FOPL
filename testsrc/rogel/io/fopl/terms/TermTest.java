package rogel.io.fopl.terms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TermTest {

	private Function father_of;
	private Function mother_of;
	
	private Function cAbe;
	private Function cIsh;
	private Function cZak;
	private Function cSally;
	
	private Variable x;
	
	private Set<Term> constantSet;
	private Set<Term> unarySet;
	private Set<Term> variedSet;
	
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
		
		constantSet = new HashSet<Term>();
		constantSet.add(cAbe);
		constantSet.add(cIsh);
		constantSet.add(cZak);
		constantSet.add(cSally);
		
		unarySet = new HashSet<Term>();
		unarySet.add(father_of);
		unarySet.add(mother_of);
		
		variedSet = new HashSet<Term>();
		variedSet.add(cAbe);
		variedSet.add(father_of);
	}

	@Test
	public void testFlatten() {
	
		Comparator<Term[]> arrayComparator = new Comparator<Term[]>() {
			@Override
			public int compare(Term[] o1, Term[] o2) {
				return Term.getLexicographicTermComparator().compare(o1[0], o2[0]);
			}
		};
		
		Term[][] flattenedConstantSet = Term.flatten(constantSet);
		Term[][] expectedFlattenedConstantSet = { {cSally}, {cZak}, {cAbe}, {cIsh} };
		Arrays.sort(flattenedConstantSet, arrayComparator);
		Arrays.sort(expectedFlattenedConstantSet, arrayComparator);
		
		assertTrue(Arrays.deepEquals(flattenedConstantSet, expectedFlattenedConstantSet));
		assertEquals(1, flattenedConstantSet[0].length);
		
		Term[][] flattenedUnarySet = Term.flatten(unarySet);
		Term[][] expectedFlattenedUnarySet = { {father_of, x}, {mother_of, x} };
		Arrays.sort(flattenedUnarySet, arrayComparator);
		Arrays.sort(expectedFlattenedUnarySet, arrayComparator);
		
		assertTrue(Arrays.deepEquals(flattenedUnarySet, expectedFlattenedUnarySet));
		assertEquals(2, flattenedUnarySet[0].length);
		
		
		Term[][] flattenedVariedSet = Term.flatten(variedSet);
		Term[][] expectedFlattenedVariedSet = { {cAbe, null}, {father_of, x} };
		Arrays.sort(flattenedVariedSet, arrayComparator);
		Arrays.sort(expectedFlattenedVariedSet, arrayComparator);
	
		System.out.println(Arrays.deepToString(flattenedVariedSet));
		System.out.println(Arrays.deepToString(expectedFlattenedVariedSet));
		
		assertTrue(Arrays.deepEquals(flattenedVariedSet, expectedFlattenedVariedSet));
	}

}
