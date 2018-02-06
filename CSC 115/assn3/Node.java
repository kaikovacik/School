/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: October 12, 2017
 * Filename: Node.java
 * Details: CSC115 Assignment 3
 */

public class Node
{
	protected String item;
	protected Node next;

	//Constructs full node
	public Node(String item)
	{
		this.item = item;
		next = null;
	}

	//Constructs empty node
	public Node()
	{
		this.item = item;
		next = null;
	}
}

