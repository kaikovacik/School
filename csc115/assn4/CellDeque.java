/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: October 13, 2017
 * Filename: CellDeque.java
 * Details: CSC115 Assignment 4
 */


public class CellDeque
{
	private CellNode head;
	private CellNode tail;
	private int count;


	//creates an empty Deque object.
	public CellDeque()
	{
		head = null;
		tail = null;
		count = 0;
	}


	//inserts into the front of Deque.
	public void insertFirst(Cell item) 
	{
		CellNode newNode = new CellNode(item);

		if(count == 0)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		count++;
	}


 	//inserts into the end of the Deque.
	public void insertLast(Cell item)
	{
		CellNode newNode = new CellNode(item);

		if(count == 0)
		{
			head = newNode;
			tail = newNode;
		}
		else
		{
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		count++;
	}


	//accesses the first item in the Deque (does not remove it).
	public Cell first()
	{
		return head.item;
	}


	//accesses the last item in the Deque (does not remove it).
	public Cell last() 
	{
		return tail.item;
	}


	//removes the item at the front of the Deque (and returns it).
	public Cell removeFirst() 
	{
		CellNode temp = head;

		if(count == 0)
		{
			throw new DequeEmptyException("Can't remove from empty deque");
		}
		else if(count == 1)
		{
			head = null;
			tail = null;
		}
		else
		{
			head = head.next;
			head.prev = null;
		}
		count--; 

		return temp.item;
	}


	//removes the item at the back of the Deque (and returns it).
	public Cell removeLast() 
	{
		CellNode temp = tail;

		if(count == 0)
		{
			throw new DequeEmptyException("Can't remove from empty deque");
		}
		else if(count == 1)
		{
			head = null;
			tail = null;
		}
		else
		{
			tail = tail.prev;
			tail.next = null;
		}
		count--; 

		return temp.item;
	}


	//returns true if the Deque is empty, false otherwise.
	public boolean isEmpty() 
	{
		return head == null && tail == null;
	}

	//removes all items from the Deque.
	public void makeEmpty()
	{
		head = null;
		tail = null;
		count = 0;
	}

	//returns the size of the Deque.
	public int size()
	{
		return count;
	}


	//String representation of the Deque.
	public String toString()
	{
		CellNode curr = head;
		String s = "";

		for(int i = 0; curr != null; i++)
		{
			if(i == 0)
			{
				s = "" + curr.item;
			}
			else if(i== count-1)
			{
				s = s + "; " + curr.item;
			}
			else
			{
				s = s + "; " + curr.item;
			}
			curr= curr.next;
		}
		return s;
	}
}