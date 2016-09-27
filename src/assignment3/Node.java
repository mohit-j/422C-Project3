package assignment3;

public class Node {
	String word;
	Node parent;
	
	public Node(){
		word = "";
		parent = null;
	}

	public Node(String w, Node p){
		word = "";
		word += w.toUpperCase();
		parent = p;
	}
	
}
