import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class InspectorTest {

	Inspector i;
	TestClass2 t2;
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		
		
		t2 = new TestClass2();
		i = new Inspector(t2.getClass());
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
		
	}

	@After
	public void tearDown() throws Exception {
		
		i = null;
	    System.setOut(null);
	    System.setErr(null);
	}
	
	/*
	 * Tests that the inspectFieldsTest outputs the corrent infomration to the screen
	 */
	@Test
	public void inspectFieldsTest() {
		
		i.inspectFields(t2.getClass());
		assertEquals("\nField 1: \n\tType: int\n\tModifiers: public\n\tValue: 5\n", outContent.toString());
	}
	
	/*
	 * Tests that the inspectConstructors method outputs the corrent information 
	 */
	@Test
	public void inspectConstructorTest() {
		
		i.inspectConstructors(t2.getClass());
		assertEquals("\nConstructor 1:\n\tNo Parameter Constructor\n\tModifiers: public\n"
				+ "\nConstructor 2:\n\tNo Parameter Constructor\n\tModifiers: public\n", outContent.toString());
	}
	
	@Test
	public void inspectMethodTest() {
		
		String m1 = "\nName of method 1: randomMethod"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public"
				+ "\n";
		
		String m2 = "\nName of method 2: finalize"
				+ "\n\tExceptions:"
				+ "\n\t\tName of exception 1: java.lang.Throwable"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: protected"
				+ "\n";
		
		String m3 = "\nName of method 3: wait"
				+ "\n\tExceptions:"
				+ "\n\t\tName of exception 1: java.lang.InterruptedException"
				+ "\n\tParameters:"
				+ "\n\t\tType of parameter 1: long"
				+ "\n\t\tType of parameter 2: int"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public final"
				+ "\n";
		
		
		String m4 = "\nName of method 4: wait"
				+ "\n\tExceptions:"
				+ "\n\t\tName of exception 1: java.lang.InterruptedException"
				+ "\n\tParameters:"
				+ "\n\t\tType of parameter 1: long"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public final native"
				+ "\n";
		
		String m5 = "\nName of method 5: wait"
				+ "\n\tExceptions:"
				+ "\n\t\tName of exception 1: java.lang.InterruptedException"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public final"
				+ "\n";
		
		String m6 = "\nName of method 6: equals"
				+ "\n\tThere are no exceptions"
				+ "\n\tParameters:"
				+ "\n\t\tType of parameter 1: java.lang.Object"
				+ "\n\tReturn type: boolean"
				+ "\n\tModifiers: public"
				+ "\n";
		
		
		String m7 = "\nName of method 7: toString"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: java.lang.String"
				+ "\n\tModifiers: public"
				+ "\n";

		String m8 = "\nName of method 8: hashCode"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: int"
				+ "\n\tModifiers: public native"
				+ "\n";

		String m9 = "\nName of method 9: getClass"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: java.lang.Class"
				+ "\n\tModifiers: public final native"
				+ "\n";

		String m10 = "\nName of method 10: clone"
				+ "\n\tExceptions:"
				+ "\n\t\tName of exception 1: java.lang.CloneNotSupportedException"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: java.lang.Object"
				+ "\n\tModifiers: protected native"
				+ "\n";

		String m11 = "\nName of method 11: registerNatives"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: private static native"
				+ "\n";
		

		String m12 = "\nName of method 12: notify"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public final native"
				+ "\n";
		

		String m13 = "\nName of method 13: notifyAll"
				+ "\n\tThere are no exceptions"
				+ "\n\tThere are no parameters"
				+ "\n\tReturn type: void"
				+ "\n\tModifiers: public final native"
				+ "\n";
		
		
		i.inspectMethods(t2.getClass());
		assertEquals(m1+m2+m3+m4+m5+m6+m7+m8+m9+m10+m11+m12+m13, outContent.toString());
		
		
		/*Need to inspect other methods next and make sure they are corrent */
		/* recursion is current disable in inspector.inspectMethods*/
	}

}
