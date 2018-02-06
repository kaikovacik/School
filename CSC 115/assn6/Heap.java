/*
 * Name: Kai Kovacik
 * ID: V00880027
 * Date: November 21, 2017
 * Filename: Heap.java
 * Details: CSC115 Assignment 6
 */

import java.util.NoSuchElementException;
import java.util.Vector;


public class Heap<E extends Comparable<E>> 
{
	private Vector<E> heapArray;;

	/**
	 * Create an empty heap.
	 */
	public Heap() 
	{
		heapArray = new Vector<E>();
	}

	/**
	 * @return True if the heap is empty, false if it is not.
	 */
	public boolean isEmpty()
	{
		return heapArray.isEmpty();
	}

	/**
	 * @return The number of items in the heap.
	 */
	public int size()
	{
		return heapArray.size();
	}

	/**
	 * Inserts an item into the heap.
	 * @param item The newly added item.
	 */
	public void insert(E item)
	{
		heapArray.add(item);
		siftUp(size()-1);
	}

	/**
	 * Removes the item at the root node of the heap.
	 * @return The item at the root of the heap.
	 * @throws java.util.NoSuchElementException if the heap is empty.
	 */
	public E removeRootItem()
	{
		E rootItem;
		if(isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		// Removes item with no need to sift.
		else if(size() == 1)
			rootItem = heapArray.remove(0);
		// Removes item and sifts.
		else
		{
			rootItem = heapArray.remove(0);
			heapArray.add(0, heapArray.remove(size()-1));
			siftDown(0);
		}
		return rootItem;	
	}

	/**
	 * Retrieves, without removing the item in the root.
	 * @return The top item in the tree.
	 * @throws java.util.NoSuchElementException if the heap is empty.
	 */
	public E getRootItem()
	{
		if(isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		else
			return heapArray.get(0);
	}	

	/**
	 * Helper method that checks if items at given indeces
	 * are in order by precidence.
	 * @throws ArrayIndexOutOfBoundsException If index 1 is out of bounds.
	 * @param index1 The first index.
	 * @param index2 The second index.
	 * @return whether item atindex2 has a lower 
	 * precidence than item at index1 or not.
	 */
	private boolean orderIs(int index1, int index2)
	{
		try
		{
			return nullAt(index2) || heapArray.get(index1).compareTo(heapArray.get(index2)) >= 0;
		}
		// Returns false if index1 is out of bounds.
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
	}

	/**
	 * Helper method that checks if the given index is in bounds.
	 * @param index The given index/
	 * @return whether the index is out of bounds or not.
	 */
	private boolean nullAt(int index)
	{
		return index > size()-1 || index < 0;
	}

	/**
	 * Recursive helper method called by insert.
	 * @param index The index of the item being added.
	 * @param item The item being added.
	 */
	private void siftUp(int index)
	{
		int parentIndex = (index-1)/2;

		// Basecase when index is root or inappropriate to swap.
		if(nullAt(parentIndex) || orderIs(index, parentIndex))
			return;
		else
		{
			heapArray.add(index-1, heapArray.remove(parentIndex));
			heapArray.add(parentIndex, heapArray.remove(index));
			siftUp(parentIndex);
		}
	}

	/**
	 * Recursive helper method called by removeRootItem.
	 * @param index The index of the item being removed.
	 * @param item The item being added.
	 */
	private void siftDown(int index)
	{
		int leftIndex = 2*index+1;
		int rightIndex = 2*index+2;

		// Basecase when no daughters or when both daughters are greater.
		if(nullAt(leftIndex) || !orderIs(index, rightIndex) || !orderIs(index, leftIndex))
			return;
		else if(nullAt(rightIndex) || orderIs(rightIndex, leftIndex))
		{
			heapArray.add(index, heapArray.remove(leftIndex));
			heapArray.add(leftIndex, heapArray.remove(index+1));
			siftDown(leftIndex);
		}
		else
		{
			heapArray.add(index, heapArray.remove(rightIndex));
			heapArray.add(rightIndex, heapArray.remove(index+1));
			siftDown(rightIndex);
		}
	}

	/**
	 * Used for internal testing purposes.
	 * @param args Not used.
	 */
	public static void main(String[] args)
	{
		Heap<Integer> heap = new Heap<>();
		System.out.println("inserting '2'");
		heap.insert(2);
		System.out.println("inserting '6'");
		heap.insert(6);
		System.out.println("inserting '8'");
		heap.insert(8);
		System.out.println("inserting '1'");
		heap.insert(1);
		System.out.println("inserting '3'");
		heap.insert(3);
		System.out.println("inserting '4'");
		heap.insert(4);
		System.out.println("inserting '5'");
		heap.insert(5);
		System.out.println("inserting '2'");
		heap.insert(2);
		System.out.println("inserting '7'");
		heap.insert(7);
		System.out.println("inserting '9'");
		heap.insert(9);
		System.out.println("inserting '3'");
		heap.insert(3);
		System.out.println("inserting '1'");
		heap.insert(1);
		System.out.println("inserting '10'");
		heap.insert(10);
		System.out.println();
		System.out.println("Heap root removal is in following order:");
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
		System.out.println(heap.removeRootItem());
	}
}