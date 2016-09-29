/**
 * 
 */
package assignment3;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class MainTest {

	@Test(timeout = 30000)
	public void testInit() {
		Main.initialize();
		assertTrue(Main.words != null && Main.bfsQueue != null && Main.markedWords != null && Main.neighbors != null);
	}
	
	@Test(timeout = 30000)
	public void testParse() {
		String input = "hello world";
		Scanner scan = new Scanner(input);
		ArrayList<String> expected = new ArrayList<>();
		expected.add("HELLO");
		expected.add("WORLD");
		assertEquals(expected, Main.parse(scan));
	}
	
	@Test(timeout = 30000)
	public void bfsNormalTestPass() {
		ArrayList<String> test = Main.getWordLadderBFS("FLOOD", "FRONT");
		Main.printLadder(test);
		assertFalse(test == null || test.size() == 0);
		assertTrue(test.size() < 10);
	}
	
	@Test(timeout = 30000)
	public void bfsNormalTestFail() {
		ArrayList<String> test = Main.getWordLadderBFS("FLOOD", "ALOOF");
		assertTrue(test == null || test.size() == 0);
	}
	
	@Test(timeout = 30000)
	public void bfsTestifNeighbors() {
		ArrayList<String> test = Main.getWordLadderBFS("CAVED", "CAVES");
		Main.printLadder(test);
		assertFalse(test == null || test.size() == 0);
		assertTrue(test.size() < 3);
	}
	
	@Test
	public void noDuplicatesBFS(){
		ArrayList<String> test = Main.getWordLadderBFS("SMART", "MONEY");
		ArrayList<String> testDup = new ArrayList<String>();
		for(int i = 0; i < test.size(); i++){
			if(!testDup.contains(test.get(i))){
				testDup.add(test.get(i));
			}
		}
		assertEquals(test.size(), testDup.size());
	}
	
	@Test
	public void dfsNormalTestPass() {
		ArrayList<String> test = Main.getWordLadderDFS("FLOOD", "FRONT");
		Main.printLadder(test);
		assertFalse(test == null || test.size() == 0);
		assertTrue(test.size() < 1000);
	}
	
	@Test
	public void dfsNormalTestFail() {
		ArrayList<String> test = Main.getWordLadderDFS("FLOOD", "ALOOF");
		assertTrue(test == null || test.size() == 0);
	}
	
	@Test
	public void noDuplicatesDFS(){
		ArrayList<String> test = Main.getWordLadderDFS("SMART", "MONEY");
		ArrayList<String> testDup = new ArrayList<String>();
		for(int i = 0; i < test.size(); i++){
			if(!testDup.contains(test.get(i))){
				testDup.add(test.get(i));
			}
		}
		assertEquals(test.size(), testDup.size());
	}
}
