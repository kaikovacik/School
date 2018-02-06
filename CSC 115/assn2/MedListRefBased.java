/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: September 25, 2017
 * Filename: MedListRefBased.java
 * Details: CSC115 Assignment 2
 */ 

import java.util.NoSuchElementException;

public class MedListRefBased implements List<Medication>
{
	private MedicationNode head;
	private MedicationNode tail;
	private int count;

	public MedListRefBased()
	{
		head = null;
		tail = null;
		count = 0;
	}

	/**
	 * Inserts an item to into the list at the positioned index.
	 * Any items after the new item will have their index position increased by one.
	 * For example, if <code>list</code> is a list of integers {1,2,3}, then
	 * <code>list.add(9,0)</code> will alter the list to contain {9,1,2,3}, in 
	 * that order.
	 * Another call, <code>list.add(5,2)</code> will alter the list to contain {9,1,5,2,3},
	 * in that order.
	 * @param item The object to add to the list.
	 * @param index The index position for the new item.
	 * Note that the index position may be equal to the number of items, meaning that
	 * the item will be placed at the end of the list.
	 * @throws IndexOutOfBoundsException if the index is outside(0 &hellip; size).
	 */ 
	public void add(Medication item, int index)
	{
		MedicationNode newNode = new MedicationNode(item);
		MedicationNode curr = null;

		//checks if index is out of bounds
		if(index > count || index < 0)
		{
			throw new IndexOutOfBoundsException("Index must be 0 to" + " " + count + "!" + "Was " + index);
		}
		else
		{
			//when list is empty, creates new node and assigns head and tail
			if(count == 0)
			{
				head = newNode;
				tail = newNode;
			
			}
			//when list not empty...
			else
			{
				//adds at head
				if(index == 0)
				{
					newNode.next = head;
					head = newNode;
				
				}
				//adds at tail
				else if(index == count)
				{
					tail.next = newNode;
					newNode.prev = tail;
					tail = newNode;
				
				}
				//adds elsewhere
				else
				{
					//traverses from head if index closer to head
					if(index <= count/2)
					{
						curr = head;
	
						for(int i = 0; i < index-1; i++)
						{
							curr = curr.next;
						}
						newNode.next = curr.next;
						curr.next.prev = newNode;
						curr.next = newNode;
						newNode.prev = curr;
					}
					//traverses from tail if index closer to tail
					else if(index > count/2)
					{
						curr = tail;

						for(int i = count+1; i > index+1; i--)
						{
							curr = curr.prev;
						}
						newNode.next = curr.next;
						curr.next.prev = newNode;
						curr.next = newNode;
						newNode.prev = curr;
					}	
				}	
			}
			count++;	
		}
	}


	/**
 	 * Accesses the item by its position in the list.
 	 * @param index The index of the position.
 	 * @return The item at that index position.
	 * @throws IndexOutOfBoundsException if the index is outside (0 &hellip; size-1).
	 */ 
	public Medication get(int index)
	{
		MedicationNode curr = null;

		//checks if index is out of bounds
		if(index >= count || index < 0)
		{
			throw new IndexOutOfBoundsException("Index must be 0 to" + " " + (count-1) + "!" + "Was " + index);
		}
		else
		{	
			//traverses from head if index closer to head
			if(index <= count/2)
			{
				curr = head;

				for(int i = 0; i < index; i++)
				{
					curr = curr.next;
				}	
			}
			//traverses from tail if index closer to tail
			else if(index > count/2)
			{
				curr = tail;

				for(int i = count; i > index+1; i--)
				{
					curr = curr.prev;
				}	
			}
		}
		return curr.item;
	}

	/**
	 * @return true if the list is empty, false otherwise.
 	 */
	public boolean isEmpty()
	{
		return head == null && tail == null;
	}

	/**
 	 * @return The number of items in the list.
	 */
	public int size()
	{
		return count;
	}

	/**
	 * Finds the index position of an equivalent item in the list.
	 * @param item The item that is equivalent to the item we are looking for.
	 * @return The index position of the equivalent item.
	 * If the item is not in the list, then -1 is returned.
	 */
	public int indexOf(Medication item)
	{
		MedicationNode curr = head;
		int index = 0;

		//traverses list
		while(curr != null)
		{
			//stops traverse at given item
			if(item.equals(curr.item))
			{
				return index;
			}
			index++;
			curr = curr.next;
		}
		//returns -1 if item not found
		return -1;
	}

	/**
	 * Removes the item at the index position of the list.
	 * @param index The index number.
	 * @throws IndexOutOfBoundsException if the index is outside (0 &hellip; size-1).
	 */
	public void remove(int index)
	{
		MedicationNode curr = null;

		//checks if index is out of bounds
		if(index >= count || index < 0)
		{
			throw new IndexOutOfBoundsException("Index must be 0 to" + " " + (count-1) + "! " + "Was " + index);
		}
		else
		{
			//only activates remove method if list not empty
			if(count > 0)
			{
				//removes head
				if(index == 0)
				{
					head.next.prev = null;
					head = head.next;
				
				}
				//removes tail
				else if(index == count-1)
				{
					tail.prev.next = null;
					tail = tail.prev;
				
				}
				//removes elsewhere
				else
				{
					//traverses from head if index closer to head
					if(index <= count/2)
					{
						curr = head;
	
						for(int i = 0; i < index-1; i++)
						{
							curr = curr.next;
						}
						curr.next = curr.next.next;
						curr.next.prev = curr;
					}
					//traverses from tail if index closer to tail
					else if(index > count/2)
					{
						curr = tail;

						for(int i = count; i > index+1; i--)
						{
							curr = curr.prev;
						}
						curr.next = curr.next.next;
						curr.next.prev = curr;
					}	
				}
			}
			count--;	
		}
	}

	/**
 	 * Removes all the items from the list, resulting in an empty list.
	 */
	public void removeAll()
	{
		head = null;
		tail = null;
		count = 0;
	}

	/**
	 * Removes all matching items from the list.
	 * For example, if the list is made up of integers and contains {67,12,13,12},
	 * then <code>remove(12)</code>
	 * will alter the list to {67,13}.
	 * @param value The item that identies what to remove. 
	 *	Any item in the list that matches this item will be removed.
	 * 	Note that if no matching item can be found, then nothing is removed.
 	 */
	public void remove(Medication value)
	{
		int index = 0;
		
		//removes given item until no same item present in list
		while(index != -1)
		{
			index = indexOf(value);
			//ensures the removal of item ONLY
			if(index == -1)
			{
				break;
			}
			else
			{
				if(get(index) == null)
				{
					break;
				}	
				remove(index);
			}
		}
	}

	/**
 	 * A printout of the list is in the form:
	 * <code>List: {&lt;item1&gt;,
	 *	&lt;item2&gt;,
	 *	&hellip;,
	 *	&lt;last item&gt;}
	 * </code>
	 * where each item is the string returned by its <code>toString</code> method.
	 * Note that all the items can be listed on one line or placed on separate lines, depending on the
	 * aesthetics.
	 * An empty list is represented by <code>List: {}</code>
	 * @return The string representation of the list, formatted as above.
	 */
	public String toString()
	{
		String list = "";
		MedicationNode curr = head;

		for(int i = 0; i < count; i++)
		{
			//first item prints on it's own
			if(i == 0)
			{
				list = list + curr.item;
			
			}
			//all following items formatted
			else
			{
				list = list + ", " + curr.item;
			}
			curr = curr.next;
		}
	return list;
	}
}