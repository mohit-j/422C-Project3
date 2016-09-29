/* WORD LADDER Node.java
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

public class Node {
	String word;
	Node parent;
	
	public Node(){
		word = "";
		parent = null;
	}
	
	public Node(String root){
		word = root;
		parent = null;
	}
	
	public Node(String w, Node p){
		word = "";
		word += w.toUpperCase();
		parent = p;
	}
	
}
