/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Mohit Joshi
 * msj696
 * #16475
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL: https://github.com/mohit-j/422C-Project3.git
 * Fall 2016
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	public static Set<String> words;
	public static ArrayList<String> input;
	public static Queue<Node> bfsQueue;
	public static Set<String> markedWords;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);					// redirect output to ps
		} else {
			kb = new Scanner(System.in);		// default from Stdin
			ps = System.out;					// default to Stdout
		}
		
		initialize(kb);
		
		if(isConnected(input.get(0), input.get(1)))
			System.out.println("Connected");
		else
			System.out.println("Not Connected");
	}
	
	public static void initialize(Scanner keyboard) {
		
		words = makeDictionary();
		if(input != null)
			input.clear();
		input = parse(keyboard);
		bfsQueue = new LinkedList();
		markedWords = new HashSet<String>();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		
		ArrayList<String> in = new ArrayList<String>();
		while(in.size() < 2)
		{
			in.add(keyboard.next());
		}
		
		return in;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		
		return null; // replace this line later with real return
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
    	// Returned list should be ordered start to end.  Include start and end.
    	// Return empty list if no ladder.
		
		return null; // replace this line later with real return
	}
    
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
	
	public static void printLadder(ArrayList<String> ladder) {
		
	}
	
	public static boolean isConnected(String a, String b) {
		
		if(!a.substring(0,1).equals(b.substring(0,1)) && a.substring(1,2).equals(b.substring(1,2)) && a.substring(2,3).equals(b.substring(2,3)) && a.substring(3,4).equals(b.substring(3,4)) && a.substring(4,5).equals(b.substring(4,5)))
			return true;
		if(a.substring(0,1).equals(b.substring(0,1)) && !a.substring(1,2).equals(b.substring(1,2)) && a.substring(2,3).equals(b.substring(2,3)) && a.substring(3,4).equals(b.substring(3,4)) && a.substring(4,5).equals(b.substring(4,5)))
			return true;
		if(a.substring(0,1).equals(b.substring(0,1)) && a.substring(1,2).equals(b.substring(1,2)) && !a.substring(2,3).equals(b.substring(2,3)) && a.substring(3,4).equals(b.substring(3,4)) && a.substring(4,5).equals(b.substring(4,5)))
			return true;
		if(a.substring(0,1).equals(b.substring(0,1)) && a.substring(1,2).equals(b.substring(1,2)) && a.substring(2,3).equals(b.substring(2,3)) && !a.substring(3,4).equals(b.substring(3,4)) && a.substring(4,5).equals(b.substring(4,5)))
			return true;
		if(a.substring(0,1).equals(b.substring(0,1)) && a.substring(1,2).equals(b.substring(1,2)) && a.substring(2,3).equals(b.substring(2,3)) && a.substring(3,4).equals(b.substring(3,4)) && !a.substring(4,5).equals(b.substring(4,5)))
			return true;
		return false;
	}
	
	/**
	 * This function performs the depth first search and returns a node containing the end word and a path back to the root
	 * @param end the word to search for
	 * @return node containing parent nodes back to root
	 */
	public static Node breadthFirstSearch(String start, String end){
		
		Node root = new Node(start);
		bfsQueue.add(root);
		Iterator queueIndex = bfsQueue.iterator();
		while(queueIndex.hasNext()){
			Node current = (Node) queueIndex.next();
			if(!markedWords.contains(current.word)){	//if its not already been checked
				if(current.word == end){				//if it equals the end word, return it
					return current;	
				}
				for(int i = 0; i < hashmap.get(current.word).length; i++){		//replace hashmap with name of hashmap containing string key arraylist<string> values
					bfsQueue.add(new Node(hashmap.get(current.word).get(i), current));				//add every neighbor to the queue
				}
			}
			bfsQueue.remove();
		}
		return null;
	}
}
