package assignment3;

public class Node {
	String word;
	boolean marked;
	Node parent;
	
	public Node(){
		word = "";
		marked = false;
		parent = null;
	}

	public Node(String w, Node p){
		word = "";
		word += w.toUpperCase();
		marked = false;
		parent = p;
	}
	
}
