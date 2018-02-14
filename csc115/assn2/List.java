/**
 * CSC115 Assignment 2 : Containers.
 * List.java
 * Created for use by CSC115 Summer2016 
 */

/*
 * NOTE TO PROGRAMMER:
 * Do not alter this file!
 */

/**
 * A completed interface for a list container.
 * This ADT specification is similar, but not identical to the List ADT
 * described in the textbook.
 */
public interface List<E> {

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
	public void add(E item, int index);	
	
	/**
 	 * Accesses the item by its position in the list.
 	 * @param index The index of the position.
 	 * @return The item at that index position.
	 * @throws IndexOutOfBoundsException if the index is outside (0 &hellip; size-1).
	 */ 
	public E get(int index);

	/**
	 * @return true if the list is empty, false otherwise.
 	 */
	public boolean isEmpty();

	/**
 	 * @return The number of items in the list.
	 */
	public int size();

	/**
	 * Finds the index position of an equivalent item in the list.
	 * @param item The item that is equivalent to the item we are looking for.
	 * @return The index position of the equivalent item.
	 * If the item is not in the list, then -1 is returned.
	 */
	public int indexOf(E item);

	/**
	 * Removes the item at the index position of the list.
	 * @param index The index number.
	 * @throws IndexOutOfBoundsException if the index is outside (0 &hellip; size-1).
	 */
	public void remove(int index);

	/**
 	 * Removes all the items from the list, resulting in an empty list.
	 */
	public void removeAll();

	/**
	 * Removes all matching items from the list.
	 * For example, if the list is made up of integers and contains {67,12,13,12},
	 * then <code>remove(12)</code>
	 * will alter the list to {67,13}.
	 * @param value The item that identies what to remove. 
	 *	Any item in the list that matches this item will be removed.
	 * 	Note that if no matching item can be found, then nothing is removed.
 	 */
	public void remove(E value);

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
	public String toString();
}
