/**
 * 
 */
package assignment3;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class MainTest {

	@Test
	public void testInit() {
		Main.initialize();
		assertTrue(Main.words != null && Main.bfsQueue != null && Main.markedWords != null && Main.neighbors != null);
	}
	
	@Test
	public void bfsNormalTest() {
		Main.initialize();
		ArrayList<String> test = new ArrayList<String>();
		test.add("FLOOD"); test.add("BLOOD"); test.add("BROOD");
		test.add("BROOS"); test.add("BROWS"); test.add("FROWS");
		test.add("FRONS"); test.add("FRONT");
		ArrayList<String> test2 = Main.getWordLadderBFS("FLOOD", "FRONT");
		assertEquals(test, test2);
	}

}
