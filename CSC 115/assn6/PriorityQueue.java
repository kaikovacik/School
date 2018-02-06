/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: November 21, 2017
 * Filename: PriorityQueue.java
 * Details: CSC115 Assignment 6
 */

import java.util.NoSuchElementException;


public class PriorityQueue<E extends Comparable<E>> 
{	
	private Heap<E>  heap;

	/**
	 * Create an empty priority queue.
	 */
	public PriorityQueue() 
	{
		heap = new Heap<E>();
	}

	/**
	 * Inserts an item into the queue.
	 * @param item The item to insert.
	 */
 	public void enqueue(E item)
 	{
 		heap.insert(item);
 	}

 	/**
	 * Removes the highest priority item from the queue.
	 * @return The item.
	 * @throws java.util.NoSuchElementException if the queue is empty.
	 */
 	public E dequeue()
 	{
 		return heap.removeRootItem();
 	}

 	/**
	 * Retrieves, but does not remove the next item from the queue.
	 * @return The item with the highest priority in the queue.
	 * @throws java.util.NoSuchElementException if the queue is empty.
	 */
 	public E peek()
 	{
 		return heap.getRootItem();
 	}	

 	/**
	 * @return True if the queue is empty, false if it is not.
	 */
 	public boolean isEmpty()
 	{
 		return heap.isEmpty();
 	}	

 	/**
	 * Used for internal testing purposes.
	 * @param args Not used.
	 */
 	public static void main(String[] args) 
	{
 		PriorityQueue<ER_Patient> test = new PriorityQueue<>();
 		ER_Patient[] patients = new ER_Patient[10];
		String[] complaints = {"Walk-in", "Life-threatening","Chronic","Major fracture", 
							"Chronic", "Major fracture", "Walk-in", "Life-threatening", "Chronic", "Walk-in"};

		for(int i=0; i<10; i++) 
		{
			patients[i] = new ER_Patient(complaints[i]);
			// spread out the admission times by 1 second
			try 
			{
				Thread.sleep(1000);
				System.out.print("PATIENTS CREATED: " + (i+1) + "/10");
				System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
			}
			catch(InterruptedException e) 
			{
				System.out.println("sleep interrupted");
				return;
			}
		}

    	System.out.println("===================BEGGINING OF TESTING================\n");

		// Tests enqueue().
		for(int i=0; i<10; i++) 
		{
			System.out.println("Enqueing: " + patients[i]);
			test.enqueue(patients[i]);
		}

		// Tests isEmpty().
		System.out.println("\nChecking if empty: " + test.isEmpty());
		// Tests peek().
		System.out.println("\nPeeking first in queue: " + test.peek());
		// Tests dequeue().
		System.out.println("\nDequeues in order: ");
		try
		{
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
			System.out.println(test.dequeue());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("\n====================END OF TESTING=====================");
 	}
}