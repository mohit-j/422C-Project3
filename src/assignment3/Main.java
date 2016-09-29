/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Mohit Joshi
 * msj696
 * #16475
 * Ram Muthukumar
 * rm48763
 * #16470
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
	
	/**
	 * This method connects the scanner to the keyboard and runs a test case of printLadder()
	 * @param args
	 * @throws Exception
	 */
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
		
		initialize();
		input = parse(kb);
		printLadder(getWordLadderDFS(input.get(0), input.get(1)));
	}
	
	/**
	 * This method instantiates the global variables and generates the list of neighbors for every word
	 */
	public static void initialize() {
		
		words = makeDictionary();
		bfsQueue = new LinkedList<Node>();
		markedWords = new HashSet<String>();
		neighbors = new HashMap<String, HashSet<String>>();
		generateNeighbors();
	}
	
	/**
	 * This method reads from the scanner and returns the first two words
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of 2 Strings containing start word and end word. 
	 * If command is /quit, exit the program
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		
		ArrayList<String> in = new ArrayList<String>();
		do {
			
			in.add(keyboard.next().toUpperCase());			//add input from console
			if(in.get(0).equals("/QUIT") || (in.size()>1 && in.get(1).equals("/QUIT")))
				System.exit(1);				//check if user wants to quit
			
		} while(in.size() < 2);
		
		return in;
	}
	
	/**
	 * This method creates a ladder connecting the start and end word using an DFS algorithm
	 * @param start is the starting word
	 * @param end is the goal word
	 * @return ArrayList containing the ladder, or an empty ArrayList if a ladder does not exist
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		markedWords = new HashSet<String>();
		Node bottom = depthFirstSearch(new Node(start), end);
		ArrayList<String> ladder = new ArrayList<String>();
		Node temp = bottom;					//start at ending word
		while(temp != null){				
			ladder.add(0, temp.word);		//add to ladder
			temp = temp.parent;				//move on to the previous word
		}
		return shorten(ladder); 
	}
	
    /**
     * This method creates a ladder connecting the start and end word using an BFS algorithm
     * @param start is the starting word
	 * @param end is the goal word
     * @return ArrayList containing the ladder, or an empty ArrayList if a ladder does not exist
     */
	public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		markedWords = new HashSet<String>();
		bfsQueue = new LinkedList<Node>();
    	Node bottom = breadthFirstSearch(start, end);
		ArrayList<String> ladder = new ArrayList<String>();
		Node temp = bottom;					//start at ending word
		while(temp != null){				
			ladder.add(0, temp.word);		//add to ladder
			temp = temp.parent;				//move on to the previous word
		}
		return ladder; 
	}
    
	/**
	 * This method adds all the words from a text file into a set
	 * @return Set containing all the words found in the text file
	 */
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
	
	/**
	 * This method prints a given ladder to the console
	 * @param ladder is the ladder to be printed
	 */
	public static void printLadder(ArrayList<String> ladder) {
		
		if(ladder == null || ladder.isEmpty())		//check if a ladder was found
		{
			System.out.println("no word ladder can be found between " + input.get(0).toLowerCase() + " and " + input.get(1).toLowerCase() + ".");
		}
		else										//print ladder if it exists
		{
			System.out.println("a " + (ladder.size()-2) + "-rung word ladder exists between " + ladder.get(0).toLowerCase() + " and " + ladder.get(ladder.size()-1).toLowerCase() + ".");
			for(String s : ladder)
				System.out.println(s.toLowerCase());
		}
	}
	
	/**
	 * This method generates a list of all words 1 letter away for every word in the dictionary
	 */
	public static void generateNeighbors() {
		
		if(neighbors!=null && !neighbors.isEmpty())
			neighbors.clear();
		
		for(String s : words)
		{
		    HashSet<String> nlist = new HashSet<String>();
		    for(int i=0; i<5; i++)
		    {									//test every possible word 1 letter away
				String test = s;
		    	for(int j=1; j<26; j++)
		    	{
		    		if(test.charAt(i) == 'Z')
		    			test = test.substring(0, i) + 'A' + test.substring(i+1, s.length());	
		    		else
		    			test = test.substring(0, i) + (char)(test.charAt(i)+1) + test.substring(i+1, s.length());
		    		
		    		if(words.contains(test))	//check if it's in the dictionary
		    		{
		    			nlist.add(test);		//add it to list of neighbors
		    		}
		    	}
		    }
		    neighbors.put(s, nlist);
		}
	}
	
	/**
	 * This function performs BFS and returns a node containing the end word and a path back to the root
	 * @param start the node containing the start word
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
	
	/**
	 * This function performs DFS and returns a node containing the end word and a path back to the root
	 * @param start the node containing the start word
	 * @param end the word to search for
	 * @return node containing parent nodes back to root
	 */
	public static Node depthFirstSearch(Node start, String end) {
		
		markedWords.add(start.word);
														//base case
		if(start.word.equals(end))						//check if we have reached the end word
			return start;
		else if(markedWords.size() == words.size())		//check if we have gone through every word
			return null;
		
		for(String s : neighbors.get(start.word))		//iterate through a word's neighbors
			if(!markedWords.contains(s))
			{
				Node n = depthFirstSearch(new Node(s, start), end);
				if(n == null)							//recursively call itself with new start node
					continue;
				return n;
			}
		return null;
	}
	
	/**
	 * This method takes a ladder and attempts to shorten it by looking for if any element has a neighbor elsewhere in the ladder
	 * @param list the ladder to be shortened
	 * @return ArrayList containing the shortened ladder
	 */
	public static ArrayList<String> shorten(ArrayList<String> list) {
		
		int start = 0;
		int end = 0;
		
		for(int j=0; j<list.size(); j++)			//iterate starting from front
		{
			for(int k=list.size()-1; k>j+1; k--)		//iterate starting from back
			{
				if(neighbors.get(list.get(j)).contains(list.get(k)) || list.get(j).equals(list.get(k)))
				{
					if(k-j-1 > end-start)
					{
						start = j+1;					//check if the two words are neighbors
						end = k;
					}
				}
			}
		}
		
		list.subList(start, end).clear();			//remove everything in between
		
		return list;
	}
}
