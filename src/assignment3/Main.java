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
	public static HashMap<String, HashSet<String>> neighbors;
	
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
		generateNeighbors();
		
//		System.out.println(neighbors.get("HOSTS").toString());
		printLadder(getWordLadderDFS(input.get(0), input.get(1)));
		
	}
	
	public static void initialize(Scanner keyboard) {
		
		if(input!=null && !input.isEmpty())
			input.clear();
		input = parse(keyboard);
		words = makeDictionary();
		bfsQueue = new LinkedList<Node>();
		markedWords = new HashSet<String>();
		neighbors = new HashMap<String, HashSet<String>>();
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		
		ArrayList<String> in = new ArrayList<String>();
		do {
			
			in.add(keyboard.next().toUpperCase());
			if(in.get(0).equals("/QUIT") || (in.size()>1 && in.get(1).equals("/QUIT")))
				System.exit(1);
			
		} while(in.size() < 2);
		
		return in;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// Return empty list if no ladder.
		
		Node bottom = depthFirstSearch(new Node(start), end);
		ArrayList<String> ladder = new ArrayList<String>();
		Node temp = bottom;
		while(temp != null){
			ladder.add(0, temp.word);
			temp = temp.parent;
		}
		return ladder; 
	}
	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
    	// Returned list should be ordered start to end.  Include start and end.
    	// Return empty list if no ladder.
		
    	Node bottom = breadthFirstSearch(start, end);
		ArrayList<String> ladder = new ArrayList<String>();
		Node temp = bottom;
		while(temp != null){
			ladder.add(0, temp.word);
			temp = temp.parent;
		}
		return ladder; // replace this line later with real return
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
		
		if(ladder == null || ladder.isEmpty())
		{
			System.out.println("no word ladder can be found between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase() + ".");
		}
		else
		{
			System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase() + ".");
			for(String s : ladder)
				System.out.println(s);
		}
	}
	
	public static void generateNeighbors() {
		
		if(neighbors!=null && !neighbors.isEmpty())
			neighbors.clear();
		
		for(String s : words)
		{
		    HashSet<String> nlist = new HashSet<String>();
		    for(int i=0; i<5; i++)
		    {
				String test = s;
		    	for(int j=1; j<26; j++)
		    	{
		    		if(test.charAt(i) == 'Z')
		    			test = test.substring(0, i) + 'A' + test.substring(i+1, s.length());	
		    		else
		    			test = test.substring(0, i) + (char)(test.charAt(i)+1) + test.substring(i+1, s.length());
		    		
		    		if(words.contains(test))
		    		{
		    			nlist.add(test);
		    		}
		    	}
		    }
		    neighbors.put(s, nlist);
		}
	}
	
	/**
	 * This function performs the depth first search and returns a node containing the end word and a path back to the root
	 * @param end the word to search for
	 * @return node containing parent nodes back to root
	 */
	public static Node breadthFirstSearch(String start, String end){
		
		Node root = new Node(start);
		bfsQueue.add(root);
		while(!bfsQueue.isEmpty()){
			Node current = bfsQueue.poll();
			if(!markedWords.contains(current.word)){	//if its not already been checked
				markedWords.add(current.word);
				if(current.word.equals(end)){				//if it equals the end word, return it
					return current;	
				}
				for(String s : neighbors.get(current.word)){		
					bfsQueue.add(new Node(s, current));				//add every neighbor to the queue
				}
			}
		}
		return null;
	}
	
	public static Node depthFirstSearch(Node start, String end) {
		
		markedWords.add(start.word);
		
		if(start.word.equals(end))
			return start;
		else if(markedWords.size() == words.size())
			return null;
		
		for(String s : neighbors.get(start.word))
			if(!markedWords.contains(s))
			{
				Node n = depthFirstSearch(new Node(s, start), end);
				if(n == null)
					continue;
				return n;
			}
		return null;
	}
}
