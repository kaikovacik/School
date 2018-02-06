/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: October 12, 2017
 * Filename: StringStack.java
 * Details: CSC115 Assignment 3
 */

public class StringStack 
{
	private Node head;

	/**
	 * A public method that checks if the stack is empty.
	 * @return true if head == null.
	 */
	public boolean isEmpty() 
	{
		return head == null;
	}

	/**
	 * A public method that removes and returns the string from the top.
	 * @return item The string on top of the stack.
	 * @throws StackEmptyException if stack is empty.
	 */
	public String pop() 
	{
		String item;

		if(isEmpty())
		{
			throw new StackEmptyException("Stack is empty");
		}
		item = head.item;
		head = head.next;
		return item;	
	}

	/**
	 * A public method that returns the string from the top but does not remove it.
	 * @return item The string on top of the stack.
	 * @throws StackEmptyException if stack is empty.
	 */
	public String peek() 
	{
		if(isEmpty())
		{
			throw new StackEmptyException("Stack is empty");
		}
		else
		{
			return head.item;
		}	
	}

	/**
	 * A public method that adds the string to the top.
	 * @param item The string that is being added to the stack
	 * @throws StackEmptyException if stack is empty
	 */
	public void push(String item) 
	{
		Node n = new Node(item);

		n.next = head;
		head = n;
	}

	/**
	 * A public method that removes all items in the stack.
	 */
	public void popAll() 
	{
		head = null;
	}

	public static void main(String[] args) {
		
	}
}